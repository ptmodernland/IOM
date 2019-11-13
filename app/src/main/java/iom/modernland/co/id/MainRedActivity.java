package iom.modernland.co.id;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MainRedActivity extends AppCompatActivity {

    TextView nameuser, walletuser;
    LinearLayout menuiom, menupbj, menunpv;
    ViewFlipper v_flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_red);

        nameuser = findViewById(R.id.nameuser);
        walletuser = findViewById(R.id.walletuser);

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
