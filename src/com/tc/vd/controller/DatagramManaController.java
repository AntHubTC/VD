package com.tc.vd.controller;

import com.tc.vd.config.ResConstant;
import com.tc.vd.model.ContactGoalConfig;
import com.tc.vd.model.Datagram;
import com.tc.vd.net.IPContact;
import com.tc.vd.net.PSocketClient;
import com.tc.vd.service.AddressBook;
import com.tc.vd.service.DatagramMana;
import com.tc.vd.ui.control.monologfx.MonologFX;
import com.tc.vd.utils.KeyValue;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * 报文管理
 * Created by tangcheng on 2017/3/28.
 */
public class DatagramManaController extends WindowController implements Initializable {
    private static Logger LOG = Logger.getLogger(DatagramManaController.class);

    private StringProperty datagramRevTextProp;

    @FXML
    private Button addDataGram;
    @FXML
    private Button delDataGram;
    @FXML
    private TreeView datagramTreeView;
    @FXML
    private TextArea datagramText;
    @FXML
    private TextArea datagramTemplateText;
    @FXML
    private ComboBox<ContactGoalConfig> addressCombo;
    @FXML
    private TextField sendTimesTxt;
    @FXML
    private TextArea datagramRevText;
    @FXML
    private Label currentDatagramTitle;


