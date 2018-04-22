package cn.silen_dev.lantransmission.transhistory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.silen_dev.lantransmission.R;
import cn.silen_dev.lantransmission.core.transmission.Transmission;

import static cn.silen_dev.lantransmission.core.transmission.ConstValue.RECEIVE;
import static cn.silen_dev.lantransmission.core.transmission.ConstValue.SEND;

/**
 * Created by HTT on 2018/4/22.
 */

public class File_Adapter extends RecyclerView.Adapter<File_Adapter.ViewHolder>{
    private Context context;
    private List<Transmission> fileList;
//    private static final String TAG = "WordList_Adapter";
    static class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        ImageView imageView;
        TextView text_word;
        TextView text_user;
        TextView fileStatus;
        ImageView load;

        public ViewHolder(View view)
        {
            super(view);
            cardView =(CardView) view;
            text_word=(TextView) view.findViewById(R.id.text_word);
            text_user=(TextView) view.findViewById(R.id.text_user);
            //fileStatus=(TextView) view.findViewById(R.id.file_status);
//            check=(CheckBox) view.findViewById(R.id.check);
        }
    }
    public File_Adapter(List<Transmission> mwordlist){
        fileList=mwordlist;
    }

    @NonNull
    @Override

    public File_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context == null){
            context=parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.trans_info_word,
                parent,false);
        return new File_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transmission transmission=fileList.get(position);
        holder.text_word.setText(transmission.getMessage());
        holder.text_user.setText(String.valueOf(transmission.getUserId()));
        switch (transmission.getSr()){
            case SEND:
                holder.load.setImageResource(R.mipmap.upload);
                break;
            case RECEIVE:
                holder.load.setImageResource(R.mipmap.dowload);
                break;
            default:
                break;
        }
    }
/*
    @Override
    public void onBindViewHolder(@NonNull WordList_Adapter.ViewHolder holder, int position) {
        Transmission transmission=fileList.get(position);
        holder.trans_word.setText(transmission.getMessage());
        holder.trans_word_user.setText(String.valueOf(transmission.getUserId()));
        switch (transmission.getSr()){
            case SEND:
                holder.load.setImageResource(R.mipmap.upload);
                break;
            case RECEIVE:
                holder.load.setImageResource(R.mipmap.dowload);
                break;
            default:
                break;
        }
    }*/

    @Override
    public int getItemCount() {
        return fileList.size();
    }
}