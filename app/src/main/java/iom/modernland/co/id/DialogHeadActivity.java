package iom.modernland.co.id;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class DialogHeadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_header);


        final TextView tIDMemo = (TextView) findViewById(R.id.tIDMemo);
        final TextView tNomorMemo = (TextView) findViewById(R.id.tNomorMemo);

        Bundle bundle = getIntent().getExtras();

        tNomorMemo.setText(bundle.getString("nomornya"));
        tIDMemo.setText(bundle.getString("idiomnya"));

        tNomorMemo.setVisibility(tNomorMemo.INVISIBLE);
        tIDMemo.setVisibility(tIDMemo.INVISIBLE);

        String NomorMemo = tNomorMemo.getText().toString();
        String IDMemo = tIDMemo.getText().toString();

        DialogHeadFragment df = new DialogHeadFragment();

        Bundle bf = new Bundle();
        bf.putString("NomorMemo",NomorMemo);
        bf.putString("IDMemo",IDMemo);

        df.setArguments(bf);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameHead, df)
                .addToBackStack(null)
                .commit();

    }
}
