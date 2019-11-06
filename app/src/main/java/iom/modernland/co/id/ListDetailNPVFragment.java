package iom.modernland.co.id;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListDetailNPVFragment extends Fragment {


    public ListDetailNPVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_list_detail_npv, container, false);

        final String npv_no = getArguments().getString("npv_no_list");
        final String kd_unit = getArguments().getString("kode_unit_list");
        final String cluster = getArguments().getString("cluster_list");
        final String nama = getArguments().getString("nama_list");

        final TextView txtKdUnit = (TextView) x.findViewById(R.id.txtKdUnitLNV);
        final TextView txtNo = (TextView) x.findViewById(R.id.txtNoLNV);
        final TextView txtCluster = (TextView) x.findViewById(R.id.txtClusterLNV);
        final TextView txtNama = (TextView) x.findViewById(R.id.txtNamaLNV);
        final TextView txtBuild = (TextView) x.findViewById(R.id.txtBuildLNV);
        final TextView txtLand = (TextView) x.findViewById(R.id.txtLandLNV);
        final TextView txtPrice = (TextView) x.findViewById(R.id.txtPriceLNV);
        final TextView txtDiskon = (TextView) x.findViewById(R.id.txtDiskonLNV);
        final TextView txtUpHarga = (TextView) x.findViewById(R.id.txtUpHargaLNV);
        final TextView txtRate = (TextView) x.findViewById(R.id.txtRateLNV);
        final TextView txtTglBuat = (TextView) x.findViewById(R.id.txtTglBuatLNV);

        final DecimalFormat decim = new DecimalFormat("#,###.##");

        OkHttpClient postman = new OkHttpClient();

        Request r = new Request.Builder()
                .get()
                .url(Setting.IP + "get_npv.php?npv_no=" + npv_no)
                .build();

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Please wait ...");
        pd.setTitle("Loading ..." + npv_no);
        pd.setIcon(R.drawable.ic_check_black_24dp);
        pd.setCancelable(true);
        pd.show();

        postman.newCall(r).enqueue(new Callback() {
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
                    JSONObject jolv = new JSONObject(hasil);

                    final String build_size = jolv.getString("Build_size");
                    final String land_size = jolv.getString("Land_size");
                    final String interest_rate = jolv.getString("Interest_rate");
                    final Integer diskon = jolv.getInt("Diskon");
                    final Integer upharga = jolv.getInt("Up_harga");
                    final Integer price = jolv.getInt("Price");
                    final String tgl_buat = jolv.getString("Tgl_buat");

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            txtKdUnit.setText(kd_unit);
                            txtNo.setText(npv_no);
                            txtNama.setText(nama);
                            txtCluster.setText(cluster);
                            txtBuild.setText(build_size + " m2");
                            txtLand.setText(land_size + " m2");
                            txtRate.setText(interest_rate + " %");
                            txtDiskon.setText(diskon + " %");
                            txtPrice.setText("Rp " + decim.format(price));
                            txtUpHarga.setText("Rp " + decim.format(upharga));
                            txtTglBuat.setText(tgl_buat);

                            pd.dismiss();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Button btnWVLNV = (Button) x.findViewById(R.id.btnWVLNV);
        btnWVLNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String npv_no = txtNo.getText().toString();

                WebViewNPVFragment wvnv = new WebViewNPVFragment();

                Bundle b = new Bundle();
                b.putString("npv_no",npv_no);

                wvnv.setArguments(b);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameNPV, wvnv)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return x;
    }

}
