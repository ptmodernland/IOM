package iom.modernland.co.id;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewPOFragment extends Fragment {

    WebView webviewku;

    public WebViewPOFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_web_view, container, false);

        String id_iom = getArguments().getString("idiomnya");

        WebView webviewku = (WebView) x.findViewById(R.id.WebView1);

        webviewku.getSettings().setLoadsImagesAutomatically(true);
        webviewku.getSettings().setJavaScriptEnabled(true);
        webviewku.getSettings().setDomStorageEnabled(true);

        webviewku.getSettings().setSupportZoom(true);
        webviewku.getSettings().setBuiltInZoomControls(true);
        webviewku.getSettings().setDisplayZoomControls(true);

        webviewku.getSettings().setJavaScriptEnabled(true);
        webviewku.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webviewku.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        webviewku.setWebViewClient(new WebViewClient());
        webviewku.loadUrl("https://approval.modernland.co.id/po/views_mobile/" + id_iom);

        return x;
    }

    private class MyBrowser extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return true;
        }
    }
}
