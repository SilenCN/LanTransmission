package cn.silen_dev.lantransmission.dialog;

import cn.silen_dev.lantransmission.R;
import cn.silen_dev.lantransmission.core.transmission.Transmission;
import cn.silen_dev.lantransmission.model.Equipment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by 杨志坤 on 2018/4/18.
 */

public class TransConfirmDialogFragment  extends DialogFragment {
    private Transmission transmission;
    private Equipment equipment;
    public TransConfirmDialogFragment(Transmission transmission,Equipment equipment) {
        this.transmission=transmission;
        this.equipment=equipment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_transconfirm, null);
        builder.setView(view);
        builder.setTitle("传输文件确认");

        //TODO:获取各个控件ID，根据transmission和equipment初始化

        builder.setPositiveButton("确认传输", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("取消传输", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}

/**这段代码放入调入该TransConfirmDialog的Activity中
 * 用于显示该对话框，并实现对话框的确认和取消功能。
 *
 public void showTransConfirmDialog(){
 TransConfirmDialogFragment confirmDialogFragment = new TransConfirmDialogFragment();
 confirmDialogFragment.show(new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {}
},new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {}
}, getFragmentManager());
 }
 */