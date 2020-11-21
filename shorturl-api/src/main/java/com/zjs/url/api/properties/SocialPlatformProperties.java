package com.zjs.url.api.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zjs
 */
@Getter
@Setter
@Component
@ConfigurationProperties("group-params")
public class SocialPlatformProperties {
    private String appKey;
    private String appId;
    private String url;
}
