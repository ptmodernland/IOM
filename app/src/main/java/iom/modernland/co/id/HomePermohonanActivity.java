package iom.modernland.co.id;

import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
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
import ru.nikartm.support.BadgePosition;
import ru.nikartm.support.ImageBadgeView;

public class HomePermohonanActivity extends AppCompatActivity {

    private ImageBadgeView imageBadgeView;
    private ImageBadgeView imageBadgeViewKordP;
    private int value = 0;
    DrawerLayout drawermain;
    NavigationView navmain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_permohonan);

        imageBadgeView = findViewById(R.id.ibv_icon5);
        imageBadgeViewKordP = findViewById(R.id.ibv_iconKordP);

        drawermain = (DrawerLayout) findViewById(R.id.drawerMain);
        navmain = (NavigationView) findViewById(R.id.navMain);

        navmain.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.menuChange) {

                    //Intent i = new Intent(MainRedActivity.this, ChangePAsswordActivity.class);
                    //startActivity(i);

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomePermohonanActivity.this);
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
                            SharedPreferences sp = (HomePermohonanActivity.this)
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
                                    HomePermohonanActivity.this
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

                    AlertDialog.Builder ab = new AlertDialog.Builder(HomePermohonanActivity.this);

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
                            SharedPreferences spl = (HomePermohonanActivity.this)
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
                                    HomePermohonanActivity.this
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

                                                    Intent i = new Intent(HomePermohonanActivity.this, LoginActivity.class);
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

                    AlertDialog.Builder ab = new AlertDialog.Builder(HomePermohonanActivity.this);

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
        
        SharedPreferences sp = getSharedPreferences("DATALOGIN", 0);

        String username      = sp.getString("username", "");

        OkHttpClient postman = new OkHttpClient();

        Request request = new Request.Builder()
                .get()
                .url(Setting.IP + "counter_permohonan.php?username=" + username)
                .build();

        final ProgressDialog pd = new ProgressDialog(HomePermohonanActivity.this);
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
                    JSONObject jpk = new JSONObject(hasil);
                    boolean st = jpk.getBoolean("status");
                    final int total = jpk.getInt("total");
                    //final int totalKordinasi = jpk.getInt("total_kordinasi");

                    if(st == false)
                    {
                        final String p = jpk.getString("pesan");
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
                                pd.dismiss();

                                initIconWithBadges(total);
                                //initIconWithBadgesKordP(totalKordinasi);

                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        /*
        LinearLayout menuUserExit = (LinearLayout) findViewById(R.id.menuUserExitPBJ);
        menuUserExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder ab = new AlertDialog.Builder(HomePermohonanActivity.this);

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
        });
        */
        LinearLayout menuUserLogout = (LinearLayout) findViewById(R.id.menuUserLogout);
        menuUserLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawermain = (DrawerLayout) findViewById(R.id.drawerMain);
                if(!drawermain.isDrawerOpen(Gravity.START)) drawermain.openDrawer(Gravity.START);
                else drawermain.closeDrawer(Gravity.END);
            }
        });
    }

    public void ApprovePermohonan(View view) {

        Intent i = new Intent(HomePermohonanActivity.this, ContentApprovePBJActivity.class);
        startActivity(i);

    }

    public void listPermohonan(View view) {

        Intent i = new Intent(HomePermohonanActivity.this, ContentListPBJActivity.class);
        startActivity(i);
    }

    public void KordinasiPBJ(View view) {

        Intent i = new Intent(HomePermohonanActivity.this, ContentKordinasiPBJActivity.class);
        startActivity(i);
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
                .setBadgePosition(BadgePosition.BOTTOM_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);
    }

    private void initIconWithBadgesKordP(int totalKordinasi) {
        value = totalKordinasi;
        Typeface typeface = Typeface.createFromAsset(getAssets(), "exo_regular.ttf");
        imageBadgeViewKordP.setBadgeValue(value)
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

}
