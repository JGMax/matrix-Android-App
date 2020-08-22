package com.project.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class alarmService extends Service {

    /*int i;
    long minutes = 0;

    GregorianCalendar calendar = new GregorianCalendar();
    long oldDate = 0;
    long newDate = 0;

    Date currentTime = Calendar.getInstance().getTime();

    List<Integer> activeAlarms;
    int sunRise;
    NotificationManager notificationManager;
    Context cont;

    @Override
    public void onCreate() {
        super.onCreate();
        cont = this;
        activeAlarms = ClockActivity.alarmMinutesActivated;
        sunRise = ClockActivity.sunriseTime;
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Handler alarmHandler = new Handler();
    Runnable alarmRunnable = new Runnable() {
        @Override
        public void run() {
            for(i = 0; i < activeAlarms.size(); i++)
            {
                oldDate = activeAlarms.get(i); //старое время в миллисекундах
                newDate = currentTime.getTime()/60000; //текущее время
                minutes = newDate-oldDate; //возвращаем в секундах разницу
                if(minutes >= 0)
                    break;
            }
            if(i == activeAlarms.size() && activeAlarms.size() != 0)
            {
                oldDate = activeAlarms.get(0); //старое время в миллисекундах
                minutes = oldDate-newDate; //возвращаем в секундах разницу
            }

            Toast.makeText(cont, Long.toString(newDate), Toast.LENGTH_SHORT).show();

            if(minutes < sunRise) {
                sendNotif();
                //notificationManager.notify(1, notification);
            }

            if(activeAlarms.size() != 0)
                alarmHandler.postDelayed(alarmRunnable, 1);
            else
                stopSelf();
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show();
        Toast.makeText(cont, Integer.toString(ClockActivity.alarmMinutesActivated.size()), Toast.LENGTH_SHORT).show();
        alarmHandler.post(alarmRunnable);
        return Service.START_STICKY;
    }

    void sendNotif() {
        // 1-я часть
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.alarm_icon)
                .setContentTitle("Рассвет")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setOngoing(false);

        Notification notification = builder.build();
        Toast.makeText(this, "Try to send", Toast.LENGTH_SHORT).show();
        //Notification notif = new Notification(R.drawable.alarm_icon, "Text in status bar",
                //System.currentTimeMillis());

        // 3-я часть
        //Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra(MainActivity.FILE_NAME, "somefile");
        //PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // 2-я часть
        //notif.setLatestEventInfo(this, "Notification's title", "Notification's text", pIntent);

        // ставим флаг, чтобы уведомление пропало после нажатия
        //notif.flags |= Notification.FLAG_AUTO_CANCEL;

        // отправляем
        notificationManager.notify(1, notification);
    }*/

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Destroyed", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
