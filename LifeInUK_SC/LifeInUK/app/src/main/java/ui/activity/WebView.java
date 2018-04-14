package ui.activity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.tecjaunt.www.lifeinuk.R;

public class WebView extends AppCompatActivity {

    private android.webkit.WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = (android.webkit.WebView)findViewById(R.id.wv_webview);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                webView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                webView.setVisibility(View.GONE);
            }
            @Override
            public void onReceivedHttpAuthRequest(android.webkit.WebView view, HttpAuthHandler handler, String host, String realm) {
                super.onReceivedHttpAuthRequest(view, handler, host, realm);
            }

            @Override
            public void onReceivedSslError(android.webkit.WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                if (error.getPrimaryError() == SslError.SSL_UNTRUSTED) {
                    handler.proceed();
                } else {
                    handler.proceed();
                }
            }
        });

        webView.loadUrl("https://www.lituktestbooking.co.uk/lituk-web/");
    }
}
