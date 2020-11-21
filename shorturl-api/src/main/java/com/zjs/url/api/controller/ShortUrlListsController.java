package com.zjs.url.api.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjs.url.api.common.R;
import com.zjs.url.api.entity.ShortUrlLists;
import com.zjs.url.api.exception.RcsException;
import com.zjs.url.api.service.IShortUrlListsService;
import com.zjs.url.api.utils.Decode64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


/**
 * <p>
 * 群二维码短链生成表 前端控制器
 * </p>
 *
 * @author zjs
 * @since 2020-10-29
 */
@RestController
@RequestMapping("/qr")
public class ShortUrlListsController {
    private static final Logger logger = LoggerFactory.getLogger(ShortUrlListsController.class);

    private static final String redisKeyFirst = "short:url:";

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private IShortUrlListsService iShortUrlListsService;

    @Resource
    private RestTemplate restTemplate;

    @Value("${short-config.baseUrl}")
    private String baseUrl;

    /**
     * 长链转锻炼
     * 生成二维码

     * @return
     */
    @RequestMapping(value = "/getShortUrl", method = RequestMethod.POST)
    public R postGetConferenceInfo(@RequestParam String longUrl,String qr) {
        try {
            //todo 生成短链 生成二维码
            if (StringUtils.isEmpty(longUrl)) {
                return R.error("error:获取二维码异常");
            }
            //插入对象
            //写入redis
            //先看下是不是有同样长链的存在 如果存在则更新过期时间并返回
            //url只有两个参数一个uid 一个uri
            ShortUrlLists shortUrlLists = iShortUrlListsService.getOne(new QueryWrapper<ShortUrlLists>()
                    .eq("long_url",longUrl)
            );
            if(ObjectUtils.isEmpty(shortUrlLists)){
                shortUrlLists = new ShortUrlLists();
                shortUrlLists.setQrCodeUrl(longUrl);
                iShortUrlListsService.save(shortUrlLists);
            }
            String shortUrl = Decode64.encode(shortUrlLists.getId());
            //保存成功后写入redis,并更新过期时间
            shortUrlLists.setQrCodeShortUrl(shortUrl);
            String redisKey = redisKeyFirst+shortUrl;
            redisTemplate.opsForValue().set(redisKey, longUrl, 7, TimeUnit.DAYS);
            return R.ok().put("shortBaseUrl",baseUrl).put("originGroupQrShortLists", shortUrlLists);
        } catch (Exception e) {
            throw new RcsException("|| 获取二维码异常 ||" + e.toString());
        }
    }
}
