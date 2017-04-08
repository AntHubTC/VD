package com.tc.vd.utils;

import java.io.*;

/**
 * Created by tangcheng on 2017/4/8.
 */
public class FileReaderUtil {
    private FileReaderUtil(){}

    /**
     * 获取文件内容
     * @param file
     * @return
     * @throws IOException
     */
    public static String getFileContent(File file) throws IOException {
        BufferedReader br = null;
        try {
            StringBuilder sb = new StringBuilder();
            br = new BufferedReader(new FileReader(file));
            String s = null;
            while ((s = br.readLine()) != null) {
                sb.append(s);
                sb.append(System.lineSeparator());//换行符
            }
            return sb.toString();
        } catch (IOException e) {
            throw e;
        } finally {
            if(null != br){
                br.close();
            }
        }
    }

    /**
     * 获取文件内容
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String getFileContent(String filePath) throws IOException {
        return getFileContent(new File(filePath));
    }
}
