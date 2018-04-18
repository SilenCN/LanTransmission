package cn.silen_dev.lantransmission.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import cn.silen_dev.lantransmission.R;
import cn.silen_dev.lantransmission.model.Equipment;

/**
 * Created by admin on 2018/4/17.
 */

public class LinkDialog extends DialogFragment {
    public Equipment equipment;
    public LinkDialog() {
        super();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
         super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("设备连接");
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.link_dialog,null);
        //布局控件等的安排
        String ipaddressinfo=view.findViewById(R.id.ipaddressinfo).toString();
        String hostinfo=view.findViewById(R.id.hostinfo).toString();
        String webinfo= view.findViewById(R.id.webinfo).toString();

        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("取消",null);

        return builder.create();
    }
}
