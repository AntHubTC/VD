package com.tc.vd.ui.control.monologfx;

import javafx.beans.property.*;
import javafx.scene.control.ControlBuilder;
import javafx.util.Builder;

import java.util.HashMap;

/**
 *
 * @author Mark Heckler (mark.heckler@gmail.com, @MkHeck)
 */
public class MonologFXButtonBuilder<B extends MonologFXButtonBuilder<B>> extends ControlBuilder<B> implements Builder<MonologFXButton> {
    private HashMap<String, Property> properties = new HashMap<>();

    protected MonologFXButtonBuilder() {
    }

    /**
     * Creates and returns a MonologFXButton builder object upon which 
     * to set properties and eventually, create a MonologFXButton for use with
     * a MonologFX dialog.
     */
    public static MonologFXButtonBuilder create() {
        return new MonologFXButtonBuilder();
    }

    /**
     * Sets the type of this button.
     * 
     * @param type MonologFXButton.Type designation.
     * 
     * @see MonologFXButton.Type
     */
    public final MonologFXButtonBuilder type(final MonologFXButton.Type type) {
        properties.put("type", new SimpleObjectProperty<>(type));
        return this;
    }

    /**
     * Sets the label text for the button.
     * 
     * To assign a shortcut key, simply place an underscore character ("_")
     * in front of the desired shortcut character.
     * 
     * @param label String consisting of the desired button text.
     */    
    public final MonologFXButtonBuilder label(final String label) {
        properties.put("label", new SimpleStringProperty(label));
        return this;
    }

    /**
     * Sets the graphic for use on the button, either alone or with text.
     * Graphic format must be .png, .jpg (others?) supported by ImageView.
     * 
     * @param icon String containing the location and name of a graphic file
     *      (.png, .jpg) for use as an icon on the button face.
     *
     * @see javafx.scene.image.ImageView
     */
    public final MonologFXButtonBuilder icon(final String icon) {
        properties.put("icon", new SimpleStringProperty(icon));
        return this;
    }

    /**
     * Designates this button as the "default" button - or not.
     * 
     * @param defaultButton Boolean.
     */
    public final MonologFXButtonBuilder defaultButton(final boolean defaultButton) {
        properties.put("defaultButton", new SimpleBooleanProperty(defaultButton));
        return this;
    }

    /**
     * Designates this button as the "cancel" button - or not.
     * 
     * @param cancelButton Boolean.
     */    
    public final MonologFXButtonBuilder cancelButton(final boolean cancelButton) {
        properties.put("cancelButton", new SimpleBooleanProperty(cancelButton));
        return this;
    }

    /**
     * This is where the button is created/assembled. Returns a MonologFXButton
     * object, ready to add to a MonologFX dialog.
     * 
     * @return MonologFXButton
     */
    @Override
    public MonologFXButton build() {
        final MonologFXButton fxBtn = new MonologFXButton();

        for (String key : properties.keySet()) {
            switch (key) {
                case "type":
                    fxBtn.setType(((ObjectProperty<MonologFXButton.Type>) properties.get(key)).get());
                    break;
                case "label":
                    fxBtn.setLabel(((StringProperty) properties.get(key)).get());
                    break;
                case "icon":
                    fxBtn.setIcon(((StringProperty) properties.get(key)).get());
                    break;
                case "defaultButton":
                    fxBtn.setDefaultButton(((BooleanProperty) properties.get(key)).get());
                    break;
                case "cancelButton":
                    fxBtn.setCancelButton(((BooleanProperty) properties.get(key)).get());
                    break;
            }
        }

        return fxBtn;
    }
}
