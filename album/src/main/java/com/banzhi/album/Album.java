package com.banzhi.album;

import android.support.annotation.IntDef;

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

public class Album {
    public static final int MODE_IMAGES = 1;
    public static final int MODE_VIDEOS = 2;
    public static final int MODE_AUDIOS = 3;
    public static final int MODE_IMAGES_VIDEOS = 4;
    public static final int MODE_IMAGES_AUDIOS = 5;
    public static final int MODE_VIDEOS_AUDIOS = 6;
    public static final int MODE_MEDIA = 7;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MODE_IMAGES, MODE_VIDEOS, MODE_AUDIOS, MODE_IMAGES_VIDEOS, MODE_IMAGES_AUDIOS, MODE_VIDEOS_AUDIOS, MODE_MEDIA})
    public @interface ChoiseMode {

    }

    @ChoiseMode
    int mode = MODE_IMAGES;


}
