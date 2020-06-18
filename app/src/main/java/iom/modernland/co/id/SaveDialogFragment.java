package iom.modernland.co.id;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class SaveDialogFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment

        //setContentView(R.layout.frame_header);

        OkHttpClient postman = new OkHttpClient();

        SharedPreferences sp = getActivity()
                .getSharedPreferences("DATALOGIN", 0);

        String id_user = sp.getString("id_user", "");

        final String head = getArguments().getString("head");

        final String nomor = getArguments().getString("nomor");

        //Toast.makeText(getActivity(),
                //nomor, Toast.LENGTH_LONG).show();

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("head", head)
                .addFormDataPart("nomor", nomor)
                .addFormDataPart("id_user", id_user)
                .build();

        Request request = new Request.Builder()
                .post(body)
                .url(Setting.IP + "proses_kordinasi.php")
                .build();

        final ProgressDialog pd = new ProgressDialog(
                getActivity()
        );

        pd.setMessage("Please Wait");
        pd.setTitle("Loading Kordinasi...");
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
                                Toast.LENGTH_SHORT).show();
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

                    if (st == false) {
                        final String p = j.getString("pesan");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(),
                                        p, Toast.LENGTH_LONG).show();
                                pd.dismiss();
                            }
                        });
                    } else {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getApplicationContext(),
                                        "Kordinasi ke Bagian terkait sudah dikirimkan!",
                                        Toast.LENGTH_LONG).show();

                                Intent i = new Intent(getActivity(),
                                        ContentApproveActivity.class);
                                startActivity(i);

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

}
