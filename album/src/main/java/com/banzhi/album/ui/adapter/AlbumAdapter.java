package com.banzhi.album.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.banzhi.album.R;
import com.banzhi.album.bean.FileBean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/9/30.
 * @desciption :
 * @version :
 * </pre>
 */

public class AlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_BUTTON = 1;
    private static final int TYPE_IMAGE = 2;
    private static final int TYPE_VIDEO = 3;
    private static final int TYPE_AUDIO = 4;

    List<FileBean> fileBeans = new ArrayList<>();
    boolean hasCamera;
    Context mContext;
    LayoutInflater mInflater;
    int itemSize;

    public AlbumAdapter(Context context, boolean hasCamera, int itemSize) {
        this.mContext = context;
        this.hasCamera = hasCamera;
        this.itemSize = itemSize;
        mInflater = LayoutInflater.from(mContext);

    }
    public void notifyDataSetChanged(List<FileBean> albumFiles) {
        this.fileBeans = albumFiles;
        super.notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        int camera = hasCamera ? 1 : 0;
        return fileBeans == null ? camera : fileBeans.size() + camera;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return hasCamera ? TYPE_BUTTON : getItemType(fileBeans.get(position));
        } else {
            position = hasCamera ? position - 1 : position;
            FileBean albumFile = fileBeans.get(position);
            return getItemType(albumFile);
        }
    }

    private int getItemType(FileBean albumFile) {
        return albumFile.getMediaType() == FileBean.TYPE_IMAGE ?
                TYPE_IMAGE :
                (albumFile.getMediaType() == FileBean.TYPE_VIDEO ?
                        TYPE_VIDEO :
                        TYPE_AUDIO);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BUTTON) {
            return new ButtonViewHolder(mInflater.inflate(R.layout.item_content_button, parent, false),
                    false, itemSize);
        } else if (viewType == TYPE_IMAGE) {
            return new ImageViewHolder(mInflater.inflate(R.layout.item_content_image, parent, false),
                    false, itemSize);
        } else if (viewType == TYPE_VIDEO) {
            return new VideoViewHolder(mInflater.inflate(R.layout.item_content_video, parent, false),
                    false, itemSize);
        } else if (viewType == TYPE_AUDIO) {
            return new AudioViewHolder(mInflater.inflate(R.layout.item_content_audio, parent, false),
                    false, itemSize);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case TYPE_BUTTON:

                break;
            case TYPE_IMAGE:
                ImageViewHolder imageHolder = (ImageViewHolder) holder;
                int camera = hasCamera ? 1 : 0;
                position = holder.getAdapterPosition() - camera;
                FileBean fileBean = fileBeans.get(position);
                imageHolder.setData(fileBean);
                break;
            case TYPE_VIDEO:

                break;
            case TYPE_AUDIO:

                break;
            default:

                break;
        }
    }

    private static class ButtonViewHolder extends RecyclerView.ViewHolder {

        public ButtonViewHolder(View itemView, boolean hasCamera, int itemSize) {
            super(itemView);
            itemView.getLayoutParams().height = itemSize;
            itemView.getLayoutParams().width = itemSize;

        }
    }

    private static class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvImage;
int itemSize;
        public ImageViewHolder(View itemView, boolean hasCamera, int itemSize) {
            super(itemView);
            this.itemSize=itemSize;
            itemView.getLayoutParams().height = itemSize;
            itemView.getLayoutParams().width = itemSize;
            mIvImage = itemView.findViewById(R.id.iv_album_content_image);
        }

        void setData(FileBean fileBean) {
            Glide.with(mIvImage.getContext()).load(fileBean.getPath()).into(mIvImage);

        }
    }

    private static class VideoViewHolder extends RecyclerView.ViewHolder {

        public VideoViewHolder(View itemView, boolean hasCamera, int itemSize) {
            super(itemView);
            itemView.getLayoutParams().height = itemSize;
            itemView.getLayoutParams().width = itemSize;
        }
    }

    private static class AudioViewHolder extends RecyclerView.ViewHolder {

        public AudioViewHolder(View itemView, boolean hasCamera, int itemSize) {
            super(itemView);
            itemView.getLayoutParams().height = itemSize;
            itemView.getLayoutParams().width = itemSize;
        }
    }

}
