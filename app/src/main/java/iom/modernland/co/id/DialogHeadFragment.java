package iom.modernland.co.id;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogHeadFragment extends Fragment {


    public DialogHeadFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View x = inflater.inflate(R.layout.list_head, container, false);

        final RecyclerView rvHead = (RecyclerView) x.findViewById(R.id.rvHead);
        final String nomornya = getArguments().getString("NomorMemo");


        LinearLayoutManager lp = new LinearLayoutManager(getActivity());
        lp.setOrientation(LinearLayoutManager.VERTICAL);
        rvHead.setLayoutManager(lp);

        OkHttpClient postman = new OkHttpClient();

        Request request = new Request.Builder()
                .get()
                .url(Setting.IP + "get_head.php")
                .build();

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Please wait");
        pd.setTitle("Loading ...");
        pd.setIcon(R.drawable.ic_check_black_24dp);
        pd.setCancelable(false);
        pd.show();

        postman.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),
                                "Please Try Again",
                                Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String HasilHead = response.body().string();
                try {
                    JSONArray ji = new JSONArray(HasilHead);

                    final HeadAdapter adapter = new HeadAdapter();
                    adapter.data = new ArrayList<ListHead>();
                    for (int i = 0;i < ji.length();i++)
                    {
                        JSONObject jl = ji.getJSONObject(i);
                        ListHead lh = new ListHead();

                        lh.namaUser = jl.getString("namaUser");
                        lh.nomornya = nomornya;

                        adapter.data.add(lh);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            rvHead.setAdapter(adapter);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return x;
    }

}
