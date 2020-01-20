package iom.modernland.co.id;


import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
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
public class KordinasiDetailPBJFragment extends Fragment {

    DownloadManager dm ;

    public KordinasiDetailPBJFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View x = inflater.inflate(R.layout.fragment_kordinasi_detail_pbj, container, false);

        final String nopermintaan = getArguments().getString("no_permintaan");

        final TextView txtNoPermintaan = (TextView) x.findViewById(R.id.txtNoPermoAdKD);
        final TextView txtTanggal = (TextView) x.findViewById(R.id.txtTanggalAdKD);
        final TextView txtJenis = (TextView) x.findViewById(R.id.txtJenisPermoAdKD);
        final TextView txtKaryawan = (TextView) x.findViewById(R.id.txtKaryawanAdKD);
        final TextView txtJabatan = (TextView) x.findViewById(R.id.txtJabatanAdKD);
        final TextView txtDepartemen = (TextView) x.findViewById(R.id.txtDepartemenAdKD);
        final EditText tCatatan = (EditText) x.findViewById(R.id.tCatatanPBJKD);
        final TextView txtWVP = (TextView) x.findViewById(R.id.txtWVPKD);
        final TextView txtAFP = (TextView) x.findViewById(R.id.txtAFPKD);
        final LinearLayout lnAFP = (LinearLayout) x.findViewById(R.id.lnAFPKD);

        final Button btnApproveKD = (Button) x.findViewById(R.id.btnApprovePKD);
        final Button btnCancelKD = (Button) x.findViewById(R.id.btnCancelPKD);

        OkHttpClient postman = new OkHttpClient();

        Request r = new Request.Builder()
                .get()
                .url(Setting.IP + "get_permohonan.php?nopermintaan=" + nopermintaan)
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
                    JSONObject go = new JSONObject(hasil);

                    final String no_permintaan = go.getString("no_permintaan");
                    final String tgl_permintaan = go.getString("tgl_permintaan");
                    final String jenis = go.getString("jenis");
                    final String lampiran = go.getString("attch_file");
                    final String nama_user = go.getString("namaUser");
                    final String jabatan = go.getString("jabatan");
                    final String departemen = go.getString("departemen");

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (!lampiran.isEmpty()){

                                txtNoPermintaan.setText(no_permintaan);
                                txtTanggal.setText(tgl_permintaan);
                                txtJenis.setText(jenis);
                                txtKaryawan.setText(nama_user);
                                txtJabatan.setText(jabatan);
                                txtDepartemen.setText(departemen);
                                txtAFP.setText(lampiran);
                                txtWVP.setText(no_permintaan);

                            } else {

                                txtNoPermintaan.setText(no_permintaan);
                                txtTanggal.setText(tgl_permintaan);
                                txtJenis.setText(jenis);
                                txtKaryawan.setText(nama_user);
                                txtJabatan.setText(jabatan);
                                txtDepartemen.setText(departemen);
                                txtWVP.setText(no_permintaan);

                                lnAFP.setVisibility(View.INVISIBLE);

                            }

                            pd.dismiss();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Button btnWvP = (Button) x.findViewById(R.id.btnWVPKD);
        btnWvP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nopermintaan = txtWVP.getText().toString();

                WebViewPBJFragment wv = new WebViewPBJFragment();

                Bundle b = new Bundle();
                b.putString("no_permintaan", nopermintaan);

                wv.setArguments(b);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameKordinasiPBJ, wv)
                        .addToBackStack(null)
                        .commit();
            }
        });

        Button btnAFPKD = (Button) x.findViewById(R.id.btnAFPKD);
        btnAFPKD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String attch_lampiran = txtAFP.getText().toString();

                dm = (DownloadManager)getContext().getSystemService(Context.DOWNLOAD_SERVICE);

                Uri uri = Uri.parse("https://approval.modernland.co.id/assets/file/"
                        + attch_lampiran);

                DownloadManager.Request request = new DownloadManager.Request(uri);

                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                Long reference = dm.enqueue(request);
            }
        });

        btnApproveKD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());

                ab.create();
                ab.setTitle("Confirmation");
                ab.setIcon(R.drawable.ic_check_black_24dp);
                ab.setMessage("Apakah Anda Akan Menyetujui?");
                ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final String isiKomen = tCatatan.getText().toString();

                        OkHttpClient postman = new OkHttpClient();

                        SharedPreferences sp = getActivity()
                                .getSharedPreferences("DATALOGIN", 0);

                        String id_user      = sp.getString("id_user", "");

                        RequestBody body = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("komen", isiKomen)
                                .addFormDataPart("nomor", nopermintaan)
                                .addFormDataPart("id_user", id_user)
                                .build();

                        Request request = new Request.Builder()
                                .post(body)
                                .url(Setting.IP + "proses_approve_kordinasi_pbj.php")
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
                ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                ab.show();
            }
        });

        btnCancelKD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder abc = new AlertDialog.Builder(getActivity());

                abc.create();
                abc.setTitle("Confirmation");
                abc.setIcon(R.drawable.ic_check_black_24dp);
                abc.setMessage("Apakah Anda Yakin Tidak Menyetujui Pengajuan Ini?");
                abc.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final String isiKomen = tCatatan.getText().toString();

                        OkHttpClient postman = new OkHttpClient();

                        SharedPreferences sp = getActivity()
                                .getSharedPreferences("DATALOGIN", 0);

                        String id_user      = sp.getString("id_user", "");

                        RequestBody body = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("komen", isiKomen)
                                .addFormDataPart("nomor", nopermintaan)
                                .addFormDataPart("id_user", id_user)
                                .build();

                        Request request = new Request.Builder()
                                .post(body)
                                .url(Setting.IP + "proses_cancel_kordinasi_pbj.php")
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
                abc.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                abc.show();
            }
        });

        return x;
    }

}
