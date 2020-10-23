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
public class WebViewNPVFragment extends Fragment {


    public WebViewNPVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_web_view_npv, container, false);

        String npv_no = getArguments().getString("npv_no");

        WebView webviewnpv = (WebView) x.findViewById(R.id.WebViewNPV);

        webviewnpv.getSettings().setLoadsImagesAutomatically(true);
        webviewnpv.getSettings().setJavaScriptEnabled(true);
        webviewnpv.getSettings().setDomStorageEnabled(true);

        webviewnpv.getSettings().setSupportZoom(true);
        webviewnpv.getSettings().setBuiltInZoomControls(true);
        webviewnpv.getSettings().setDisplayZoomControls(true);

        webviewnpv.getSettings().setJavaScriptEnabled(true);
        webviewnpv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webviewnpv.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        webviewnpv.setWebViewClient(new WebViewClient());
        webviewnpv.loadUrl("https://approval.modernland.co.id/npv/view_mobile/" + npv_no);

        return x;
    }

    private class MyBrowser extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return true;
        }
    }

}
