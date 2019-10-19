package sadh.sarthak.cool.com.nightwatch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;


public class IncomingSms extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction()==SMS_RECEIVED){
            Bundle bundle = intent.getExtras();

            if (bundle != null) {
                // get sms objects
                Object[] pdus = (Object[]) bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[pdus.length];
                String format = bundle.getString("format");

                for(int i=0;i<pdus.length;i++){
                    messages[i]= SmsMessage.createFromPdu((byte[])pdus[i],format);
                    String sender = messages[i].getOriginatingAddress();
                    String message = messages[i].getMessageBody();
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }

            }

        }

    }
}

