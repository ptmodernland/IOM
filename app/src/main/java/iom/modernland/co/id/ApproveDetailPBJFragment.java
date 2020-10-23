package iom.modernland.co.id;


import android.app.Dialog;
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
import android.widget.Spinner;
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
public class ApproveDetailPBJFragment extends Fragment {

    DownloadManager dm ;

    public ApproveDetailPBJFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View x = inflater.inflate(R.layout.fragment_approve_detail_pbj, container, false);

        final String nopermintaan = getArguments().getString("no_permintaan");

        final TextView txtNoPermintaan = (TextView) x.findViewById(R.id.txtNoPermoAd);
        final TextView txtTanggal = (TextView) x.findViewById(R.id.txtTanggalAd);
        final TextView txtLama = (TextView) x.findViewById(R.id.txtLamaPengaju);
        final TextView txtPending = (TextView) x.findViewById(R.id.txtLamaPeding);
        final TextView txtJenis = (TextView) x.findViewById(R.id.txtJenisPermoAd);
        final TextView txtKaryawan = (TextView) x.findViewById(R.id.txtKaryawanAd);
        final TextView txtJabatan = (TextView) x.findViewById(R.id.txtJabatanAd);
        final TextView txtDepartemen = (TextView) x.findViewById(R.id.txtDepartemenAd);
        final TextView txtWVP = (TextView) x.findViewById(R.id.txtWVP);
        final TextView txtAFP = (TextView) x.findViewById(R.id.txtAFP);
        final LinearLayout lnAFP = (LinearLayout) x.findViewById(R.id.lnAFP);
        final Button btnAFP = (Button) x.findViewById(R.id.btnAFP);
        final Button btnKordinasi = (Button) x.findViewById(R.id.btnKordinasiP);
        final Button btnApprove = (Button) x.findViewById(R.id.btnApproveP);

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
                    final String lama = go.getString("lama");
                    final String pending = go.getString("pending");
                    final String status = go.getString("status");

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
                                txtLama.setText(lama + " hari");
                                txtPending.setText(pending + " hari");

                            } else {

                                txtNoPermintaan.setText(no_permintaan);
                                txtTanggal.setText(tgl_permintaan);
                                txtJenis.setText(jenis);
                                txtKaryawan.setText(nama_user);
                                txtJabatan.setText(jabatan);
                                txtDepartemen.setText(departemen);
                                txtAFP.setText(lampiran);
                                txtWVP.setText(no_permintaan);
                                txtLama.setText("\n" + lama + " hari");
                                txtPending.setText(pending + " hari");

                                lnAFP.setVisibility(View.INVISIBLE);

                            }

                            if ("K".equals(status)){

                                btnApprove.setVisibility(View.INVISIBLE);
                                btnKordinasi.setVisibility(View.INVISIBLE);

                            }
                            else if ("C".equals(status)){

                                btnApprove.setVisibility(View.INVISIBLE);
                                btnKordinasi.setVisibility(View.INVISIBLE);

                            }
                            else if ("T".equals(status)){


                            }

                            pd.dismiss();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText tCatatanAd = (EditText) x.findViewById(R.id.tCatatanPBJ);

                final String isiKomenAd = tCatatanAd.getText().toString();

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
                                .addFormDataPart("komenad", isiKomenAd)
                                .addFormDataPart("no_permintaan", nopermintaan)
                                .addFormDataPart("id_user", id_user)
                                .build();

                        Request request = new Request.Builder()
                                .post(body)
                                .url(Setting.IP + "proses_approve_pbj.php")
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
                        Toast.makeText(getActivity(),
                                "Pengajuan belum disetujui!", Toast.LENGTH_SHORT).show();
                    }
                });

                abp.show();
            }
        });

        btnKordinasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                final View mView = getLayoutInflater().inflate(R.layout.dialog_pilih_head, null);

                mBuilder.setView(mView);

                AlertDialog dialog = mBuilder.create();
                dialog.setButton(Dialog.BUTTON_POSITIVE, "Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //final Spinner spnPilihHead = (Spinner) mView.findViewById(R.id.spnHead);
                        //final String headPilihan = spnPilihHead.getSelectedItem().toString();

                        OkHttpClient postman = new OkHttpClient();

                        SharedPreferences sp = getActivity()
                                .getSharedPreferences("DATALOGIN", 0);

                        String username_apr      = sp.getString("username", "");

                        RequestBody body = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                //.addFormDataPart("nomor", nopermintaan)
                                //.addFormDataPart("head", headPilihan)
                                .addFormDataPart("username", username_apr)
                                .build();

                        Request request = new Request.Builder()
                                .post(body)
                                .url(Setting.IP + "proses_kordinasi_pbj.php")
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
                                                        "Kordinasi ke Bagian terkait sudah dikirimkan!",
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

                        //Toast.makeText(getActivity(),
                        //        "Head yang dipilih: " + headPilihan,
                        //        Toast.LENGTH_LONG).show();
                    }
                });
                dialog.show();
            }
        });

        Button btnCancel = (Button) x.findViewById(R.id.btnCancelP);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText tCatatanCAd = (EditText) x.findViewById(R.id.tCatatanPBJ);

                final String isiKomenCAd = tCatatanCAd.getText().toString();

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
                                .addFormDataPart("komenad", isiKomenCAd)
                                .addFormDataPart("no_permintaan", nopermintaan)
                                .addFormDataPart("id_user", id_user)
                                .build();

                        Request request = new Request.Builder()
                                .post(body)
                                .url(Setting.IP + "proses_cancel_pbj.php")
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

        Button btnWvP = (Button) x.findViewById(R.id.btnWVP);
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
                        .replace(R.id.frameApprovePBJ, wv)
                        .addToBackStack(null)
                        .commit();
            }
        });

        LinearLayout lnWvp = (LinearLayout) x.findViewById(R.id.lnWVP);
        lnWvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String no_permintaan = txtWVP.getText().toString();

                WebViewPBJFragment wvp = new WebViewPBJFragment();

                Bundle b = new Bundle();
                b.putString("no_permintaan",no_permintaan);

                wvp.setArguments(b);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameApprovePBJ, wvp)
                        .addToBackStack(null)
                        .commit();
            }
        });

        lnAFP.setOnClickListener(new View.OnClickListener() {
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

        btnAFP.setOnClickListener(new View.OnClickListener() {
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

        return x;
    }

}
