package org.bsuir.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bsuir.dto.UserDto;
import org.bsuir.service.UserService;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class AuthController {

    private final MainMenuController mainMenuController;
    private final UserOptionController userOptionController;
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

    public void initialize() {

    }

    protected void launchMainFrame() throws java.io.IOException {
        Stage mainFrameStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/main_menu.fxml"));
        loader.setController(mainMenuController);
        mainFrameStage.setScene(new Scene(loader.load()));
        mainFrameStage.setTitle("Мотосалон");
        primaryStage.hide();
        mainFrameStage.showAndWait();
    }

    @FXML
    @SneakyThrows
    void logIn(ActionEvent event) {
        try {
            String email = email_fd.getText().trim();
            String password = password_fd.getText().trim();
            UserDto userDto = userService.auth(email, password);
            if (userDto != null) {
                userOptionController.setAuthorizedUser(userDto);
                launchMainFrame();
            } else infoLabel.setText("Проверьте введенные данные!");
        }catch (Exception e){
            errorAlert("Проверьте введенные");
        }
    }

    private void errorAlert(String message) {
        new Alert(Alert.AlertType.ERROR, message).show();
    }
}
