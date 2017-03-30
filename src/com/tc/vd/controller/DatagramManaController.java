package com.tc.vd.controller;

import com.sun.javafx.event.EventDispatchChainImpl;
import com.sun.javafx.event.EventDispatchTreeImpl;
import com.tc.vd.VdApplication;
import com.tc.vd.model.Datagram;
import com.tc.vd.service.AddressBook;
import com.tc.vd.service.DatagramMana;
import com.tc.vd.ui.control.monologfx.MonologFX;
import com.tc.vd.ui.control.monologfx.MonologFXButton;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 报文管理
 * Created by tangcheng on 2017/3/28.
 */
public class DatagramManaController extends WindowController implements Initializable {
    private static Logger LOG = Logger.getLogger(VdApplication.class);

    @FXML
    private Button addDataGram;
    @FXML
    private Button delDataGram;
    @FXML
    private TreeView treeView;

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
            treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                TreeItem selectedNode = (TreeItem) newValue;
//                Datagram datagram = (Datagram) (selectedNode).getValue();
//                String path = datagram.getPath();
//                String fileName = datagram.getFileName();
                if(null == selectedNode){
                    return;
                }
                addDataGram.setDisable(false);
                delDataGram.setDisable(false);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理新增报文或报文目录
     * @param event
     */
    @FXML
    public void handleNewDragramClick(MouseEvent event){

    }

    /**
     * 处理删除报文或报文目录
     * @param event
     */
    @FXML
    public void handleDelDragramClick(MouseEvent event){
        TreeItem<Datagram> selectedItem = (TreeItem<Datagram>) treeView.getSelectionModel().getSelectedItem();
        Datagram selectedDatagram = selectedItem.getValue();
        TreeItem<Datagram> parent = selectedItem.getParent();

        String nodeName = "";
        if(null == parent) {//如果是根节点，删除所有的子节点
            nodeName = "所有";
        } else {//否则删除该子节点
            if(0 == selectedItem.getChildren().size()){ //单个子节点
                nodeName = selectedDatagram.getFileName();
            } else { //包含子节点
                nodeName = selectedDatagram.getFileName() + "以及子";
            }
        }

        //弹出提示框，提示保存成功
        MonologFX monologFX = new MonologFX(MonologFX.Type.QUESTION);
        monologFX.setTitleText("提示");
        monologFX.setMessage("你确定要删除" + nodeName + "节点吗？");
        monologFX.setOkEventHandler(event1 -> {
            //点击“确定”要进行的处理
            //1.删除持久层
            DatagramMana instance = DatagramMana.getInstance();
            instance.delete(selectedItem);
            //2.删除界面上的节点
            ObservableList<TreeItem<Datagram>> childTreeItems;
            if(null == parent) {//如果是根节点，删除所有的子节点
                childTreeItems = selectedItem.getChildren();
                childTreeItems.removeAll(childTreeItems);
            } else {//否则删除该子节点
                if(0 == selectedItem.getChildren().size()){ //单个子节点
                } else { //包含子节点
                }
                childTreeItems = parent.getChildren();
                childTreeItems.remove(selectedItem);
            }
        });
        monologFX.setNoEventHandler(event1 -> {
            //点击"取消"要执行的处理
        });
        monologFX.addYesNoButtons();
        monologFX.show();
    }
}
