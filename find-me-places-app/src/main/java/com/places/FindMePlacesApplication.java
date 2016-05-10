package com.places;

import com.places.GUI.MainLayout;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

@Lazy
@SpringBootApplication
public class FindMePlacesApplication extends AbstractJavaFxApplicationSupport {

    @Autowired
    private MainLayout mainLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        {
            primaryStage.setTitle("Find Me Places..");
            primaryStage.setWidth(1000);
            primaryStage.setHeight(1000);

            primaryStage.setScene(new Scene(mainLayout));
            primaryStage.setResizable(true);
            primaryStage.centerOnScreen();
            primaryStage.show();
        }
    }


    public static void main(String[] args) {
        launchApp(FindMePlacesApplication.class, args);
    }
}
