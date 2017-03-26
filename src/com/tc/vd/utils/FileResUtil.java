package com.tc.vd.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by tangcheng on 2017/3/11.
 */
public class FileResUtil {

    /**
     * 获取资源文件
     * @param rootPath
     * @param res
     * @return
     * @throws FileNotFoundException
     */
    public static File getResFile(String rootPath, String res) throws FileNotFoundException {
        File resourceFile = new File(rootPath, res);
        if (!resourceFile.exists()) {
            throw new FileNotFoundException();
        }
        return resourceFile;
    }

    /**
     * 获取资源文件
     *
     * @param res
     * @return
     */
    public static File getResFile(String res) throws FileNotFoundException {
        File resourceFile = new File(res);
        if (!resourceFile.exists()) {
            throw new FileNotFoundException();
        }
        return resourceFile;
    }

    /**
     * 获取资源url
     *
     * @param res
     * @return
     */
    public static URL getResUrl(String res) throws FileNotFoundException {
        File resourceFile = getResFile(res);
        URI uri = resourceFile.toURI();
        try {
            return uri.toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}