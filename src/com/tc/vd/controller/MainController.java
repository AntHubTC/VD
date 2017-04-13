package com.tc.vd.controller;

import com.tc.vd.ui.control.monologfx.MonologFX;
import com.tc.vd.utils.FileResUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URL;

/**
 * 主窗口控制器
 */
public class MainController extends WindowController{
    private static Logger LOG = Logger.getLogger(MainController.class);

    @FXML
    public VBox appWindowBox;//应用程序窗口
    @FXML
    public Label appTitle;//应用程序标题
    @FXML
    public AnchorPane mainContainer;

    /**
     * 控制器初始化
     */
    public void init(){
        Stage primaryStage = getPrimaryStage();
        Scene primaryStageScene = getPrimaryStageScene();
        //加载公共样式
        ObservableList<String> stylesheets = primaryStageScene.getStylesheets();
        String commonCssPath = System.getProperty("res.css.skin.path");
        URL commonCssUrl = null;
        try {
            commonCssUrl = FileResUtil.getResUrl(commonCssPath + File.separator + "common.css");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        stylesheets.add(commonCssUrl.toExternalForm());
        //加载私有样式
        String cssUrlStr = UIResourceEnum.PRIMARY_STAGE.getCssResourceUrl().toExternalForm();
        primaryStageScene.getStylesheets().add(cssUrlStr);

        //设置窗口标题
        //String appTitle = System.getProperty("app.title");
        //this.appTitle.setText(appTitle);

        //ObservableList<Node> children = appWindowBox.getChildren();
        //handle(children);

        ObservableList<Node> children = mainContainer.getChildren();
        //Button btn3 = new Button("修改listView<Person>的数据，方式2");
        //children.add(btn3);
    }

    public void handle(ObservableList<Node> children){
        for (Node child : children) {
            if(child instanceof Label){
                Label tl = (Label) child;
                String labelText = tl.getText();

                String regex = "^\\$\\{\\S{1,}\\}$";
                if(null != labelText && !"".equals(labelText) && labelText.matches(regex)){
                    String key = labelText.substring(2,labelText.length()-1);
                    System.out.println(key);
                }
            }
            System.out.println(child);
            if(child instanceof AnchorPane){
                handle(((AnchorPane) child).getChildren());
            }
        }
    }

    /**
     * 关于
     * @param event
     */
    @FXML
    public void handleAboutMenuItemClick(ActionEvent event){
        try {
            UIResourceEnum datagramAdd = UIResourceEnum.VD_ABOUT;
            URL datagramAddUrl = new File(datagramAdd.getUiResource()).toURI().toURL();
            FXMLLoader loader = new FXMLLoader(datagramAddUrl, vdLang);
            Node rootNode = (Node)loader.load();
            Hyperlink projectAddress = (Hyperlink)rootNode.lookup("#projectAddress");
            if(null != projectAddress){
                projectAddress.setOnAction(event1 -> {
                    try {
                        String paddress = projectAddress.getText();
                        URI uri = new URI(paddress);
                        Desktop.getDesktop().browse(uri);
                    } catch (Exception e) {
                        LOG.error("点击发送错误：", e);
                        e.printStackTrace();
                    }
                });
            }
            MonologFX monologFX = new MonologFX(MonologFX.Type.TEMP);
            String addDatagramWinTitle = vdLang.getString("app.datagramManafun.vdAbout");
            monologFX.setTitleText(addDatagramWinTitle);
            monologFX.setCenterContent(rootNode);

            monologFX.show();
        } catch (Exception e){
            LOG.error("发送错误：", e);
            e.printStackTrace();
        }
    }

    /**
     * 处理地址簿单击事件
     * @param event
     */
    @FXML
    public void handleAddressBookBtnClick(MouseEvent event){
        StageController stageController = getStageController();
        stageController.loadStage(UIResourceEnum.ADDRESS_BOOK_STAGE, new StageStyle[]{ StageStyle.UNDECORATED });
        stageController.setStage(UIResourceEnum.ADDRESS_BOOK_STAGE);
        super.handleOpenWinAction(event);
    }

    /**
     * 处理地址簿单击事件
     * @param event
     */
    @FXML
    public void handleDgManaBtnClick(MouseEvent event){
        StageController stageController = getStageController();
        stageController.loadStage(UIResourceEnum.DATAGRAM_MANA_STAGE, new StageStyle[]{ StageStyle.UNDECORATED });
        stageController.setStage(UIResourceEnum.DATAGRAM_MANA_STAGE);
        super.handleOpenWinAction(event);
    }
}
