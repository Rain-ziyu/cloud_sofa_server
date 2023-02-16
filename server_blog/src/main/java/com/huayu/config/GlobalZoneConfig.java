package com.huayu.config;


import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

import static com.huayu.enums.ZoneEnum.SHANGHAI;

/**
 * 设置全局时区
 *
 * @author User
 */
@Configuration
public class GlobalZoneConfig {

    @PostConstruct
    public void setGlobalZone() {
        TimeZone.setDefault(TimeZone.getTimeZone(SHANGHAI.getZone()));
    }

}
