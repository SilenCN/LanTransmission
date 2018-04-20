
package cn.silen_dev.lantransmission.transhistory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cn.silen_dev.lantransmission.R;
import cn.silen_dev.lantransmission.core.transmission.Transmission;


public class TransInfoActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private List<Transmission> transList_all = new ArrayList<>();
    private List<Transmission> transList_pic = new ArrayList<>();
    private List<Transmission> transList_vedio = new ArrayList<>();
    private List<Transmission> transList_file = new ArrayList<>();
    private List<Transmission> transList_device = new ArrayList<>();

    private List<Transmission> transList=new ArrayList<>();

    private Toolbar toolbar;

    private FileList_Adapter adapter;
    private GridLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RadioGroup rg_tab_bar;
    private RadioButton rb_all;
    private RadioButton rb_pic;
    private RadioButton rb_vedio;
    private RadioButton rb_file;
    private RadioButton rb_device;
    private CheckBox cb;

    private Transmission[] files={
            new Transmission("000000000",0,0),
            new Transmission("111111111",1,1),
            new Transmission("222222222",2,2),
            new Transmission("333333333",3,3),
            new Transmission("444444444",3,3),
            new Transmission("555555555",3,3),
    };
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_info);
        setToolbar();

        rg_tab_bar=(RadioGroup)findViewById(R.id.rg_tab_bar);
        rg_tab_bar.setOnCheckedChangeListener(this);
        //获取第一个单选按钮
        rb_all=(RadioButton)findViewById(R.id.rb_all);
        rb_all.setChecked(true);
        rb_pic=(RadioButton)findViewById(R.id.rb_pic);
        rb_vedio=(RadioButton)findViewById(R.id.rb_vedio);
        rb_file=(RadioButton)findViewById(R.id.rb_file);
        rb_device=(RadioButton)findViewById(R.id.rb_device);

        //初始化传输记录数据
        init_all(new ArrayList<Transmission>(Arrays.asList(files)));
        init_pic();
//        init_vedio();
        init_file();
//        init_device();
    }

    //为每个button设置点击监听事件
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        layoutManager=new GridLayoutManager(this,1);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        switch (checkedId){
            case R.id.rb_all:
                init_all(transList);
//                adapter=new FileList_Adapter(transList_all);
//                recyclerView.setAdapter(adapter);
                break;
            case R.id.rb_pic:
                adapter=new FileList_Adapter(transList_pic);
                recyclerView.setAdapter(adapter);
                break;
            case R.id.rb_vedio:
                adapter=new FileList_Adapter(transList_vedio);
                recyclerView.setAdapter(adapter);
                break;
            case R.id.rb_file:
                adapter=new FileList_Adapter(transList_file);
                recyclerView.setAdapter(adapter);
                break;
            case R.id.rb_device:
                adapter=new FileList_Adapter(transList_device);
                recyclerView.setAdapter(adapter);
                break;
        }
    }
    public void init_all(List<Transmission> transList_all){
        adapter=new FileList_Adapter(transList_all);
        recyclerView.setAdapter(adapter);
//        transList_all.clear();
//        int len=transList_all.size();
//        for(int i=0;i<len;i++)
//            transList_all.add(files[i]);
    }
    public void init_pic() {
        transList_pic.clear();
        for(int i=0;i<20;i++){
            Random random = new Random();
            int index=random.nextInt(files.length);
            transList_pic.add(files[index]);
        }
    }
    public void init_vedio() {
        transList_vedio.clear();
        for(int i=0;i<20;i++){
            Random random = new Random();
            int index=random.nextInt(files.length);
            transList_vedio.add(files[index]);
        }
    }
    public void init_file() {
        transList_file.clear();
        for(int i=0;i<20;i++){
            Random random = new Random();
            int index=random.nextInt(files.length);
            transList_file.add(files[index]);
        }
    }
    public void init_device() {
        transList_device.clear();
        for(int i=0;i<20;i++){
            Random random = new Random();
            int index=random.nextInt(files.length);
            transList_device.add(files[index]);
        }
    }


    public void setToolbar(){
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
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            //TODO:其他事件监听
        }
        return super.onOptionsItemSelected(item);
    }
}

