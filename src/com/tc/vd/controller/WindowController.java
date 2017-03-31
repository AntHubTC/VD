package com.tc.vd.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by tangcheng on 2017/3/15.
 */
public class WindowController extends AppController implements Initializable {
    public ResourceBundle vdLang = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.vdLang = resources;
    }
    public Stage getStage(){
        StageController stageController = getStageController();
        Stage stage = stageController.getStage(getStageName());
        return stage;
    }

    private double xOffset = 0;
    private double yOffset = 0;
    /**
     * 处理菜单栏拖动事件
     * @param event
     */
    @FXML
    public void handleAppTitleDragAction(MouseEvent event){
        Stage stage = getStage();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    /**
     * 处理菜单栏鼠标按下事件
     * @param event
     */
    @FXML
    public void handleAppTitleMousePressed(MouseEvent event){
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    /**
     * 最小化按钮处理
     */
    @FXML
    public void handleMinimizeWinBtnClick(MouseEvent event){
        Stage stage = getStage();
        stage.setIconified(true);
    }

    private double oldX = 0.0d;
    private double oldY = 0.0d;
    private double oldWidth = 0.0d;
    private double oldHeight = 0.0d;
    private boolean isMax = false;
    private boolean isFirst = true;
    @FXML
    public void handleMaximizeWinBtnClick(MouseEvent event){
        Stage stage = getStage();
        if(isFirst){
            oldX = stage.getX();
            oldY = stage.getY();
            oldWidth = stage.getWidth();
            oldHeight = stage.getHeight();
            isFirst = false;
        }

        if(isMax){
            //设置最小化
            stage.setX(oldX);
            stage.setY(oldY);
            stage.setWidth(oldWidth);
            stage.setHeight(oldHeight);
            isMax = false;
        } else {
            //设置最大化
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX(primaryScreenBounds.getMinX());
            stage.setY(primaryScreenBounds.getMinY());
            stage.setWidth(primaryScreenBounds.getWidth());
            stage.setHeight(primaryScreenBounds.getHeight());
            isMax = true;
        }
    }

    /**
     * 处理窗口打开
     * @param event
     */
    @FXML
    public void handleOpenWinAction(Event event){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getStage().hide();
            }
        });
    }

    /**
     * 菜单关闭菜单项处理
     * @param event
     */
    @FXML
    public void handleCloseWinBtnClick(Event event){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage stage = getStage();
                Stage primaryStage = getPrimaryStage();
                if(stage != primaryStage){
                    primaryStage.show();
                }
                stage.hide();
            }
        });
        //Event.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    private boolean isFullScreen = false;
    /**
     * 设置全屏
     * @param event
     */
    @FXML
    public void setFullScreanAction(ActionEvent event){
        MenuItem target = (MenuItem) event.getTarget();
        Stage primaryStage = getPrimaryStage();
        String menuItemTxt = target.getText();

        if(!isFullScreen){
            String exitFullScreenTxt = vdLang.getString("app.menu.view.exitfullscreen");

            primaryStage.setFullScreen(isFullScreen = true);
            target.setText(exitFullScreenTxt);
        } else {
            String fullScreenTxt = vdLang.getString("app.menu.view.fullscreen");

            primaryStage.setFullScreen(isFullScreen = false);
            target.setText(fullScreenTxt);
        }
    }

    /**
     * 设置皮肤
     * @param event
     */
    @FXML
    public void setSkinAction(ActionEvent event){
        MenuItem target = (MenuItem) event.getTarget();
        String menu = target.getText();
        System.out.println(menu);

        Scene primaryStageScene = getPrimaryStageScene();

        if("caspian".equals(menu)){
            ObservableList<String> stylesheets = primaryStageScene.getStylesheets();
            stylesheets.removeAll(stylesheets);
        } else if("modena".equals(menu)){

        }
    }
}
