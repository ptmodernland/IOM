package iom.modernland.co.id;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class HomePermohonanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_permohonan);

        LinearLayout menuUserLogout = (LinearLayout) findViewById(R.id.menuUserLogout);
        LinearLayout menuUserExit = (LinearLayout) findViewById(R.id.menuUserExit);

        menuUserLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder ab = new AlertDialog.Builder(HomePermohonanActivity.this);

                ab.create();
                ab.setTitle("Confirmation");
                ab.setIcon(R.drawable.ic_check_black_24dp);
                ab.setMessage("Are you sure to logout?");
                ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        getSharedPreferences("DATALOGIN", MODE_PRIVATE)
                                .edit().clear().commit();

                        Intent i = new Intent(HomePermohonanActivity.this, LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
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
    }

    public void ApprovePermohonan(View view) {
    }

    public void listPermohonan(View view) {
    }
}
