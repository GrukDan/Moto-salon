package org.bsuir.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class MainMenuController {

    private final ProducerController producerController;
    private final ProductController productController;
    private final UserController userController;
    private final OrderController orderController;

    @FXML
    private Button orders_bn;

    @FXML
    private Button product_bn;

    @FXML
    private Button producer_bn;

    @FXML
    private Button users_btn;

    @FXML
    private Button option_bn;

    @FXML
    private Button exit_bn;

    @FXML
    void exitButtonClick(ActionEvent event) {

    }

    @FXML
    void optionButtonClick(ActionEvent event) {

    }

    @FXML
    void ordersButtonClick(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Управление заказами");
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/orders.fxml"));
        loader.setController(orderController);
        orderController.setStage(stage);
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.showAndWait();
    }

    @FXML
    void producerButtonClick(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Управление поставщиками");
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/producers.fxml"));
        loader.setController(producerController);
        producerController.setStage(stage);
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.showAndWait();
    }

    @FXML
    void productButtonClick(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Управление товарами");
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/products.fxml"));
        loader.setController(productController);
        productController.setStage(stage);
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.showAndWait();
    }

    @FXML
    void usersButtonClick(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Управление пользователями");
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/users.fxml"));
        loader.setController(userController);
        userController.setStage(stage);
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.showAndWait();
    }
}
