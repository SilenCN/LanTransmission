package cn.silen_dev.lantransmission.selectconn;

import cn.silen_dev.lantransmission.R;
import cn.silen_dev.lantransmission.model.Equipment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import static cn.silen_dev.lantransmission.R.color.colorPrimary;

public class SelectConnectionActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefresh;
    private List<Equipment> equipmentList;
    MyItemDecoration myItemDecoration;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_connection);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeColors(colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                //  refreshEquipment();//更新设备信息
            }
        });
//        initEquipment();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.equipment_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        EquipmentAdapter adapter = new EquipmentAdapter(equipmentList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyItemDecoration());
    }

    private void refreshEquipment() {
        //更新设备信息
    }

//    private void initEquipment() {
//        equipmentList = new ArrayList<>();
//        Equipment equipment;
//        for (int i = 0; i < 16; i++) {
//            if(i%2==0){
//                equipment = new Equipment("小米手机", "192.168.10.3", 0);
//            }
//            else{
//                equipment = new Equipment("电脑", "192.168.10.3", 1);
//            }
//            equipmentList.add(equipment);
//        }
//    }

    class MyItemDecoration extends RecyclerView.ItemDecoration {
        private Paint mPaint;
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(50, 0, 50, 0);
        }
    }
}

