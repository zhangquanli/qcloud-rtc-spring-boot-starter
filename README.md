# qcloud-rtc-spring-boot-starter
基于 `Spring Boot` 框架，快速集成【腾讯云】【实时音视频】服务

## 快速集成
1. 在 `pom.xml` 文件中，引入依赖
    ```xml
    <dependency>
        <groupId>com.github.zhangquanli</groupId>
        <artifactId>qcloud-rtc-spring-boot-starter</artifactId>
        <version>1.0.0</version>
    </dependency>
    ```
2. 在 `application.properties` 或 `application.yml` 文件中，进行配置
    ```properties
    qcloud.rtc.sdk-app-id=sdkAppId
    qcloud.rtc.private-key-path=privateKeyPath
    qcloud.rtc.tls-sig=ecdsa_sha256
    qcloud.rtc.expire=15552000
    qcloud.rtc.biz-id=bizId
    qcloud.rtc.play-domain=playDomain
    qcloud.rtc.play-key=playKey
    qcloud.rtc.play-expire=86400
    ```
    ```yaml
    qcloud:
      rtc:
        sdk-app-id: sdkAppId
        private-key-path: privateKeyPath
        tls-sig: ecdsa_sha256
        expire: 15552000
        biz-id: bizId
        play-domain: playDomain
        play-key: playKey
        play-expire: 86400
    ```

## 快速开发
1. 在 `Spring Boot` 项目中，可以直接注入 `QcloudRtc` 接口
    ```java
    @Resource
    private QcloudRtc qcloudRtc;
    ```
2. 使用 `QcloudRtc` 接口中封装好的方法

## 更新日志
### 1.0.0
1. 获取 `UserSig` 
2. 获取 `rtmp`, `flv`, `hls` 等协议的旁路推流播放地址
