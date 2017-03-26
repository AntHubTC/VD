package com.tc.vd.event;

import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Created by tangcheng on 2017/3/9.
 */
public class WindowsCloseEvent implements EventHandler<WindowEvent> {
    private Stage stage;

    public WindowsCloseEvent(Stage stage){
        this.stage = stage;
    }

    public void handle(WindowEvent event) {
        event.consume();
    }
}
