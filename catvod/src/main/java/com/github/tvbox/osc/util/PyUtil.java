package com.github.tvbox.osc.util;

import androidx.annotation.Keep;

import com.github.catvod.Init;
import com.github.catvod.Proxy;
import com.github.tvbox.osc.reflect.Reflect;
import java.io.File;
import dalvik.system.DexClassLoader;

public class PyUtil {
    static DexClassLoader classLoader;
    @Keep
    public static String getProxy(boolean local) {
        return Proxy.getUrl(local) + "?do=py";
    }
    @Keep
    public static void load(String jar) {
        File cacheDir = new File(Init.context().getCacheDir().getAbsolutePath() + "/py");
        if (!cacheDir.exists())
            cacheDir.mkdirs();
        classLoader = new DexClassLoader(jar, cacheDir.getAbsolutePath(), null, Init.context().getClassLoader());
        // make force wait here, some device async dex load
    }
    @Keep
    public static String call(String className, String name, Object... obj) {
        try {
            if(obj.length > 0){
                return Reflect.onClass(classLoader.loadClass(className)).create().call(name, obj).get();
            }
            return Reflect.onClass(classLoader.loadClass(className)).create().call(name).get();
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return null;
    }

}
