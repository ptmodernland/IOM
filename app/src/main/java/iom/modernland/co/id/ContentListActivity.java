package iom.modernland.co.id;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ContentListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_list);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameMemo, new ListMemoFragment())
                .addToBackStack(null)
                .commit();
    }
}
