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

    /**
     * 获取模板路径
     * @return
     */
    public String getTemplatePath() {
        if(null == path) return null;
        if(!path.endsWith(".xml")) return null;
        String templatePath = "";
        templatePath = path.substring(0, path.length() - ".xml".length());
        templatePath += "_template.xml";
        return templatePath;
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

    /**
     * 创建报文文件
     * @param isCatalog
     * @return
     * @throws IOException
     */
    public boolean createFile(boolean isCatalog) throws IOException {
        //创建报文或报文类别
        File datagramFile = new File(path);
        boolean isCreateSuccess = false;
        if(isCatalog){
            isCreateSuccess = datagramFile.mkdir();
        } else {
            isCreateSuccess = datagramFile.createNewFile();
            if(isCreateSuccess){ //如果是报文，那么还需要创建对应的报文模板
                String templateFilePath = getTemplatePath();
                File templateFile = new File(templateFilePath);
                isCreateSuccess &= templateFile.createNewFile();
                if(!isCreateSuccess){ //未成功，删除之前创建的报文文件
                    datagramFile.delete();
                }
            }
        }
        return isCreateSuccess;
    }

    /**
     * 删除报文
     * @return 删除是否成功
     */
    public boolean delete() {
        File file = new File(path);
        boolean runResult = true;
        if(file.exists()){
            if(file.isFile()){//如果是文件，那么还需要删除它的template
                String templatePath = getTemplatePath();
                File templateFile = new File(templatePath);
                runResult &= templateFile.delete();
            }
            //删除报文或报文类别文件
            runResult &= file.delete();
            return runResult;
        } else return false;
    }
}
