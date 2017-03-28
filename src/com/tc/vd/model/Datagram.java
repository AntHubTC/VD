package com.tc.vd.model;

/**
 * 报文
 * Created by tangcheng on 2017/3/28.
 */
public class Datagram {
    public String path; //文件路径
    public String fileName; //文件名

    public Datagram(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return this.fileName;
    }
}
