package com.smarthr.smarthr.config;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
//Create bean of TwilioProperties populate from application properties
@EnableConfigurationProperties(TwilioProperties.class)
public class TwilioConfiguration {
}
