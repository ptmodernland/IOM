package iom.modernland.co.id;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class ContentListPBJActivity extends AppCompatActivity {

    FloatingActionMenu materialDesignFamL;
    FloatingActionButton fabBackL, fabLogoutL, fabExitL, fabMainL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_list_pbj);

        getSupportFragmentManager().beginTransaction().replace(R.id.framePermohonanBJ, new ListPBJFragment())
                .addToBackStack(null)
                .commit();

        materialDesignFamL = (FloatingActionMenu) findViewById(R.id.menuFamLP);
        //fabBackA = (FloatingActionButton) findViewById(R.id.menuFabBackAP);
        fabLogoutL = (FloatingActionButton) findViewById(R.id.menuFabLogoutLP);
        fabMainL = (FloatingActionButton) findViewById(R.id.menuFabMainLP);
        fabExitL = (FloatingActionButton) findViewById(R.id.menuFabExitLP);

        fabMainL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        HomePermohonanActivity.class);

                startActivity(i);
            }
        });

        fabLogoutL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(ContentListPBJActivity.this);

                ab.create();
                ab.setTitle("Confirmation");
                ab.setIcon(R.drawable.ic_check_black_24dp);
                ab.setMessage("Are you sure to logout?");
                ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        getSharedPreferences("DATALOGIN", MODE_PRIVATE)
                                .edit().clear().commit();

                        Intent i = new Intent(ContentListPBJActivity.this, LoginActivity.class);
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

        fabExitL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(ContentListPBJActivity.this);

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
