package com.tssss.bysj.other;

public class VersionManager {
    private static VersionManager instance;

    public static VersionManager getInstance() {
        if (null == instance) {
            instance = new VersionManager();
        }
        return instance;
    }

    /**
     *  获取当前版本
     */
    public String getVersion() {
        return "1.0.0";
    }
}
