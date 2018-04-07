package com.wj.logger;

import com.orhanobut.logger.DiskLogStrategy;
import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.Printer;

/**
 * 针对com.orhanobut.logger.Logger做隔离
 * Created by wangjian on 2018/3/19.
 */

public final class Logger {

    public static void printer(Printer printer) {
        com.orhanobut.logger.Logger.printer(printer);
    }

    public static void addLogAdapter(LogAdapter adapter) {
        com.orhanobut.logger.Logger.addLogAdapter(adapter);
    }

    public static void clearLogAdapters() {
        com.orhanobut.logger.Logger.clearLogAdapters();
    }

    public static Printer t(String tag) {
        return com.orhanobut.logger.Logger.t(tag);
    }

    public static void log(int priority, String tag, String message, Throwable throwable) {
        com.orhanobut.logger.Logger.log(priority, tag, message, throwable);
    }

    public static void d(String message, Object... args) {
        com.orhanobut.logger.Logger.d(message, args);
    }

    public static void d(Object object) {
        com.orhanobut.logger.Logger.d(object);
    }

    public static void e(String message, Object... args) {
        com.orhanobut.logger.Logger.e(null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        com.orhanobut.logger.Logger.e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        com.orhanobut.logger.Logger.i(message, args);
    }

    public static void v(String message, Object... args) {
        com.orhanobut.logger.Logger.v(message, args);
    }

    public static void w(String message, Object... args) {
        com.orhanobut.logger.Logger.w(message, args);
    }

    public static void wtf(String message, Object... args) {
        com.orhanobut.logger.Logger.wtf(message, args);
    }

    public static void json(String json) {
        com.orhanobut.logger.Logger.json(json);
    }

    public static void xml(String xml) {
        com.orhanobut.logger.Logger.xml(xml);
    }

    /**
     * 新增方法，获取日志储存目录
     * */
    public static String getLogPath(){
        return DiskLogStrategy.logFolder;
    }
}
