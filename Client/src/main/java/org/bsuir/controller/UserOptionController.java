package org.bsuir.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bsuir.dto.UserDto;
import org.bsuir.service.UserService;
import org.bsuir.validation.Patterns;
import org.springframework.stereotype.Controller;

import java.util.regex.Pattern;
import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor
public class UserOptionController {

    @Setter
    private Stage stage;

    @Setter
    @Getter
    private UserDto authorizedUser;

    private static final String REQUIRED_FIELD = "Это поле обязательно!";

    private final UserService userService;

    @FXML
    private JFXTextField user_name_fd;

    @FXML
    private JFXTextField user_surname_fd;

    @FXML
    private JFXTextField user_email_fd;

    @FXML
    private JFXPasswordField user_password_fd;

    @FXML
    private Button save_btn;

    @FXML
    private Button close_btn;

    public boolean isAdmin(){
        return authorizedUser.getRoleName().equals("ADMIN");
    }

    public void initialize() {
        RequiredFieldValidator requiredValidator = new RequiredFieldValidator(REQUIRED_FIELD);

        Stream.of(user_name_fd, user_surname_fd, user_email_fd, user_password_fd)
                .forEach(control -> {
                    control.getValidators().add(requiredValidator);
                    control.focusedProperty().addListener((o, oldVal, newVal) -> {
                        if (!newVal) control.validate();
                    });
                });
        refreshInputs();

        try {
            user_name_fd.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.length() >= 2 && newValue.length() <= 20) {
                    authorizedUser.setName(newValue);
                } else errorAlert("Ошибка введенных данных имени!");
            });
            user_surname_fd.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.length() >= 2 && newValue.length() <= 20) {
                    authorizedUser.setSurname(newValue);
                } else errorAlert("Ошибка введенных данных фамилии!");
            });
            user_email_fd.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.length() >= 9 && newValue.length() <= 30) {
                    authorizedUser.setEmail(newValue);
                } else errorAlert("Ошибка введенных данных Email!");
            });
            user_password_fd.textProperty().addListener((observable, oldValue, newValue) -> {
                if (user_password_fd.validate() && newValue.length() >= 6 && newValue.length() <= 15) {
                    authorizedUser.setPassword(newValue);
                } else errorAlert("Ошибка введенных данных пароля!");
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void close(ActionEvent event) {
        stage.close();
    }

    @FXML
    void save(ActionEvent event) {
        try {
            if(Pattern.compile(Patterns.USER_NAME_PATTERN).matcher(user_name_fd.getText()).matches()
            && Pattern.compile(Patterns.USER_NAME_PATTERN).matcher(user_surname_fd.getText()).matches()
            && Pattern.compile(Patterns.EMAIL_PATTERN).matcher(user_email_fd.getText()).matches()) {
                authorizedUser = userService.saveDto(authorizedUser);
                refreshInputs();
            }else  errorAlert("Ошибка введенных данных!");
        } catch (Exception exception) {
            exception.printStackTrace();
            errorAlert("Ошибка. Попробйте в другой раз...");
        }
    }

    private void errorAlert(String message) {
        new Alert(Alert.AlertType.ERROR, message).show();
    }

    private void refreshInputs() {
        user_name_fd.setText(authorizedUser.getName());
        user_surname_fd.setText(authorizedUser.getSurname());
        user_email_fd.setText(authorizedUser.getEmail());
        user_password_fd.setText(authorizedUser.getPassword());
    }
}
