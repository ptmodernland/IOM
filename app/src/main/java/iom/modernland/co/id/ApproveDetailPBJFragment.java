package iom.modernland.co.id;


import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.format.Formatter;
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
    String IP_ISI = "";
    String address = "";

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
        //final TextView txtWVP = (TextView) x.findViewById(R.id.txtWVP);
        //final TextView txtAFP = (TextView) x.findViewById(R.id.txtAFP);
        //final LinearLayout lnAFP = (LinearLayout) x.findViewById(R.id.lnAFP);
        //final Button btnAFP = (Button) x.findViewById(R.id.btnAFP);
        final TextView txtWV = (TextView) x.findViewById(R.id.txtWV);
        final TextView txtdownload_file = (TextView) x.findViewById(R.id.txtdownload_file);
        final Button btnKordinasi = (Button) x.findViewById(R.id.btnKordinasiP);
        final Button btnApprove = (Button) x.findViewById(R.id.btnApproveP);
        final TextView download_file = (TextView) x.findViewById(R.id.download_file);

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
                                txtWV.setText(nopermintaan);
                                txtdownload_file.setText(lampiran);
                                txtLama.setText(lama + " hari");
                                txtPending.setText(pending + " hari");
                                txtdownload_file.setPaintFlags(txtdownload_file.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                            } else {

                                txtNoPermintaan.setText(no_permintaan);
                                txtTanggal.setText(tgl_permintaan);
                                txtJenis.setText(jenis);
                                txtKaryawan.setText(nama_user);
                                txtJabatan.setText(jabatan);
                                txtDepartemen.setText(departemen);
                                txtWV.setText(nopermintaan);
                                txtdownload_file.setVisibility(View.INVISIBLE);
                                download_file.setVisibility(View.INVISIBLE);
                                txtLama.setText("\n" + lama + " hari");
                                txtPending.setText(pending + " hari");

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
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                final View mView = getLayoutInflater().inflate(R.layout.approve_password, null);

                mBuilder.setView(mView);

                AlertDialog dialog = mBuilder.create();
                dialog.setButton(Dialog.BUTTON_POSITIVE, "Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);

                        WifiInfo info = wifiManager.getConnectionInfo();
                        String IP = Formatter.formatIpAddress(info.getIpAddress());
                        String address_wifi = Utils.getMACAddress("wlan0");
                        String address_lan =  Utils.getMACAddress("eth0");
                        String IP_LOKAL = Utils.getIPAddress(true); // IPv4

                        if (IP_LOKAL==""){
                            IP_ISI = IP;
                            address = address_lan;

                        }
                        else{
                            IP_ISI = IP_LOKAL;
                            address = address_wifi;
                        }

                        final TextView passwordUser = (TextView) mView.findViewById(R.id.etPassword);
                        final String isiPassword = passwordUser.getText().toString();

                        EditText tCatatanAd = (EditText) x.findViewById(R.id.tCatatanPBJ);

                        final String isiKomenAd = tCatatanAd.getText().toString();

                                OkHttpClient postman = new OkHttpClient();

                                SharedPreferences sp = getActivity()
                                        .getSharedPreferences("DATALOGIN", 0);

                                String id_user      = sp.getString("id_user", "");

                                RequestBody body = new MultipartBody.Builder()
                                        .setType(MultipartBody.FORM)
                                        .addFormDataPart("komenad", isiKomenAd)
                                        .addFormDataPart("no_permintaan", nopermintaan)
                                        .addFormDataPart("id_user", id_user)
                                        .addFormDataPart("passwordUser", isiPassword)
                                        .addFormDataPart("ipaddres", IP_ISI)
                                        .addFormDataPart("macaddress", address)
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
                dialog.show();

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
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                final View mView = getLayoutInflater().inflate(R.layout.approve_password, null);

                mBuilder.setView(mView);

                AlertDialog dialog = mBuilder.create();
                dialog.setButton(Dialog.BUTTON_POSITIVE, "Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText tCatatan = (EditText) x.findViewById(R.id.tCatatan);
                        final TextView passwordUser = (TextView) mView.findViewById(R.id.etPassword);
                        final String isiPassword = passwordUser.getText().toString();

                        WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);

                        WifiInfo info = wifiManager.getConnectionInfo();
                        String IP = Formatter.formatIpAddress(info.getIpAddress());
                        String address_wifi = Utils.getMACAddress("wlan0");
                        String address_lan =  Utils.getMACAddress("eth0");
                        String IP_LOKAL = Utils.getIPAddress(true); // IPv4

                        if (IP_LOKAL==""){
                            IP_ISI = IP;
                            address = address_lan;

                        }
                        else{
                            IP_ISI = IP_LOKAL;
                            address = address_wifi;
                        }

                        EditText tCatatanCAd = (EditText) x.findViewById(R.id.tCatatanPBJ);

                        final String isiKomenCAd = tCatatanCAd.getText().toString();


                        OkHttpClient postman = new OkHttpClient();

                        SharedPreferences sp = getActivity()
                                .getSharedPreferences("DATALOGIN", 0);

                        String id_user      = sp.getString("id_user", "");

                        RequestBody body = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("komenad", isiKomenCAd)
                                .addFormDataPart("no_permintaan", nopermintaan)
                                .addFormDataPart("id_user", id_user)
                                .addFormDataPart("ipaddres", IP_ISI)
                                .addFormDataPart("macaddress", address)
                                .addFormDataPart("passwordUser", isiPassword)
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

                dialog.show();
            }
        });

        TextView  txtview_detail = (TextView) x.findViewById(R.id.txtview_detail);
        txtview_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String no_permintaan = txtWV.getText().toString();

                WebViewPBJFragment wv = new WebViewPBJFragment();

                Bundle b = new Bundle();
                b.putString("no_permintaan",no_permintaan);

                wv.setArguments(b);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameApprovePBJ, wv)
                        .addToBackStack(null)
                        .commit();
            }
        });

        txtdownload_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String attch_lampiran = txtdownload_file.getText().toString();

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
