package iom.modernland.co.id;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

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

public class MainRedActivity extends AppCompatActivity {

    TextView nameuser, walletuser;
    LinearLayout menuiom, menupbj, menunpv;
    ViewFlipper v_flipper;
    DrawerLayout drawermain;
    NavigationView navmain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_red);

        nameuser = findViewById(R.id.nameuser);
        walletuser = findViewById(R.id.walletuser);

        drawermain = (DrawerLayout) findViewById(R.id.drawerMain);
        navmain = (NavigationView) findViewById(R.id.navMain);

        navmain.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.menuChange) {

                    //Intent i = new Intent(MainRedActivity.this, ChangePAsswordActivity.class);
                    //startActivity(i);

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainRedActivity.this);
                    final View mView = getLayoutInflater().inflate(R.layout.dialog_change_password,null);


                    Button mSubmit = (Button) mView.findViewById(R.id.btnSubmitPass);
                    mSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            final TextInputEditText mNewPass = (TextInputEditText) mView.findViewById(R.id.newPassword);
                            final TextInputEditText mConfirmPass = (TextInputEditText) mView.findViewById(R.id.confirmPassword);

                            final String isi1 = mNewPass.getText().toString();
                            final String isi2 = mConfirmPass.getText().toString();

                           //if(isi1.isEmpty() && isi2.isEmpty()){
                           //    Toast.makeText(MainRedActivity.this,
                           //            "Please fill any empty fields",
                           //           Toast.LENGTH_SHORT).show();
                           //}

                            if (isi1.length() == 0) {
                                Toast.makeText(MainRedActivity.this,
                                        "Password masih kosong",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (isi2.length() == 0)
                            {
                                Toast.makeText(MainRedActivity.this,
                                        "Confirm Password masih kosong",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }

                           if(!isi1.equals(isi2)) {
                               Toast.makeText(MainRedActivity.this,
                                       "New Password must same with Confirmation",
                                       Toast.LENGTH_SHORT).show();
                               return;
                           }

                            // buka postman
                            OkHttpClient postman = new OkHttpClient();

                            // body
                            SharedPreferences sp = (MainRedActivity.this)
                                    .getSharedPreferences("DATALOGIN", 0);

                            String id_user      = sp.getString("id_user", "");

                            RequestBody body = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("id_user", id_user)
                                    .addFormDataPart("password", isi1)
                                    .build();

                            // request (POST + tujuan)
                            Request request = new Request.Builder()
                                    .post(body)
                                    .url(Setting.IP + "proses_change_password.php")
                                    .build();

                            // progress dialog
                            final ProgressDialog pd = new ProgressDialog(
                                    MainRedActivity.this
                            );
                            pd.setMessage("Please Wait");
                            pd.setTitle("Loading ...");
                            pd.setIcon(R.drawable.ic_check_black_24dp);
                            pd.show();

                            // send
                            postman.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(),
                                                    "Please try again",
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
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getApplicationContext(),
                                                            "Sukses",
                                                            Toast.LENGTH_LONG).show();
                                                    pd.dismiss();

                                                    Intent i = new Intent(getApplicationContext(),
                                                            MainRedActivity.class);

                                                    startActivity(i);
                                                    finish();
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

                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();


                } else if (menuItem.getItemId() == R.id.menuLogout) {

                    AlertDialog.Builder ab = new AlertDialog.Builder(MainRedActivity.this);

                    ab.create();
                    ab.setTitle("Confirmation");
                    ab.setIcon(R.drawable.ic_check_black_24dp);
                    ab.setMessage("Are you sure to logout?");
                    ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // buka postman
                            OkHttpClient postman = new OkHttpClient();

                            // body
                            SharedPreferences spl = (MainRedActivity.this)
                                    .getSharedPreferences("DATALOGIN", 0);

                            String username      = spl.getString("username", "");

                            RequestBody body = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("username", username)
                                    .build();

                            // request (POST + tujuan)
                            Request request = new Request.Builder()
                                    .post(body)
                                    .url(Setting.IP + "proses_logout.php")
                                    .build();

                            // progress dialog
                            final ProgressDialog pdl = new ProgressDialog(
                                    MainRedActivity.this
                            );
                            pdl.setMessage("Please Wait");
                            pdl.setTitle("Loading ...");
                            pdl.setIcon(R.drawable.ic_check_black_24dp);
                            pdl.show();

                            // send
                            postman.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(),
                                                    "Please try again",
                                                    Toast.LENGTH_LONG).show();
                                            pdl.dismiss();
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
                                                    pdl.dismiss();
                                                }
                                            });
                                        }
                                        else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getApplicationContext(),
                                                            "Sukses",
                                                            Toast.LENGTH_LONG).show();
                                                    pdl.dismiss();

                                                    getSharedPreferences("DATALOGIN", MODE_PRIVATE)
                                                            .edit().clear().commit();

                                                    Intent i = new Intent(MainRedActivity.this, LoginActivity.class);
                                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(i);
                                                    finish();
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
                            dialog.cancel();
                        }
                    });

                    ab.show();

                } else if (menuItem.getItemId() == R.id.menuExit) {

                    AlertDialog.Builder ab = new AlertDialog.Builder(MainRedActivity.this);

                    ab.create();
                    ab.setTitle("Confirmation");
                    ab.setIcon(R.drawable.ic_check_black_24dp);
                    ab.setMessage("Are you sure to exit?");
                    ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finishAffinity();

                        }
                    });
                    ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    ab.show();
                }
                return false;
            }
        });

        menuiom = (LinearLayout) findViewById(R.id.menugIom);
        menupbj = (LinearLayout) findViewById(R.id.menugPbj);
        menunpv = (LinearLayout) findViewById(R.id.menugNpv);

        int images[] = {R.drawable.slider1mdln,
                R.drawable.slider2mdln};
        v_flipper = findViewById(R.id.v_flipper);

        for (int i =0; i<images.length; i++){
            fliverImages(images[i]);
        }
        for (int image: images)
            fliverImages(image);

        menuiom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainRedActivity.this, HomeUserActivity.class);
                startActivity(i);
            }
        });

        menupbj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainRedActivity.this, HomePermohonanActivity.class);
                startActivity(i);
            }
        });

        menunpv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainRedActivity.this, HomeNPVActivity.class);
                startActivity(i);


            }
        });

    }

    public  void  fliverImages(int images){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(images);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(4000);
        v_flipper.setAutoStart(true);

        v_flipper.setInAnimation(this,android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(this,android.R.anim.slide_out_right);

    }
}
