package cn.silen_dev.lantransmission.transhistory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.silen_dev.lantransmission.R;
import cn.silen_dev.lantransmission.core.transmission.Transmission;
import cn.silen_dev.lantransmission.core.transmission.ConstValue;

import static cn.silen_dev.lantransmission.core.transmission.ConstValue.RECEIVE;
import static cn.silen_dev.lantransmission.core.transmission.ConstValue.SEND;
import static cn.silen_dev.lantransmission.core.transmission.ConstValue.STATUS_DONE;
import static cn.silen_dev.lantransmission.core.transmission.ConstValue.STATUS_ING;
import static cn.silen_dev.lantransmission.core.transmission.ConstValue.STATUS_NONE;

public class FileList_Adapter extends RecyclerView.Adapter<FileList_Adapter.ViewHolder>
{
    private Context context;
    private List<Transmission> filesList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        ImageView image;
        TextView fileName;
        TextView fileUser;
        TextView fileStatus;
        ImageView fileLoad;
        CheckBox check;

        public ViewHolder(View view)
        {
            super(view);
            cardView =(CardView) view;
            image=(ImageView) view.findViewById(R.id.pictures);
            fileName=(TextView) view.findViewById(R.id.file_name);
            fileUser=(TextView) view.findViewById(R.id.file_user);
            fileStatus=(TextView) view.findViewById(R.id.file_status);
            fileLoad=(ImageView)view.findViewById(R.id.file_load);
        }
    }
    public FileList_Adapter(List<Transmission> mfilesList){
        filesList=mfilesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context == null){
            context=parent.getContext();
        }
        View view =LayoutInflater.from(context).inflate(R.layout.activity_trans_item,
                parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Transmission transmission=filesList.get(position);
        holder.fileName.setText((transmission.getFileName()).toString());
        holder.fileUser.setText(String.valueOf(transmission.getUserId()));
        switch (transmission.getStatus()){
            case STATUS_DONE:
                holder.fileStatus.setText("已完成");
                break;
            case STATUS_ING:
                holder.fileStatus.setText("传输中");
                break;
            case STATUS_NONE:
                holder.fileStatus.setText("未完成");
                break;
        }
        switch (transmission.getSr()){
            case SEND:
                holder.fileLoad.setImageResource(R.mipmap.share);
                break;
            case RECEIVE:
                holder.fileLoad.setImageResource(R.mipmap.download);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return filesList.size();
    }


}


