package iom.modernland.co.id;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView bgapp, clover;
    TextView txtFlashUser;
    LinearLayout textsplash, texthome, menus, menuiom, menupbj, menunpv;
    Animation frombottom;
    boolean click = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);

        bgapp = (ImageView) findViewById(R.id.bgapp);
        clover = (ImageView) findViewById(R.id.clover);
        textsplash = (LinearLayout) findViewById(R.id.textsplash);
        texthome = (LinearLayout) findViewById(R.id.texthome);
        menus = (LinearLayout) findViewById(R.id.menus);
        menuiom = (LinearLayout) findViewById(R.id.menuIom);
        menupbj = (LinearLayout) findViewById(R.id.menuPbj);
        menunpv = (LinearLayout) findViewById(R.id.menuNpv);

        txtFlashUser = (TextView) findViewById(R.id.txtFlashUser);

        SharedPreferences sp = getApplicationContext()
                .getSharedPreferences("DATALOGIN", 0);

        String username      = sp.getString("nama", "");

        txtFlashUser.setText(username);

        bgapp.animate().translationY(-900).setDuration(3000).setStartDelay(1000);
        clover.animate().alpha(0).setDuration(3000).setStartDelay(1000);
        textsplash.animate().translationY(140).alpha(0).setDuration(3000).setStartDelay(1000);

        texthome.startAnimation(frombottom);
        menus.startAnimation(frombottom);

        menuiom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, HomeUserActivity.class);
                startActivity(i);
            }
        });

        menupbj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, HomePermohonanActivity.class);
                startActivity(i);
            }
        });

        menunpv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Under Construction", Toast.LENGTH_LONG).show();

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setMessage("Sorry.. This Content Under Construction");
                alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.create().show();
            }
        });


    }
}
