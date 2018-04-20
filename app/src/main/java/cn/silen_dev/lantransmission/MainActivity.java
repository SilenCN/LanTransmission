package cn.silen_dev.lantransmission;
import cn.silen_dev.lantransmission.core.scan.Server.ScannerServer;
import cn.silen_dev.lantransmission.dialog.*;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.io.File;
import java.net.SocketException;

import cn.silen_dev.lantransmission.fileBrowser.FileBrowserActivity;
import cn.silen_dev.lantransmission.fileBrowser.OnFileBrowserResultListener;
import cn.silen_dev.lantransmission.settings.SettingActivity;
import cn.silen_dev.lantransmission.transhistory.TransInfoActivity;
import cn.silen_dev.lantransmission.widget.RandomTextView.RandomTextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RandomTextView randomTextView;
    InputWordDialog inputWordDialog;

    FloatingActionButton[] floatingActionButtons=new FloatingActionButton[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        randomTextView=findViewById(R.id.random_textview);
        randomTextView.setOnRippleViewClickListener(new RandomTextView.OnRippleViewClickListener() {
            @Override
            public void onRippleViewClicked(View view) {
                System.out.println(((TextView)view).getText());
            }
        });

        //startActivity(new Intent().setClass(this,TestActivity.class));
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                randomTextView.addKeyWord("肖丁剑");
                randomTextView.addKeyWord("李新宇");
                randomTextView.addKeyWord("彭艳秋");
                randomTextView.addKeyWord("杨志坤");
                randomTextView.addKeyWord("韩婷婷");
                randomTextView.addKeyWord("习近平6");
                randomTextView.addKeyWord("彭丽媛7");
                randomTextView.addKeyWord("习近平8");
                randomTextView.addKeyWord("彭丽媛9");
                randomTextView.addKeyWord("习近平0");
                randomTextView.show();
            }
        }, 2 * 1000);

        findFloatingActionButton();
        requestPermission();

        ScannerServer scannerServer= null;
        try {
            scannerServer = new ScannerServer();
            scannerServer.start();
            scannerServer.scan();
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }



    private void findFloatingActionButton(){
        floatingActionButtons[0]=findViewById(R.id.fab_item_file);
        floatingActionButtons[1]=findViewById(R.id.fab_item_text);
        floatingActionButtons[2]=findViewById(R.id.fab_item_video);
        floatingActionButtons[3]=findViewById(R.id.fab_item_image);
        floatingActionButtons[4]=findViewById(R.id.fab_item_clipboard);
        for (int i=0;i<floatingActionButtons.length;i++){
            floatingActionButtons[i].setOnClickListener(new FABClickListener());
        }
    }


    private class FABClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.fab_item_clipboard:
                    inputWordDialog=new InputWordDialog(GetClipBoardContent());
                    inputWordDialog.show(getSupportFragmentManager(),null);
                    break;
                case R.id.fab_item_text:
                    inputWordDialog=new InputWordDialog();
                    inputWordDialog.show(getSupportFragmentManager(),null);
                    break;
                case R.id.fab_item_video:
                    break;
                case R.id.fab_item_image:
                    break;
                case R.id.fab_item_file:
                    OnFileBrowserResultListener onFileBrowserResultListener=new OnFileBrowserResultListener() {
                        @Override
                        public void selectFile(File file) {
                            Toast.makeText(MainActivity.this,file.getAbsolutePath(),Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void nothing() {
                            Toast.makeText(MainActivity.this,"Nothing selected!",Toast.LENGTH_SHORT).show();
                        }
                    };
                    FileBrowserActivity.onFileBrowserResultListener=onFileBrowserResultListener;
                    startActivity(new Intent(MainActivity.this, FileBrowserActivity.class));
                    break;

            }
        }
    }

    //剪切板内容
    ClipboardManager clipboardManager;
    static String tempStr;
    public String GetClipBoardContent()
    {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                clipboardManager=(ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                if(clipboardManager==null)
                {
                    Log.i("cp", "clipboardManager==null");

                }
                if(clipboardManager.getText()!=null)
                {
                    tempStr=clipboardManager.getText().toString();
                }
            }
        });
        return tempStr;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_scan_qrcode:
                LinkDialog linkDialog=new LinkDialog();
                linkDialog.show(getSupportFragmentManager(),null);
                break;
            case R.id.action_transmission_manager:
                startActivity(new Intent(MainActivity.this,TransInfoActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_settings:
                startActivity(new Intent(this, SettingActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void requestPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            // 没有权限。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
                // 用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。
            } else {
                // 申请授权。
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, 1);
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 没有权限。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            } else {
                // 申请授权。
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 没有权限。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);

            } else {
                // 申请授权。
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
            }
        }

    }
}
