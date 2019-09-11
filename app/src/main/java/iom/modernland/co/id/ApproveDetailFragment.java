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
public class ApproveDetailFragment extends Fragment {


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
                    final String jenis = jo.getString("jenis");
                    final String perihal = jo.getString("perihal");

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            txtNomor.setText(nomor);
                            txtKepada.setText(kepada);
                            txtCc.setText(cc);
                            txtDari.setText(dari);
                            txtTanggal.setText(tanggal);
                            txtJenis.setText(jenis);
                            txtPerihal.setText(perihal);
                            txtWV.setText(id);

                            pd.dismiss();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Button btnCancel = (Button) x.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .popBackStackImmediate();
            }
        });

        Button btnApprove = (Button) x.findViewById(R.id.btnApprove);
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

                        OkHttpClient postman = new OkHttpClient();

                        SharedPreferences sp = getActivity()
                                .getSharedPreferences("DATALOGIN", 0);

                        String id_user      = sp.getString("id_user", "");

                        RequestBody body = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
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
                        Toast.makeText(getActivity(),
                                "Pengajuan belum disetujui!", Toast.LENGTH_SHORT).show();
                    }
                });

                ab.show();
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

        return x;
    }

}
