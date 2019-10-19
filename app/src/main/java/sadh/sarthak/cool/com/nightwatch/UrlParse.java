package sadh.sarthak.cool.com.nightwatch;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.crowdfire.cfalertdialog.CFAlertDialog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

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
        try {
            run(url);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onResume() {

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String url = prefs.getString("url", "No name defined");
        textView = findViewById(R.id.url);
//        textView.setText(url);

        super.onResume();
    }


    void run(final String url) throws IOException {

        final ProgressDialog  progressDialog = new ProgressDialog(UrlParse.this);
        progressDialog.setMessage("Please wait..."); // Setting Message
        progressDialog.setTitle("Checking Url for Spam"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://hackcbs2.herokuapp.com/url/"+ URLEncoder.encode(url, "utf-8"))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                final String myResponse = response.body().string();

                UrlParse.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        textView.setText(myResponse);
                        if(myResponse.equals("OK")){
                            openChrome(url);
                        }
                        else{
                            showError(url);
                        }

                    }
                });

            }
        });
    }

    public void showError(String url){
        // Create Alert using Builder
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this)
                .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setTitle("Spam Alert!")
                .setMessage("Looks like "+url+" is not safe avoid visiting this url");


// Show the alert
        builder.show();
    }

    public void openChrome(String url){

        try {
            Intent i = new Intent("android.intent.action.MAIN");
            i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
            i.addCategory("android.intent.category.LAUNCHER");
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        catch(ActivityNotFoundException e) {
            // Chrome is not installed
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(i);
        }
    }
}
