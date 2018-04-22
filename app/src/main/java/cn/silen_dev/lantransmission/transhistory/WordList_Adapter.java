package cn.silen_dev.lantransmission.transhistory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.silen_dev.lantransmission.R;
import cn.silen_dev.lantransmission.core.transmission.Transmission;

import static cn.silen_dev.lantransmission.core.transmission.ConstValue.RECEIVE;
import static cn.silen_dev.lantransmission.core.transmission.ConstValue.SEND;

/**
 * Created by admin on 2018/4/20.
 */

public class WordList_Adapter extends RecyclerView.Adapter<WordList_Adapter.ViewHolder> {
    private Context context;
    private List<Transmission> wordList;
    private static final String TAG = "WordList_Adapter";
    static class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        ImageView imageView;
        TextView trans_word;
        TextView trans_word_user;
        TextView fileStatus;
        ImageView load;

        public ViewHolder(View view)
        {
            super(view);
            cardView =(CardView) view;
            trans_word=(TextView) view.findViewById(R.id.trans_word);
            trans_word_user=(TextView) view.findViewById(R.id.trans_word_user);
            load=(ImageView)view.findViewById(R.id.load);
            //fileStatus=(TextView) view.findViewById(R.id.file_status);
//            check=(CheckBox) view.findViewById(R.id.check);
        }
    }
    public WordList_Adapter(List<Transmission> mwordlist){
        wordList=mwordlist;
    }

    @NonNull
    @Override

    public WordList_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context == null){
            context=parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.trans_info_word,
                parent,false);
        return new WordList_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordList_Adapter.ViewHolder holder, int position) {
        Transmission transmission=wordList.get(position);
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
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }
}
