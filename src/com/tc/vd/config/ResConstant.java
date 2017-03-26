package com.tc.vd.config;

import java.io.File;

/**
 * Created by tangcheng on 2017/3/13.
 */
public class ResConstant {
    //系统主目录
    public static final String rootPath = System.getProperty("user.dir");
    //系统核心设置
    public static final String sysConf = "conf" + File.separator + "system.properties";
    //系统界面设置
    public static final String uiConf = "conf" + File.separator + "ui.properties";
    //日志配置
    public static final String logConf = "conf" + File.separator + "log4j.properties";
    //用户个性化设置
    public static final String userConf = "data" + File.separator + "userConfig.ini";
    //地址簿
    public static final String addressBook = "data" + File.separator + "address.ini";
}
