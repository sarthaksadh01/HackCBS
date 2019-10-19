package sadh.sarthak.cool.com.nightwatch;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE = 1222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // for clipboard trigger
        Intent intent = new Intent(MainActivity.this, ClipBoardService.class);
        startService(intent);

        // for reading screen
//        Intent intent2 = new Intent(MainActivity.this,ScreenReader.class);
//        startService(intent2);

        // for text msg
        Intent i = new Intent(MainActivity.this, IncomingSms.class);
        startService(i);

        // for notification

        Intent notification = new Intent(MainActivity.this,NotificationService.class);
        startService(notification);
    }



}
