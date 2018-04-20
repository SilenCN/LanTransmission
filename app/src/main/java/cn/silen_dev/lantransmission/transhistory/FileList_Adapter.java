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


public class FileList_Adapter extends RecyclerView.Adapter<FileList_Adapter.ViewHolder>
{
    private Context context;
    private List<Transmission> filesList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        ImageView imageView;
        TextView fileName;
        TextView fileUser;
        TextView fileStatus;
        CheckBox check;

        public ViewHolder(View view)
        {
            super(view);
            cardView =(CardView) view;
            fileName=(TextView) view.findViewById(R.id.file_name);
            fileUser=(TextView) view.findViewById(R.id.file_user);
            fileStatus=(TextView) view.findViewById(R.id.file_status);
//            check=(CheckBox) view.findViewById(R.id.check);
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
        holder.fileStatus.setText(String.valueOf(transmission.getStatus()));
//        holder.imageView.setImageResource();//暂时不设置图片
    }

    @Override
    public int getItemCount() {
        return filesList.size();
    }


}


