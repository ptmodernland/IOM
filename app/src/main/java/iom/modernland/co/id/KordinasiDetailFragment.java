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
import android.widget.TableRow;
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
public class KordinasiDetailFragment extends Fragment {

    DownloadManager dm ;

    public KordinasiDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View x = inflater.inflate(R.layout.fragment_kordinasi_detail, container, false);

        final String id = getArguments().getString("idnya");
        final String nomormemo = getArguments().getString("nomornya");
        final String username = getArguments().getString("username");

        final TextView txtNomor = (TextView) x.findViewById(R.id.txtNomorKD);
        final TextView txtKepada = (TextView) x.findViewById(R.id.txtKepadaKD);
        final TextView txtCc = (TextView) x.findViewById(R.id.txtCcKD);
        final TextView txtDari = (TextView) x.findViewById(R.id.txtDariKD);
        final TextView txtTanggal = (TextView) x.findViewById(R.id.txtTanggalKD);
        final TextView txtJenis = (TextView) x.findViewById(R.id.txtJenisKD);
        final TextView txtPerihal = (TextView) x.findViewById(R.id.txtPerihalKD);
        final TextView txtStatusKD = (TextView) x.findViewById(R.id.txtStatusKD);
        //final TextView txtStatusKor = (TextView) x.findViewById(R.id.txtStatusKD);


        final TextView txtWVKD = (TextView) x.findViewById(R.id.txtWVKD);
        final TextView txtAFKD = (TextView) x.findViewById(R.id.txtAFKD);
        final LinearLayout lnAFKD = (LinearLayout) x.findViewById(R.id.lnAFKD);

        final Button btnApproveKD = (Button) x.findViewById(R.id.btnApproveMKD);
        final Button btnCancelKD = (Button) x.findViewById(R.id.btnCancelMKD);

        OkHttpClient postman = new OkHttpClient();

        SharedPreferences sp = getActivity()
                .getSharedPreferences("DATALOGIN", 0);

        //final String username_apr      = sp.getString("username", "");

        Request r = new Request.Builder()
                .get()
                .url(Setting.IP + "get_kordinasi.php?nomormemo=" + nomormemo+"&id="+id+"&username="+username)
                .build();

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Please wait ...");
        pd.setTitle("Loading Ticket ...");
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
                    final String status = jo.getString("status");

                    final EditText tCatatanKD = (EditText) x.findViewById(R.id.tCatatanKD);

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
                                txtWVKD.setText(id);
                                txtAFKD.setText(lampiran);

                                if ("Y".equals(status)){
                                    txtStatusKD.setText("Kordinasi Belum Di Approve");
                                }
                                else if ("T".equals(status)){
                                    txtStatusKD.setText("Kordinasi Di Approve");
                                }
                                else{
                                    txtStatusKD.setText("Kordinasi Di Batalkan");
                                }
                            } else {

                                txtNomor.setText(nomor);
                                txtKepada.setText(kepada);
                                txtCc.setText(cc);
                                txtDari.setText(dari);
                                txtTanggal.setText(tanggal);
                                txtJenis.setText(jenis);
                                txtPerihal.setText(perihal);
                                txtWVKD.setText(id);

                                lnAFKD.setVisibility(View.INVISIBLE);

                                if ("Y".equals(status)){
                                    txtStatusKD.setText("Kordinasi Belum Di Approve");
                                }
                                else if ("T".equals(status)){
                                    txtStatusKD.setText("Kordinasi Di Approve");
                                }
                                else{
                                    txtStatusKD.setText("Kordinasi Di Batalkan");
                                }
                            }

                            if ("T".equals(status)){
                                btnApproveKD.setVisibility(View.INVISIBLE);
                                btnCancelKD.setVisibility(View.INVISIBLE);

                            }


                            pd.dismiss();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Button btnWvKD = (Button) x.findViewById(R.id.btnWVKD);
        btnWvKD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_iom = txtWVKD.getText().toString();

                WebViewFragment wv = new WebViewFragment();

                Bundle b = new Bundle();
                b.putString("idiomnya",id_iom);

                wv.setArguments(b);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameKordinasi, wv)
                        .addToBackStack(null)
                        .commit();
            }
        });

        LinearLayout lnWvKD = (LinearLayout) x.findViewById(R.id.lnWVKD);
        lnWvKD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_iom = txtWVKD.getText().toString();

                WebViewFragment wv = new WebViewFragment();

                Bundle b = new Bundle();
                b.putString("idiomnya",id_iom);

                wv.setArguments(b);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameKordinasi, wv)
                        .addToBackStack(null)
                        .commit();
            }
        });

        Button btnAFKD = (Button) x.findViewById(R.id.btnAFKD);
        btnAFKD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String attch_lampiran = txtAFKD.getText().toString();

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

                        final EditText tCatatanKD = (EditText) x.findViewById(R.id.tCatatanKD);

                        final String isiKomen = tCatatanKD.getText().toString();

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
                                .url(Setting.IP + "proses_approve_kordinasi.php")
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

                        final EditText tCatatanKD = (EditText) x.findViewById(R.id.tCatatanKD);

                        final String isiKomen = tCatatanKD.getText().toString();

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
                                .url(Setting.IP + "proses_cancel_kordinasi.php")
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
