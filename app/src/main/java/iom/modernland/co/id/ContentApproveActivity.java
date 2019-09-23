package iom.modernland.co.id;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class ContentApproveActivity extends AppCompatActivity {

    FloatingActionMenu materialDesignFAmA;
    FloatingActionButton fabBackA, fabLogoutA, fabExitA, fabMainA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_approve);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameApprove, new ApproveMemoFragment())
                .addToBackStack(null)
                .commit();

        materialDesignFAmA = (FloatingActionMenu) findViewById(R.id.menuFAMA);
        //fabBackA = (FloatingActionButton) findViewById(R.id.menuFabBackA);
        fabLogoutA = (FloatingActionButton) findViewById(R.id.menuFabLogoutA);
        fabMainA = (FloatingActionButton) findViewById(R.id.menuFabMainA);
        fabExitA = (FloatingActionButton) findViewById(R.id.menuFabExitA);

        /*
        fabBackA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .popBackStackImmediate();
            }
        });
        */

        fabMainA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        HomeUserActivity.class);

                startActivity(i);
            }
        });

        fabLogoutA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(ContentApproveActivity.this);

                ab.create();
                ab.setTitle("Confirmation");
                ab.setIcon(R.drawable.ic_check_black_24dp);
                ab.setMessage("Are you sure to logout?");
                ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        getSharedPreferences("DATALOGIN", MODE_PRIVATE)
                                .edit().clear().commit();

                        Intent i = new Intent(ContentApproveActivity.this, LoginActivity.class);
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

        fabExitA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(ContentApproveActivity.this);

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
