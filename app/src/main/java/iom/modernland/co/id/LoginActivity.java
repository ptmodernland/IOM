package iom.modernland.co.id;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void prosesLogin(View view) {

        TextInputEditText etUsername = (TextInputEditText) findViewById(R.id.etUsername);
        TextInputEditText etPassword = (TextInputEditText) findViewById(R.id.etPassword);

        final String isiuser = etUsername.getText().toString();
        final String isipass = etPassword.getText().toString();


        //Intent i = new Intent(getApplicationContext(),
        //        HomeUserActivity.class);
        //startActivity(i);
        //finish();

        OkHttpClient postman = new OkHttpClient();

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", isiuser)
                .addFormDataPart("password", isipass)
                .build();

        Request request = new Request.Builder()
                .post(body)
                .url(Setting.IP + "proses_login.php")
                .build();

        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setTitle("Loading");
        pd.setMessage("Please Wait..");
        pd.setIcon(R.drawable.ic_playlist_add_check_black_24dp);
        pd.setCancelable(false);
        pd.show();

        postman.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Please try again : " +e.getMessage(),
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        p, Toast.LENGTH_LONG).show();
                                pd.dismiss();
                            }
                        });
                    }
                    else {

                        final String lvl = j.getString("level");
                        String iduser = j.getString("id_user");
                        String isiuser = j.getString("username");
                        String nama = j.getString("nama");


                        SharedPreferences.Editor sp
                                = getSharedPreferences("DATALOGIN",0).edit();

                        sp.putString("id_user",iduser);
                        sp.putString("username",isiuser);
                        sp.putString("nama",nama);
                        sp.putString("level",lvl);

                        sp.commit();

                        if (lvl.equals("head")){

                            Intent i = new Intent(getApplicationContext(),
                                    MainActivity.class);

                            i.putExtra("id_user", iduser);
                            i.putExtra("user", isiuser);
                            i.putExtra("nama", nama);
                            i.putExtra("level",lvl);

                            startActivity(i);
                            finish();
                        }

                        /*else if (lvl.equals("administrasi")){

                            Intent i = new Intent(getApplicationContext(),
                                    HomeUserActivity.class);

                            i.putExtra("id_user", iduser);
                            i.putExtra("user", isiuser);
                            i.putExtra("password", isipass);
                            i.putExtra("level",lvl);

                            startActivity(i);
                            finish();

                        }*/

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