    /**
     * 初始化
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
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
            datagramTreeView.setRoot(dataTree);
            datagramTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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

            //初始化地址下拉列表
            ObservableList items = AddressBook.getInstance().getAddressBookList();
            addressCombo.setItems(items);
            addressCombo.setConverter(new StringConverter<ContactGoalConfig>() {
                @Override
                public String toString(ContactGoalConfig contactGoalConfig) {
                    return contactGoalConfig.getName();
                }

                @Override
                public ContactGoalConfig fromString(String name) {
                    ContactGoalConfig contactGoalConfig = AddressBook.getInstance().getAddressBookByName(name);
                    return contactGoalConfig;
                }
            });
//            采用下面这种设置值到显示中不是很好的方案，应该采用setConverter设置转换器的方式
//            addressCombo.setCellFactory(new Callback<ListView<ContactGoalConfig>, ListCell<ContactGoalConfig>>() {
//                @Override
//                public ListCell<ContactGoalConfig> call(ListView<ContactGoalConfig> param) {
//                    Object userData = param.getUserData();
//                    return new ListCell<ContactGoalConfig>(){
//                        @Override
//                        protected void updateItem(ContactGoalConfig item, boolean empty) {
//                            super.updateItem(item, empty);
//                            if (item == null || empty) {
//                                setGraphic(null);
//                            } else {
//                                //方法一
//                                setText(item.getName());
//                                //方法二
////                                Text text = new Text(item.getName());
////                                setGraphic(text);
//                            }
//                        }
//                    };
//                }
//            });

            //初始化发送次数
            sendTimesTxt.setText("1");

            //双向绑定
            datagramRevTextProp = new SimpleStringProperty();
            datagramRevText.textProperty().bind(datagramRevTextProp);
            datagramRevTextProp.setValue("");

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("报文管理窗口初始化报错", e);
        }
    }

    /**
     * 处理新增报文或报文目录
     * @param event
     */
    @FXML
    public void handleNewDragramClick(MouseEvent event){
        try {
            UIResourceEnum datagramAdd = UIResourceEnum.DATAGRAM_ADD;
            URL datagramAddUrl = new File(datagramAdd.getUiResource()).toURI().toURL();
            FXMLLoader loader = new FXMLLoader(datagramAddUrl, vdLang);
            Object root = loader.load();
            DatagramAddController datagramAddController = loader.getController();

            MonologFX monologFX = new MonologFX(MonologFX.Type.INFO);
            String addDatagramWinTitle = vdLang.getString("app.datagramManafun.addWinTitle");
            monologFX.setTitleText(addDatagramWinTitle);
            monologFX.setCenterContent((Node) root);
            monologFX.setOkEventHandler(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        String newDatagramName = datagramAddController.nameTxt.getText();
                        KeyValue<Integer, String> comboSelectedItem = datagramAddController.typesCombo.getSelectionModel().getSelectedItem();
                        if(null == newDatagramName || "".equals(newDatagramName)){
                            //todo: 校验不通过提示
                            return;
                        }
                        if(null == comboSelectedItem){
                            //todo: 如果没有选择报文的类型提示
                            return;
                        }
                        Integer key = comboSelectedItem.getKey();
                        TreeItem<Datagram> treeSelectItem = (TreeItem<Datagram>) datagramTreeView.getSelectionModel().getSelectedItem();
                        Datagram selectDatagram = treeSelectItem.getValue();
                        String selectPath = selectDatagram.getPath();
                        String selectFileName = selectDatagram.getFileName();
                        boolean isSelectDatagramXml = selectFileName.endsWith(".xml");
                        if(isSelectDatagramXml){
                            //如果是xml文件，那么就是同级目录
                            selectPath = selectPath.substring(0, selectPath.length() - selectFileName.length());
                        }
                        ObservableList<TreeItem<Datagram>> children = treeSelectItem.getChildren();
                        TreeItem<Datagram> datagramTreeItem = new TreeItem<>();
                        String path = selectPath + File.separator + newDatagramName;
                        Datagram datagram = null;
                        boolean isSuccess = false;
                        if(1 == key){ //报文
                            newDatagramName += ".xml";
                            path += ".xml";
                            datagram = new Datagram(path, newDatagramName);
                            isSuccess = datagram.createFile(false); //创建文件
                        } else if(2 == key){ //类别
                            datagram = new Datagram(path, newDatagramName);
                            isSuccess = datagram.createFile(true); //创建文件
                        }

                        if (!isSuccess) {
                            //todo: 未成功提示
                            return;
                        }

                        //展开新建的目录树
                        datagramTreeItem.setValue(datagram);
                        if(isSelectDatagramXml){
                            treeSelectItem.getParent().getChildren().add(datagramTreeItem);
                            //展开父目录
                            treeSelectItem.getParent().setExpanded(true);
                        } else {
                            children.add(datagramTreeItem);
                            //展开当前目录
                            treeSelectItem.setExpanded(true);
                        }
                    } catch (Exception e) {
                        LOG.error("新增报文确定后报错:", e);
                        e.printStackTrace();
                    }
                }
            });
            monologFX.addOKButton();
            monologFX.setButtonAlignment(MonologFX.ButtonAlignment.RIGHT);
            monologFX.show();
        } catch (Exception e){
            LOG.error("新增报文处理报错:", e);
            e.printStackTrace();
        }
    }

    /**
     * 处理删除报文或报文目录
     * @param event
     */
    @FXML
    public void handleDelDragramClick(MouseEvent event){
        try {
            TreeItem<Datagram> selectedItem = (TreeItem<Datagram>) datagramTreeView.getSelectionModel().getSelectedItem();
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
            monologFX.setTitleText(vdLang.getString("app.datagramManafun.delWinTitle"));
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
        } catch (Exception e) {
            LOG.error("删除报文处理报错:", e);
            e.printStackTrace();
        }
    }

    /**
     * 地址下拉框改变处理
     * @param actionEvent
     */
    public void handleAddressComboChange(ActionEvent actionEvent) {
        ContactGoalConfig selectedItem = addressCombo.getSelectionModel().getSelectedItem();

        LOG.info("地址列表选择了：" + selectedItem);
    }

    /**
     * 发送按钮单击处理
     * @param event
     */
    public void handleSendClick(MouseEvent event) {
        try {
            //要发送的报文内容
            String datagram = datagramText.getText();
            //当前选中的地址
            ContactGoalConfig selectedItem = addressCombo.getSelectionModel().getSelectedItem();
            //发送的次数
            String sendTimesStr = sendTimesTxt.getText();
            Integer sendTiems;

            //发送校验
            boolean valid = true;
            if(null == datagram || "".equals(datagram.trim())){
                //todo:如果没有填写任何报文,提示用户填写

                valid &= false;
            }
            if(null == selectedItem){
                //todo:如果没有选择地址项，提示用户填写

                valid &= false;
            }
            if(null == sendTimesStr || "".equals(sendTimesStr.trim()) || !sendTimesStr.matches("\\d*")){
                //todo:如果没有选择地址项，提示用户填写

                valid &= false;
            }
            if(!valid){ //未校验通过不做以下处理
                return;
            }

            sendTiems = Integer.valueOf(sendTimesStr);

            String encoding = selectedItem.getEncoding();//编码

            do {
                new Thread(new Runnable() {  //启动子线程来发报文接收报文
                    @Override
                    public void run() {
                        IPContact contact = new PSocketClient(selectedItem);
                        try {
                            contact.connect();
                            String revDatagram = contact.psSend(datagram);
                            String text = datagramRevTextProp.getValue();
                            text += revDatagram;
                            text += "============================================\r\n";

                            final String displayText = text;
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    datagramRevTextProp.setValue(displayText);//显示接收报文
                                }
                            });
                        } catch (Exception e) {
                            LOG.error("发送报文出错：", e);
                            e.printStackTrace();

                            //显示到报文接收中
                            String text = datagramRevTextProp.getValue();
                            ByteArrayOutputStream aos = new ByteArrayOutputStream();
                            e.printStackTrace(new PrintStream(aos));
                            String str = new String(aos.toByteArray());
                            text += "发生错误(详情参考日志)：\r\n";
                            text += str + "\r\n";
                            text += "============================================\r\n";

                            final String displayText = text;
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    datagramRevTextProp.setValue(displayText);
                                }
                            });
                        } finally {
                            contact.close();
                        }
                    }
                }).start();
            } while (--sendTiems > 0);
        } catch (Exception e) {
            LOG.error("发送报文处理报错:", e);
            e.printStackTrace();

            //显示到报文接收中
            String text = datagramRevTextProp.getValue();
            ByteArrayOutputStream aos = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(aos));
            String str = new String(aos.toByteArray());
            text += "发生错误(详情参考日志)：\r\n";
            text += str + "\r\n";
            text += "============================================\r\n";
            datagramRevTextProp.setValue(text);
        }
    }

    /**
     * 处理报文树点击事件
     * @param event
     */
    public void handleDatagramTreeClick(MouseEvent event) {
        try {
            TreeItem<Datagram> selectedItem = (TreeItem<Datagram>) datagramTreeView.getSelectionModel().getSelectedItem();
            Datagram datagram = selectedItem.getValue();
            int clickCount = event.getClickCount();
            if(1 == clickCount) {//单击
                System.out.println("");
            } else if(2 == clickCount){//双击
                if(datagram.isDatagramText()){
                    //显示当前报文提示
                    String path = datagram.getPath();
                    String rootPath = ResConstant.rootPath;
                    rootPath += File.separator + "data";
                    path = path.substring(rootPath.length() + 1);
                    path = path.replaceAll("/", ">");
                    path = path.replaceAll("\\\\", ">");
                    currentDatagramTitle.setText(path);
                    //显示报文和报文模板
                    String fileDatagramText = datagram.getDatagramTextContent();
                    datagramText.setText(fileDatagramText);
                }
            };
        } catch (Exception e) {
            LOG.error("报文树点击报错:", e);
            e.printStackTrace();
        }
    }
}
