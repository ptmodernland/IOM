package iom.modernland.co.id;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class ContentListActivity extends AppCompatActivity {

    FloatingActionMenu materialDesignFAm;
    FloatingActionButton fabBack, fabLogout, fabExit, fabMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_list);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameMemo, new ListMemoFragment())
                .addToBackStack(null)
                .commit();

        materialDesignFAm = (FloatingActionMenu) findViewById(R.id.menuFAM);
        //fabBack = (FloatingActionButton) findViewById(R.id.menuFabBack);
        fabLogout = (FloatingActionButton) findViewById(R.id.menuFabLogout);
        fabMain = (FloatingActionButton) findViewById(R.id.menuFabMain);
        fabExit = (FloatingActionButton) findViewById(R.id.menuFabExit);

        /*
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getSupportFragmentManager()
                            .popBackStackImmediate();
            }
        });
        */

        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        HomeUserActivity.class);

                startActivity(i);
            }
        });

        fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(ContentListActivity.this);

                ab.create();
                ab.setTitle("Confirmation");
                ab.setIcon(R.drawable.ic_check_black_24dp);
                ab.setMessage("Are you sure to logout?");
                ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        getSharedPreferences("DATALOGIN", MODE_PRIVATE)
                                .edit().clear().commit();

                        Intent i = new Intent(ContentListActivity.this, LoginActivity.class);
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

        fabExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(ContentListActivity.this);

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

}
