package cn.silen_dev.lantransmission.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import cn.silen_dev.lantransmission.R;

/**
 * Created by admin on 2018/4/17.
 */

public class LinkOtherEquipmentDialog extends DialogFragment {
        public LinkOtherEquipmentDialog(){super();}
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            super.onCreateDialog(savedInstanceState);
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            builder.setTitle("连接其他设备");
            View view= LayoutInflater.from(getActivity()).inflate(R.layout.link_other_equipment_dialog_layut,null);
            //布局控件等的安排
            EditText editText=(EditText)view.findViewById(R.id.editText);
            editText.setText("127.0.0.1");

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
