package com.banzhi.album.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/9/30.
 * @desciption :
 * @version :
 * </pre>
 */

public class FileBean implements Parcelable, Comparable<FileBean> {
    public static final int TYPE_INVALID = 0;
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_VIDEO = 2;
    public static final int TYPE_AUDIO = 3;
    public static final int TYPE_OTHER = 4;


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TYPE_IMAGE, TYPE_VIDEO, TYPE_AUDIO, TYPE_OTHER})
    public @interface MediaType {

    }

    /**
     * 文件路径
     * <p>
     * 对应 MediaStore.Video.Media.DATA
     */
    private String path;
    /**
     * 文件名称（包括扩展名）
     * MediaStore.Video.Media.DISPLAY_NAME
     */
    private String name;
    /**
     * 文件名称（不包括扩展名）
     * <p>
     * MediaStore.Video.Media.TITLE
     */
    private String title;
    /**
     * 文件夹id
     * MediaStore.Video.Media.BUCKET_ID,
     */
    private int bucketId;
    /**
     * 文件夹名称
     * MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
     */
    private String bucketName;
    /**
     * 文件 mime type
     * MediaStore.Video.Media.MIME_TYPE
     */
    private String mimeType;
    /**
     * 添加时间
     * MediaStore.Video.Media.DATE_ADDED
     */
    private long addDate;
    /**
     * 最后修改时间
     * MediaStore.Video.Media.DATE_MODIFIED
     */
    private long modifyDate;
    /**
     * 维度
     * MediaStore.Video.Media.LATITUDE
     */
    private float latitude;
    /**
     * 经度
     * MediaStore.Video.Media.LONGITUDE
     */
    private float longitude;
    /**
     * 文件大小
     * MediaStore.Video.Media.SIZE
     */
    private long size;
    /**
     * 时长（音视频）
     * MediaStore.Video.Media.DURATION
     */
    private long duration;
    /**
     * 缩略图
     */
    private String thumbPath;
    /**
     * 宽度
     */
    private int width;
    /**
     * 高度
     */
    private int height;
    /**
     * 媒体类型
     */
    @MediaType
    private int mediaType;
    /**
     * 是否选中
     */
    private boolean isChecked;


    public FileBean() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBucketId() {
        return bucketId;
    }

    public void setBucketId(int bucketId) {
        this.bucketId = bucketId;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public long getAddDate() {
        return addDate;
    }

    public void setAddDate(long addDate) {
        this.addDate = addDate;
    }

    public long getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(long modifyDate) {
        this.modifyDate = modifyDate;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(@MediaType int mediaType) {
        this.mediaType = mediaType;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public int compareTo(@NonNull FileBean o) {
        long time = o.getAddDate() - getAddDate();
        if (time > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        } else if (time < -Integer.MAX_VALUE) {
            return -Integer.MAX_VALUE;
        }
        return (int) time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileBean fileBean = (FileBean) o;
        return path.equals(fileBean.path);
    }

    @Override
    public int hashCode() {
        int result = path.hashCode();
        result = 31 * result + bucketId;
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected FileBean(Parcel in) {
        this.path = in.readString();
        this.name = in.readString();
        this.title = in.readString();
        this.bucketId = in.readInt();
        this.bucketName = in.readString();
        this.mimeType = in.readString();
        this.addDate = in.readLong();
        this.modifyDate = in.readLong();
        this.latitude = in.readFloat();
        this.longitude = in.readFloat();
        this.size = in.readLong();
        this.duration = in.readLong();
        this.thumbPath = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.mediaType = in.readInt();
        this.isChecked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeString(name);
        dest.writeString(title);
        dest.writeInt(bucketId);
        dest.writeString(bucketName);
        dest.writeString(mimeType);
        dest.writeLong(addDate);
        dest.writeLong(modifyDate);
        dest.writeFloat(latitude);
        dest.writeFloat(longitude);
        dest.writeLong(size);
        dest.writeLong(duration);
        dest.writeString(thumbPath);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeInt(mediaType);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }

    public static final Creator<FileBean> CREATOR = new Creator<FileBean>() {
        @Override
        public FileBean createFromParcel(Parcel in) {
            return new FileBean(in);
        }

        @Override
        public FileBean[] newArray(int size) {
            return new FileBean[size];
        }
    };

}
