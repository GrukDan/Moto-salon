package org.bsuir.controller;


import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bsuir.model.Producer;
import org.bsuir.service.ProducerService;
import org.bsuir.validation.Patterns;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor
public class ProducerController {

    private static final String REQUIRED_FIELD = "Это поле обязательно!";
    private static final String ERROR_INPUT = "Данные введены с ошибкой";
    private final ProducerService producerService;

    @FXML
    private TableView<Producer> producers_tbl;

    @FXML
    private TableColumn<Producer, String> producer_name_col;

    @FXML
    private TableColumn<Producer, String> producer_email_col;

    @FXML
    private TableColumn<Producer, String> producer_number_col;

    @FXML
    private Button save_producers_btn;

    @FXML
    private JFXProgressBar progress_bar;

    @FXML
    private Button close_btn;

    @FXML
    private Button delete_btn;

    @FXML
    private Label producer_name_lbl;

    @FXML
    private Label producer_number_lbl;

    @FXML
    private Label producer_email_lbl;

    @FXML
    private Button save_btn;

    @FXML
    private JFXTextField producer_name_fd;

    @FXML
    private JFXTextField producer_email_fd;

    @FXML
    private JFXTextField producer_number_fd;

    @Setter
    private Stage stage;

    private Timeline timeline;

    @FXML
    void save(ActionEvent event) {
        if (producer_email_fd.validate() && producer_name_fd.validate() && producer_number_fd.validate()) {

            String producer_email = producer_email_fd.getText();
            String producer_name = producer_name_fd.getText();
            String producer_number = producer_number_fd.getText();

            if (Pattern.compile(Patterns.PRODUCER_PRODUCT_NAME_PATTERN).matcher(producer_name).matches()) {
                producer_name_lbl.setText("");
                if (Pattern.compile(Patterns.EMAIL_PATTERN).matcher(producer_email).matches()) {
                    producer_email_lbl.setText("");
                    if (Pattern.compile(Patterns.PRODUCER_NUMBER_PATTERN).matcher(producer_number).matches()) {
                        producer_number_lbl.setText("");
                        Producer producer = Producer.builder()
                                .producerName(producer_name)
                                .email(producer_email)
                                .telephoneNumber(producer_number)
                                .build();
                        producerService.save(producer);
                        clearInputs();
                        loadProducers();
                    } else producer_number_lbl.setText(ERROR_INPUT);
                } else producer_email_lbl.setText(ERROR_INPUT);
            } else producer_name_lbl.setText(ERROR_INPUT);

        } else {
            producer_name_lbl.setText("Заполните поля!");
        }
    }

    @FXML
    void saveProducers(ActionEvent event) {
        List<Producer> producers = producers_tbl.getItems();
        producers = producerService.saveAll(producers);
        refreshTable(FXCollections.observableArrayList(producers));
    }

    private void clearInputs() {
        producer_name_fd.setText("");
        producer_email_fd.setText("");
        producer_number_fd.setText("");
    }

    public void initialize() {
        setUpColumns();
        setEditCommits();

        RequiredFieldValidator requiredValidator = new RequiredFieldValidator(REQUIRED_FIELD);

        Stream.of(producer_email_fd,producer_name_fd,producer_number_fd)
                .forEach(jfxTextField -> {
                    jfxTextField.getValidators().add(requiredValidator);
                    jfxTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
                        if (!newVal) jfxTextField.validate();
                    });
        });

        progress_bar.setProgress(-1.0f);
        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(progress_bar.progressProperty(), 0), new KeyValue(progress_bar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(2), new KeyValue(progress_bar.progressProperty(), 1), new KeyValue(progress_bar.progressProperty(), 1)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        loadProducers();
    }

    private void setUpColumns() {
        producer_name_col.setCellValueFactory(new PropertyValueFactory<>("producerName"));
        producer_name_col.setCellFactory(TextFieldTableCell.<Producer>forTableColumn());

        producer_email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        producer_email_col.setCellFactory(TextFieldTableCell.<Producer>forTableColumn());

        producer_number_col.setCellValueFactory(new PropertyValueFactory<>("telephoneNumber"));
        producer_number_col.setCellFactory(TextFieldTableCell.<Producer>forTableColumn());

    }

    private void setEditCommits(){
        producer_name_col.setOnEditCommit((TableColumn.CellEditEvent<Producer, String> tableEvent) -> {
            TablePosition<Producer, String> pos = tableEvent.getTablePosition();
            String newName = tableEvent.getNewValue();
            int row = pos.getRow();
            Producer producer = tableEvent.getTableView().getItems().get(row);
            producer.setProducerName(newName);
        });

        producer_email_col.setOnEditCommit((TableColumn.CellEditEvent<Producer, String> tableEvent) -> {
            TablePosition<Producer, String> pos = tableEvent.getTablePosition();
            String newEmail = tableEvent.getNewValue();
            int row = pos.getRow();
            Producer producer = tableEvent.getTableView().getItems().get(row);
            producer.setEmail(newEmail);
        });

        producer_number_col.setOnEditCommit((TableColumn.CellEditEvent<Producer, String> tableEvent) -> {
            TablePosition<Producer, String> pos = tableEvent.getTablePosition();
            String newNumber = tableEvent.getNewValue();
            int row = pos.getRow();
            Producer producer = tableEvent.getTableView().getItems().get(row);
            producer.setTelephoneNumber(newNumber);
        });
    }

    private void loadProducers() {
        progress_bar.setVisible(true);
        timeline.play();
        refreshTable(FXCollections.observableArrayList(producerService.findAll()));
        timeline.stop();
        progress_bar.setVisible(false);
    }

    private void refreshTable(ObservableList<Producer> producers){
        producers_tbl.getItems().clear();
        producers_tbl.setItems(producers);
        producers_tbl.refresh();
    }

    @FXML
    void deleteSelected(ActionEvent event) {
        Producer selectedItem = producers_tbl.getSelectionModel().getSelectedItem();
        producers_tbl.getItems().remove(selectedItem);
    }

}
