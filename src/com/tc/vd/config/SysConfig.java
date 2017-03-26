package com.tc.vd.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by tangcheng on 2017/3/3.
 */
public class SysConfig {
    static {
        File sysConf = new File(ResConstant.rootPath, ResConstant.sysConf);
        parsePropertyFile(sysConf);
        File uiRes = new File(ResConstant.rootPath, ResConstant.uiConf);
        parsePropertyFile(uiRes);

        loadAfter();//加载系统配置之后
    }

    /**
     * 载入配置后执行
     */
    private static void loadAfter() {
        //设置皮肤
        String skin = System.getProperty("ui.skin");
        if(null != skin && !"".equals(skin)){
            System.setProperty("javafx.userAgentStylesheetUrl", skin);
        }
    }

    private static void parsePropertyFile(File propertiesFile) {
        Properties properites = new Properties();

        try {
            properites.load(new FileInputStream(propertiesFile));
            Enumeration e = properites.propertyNames();

            while(e.hasMoreElements()) {
                String key = (String)e.nextElement();
                String value = properites.getProperty(key);
                if(System.getProperty(key) == null) {
                    System.setProperty(key, value);
                }
            }
        } catch (IOException e) {
            System.out.println("Load conf/system.properties failure!");
        }
    }
}
