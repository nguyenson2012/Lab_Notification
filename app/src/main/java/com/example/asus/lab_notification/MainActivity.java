package com.example.asus.lab_notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText editTextContent;
    Button buttonNotification;
    Button buttonCustomNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
        buttonNotification.setOnClickListener(this);
        buttonCustomNotification.setOnClickListener(this);

    }

    private void setupView() {
        editTextContent=(EditText)findViewById(R.id.edit_content_notification);
        buttonNotification=(Button)findViewById(R.id.button_notification);
        buttonCustomNotification=(Button)findViewById(R.id.custom_notification);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.button_notification:
                Notify("New Message",editTextContent.getText()+"");
                break;
            case R.id.custom_notification:
                CustomNotification();
                break;
        }

    }
    private void Notify(String notificationTitle, String notificationMessage){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.animal_cartoon)
                        .setContentTitle(notificationTitle)
                        .setContentText(notificationMessage);
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, ActivityTwo.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(ActivityTwo.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(9999, mBuilder.build());
    }
    public void CustomNotification() {
        // Using RemoteViews to bind custom layouts into Notification
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(),
                R.layout.custom_notification_layout);

        // Set Notification Title
        String strtitle = "New message";
        // Set Notification Text
        String strtext = editTextContent.getText()+"";
        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(this, ActivityTwo.class);
        // Open NotificationView.java Activity
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setImageViewResource(R.id.img_left,R.mipmap.ic_launcher);
        remoteViews.setImageViewResource(R.id.img_right,R.mipmap.ic_launcher);

        // Locate and set the Text into customnotificationtext.xml TextViews
        remoteViews.setTextViewText(R.id.tvTitle,strtitle);
        remoteViews.setTextViewText(R.id.tvContent,strtext);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                // Set Icon
                .setSmallIcon(R.drawable.animal_cartoon)
                // Dismiss Notification
                .setAutoCancel(true)
                // Set PendingIntent into Notification
                .setContentIntent(pIntent)
                // Set RemoteViews into Notification
                .setContent(remoteViews);

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(9999, builder.build());

    }
}
