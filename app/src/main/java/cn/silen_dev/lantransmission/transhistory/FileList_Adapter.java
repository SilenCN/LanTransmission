package cn.silen_dev.lantransmission.transhistory;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
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

import java.io.FileNotFoundException;
import java.util.List;

import cn.silen_dev.lantransmission.MyApplication;
import cn.silen_dev.lantransmission.R;
import cn.silen_dev.lantransmission.SQLite.TransOperators;
import cn.silen_dev.lantransmission.core.transmission.Transmission;
import cn.silen_dev.lantransmission.core.transmission.ConstValue;
import cn.silen_dev.lantransmission.model.Equipment;

import static cn.silen_dev.lantransmission.core.transmission.ConstValue.RECEIVE;
import static cn.silen_dev.lantransmission.core.transmission.ConstValue.SEND;
import static cn.silen_dev.lantransmission.core.transmission.ConstValue.STATUS_DONE;
import static cn.silen_dev.lantransmission.core.transmission.ConstValue.STATUS_ING;
import static cn.silen_dev.lantransmission.core.transmission.ConstValue.STATUS_NONE;
import static cn.silen_dev.lantransmission.core.transmission.ConstValue.TRANSMISSION_IMAGE;
import static cn.silen_dev.lantransmission.core.transmission.ConstValue.TRANSMISSION_VIDEO;

public class FileList_Adapter extends RecyclerView.Adapter<FileList_Adapter.ViewHolder> {
    private static final String TAG = "FileList_Adapter";
    private Context context;
    private List<Transmission> filesList;
    private OnItemClickListener onItemClickListener;

    private MyApplication myApplication;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public FileList_Adapter(List<Transmission> mfilesList,MyApplication myApplication) {
        filesList = mfilesList;
        this.myApplication=myApplication;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView image;
        TextView fileName;
        TextView fileUser;
        TextView fileStatus;
        ImageView fileLoad;
        CheckBox check;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            image = (ImageView) view.findViewById(R.id.pictures);
            fileName = (TextView) view.findViewById(R.id.file_name);
            fileUser = (TextView) view.findViewById(R.id.file_user);
            fileStatus = (TextView) view.findViewById(R.id.file_status);
            fileLoad = (ImageView) view.findViewById(R.id.file_load);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }

        View view = LayoutInflater.from(context).inflate(R.layout.activity_trans_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
          Transmission transmission = filesList.get(position);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) { //recycler的item相应单击事件
//                if (null!=onItemClickListener){
//                    onItemClickListener.onClick(transmission);
//                }
//            }
//        });
        holder.fileName.setText(transmission.getFileName());
        Equipment equipment=myApplication.findEquipment(transmission.getUserId());
        holder.fileUser.setText(null==equipment?transmission.getUserId()+"":equipment.getName());
        switch (transmission.getStatus()) {
            case STATUS_DONE:
                holder.fileStatus.setText("已完成");
                break;
            case STATUS_ING:
                holder.fileStatus.setText("传输中");
                break;
            case STATUS_NONE:
                holder.fileStatus.setText("未完成");
                break;
            default:
                break;
        }
        switch (transmission.getSr()) {
            case SEND:
                holder.fileLoad.setImageResource(R.mipmap.upload);
                break;
            case RECEIVE:
                holder.fileLoad.setImageResource(R.mipmap.dowload);
                break;
            default:
                break;
        }

        String path = transmission.getSavePath();//获取路径
        Bitmap bmp = null;
        switch (transmission.getType()) {
            case TRANSMISSION_IMAGE:
                bmp = BitmapFactory.decodeFile(path, null);
                holder.image.setImageBitmap(bmp);
                break;
            case TRANSMISSION_VIDEO:
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                try {
                    retriever.setDataSource(path);
                    bmp = retriever.getFrameAtTime();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        retriever.release();
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                }
                holder.image.setImageBitmap(bmp);
                break;
            default:
                break;
        }
    }


    @Override
    public int getItemCount() {
        return filesList.size();
    }

    public interface OnItemClickListener{
        void onClick(Transmission transmission);
    }

}


