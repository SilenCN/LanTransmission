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
import android.widget.TextView;

import static cn.silen_dev.lantransmission.core.transmission.ConstValue.TRANSMISSION_FILE;
import static cn.silen_dev.lantransmission.core.transmission.ConstValue.TRANSMISSION_IMAGE;
import static cn.silen_dev.lantransmission.core.transmission.ConstValue.TRANSMISSION_TEXT;
import static cn.silen_dev.lantransmission.core.transmission.ConstValue.TRANSMISSION_VIDEO;

/**
 * Created by 杨志坤 on 2018/4/18.
 */

public class TransConfirmDialogFragment extends DialogFragment {

    private Transmission transmission;
    private Equipment equipment;
    private TextView fileName;
    private TextView fileType;
    private TextView sender;
    private TextView sendIp;
    private TextView sendTime;
    private TextView catalogue;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_transconfirm, null);
        builder.setView(view);
        builder.setTitle("传输文件确认");
        fileName = (TextView) view.findViewById(R.id.text_FileName);
        fileType = (TextView) view.findViewById(R.id.text_FileType);
        sender = (TextView) view.findViewById(R.id.text_Sender);
        sendIp = (TextView) view.findViewById(R.id.text_SendIP);
        sendTime = (TextView) view.findViewById(R.id.text_SendTime);
        catalogue = (TextView) view.findViewById(R.id.text_Catalogue);
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

    public void show(Transmission transmission, Equipment equipment, FragmentManager manager) {
        this.transmission = transmission;
        this.equipment = equipment;
        fileName.setText(transmission.getFileName());
        switch (transmission.getType()) {
            case TRANSMISSION_FILE:
                fileType.setText("文件");
            case TRANSMISSION_IMAGE:
                fileType.setText("图片");
            case TRANSMISSION_VIDEO:
                fileType.setText("视频");
            case TRANSMISSION_TEXT:
                fileType.setText("文本");
        }
        sender.setText(equipment.getName());
        sendIp.setText(transmission.getSendPath());
        sendTime.setText(String.valueOf(transmission.getTime()));
        super.show(manager, "TransConfirmDialogFragment");
    }
}
    /**
     * 调用显示ConfirmDialog
     * TransConfirmDialogFragment confirmDialogFragment=new TransConfirmDialogFragment();
     * confirmDialogFragment.show(transmission,equipment,getFragmentManager());
     */


