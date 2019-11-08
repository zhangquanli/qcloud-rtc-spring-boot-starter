package com.gituhub.zhangquanli.qcloud.rtc;

import com.gituhub.zhangquanli.qcloud.rtc.constants.TlsSig;
import com.gituhub.zhangquanli.qcloud.rtc.user_sig.TlsSigStrategy;
import com.gituhub.zhangquanli.qcloud.rtc.user_sig.UserSig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * QcloudRtcImpl
 *
 * @author zhangquanli
 */
public class QcloudRtcImpl implements QcloudRtc {
    private static final Logger log = LoggerFactory.getLogger(QcloudRtcImpl.class);

    private Long sdkAppId;
    private String privateKey;
    private TlsSig tlsSig;
    private Long expire;

    private Long bizId;
    private String playDomain;
    private String playKey;
    private Long playExpire;

    public QcloudRtcImpl(QcloudRtcProperties qcloudRtcProperties) {
        this.sdkAppId = qcloudRtcProperties.getSdkAppId();
        try {
            // 读取密钥文件
            String privateKeyPath = qcloudRtcProperties.getPrivateKeyPath();
            StringBuilder stringBuilder = new StringBuilder();
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(privateKeyPath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                stringBuilder.append(temp).append(System.lineSeparator());
            }
            bufferedReader.close();
            // 获取密钥
            this.privateKey = stringBuilder.toString();
        } catch (IOException e) {
            String msg = "【腾讯云】>>>【实时音视频】>>>读取密钥失败";
            log.error(msg, e);
            throw new RuntimeException(msg);
        }
        this.tlsSig = qcloudRtcProperties.getTlsSig();
        this.expire = qcloudRtcProperties.getExpire();
        this.bizId = qcloudRtcProperties.getBizId();
        this.playDomain = qcloudRtcProperties.getPlayDomain();
        this.playKey = qcloudRtcProperties.getPlayKey();
        this.playExpire = qcloudRtcProperties.getPlayExpire();
    }

    @Override
    public String getUserSig(String identifier) {
        try {
            TlsSigStrategy tlsSigStrategy = tlsSig.getStrategy().newInstance();
            UserSig userSig = new UserSig(tlsSigStrategy);
            return userSig.generate(sdkAppId, privateKey, expire, identifier);
        } catch (IllegalAccessException | InstantiationException e) {
            String msg = "【腾讯云】>>>【实施音视频】>>>签名生成异常";
            log.error(msg, e);
            throw new RuntimeException(msg);
        }
    }

    @Override
    public String getRTMPPlayUrl(String roomId, String identifier, String type) {
        checkPlayParams();
        // streamId
        String streamId = bizId + "_" + md5(roomId + "_" + identifier + "_" + type);
        // txTime
        String txTime = generateTxTime(playExpire);
        // txSecret
        String txSecret = generateTxSecret(playKey, streamId, txTime);
        return "rtmp://" + playDomain + "/live/"
                + streamId
                + "?txSecret=" + txSecret
                + "&txTime=" + txTime;
    }

    @Override
    public String getFLVPlayUrl(String roomId, String identifier, String type) {
        checkPlayParams();
        // streamId
        String streamId = bizId + "_" + md5(roomId + "_" + identifier + "_" + type);
        // txTime
        String txTime = generateTxTime(playExpire);
        // txSecret
        String txSecret = generateTxSecret(playKey, streamId, txTime);
        return "http://" + playDomain + "/live/"
                + streamId + ".flv"
                + "?txSecret=" + txSecret
                + "&txTime=" + txTime;
    }

    @Override
    public String getHLSPlayUrl(String roomId, String identifier, String type) {
        checkPlayParams();
        // streamId
        String streamId = bizId + "_" + md5(roomId + "_" + identifier + "_" + type);
        // txTime
        String txTime = generateTxTime(playExpire);
        // txSecret
        String txSecret = generateTxSecret(playKey, streamId, txTime);
        return "http://" + playDomain + "/live/"
                + streamId + ".m3u8"
                + "?txSecret=" + txSecret
                + "&txTime=" + txTime;
    }

    private void checkPlayParams() {
        if (bizId == null || playDomain == null || playKey == null) {
            String msg = "【腾讯云】>>>【实时音视频】>>>直播参数未配置";
            RuntimeException e = new RuntimeException(msg);
            log.error(msg, e);
            throw e;
        }
    }

    private String md5(String str) {
        try {
            StringBuilder result = new StringBuilder();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes());
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            String msg = "【腾讯云】>>>【实时音视频】>>>加密数据异常";
            log.error(msg, e);
            throw new RuntimeException(msg);
        }
    }

    private String generateTxTime(long expire) {
        long time = System.currentTimeMillis() / 1000 + expire;
        return Long.toHexString(time).toUpperCase();
    }

    private String generateTxSecret(String key, String streamName, String txTime) {
        try {
            String inputStr = key + streamName + txTime;
            byte[] inputBytes = inputStr.getBytes(StandardCharsets.UTF_8);
            byte[] outputBytes = MessageDigest.getInstance("MD5").digest(inputBytes);
            return byteArrayToHexString(outputBytes);
        } catch (NoSuchAlgorithmException e) {
            String msg = "【腾讯云】>>>【实时音视频】>>>加密数据异常";
            log.error(msg, e);
            throw new RuntimeException(msg);
        }
    }

    private String byteArrayToHexString(byte[] data) {
        char[] digitsLower = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] out = new char[data.length << 1];
        for (int i = 0, j = 0; i < data.length; i++) {
            out[j++] = digitsLower[(0xF0 & data[i]) >>> 4];
            out[j++] = digitsLower[0x0F & data[i]];
        }
        return new String(out);
    }
}
