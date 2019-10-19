package sadh.sarthak.cool.com.nightwatch;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Person;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.renderscript.RenderScript;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ClipBoardService extends Service {
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

    private OnPrimaryClipChangedListener listener = new OnPrimaryClipChangedListener() {
        public void onPrimaryClipChanged() {
            performClipboardCheck();
        }
    };

    @Override
    public void onCreate() {
        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).addPrimaryClipChangedListener(listener);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void performClipboardCheck() {
        ClipboardManager cb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (cb.hasPrimaryClip()) {
            ClipData cd = cb.getPrimaryClip();
            assert cd != null;
            if (cd.getDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                detectSpam(cd.getItemAt(0).getText().toString());

//               Toast.makeText(ClipBoardService.this,cd.getItemAt(0).getText(),Toast.LENGTH_LONG).show();
//               openBubble();




            }
        }

    }


    public void detectSpam(String text){

        openBubble("spam");

//        Toast.makeText(ClipBoardService.this,"detect started",Toast.LENGTH_LONG).show();

        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.post("https://hackcbs2.herokuapp.com/text")
                .addBodyParameter("text", text)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                       Toast.makeText(ClipBoardService.this,response.toString(),Toast.LENGTH_LONG).show();
                       openBubble(response.toString());
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });


    }


    public void openBubble(String response){
        SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
        editor.putString("result", response);
        editor.apply();
        Intent service = new Intent(ClipBoardService.this,PopUpService.class);
        startService(service);


    }



}



