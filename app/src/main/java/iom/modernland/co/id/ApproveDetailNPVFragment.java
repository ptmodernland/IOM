package iom.modernland.co.id;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ApproveDetailNPVFragment extends Fragment {


    public ApproveDetailNPVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View x = inflater.inflate(R.layout.fragment_approve_detail_npv, container, false);

        final String npv_no = getArguments().getString("npv_no");
        final String kd_unit = getArguments().getString("kode_unitnya");
        final String cluster = getArguments().getString("cluster");
        final String nama = getArguments().getString("nama");

        final TextView txtKdUnit = (TextView) x.findViewById(R.id.txtKdUnitANV);
        final TextView txtNo = (TextView) x.findViewById(R.id.txtNoANV);
        final TextView txtCluster = (TextView) x.findViewById(R.id.txtClusterANV);
        final TextView txtNama = (TextView) x.findViewById(R.id.txtNamaANV);
        final TextView txtBuild = (TextView) x.findViewById(R.id.txtBuildANV);
        final TextView txtLand = (TextView) x.findViewById(R.id.txtLandANV);
        final TextView txtPrice = (TextView) x.findViewById(R.id.txtPriceANV);
        final TextView txtRate = (TextView) x.findViewById(R.id.txtRateANV);
        final TextView txtDiskon = (TextView) x.findViewById(R.id.txtDiskonANV);
        final TextView txtUpHarga = (TextView) x.findViewById(R.id.txtUpHargaANV);
        final TextView txtTglBuat = (TextView) x.findViewById(R.id.txtTglBuatANV);

        final DecimalFormat decim = new DecimalFormat("#,###.##");

        OkHttpClient postman = new OkHttpClient();

        Request r = new Request.Builder()
                .get()
                .url(Setting.IP + "get_npv.php?npv_no=" + npv_no)
                .build();

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Please wait ...");
        pd.setTitle("Loading ...");
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
                    JSONObject joav = new JSONObject(hasil);

                    final String build_size = joav.getString("Build_size");
                    final String land_size = joav.getString("Land_size");
                    final String interest_rate = joav.getString("Interest_rate");
                    final Integer diskon = joav.getInt("Diskon");
                    final Integer upharga = joav.getInt("Up_harga");
                    final Integer price = joav.getInt("Price");
                    final String tgl_buat = joav.getString("Tgl_buat");

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

        Button btnApprove = (Button) x.findViewById(R.id.btnApproveNV);
        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText tCatatanNV = (EditText) x.findViewById(R.id.tCatatanNPV);

                final String isiKomenNV = tCatatanNV.getText().toString();

                AlertDialog.Builder abp = new AlertDialog.Builder(getActivity());

                abp.create();
                abp.setTitle("Confirmation");
                abp.setIcon(R.drawable.ic_check_black_24dp);
                abp.setMessage("Apakah Anda Akan Menyetujui?");
                abp.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        OkHttpClient postman = new OkHttpClient();

                        SharedPreferences sp = getActivity()
                                .getSharedPreferences("DATALOGIN", 0);

                        String id_user      = sp.getString("id_user", "");

                        RequestBody body = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("komennv", isiKomenNV)
                                .addFormDataPart("npv_no", npv_no)
                                .addFormDataPart("id_user", id_user)
                                .build();

                        Request request = new Request.Builder()
                                .post(body)
                                .url(Setting.IP + "proses_approve_npv.php")
                                .build();

                        final ProgressDialog pd = new ProgressDialog(
                                getActivity()
                        );
                        pd.setMessage("Please Wait");
                        pd.setTitle("Loading Approve...");
                        pd.setIcon(R.drawable.ic_check_black_24dp);
                        pd.show();

                        postman.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(),
                                                "Please Try Again",
                                                Toast.LENGTH_LONG).show();
                                        pd.dismiss();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String hasil = response.body().string();
                                try {
                                    JSONObject j = new JSONObject(hasil);
                                    boolean st = j.getBoolean("status");

                                    if(st == false)
                                    {
                                        final String p = j.getString("pesan");
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getActivity(),
                                                        p, Toast.LENGTH_LONG).show();
                                                pd.dismiss();
                                            }
                                        });
                                    }
                                    else {

                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getActivity().getApplicationContext(),
                                                        "Pengajuan Sudah disetujui!",
                                                        Toast.LENGTH_LONG).show();

                                                getActivity().getSupportFragmentManager()
                                                        .popBackStackImmediate();

                                                pd.dismiss();
                                            }
                                        });
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });

                abp.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                abp.show();
            }
        });

        Button btnCancel = (Button) x.findViewById(R.id.btnCancelNV);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText tCatatanCNV = (EditText) x.findViewById(R.id.tCatatanNPV);

                final String isiKomenCNV = tCatatanCNV.getText().toString();

                AlertDialog.Builder abpc = new AlertDialog.Builder(getActivity());

                abpc.create();
                abpc.setTitle("Confirmation");
                abpc.setIcon(R.drawable.ic_check_black_24dp);
                abpc.setMessage("Apakah Anda Yakin Tidak Menyetujui Pengajuan Ini?");
                abpc.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        OkHttpClient postman = new OkHttpClient();

                        SharedPreferences sp = getActivity()
                                .getSharedPreferences("DATALOGIN", 0);

                        String id_user      = sp.getString("id_user", "");

                        RequestBody body = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("komennv", isiKomenCNV)
                                .addFormDataPart("npv_no", npv_no)
                                .addFormDataPart("id_user", id_user)
                                .build();

                        Request request = new Request.Builder()
                                .post(body)
                                .url(Setting.IP + "proses_cancel_npv.php")
                                .build();

                        final ProgressDialog pd = new ProgressDialog(
                                getActivity()
                        );
                        pd.setMessage("Please Wait");
                        pd.setTitle("Loading Rejected...");
                        pd.setIcon(R.drawable.ic_check_black_24dp);
                        pd.show();

                        postman.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(),
                                                "Please Try Again",
                                                Toast.LENGTH_LONG).show();
                                        pd.dismiss();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String hasil = response.body().string();
                                try {
                                    JSONObject j = new JSONObject(hasil);
                                    boolean st = j.getBoolean("status");

                                    if(st == false)
                                    {
                                        final String p = j.getString("pesan");
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getActivity(),
                                                        p, Toast.LENGTH_LONG).show();
                                                pd.dismiss();
                                            }
                                        });
                                    }
                                    else {

                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getActivity().getApplicationContext(),
                                                        "Terima Kasih!",
                                                        Toast.LENGTH_LONG).show();

                                                getActivity().getSupportFragmentManager()
                                                        .popBackStackImmediate();

                                                pd.dismiss();
                                            }
                                        });
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });

                abpc.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                abpc.show();
            }
        });

        Button btnWVANV = (Button) x.findViewById(R.id.btnWVANV);
        btnWVANV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String npv_no = txtNo.getText().toString();

                WebViewNPVFragment wvnv = new WebViewNPVFragment();

                Bundle b = new Bundle();
                b.putString("npv_no",npv_no);

                wvnv.setArguments(b);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameApproveNPV, wvnv)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return x;
    }

}
