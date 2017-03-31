package com.tc.vd.controller;

import com.tc.vd.utils.FileResUtil;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * Created by tangcheng on 2017/3/7.
 */
public enum UIResourceEnum {
    PRIMARY_STAGE(System.getProperty("res.ui.main"), System.getProperty("res.css.main")),
    ADDRESS_BOOK_STAGE(System.getProperty("res.ui.addressbook"), System.getProperty("res.css.addressbook")),
    DATAGRAM_MANA_STAGE(System.getProperty("res.ui.datagramMana"), System.getProperty("res.css.datagramMana")),
    DATAGRAM_ADD(System.getProperty("res.ui.datagramAdd"), System.getProperty("res.css.datagramAdd")),
    ;
    private String uiResource;
    private String styleSheet;
    private StageStyle styles[];

    UIResourceEnum(String uiResource, StageStyle... styles) {
        this(uiResource, "", styles);
    }
    UIResourceEnum(String uiResource, String styleSheet, StageStyle... styles) {
        this.uiResource = System.getProperty("res.ui.path") + uiResource;
        this.styleSheet = System.getProperty("res.css.path") +
                System.getProperty("ui.skin")  + File.separator + styleSheet;
        this.styles = styles;
    }

    public String getUiResource() {
        return uiResource;
    }

    public StageStyle[] getStyles() {
        return styles;
    }

    public String getStyleSheet() {
        return styleSheet;
    }

    public void setStyleSheet(String styleSheet) {
        this.styleSheet = styleSheet;
    }

    public URL getUiResourceUrl() {
        return getResourceUrl(getUiResource());
    }

    public URL getCssResourceUrl() {
        return getResourceUrl(getStyleSheet());
    }

    /**
     * 获取文件位置
     * @return
     */
    private URL getResourceUrl(String res) {
        try {
            return FileResUtil.getResUrl(res);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
