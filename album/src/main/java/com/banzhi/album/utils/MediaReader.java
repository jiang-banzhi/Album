package com.banzhi.album.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;

import com.banzhi.album.R;
import com.banzhi.album.bean.FileBean;
import com.banzhi.album.bean.FolderBean;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * @author : banzhi
 * @time : 2018/9/29.
 * @desciption :
 * @version :
 * </pre>
 */

public class MediaReader {
    Context mContext;

    public MediaReader(Context context) {
        this.mContext = context;
    }

    /**
     * 图片属性
     */
    private static final String[] IMAGES = {
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.TITLE,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.MIME_TYPE,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.DATE_MODIFIED,
            MediaStore.Images.Media.LATITUDE,
            MediaStore.Images.Media.LONGITUDE,
            MediaStore.Images.Media.SIZE
    };

    /**
     * 媒体属性
     */
    private static final String[] VIDEOS = {
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.BUCKET_ID,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media.MIME_TYPE,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.DATE_MODIFIED,
            MediaStore.Video.Media.LATITUDE,
            MediaStore.Video.Media.LONGITUDE,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.RESOLUTION
    };
    /**
     * 媒体属性
     */
    private static final String[] AUDIOS = {
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.BUCKET_ID,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media.MIME_TYPE,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.DATE_MODIFIED,
            MediaStore.Video.Media.LATITUDE,
            MediaStore.Video.Media.LONGITUDE,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DURATION
    };

    /**
     * 扫描图片
     */
    @WorkerThread
    public void scanImages(Map<String, FolderBean> folderMap, FolderBean allFileFolder) {
        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                IMAGES,
                null,
                null,
                MediaStore.Images.Media.DATE_ADDED);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String path = cursor.getString(cursor.getColumnIndex(IMAGES[0]));

                File file = new File(path);
                if (!file.exists() || !file.canRead()) continue;

                String name = cursor.getString(cursor.getColumnIndex(IMAGES[1]));
                String title = cursor.getString(cursor.getColumnIndex(IMAGES[2]));
                int bucketId = cursor.getInt(cursor.getColumnIndex(IMAGES[3]));
                String bucketName = cursor.getString(cursor.getColumnIndex(IMAGES[4]));
                String mimeType = cursor.getString(cursor.getColumnIndex(IMAGES[5]));
                long addDate = cursor.getLong(cursor.getColumnIndex(IMAGES[6]));
                long modifyDate = cursor.getLong(cursor.getColumnIndex(IMAGES[7]));
                float latitude = cursor.getFloat(cursor.getColumnIndex(IMAGES[8]));
                float longitude = cursor.getFloat(cursor.getColumnIndex(IMAGES[9]));
                long size = cursor.getLong(cursor.getColumnIndex(IMAGES[10]));


                FileBean fileBean = new FileBean();
                fileBean.setMediaType(FileBean.TYPE_IMAGE);
                fileBean.setPath(path);
                fileBean.setName(name);
                fileBean.setTitle(title);
                fileBean.setBucketId(bucketId);
                fileBean.setBucketName(bucketName);
                fileBean.setMimeType(mimeType);
                fileBean.setAddDate(addDate);
                fileBean.setModifyDate(modifyDate);
                fileBean.setLatitude(latitude);
                fileBean.setLongitude(longitude);
                fileBean.setSize(size);

