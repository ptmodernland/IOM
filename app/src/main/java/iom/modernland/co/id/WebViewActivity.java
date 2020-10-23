package iom.modernland.co.id;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    WebView webviewku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView webviewku = (WebView)findViewById(R.id.WebView1);
        webviewku.getSettings().setLoadsImagesAutomatically(true);
        webviewku.getSettings().setJavaScriptEnabled(true);
        webviewku.getSettings().setDomStorageEnabled(true);

        webviewku.getSettings().setSupportZoom(false);
        webviewku.getSettings().setBuiltInZoomControls(true);
        webviewku.getSettings().setDisplayZoomControls(false);

        webviewku.getSettings().setJavaScriptEnabled(true);
        webviewku.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webviewku.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webviewku.setWebViewClient(new WebViewClient());
        webviewku.loadUrl("https://reminder.modernland.co.id/iom/memo/view_mobile/");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webviewku.canGoBack()){
            webviewku.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class MyBrowser extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return true;
        }
    }
}
