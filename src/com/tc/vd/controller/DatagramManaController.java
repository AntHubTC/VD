package com.tc.vd.controller;

import com.tc.vd.VdApplication;
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
        TreeItem<String> root = new TreeItem<String>("Root Node");
        root.setExpanded(true);
        root.getChildren().addAll(
                new TreeItem<String>("Item 1"),
                new TreeItem<String>("Item 2"),
                new TreeItem<String>("Item 3")
        );
        treeView.setRoot(root);
    }
}
