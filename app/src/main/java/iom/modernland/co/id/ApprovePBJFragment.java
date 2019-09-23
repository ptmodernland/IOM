package iom.modernland.co.id;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class ApprovePBJFragment extends Fragment {


    public ApprovePBJFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_approve_pbj, container, false);

        final RecyclerView rvApprovePBJ = (RecyclerView) x.findViewById(R.id.rvApprovePBJ);

        SharedPreferences sp = getActivity()
                .getSharedPreferences("DATALOGIN", 0);

        String username      = sp.getString("username", "");


        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rvApprovePBJ.setLayoutManager(lm);

        OkHttpClient postman = new OkHttpClient();

        Request request = new Request.Builder()
                .get()
                .url(Setting.IP + "list_approve_pbj.php?username=" + username)
                .build();

        final ProgressDialog pdo = new ProgressDialog(getActivity());
        pdo.setMessage("Please wait");
        pdo.setTitle("Loading ...");
        pdo.setIcon(R.drawable.ic_check_black_24dp);
        pdo.setCancelable(false);
        pdo.show();

        postman.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),
                                "Please Try Again",
                                Toast.LENGTH_SHORT).show();
                        pdo.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String hasil = response.body().string();
                try {
                    JSONArray k = new JSONArray(hasil);

                    final ApprovePBJAdapter adapter = new ApprovePBJAdapter();
                    adapter.data = new ArrayList<ListPermohonan>();
                    for (int o = 0;o < k.length();o++)
                    {
                        JSONObject ko = k.getJSONObject(o);
                        ListPermohonan p = new ListPermohonan();

                        p.nomor = ko.getString("no_permintaan");
                        p.jenis = ko.getString("jenis");

                        adapter.data.add(p);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdo.dismiss();
                            rvApprovePBJ.setAdapter(adapter);
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
