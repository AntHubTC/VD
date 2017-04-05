package com.tc.vd.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 报文
 * Created by tangcheng on 2017/3/28.
 */
public class Datagram {
    public String path; //文件路径
    public String fileName; //文件名
    private String datagramTextContent = null; //报文内容

    public Datagram(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return this.fileName;
    }

    /**
     * 是否是报文文本
     * @return
     */
    public boolean isDatagramText() {
        return this.fileName.endsWith(".xml");
    }

    /**
     * 是否是报文类别
     * @return
     */
    public boolean isDatagramCatalog() {
        return !isDatagramText();
    }

    /**
     * 获取报文正文
     * @return
     */
    public String getDatagramTextContent() throws Exception {
        if(null != datagramTextContent) return datagramTextContent;
        if(isDatagramCatalog()) return null;
        BufferedReader br = null;
        try {
            StringBuilder sb = new StringBuilder();
            File file = new File(this.getPath());
            br = new BufferedReader(new FileReader(file));
            String s = null;
            while ((s = br.readLine()) != null) {
                sb.append(s);
                sb.append(System.lineSeparator());//换行符
            }
            datagramTextContent = sb.toString();
            return datagramTextContent;
        } catch (Exception e) {
            throw e;
        } finally {
            if(null != br){
                br.close();
            }
        }
    }
}
