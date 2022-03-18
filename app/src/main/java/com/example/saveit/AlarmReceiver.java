package com.example.saveit;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.saveit.document.DocumentActivity;

/**
 * alarm receiver class for push notifications
 */
public class AlarmReceiver extends BroadcastReceiver {

    private String CHANNEL_ID = "CHANNEL_ID";
    private String TEXT_TITLE = "Reminder!";
    private String CONTENT_TITLE = "One of your docs is about to expire";


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent documentIntent = new Intent(context, DocumentActivity.class);
        documentIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        documentIntent.putExtra("call_reason", "edit_document");
        documentIntent.putExtra("position", intent.getIntExtra("position", -1));
        documentIntent.putExtra("document_title", intent.getStringExtra("document_title"));
        documentIntent.putExtra("category_title", intent.getStringExtra("category_title"));
        documentIntent.putExtra("document_comment", intent.getStringExtra("document_comment"));
        documentIntent.putExtra("document_expiration_date", intent.getStringExtra("document_expiration_date"));
        documentIntent.putExtra("document_reminder_time", intent.getStringExtra("document_reminder_time"));
        documentIntent.putExtra("has_image", intent.getBooleanExtra("has_image", false));
        documentIntent.putExtra("has_file", intent.getBooleanExtra("has_file", false));
        documentIntent.putExtra("file_download_uri", intent.getStringExtra("file_download_uri"));
        documentIntent.putExtra("has_alarm", intent.getBooleanExtra("has_alarm", false));
        documentIntent.putExtra("is_add_event_to_phone_calender", intent.getBooleanExtra("is_add_event_to_phone_calender", false));

        final int notificationId = intent.getIntExtra("notificationId", 0);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notificationId, documentIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_event_busy)
                .setContentTitle(TEXT_TITLE)
                .setContentText(CONTENT_TITLE)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());
    }
}
