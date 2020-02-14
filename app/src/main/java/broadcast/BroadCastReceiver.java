package broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.example.auctionsystemapplication.R;

import createChannel.CreateChannel;
public class BroadCastReceiver extends android.content.BroadcastReceiver {

    private int id =1;
    private NotificationManagerCompat notificationManagerCompat;
    Context context;

    public BroadCastReceiver(Context context) {
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean noConnectivity;
        notificationManagerCompat = NotificationManagerCompat.from(context);

        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){

            noConnectivity = intent.getBooleanExtra(
                    ConnectivityManager.EXTRA_NO_CONNECTIVITY,
                    false
            );

            if (noConnectivity){
                Toast.makeText(context, "not connected", Toast.LENGTH_SHORT).show();
                displayNotificationOne();
            }else {
                Toast.makeText(context, "connected", Toast.LENGTH_SHORT).show();
                displayNotificationTwo();
            }
        }


    }

    private void displayNotificationTwo() {
        Notification notificationTwo= new NotificationCompat.Builder(context, CreateChannel.CHANNEL_2)
                .setSmallIcon(R.drawable.ic_add_circle_outline_black_24dp)
                .setContentTitle("Added")
                .setContentText("You bid  have been added")
                .setCategory(NotificationCompat.CATEGORY_SYSTEM)
                .build();
        notificationManagerCompat.notify(id, notificationTwo);
        id++;


    }

    private void displayNotificationOne() {
        Notification notificationOne = new NotificationCompat.Builder(context, CreateChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_add_circle_outline_black_24dp)
                .setContentTitle("No connectivity")
                .setContentText("No connection, please connect")
                .setCategory(NotificationCompat.CATEGORY_SYSTEM)
                .build();
        notificationManagerCompat.notify(id, notificationOne);
        id++;

    }
}