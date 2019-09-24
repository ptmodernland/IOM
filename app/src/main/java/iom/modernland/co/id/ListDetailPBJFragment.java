package iom.modernland.co.id;


import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListDetailPBJFragment extends Fragment {

    DownloadManager dm ;

    public ListDetailPBJFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_list_detail_pbj, container, false);

        final String nopermintaan = getArguments().getString("no_permintaan");

        final TextView txtNoPermintaan = (TextView) x.findViewById(R.id.txtNoPermoLd);
        final TextView txtTanggal = (TextView) x.findViewById(R.id.txtTanggalLd);
        final TextView txtJenis = (TextView) x.findViewById(R.id.txtJenisPermoLd);
        final TextView txtKaryawan = (TextView) x.findViewById(R.id.txtKaryawanLd);
        final TextView txtJabatan = (TextView) x.findViewById(R.id.txtJabatanLd);
        final TextView txtDepartemen = (TextView) x.findViewById(R.id.txtDepartemenLd);
        final TextView txtWVP = (TextView) x.findViewById(R.id.txtWVLP);
        final TextView txtAFP = (TextView) x.findViewById(R.id.txtAFLP);
        final LinearLayout lnAFP = (LinearLayout) x.findViewById(R.id.lnAFLP);
        final Button btnAFP = (Button) x.findViewById(R.id.btnAFLP);

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
                                txtAFP.setText(lampiran);
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

        Button btnWvP = (Button) x.findViewById(R.id.btnWVLP);
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
                        .replace(R.id.framePermohonanBJ, wv)
                        .addToBackStack(null)
                        .commit();
            }
        });


        return x;
    }

}
