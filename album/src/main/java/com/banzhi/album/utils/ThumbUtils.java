package com.banzhi.album.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/10/9.
 * @desciption :
 * @version :
 * </pre>
 */

public class ThumbUtils {

    Context mContext;
    File cacheFile;

    public ThumbUtils(Context mContext) {
        this.mContext = mContext;
        cacheFile = createCacheDir(mContext);
    }

    private static File createCacheDir(Context context) {
        File rootDir = FileUtils.getRootPath(context);
        File cacheDir = new File(rootDir, "album");
        if (cacheDir.exists() && cacheDir.isFile()) {
            cacheDir.delete();
        }
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        return cacheDir;
    }

    /**
     * 获取图片缩略图
     *
     * @param imagePath
     * @return
     */
    public String getImageThumb(String imagePath) {
        if (TextUtils.isEmpty(imagePath)) {
            return null;
        }
        File thumbnailFile = getCachePath(cacheFile, imagePath);
        if (thumbnailFile.exists()) {
            return thumbnailFile.getAbsolutePath();
        }
        Bitmap bitmap = readBitmapFromPath(imagePath);
        if (bitmap == null) return imagePath;

        ByteArrayOutputStream compressStream = new ByteArrayOutputStream();
        int options = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, compressStream);

        while (compressStream.toByteArray().length > 200 * 1024) {
            if (options <= 0) {
                break;
            }
            compressStream.reset();
            options -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, compressStream);
        }

        try {
            compressStream.close();
        } catch (IOException ignored) {
        }

        try {
            thumbnailFile.createNewFile();
        } catch (Exception ignored) {
            return imagePath;
        }
        FileOutputStream writeStream = null;
        try {
            writeStream = new FileOutputStream(thumbnailFile);
            writeStream.write(compressStream.toByteArray());
        } catch (Exception ignored) {
            return imagePath;
        } finally {
            if (writeStream != null) {
                try {
                    writeStream.flush();
                } catch (IOException ignored) {
                }
                try {
                    writeStream.close();
                } catch (IOException ignored) {
                }
            }
        }
        return thumbnailFile.getAbsolutePath();
    }

    /**
     * 将图片读取为bitmap
     *
     * @param path
     * @return
     */
    private Bitmap readBitmapFromPath(String path) {
        Bitmap decodeSampledBitmap = null;
        BufferedInputStream inputStream = null;
        File imageFile = new File(path);
        if (imageFile.exists()) {
            try {
                inputStream = new BufferedInputStream(new FileInputStream(imageFile));
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(inputStream, null, options);
                inputStream.close();

                options.inSampleSize = computeSize(options, 720, 1280);
                options.inJustDecodeBounds = false;

                boolean decodeAttemptSuccess = false;
                while (!decodeAttemptSuccess) {
                    inputStream = new BufferedInputStream(new FileInputStream(imageFile));
                    try {
                        decodeSampledBitmap = BitmapFactory.decodeStream(inputStream, null, options);
                        decodeAttemptSuccess = true;
                    } catch (Exception e) {
                        options.inSampleSize *= 2;
                    }
                    inputStream.close();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return decodeSampledBitmap;
    }

    /**
     * 计算压缩比例
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int computeSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            while ((height / inSampleSize) > reqHeight || (width / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 获取视频缩略图
     *
     * @param path
     * @return
     */

    public String getVideoThumb(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        File thumbnailFile = getCachePath(cacheFile, path);
        if (thumbnailFile.exists()) {
            return thumbnailFile.getAbsolutePath();
        }
        try {
            thumbnailFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MINI_KIND);
        if (bitmap == null) {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(mContext, Uri.fromFile(new File(path)));
            bitmap = retriever.getFrameAtTime();
        }
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(thumbnailFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return thumbnailFile.getAbsolutePath();
    }


    private static File getCachePath(File cacheDir, String filePath) {
        String name = filePath.substring(filePath.lastIndexOf("/"), filePath.lastIndexOf(".")) + "_thumb";
        String outFilePath = name + ".jpg";
        return new File(cacheDir, outFilePath);
    }
}
