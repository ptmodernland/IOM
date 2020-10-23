package iom.modernland.co.id;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomeTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_test);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameTest, new ApproveMemoFragment())
                .addToBackStack(null)
                .commit();
    }
}
