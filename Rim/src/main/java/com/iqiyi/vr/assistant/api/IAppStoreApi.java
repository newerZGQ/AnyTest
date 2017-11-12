package com.iqiyi.vr.assistant.api;

import com.iqiyi.vr.assistant.api.bean.BaseResponse;
import com.iqiyi.vr.assistant.api.bean.app.AppResponse;
import com.iqiyi.vr.assistant.api.bean.app.DetailResponse;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

import static com.iqiyi.vr.assistant.api.RetrofitService.CACHE_CONTROL_NETWORK;


/**
 * Created by wangyancong on 2017/8/23.
 * app store API 接口
 */

public interface IAppStoreApi {

    /**
     * 获取应用商店列表
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @POST("apis/vr/app/app_album.action?platform=61&album_id=2889")
    Observable<AppResponse> getAppList();

    /**
     * 获取应用详情
     * @param appId
     * @param appPkgName
     * @param appVer
     * @param uid
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @POST("apis/app/info.action?agent_type=61&fields=basic,res")
    Observable<DetailResponse> getAppDetail(@Query("app_id") long appId,
                                            @Query("app_package_name") String appPkgName,
                                            @Query("app_ver") int appVer,
                                            @Query("uid") long uid);

    /**
     * 获取订单号
     * @param appId
     * @param authCookie
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("apis/order/submit.action?platform=MBAQ")
    Observable<BaseResponse<String>> getOrderNum(@Query("appId") long appId,
                                                 @Query("authCookie") String authCookie);

    /**
     * 验证订单是否支付
     * @param orderId
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("apis/order/payVerify.action?agent_type=193")
    Observable<BaseResponse> verifyPayState(@Query("orderId") int orderId);
}
