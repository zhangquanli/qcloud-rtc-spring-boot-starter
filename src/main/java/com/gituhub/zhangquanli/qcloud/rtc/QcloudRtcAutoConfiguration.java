package com.gituhub.zhangquanli.qcloud.rtc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * QcloudRtcAutoConfiguration
 *
 * @author zhangquanli
 */
@Configuration
@EnableConfigurationProperties(QcloudRtcProperties.class)
@ConditionalOnProperty(prefix = "qcloud.rtc", name = {"sdk-app-id", "private-key-path", "tls-sig"})
public class QcloudRtcAutoConfiguration {

    private QcloudRtcProperties qcloudRtcProperties;

    public QcloudRtcAutoConfiguration(QcloudRtcProperties qcloudRtcProperties) {
        this.qcloudRtcProperties = qcloudRtcProperties;
    }

    @Bean
    public QcloudRtc qcloudRtc() {
        return new QcloudRtcImpl(qcloudRtcProperties);
    }
}
