package cn.silen_dev.lantransmission.dialog;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import cn.silen_dev.lantransmission.R;
import cn.silen_dev.lantransmission.core.transmission.ConstValue;
import cn.silen_dev.lantransmission.core.transmission.Transmission;
import cn.silen_dev.lantransmission.transhistory.TransInfoActivity;

/**
 * Created by admin on 2018/4/21.
 */

public class NotificationCome {
    private Transmission transmission;

    public NotificationCome(Transmission transmission) {
        this.transmission = transmission;
    }

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
                .setContentIntent(mainPendingIntent);

        switch (transmission.getType()) {
            case ConstValue.TRANSMISSION_CLIPBOARD:
            case ConstValue.TRANSMISSION_TEXT:
                builder.setContentTitle("收到文本")
                        .setContentText(transmission.getMessage());
                break;
            case ConstValue.TRANSMISSION_FILE:
            case ConstValue.TRANSMISSION_IMAGE:
            case ConstValue.TRANSMISSION_VIDEO:
                builder.setContentTitle("文件传输完成")
                        .setContentText(transmission.getMessage());
                break;
        }


        //发送通知
        mNotifyManager.notify(transmission.getId(), builder.build());
    }
}
