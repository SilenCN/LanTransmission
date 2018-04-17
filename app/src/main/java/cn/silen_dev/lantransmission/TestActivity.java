package cn.silen_dev.lantransmission;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class TestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        setToolbar();
        //TODO：其他操作
    }

    public void setToolbar(){
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        try {
            getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_HOME_AS_UP, android.support.v7.app.ActionBar.DISPLAY_HOME_AS_UP);
        } catch (Exception e) {
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //TODO:左上角返回监听操作
                break;
                //TODO:其他事件监听
        }
        return super.onOptionsItemSelected(item);
    }
}
