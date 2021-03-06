package ui.activity;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import UtilityClasses.Constants;

public class FirebaseFcmService extends FirebaseMessagingService {
    public FirebaseFcmService() {
    }

    private static final String TAG = "MyActivity";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        Log.d(TAG, "im here ");
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        FirebaseMessaging.getInstance().subscribeToTopic(Constants.CITY);
        Log.e("city",Constants.CITY);

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    //@Override
    //public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        //String test1 = remoteMessage.getFrom();
        //String test2 = remoteMessage.getNotification().getBody();

      //  String value = remoteMessage.getData().get("urlAddress");
    //}
}
