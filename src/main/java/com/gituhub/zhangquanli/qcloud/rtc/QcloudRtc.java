package com.gituhub.zhangquanli.qcloud.rtc;

/**
 * QcloudRtc
 *
 * @author zhangquanli
 */
public interface QcloudRtc {

    /**
     * 生成密码
     *
     * @param identifier 账号
     * @return UserSig
     */
    String getUserSig(String identifier);

    /**
     * 获取 rtmp 协议播放地址
     *
     * @param roomId     房间编号
     * @param identifier 账号
     * @param type       类型，摄像头画面的流类型是 main，屏幕分享的流类型是 aux（有个例外，由于 WebRTC 端同时只支持一路上行，所以 WebRTC 上屏幕分享的流类型也是 main）
     * @return String
     */
    String getRTMPPlayUrl(String roomId, String identifier, String type);

    /**
     * 获取 flv 协议播放地址
     *
     * @param roomId     房间编号
     * @param identifier 账号
     * @param type       类型，摄像头画面的流类型是 main，屏幕分享的流类型是 aux（有个例外，由于 WebRTC 端同时只支持一路上行，所以 WebRTC 上屏幕分享的流类型也是 main）
     * @return String
     */
    String getFLVPlayUrl(String roomId, String identifier, String type);

    /**
     * 获取 hls 协议播放地址
     *
     * @param roomId     房间编号
     * @param identifier 账号
     * @param type       类型，摄像头画面的流类型是 main，屏幕分享的流类型是 aux（有个例外，由于 WebRTC 端同时只支持一路上行，所以 WebRTC 上屏幕分享的流类型也是 main）
     * @return String
     */
    String getHLSPlayUrl(String roomId, String identifier, String type);
}
