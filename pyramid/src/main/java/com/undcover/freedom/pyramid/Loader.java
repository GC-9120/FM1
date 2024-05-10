package com.undcover.freedom.pyramid;

import android.content.Context;

import androidx.annotation.Keep;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.github.catvod.net.OkHttp;
import com.github.catvod.utils.Path;

public class Loader {
    private Python pyInstance;
    private PyObject pyApp;
    private String cache;
    private static int port = -1;

    @Keep
    private void init(Context context) {
        if (pyInstance == null) {
            if (!Python.isStarted()) {
                Python.start(new AndroidPlatform(context));
            }
            pyInstance = Python.getInstance();
            pyApp = pyInstance.getModule("app");
        }
    }

    @Keep
    public Spider spider(Context context, String key, String api, String ext) {
        cache = Path.py().getAbsolutePath() + "/";
        if (pyApp == null) init(context);
        Spider spider = new Spider(pyApp, key, cache, ext);
        spider.init(context, api);
        return spider;
    }

    public static String getPort() {
        if (port <= 0) {
            for (int i = 9978; i < 10000; i++) {
                if (OkHttp.string("http://127.0.0.1:" + i + "/proxy?do=ck").equals("OK")) {
                    port = i;
                    return "http://127.0.0.1:" + port + "/proxy";
                }
            }
        }
        return "http://127.0.0.1:9978/proxy";
    }

    public static String localProxyUrl() {
        return getPort();
    }

}
