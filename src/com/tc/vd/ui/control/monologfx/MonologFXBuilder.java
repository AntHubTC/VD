package com.tc.vd.ui.control.monologfx;

import javafx.beans.property.*;
import javafx.scene.control.ControlBuilder;
import javafx.util.Builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Mark Heckler (mark.heckler@gmail.com, @MkHeck)
 * @param <B>
 */
public class MonologFXBuilder<B extends MonologFXBuilder<B>> extends ControlBuilder<B> implements Builder<MonologFX> {
    private final HashMap<String, Property> properties = new HashMap<>();
    private final List<MonologFXButton> buttons = new ArrayList<>();
    private final List<String> stylesheets = new ArrayList<>();
 
    protected MonologFXBuilder() {
    }

    /**
     * Creates and returns a MonologFX dialog box builder object upon which 
     * to set properties and eventually, create a MonologFX dialog box.
     */
    public static MonologFXBuilder create() {
        return new MonologFXBuilder();
    }

    /**
     * Public method used to add a button to a MonologFX dialog.
     * 
     * @param button A MonologFXButton object.
     * 
     * @see MonologFXButton
     */
    public final MonologFXBuilder button(final MonologFXButton button) {
        //properties.put("button", new SimpleObjectProperty<>(BUTTON));
        buttons.add(button);
        return this;
    }

    /**
     * Public method used to add a button to a MonologFX dialog.
     * 
     * @param displayTime A MonologFXButton object.
     * 
     * @see MonologFXButton
     */
    public final MonologFXBuilder displayTime(final int displayTime) {
        properties.put("displayTime", new SimpleIntegerProperty(displayTime));
        return this;
    }
    
    /**
     * Sets the type of MonologFX dialog box to build/display.
     * 
     * @param type One of the supported types of dialogs.
     * @see MonologFX.Type
     */
    public final MonologFXBuilder type(final MonologFX.Type type) {
        properties.put("type", new SimpleObjectProperty<>(type));
        return this;
    }

    /**
     * Sets the button alignment for the MonologFX dialog box. Default is CENTER.
     * 
     * @param alignButtons Valid values are LEFT, RIGHT, and CENTER.
     * 
     * @see MonologFX.ButtonAlignment
     */
    public final MonologFXBuilder buttonAlignment(final MonologFX.ButtonAlignment alignButtons) {
        properties.put("alignbuttons", new SimpleObjectProperty<>(alignButtons));
        return this;
    }

    /**
     * Sets the text displayed within the MonologFX dialog box. Word wrap 
     * ensures that all text is displayed.
     * 
     * @param message String variable containing the text to display.
     */
    public final MonologFXBuilder message(final String message) {
        properties.put("message", new SimpleStringProperty(message));
        return this;
    }

    /**
     * Sets the modality of the MonologFX dialog box to build/display.
     * 
     * @param modal Boolean. A true value = APPLICATION_MODAL, false = NONE.
     */
    public final MonologFXBuilder modal(final boolean modal) {
        properties.put("modal", new SimpleBooleanProperty(modal));
        return this;
    }

    /**
     * Sets the text to be displayed in the title bar of the MonologFX dialog.
     * 
     * @param titleText String containing the text to place in the title bar.
     */
    public final MonologFXBuilder titleText(final String titleText) {
        properties.put("titleText", new SimpleStringProperty(titleText));
        return this;
    }

    /**
     * Sets x coordinate of the MonologFX dialog (if centering is not desired).
     * 
     * @param xCoord Double representing the x coordinate to use for display.
     */
    public final MonologFXBuilder X(final double xCoord) {
        properties.put("xCoord", new SimpleDoubleProperty(xCoord));
        return this;
    }

    /**
     * Sets y coordinate of the MonologFX dialog (if centering is not desired).
     * 
     * @param yCoord Double representing the y coordinate to use for display.
     */
    public final MonologFXBuilder Y(final double yCoord) {
        properties.put("yCoord", new SimpleDoubleProperty(yCoord));
        return this;
    }

    /**
     * Allows developer to add stylesheet(s) for MonologFX dialog, supplementing 
     * or overriding existing styling.
     * 
     * @param stylesheet String variable containing the path/name of the
     * stylesheet to apply to the dialog's scene and contained controls.
     */
    public final MonologFXBuilder stylesheet(final String stylesheet) {
        //properties.put("stylesheet", new SimpleStringProperty(STYLESHEET));
        stylesheets.add(stylesheet);
        return this;
    }

    /**
     * This is where the magic happens...or at least where it all comes 
     * together.  :-) Returns a MonologFX dialog, ready to display with
     * showDialog().
     * 
     * @return MonologFX A dialog.
     */
    @Override
    public MonologFX build() {
        final MonologFX control = new MonologFX();

        for (String key : properties.keySet()) {
            switch (key) {
                case "type":
                    control.setType(((ObjectProperty<MonologFX.Type>) properties.get(key)).get());
                    break;
                case "alignbuttons":
                    control.setButtonAlignment(((ObjectProperty<MonologFX.ButtonAlignment>) properties.get(key)).get());
                    break;
                case "displayTime":
                    control.setDisplayTime(((IntegerProperty) properties.get(key)).get());
                    break;
                case "message":
                    control.setMessage(((StringProperty) properties.get(key)).get());
                    break;
                case "modal":
                    control.setModal(((BooleanProperty) properties.get(key)).get());
                    break;
                case "titleText":
                    control.setTitleText(((StringProperty) properties.get(key)).get());
                    break;
                case "xCoord":
                    control.setX(((DoubleProperty) properties.get(key)).get());
                    break;
                case "yCoord":
                    control.setY(((DoubleProperty) properties.get(key)).get());
                    break;
            }
        }

        for ( MonologFXButton mb : buttons ) {
            control.addButton(mb);
        }
        
        for ( String ss : stylesheets ) {
            control.addStylesheet(ss);
        }
        
        return control;
    }
}
