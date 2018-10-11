package com.banzhi.album.ui.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.banzhi.album.R;
import com.banzhi.album.bean.FileBean;
import com.banzhi.album.bean.FolderBean;
import com.banzhi.album.interfaces.OnItemClickListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/10/11.
 * @desciption :
 * @version :
 * </pre>
 */

public class AlbumFolderAdapter extends RecyclerView.Adapter<AlbumFolderAdapter.FolderViewHolder> {
    ArrayList<FolderBean> folderBeans;
    Context mContext;
    LayoutInflater inflater;

    int oldPosition;
    OnItemClickListener onItemClickListener;

    public AlbumFolderAdapter(Context context, ArrayList<FolderBean> folderBeans) {
        this.folderBeans = folderBeans;
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public AlbumFolderAdapter.FolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FolderViewHolder(inflater.inflate(R.layout.item_dialog_folder, parent, false),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(v, position);
                        }
                        FolderBean folderBean = folderBeans.get(position);
                        if (!folderBean.isChecked()) {
                            folderBean.setChecked(true);
                            folderBeans.get(oldPosition).setChecked(false);
                            notifyItemChanged(oldPosition);
                            notifyItemChanged(position);
                            oldPosition = position;
                        }
                    }
                });
    }

    @Override
    public void onBindViewHolder(AlbumFolderAdapter.FolderViewHolder holder, int position) {
        holder.setData(folderBeans.get(position));
    }

    @Override
    public int getItemCount() {
        return folderBeans == null ? 0 : folderBeans.size();
    }

    class FolderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivCover;
        TextView mTitle;
        AppCompatCheckBox checkBox;
        OnItemClickListener mOnItemClick;

        public FolderViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.mOnItemClick = onItemClickListener;
            ivCover = itemView.findViewById(R.id.iv_cover);
            mTitle = itemView.findViewById(R.id.tv_title);
            checkBox = itemView.findViewById(R.id.checkbox);
            itemView.setOnClickListener(this);
        }

        public void setData(FolderBean folderBean) {
            ArrayList<FileBean> fileBeans = folderBean.getFileBeans();
            mTitle.setText(folderBean.getName() + "(" + fileBeans.size() + ")");
            Glide.with(ivCover.getContext()).load(fileBeans.get(0).getThumbPath()).into(ivCover);
            checkBox.setChecked(folderBean.isChecked());
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClick != null) {
                mOnItemClick.onItemClick(v, getAdapterPosition());
            }
        }
    }
}
