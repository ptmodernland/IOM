package iom.modernland.co.id;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    String strPhoneType = "";
    int PHONE_type;
    TelephonyManager telephonyManager;
    static final int PERMISSION_READ_STATE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sp = getSharedPreferences("DATALOGIN",MODE_PRIVATE);

        if(sp.contains("username")){
            Intent i = new Intent(getApplicationContext(),MainRedActivity.class);

            i.putExtra("x",
                    sp.getString("email",""));

            startActivity(i);
            finish();
        }else{

            Toast.makeText(this, "Please Login", Toast.LENGTH_LONG).show();

        }

        CheckBox Remember = (CheckBox) findViewById(R.id.ckRemember);
        Remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    Toast.makeText(LoginActivity.this,"Data Login Saved", Toast.LENGTH_SHORT).show();
                }else if(!buttonView.isChecked()){
                    Toast.makeText(LoginActivity.this,"Unsave Data Login", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void prosesLogin(View view) {
        int permissionCheck = ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED){
            MyTelephoneManager();
        } else {
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[] {Manifest.permission.READ_PHONE_STATE},
                    PERMISSION_READ_STATE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_READ_STATE:
            {
                if(grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    MyTelephoneManager();
                }else {
                    Toast.makeText(this,
                            "You don't have required permission to make the action",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void MyTelephoneManager(){

        TextInputEditText etUsername = (TextInputEditText) findViewById(R.id.etUsername);
        TextInputEditText etPassword = (TextInputEditText) findViewById(R.id.etPassword);

        final String isiuser = etUsername.getText().toString();
        final String isipass = etPassword.getText().toString();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        String token = refreshedToken.toString();

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imeiNumber = telephonyManager.getImei();
        String softwareVersion = telephonyManager.getDeviceSoftwareVersion();
        String simSerial = telephonyManager.getSimSerialNumber();
        String subscribeId = telephonyManager.getSubscriberId();

        PHONE_type = telephonyManager.getPhoneType();

        switch (PHONE_type){
            case (TelephonyManager.PHONE_TYPE_CDMA) :
                strPhoneType = "CDMA";
                break;

            case (TelephonyManager.PHONE_TYPE_GSM) :
                strPhoneType = "GSM";
                break;

            case (TelephonyManager.PHONE_TYPE_NONE) :
                strPhoneType = "NONE";
                break;
        }

        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = wifiManager.getConnectionInfo();
        String address = info.getMacAddress();
        String IP = Formatter.formatIpAddress(info.getIpAddress());
        String Imei = UUID.randomUUID().toString();


        if (token.length() == 0) {
            Toast.makeText(getApplicationContext(),
                    "Token Ga Dapat",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        //Toast.makeText(getApplicationContext(),
        //        "Nomor Imei " + imeiNumber + "\n"
        //                + strPhoneType + "\n"
        //                + softwareVersion + "\n"
        //                + simSerial + "\n"
        //                + subscribeId + "\n"
        //                + Build.MANUFACTURER + "\n"
        //                + Build.MODEL + "\n"
        //                + Build.SERIAL + "\n"
        //                 + Build.BRAND,
        //        Toast.LENGTH_LONG).show();


        //Intent i = new Intent(getApplicationContext(),
        //        HomeUserActivity.class);
        //startActivity(i);
        //finish();


        OkHttpClient postman = new OkHttpClient();

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", isiuser)
                .addFormDataPart("password", isipass)
                .addFormDataPart("token", token)
                .addFormDataPart("simserial",simSerial)
                .addFormDataPart("imei",imeiNumber)
                .addFormDataPart("address", address)
                .addFormDataPart("ip", IP)
                .addFormDataPart("brand", Build.BRAND)
                .addFormDataPart("model", Build.MODEL)
                .addFormDataPart("phonetype", strPhoneType)
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

                                Intent i = new Intent(getApplicationContext(),
                                        MainRedActivity.class);

                                startActivity(i);
                                finish();
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
                                    MainRedActivity.class);

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
