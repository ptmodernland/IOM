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

public class KategoriIomActivity extends AppCompatActivity  {


    private ImageBadgeView imageBadgeViewMarketingClub,imageBadgeViewFinance,imageBadgeViewQs,imageBadgeViewLegal;
    private ImageBadgeView imageBadgeViewPurchasing,imageBadgeViewBdd,imageBadgeViewProject,imageBadgeViewPromosi;
    private ImageBadgeView imageBadgeViewMarketing,imageBadgeViewHrd,imageBadgeViewTown,imageBadgeViewLanded,imageBadgeViewPermit;
    DrawerLayout drawermain;
    NavigationView navmain;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_iom);

        imageBadgeViewMarketingClub = findViewById(R.id.iMarketingClub);
        imageBadgeViewFinance = findViewById(R.id.iFinance);
        imageBadgeViewQs = findViewById(R.id.iQs);
        imageBadgeViewLegal = findViewById(R.id.iLegal);
        imageBadgeViewPurchasing = findViewById(R.id.iPurchasing);
        imageBadgeViewBdd = findViewById(R.id.iBdd);
        imageBadgeViewProject = findViewById(R.id.iProject);
        imageBadgeViewPromosi = findViewById(R.id.iPromosi);
        imageBadgeViewMarketing = findViewById(R.id.iMarketing);
        imageBadgeViewHrd = findViewById(R.id.iHrd);
        imageBadgeViewTown = findViewById(R.id.iTown);
        imageBadgeViewLanded = findViewById(R.id.iLanded);
        imageBadgeViewPermit = findViewById(R.id.iPermit);

        drawermain = (DrawerLayout) findViewById(R.id.drawerMain);
        navmain = (NavigationView) findViewById(R.id.navMain);

        navmain.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.menuChange) {


                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(KategoriIomActivity.this);
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
                            SharedPreferences sp = (KategoriIomActivity.this)
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
                                    KategoriIomActivity.this
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

                    AlertDialog.Builder ab = new AlertDialog.Builder(KategoriIomActivity.this);

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
                            SharedPreferences spl = (KategoriIomActivity.this)
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
                                    KategoriIomActivity.this
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

                                                    Intent i = new Intent(KategoriIomActivity.this, LoginActivity.class);
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

                    AlertDialog.Builder ab = new AlertDialog.Builder(KategoriIomActivity.this);

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
                .url(Setting.IP + "counter_kategori.php?username=" + username)
                .build();

        final ProgressDialog pd = new ProgressDialog(KategoriIomActivity.this);
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
                    final int total_marketing_club = j.getInt("total_marketing_club");
                    final int total_finance = j.getInt("total_finance");
                    final int total_qs = j.getInt("total_qs");
                    final int total_legal = j.getInt("total_legal");
                    final int total_purchasing = j.getInt("total_purchasing");
                    final int total_bdd = j.getInt("total_bdd");
                    final int total_project = j.getInt("total_project");
                    final int total_promosi = j.getInt("total_promosi");
                    final int total_marketing = j.getInt("total_marketing");
                    final int total_hrd = j.getInt("total_hrd");
                    final int total_landed = j.getInt("total_landed");
                    final int total_town = j.getInt("total_town");
                    final int total_permit = j.getInt("total_permit");

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
                                pd.dismiss();
                                initIconWithBadges(total_marketing_club,total_finance,total_qs,total_legal,total_purchasing,total_bdd,total_project,total_promosi,total_marketing,total_hrd,total_landed,total_town,total_permit);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void MarketingClub(View view) {
        SharedPreferences.Editor sp
                = getSharedPreferences("DATALOGIN",0).edit();
        sp.putString("divisi","1");
        sp.putString("class_action","lHead");
        sp.commit();
        Intent i = new Intent(KategoriIomActivity.this, ContentKategoriActivity.class);
        startActivity(i);
    }

    public void Finance(View view) {
        SharedPreferences.Editor sp
                = getSharedPreferences("DATALOGIN",0).edit();
        sp.putString("divisi","2");
        sp.commit();
        Intent i = new Intent(KategoriIomActivity.this, ContentKategoriActivity.class);
        startActivity(i);
    }

    public void Qs(View view) {
        SharedPreferences.Editor sp
                = getSharedPreferences("DATALOGIN",0).edit();
        sp.putString("divisi","3");
        sp.commit();
        Intent i = new Intent(KategoriIomActivity.this, ContentKategoriActivity.class);
        startActivity(i);
    }

    public void Legal(View view) {
        SharedPreferences.Editor sp
                = getSharedPreferences("DATALOGIN",0).edit();
        sp.putString("divisi","4");
        sp.commit();
        Intent i = new Intent(KategoriIomActivity.this, ContentKategoriActivity.class);
        startActivity(i);
    }

    public void Purchasing(View view) {
        SharedPreferences.Editor sp
                = getSharedPreferences("DATALOGIN",0).edit();
        sp.putString("divisi","5");
        sp.commit();
        Intent i = new Intent(KategoriIomActivity.this, ContentKategoriActivity.class);
        startActivity(i);
    }

    public void Bdd(View view) {
        SharedPreferences.Editor sp
                = getSharedPreferences("DATALOGIN",0).edit();
        sp.putString("divisi","6");
        sp.commit();
        Intent i = new Intent(KategoriIomActivity.this, ContentKategoriActivity.class);
        startActivity(i);
    }

    public void Project(View view) {
        SharedPreferences.Editor sp
                = getSharedPreferences("DATALOGIN",0).edit();
        sp.putString("divisi","7");
        sp.commit();
        Intent i = new Intent(KategoriIomActivity.this, ContentKategoriActivity.class);
        startActivity(i);
    }

    public void Promosi(View view) {
        SharedPreferences.Editor sp
                = getSharedPreferences("DATALOGIN",0).edit();
        sp.putString("divisi","8");
        sp.commit();
        Intent i = new Intent(KategoriIomActivity.this, ContentKategoriActivity.class);
        startActivity(i);
    }

    public void Marketing(View view) {
        SharedPreferences.Editor sp
                = getSharedPreferences("DATALOGIN",0).edit();
        sp.putString("divisi","9");
        sp.commit();
        Intent i = new Intent(KategoriIomActivity.this, ContentKategoriActivity.class);
        startActivity(i);
    }
    public void Hrd(View view) {
        SharedPreferences.Editor sp
                = getSharedPreferences("DATALOGIN",0).edit();
        sp.putString("divisi","10");
        sp.commit();
        Intent i = new Intent(KategoriIomActivity.this, ContentKategoriActivity.class);
        startActivity(i);
    }

    public void Town(View view) {
        SharedPreferences.Editor sp
                = getSharedPreferences("DATALOGIN",0).edit();
        sp.putString("divisi","11");
        sp.commit();
        Intent i = new Intent(KategoriIomActivity.this, ContentKategoriActivity.class);
        startActivity(i);
    }

    public void Landed(View view) {
        SharedPreferences.Editor sp
                = getSharedPreferences("DATALOGIN",0).edit();
        sp.putString("divisi","12");
        sp.commit();
        Intent i = new Intent(KategoriIomActivity.this, ContentKategoriActivity.class);
        startActivity(i);
    }

    public void Permit(View view) {
        SharedPreferences.Editor sp
                = getSharedPreferences("DATALOGIN",0).edit();
        sp.putString("divisi","13");
        sp.commit();
        Intent i = new Intent(KategoriIomActivity.this, ContentKategoriActivity.class);
        startActivity(i);
    }
    private void initIconWithBadges(int total, int total_finance,int total_qs,int total_legal,int total_purchasing,int total_bdd,int total_project,int total_promosi,int total_marketing,int total_hrd,int total_landed,int total_town,int total_permit) {
        //value = total;
        Typeface typeface = Typeface.createFromAsset(getAssets(), "exo_regular.ttf");
        imageBadgeViewMarketingClub.setBadgeValue(total)
                .setBadgeOvalAfterFirst(true)
                .setBadgeTextSize(16)
                .setMaxBadgeValue(999)
                .setBadgeTextFont(typeface)
                .setBadgeBackground(getResources().getDrawable(R.drawable.rectangle_rounded))
                .setBadgePosition(BadgePosition.TOP_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);

        imageBadgeViewFinance.setBadgeValue(total_finance)
                .setBadgeOvalAfterFirst(true)
                .setBadgeTextSize(16)
                .setMaxBadgeValue(999)
                .setBadgeTextFont(typeface)
                .setBadgeBackground(getResources().getDrawable(R.drawable.rectangle_rounded))
                .setBadgePosition(BadgePosition.TOP_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);

        imageBadgeViewQs.setBadgeValue(total_qs)
                .setBadgeOvalAfterFirst(true)
                .setBadgeTextSize(16)
                .setMaxBadgeValue(999)
                .setBadgeTextFont(typeface)
                .setBadgeBackground(getResources().getDrawable(R.drawable.rectangle_rounded))
                .setBadgePosition(BadgePosition.TOP_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);

        imageBadgeViewLegal.setBadgeValue(total_legal)
                .setBadgeOvalAfterFirst(true)
                .setBadgeTextSize(16)
                .setMaxBadgeValue(999)
                .setBadgeTextFont(typeface)
                .setBadgeBackground(getResources().getDrawable(R.drawable.rectangle_rounded))
                .setBadgePosition(BadgePosition.TOP_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);

        imageBadgeViewPurchasing.setBadgeValue(total_purchasing)
                .setBadgeOvalAfterFirst(true)
                .setBadgeTextSize(16)
                .setMaxBadgeValue(999)
                .setBadgeTextFont(typeface)
                .setBadgeBackground(getResources().getDrawable(R.drawable.rectangle_rounded))
                .setBadgePosition(BadgePosition.TOP_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);

        imageBadgeViewBdd.setBadgeValue(total_bdd)
                .setBadgeOvalAfterFirst(true)
                .setBadgeTextSize(16)
                .setMaxBadgeValue(999)
                .setBadgeTextFont(typeface)
                .setBadgeBackground(getResources().getDrawable(R.drawable.rectangle_rounded))
                .setBadgePosition(BadgePosition.TOP_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);

        imageBadgeViewProject.setBadgeValue(total_project)
                .setBadgeOvalAfterFirst(true)
                .setBadgeTextSize(16)
                .setMaxBadgeValue(999)
                .setBadgeTextFont(typeface)
                .setBadgeBackground(getResources().getDrawable(R.drawable.rectangle_rounded))
                .setBadgePosition(BadgePosition.TOP_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);

        imageBadgeViewPromosi.setBadgeValue(total_promosi)
                .setBadgeOvalAfterFirst(true)
                .setBadgeTextSize(16)
                .setMaxBadgeValue(999)
                .setBadgeTextFont(typeface)
                .setBadgeBackground(getResources().getDrawable(R.drawable.rectangle_rounded))
                .setBadgePosition(BadgePosition.TOP_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);

        imageBadgeViewMarketing.setBadgeValue(total_marketing)
                .setBadgeOvalAfterFirst(true)
                .setBadgeTextSize(16)
                .setMaxBadgeValue(999)
                .setBadgeTextFont(typeface)
                .setBadgeBackground(getResources().getDrawable(R.drawable.rectangle_rounded))
                .setBadgePosition(BadgePosition.TOP_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);

        imageBadgeViewHrd.setBadgeValue(total_hrd)
                .setBadgeOvalAfterFirst(true)
                .setBadgeTextSize(16)
                .setMaxBadgeValue(999)
                .setBadgeTextFont(typeface)
                .setBadgeBackground(getResources().getDrawable(R.drawable.rectangle_rounded))
                .setBadgePosition(BadgePosition.TOP_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);

        imageBadgeViewLanded.setBadgeValue(total_landed)
                .setBadgeOvalAfterFirst(true)
                .setBadgeTextSize(16)
                .setMaxBadgeValue(999)
                .setBadgeTextFont(typeface)
                .setBadgeBackground(getResources().getDrawable(R.drawable.rectangle_rounded))
                .setBadgePosition(BadgePosition.TOP_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);

        imageBadgeViewTown.setBadgeValue(total_town)
                .setBadgeOvalAfterFirst(true)
                .setBadgeTextSize(16)
                .setMaxBadgeValue(999)
                .setBadgeTextFont(typeface)
                .setBadgeBackground(getResources().getDrawable(R.drawable.rectangle_rounded))
                .setBadgePosition(BadgePosition.TOP_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);

        imageBadgeViewPermit.setBadgeValue(total_permit)
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
