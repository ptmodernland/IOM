package iom.modernland.co.id;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
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
import ru.nikartm.support.BadgePosition;
import ru.nikartm.support.ImageBadgeView;

public class MainRedActivity extends AppCompatActivity {

    TextView nameuser, walletuser;
    private ImageBadgeView imageBadgeView, imageBadgeViewPO, imageBadgeViewPermohonan;
    private int value = 0;
    //LinearLayout menuiom, menupbj, menunpv, menuperubahan;
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
        imageBadgeView = findViewById(R.id.ibv_icon4);
        imageBadgeViewPO = findViewById(R.id.ibv_icon5);
        imageBadgeViewPermohonan = findViewById(R.id.ibv_icon6);

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
                            final TextInputEditText mNewPin = (TextInputEditText) mView.findViewById(R.id.newPin);

                            final String isi1 = mNewPass.getText().toString();
                            final String isi2 = mConfirmPass.getText().toString();
                            final String isiPin = mNewPin.getText().toString();

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
                                    .addFormDataPart("confirm_password", isi2)
                                    .addFormDataPart("pin", isiPin)
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
                                                            "Berhasil Logout",
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
                else if (menuItem.getItemId() == R.id.menuUtama) {

                    Intent i = new Intent(MainRedActivity.this, MainRedActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }
                return false;
            }
        });


        SharedPreferences sp = getSharedPreferences("DATALOGIN", 0);

        String username      = sp.getString("username", "");

        OkHttpClient postman = new OkHttpClient();

        Request request = new Request.Builder()
                .get()
                .url(Setting.IP + "counter_memo.php?username=" + username)
                .build();

        final ProgressDialog pd = new ProgressDialog(MainRedActivity.this);
        pd.setMessage("Please wait");
        pd.setTitle("Loading ...");
        pd.setIcon(R.drawable.ic_check_black_24dp);
        pd.setCancelable(false);
        pd.show();

        postman.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
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
                    final int total = j.getInt("total");
                    final int total_kordinasi = j.getInt("total_kordinasi");
                    final int total_keseluruhan = total + total_kordinasi;

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
                                initIconWithBadges(total_keseluruhan);
                                pd.dismiss();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        /*Request requestPO = new Request.Builder()
                .get()
                .url(Setting.IP + "counter_po.php?username=" + username)
                .build();

        final ProgressDialog pds = new ProgressDialog(MainRedActivity.this);
        pds.setMessage("Please wait");
        pds.setTitle("Loading ...");
        pds.setIcon(R.drawable.ic_check_black_24dp);
        pds.setCancelable(false);
        pds.show();

        postman.newCall(requestPO).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Please Try Again",
                                Toast.LENGTH_SHORT).show();
                        pds.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String hasil = response.body().string();
                try {
                    JSONObject j = new JSONObject(hasil);
                    boolean st = j.getBoolean("status");
                    final int total = j.getInt("total");

                    if(st == false)
                    {
                        final String p = j.getString("pesan");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        p, Toast.LENGTH_LONG).show();
                                pds.dismiss();
                            }
                        });
                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initIconWithBadgesPO(total);
                                pds.dismiss();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });*/

        Request requestPermohonan = new Request.Builder()
                .get()
                .url(Setting.IP + "counter_permohonan.php?username=" + username)
                .build();

        final ProgressDialog pdp = new ProgressDialog(MainRedActivity.this);
        pdp.setMessage("Please wait");
        pdp.setTitle("Loading ...");
        pdp.setIcon(R.drawable.ic_check_black_24dp);
        pdp.setCancelable(false);
        pdp.show();

        postman.newCall(requestPermohonan).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Please Try Again",
                                Toast.LENGTH_SHORT).show();
                        pdp.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String hasil = response.body().string();
                try {
                    JSONObject j = new JSONObject(hasil);
                    boolean st = j.getBoolean("status");
                    final int total = j.getInt("total");
                    final int total_kordinasi = j.getInt("total_kordinasi");
                    final int total_keseluruhan = total + total_kordinasi;

                    if(st == false)
                    {
                        final String p = j.getString("pesan");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        p, Toast.LENGTH_LONG).show();
                                pdp.dismiss();
                            }
                        });
                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initIconWithBadgesPermohonan(total_keseluruhan);
                                pdp.dismiss();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        int images[] = {R.drawable.slider1mdln,
                R.drawable.slider2mdln};
        v_flipper = findViewById(R.id.v_flipper);

        for (int i =0; i<images.length; i++){
            fliverImages(images[i]);
        }

        for (int image: images) {
            fliverImages(image);
        }

        LinearLayout menuiom = (LinearLayout) findViewById(R.id.menugIom);
        LinearLayout menupbj = (LinearLayout) findViewById(R.id.menugPbj);
        LinearLayout menunpv = (LinearLayout) findViewById(R.id.menugNpv);
        LinearLayout menuperubahan = (LinearLayout) findViewById(R.id.menugSetting);
        //LinearLayout menupo = (LinearLayout) findViewById(R.id.menugPO);

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

        /*menupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainRedActivity.this, HomePOActivity.class);
                startActivity(i);
            }
        });*/

        /*menunpv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainRedActivity.this, HomeNPVActivity.class);
                startActivity(i);
            }
        });*/

       menuperubahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!drawermain.isDrawerOpen(Gravity.START)) {
                    drawermain.openDrawer(Gravity.START);
                }
                else {
                    drawermain.closeDrawer(Gravity.END);
                }
            }
        });

    }

    private void initIconWithBadgesPO(int total) {
        value = total;
        Typeface typeface = Typeface.createFromAsset(getAssets(), "exo_regular.ttf");
        imageBadgeViewPO.setBadgeValue(value)
                .setBadgeOvalAfterFirst(true)
                .setBadgeTextSize(16)
                .setMaxBadgeValue(999)
                .setBadgeTextFont(typeface)
                .setBadgeBackground(getResources().getDrawable(R.drawable.rectangle_rounded))
                .setBadgePosition(BadgePosition.TOP_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);
    }

    private void initIconWithBadges(int total) {
        value = total;
        Typeface typeface = Typeface.createFromAsset(getAssets(), "exo_regular.ttf");
        imageBadgeView.setBadgeValue(value)
                .setBadgeOvalAfterFirst(true)
                .setBadgeTextSize(16)
                .setMaxBadgeValue(999)
                .setBadgeTextFont(typeface)
                .setBadgeBackground(getResources().getDrawable(R.drawable.rectangle_rounded))
                .setBadgePosition(BadgePosition.TOP_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);
    }

    private void initIconWithBadgesPermohonan(int total) {
        value = total;
        Typeface typeface = Typeface.createFromAsset(getAssets(), "exo_regular.ttf");
        imageBadgeViewPermohonan.setBadgeValue(value)
                .setBadgeOvalAfterFirst(true)
                .setBadgeTextSize(16)
                .setMaxBadgeValue(999)
                .setBadgeTextFont(typeface)
                .setBadgeBackground(getResources().getDrawable(R.drawable.rectangle_rounded))
                .setBadgePosition(BadgePosition.TOP_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);
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

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),
                "Tombol Back Tidak Bisa Digunakan", Toast.LENGTH_LONG).show();
    }
}
