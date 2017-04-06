package com.tc.vd.controller;

import com.tc.vd.utils.KeyValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
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
    public TextField nameTxt; //名称
    @FXML
    public ComboBox<KeyValue<Integer, String>> typesCombo; //类型

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(LOG.isDebugEnabled()){
            LOG.debug("新增报文窗口初始化");
        }

        //初始化类型列表
        ObservableList<KeyValue<Integer, String>> items = typesCombo.getItems();
        items.add(new KeyValue<Integer, String>(1, "报文"));
        items.add(new KeyValue<Integer, String>(2, "分类"));

        typesCombo.setConverter(new StringConverter<KeyValue<Integer, String>>() {
            @Override
            public String toString(KeyValue<Integer, String> keyValue) {
                return keyValue.getValue();
            }
            @Override
            public KeyValue<Integer, String> fromString(String value) {
                for (KeyValue<Integer, String> item : items) {
                    if(item.getValue().equals(value)){
                        return item;
                    }
                }
                return null;
            }
        });

        //默认选中报文
        typesCombo.setValue(items.get(0));
    }
}
