package com.iqiyi.vr.assistant.api;

import com.iqiyi.vr.assistant.api.bean.BaseResponse;
import com.iqiyi.vr.assistant.api.bean.device.DeviceModel;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

import static com.iqiyi.vr.assistant.api.RetrofitService.CACHE_CONTROL_NETWORK;

/**
 * Created by wangyancong on 2017/9/7.
 * vr user API 接口
 */

public interface IVrUserApi {

    /**
     * push登录信息 deviceType  1-VR手机助手 2-VR一体机
     * @param userId
     * @param deviceId
     * @param deviceName
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("/device/login?deviceType=1")
    Observable<BaseResponse<String>> pushLogin(@Query("mchId") String mchId,
                                               @Query("timestamp") String timestamp,
                                               @Query("sign") String sign,
                                               @Query("userId") long userId,
                                               @Query("deviceId") String deviceId,
                                               @Query("deviceName") String deviceName);

    /**
     * push退登信息 deviceType  1-VR手机助手 2-VR一体机
     * @param userId
     * @param deviceId
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("/device/logout?deviceType=1")
    Observable<BaseResponse<String>> pushLogout(@Query("mchId") String mchId,
                                                @Query("timestamp") String timestamp,
                                                @Query("sign") String sign,
                                                @Query("userId") long userId,
                                                @Query("deviceId") String deviceId);

    /**
     * 获取已登录设备列表 deviceType  1-VR手机助手 2-VR一体机
     * @param userId
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("/device/login/all?deviceType=1")
    Observable<BaseResponse<List<DeviceModel>>> getDeviceList(@Query("mchId") String mchId,
                                                              @Query("timestamp") String timestamp,
                                                              @Query("sign") String sign,
                                                              @Query("userId") String userId);

    /**
     * 同步vip信息
     * @param userId
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("/user/vip/mark/sync?")
    Observable<BaseResponse<String>> syncVip(@Query("mchId") String mchId,
                                             @Query("timestamp") String timestamp,
                                             @Query("sign") String sign,
                                             @Query("userId") String userId);

    /**
     * 应用购买同步
     * @param userId
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("/app/buy/sync?")
    Observable<BaseResponse<String>> syncApp(@Query("mchId") String mchId,
                                             @Query("timestamp") String timestamp,
                                             @Query("sign") String sign,
                                             @Query("userId") String userId,
                                             @Query("qipuId") long qipuId,
                                             @Query("deviceIds") String deviceIds);
}
