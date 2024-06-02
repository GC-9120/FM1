package com.github.catvod.crawler;

import android.text.TextUtils;
import android.util.Log;

import com.github.tvbox.osc.event.LogEvent;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

public class SpiderDebug {

    private static final String TAG = SpiderDebug.class.getSimpleName();

    public static void log(Throwable th) {
        if (th != null) {
            EventBus.getDefault().post(new LogEvent(String.format("【%s】=>>>", TAG) + Log.getStackTraceString(th)));
            th.printStackTrace();
        }
    }

    public static void log(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            EventBus.getDefault().post(new LogEvent(String.format("【%s】=>>>", TAG) + msg));
            Logger.t(TAG).d(msg);
        }
    }
}
