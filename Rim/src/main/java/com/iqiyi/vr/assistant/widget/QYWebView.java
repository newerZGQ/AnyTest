package com.iqiyi.vr.assistant.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.iqiyi.vr.assistant.R;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by long on 2016/1/5.
 * Web控件
 */
public class QYWebView extends WebView {

    private static final String ALIPAY_URL = "alipays://";
    private static final String WEICHAT_URL = "weixin://";
    private static final String BROWSABLE = "android.intent.category.BROWSABLE";
    //Referer 或者是m.iqiyi.com
    public static final String REFERER = "http://pay.iqiyi.com";

    private ProgressBar progressBar;
    private OnWebViewListener webListener;
    private Context context;


    public QYWebView(Context context) {
        this(context, null);
    }

    public QYWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init(Context context) {
        this.context = context;
        // 顶部显示的进度条
        progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 7, 0, 0));
        Drawable drawable = context.getResources().getDrawable(R.drawable.layer_web_progress_bar);
        progressBar.setProgressDrawable(drawable);
        addView(progressBar);

        WebSettings webSettings = this.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);   // 是能放大缩小
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);//隐藏
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        this.addJavascriptInterface(new JsIntetation(), "android");
        this.setWebViewClient(webViewClientBase);
        this.setWebChromeClient(webChromeClientBase);
        setDownloadListener(new DownloadListener());
        this.onResume();
    }

    /**
     * 加载HTML数据
     *
     * @param htmlData
     */
    public void loadHtmlData(String htmlData) {
        String data = Html.fromHtml(htmlData).toString();
        loadDataWithBaseURL(null, data, "text/html", "UTF-8", null);
    }

    public void loadHtmlString(String htmlStr) {
        loadData(htmlStr, "text/html; charset=UTF-8", null);
    }

    private WebViewClientBase webViewClientBase = new WebViewClientBase();

    private class WebViewClientBase extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith(ALIPAY_URL)) {
                try {
                    Intent intent;
                    intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    intent.addCategory(BROWSABLE);
                    intent.setComponent(null);
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, context.getString(R.string.toast_alipay), Toast.LENGTH_SHORT).show();
                }
            } else if (url.startsWith(WEICHAT_URL)) {
                try {
                    Intent intent;
                    intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    intent.addCategory(BROWSABLE);
                    intent.setComponent(null);
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, context.getString(R.string.toast_wechat), Toast.LENGTH_SHORT).show();
                }
            } else {
                Map extraHeaders = new HashMap();
                extraHeaders.put("Referer", REFERER);
                view.loadUrl(url, extraHeaders);
            }
            return true;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (request.getUrl().toString().startsWith(ALIPAY_URL)) {
                try {
                    Intent intent;
                    intent = Intent.parseUri(request.getUrl().toString(), Intent.URI_INTENT_SCHEME);
                    intent.addCategory(BROWSABLE);
                    intent.setComponent(null);
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, context.getString(R.string.toast_alipay), Toast.LENGTH_SHORT).show();
                }
            } else if (request.getUrl().toString().startsWith(WEICHAT_URL)) {
                try {
                    Intent intent;
                    intent = Intent.parseUri(request.getUrl().toString(), Intent.URI_INTENT_SCHEME);
                    intent.addCategory(BROWSABLE);
                    intent.setComponent(null);
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, context.getString(R.string.toast_wechat), Toast.LENGTH_SHORT).show();
                }
            } else {
                Map extraHeaders = new HashMap();
                extraHeaders.put("Referer", REFERER);
                view.loadUrl(request.getUrl().toString(), extraHeaders);
            }
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            if (webListener != null) {
                webListener.onError();
            }
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);
        }
    }

    private WebChromeClientBase webChromeClientBase = new WebChromeClientBase();

    private class WebChromeClientBase extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (webListener != null) {
                webListener.onProgressChanged(newProgress);
            }
            if (newProgress == 100) {
                progressBar.setVisibility(GONE);
            } else {
                if (progressBar.getVisibility() == GONE)
                    progressBar.setVisibility(VISIBLE);
                progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
            super.onReceivedTouchIconUrl(view, url, precomposed);
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }
    }

    public class DownloadListener implements android.webkit.DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            getContext().startActivity(intent);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progressBar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressBar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void setWebListener(OnWebViewListener webListener) {
        this.webListener = webListener;
    }

    public static class OnWebViewListener {
        public void onProgressChanged(int progress) {
        }

        public void onError() {
        }

        public void onBack() {
        }
    }

    public class JsIntetation {
        @JavascriptInterface
        public void back() {
            webListener.onBack();
        }
    }
}
