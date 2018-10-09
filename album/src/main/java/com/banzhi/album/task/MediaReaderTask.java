package com.banzhi.album.task;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;

import com.banzhi.album.Album;
import com.banzhi.album.bean.FileBean;
import com.banzhi.album.bean.FolderBean;
import com.banzhi.album.ui.dialog.LoadingDialog;
import com.banzhi.album.utils.MediaReader;

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

public class MediaReaderTask extends AsyncTask<ArrayList<FileBean>, Void, ArrayList<FolderBean>> {
    Context mContext;
    ScanCallback mCallback;
    @Album.ChoiseMode
    int mMode;
    List<FileBean> checkList;

    Dialog mDialog;

    public MediaReaderTask(Context context, @Album.ChoiseMode int mode, ScanCallback mCallback) {
        this.mContext = context;
        this.mMode = mode;
        this.mCallback = mCallback;
        mDialog = new LoadingDialog(mContext);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    @Override
    protected ArrayList<FolderBean> doInBackground(ArrayList<FileBean>[] params) {
        MediaReader reader = new MediaReader(mContext);
        ArrayList<FolderBean> allFolderBeans;
        switch (mMode) {
            case Album.MODE_IMAGES:
                allFolderBeans = reader.getAllImages();
                break;
            case Album.MODE_VIDEOS:
                allFolderBeans = reader.getAllVideos();
                break;
            case Album.MODE_AUDIOS:
                allFolderBeans = reader.getAllAudios();
                break;
            case Album.MODE_IMAGES_VIDEOS:
                allFolderBeans = reader.getImagesAndVideos();
                break;
            case Album.MODE_IMAGES_AUDIOS:
                allFolderBeans = reader.getImagesAndAudios();
                break;
            case Album.MODE_VIDEOS_AUDIOS:
                allFolderBeans = reader.getAudiosAndVideos();
                break;
            case Album.MODE_MEDIA:
                allFolderBeans = reader.getAllMedia();
                break;
            default:
                allFolderBeans = reader.getAllMedia();
                break;
        }
        if (params != null && params.length > 0) {
            ArrayList<FileBean> checkFiles = params[0];
            if (checkFiles != null && checkFiles.size() > 0) {
                ArrayList<FileBean> allFileBeans = allFolderBeans.get(0).getFileBeans();
                for (FileBean checkFile : checkFiles) {
                    for (int i = 0; i < allFileBeans.size(); i++) {
                        FileBean fileBean = allFileBeans.get(i);
                        if (fileBean.equals(checkFile)) {
                            fileBean.setChecked(true);
                            checkList.add(fileBean);
                        }
                    }

                }
            }
        }
        return allFolderBeans;
    }

    @Override
    protected void onPostExecute(ArrayList<FolderBean> folderBeans) {
        super.onPostExecute(folderBeans);
        if (mDialog != null) {
            mDialog.dismiss();
        }
        if (mCallback != null) {
            mCallback.onScanCallback(folderBeans);
        }
    }

    public interface ScanCallback {
        void onScanCallback(ArrayList<FolderBean> folderBeans);
    }

}
