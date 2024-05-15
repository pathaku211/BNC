package com.example.bnc;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static final int PERMISSION_REQUEST_CODE = 100;
    private String title;
    private String message;
    private String imageUrl;

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("token", token);
        assert currentUser != null;
        tokenData.put("email", currentUser.getEmail());
        tokenData.put("timestamp", FieldValue.serverTimestamp());
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("DeviceTokens").document().set(tokenData);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

//        Map<String, String> data = remoteMessage.getData();

        if (remoteMessage.getNotification() != null) {
            // Show the notification
            title = remoteMessage.getNotification().getTitle();
            message = remoteMessage.getNotification().getBody();
            imageUrl = String.valueOf(remoteMessage.getNotification().getImageUrl());
//        title = data.get("title");
//        message = data.get("message");
//        imageUrl = data.get("image"); // Assuming the image URL is provided in the data payload
        }
        // Handle the notification
        sendNotification();
    }

    private void sendNotification() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_IMMUTABLE);

        String channelId = "fcm_default_channel";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher) // Set your app's small icon
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        // If imageUrl is not null, load the image and set it as notification's large icon
        if (imageUrl != null) {
            try {
                Bitmap bitmap = Glide.with(this)
                        .asBitmap()
                        .load(imageUrl)
                        .submit()
                        .get();

                if (bitmap != null) {
                    notificationBuilder.setLargeIcon(bitmap);
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }


        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
