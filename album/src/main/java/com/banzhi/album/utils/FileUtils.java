package com.banzhi.album.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/10/9.
 * @desciption :
 * @version :
 * </pre>
 */

public class FileUtils {

    public static boolean sdCardIsAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().canWrite();
        }
        return false;
    }
    public static File getRootPath(Context context) {
        if (sdCardIsAvailable()) {
            return Environment.getExternalStorageDirectory();
        } else {
            return context.getFilesDir();
        }
    }
}
