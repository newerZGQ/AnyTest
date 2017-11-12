package com.iqiyi.vr.assistant.api;

import com.iqiyi.vr.assistant.api.bean.BaseResponse;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

import static com.iqiyi.vr.assistant.api.RetrofitService.CACHE_CONTROL_NETWORK;

/**
 * Created by wangyancong on 2017/9/7.
 */

public interface IFeedbackApi {

    /**
     * 获取ticket
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("f/b/p.html")
    Observable<String> getTicket();

    /**
     * 提交问题反馈
     * @param ticket
     * @param content
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @POST("f/b/s.html?entry_class=vrassistantandroid&fb_class=问题反馈&qypid=02023521010000000000")
    Observable<BaseResponse<String>> commitFeedback(@Query("ticket") String ticket,
                                                    @Query("content") String content);
}
