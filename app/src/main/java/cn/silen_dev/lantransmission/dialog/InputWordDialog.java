package cn.silen_dev.lantransmission.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import cn.silen_dev.lantransmission.R;


/**
 * Created by admin on 2018/4/18.
 */

public class InputWordDialog extends DialogFragment {
    public InputWordDialog(){
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("请输入您要发送的文字");
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.input_word_dialog,null);
        //布局控件等的安排
        EditText editText=((EditText)view.findViewById(R.id.inputareaofword));
        editText.setText("我爱安卓！");
        builder.setView(view);
        builder.setPositiveButton("发送", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("取消",null);

        return builder.create();
    }
}
