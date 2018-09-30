package com.banzhi.album.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/9/30.
 * @desciption :
 * @version :
 * </pre>
 */

public class FolderBean implements Parcelable {

    private int id;

    private String name;

    private ArrayList<FileBean> fileBeans = new ArrayList<>();

    private boolean isChecked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<FileBean> getFileBeans() {
        return fileBeans;
    }

    public void addFileBean(FileBean fileBean) {
        if (!fileBeans.contains(fileBean)) {
            fileBeans.add(fileBean);
        }
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public FolderBean() {
    }

    protected FolderBean(Parcel in) {
        id = in.readInt();
        name = in.readString();
        fileBeans = in.createTypedArrayList(FileBean.CREATOR);
        isChecked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(fileBeans);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FolderBean> CREATOR = new Creator<FolderBean>() {
        @Override
        public FolderBean createFromParcel(Parcel in) {
            return new FolderBean(in);
        }

        @Override
        public FolderBean[] newArray(int size) {
            return new FolderBean[size];
        }
    };
}
