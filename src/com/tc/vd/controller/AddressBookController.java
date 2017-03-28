package com.tc.vd.controller;

import com.tc.vd.VdApplication;
import com.tc.vd.service.AddressBook;
import com.tc.vd.model.ContactGoalConfig;
import com.tc.vd.ui.control.monologfx.MonologFX;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by tangcheng on 2017/3/26.
 */
public class AddressBookController extends WindowController implements Initializable {
    private static Logger LOG = Logger.getLogger(VdApplication.class);
    //地址簿数据
    private ObservableList<ContactGoalConfig> addressBookList;

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
//    static Integer num = 0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        if(num == 1) return;
//        num++;
//        LOG.info("运行次数:" + num);
        super.initialize(location, resources);

        Callback callbackRtnTFTCStr = new Callback<TableColumn<ContactGoalConfig, String>, TableCell<ContactGoalConfig, String>>() {
            @Override
            public TableCell<ContactGoalConfig, String> call(TableColumn<ContactGoalConfig, String> param) {
                TextFieldTableCell textFieldTableCell = new TextFieldTableCell(new DefaultStringConverter());
                textFieldTableCell.setEditable(true);
//                textFieldTableCell.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                    }
//                });
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
                        textFieldTableCell.startEdit();
                    }
                });
                return textFieldTableCell;
            }
        };
        Callback callbackRtnCBTC = new Callback<TableColumn<ContactGoalConfig, String>, TableCell<ContactGoalConfig, String>>() {
            @Override
            public TableCell<ContactGoalConfig, String> call(TableColumn<ContactGoalConfig, String> param) {
                ComboBoxTableCell comboBoxTableCell = new ComboBoxTableCell();
                ObservableList items = comboBoxTableCell.getItems();
                items.add("tcp");
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

        this.nameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ContactGoalConfig, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ContactGoalConfig, String> event) {
                String newValue = event.getNewValue();
                ObservableList<ContactGoalConfig> items = event.getTableView().getItems();
                int row = event.getTablePosition().getRow();
                ContactGoalConfig contactGoalConfig = items.get(row);

                contactGoalConfig.setName(newValue);
            }
        });
        this.protocolColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ContactGoalConfig, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ContactGoalConfig, String> event) {
                String newValue = event.getNewValue();
                ObservableList<ContactGoalConfig> items = event.getTableView().getItems();
                int row = event.getTablePosition().getRow();
                ContactGoalConfig contactGoalConfig = items.get(row);

                contactGoalConfig.setProtocol(newValue);
            }
        });
        this.ipColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ContactGoalConfig, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ContactGoalConfig, String> event) {
                String newValue = event.getNewValue();
                ObservableList<ContactGoalConfig> items = event.getTableView().getItems();
                int row = event.getTablePosition().getRow();
                ContactGoalConfig contactGoalConfig = items.get(row);

                contactGoalConfig.setIp(newValue);
            }
        });
        this.portColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ContactGoalConfig, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ContactGoalConfig, String> event) {
                String newValue = event.getNewValue();
                ObservableList<ContactGoalConfig> items = event.getTableView().getItems();
                int row = event.getTablePosition().getRow();
                ContactGoalConfig contactGoalConfig = items.get(row);

                contactGoalConfig.setName(newValue);
            }
        });
        this.sendBeginColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ContactGoalConfig, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ContactGoalConfig, Integer> event) {
                Integer newValue = event.getNewValue();
                ObservableList<ContactGoalConfig> items = event.getTableView().getItems();
                int row = event.getTablePosition().getRow();
                ContactGoalConfig contactGoalConfig = items.get(row);

                contactGoalConfig.setSendLenBegin(newValue);
            }
        });
        this.sendLenEndColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ContactGoalConfig, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ContactGoalConfig, Integer> event) {
                Integer newValue = event.getNewValue();
                ObservableList<ContactGoalConfig> items = event.getTableView().getItems();
                int row = event.getTablePosition().getRow();
                ContactGoalConfig contactGoalConfig = items.get(row);

                contactGoalConfig.setSendLenEnd(newValue);
            }
        });
        this.recvLenBeginColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ContactGoalConfig, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ContactGoalConfig, Integer> event) {
                Integer newValue = event.getNewValue();
                ObservableList<ContactGoalConfig> items = event.getTableView().getItems();
                int row = event.getTablePosition().getRow();
                ContactGoalConfig contactGoalConfig = items.get(row);

                contactGoalConfig.setRecvLenBegin(newValue);
            }
        });
        this.recvLenEndColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ContactGoalConfig, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ContactGoalConfig, Integer> event) {
                Integer newValue = event.getNewValue();
                ObservableList<ContactGoalConfig> items = event.getTableView().getItems();
                int row = event.getTablePosition().getRow();
                ContactGoalConfig contactGoalConfig = items.get(row);

                contactGoalConfig.setRecvLenEnd(newValue);
            }
        });
        this.timeOutColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ContactGoalConfig, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ContactGoalConfig, Integer> event) {
                Integer newValue = event.getNewValue();
                ObservableList<ContactGoalConfig> items = event.getTableView().getItems();
                int row = event.getTablePosition().getRow();
                ContactGoalConfig contactGoalConfig = items.get(row);

                contactGoalConfig.setTimeOut(newValue);
            }
        });
        this.encodingColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ContactGoalConfig, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ContactGoalConfig, String> event) {
                String newValue = event.getNewValue();
                ObservableList<ContactGoalConfig> items = event.getTableView().getItems();
                int row = event.getTablePosition().getRow();
                ContactGoalConfig contactGoalConfig = items.get(row);

                contactGoalConfig.setEncoding(newValue);
            }
        });
        this.bufferSizeColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ContactGoalConfig, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ContactGoalConfig, Integer> event) {
                Integer newValue = event.getNewValue();
                ObservableList<ContactGoalConfig> items = event.getTableView().getItems();
                int row = event.getTablePosition().getRow();
                ContactGoalConfig contactGoalConfig = items.get(row);

                contactGoalConfig.setBufferSize(newValue);
            }
        });
        this.commentColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ContactGoalConfig, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ContactGoalConfig, String> event) {
                String newValue = event.getNewValue();
                ObservableList<ContactGoalConfig> items = event.getTableView().getItems();
                int row = event.getTablePosition().getRow();
                ContactGoalConfig contactGoalConfig = items.get(row);

                contactGoalConfig.setComment(newValue);
            }
        });

        //初始化基础数据
        LOG.info("初始化地址簿");
        AddressBook instance = AddressBook.getInstance();
        LOG.info("正在加载地址簿数据开始");
        instance.loadData();
        LOG.info("正在加载地址簿数据完成");
        addressBookList = instance.getAddressBookList();

        this.addressBookView.setItems(addressBookList);
    }

    /**
     * 处理新建按钮单击事件
     * @param event
     */
    @FXML
    public void handleNewBtnClick(MouseEvent event){
        ContactGoalConfig contactGoalConfig = new ContactGoalConfig("","","",8080);
        addressBookList.add(contactGoalConfig);

        //获取最后一行
        int endRowIndex = addressBookView.getItems().size() - 1;
        //获取最后一列
        TableColumn<ContactGoalConfig, ?> LastTableColumn = addressBookView.getColumns().get(0);
        //开始编辑最新一行的第一个单元格
        addressBookView.edit(endRowIndex, LastTableColumn);
        if(LOG.isDebugEnabled()){
            LOG.debug("新增一条编辑项");
        }
    }

    /**
     * 处理删除按钮单击事件
     * @param event
     */
    @FXML
    public void handleDelBtnClick(MouseEvent event){
        TableView.TableViewSelectionModel<ContactGoalConfig> selectionModel = addressBookView.getSelectionModel();
        ContactGoalConfig selectedItem = selectionModel.getSelectedItem();
        if(null == selectedItem){
            //弹出提示框
            MonologFX monologFX = new MonologFX(MonologFX.Type.INFO);
            monologFX.setTitleText("提示");
            monologFX.setMessage("请选择要删除的地址项!~");
//            monologFX.setDisplayTime(2);

            monologFX.show();
        }
        if(SelectionMode.SINGLE.equals(selectionModel.getSelectionMode())){//如果是单行删除
            addressBookList.remove(selectedItem);

            if(LOG.isDebugEnabled()){
                LOG.debug("处理地址簿删除数据，删除地址信息：" + selectedItem);
            }
        }
    }

    /**
     * 处理保存按钮单击事件
     * @param event
     */
    @FXML
    public void handleSaveBtnClick(MouseEvent event){
        AddressBook instance = AddressBook.getInstance();
        if(LOG.isDebugEnabled()){
            LOG.debug("处理地址簿持久化");
        }
        instance.persistData();//持久化地址簿数据

        //弹出提示框，提示保存成功
        MonologFX monologFX = new MonologFX(MonologFX.Type.INFO);
        monologFX.setTitleText("提示");
        monologFX.setMessage("保存成功!~");
        monologFX.setDisplayTime(2);

        monologFX.show();
    }
}
