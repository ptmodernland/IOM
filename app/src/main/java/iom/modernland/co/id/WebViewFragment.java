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
public class WebViewFragment extends Fragment {

    WebView webviewku;

    public WebViewFragment() {
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

        webviewku.getSettings().setSupportZoom(false);
        webviewku.getSettings().setBuiltInZoomControls(true);
        webviewku.getSettings().setDisplayZoomControls(false);

        webviewku.getSettings().setJavaScriptEnabled(true);
        webviewku.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webviewku.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webviewku.setWebViewClient(new WebViewClient());
        webviewku.loadUrl("https://reminder.modernland.co.id/iom/memo/view_mobile/" + id_iom);

        //FloatingActionButton btnBackAp = (FloatingActionButton) x.findViewById(R.id.btnBackApp);
        //btnBackAp.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        getActivity().getSupportFragmentManager()
        //                .popBackStackImmediate();
        //    }
        //});

        return x;
    }

    private class MyBrowser extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return true;
        }
    }



}
