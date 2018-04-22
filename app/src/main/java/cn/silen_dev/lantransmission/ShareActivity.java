package cn.silen_dev.lantransmission;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;
import java.net.URLDecoder;

import cn.silen_dev.lantransmission.core.transmission.ConstValue;
import cn.silen_dev.lantransmission.core.transmission.Transmission;
import cn.silen_dev.lantransmission.fileBrowser.Utils;
import cn.silen_dev.lantransmission.selectconn.SelectConnectionActivity;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String action = intent.getAction();
        // 判断Intent是否是“分享”功能(Share Via)
        if (Intent.ACTION_SEND.equals(action))
        {
            if (extras.containsKey(Intent.EXTRA_STREAM))
            {
                try
                {
                    // 获取资源路径Uri
                    Uri uri = (Uri) extras.getParcelable(Intent.EXTRA_STREAM);
                    String path=URLDecoder.decode(uri.toString(),"UTF-8");
                    if (path.startsWith("file://")){
                        path=path.substring(7);
                        transmissionFile(ConstValue.TRANSMISSION_FILE,new File(path));

                    }else{
                        Toast.makeText(ShareActivity.this,"不支持的操作",Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }
        finish();
    }

    private void transmissionFile(int type,File file){
        Transmission transmission=new Transmission();
        transmission.setSendPath(file.getAbsolutePath());
        transmission.setFileName(file.getName());
        transmission.setMessage(transmission.getFileName());
        transmission.setType(type);
        try {
            transmission.setLength(Utils.getFileSize(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        transmission.setSr(ConstValue.SEND);
        Intent intent=new Intent();
        intent.setClass(ShareActivity.this, SelectConnectionActivity.class);
        intent.putExtra("Transmission",transmission);
        startActivity(intent);
    }
}
