package cn.silen_dev.lantransmission.selectconn;

import cn.silen_dev.lantransmission.MyApplication;
import cn.silen_dev.lantransmission.R;
import cn.silen_dev.lantransmission.SQLite.TransOperators;
import cn.silen_dev.lantransmission.core.transmission.Client.LanClient;
import cn.silen_dev.lantransmission.core.transmission.Transmission;
import cn.silen_dev.lantransmission.model.Equipment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import static cn.silen_dev.lantransmission.R.color.colorPrimary;

public class SelectConnectionActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefresh;
    private List<Equipment> equipmentList;
    MyItemDecoration myItemDecoration;
    MyApplication myApplication;
    EquipmentAdapter adapter;
    public  Transmission transmission;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_connection);
        setToolbar();

        transmission=(Transmission) getIntent().getSerializableExtra("Transmission");

        myApplication=(MyApplication)getApplication();
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeColors(colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                initEquipment();
                adapter.notifyDataSetChanged();
                swipeRefresh.setRefreshing(false);
            }
        });
        initEquipment();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.equipment_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new EquipmentAdapter(equipmentList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyItemDecoration());

        adapter.setOnItemClickListener(new EquipmentAdapter.OnItemClickListener() {
            @Override
            public void onClick(Equipment equipment) {
                TransOperators transOperators=new TransOperators(SelectConnectionActivity.this);
                transmission.setUserId(equipment.getId());
                transmission.setId(transOperators.insertTransWithReturnId(transmission));
                transmission.setTime(System.currentTimeMillis());
                LanClient lanClient=new LanClient(equipment.getAddress());
                lanClient.sendTransmisstion(transmission,((MyApplication) getApplication()).getMyEquipmentInfo());
            }
        });
    }

    private void refreshEquipment() {
        //更新设备信息
    }

    private void initEquipment(){
        equipmentList=new ArrayList<>();
        equipmentList=myApplication.getConnectEquipments();

    }

   /* private void initEquipment() {
        equipmentList = new ArrayList<>();
        Equipment equipment;
        for (int i = 0; i < 16; i++) {
            if(i%2==0){
                equipment = new Equipment("小米手机", "192.168.10.3", 0);
                equipment=new Equipment();
                equipment.setAddress("");
            }
            else{
                equipment = new Equipment("电脑", "192.168.10.3", 1);
            }
            equipmentList.add(equipment);
        }
    }
*/
    class MyItemDecoration extends RecyclerView.ItemDecoration {
        private Paint mPaint;
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(50, 0, 50, 0);
        }
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