                allFileFolder.addFileBean(fileBean);
                FolderBean folderBean = folderMap.get(bucketName);
                if (folderBean == null) {
                    folderBean = new FolderBean();
                    folderBean.setId(bucketId);
                    folderBean.setName(bucketName);
                    folderBean.addFileBean(fileBean);
                } else {
                    folderBean.addFileBean(fileBean);
                }
                folderMap.put(bucketName, folderBean);
            }
            cursor.close();
        }
    }

    /**
     * 扫描音频
     */
    @WorkerThread
    public void scanAudios(Map<String, FolderBean> folderMap, FolderBean allFileFolder) {
        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                AUDIOS,
                null,
                null,
                MediaStore.Images.Media.DATE_ADDED);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String path = cursor.getString(cursor.getColumnIndex(AUDIOS[0]));

                File file = new File(path);
                if (!file.exists() || !file.canRead()) continue;

                String name = cursor.getString(cursor.getColumnIndex(AUDIOS[1]));
                String title = cursor.getString(cursor.getColumnIndex(AUDIOS[2]));
                int bucketId = cursor.getInt(cursor.getColumnIndex(AUDIOS[3]));
                String bucketName = cursor.getString(cursor.getColumnIndex(AUDIOS[4]));
                String mimeType = cursor.getString(cursor.getColumnIndex(AUDIOS[5]));
                long addDate = cursor.getLong(cursor.getColumnIndex(AUDIOS[6]));
                long modifyDate = cursor.getLong(cursor.getColumnIndex(AUDIOS[7]));
                float latitude = cursor.getFloat(cursor.getColumnIndex(AUDIOS[8]));
                float longitude = cursor.getFloat(cursor.getColumnIndex(AUDIOS[9]));
                long size = cursor.getLong(cursor.getColumnIndex(AUDIOS[10]));
                long duration = cursor.getLong(cursor.getColumnIndex(AUDIOS[11]));

                FileBean fileBean = new FileBean();
                fileBean.setMediaType(FileBean.TYPE_IMAGE);
                fileBean.setPath(path);
                fileBean.setName(name);
                fileBean.setTitle(title);
                fileBean.setBucketId(bucketId);
                fileBean.setBucketName(bucketName);
                fileBean.setMimeType(mimeType);
                fileBean.setAddDate(addDate);
                fileBean.setModifyDate(modifyDate);
                fileBean.setLatitude(latitude);
                fileBean.setLongitude(longitude);
                fileBean.setSize(size);
                fileBean.setSize(duration);

                allFileFolder.addFileBean(fileBean);
                FolderBean folderBean = folderMap.get(bucketName);
                if (folderBean == null) {
                    folderBean = new FolderBean();
                    folderBean.setId(bucketId);
                    folderBean.setName(bucketName);
                    folderBean.addFileBean(fileBean);
                } else {
                    folderBean.addFileBean(fileBean);
                }
                folderMap.put(bucketName, folderBean);
            }
            cursor.close();
        }
    }

    /**
     * 扫描视频
     */
    @WorkerThread
    public void scanVideo(Map<String, FolderBean> folderMap, FolderBean allFileFolder) {
        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                VIDEOS,
                null,
                null,
                MediaStore.Images.Media.DATE_ADDED);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String path = cursor.getString(cursor.getColumnIndex(VIDEOS[0]));

                File file = new File(path);
                if (!file.exists() || !file.canRead()) continue;

                String name = cursor.getString(cursor.getColumnIndex(VIDEOS[1]));
                String title = cursor.getString(cursor.getColumnIndex(VIDEOS[2]));
                int bucketId = cursor.getInt(cursor.getColumnIndex(VIDEOS[3]));
                String bucketName = cursor.getString(cursor.getColumnIndex(VIDEOS[4]));
                String mimeType = cursor.getString(cursor.getColumnIndex(VIDEOS[5]));
                long addDate = cursor.getLong(cursor.getColumnIndex(VIDEOS[6]));
                long modifyDate = cursor.getLong(cursor.getColumnIndex(VIDEOS[7]));
                float latitude = cursor.getFloat(cursor.getColumnIndex(VIDEOS[8]));
                float longitude = cursor.getFloat(cursor.getColumnIndex(VIDEOS[9]));
                long size = cursor.getLong(cursor.getColumnIndex(VIDEOS[10]));
                long duration = cursor.getLong(cursor.getColumnIndex(VIDEOS[11]));
                String resolution = cursor.getString(cursor.getColumnIndex(VIDEOS[12]));

                FileBean fileBean = new FileBean();
                fileBean.setMediaType(FileBean.TYPE_VIDEO);
                fileBean.setPath(path);
                fileBean.setName(name);
                fileBean.setTitle(title);
                fileBean.setBucketId(bucketId);
                fileBean.setBucketName(bucketName);
                fileBean.setMimeType(mimeType);
                fileBean.setAddDate(addDate);
                fileBean.setModifyDate(modifyDate);
                fileBean.setLatitude(latitude);
                fileBean.setLongitude(longitude);
                fileBean.setSize(size);
                fileBean.setDuration(duration);

                int width = 0, height = 0;
                if (!TextUtils.isEmpty(resolution) && resolution.contains("x")) {
                    String[] resolutionArray = resolution.split("x");
                    width = Integer.valueOf(resolutionArray[0]);
                    height = Integer.valueOf(resolutionArray[1]);
                }
                fileBean.setWidth(width);
                fileBean.setHeight(height);

                allFileFolder.addFileBean(fileBean);
                FolderBean folderBean = folderMap.get(bucketName);
                if (folderBean == null) {
                    folderBean = new FolderBean();
                    folderBean.setId(bucketId);
                    folderBean.setName(bucketName);
                    folderBean.addFileBean(fileBean);
                } else {
                    folderBean.addFileBean(fileBean);
                }
                folderMap.put(bucketName, folderBean);
            }
            cursor.close();
        }
    }

    /**
     * 获取所有图片
     *
     * @return
     */
    public ArrayList<FolderBean> getAllImages() {
        Map<String, FolderBean> folderMap = new HashMap<>();
        FolderBean allFolderBean = new FolderBean();
        allFolderBean.setChecked(true);
        allFolderBean.setName(mContext.getString(R.string.all_images));
        //扫描图片
        scanImages(folderMap, allFolderBean);

        ArrayList<FolderBean> allFolderList = new ArrayList<>();
        Collections.sort(allFolderBean.getFileBeans());
        allFolderList.add(allFolderBean);
        for (Map.Entry<String, FolderBean> entry : folderMap.entrySet()) {
            FolderBean albumFolder = entry.getValue();
            Collections.sort(albumFolder.getFileBeans());
            allFolderList.add(albumFolder);
        }
        return allFolderList;
    }

    /**
     * 获取所有视频
     *
     * @return
     */
    public ArrayList<FolderBean> getAllVideos() {
        Map<String, FolderBean> folderMap = new HashMap<>();
        FolderBean allFolderBean = new FolderBean();
        allFolderBean.setChecked(true);
        allFolderBean.setName(mContext.getString(R.string.all_videos));
        //扫描视频
        scanVideo(folderMap, allFolderBean);

        ArrayList<FolderBean> allFolderList = new ArrayList<>();
        Collections.sort(allFolderBean.getFileBeans());
        allFolderList.add(allFolderBean);

        for (Map.Entry<String, FolderBean> entry : folderMap.entrySet()) {
            FolderBean albumFolder = entry.getValue();
            Collections.sort(albumFolder.getFileBeans());
            allFolderList.add(albumFolder);
        }
        return allFolderList;
    }

    /**
     * 获取所有音频
     *
     * @return
     */
    public ArrayList<FolderBean> getAllAudios() {
        Map<String, FolderBean> folderMap = new HashMap<>();
        FolderBean allFolderBean = new FolderBean();
        allFolderBean.setChecked(true);
        allFolderBean.setName(mContext.getString(R.string.all_audios));
        //扫描音频
        scanAudios(folderMap, allFolderBean);

        ArrayList<FolderBean> allFolderList = new ArrayList<>();
        Collections.sort(allFolderBean.getFileBeans());
        allFolderList.add(allFolderBean);
        for (Map.Entry<String, FolderBean> entry : folderMap.entrySet()) {
            FolderBean albumFolder = entry.getValue();
            Collections.sort(albumFolder.getFileBeans());
            allFolderList.add(albumFolder);
        }
        return allFolderList;
    }

    /**
     * 获取所有图片 音频
     *
     * @return
     */
    public ArrayList<FolderBean> getImagesAndAudios() {
        Map<String, FolderBean> folderMap = new HashMap<>();
        FolderBean allFolderBean = new FolderBean();
        allFolderBean.setChecked(true);
        allFolderBean.setName(mContext.getString(R.string.all_images_audios));

        scanImages(folderMap, allFolderBean);
        scanAudios(folderMap, allFolderBean);

        ArrayList<FolderBean> allFolderList = new ArrayList<>();
        Collections.sort(allFolderBean.getFileBeans());
        allFolderList.add(allFolderBean);
        for (Map.Entry<String, FolderBean> entry : folderMap.entrySet()) {
            FolderBean albumFolder = entry.getValue();
            Collections.sort(albumFolder.getFileBeans());
            allFolderList.add(albumFolder);
        }
        return allFolderList;
    }

    /**
     * 获取所有图片 视频
     *
     * @return
     */
    public ArrayList<FolderBean> getImagesAndVideos() {
        Map<String, FolderBean> folderMap = new HashMap<>();
        FolderBean allFolderBean = new FolderBean();
        allFolderBean.setChecked(true);
        allFolderBean.setName(mContext.getString(R.string.all_images_videos));

        scanImages(folderMap, allFolderBean);
        scanVideo(folderMap, allFolderBean);

        ArrayList<FolderBean> allFolderList = new ArrayList<>();
        Collections.sort(allFolderBean.getFileBeans());
        allFolderList.add(allFolderBean);
        for (Map.Entry<String, FolderBean> entry : folderMap.entrySet()) {
            FolderBean albumFolder = entry.getValue();
            Collections.sort(albumFolder.getFileBeans());
            allFolderList.add(albumFolder);
        }
        return allFolderList;
    }

    /**
     * 获取所有音频 视频
     *
     * @return
     */
    public ArrayList<FolderBean> getAudiosAndVideos() {
        Map<String, FolderBean> folderMap = new HashMap<>();
        FolderBean allFolderBean = new FolderBean();
        allFolderBean.setChecked(true);
        allFolderBean.setName(mContext.getString(R.string.all_audios_videos));

        scanAudios(folderMap, allFolderBean);
        scanVideo(folderMap, allFolderBean);

        ArrayList<FolderBean> allFolderList = new ArrayList<>();
        Collections.sort(allFolderBean.getFileBeans());
        allFolderList.add(allFolderBean);
        for (Map.Entry<String, FolderBean> entry : folderMap.entrySet()) {
            FolderBean albumFolder = entry.getValue();
            Collections.sort(albumFolder.getFileBeans());
            allFolderList.add(albumFolder);
        }
        return allFolderList;
    }

    /**
     * 获取所有媒体文件
     *
     * @return
     */
    public ArrayList<FolderBean> getAllMedia() {
        Map<String, FolderBean> folderMap = new HashMap<>();
        FolderBean allFolderBean = new FolderBean();
        allFolderBean.setChecked(true);
        allFolderBean.setName(mContext.getString(R.string.all_images_audios_videos));
        //扫描音频
        scanImages(folderMap, allFolderBean);
        scanAudios(folderMap, allFolderBean);
        scanVideo(folderMap, allFolderBean);

        ArrayList<FolderBean> allFolderList = new ArrayList<>();
        Collections.sort(allFolderBean.getFileBeans());
        allFolderList.add(allFolderBean);
        for (Map.Entry<String, FolderBean> entry : folderMap.entrySet()) {
            FolderBean albumFolder = entry.getValue();
            Collections.sort(albumFolder.getFileBeans());
            allFolderList.add(albumFolder);
        }
        return allFolderList;
    }


}
