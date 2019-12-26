package iom.modernland.co.id;


import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
        final TextView txtJenis = (TextView) x.findViewById(R.id.txtJenis);
        final TextView txtPerihal = (TextView) x.findViewById(R.id.txtPerihal);
        final TextView txtWV = (TextView) x.findViewById(R.id.txtWV);
        final TextView txtAF = (TextView) x.findViewById(R.id.txtAF);
        final LinearLayout lnAF = (LinearLayout) x.findViewById(R.id.lnAF);
        final Button btnAF = (Button) x.findViewById(R.id.btnAF);
        final Button btnKordinasi = (Button) x.findViewById(R.id.btnKordinasiM);

        OkHttpClient postman = new OkHttpClient();

        SharedPreferences sp = getActivity()
                .getSharedPreferences("DATALOGIN", 0);

        String username      = sp.getString("username", "");

        Request r = new Request.Builder()
                .get()
                .url(Setting.IP + "get_memo.php?idiom=" + id +"&user=" + username)
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
                    final String jenis = jo.getString("jenis");
                    final String perihal = jo.getString("perihal");
                    final String lampiran = jo.getString("attch_lampiran");
                    final Boolean kordinasi = jo.getBoolean("kordinasi");

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (!lampiran.isEmpty()){

                                txtNomor.setText(nomor);
                                txtKepada.setText(kepada);
                                txtCc.setText(cc);
                                txtDari.setText(dari);
                                txtTanggal.setText(tanggal);
                                txtJenis.setText(jenis);
                                txtPerihal.setText(perihal);
                                txtWV.setText(id);
                                txtAF.setText(lampiran);

                            } else {

                                txtNomor.setText(nomor);
                                txtKepada.setText(kepada);
                                txtCc.setText(cc);
                                txtDari.setText(dari);
                                txtTanggal.setText(tanggal);
                                txtJenis.setText(jenis);
                                txtPerihal.setText(perihal);
                                txtWV.setText(id);

                                lnAF.setVisibility(View.INVISIBLE);

                            }

                            if (kordinasi == false){


                            } else {

                                btnKordinasi.setVisibility(View.INVISIBLE);

                            }

                            pd.dismiss();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Button btnApprove = (Button) x.findViewById(R.id.btnApproveM);
        btnApprove.setOnClickListener(new View.OnClickListener() {
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

        btnKordinasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog_pilih_head, null);

                final Spinner spnPilihHead = (Spinner) x.findViewById(R.id.spnHead);

                /*
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
                        String hasil = response.body().string();
                        try {
                            JSONArray j = new JSONArray(hasil);

                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>();
                            final ArrayList<ListHead> data = new ArrayList<>();

                            for (int i = 0;i < j.length();i++)
                            {
                                JSONObject jo = j.getJSONObject(i);
                                ListHead l = new ListHead();

                                l.namaUser = jo.getString("namaUser");

                                data.add(l);
                            }

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pd.dismiss();

                                    spnPilihHead.setAdapter(adapter);
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                */

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
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

        Button btnWv = (Button) x.findViewById(R.id.btnWV);
        btnWv.setOnClickListener(new View.OnClickListener() {
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

        LinearLayout lnWv = (LinearLayout) x.findViewById(R.id.lnWV);
        lnWv.setOnClickListener(new View.OnClickListener() {
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


        lnAF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String attch_lampiran = txtAF.getText().toString();

                dm = (DownloadManager)getContext().getSystemService(Context.DOWNLOAD_SERVICE);

                Uri uri = Uri.parse("https://reminder.modernland.co.id/iom/assets/file/"
                        + attch_lampiran);

                DownloadManager.Request request = new DownloadManager.Request(uri);

                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                Long reference = dm.enqueue(request);
            }
        });

        btnAF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String attch_lampiran = txtAF.getText().toString();

                dm = (DownloadManager)getContext().getSystemService(Context.DOWNLOAD_SERVICE);

                Uri uri = Uri.parse("https://reminder.modernland.co.id/iom/assets/file/"
                        + attch_lampiran);

                DownloadManager.Request request = new DownloadManager.Request(uri);

                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                Long reference = dm.enqueue(request);
            }
        });

        return x;
    }

}
