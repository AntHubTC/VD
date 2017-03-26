package com.tc.vd.config;

import com.tc.vd.utils.FileResUtil;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 用户偏好设置
 * Created by tangcheng on 2017/3/13.
 *  String property = UserConfig.getInstance().getProperty("ui", "closeBtnIsExitApplication");
 */
public class UserConfig {
    public static final UserConfig userConfig = new UserConfig();
    public static HierarchicalINIConfiguration context;

    static {
        File resFile = null;
        try {
            resFile = FileResUtil.getResFile(ResConstant.rootPath, ResConstant.userConf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            context = new HierarchicalINIConfiguration(resFile);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    private UserConfig() {}

    public static UserConfig getInstance(){
        return userConfig;
    }

    /**
     * 设置用户偏好配置
     * @param section
     * @param key
     * @param value
     * @param isSave
     */
    public void setProperty(String section, String key, String value,boolean isSave){
        SubnodeConfiguration sectionConfig = context.getSection(section);
        sectionConfig.setProperty(key, value);

        if(isSave){//是否保存
            save();
        }
    }

    /**
     * 保存用户设置
     */
    public void save(){
        try {
            context.save();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     *  获取属性
     * @param section
     * @param key
     * @return
     */
    public String getProperty(String section, String key){
        SubnodeConfiguration sectionConfig = context.getSection(section);
        String value = sectionConfig.getString(key);
        return value;
    }
}
