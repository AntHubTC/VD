package com.tc.vd.ui.control.monologfx;

import com.tc.vd.controller.StageController;
import com.tc.vd.utils.FileResUtil;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 *
 * @author Mark Heckler (mark.heckler@gmail.com, @MkHeck)
 */
public class MonologFXButton {

    /**
     * Type of button, with several built-in options and three custom ones. 
     */
    public enum Type { OK, CANCEL, ABORT, RETRY, IGNORE, YES, NO, CUSTOM1, CUSTOM2, CUSTOM3 };
    private List<String> defLabels = Arrays.asList("_OK", "_Cancel", "_Abort", "_Retry", "_Ignore", "_Yes", "_No", "Custom_1", "Custom_2", "Custom_3");
    
    private HashMap<Type, String> defaultLabels = new HashMap<>();
    private Type type = MonologFXButton.Type.OK;    // Defaults to OK(-type) button
    private String label = "";
    private Node icon;
    private boolean defaultButton = false;
    private boolean cancelButton = false;

    /**
     * Default constructor for a MonologFX button. Plain button,
     * no label or icon and no default or cancel designation(s).
     */
    public MonologFXButton() {
        // Refactor.
        int i = 0;
        for (Type t: Type.values()) {
            defaultLabels.put(t, defLabels.get(i));
            i++;
        }
    }

    /**
     * Returns the type of this button.
     *
     * @return type MonologFXButton.Type designation.
     *
     * @see Type
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets the type of this button.
     *
     * @param type MonologFXButton.Type designation.
     *
     * @see Type
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Returns the appropriate button label according to the following rules:
     *
     * If the developer specifies a label, it returns that text.
     *
     * If not, the button checks for a resource with i18n (internationalization)
     * text to use for this type of button. If it finds the file and the key
     * corresponding to this button type, it returns the i18n value.
     *
     * If none of the above conditions are met, it returns default text.
     *
     * @return label String consisting of the button's text.
     */
    public String getLabel() {
        if ( !label.isEmpty() ) {
            return label;
        } else {
            String labelToReturn = defaultLabels.get(getType());

            try {
                ResourceBundle res = StageController.getInstance().getVdLang();
                if ( res != null ) {
                    labelToReturn = res.getString("app.monolog."+labelToReturn.replaceAll("_", "").toUpperCase());
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            return labelToReturn;
        }
    }

    /**
     * Sets the label text for the button.
     *
     * To assign a shortcut key, simply place an underscore character ("_")
     * in front of the desired shortcut character.
     *
     * @param label String consisting of the desired button text.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Returns the graphic file (if one is assigned) for this button.
     *
     * @return icon Node consisting of the button's graphic element.
     */
    public Node getIcon() {
        return icon;
    }

    /**
     * Sets the graphic for use on the button, either alone or with text.
     * Graphic format must be .png, .jpg (others?) supported by ImageView.
     *
     * @param iconFile String containing the location and name of a graphic file
     *      (.png, .jpg) for use as an icon on the button face.
     *
     * @see javafx.scene.image.ImageView
     */
    public void setIcon(String iconFile) {
        try {
            String skinPath = System.getProperty("res.css.skin.path");
            String resPath = skinPath + "/images/monologfx/" + iconFile;
            File resFile = FileResUtil.getResFile(resPath);
            this.icon = new ImageView(new Image(new FileInputStream(resFile)));
        } catch (Exception e) {
            System.err.println("Exception trying to load button icon:" + e.getMessage());
        }
    }

    /**
     * Indicates if this button is designated as the "default" button.
     * 
     * @return defaultButton Boolean.
     */
    public boolean isDefaultButton() {
        return defaultButton;
    }

    /**
     * Designates this button as the "default" button - or not.
     * 
     * @param defaultButton Boolean.
     */
    public void setDefaultButton(boolean defaultButton) {
        this.defaultButton = defaultButton;
    }

    /**
     * Indicates if this button is designated as the "cancel" button.
     * 
     * @return cancelButton Boolean.
     */
    public boolean isCancelButton() {
        return cancelButton;
    }

    /**
     * Designates this button as the "cancel" button - or not.
     * 
     * @param cancelButton Boolean.
     */
    public void setCancelButton(boolean cancelButton) {
        this.cancelButton = cancelButton;
    }
}
