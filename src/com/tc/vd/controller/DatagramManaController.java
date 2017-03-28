package com.tc.vd.controller;

import com.tc.vd.VdApplication;
import com.tc.vd.model.Datagram;
import com.tc.vd.service.DatagramMana;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 报文管理
 * Created by tangcheng on 2017/3/28.
 */
public class DatagramManaController extends WindowController implements Initializable {
    private static Logger LOG = Logger.getLogger(VdApplication.class);

    /**
     * 报文树
     */
    @FXML
    public TreeView treeView;

    /**
     * 初始化
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        TreeItem<String> root = new TreeItem<String>("Root Node");
//        root.setExpanded(true);
//        root.getChildren().addAll(
//                new TreeItem<String>("Item 1"),
//                new TreeItem<String>("Item 2"),
//                new TreeItem<String>("Item 3")
//        );
        try {
            //获取报文管理实例
            DatagramMana instance = DatagramMana.getInstance();
            //载入数据
            instance.loadData();
            //获取树结构数据
            TreeItem<Datagram> dataTree = instance.getData();
            //显示到树中
            treeView.setRoot(dataTree);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
