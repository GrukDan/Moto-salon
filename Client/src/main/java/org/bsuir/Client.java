package org.bsuir;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bsuir.config.AppConfig;
import org.bsuir.controller.AuthController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Client extends Application {

    ApplicationContext context;

    @Override
    public void start(Stage primaryStage) throws Exception{
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/auth.fxml"));
        AuthController authController = context.getBean("authController", AuthController.class);
        authController.setPrimaryStage(primaryStage);
        loader.setController(authController);
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setTitle("Авторизация");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
