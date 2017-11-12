package com.iqiyi.vr.assistant.api;

import android.support.annotation.NonNull;

import com.iqiyi.vr.assistant.QYApplication;
import com.iqiyi.vr.assistant.api.bean.BaseResponse;
import com.iqiyi.vr.assistant.api.bean.app.AppResponse;
import com.iqiyi.vr.assistant.api.bean.app.DetailResponse;
import com.iqiyi.vr.assistant.util.NetUtil;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wangyancong on 2017/8/23.
 * 整个网络通信服务的启动控制，必须先调用初始化函数才能正常使用网络通信接口
 */

public class RetrofitService {

    //设缓存有效期为1天
    static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存
    // 而不会请求服务器，max-stale可以配合设置缓存失效时间
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，
    // 浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)
    static final String CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=3600";
    // 避免出现 HTTP 403 Forbidden，参考：http://stackoverflow.com/questions
    // /13670692/403-forbidden-with-java-but-not-web-browser
    static final String AVOID_HTTP403_FORBIDDEN = "User-Agent: Mozilla/5.0 (Windows NT 6.1; " +
            "WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";

    private static final String APPSTORE_HOST = "http://store.iqiyi.com/";
    private static final String VRUSER_HOST = "http://10.16.83.180:8004/";

    private static IAppStoreApi appService;
    private static IVrUserApi vrUserService;

    private RetrofitService() {
        throw new AssertionError();
    }

    /**
     * 初始化网络通信服务
     */
    public static void init() {
        Cache cache = new Cache(new File(QYApplication.getContext().getCacheDir(), "HttpCache"),
                1024 * 1024 * 100);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(rewriteCacheControlInterceptor)
                .addNetworkInterceptor(rewriteCacheControlInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(APPSTORE_HOST)
                .build();
        appService = retrofit.create(IAppStoreApi.class);

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(VRUSER_HOST)
                .build();
        vrUserService = retrofit.create(IVrUserApi.class);
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private static final Interceptor rewriteCacheControlInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtil.isNetworkAvailable(QYApplication.getContext())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                Logger.e("no network");
            }
            Response originalResponse = chain.proceed(request);

            if (NetUtil.isNetworkAvailable(QYApplication.getContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, " + CACHE_CONTROL_CACHE)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    /**
     * 打印返回的json数据拦截器
     */
    private static final Interceptor loggingInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request request = chain.request();
            Buffer requestBuffer = new Buffer();
            if (request.body() != null) {
                request.body().writeTo(requestBuffer);
            } else {
                Logger.d("LogTAG", "request.body() == null");
            }
            //打印url信息
            Logger.w(request.url() + (request.body() != null ? "?"
                    + _parseParams(request.body(), requestBuffer) : ""));
            final Response response = chain.proceed(request);

            return response;
        }
    };

    @NonNull
    private static String _parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }

    /************************************ API *******************************************/

    public static Observable<AppResponse> getAppList() {
        return appService.getAppList()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<DetailResponse> getAppDetail(long appId, String appPkgName, int appVer, long uid) {
        return appService.getAppDetail(appId, appPkgName, appVer, uid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<BaseResponse<String>> getOrderNum(long appId, String authCookie) {
        return appService.getOrderNum(appId, authCookie)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<BaseResponse<String>> syncApp(String mchId, String timestamp, String sign, int orderId, String userId,
                                                           long qipuId, String deviceIds) {
        return appService.verifyPayState(orderId)
                .flatMap(_flatSyncApp(mchId, timestamp, sign, userId, qipuId, deviceIds))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    /******************************************* 转换器 **********************************************/
    private static Func1<BaseResponse, Observable<BaseResponse<String>>> _flatSyncApp(final String mchId, final String timestamp, final String sign,
                                                                                      final String userId, final long qipuId, final String deviceIds) {
        return new Func1<BaseResponse, Observable<BaseResponse<String>>>() {
            @Override
            public Observable<BaseResponse<String>> call(BaseResponse baseResponse) {
                Logger.d("verify pay code : " + baseResponse.getCode());
                if (baseResponse.getCode().equals("A00000")) {
                    return vrUserService.syncApp(mchId, timestamp, sign, userId, qipuId, deviceIds);
                }
                return null;
            }
        };
    }
}
