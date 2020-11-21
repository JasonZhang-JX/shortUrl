package com.zjs.url.api.controller;

import com.zjs.url.api.entity.ShortUrlLists;
import com.zjs.url.api.service.IShortUrlListsService;
import com.zjs.url.api.utils.Decode64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZJS
 * @Date: 2020/11/2 10:02
 */
@RestController
@RequestMapping("/re")
public class RedirectController {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private IShortUrlListsService iShortUrlListsService;

    private static final String redisKeyFirst = "short:url:";
    /**
     * 302重定向

     * @param shortCode 短连接
     * @return 重定向
     */
    @RequestMapping(value = "/{shortCode}", method = RequestMethod.GET)
    public void getLongUrl(@PathVariable String shortCode, HttpServletResponse httpServletResponse){
        String redisKey = redisKeyFirst+shortCode;
        String longUrl = redisTemplate.opsForValue().get(redisKey);
        if (StringUtils.isEmpty(longUrl)) {
            int idNum = Decode64.decode(shortCode);
            ShortUrlLists shortUrlLists = iShortUrlListsService.getById(idNum);
            String codeUrl = shortUrlLists.getQrCodeUrl();
            if(!StringUtils.isEmpty(codeUrl)){
                httpServletResponse.setStatus(302);
                httpServletResponse.setHeader("Location", codeUrl);
            }
        } else {
            httpServletResponse.setStatus(302);
            httpServletResponse.setHeader("Location", longUrl);
        }
    }
}
