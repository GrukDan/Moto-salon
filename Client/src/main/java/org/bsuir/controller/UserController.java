package org.bsuir.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bsuir.dto.UserDto;
import org.bsuir.model.Role;
import org.bsuir.model.User;
import org.bsuir.service.RoleService;
import org.bsuir.service.UserService;
import org.bsuir.validation.Patterns;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private List<Role> roles;

    @Setter
    private Stage stage;

    private static final String REQUIRED_FIELD = "Это поле обязательно!";

    @FXML
    private TableView<UserDto> users_tbl;

    @FXML
    private TableColumn<UserDto, String> user_name_col;

    @FXML
    private TableColumn<UserDto, String> user_surname_col;

    @FXML
    private TableColumn<UserDto, String> user_email_col;

    @FXML
    private TableColumn<UserDto, String> user_role_col;

    @FXML
    private JFXTextField user_name_fd;

    @FXML
    private Label user_name_lbl;

    @FXML
    private JFXTextField user_surname_fd;

    @FXML
    private Label user_surname_lbl;

    @FXML
    private JFXTextField user_email_fd;

    @FXML
    private Label user_email_lbl;

    @FXML
    private Button add_btn;

    @FXML
    private Button delete_btn;

    @FXML
    private Button save_btn;

    @FXML
    private JFXComboBox<String> users_combo_box;

    public void initialize() {
        loadUsers();
        loadRoles();

        setUpColumns();
        setEditCommits();

        RequiredFieldValidator requiredValidator = new RequiredFieldValidator(REQUIRED_FIELD);

        Stream.of(user_name_fd, user_surname_fd, user_email_fd, users_combo_box)
                .forEach(control -> {
                    control.getValidators().add(requiredValidator);
                    control.focusedProperty().addListener((o, oldVal, newVal) -> {
                        if (!newVal) control.validate();
                    });
                });
        users_combo_box.getItems().addAll(roles.stream().map(Role::getRoleName).collect(Collectors.toList()));
    }

    private void setEditCommits() {
        user_name_col.setOnEditCommit((TableColumn.CellEditEvent<UserDto, String> tableEvent) -> {
            TablePosition<UserDto, String> pos = tableEvent.getTablePosition();
            String newName = tableEvent.getNewValue();
            int row = pos.getRow();
            UserDto userDto = tableEvent.getTableView().getItems().get(row);
            userDto.setName(newName);
        });

        user_surname_col.setOnEditCommit((TableColumn.CellEditEvent<UserDto, String> tableEvent) -> {
            TablePosition<UserDto, String> pos = tableEvent.getTablePosition();
            String newSurname = tableEvent.getNewValue();
            int row = pos.getRow();
            UserDto userDto = tableEvent.getTableView().getItems().get(row);
            userDto.setSurname(newSurname);
        });

        user_email_col.setOnEditCommit((TableColumn.CellEditEvent<UserDto, String> tableEvent) -> {
            TablePosition<UserDto, String> pos = tableEvent.getTablePosition();
            String newEmail = tableEvent.getNewValue();
            int row = pos.getRow();
            UserDto userDto = tableEvent.getTableView().getItems().get(row);
            userDto.setEmail(newEmail);
        });
    }

    private void setUpColumns() {
        user_name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        user_name_col.setCellFactory(TextFieldTableCell.<UserDto>forTableColumn());

        user_surname_col.setCellValueFactory(new PropertyValueFactory<>("surname"));
        user_surname_col.setCellFactory(TextFieldTableCell.<UserDto>forTableColumn());

        user_email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        user_email_col.setCellFactory(TextFieldTableCell.<UserDto>forTableColumn());

        user_role_col.setCellValueFactory(new PropertyValueFactory<>("roleName"));
        user_role_col.setCellFactory(TextFieldTableCell.<UserDto>forTableColumn());
    }

    private void loadRoles() {
        roles = roleService.findAll();
    }

    private void loadUsers() {
        refreshTable(FXCollections.observableArrayList(userService.getAllDto()));
    }

    private void refreshTable(ObservableList<UserDto> userDtos) {
        users_tbl.getItems().clear();
        users_tbl.setItems(userDtos);
        users_tbl.refresh();
    }

    @FXML
    void add(ActionEvent event) {
        try {
            if (user_name_fd.validate()
                    && user_surname_fd.validate()
                    && user_email_fd.validate()
                    && users_combo_box.validate()) {
                String userName = user_name_fd.getText();
                String userSurname = user_surname_fd.getText();
                String userEmail = user_email_fd.getText();
                String roleName = users_combo_box.getValue();

                if (Pattern.compile(Patterns.USER_NAME_PATTERN).matcher(userName).matches()
                        && Pattern.compile(Patterns.USER_NAME_PATTERN).matcher(userSurname).matches()
                        && Pattern.compile(Patterns.EMAIL_PATTERN).matcher(userEmail).matches()) {
                    User user = User.builder()
                            .name(userName)
                            .surname(userSurname)
                            .email(userEmail)
                            .role(roles.stream().filter(role -> role.getRoleName().equals(roleName)).findFirst().orElseThrow(NullPointerException::new).getIdRole())
                            .build();
                    try {
                        userService.save(user);
                    }catch (Exception exception){
                        errorAlert("Ошибка добавлени! Возможно, пользователь с таким адресом email уже существует");
                        exception.printStackTrace();
                    }
                    clearInputs();
                    loadUsers();
                } else errorAlert("Ошибка введенных данных!");
            } else errorAlert("Ошибка введенных данных!");
        }catch (Exception exception){
            errorAlert("Ошибка. Попробйте в другой раз...");
        }
    }

    private void errorAlert(String message) {
        new Alert(Alert.AlertType.ERROR, message).show();
    }

    @FXML
    void delete(ActionEvent event) {
        UserDto selectedItem = users_tbl.getSelectionModel().getSelectedItem();
        users_tbl.getItems().remove(selectedItem);
    }

    @FXML
    void save(ActionEvent event) {
        List<UserDto> userDtos = users_tbl.getItems();
        userDtos = userService.saveAll(userDtos);
        refreshTable(FXCollections.observableArrayList(userDtos));
    }

    private void clearInputs() {
        user_name_fd.setText("");
        user_surname_fd.setText("");
        user_email_fd.setText("");
    }
}
