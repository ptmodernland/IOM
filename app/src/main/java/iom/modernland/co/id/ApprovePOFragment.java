package iom.modernland.co.id;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class ApprovePOFragment extends Fragment {


    public ApprovePOFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_approve_po, container, false);

        final RecyclerView rvApprove = (RecyclerView) x.findViewById(R.id.rvApprove);

        SharedPreferences sp = getActivity()
                .getSharedPreferences("DATALOGIN", 0);

        String username      = sp.getString("username", "");


        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rvApprove.setLayoutManager(lm);

        OkHttpClient postman = new OkHttpClient();

        Request request = new Request.Builder()
                .get()
                .url(Setting.IP + "list_approve_po.php?username=" + username)
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
                String hasil = response.body().string();
                try {
                    JSONArray j = new JSONArray(hasil);

                    final ApprovePOAdapter adapter = new ApprovePOAdapter();

                    adapter.data = new ArrayList<ListPO>();
                    for (int i = 0;i < j.length();i++)
                    {
                        JSONObject jo = j.getJSONObject(i);
                        ListPO l= new ListPO();

                        l.id_po = jo.getString("id_po");
                        l.no_po = jo.getString("no_po");
                        l.desc_po = jo.getString("desc_po");
                        l.status = jo.getString("status");
                        l.status_email = jo.getString("status_email");

                        adapter.data.add(l);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            rvApprove.setAdapter(adapter);
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