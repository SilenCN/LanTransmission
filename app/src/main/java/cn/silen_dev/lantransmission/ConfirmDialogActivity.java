package cn.silen_dev.lantransmission;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.silen_dev.lantransmission.core.transmission.Transmission;
import cn.silen_dev.lantransmission.dialog.TransConfirmDialogFragment;
import cn.silen_dev.lantransmission.model.Equipment;

public class ConfirmDialogActivity extends AppCompatActivity {

    public static TransConfirmDialogFragment.OnTransmissionConfirmResultListener onTransmissionConfirmResultListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Transmission transmission = (Transmission) intent.getSerializableExtra("transmission");
        Equipment equipment = (Equipment) intent.getSerializableExtra("equipment");

        TransConfirmDialogFragment transConfirmDialogFragment = new TransConfirmDialogFragment(transmission, equipment);
        transConfirmDialogFragment.show(getSupportFragmentManager(), null);
        transConfirmDialogFragment.setOnTransmissionConfirmResultListener(onTransmissionConfirmResultListener);
    }
}
