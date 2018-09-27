package com.sirding.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * TODO
 *
 * @author zc.ding
 * @create 2018/9/13
 */
public class HelloWorld extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Label label = new Label("Hello wrold JavaFX");
        label.setFont(new Font(10));
        stage.setScene(new Scene(label));
        stage.setTitle("Hello");
        stage.setWidth(500);
        stage.setHeight(500);
        stage.show();
    }
}
