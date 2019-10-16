package iom.modernland.co.id;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class HomeNPVActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_npv);


    LinearLayout menuUserLogout = (LinearLayout) findViewById(R.id.menuUserLogoutNPV);
    LinearLayout menuUserExit = (LinearLayout) findViewById(R.id.menuUserExitNPV);

        menuUserLogout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            AlertDialog.Builder ab = new AlertDialog.Builder(HomeNPVActivity.this);

            ab.create();
            ab.setTitle("Confirmation");
            ab.setIcon(R.drawable.ic_check_black_24dp);
            ab.setMessage("Are you sure to logout?");
            ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    getSharedPreferences("DATALOGIN", MODE_PRIVATE)
                            .edit().clear().commit();

                    Intent i = new Intent(HomeNPVActivity.this, LoginActivity.class);
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

            AlertDialog.Builder ab = new AlertDialog.Builder(HomeNPVActivity.this);

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

    public void ApprovePembelian(View view) {

        //Intent i = new Intent(HomeNPVActivity.this, ContentApprovePBJActivity.class);
        //startActivity(i);

        AlertDialog.Builder alert = new AlertDialog.Builder(HomeNPVActivity.this);
        alert.setMessage("Sorry.. This Content Under Construction");
        alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.create().show();

    }

    public void listPembelian(View view) {

        //Intent i = new Intent(HomeNPVActivity.this, ContentListPBJActivity.class);
        //startActivity(i);

        AlertDialog.Builder alert = new AlertDialog.Builder(HomeNPVActivity.this);
        alert.setMessage("Sorry.. This Content Under Construction");
        alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.create().show();

    }
}
