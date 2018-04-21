package cn.silen_dev.lantransmission.dialog;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import cn.silen_dev.lantransmission.MainActivity;
import cn.silen_dev.lantransmission.R;
import cn.silen_dev.lantransmission.transhistory.TransInfoActivity;

/**
 * Created by admin on 2018/4/21.
 */

public class NotificationCome {
    public NotificationCome(){}
    public void sendSimplestNotificationWithAction(Context context) {
        NotificationManager mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //获取PendingIntent
        Intent mainIntent = new Intent(context, TransInfoActivity.class);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //创建 Notification.Builder 对象
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.get_file)
                //点击通知后自动清除
                .setAutoCancel(true)
                .setContentTitle("文件传输完毕")
                .setContentText("点击查看")
                .setContentIntent(mainPendingIntent);
        //发送通知
        mNotifyManager.notify(3, builder.build());
    }
}
