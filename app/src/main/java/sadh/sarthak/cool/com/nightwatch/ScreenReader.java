package sadh.sarthak.cool.com.nightwatch;

import android.accessibilityservice.AccessibilityService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

public class ScreenReader extends AccessibilityService {
    private static final String TAG = "";

    public ScreenReader() {
    }




    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

//        printAllViews(event.getSource());
        AccessibilityNodeInfo source = event.getSource();
        if(event.getEventType()==AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED){
            if(source.getText()!=null && source.getClassName().equals("android.widget.TextView")){
                String recivedText = source.getText().toString();
                Toast.makeText(ScreenReader.this,recivedText,Toast.LENGTH_LONG).show();
            }
        }





    }

    @Override
    public void onInterrupt() {
    }

    private void printAllViews(AccessibilityNodeInfo mNodeInfo) {
        if (mNodeInfo == null) return;
        for (int i = 0; i < mNodeInfo.getChildCount(); i++) {
            Toast.makeText(ScreenReader.this,mNodeInfo.getChild(i).getText(),Toast.LENGTH_LONG).show();
        }
    }
}
