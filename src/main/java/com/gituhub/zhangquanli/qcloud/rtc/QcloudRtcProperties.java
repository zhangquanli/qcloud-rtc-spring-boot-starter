package com.gituhub.zhangquanli.qcloud.rtc;

import com.gituhub.zhangquanli.qcloud.rtc.constants.TlsSig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * QcloudRtcProperties
 *
 * @author zhangquanli
 */
@ConfigurationProperties(prefix = "qcloud.rtc")
public class QcloudRtcProperties {

    /**
     * 应用编号，在【实时音视频-账号信息-应用基本信息】中查看
     */
    private Long sdkAppId;
    /**
     * 私钥路径，在【实时音视频-快速上手】中下载
     */
    private String privateKeyPath;
    /**
     * 签名算法，2019.07.19 之前使用 ECDSA_SHA256 算法，2019.07.19 之后使用 HMAC_SHA256 算法。
     */
    private TlsSig tlsSig;
    /**
     * 过期时间，默认180天后 UserSig 过期
     */
    private Long expire = 15552000L;
    /**
     * 直播参数，在【实时音视频-账号信息-直播信息】中查看
     */
    private Long bizId;
    /**
     * 播放域名
     */
    private String playDomain;
    /**
     * 播放密钥
     */
    private String playKey;
    /**
     * 过期时间，默认24小时后旁路推流的播放地址过期
     */
    private Long playExpire = 86400L;

    public Long getSdkAppId() {
        return sdkAppId;
    }

    public void setSdkAppId(Long sdkAppId) {
        this.sdkAppId = sdkAppId;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public TlsSig getTlsSig() {
        return tlsSig;
    }

    public void setTlsSig(TlsSig tlsSig) {
        this.tlsSig = tlsSig;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public String getPlayDomain() {
        return playDomain;
    }

    public void setPlayDomain(String playDomain) {
        this.playDomain = playDomain;
    }

    public String getPlayKey() {
        return playKey;
    }

    public void setPlayKey(String playKey) {
        this.playKey = playKey;
    }

    public Long getPlayExpire() {
        return playExpire;
    }

    public void setPlayExpire(Long playExpire) {
        this.playExpire = playExpire;
    }
}
