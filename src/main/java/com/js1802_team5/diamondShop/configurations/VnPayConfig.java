package com.js1802_team5.diamondShop.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "vnpay")
public class VnPayConfig {
    private String vnp_PayUrl;
    private String vnp_ReturnUrl;
    private String vnp_TmnCode;
    private String secretKey;
    private String vnp_ApiUrl;
    private String vnp_Version;
    private String vnp_Command;
    private String orderType;
    private String ipnUrl;
}
