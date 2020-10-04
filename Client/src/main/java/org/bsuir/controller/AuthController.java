package org.bsuir.controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bsuir.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;


@Controller
@RequiredArgsConstructor
public class AuthController {

    private final MainMenuController mainMenuController;
    private final UserService userService;

    @FXML
    private Label infoLabel;

    @FXML
    private PasswordField password_fd;

    @FXML
    private TextField email_fd;

    @FXML
    private Label infoLabel1;

    @FXML
    private Label infoLabel2;

    @Setter
    private Stage primaryStage;

    @Value("Данное поле обязательно!")
    private String REQUIRED_FIELD;

    public void initialize() {

    }

    protected void launchMainFrame() throws java.io.IOException {
        Stage mainFrameStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/main_menu.fxml"));
        loader.setController(mainMenuController);
        mainFrameStage.setScene(new Scene(loader.load()));
        mainFrameStage.setTitle("Мотосалон");
        mainFrameStage.setOnCloseRequest(e -> primaryStage.show());
        mainFrameStage.showAndWait();
        primaryStage.close();

    }

    @FXML
    @SneakyThrows
    void logIn(ActionEvent event) {
//        String email = email_fd.getText();
//        String password = password_fd.getText();
//        if(userService.auth(email,password)){
//            launchMainFrame();
//        }
        launchMainFrame();
    }
}
