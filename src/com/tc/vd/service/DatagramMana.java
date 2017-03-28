package com.tc.vd.service;

import com.tc.vd.config.ResConstant;
import com.tc.vd.model.Datagram;
import com.tc.vd.utils.FileResUtil;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;

/**
 * 报文管理
 * Created by tangcheng on 2017/3/28.
 */
public class DatagramMana {
    public Logger LOG = Logger.getLogger(DatagramMana.class);

    //单例存储
    private static DatagramMana datagramMana = null;
    //报文树
    private TreeItem<Datagram> rootTreeItem = null;

    /**
     * 私有构造
     */
    private DatagramMana() {
    }

    /**
     * 获取实例
     * @return
     */
    public static synchronized DatagramMana getInstance(){
        if(null == datagramMana){
            datagramMana = new DatagramMana();
        }
        return datagramMana;
    }

    /**
     *  载入报文数据
     * @return
     */
    public DatagramMana loadData(){
        try {
            File resFile = FileResUtil.getResFile(ResConstant.rootPath, ResConstant.datagramPath);

            Datagram datagram = new Datagram(resFile.getPath(), resFile.getName());
            rootTreeItem = new TreeItem<Datagram>(datagram);
            //展开节点
            rootTreeItem.setExpanded(true);

            //添加子节点
            addDatagram(rootTreeItem, resFile);
        } catch (FileNotFoundException e) {
            LOG.error("没有找到报文的存放目录./data/datagram", e);
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 添加报文
     * @param rootTreeItem
     * @param resFile
     */
    private void addDatagram(TreeItem<Datagram> rootTreeItem, File resFile){
        File[] files = resFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(dir.isDirectory()){
                    return true;
                }
                if(null != name && name.endsWith(".xml")){
                    return true;
                }
                return false;
            }
        });
        //遍历添加子节点
        ObservableList<TreeItem<Datagram>> children = rootTreeItem.getChildren();
        for (File file : files) {
            String filePath = file.getPath();
            String fileName = file.getName();
            Datagram datagram = new Datagram(filePath, fileName);
            TreeItem<Datagram> datagramTreeItem = new TreeItem<Datagram>(datagram);
            //如果是目录继续遍历子目录
            if(file.isDirectory()){
                //如果是目录就展开节点
                datagramTreeItem.setExpanded(true);
                //添加子报文节点
                addDatagram(datagramTreeItem, file);
            }
            //添加当前节点到树中
            children.add(datagramTreeItem);
        }
    }

    public TreeItem<Datagram> getData() throws Exception {
        if (null == rootTreeItem){
            throw new Exception("请先载入数据！~");
        }
        return rootTreeItem;
    }
}
