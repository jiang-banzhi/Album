package com.banzhi.album.task;

import android.content.Context;
import android.os.AsyncTask;

import com.banzhi.album.bean.FileBean;
import com.banzhi.album.utils.ThumbUtils;

import java.util.ArrayList;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/10/9.
 * @desciption :
 * @version :
 * </pre>
 */

public class ThumbTask extends AsyncTask<ArrayList<FileBean>, Void, ArrayList<FileBean>> {
    Context mContext;
    ThumbCallback mCallback;
    ThumbUtils thumbUtils;

    public ThumbTask(Context mContext, ThumbCallback mCallback) {
        this.mContext = mContext;
        this.mCallback = mCallback;
        thumbUtils = new ThumbUtils(mContext);

    }

    @Override
    protected ArrayList<FileBean> doInBackground(ArrayList<FileBean>[] params) {
        if (params != null && params.length > 0) {
            ArrayList<FileBean> fileBeans = params[0];
            for (FileBean fileBean : fileBeans) {
                int mediaType = fileBean.getMediaType();
                String thumbPath = null;
                if (mediaType == FileBean.TYPE_IMAGE) {
                    thumbPath = thumbUtils.getImageThumb(fileBean.getPath());
                } else if (mediaType == FileBean.TYPE_VIDEO) {
                    thumbPath = thumbUtils.getVideoThumb(fileBean.getPath());
                }
                fileBean.setThumbPath(thumbPath);
            }
            return fileBeans;
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<FileBean> fileBeans) {
        super.onPostExecute(fileBeans);
        if (mCallback != null) {
            mCallback.onThumbCallback(fileBeans);
        }
    }

    public interface ThumbCallback {
        void onThumbCallback(ArrayList<FileBean> fileBeans);
    }
}
