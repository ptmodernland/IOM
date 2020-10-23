package iom.modernland.co.id;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewPBJFragment extends Fragment {


    public WebViewPBJFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_web_view_pbj, container, false);

        String no_permintaan = getArguments().getString("no_permintaan");

        WebView webviewpbj = (WebView) x.findViewById(R.id.WebViewPBJ);

        webviewpbj.getSettings().setLoadsImagesAutomatically(true);
        webviewpbj.getSettings().setJavaScriptEnabled(true);
        webviewpbj.getSettings().setDomStorageEnabled(true);

        webviewpbj.getSettings().setSupportZoom(true);
        webviewpbj.getSettings().setBuiltInZoomControls(true);
        webviewpbj.getSettings().setDisplayZoomControls(true);

        webviewpbj.getSettings().setJavaScriptEnabled(true);
        webviewpbj.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webviewpbj.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        webviewpbj.setWebViewClient(new WebViewClient());
        webviewpbj.loadUrl("https://approval.modernland.co.id/permohonan_barang_jasa/views_permohonan/" + no_permintaan);

        return x;
    }

    private class MyBrowser extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return true;
        }
    }

}
