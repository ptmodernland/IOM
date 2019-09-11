package iom.modernland.co.id;


import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListDetailFragment extends Fragment {


    public ListDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_list_detail, container, false);

        final String id = getArguments().getString("idnya");
        String nomormemo = getArguments().getString("nomornya");

        final TextView txtNomor = (TextView) x.findViewById(R.id.txtNomord);
        final TextView txtKepada = (TextView) x.findViewById(R.id.txtKepada);
        final TextView txtCc = (TextView) x.findViewById(R.id.txtCc);
        final TextView txtDari = (TextView) x.findViewById(R.id.txtDari);
        final TextView txtTanggal = (TextView) x.findViewById(R.id.txtTanggal);
        final TextView txtJenis = (TextView) x.findViewById(R.id.txtJenis);
        final TextView txtPerihal = (TextView) x.findViewById(R.id.txtPerihal);
        final TextView txtWVM = (TextView) x.findViewById(R.id.txtWVM);

        OkHttpClient postman = new OkHttpClient();

        Request r = new Request.Builder()
                .get()
                .url(Setting.IP + "get_memo.php?idiom=" + id)
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
                            txtWVM.setText(id);

                            pd.dismiss();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Button btnBack = (Button) x.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .popBackStackImmediate();
            }
        });

        Button btnWvM = (Button) x.findViewById(R.id.btnMVM);
        btnWvM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_iom = txtWVM.getText().toString();

                WebViewFragment wv = new WebViewFragment();

                Bundle b = new Bundle();
                b.putString("idiomnya",id_iom);

                wv.setArguments(b);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameMemo, wv)
                        .addToBackStack(null)
                        .commit();
            }
        });

        LinearLayout lnWvM = (LinearLayout) x.findViewById(R.id.lnWVM);
        lnWvM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_iom = txtWVM.getText().toString();

                WebViewFragment wv = new WebViewFragment();

                Bundle b = new Bundle();
                b.putString("idiomnya",id_iom);

                wv.setArguments(b);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameMemo, wv)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return x;
    }

}
