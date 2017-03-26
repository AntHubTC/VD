package com.tc.vd.control;

import com.tc.vd.addressBook.ContactGoalConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by tangcheng on 2017/3/26.
 */
public class AddressBookController extends WindowController implements Initializable {
    //地址簿数据列表
    public ObservableList<ContactGoalConfig> addressBookList = FXCollections.observableArrayList();
    @FXML
    public TableView<ContactGoalConfig> addressBookView;//地址簿管理界面
    @FXML
    public TableColumn<ContactGoalConfig, String> nameColumn; //地址名称
    @FXML
    public TableColumn<ContactGoalConfig, String> protocolColumn; //协议
    @FXML
    public TableColumn<ContactGoalConfig, String> ipColumn; //ip
    @FXML
    public TableColumn<ContactGoalConfig, String> portColumn; //端口
    @FXML
    public TableColumn<ContactGoalConfig, Integer> sendBeginColumn; //发送开始位置
    @FXML
    public TableColumn<ContactGoalConfig, Integer> sendLenEndColumn; //发送结束位置
    @FXML
    public TableColumn<ContactGoalConfig, Integer> recvLenBeginColumn; //接收开始位置
    @FXML
    public TableColumn<ContactGoalConfig, Integer> recvLenEndColumn; //接收结束位置
    @FXML
    public TableColumn<ContactGoalConfig, Integer> timeOutColumn; //超时时间
    @FXML
    public TableColumn<ContactGoalConfig, String> encodingColumn; //编码
    @FXML
    public TableColumn<ContactGoalConfig, Integer> bufferSizeColumn; //缓冲大小
    @FXML
    public TableColumn<ContactGoalConfig, String> commentColumn; //备注

    /**
     * 初始化
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        Callback callbackRtnTFTCStr = new Callback<TableColumn<ContactGoalConfig, String>, TableCell<ContactGoalConfig, String>>() {
            @Override
            public TableCell<ContactGoalConfig, String> call(TableColumn<ContactGoalConfig, String> param) {
                TextFieldTableCell textFieldTableCell = new TextFieldTableCell();
                textFieldTableCell.setEditable(true);
                textFieldTableCell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //textFieldTableCell.startEdit();
                    }
                });
                return textFieldTableCell;
            }
        };
        Callback callbackRtnTFTCInt = new Callback<TableColumn<ContactGoalConfig, String>, TableCell<ContactGoalConfig, String>>() {
            @Override
            public TableCell<ContactGoalConfig, String> call(TableColumn<ContactGoalConfig, String> param) {
                TextFieldTableCell textFieldTableCell = new TextFieldTableCell(new IntegerStringConverter());
                textFieldTableCell.setEditable(true);
                textFieldTableCell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //textFieldTableCell.startEdit();
                    }
                });
                return textFieldTableCell;
            }
        };
        Callback callbackRtnCBTC = new Callback<TableColumn<ContactGoalConfig, String>, TableCell<ContactGoalConfig, String>>() {
            @Override
            public TableCell<ContactGoalConfig, String> call(TableColumn<ContactGoalConfig, String> param) {
                ComboBoxTableCell comboBoxTableCell = new ComboBoxTableCell();
                comboBoxTableCell.setEditable(true);
                return comboBoxTableCell;
            }
        };

        //设置单元格值工厂
        this.nameColumn.setCellValueFactory(
                new PropertyValueFactory<ContactGoalConfig, String>("name"));
        this.protocolColumn.setCellValueFactory(
                new PropertyValueFactory<ContactGoalConfig, String>("protocol"));
        this.ipColumn.setCellValueFactory(
                new PropertyValueFactory<ContactGoalConfig, String>("ip"));
        this.portColumn.setCellValueFactory(
                new PropertyValueFactory<ContactGoalConfig, String>("port"));
        this.sendBeginColumn.setCellValueFactory(
                new PropertyValueFactory<ContactGoalConfig, Integer>("sendLenBegin"));
        this.sendLenEndColumn.setCellValueFactory(
                new PropertyValueFactory<ContactGoalConfig, Integer>("sendLenEnd"));
        this.recvLenBeginColumn.setCellValueFactory(
                new PropertyValueFactory<ContactGoalConfig, Integer>("recvLenBegin"));
        this.recvLenEndColumn.setCellValueFactory(
                new PropertyValueFactory<ContactGoalConfig, Integer>("recvLenEnd"));
        this.timeOutColumn.setCellValueFactory(
                new PropertyValueFactory<ContactGoalConfig, Integer>("timeOut"));
        this.encodingColumn.setCellValueFactory(
                new PropertyValueFactory<ContactGoalConfig, String>("encoding"));
        this.bufferSizeColumn.setCellValueFactory(
                new PropertyValueFactory<ContactGoalConfig, Integer>("bufferSize"));
        this.commentColumn.setCellValueFactory(
                new PropertyValueFactory<ContactGoalConfig, String>("comment"));

        //单元格工厂
        //this.nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.nameColumn.setCellFactory(callbackRtnTFTCStr);
        this.protocolColumn.setCellFactory(callbackRtnCBTC);
        this.ipColumn.setCellFactory(callbackRtnTFTCStr);
        this.portColumn.setCellFactory(callbackRtnTFTCInt);
        this.sendBeginColumn.setCellFactory(callbackRtnTFTCInt);
        this.sendLenEndColumn.setCellFactory(callbackRtnTFTCInt);
        this.recvLenBeginColumn.setCellFactory(callbackRtnTFTCInt);
        this.recvLenEndColumn.setCellFactory(callbackRtnTFTCInt);
        this.timeOutColumn.setCellFactory(callbackRtnTFTCInt);
        this.encodingColumn.setCellFactory(callbackRtnTFTCStr);
        this.bufferSizeColumn.setCellFactory(callbackRtnTFTCInt);
        this.commentColumn.setCellFactory(callbackRtnTFTCStr);

        //设置排序
        this.nameColumn.setSortable(true);
        this.protocolColumn.setSortable(true);
        this.ipColumn.setSortable(true);
        this.portColumn.setSortable(true);
        this.sendBeginColumn.setSortable(false);
        this.sendLenEndColumn.setSortable(false);
        this.recvLenBeginColumn.setSortable(false);
        this.recvLenEndColumn.setSortable(false);
        this.timeOutColumn.setSortable(false);
        this.encodingColumn.setSortable(true);
        this.bufferSizeColumn.setSortable(false);
        this.commentColumn.setSortable(false);

        addressBookList.add(new ContactGoalConfig("地址簿测试1","http","127.0.0.1",8081));
        addressBookList.add(new ContactGoalConfig("地址簿测试2","http","127.0.0.2",8081));

        this.addressBookView.setItems(addressBookList);
    }

    /**
     * 处理新建按钮单击事件
     * @param event
     */
    @FXML
    public void handleNewBtnClick(MouseEvent event){

    }


}
