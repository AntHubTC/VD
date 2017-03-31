package com.tc.vd.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 新增报文窗口控制器
 * Created by tangcheng on 2017/3/31.
 */
public class DatagramAddController extends AppController implements Initializable {
    private static Logger LOG = Logger.getLogger(DatagramAddController.class);

    @FXML
    private TextField nameTxt; //名称
    @FXML
    private ComboBox typesCombo; //类型


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(LOG.isDebugEnabled()){
            LOG.debug("新增报文窗口初始化");
        }

        //初始化类型列表
        ObservableList items = typesCombo.getItems();
        items.add("报文");
        items.add("类别");

        typesCombo.setValue("报文");
    }
}
