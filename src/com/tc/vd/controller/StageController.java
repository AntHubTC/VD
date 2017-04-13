package com.tc.vd.controller;

import com.tc.vd.utils.FileResUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by tangcheng on 2017/3/3.
 */
public class StageController {
    private static StageController stageController = new StageController();
    //建立一个专门存储Stage的Map，全部用于存放Stage对象
    private HashMap<String, Stage> stages = new HashMap<String, Stage>();
    //国际化资源
    public ResourceBundle vdLang = null;

    public static StageController getInstance(){
        return stageController;
    }
    /**
     * 将加载好的Stage放到Map中进行管理
     *
     * @param stageEnum  设定Stage资源
     * @param stage Stage的对象
     */
    public void addStage(UIResourceEnum stageEnum, Stage stage) {
        stages.put(stageEnum.name(), stage);
    }

    /**
     * 通过Stage名称获取Stage对象
     *
     * @param stageEnum Stage枚举
     * @return 对应的Stage对象
     */
    public Stage getStage(UIResourceEnum stageEnum) {
        return stages.get(stageEnum.name());
    }

    /**
     * 通过Stage名称获取Stage对象
     *
     * @param stageName
     * @return 对应的Stage对象
     */
    public Stage getStage(String stageName) {
        return stages.get(stageName);
    }

    /**
     * 将主舞台的对象保存起来，这里只是为了以后可能需要用，目前还不知道用不用得上
     *
     * @param primaryStage     主舞台对象，在Start()方法中由JavaFx的API建立
     */
    public void setPrimaryStage(Stage primaryStage) throws FileNotFoundException {
        this.addStage(UIResourceEnum.PRIMARY_STAGE, primaryStage);
        this.loadIcon(primaryStage);
        for (StageStyle style : UIResourceEnum.PRIMARY_STAGE.getStyles()) {
            primaryStage.initStyle(style);
        }
    }

    public Stage getPrimaryStage() {
        return this.getStage(UIResourceEnum.PRIMARY_STAGE);
    }

    /**
     * 加载icon图标
     * @param stage
     * @throws FileNotFoundException
     */
    private void loadIcon(Stage stage) throws FileNotFoundException {
        String icon = System.getProperty("res.css.skin.path") + File.separator + "images/VD.png";
        File iconFile = new File(icon);
        Image iconImg = new Image(new FileInputStream(iconFile));
        stage.getIcons().add(iconImg);//这里可以设置标题图标也可以设置任务栏图标
    }

    /**
     * 加载窗口地址，需要fxml资源文件属于独立的窗口并用Pane容器或其子类继承
     *
     * @param stageEnum 场景
     * @return 是否加载成功
     */
    public boolean loadStage(UIResourceEnum stageEnum, StageStyle... styles) {
        try {
            //加载FXML资源文件
            URL stageRootUrl = new File(stageEnum.getUiResource()).toURI().toURL();
            FXMLLoader loader = new FXMLLoader(stageRootUrl, vdLang);
            Pane tempPane = (Pane) loader.load();

            //通过Loader获取FXML对应的ViewCtr，并将本StageController注入到ViewCtr中
            ControlledStage controlledStage = (ControlledStage) loader.getController();
            controlledStage.setStageController(this, stageEnum);

            //构造对应的Stage
            Scene tempScene = new Scene(tempPane);
            Stage tempStage = new Stage();
            tempStage.setScene(tempScene);

            //加入icon图标
            loadIcon(tempStage);

            //----------------加载css样式----------
            ObservableList<String> stylesheets = tempScene.getStylesheets();
            //加载公共样式
            URL commonCssUrl = FileResUtil.getResUrl(
                    System.getProperty("res.css.skin.path") + File.separator + "common.css");
            stylesheets.add(commonCssUrl.toExternalForm());
            //加载每个窗体私有样式
            URL cssResUrl = stageEnum.getCssResourceUrl();
            if(null != cssResUrl){
                String cssResPath = cssResUrl.toExternalForm();
                stylesheets.add(cssResPath);
            }
            //配置initStyle
            for (StageStyle style : styles) {
                tempStage.initStyle(style);
            }

            //将设置好的Stage放到HashMap中
            this.addStage(stageEnum, tempStage);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 显示Stage但不隐藏任何Stage
     *
     * @param stageEnum 需要显示的窗口
     * @return 是否显示成功
     */
    public boolean setStage(UIResourceEnum stageEnum) {
        this.getStage(stageEnum).show();
        return true;
    }

    /**
     * 显示Stage并隐藏对应的窗口
     *
     * @param showStage  需要显示的窗口
     * @param closeStage 需要删除的窗口
     * @return
     */
    public boolean setStage(UIResourceEnum showStage, UIResourceEnum closeStage) {
        getStage(closeStage).close();
        setStage(showStage);
        return true;
    }

    /**
     * 在Map中删除Stage加载对象
     *
     * @param name 需要删除的fxml窗口文件名
     * @return 是否删除成功
     */
    public boolean unloadStage(String name) {
        if (stages.remove(name) == null) {
            System.out.println("窗口不存在，请检查名称");
            return false;
        } else {
            System.out.println("窗口移除成功");
            return true;
        }
    }

    public ResourceBundle getVdLang() {
        return vdLang;
    }

    public void setVdLang(ResourceBundle vdLang) {
        this.vdLang = vdLang;
    }
}
