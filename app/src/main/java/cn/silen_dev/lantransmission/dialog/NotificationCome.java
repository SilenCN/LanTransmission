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
        NotificationUtils notificationUtils=new NotificationUtils(context);

        switch (transmission.getType()) {
            case ConstValue.TRANSMISSION_CLIPBOARD:
            case ConstValue.TRANSMISSION_TEXT:
                notificationUtils.sendNotification("收到文本",transmission.getMessage());
                break;
            case ConstValue.TRANSMISSION_FILE:
            case ConstValue.TRANSMISSION_IMAGE:
            case ConstValue.TRANSMISSION_VIDEO:
                notificationUtils.sendNotification("文件传输完成",transmission.getMessage());
                break;
        }

    }
}
