package org.bsuir.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bsuir.dto.OrderDto;
import org.bsuir.model.*;
import org.bsuir.service.*;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderStatusService orderStatusService;
    private final ProducerService producerService;
    private final ProductService productService;
    private final ProductTypeService productTypeService;
    private final UserOptionController userOptionController;

    @Setter
    private Stage stage;
    private static final String REQUIRED_FIELD = "Это поле обязательно!";

    @FXML
    private TableView<OrderDto> orders_tbl;

    @FXML
    private TableColumn<OrderDto, String> product_name_col;

    @FXML
    private TableColumn<OrderDto, String> product_type_col;

    @FXML
    private TableColumn<OrderDto, String> status_col;

    @FXML
    private TableColumn<OrderDto, String> producer_col;

    @FXML
    private TableColumn<OrderDto, String> customer_col;

    @FXML
    private TableColumn<OrderDto, Date> date_col;

    @FXML
    private TableColumn<OrderDto, Integer> quant_col;

    @FXML
    private TableColumn<OrderDto, String> description_col;

    @FXML
    private Button delete_btn;

    @FXML
    private Button save_btn;

    @FXML
    private JFXComboBox<String> product_type_box;

    @FXML
    private JFXComboBox<String> product_box;

    @FXML
    private JFXDatePicker datepicker;

    @FXML
    private JFXComboBox<String> status_box;

    @FXML
    private Spinner<Integer> quant_box;

    @FXML
    private JFXTextArea description_area;

    @FXML
    private Button add_btn;

    @FXML
    private JFXComboBox<String> producer_box;

    @FXML
    private Button close_btn;

    @FXML
    private PieChart statusChart;

    @FXML
    private BarChart<String, Number> producerChart;


    private List<OrderDto> orders;
    private List<Product> products;
    private List<ProductType> productTypes;
    private List<OrderStatus> orderStatuses;
    private List<Producer> producers;

    private void loadData() {
        loadOrders();
        loadProductTypes();
        loadProducts();
        loadStatuses();
        loadProducers();
    }

    private void loadProducers() {
        producers = producerService.findAll();
    }

    private void loadStatuses() {
        orderStatuses = orderStatusService.findAll();
    }

    private void loadProducts() {
        products = productService.findAll();
    }

    private void loadProductTypes() {
        productTypes = productTypeService.findAll();
    }

    private void loadOrders() {
        orders = orderService.getAllDtos();
    }

    private void initStatusChart() {
        statusChart.getData().clear();
        Map<String,Integer> statusNumber = new HashMap<>();
        orders.forEach(orderDto -> {
            if(!statusNumber.containsKey(orderDto.getStatusName())){
                statusNumber.put(orderDto.getStatusName(),1);
            }else statusNumber.put(orderDto.getStatusName(),statusNumber.get(orderDto.getStatusName()) + 1);
        });

        for (Map.Entry<String, Integer> entry : statusNumber.entrySet()) {
            statusChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }

    private void initProducerChart(){
        producerChart.getData().clear();
        Map<String,Integer> producerNumber = new HashMap<>();
        orders.forEach(orderDto -> {
            if(!producerNumber.containsKey(orderDto.getProducerName())){
                producerNumber.put(orderDto.getProducerName(),1);
            }else producerNumber.put(orderDto.getProducerName(),producerNumber.get(orderDto.getProducerName()) + 1);
        });

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<String, Number>();
        dataSeries.setName("Поставщики");

        for (Map.Entry<String, Integer> entry : producerNumber.entrySet()) {
            dataSeries.getData().add(new XYChart.Data<String, Number>(entry.getKey(), entry.getValue()));
        }
        producerChart.getData().add(dataSeries);
    }

    public void initialize() {
        loadData();

        setUpColumns();
        setEditCommits();
        setComboBoxData();

        RequiredFieldValidator requiredValidator = new RequiredFieldValidator(REQUIRED_FIELD);

        Stream.of(product_box, producer_box, datepicker, status_box)
                .forEach(control -> {
                    control.getValidators().add(requiredValidator);
                    control.focusedProperty().addListener((o, oldVal, newVal) -> {
                        if (!newVal) control.validate();
                    });
                });

        quant_box.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        refreshTable(FXCollections.observableArrayList(orders));
        initStatusChart();
        initProducerChart();

        orders_tbl.setEditable(userOptionController.isAdmin());
        add_btn.setDisable(!userOptionController.isAdmin());
        save_btn.setDisable(!userOptionController.isAdmin());
        delete_btn.setDisable(!userOptionController.isAdmin());
    }

    private void setComboBoxData() {
        product_type_box.getItems().addAll(productTypes.stream().map(ProductType::getProductType).collect(Collectors.toList()));
        product_box.getItems().addAll(products.stream().map(Product::getProductName).collect(Collectors.toList()));
        producer_box.getItems().addAll(producers.stream().map(Producer::getProducerName).collect(Collectors.toList()));
        status_box.getItems().addAll(orderStatuses.stream().map(OrderStatus::getStatus).collect(Collectors.toList()));
    }

    private void setEditCommits() {
        status_col.setOnEditCommit((TableColumn.CellEditEvent<OrderDto, String> tableEvent) -> {
            TablePosition<OrderDto, String> pos = tableEvent.getTablePosition();
            String newName = tableEvent.getNewValue();
            int row = pos.getRow();
            OrderDto orderDto = tableEvent.getTableView().getItems().get(row);
            orderDto.setStatusName(newName);
            orderDto.setStatus(orderStatuses.stream()
                    .filter(orderStatus -> orderStatus.getStatus().equals(newName))
                    .findFirst().get()
                    .getIdStatus());
        });

        producer_col.setOnEditCommit((TableColumn.CellEditEvent<OrderDto, String> tableEvent) -> {
            TablePosition<OrderDto, String> pos = tableEvent.getTablePosition();
            String newName = tableEvent.getNewValue();
            int row = pos.getRow();
            OrderDto orderDto = tableEvent.getTableView().getItems().get(row);
            orderDto.setProducerName(newName);
            orderDto.setProducer(producers.stream()
                    .filter(producer -> producer.getProducerName().equals(newName))
                    .findFirst().get()
                    .getIdProducer());
        });
    }

    private void setUpColumns() {
        product_name_col.setCellValueFactory(new PropertyValueFactory<>("productName"));
        product_type_col.setCellValueFactory(new PropertyValueFactory<>("productTypeName"));
        status_col.setCellValueFactory(new PropertyValueFactory<>("statusName"));
        producer_col.setCellValueFactory(new PropertyValueFactory<>("producerName"));
        customer_col.setCellValueFactory(new PropertyValueFactory<>("surname"));
        description_col.setCellValueFactory(new PropertyValueFactory<>("descriptionData"));
        description_col.setCellFactory(TextFieldTableCell.forTableColumn());
        quant_col.setCellValueFactory(new PropertyValueFactory<OrderDto, Integer>("quantity"));
        date_col.setCellValueFactory(new PropertyValueFactory<OrderDto, Date>("orderTime"));

        ObservableList<String> statuses = FXCollections.observableArrayList(
                orderStatuses.stream()
                        .map(OrderStatus::getStatus)
                        .collect(Collectors.toList()));
        status_col.setCellFactory(ComboBoxTableCell.forTableColumn(statuses));

        ObservableList<String> producerNames = FXCollections.observableArrayList(
                producers.stream()
                        .map(Producer::getProducerName)
                        .collect(Collectors.toList()));
        producer_col.setCellFactory(ComboBoxTableCell.forTableColumn(producerNames));
    }

    @FXML
    void add(ActionEvent event) {
        if (product_box.validate() && producer_box.validate() && status_box.validate() && datepicker.validate()) {
            try {
                Product product = products.stream()
                        .filter(product1 -> product1.getProductName().equals(product_box.getValue()))
                        .findFirst().orElseThrow(NullPointerException::new);
                OrderStatus orderStatus = orderStatuses.stream()
                        .filter(status -> status.getStatus().equals(status_box.getValue()))
                        .findFirst().orElseThrow(NullPointerException::new);
                Producer producer = producers.stream()
                        .filter(producer1 -> producer1.getProducerName().equals(producer_box.getValue()))
                        .findFirst().orElseThrow(NullPointerException::new);
                Order order = new Order();
                order.setStatus(orderStatus.getIdStatus());
                order.setProduct(product.getIdProduct());
                order.setProducer(producer.getIdProducer());
                order.setOrderTime(Timestamp.valueOf(datepicker.getValue().atStartOfDay()));
                order.setQuantity(quant_box.getValue());
                order.setCustomer(userOptionController.getAuthorizedUser().getIdUser());
                saveDescription(order);
                orderService.save(order);
                loadOrders();
                refreshTable(FXCollections.observableArrayList(orders));
                initStatusChart();
                initProducerChart();
                confirmationAlert("Данные добавлены!");
            } catch (Exception exception) {
                exception.printStackTrace();
                errorAlert("Ошибка! Попробуйте снова");
            }
        } else errorAlert("Ошибка! Поля заполнены неверно");
    }

    private void saveDescription(Order order) {
        if (description_area.getText() != null) {
            Long idDescription = orderService.saveDescription(new Description(description_area.getText())).getIdDescription();
            order.setDescription(idDescription);
        }
    }

    private void errorAlert(String message) {
        new Alert(Alert.AlertType.ERROR, message).show();
    }

    private void confirmationAlert(String message) {
        new Alert(Alert.AlertType.CONFIRMATION, message).show();
    }

    @FXML
    void changeProductType(ActionEvent event) {
        String selectedType = product_type_box.getValue();
        Long idProductType = productTypes.stream()
                .filter(productType -> productType.getProductType().equals(selectedType))
                .findFirst().orElseThrow(NullPointerException::new)
                .getIdProductType();
        product_box.getItems().clear();
        product_box.getItems().addAll(products.stream()
                .filter(product -> product.getProductType() == idProductType)
                .map(Product::getProductName)
                .collect(Collectors.toList()));
    }

    @FXML
    void close(ActionEvent event) {
        stage.close();
    }

    @FXML
    void delete(ActionEvent event) {
        OrderDto selectedItem = orders_tbl.getSelectionModel().getSelectedItem();
        orders_tbl.getItems().remove(selectedItem);
    }

    @FXML
    void save(ActionEvent event) {
        List<OrderDto> orderDtos = orders_tbl.getItems();
        orderDtos = orderService.saveAll(orderDtos);
        refreshTable(FXCollections.observableArrayList(orderDtos));
        initProducerChart();
        initStatusChart();
    }

    private void refreshTable(ObservableList<OrderDto> orderDtos) {
        orders_tbl.getItems().clear();
        orders_tbl.setItems(orderDtos);
        orders_tbl.refresh();
    }
}
