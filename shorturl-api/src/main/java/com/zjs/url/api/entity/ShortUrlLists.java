package com.zjs.url.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 群二维码短链生成表
 * </p>
 *
 * @author zjs
 * @since 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(autoResultMap = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShortUrlLists implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 二维码链接uuid
     */
    private String qrCodeUuid;

    /**
     * 二维码图片
     */
    private String qrCodePic;

    /**
     * 二维码到期时间，格式:EEE, d MMM yyyy HH:mm:ss GMT
     */
    private String expires;

    /**
     * 过期时间 时间格式yyyy-MM-dd HH:mm:ss
     */
    private String expireDate;

    /**
     * 群uri
     */
    private String groupUri;

    /**
     * 二维码对应的url
     */
    private String qrCodeUrl;

    /**
     * 二维码对应的url(短链)
     */
    private String qrCodeShortUrl;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
