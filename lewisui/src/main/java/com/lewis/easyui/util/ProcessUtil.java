package com.lewis.easyui.util;

import android.app.ActivityManager;
import android.content.Context;

public class ProcessUtil {


    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    public static boolean isTheCurrentProcess(Context context){
        String process = getCurProcessName(context);
        return context.getPackageName().equals(process);
    }

}
