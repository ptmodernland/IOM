package iom.modernland.co.id;


import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

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
public class ApproveDetailFragment extends Fragment {

    long queueid;
    DownloadManager dm ;
    String IP_ISI = "";
    String address = "";
    public ApproveDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View x = inflater.inflate(R.layout.fragment_approve_detail, container, false);

        final String id = getArguments().getString("idnya");

        final String nomormemo = getArguments().getString("nomornya");

        final TextView txtNomor = (TextView) x.findViewById(R.id.txtNomord);
        final TextView txtKepada = (TextView) x.findViewById(R.id.txtKepada);
        final TextView txtCc = (TextView) x.findViewById(R.id.txtCc);
        final TextView txtDari = (TextView) x.findViewById(R.id.txtDari);
        final TextView txtTanggal = (TextView) x.findViewById(R.id.txtTanggal);
        //final TextView txtJenis = (TextView) x.findViewById(R.id.txtJenis);
        final TextView txtPerihal = (TextView) x.findViewById(R.id.txtPerihal);
        final TextView txtWV = (TextView) x.findViewById(R.id.txtWV);
        final TextView txtdownload_file = (TextView) x.findViewById(R.id.txtdownload_file);
        final TextView txtKategori = (TextView) x.findViewById(R.id.txtKategori);
        final TextView download_file = (TextView) x.findViewById(R.id.download_file);
        //final Button btnAF = (Button) x.findViewById(R.id.btnAF);
        final Button btnKordinasi = (Button) x.findViewById(R.id.btnKordinasiM);
        final Button btnApprove = (Button) x.findViewById(R.id.btnApproveM);

        OkHttpClient postman = new OkHttpClient();

        Request r = new Request.Builder()
                .get()
                .url(Setting.IP + "get_memo.php?idiom=" + id)
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
                    JSONObject jo = new JSONObject(hasil);

                    final String nomor = jo.getString("nomor");
                    final String kepada = jo.getString("kepada");
                    final String cc = jo.getString("cc");
                    final String dari = jo.getString("dari");
                    final String tanggal = jo.getString("tanggal");
                    //final String jenis = jo.getString("jenis");
                    final String perihal = jo.getString("perihal");
                    final String lampiran = jo.getString("attch_lampiran");
                    final String status = jo.getString("status");
                    final String kategori = jo.getString("kategori_iom");

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (!lampiran.isEmpty()){

                                txtNomor.setText(nomor);
                                txtKepada.setText(kepada);
                                txtCc.setText(cc);
                                txtDari.setText(dari);
                                txtTanggal.setText(tanggal);
                                //txtJenis.setText(jenis);
                                txtPerihal.setText(perihal);
                                txtWV.setText(id);
                                txtdownload_file.setText(lampiran);
                                txtKategori.setText(kategori);
                                txtdownload_file.setPaintFlags(txtdownload_file.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                            } else {

                                txtNomor.setText(nomor);
                                txtKepada.setText(kepada);
                                txtCc.setText(cc);
                                txtDari.setText(dari);
                                txtTanggal.setText(tanggal);
                                //txtdownload_file.setText(jenis);
                                txtPerihal.setText(perihal);
                                txtWV.setText(id);
                                txtdownload_file.setVisibility(View.INVISIBLE);
                                download_file.setVisibility(View.INVISIBLE);
                                txtKategori.setText(kategori);
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

                        EditText tCatatan = (EditText) x.findViewById(R.id.tCatatan);

                        final String isiKomen = tCatatan.getText().toString();

                        OkHttpClient postman = new OkHttpClient();

                        SharedPreferences sp = getActivity()
                                .getSharedPreferences("DATALOGIN", 0);

                        String id_user      = sp.getString("id_user", "");

                        RequestBody body = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("komen", isiKomen)
                                .addFormDataPart("nomor", nomormemo)
                                .addFormDataPart("passwordUser", isiPassword)
                                .addFormDataPart("ipaddres", IP_ISI)
                                .addFormDataPart("macaddress", address)
                                .addFormDataPart("id_user", id_user)
                                .build();

                        Request request = new Request.Builder()
                                .post(body)
                                .url(Setting.IP + "proses_approve.php")
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
                                                        "Iom Berhasil DIsetujui!",
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

        btnKordinasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                final View mView = getLayoutInflater().inflate(R.layout.list_head, null);

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
                                //.addFormDataPart("nomor", nomormemo)
                                //.addFormDataPart("head", headPilihan)
                                .addFormDataPart("username", username_apr)
                                .build();

                        Request request = new Request.Builder()
                                .post(body)
                                .url(Setting.IP + "proses_kordinasi.php")
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
                dialog.show();*/
                String id_iom = txtWV.getText().toString();
                String nomornya = txtNomor.getText().toString();

                Bundle b = new Bundle();
                b.putString("idiomnya",id_iom);
                b.putString("nomornya",nomornya);

                Intent i = new Intent(getActivity(),
                        DialogHeadActivity.class);

                i.putExtras(b);
                startActivity(i);
            }
        });


        Button btnCancel = (Button) x.findViewById(R.id.btnCancelM);
        btnCancel.setOnClickListener(new View.OnClickListener() {
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

                        EditText tCatatan = (EditText) x.findViewById(R.id.tCatatan);

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

                        final String isiKomen = tCatatan.getText().toString();

                        OkHttpClient postman = new OkHttpClient();

                        SharedPreferences sp = getActivity()
                                .getSharedPreferences("DATALOGIN", 0);

                        String id_user      = sp.getString("id_user", "");

                        RequestBody body = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("komen", isiKomen)
                                .addFormDataPart("nomor", nomormemo)
                                .addFormDataPart("id_user", id_user)
                                .addFormDataPart("ipaddres", IP_ISI)
                                .addFormDataPart("macaddress", address)
                                .build();

                        Request request = new Request.Builder()
                                .post(body)
                                .url(Setting.IP + "proses_cancel_memo.php")
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

        TextView  txtview_detail = (TextView) x.findViewById(R.id.txtview_detail);
        txtview_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_iom = txtWV.getText().toString();

                WebViewFragment wv = new WebViewFragment();

                Bundle b = new Bundle();
                b.putString("idiomnya",id_iom);

                wv.setArguments(b);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameApprove, wv)
                        .addToBackStack(null)
                        .commit();
            }
        });



        /*lnAF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String attch_lampiran = txtAF.getText().toString();

                dm = (DownloadManager)getContext().getSystemService(Context.DOWNLOAD_SERVICE);

                Uri uri = Uri.parse("https://approval.modernland.co.id/assets/file/"
                        + attch_lampiran);

                DownloadManager.Request request = new DownloadManager.Request(uri);

                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                Long reference = dm.enqueue(request);
            }
        });*/


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
