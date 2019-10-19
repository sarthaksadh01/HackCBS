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
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

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

               Toast.makeText(ClipBoardService.this,cd.getItemAt(0).getText(),Toast.LENGTH_LONG).show();
               openBubble();




            }
        }

    }


    public void openBubble(){
        startService(new Intent(ClipBoardService.this, PopUpService.class));

    }



}



