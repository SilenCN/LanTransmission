
package cn.silen_dev.lantransmission.transhistory;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cn.silen_dev.lantransmission.R;
import cn.silen_dev.lantransmission.SQLite.EquipOperators;
import cn.silen_dev.lantransmission.SQLite.TransOperators;
import cn.silen_dev.lantransmission.core.transmission.Client.LanClient;
import cn.silen_dev.lantransmission.core.transmission.ConstValue;
import cn.silen_dev.lantransmission.core.transmission.Transmission;
import cn.silen_dev.lantransmission.dialog.TextDialog;
import cn.silen_dev.lantransmission.model.Equipment;
import cn.silen_dev.lantransmission.selectconn.EquipmentAdapter;
//import cn.silen_dev.lantransmission.transhistory.WordList_Adapter.OnItemClickListener;


public class TransInfoActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private List<Transmission> transList_pic = new ArrayList<>();
    private List<Transmission> transList_vedio = new ArrayList<>();
    private List<Transmission> transList_word = new ArrayList<>();
    private List<Transmission> transList_file = new ArrayList<>();
    private List<Equipment> transList_device = new ArrayList<>();

    private Toolbar toolbar;

    private FileList_Adapter tAdapter;
    private WordList_Adapter wAdapter;
    private EquipmentAdapter eAdapter;

private static final String TAG = "TransInfoActivity";

    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RadioGroup rg_tab_bar;
    private RadioButton rb_pic;
    private RadioButton rb_vedio;
    private RadioButton rb_file;
    private RadioButton rb_word;
    private RadioButton rb_device;

    private TransOperators transOperators;
    private EquipOperators equipOperators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_info);
        setToolbar();

        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rg_tab_bar.setOnCheckedChangeListener(this);

        rb_pic = (RadioButton) findViewById(R.id.rb_pic);
        rb_pic.setChecked(true);
        rb_vedio = (RadioButton) findViewById(R.id.rb_vedio);
        rb_file = (RadioButton) findViewById(R.id.rb_file);
        rb_word=(RadioButton)findViewById(R.id.rb_word);
        rb_device = (RadioButton) findViewById(R.id.rb_device);

        transOperators=new TransOperators(this);
        equipOperators=new EquipOperators(this);

        transList_word=transOperators.getAllTrans(ConstValue.TRANSMISSION_TEXT);
        transList_pic=transOperators.getAllTrans(ConstValue.TRANSMISSION_IMAGE);
        transList_device=equipOperators.getAllEquipment();
        transList_file=transOperators.getAllTrans(ConstValue.TRANSMISSION_FILE);
        transList_vedio=transOperators.getAllTrans(ConstValue.TRANSMISSION_VIDEO);


        for(Transmission transmission:transOperators.getAllTrans()){
            System.out.println(new Gson().toJson(transmission));
        }
//        Log.d(TAG, transList_word.get(0).getMessage());
//        Log.d(TAG, String.valueOf(transList_word.get(0).getUserId()));
    }

    //为每个button设置点击监听事件
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        switch (checkedId) {
            case R.id.rb_pic:
                init_pic(transList_pic);
                break;
            case R.id.rb_vedio:
                init_vedio(transList_vedio);
                break;
            case R.id.rb_file:
                init_file(transList_file);
                break;
            case R.id.rb_word:
                init_word(transList_word);
                break;
            case R.id.rb_device:
                init_device(transList_device);
                break;
            default:
                break;
        }
    }

    public void init_pic(List<Transmission> transList_pic) {
        tAdapter = new FileList_Adapter(transList_pic);
        recyclerView.setAdapter(tAdapter);
    }

    public void init_vedio(List<Transmission> transList_vedio) {
        tAdapter = new FileList_Adapter(transList_vedio);
        recyclerView.setAdapter(tAdapter);
    }

    public void init_file(List<Transmission> transList_file) {
        wAdapter = new WordList_Adapter(transList_file);
        recyclerView.setAdapter(wAdapter);
    }

    public void init_word(List<Transmission> transList_word) {
        wAdapter = new WordList_Adapter(transList_word);
        recyclerView.setAdapter(wAdapter);
    }
    public void init_device(List<Equipment> transList_device) {
        eAdapter = new EquipmentAdapter(transList_device);
        recyclerView.setAdapter(eAdapter);
    }

    public void setToolbar() {
        setSupportActionBar((android.support.v7.widget.Toolbar) findViewById(R.id.toolbar));
        try {
            getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_HOME_AS_UP, android.support.v7.app.ActionBar.DISPLAY_HOME_AS_UP);
        } catch (Exception e) {
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            //TODO:其他事件监听
        }
        return super.onOptionsItemSelected(item);
    }
}

