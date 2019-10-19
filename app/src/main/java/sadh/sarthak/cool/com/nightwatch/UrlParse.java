package sadh.sarthak.cool.com.nightwatch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class UrlParse extends AppCompatActivity {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_parse);
        final Intent intent = getIntent();
        final String action = intent.getAction();

        if (Intent.ACTION_VIEW.equals(action)) {
            SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
            editor.putString("url", intent.getData().toString());
            editor.apply();
            Toast.makeText(UrlParse.this,intent.getData().toString(),Toast.LENGTH_LONG).show();
        }


        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String url = prefs.getString("url", "No name defined");
        textView = findViewById(R.id.url);
        textView.setText(url);

    }

    @Override
    protected void onResume() {

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String url = prefs.getString("url", "No name defined");
        textView = findViewById(R.id.url);
        textView.setText(url);

        super.onResume();
    }
}
