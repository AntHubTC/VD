package com.tc.vd.controller;

/**
 * Created by tangcheng on 2017/3/7.
 */
public interface ControlledStage {
    /**
     * 设置舞台控制器
     * @param stageController
     * @param UIResourceEnum
     */
    public void setStageController(StageController stageController, UIResourceEnum UIResourceEnum);

    /**
     * 获取舞台控制器
     * @return
     */
    public StageController getStageController();

    /**
     * 获取舞台名称
     * @return
     */
    public String getStageName();

    /**
     * 设置舞台名称
     */
    public void setStageName(String stageName);
}
