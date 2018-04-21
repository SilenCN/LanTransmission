package cn.silen_dev.lantransmission.dialog;

import cn.silen_dev.lantransmission.R;
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
    private DialogInterface.OnClickListener positiveCallback;
    private DialogInterface.OnClickListener negativeCallback;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_transconfirm, null);
        builder.setView(view);
        builder.setTitle("传输文件确认");
        builder.setPositiveButton("确认传输",positiveCallback);
        builder.setNegativeButton("取消传输",negativeCallback);
        return builder.create();
    }
    //重载show()方法
    public void show(DialogInterface.OnClickListener positiveCallback,
                     DialogInterface.OnClickListener negativeCallback,
                     FragmentManager fragmentManager) {
        this.positiveCallback = positiveCallback;
        this.negativeCallback = negativeCallback;
        show(fragmentManager, "TransConfirmDialogFragment");
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