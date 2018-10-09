package com.banzhi.album.utils;

import android.support.annotation.IntRange;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/10/9.
 * @desciption :
 * @version :
 * </pre>
 */

public class AlubmUtils {


    public static String convertDuration(@IntRange(from = 1, to = Long.MAX_VALUE) long duration) {
        duration /= 1000;
        int hour = (int) (duration / 3600);
        int minute = (int) ((duration - hour * 3600) / 60);
        int second = (int) (duration - hour * 3600 - minute * 60);

        String hourValue = "";
        String minuteValue;
        String secondValue;
        if (hour > 0) {
            if (hour >= 10) {
                hourValue = Integer.toString(hour);
            } else {
                hourValue = "0" + hour;
            }
            hourValue += ":";
        }
        if (minute > 0) {
            if (minute >= 10) {
                minuteValue = Integer.toString(minute);
            } else {
                minuteValue = "0" + minute;
            }
        } else {
            minuteValue = "00";
        }
        minuteValue += ":";
        if (second > 0) {
            if (second >= 10) {
                secondValue = Integer.toString(second);
            } else {
                secondValue = "0" + second;
            }
        } else {
            secondValue = "00";
        }
        return hourValue + minuteValue + secondValue;
    }
}
