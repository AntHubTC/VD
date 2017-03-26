package com.tc.vd.control;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by tangcheng on 2017/3/7.
 */
public abstract class AppController implements ControlledStage{
    private StageController stageController;
    private String stageName;

    @Override
    public void setStageController(StageController stageController, StageEnums stageEnums) {
        this.stageController = stageController;
        this.stageName = stageEnums.name();
    }

    @Override
    public StageController getStageController() {
        return stageController;
    }

    /**
     * 获取主要舞台
     * @return
     */
    public Stage getPrimaryStage(){
        return stageController.getPrimaryStage();
    }

    /**
     * 获取主要舞台场景
     * @return
     */
    public Scene getPrimaryStageScene(){
        return getPrimaryStage().getScene();
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }
}
