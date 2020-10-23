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
public class ListNPVFragment extends Fragment {


    public ListNPVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_list_npv, container, false);

        final RecyclerView rvListNPV = (RecyclerView) x.findViewById(R.id.rvListNPV);

        SharedPreferences sp = getActivity()
                .getSharedPreferences("DATALOGIN", 0);

        String username      = sp.getString("username", "");


        LinearLayoutManager lml = new LinearLayoutManager(getActivity());
        lml.setOrientation(LinearLayoutManager.VERTICAL);
        rvListNPV.setLayoutManager(lml);

        OkHttpClient postman = new OkHttpClient();

        Request request = new Request.Builder()
                .get()
                .url(Setting.IP + "list_npv.php?username=" + username)
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
                    JSONArray p = new JSONArray(hasil);

                    final ListNPVAdapter adapter = new ListNPVAdapter();
                    adapter.data = new ArrayList<ListNPV>();
                    for (int r = 0;r < p.length();r++)
                    {
                        JSONObject jon = p.getJSONObject(r);
                        ListNPV lnp = new ListNPV();

                        lnp.Lot_no = jon.getString("Lot_no");
                        lnp.Npv_no = jon.getString("Npv_no");
                        lnp.Cluster = jon.getString("Cluster");
                        lnp.Nama = jon.getString("Nama");
                        lnp.Status = jon.getString("Status");

                        adapter.data.add(lnp);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdo.dismiss();
                            rvListNPV.setAdapter(adapter);
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
