package org.bsuir.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.DoubleValidator;
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
import org.bsuir.dto.ProductDto;
import org.bsuir.model.Product;
import org.bsuir.model.ProductType;
import org.bsuir.service.ProductService;
import org.bsuir.service.ProductTypeService;
import org.bsuir.validation.Patterns;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private static final String REQUIRED_FIELD = "Это поле обязательно!";
    private static final String ERROR_INPUT = "Данные введены с ошибкой";
    private final ProductTypeService productTypeService;
    private final ProductService productService;

    @Setter
    private Stage stage;

    @FXML
    private TableView<ProductDto> products_tbl;

    @FXML
    private TableColumn<ProductDto, String> product_name_col;

    @FXML
    private TableColumn<ProductDto, String> product_code_col;

    @FXML
    private TableColumn<ProductDto, String> product_type_col;

    @FXML
    private TableColumn<ProductDto, Double> product_prise_col;

    @FXML
    private JFXTextField product_name_fd;

    @FXML
    private Label product_name_lbl;

    @FXML
    private JFXTextField product_code_fd;

    @FXML
    private Label product_code_lbl;

    @FXML
    private JFXTextField product_prise_fd;

    @FXML
    private Label product_prise_lbl;

    @FXML
    private Button add_btn;

    @FXML
    private Button delete_btn;

    @FXML
    private Button save_btn;

    @FXML
    private JFXComboBox<String> products_combo_box;

    private List<ProductType> productTypes;

    public void initialize() {
        loadProducts();
        loadProductTypes();

        setUpColumns();
        setEditCommits();

        RequiredFieldValidator requiredValidator = new RequiredFieldValidator(REQUIRED_FIELD);
        DoubleValidator doubleValidator = new DoubleValidator("Только числа!");

        product_prise_fd.getValidators().add(doubleValidator);
        products_combo_box.getValidators().add(requiredValidator);

        Stream.of(product_name_fd, product_code_fd, product_prise_fd, products_combo_box)
                .forEach(control -> {
                    control.getValidators().add(requiredValidator);
                    control.focusedProperty().addListener((o, oldVal, newVal) -> {
                        if (!newVal) control.validate();
                    });
                });

        products_combo_box.getItems().addAll(productTypes.stream().map(ProductType::getProductType).collect(Collectors.toList()));
    }

    private void loadProductTypes() {
        productTypes = productTypeService.findAll();
    }

    private void loadProducts() {
        refreshTable(FXCollections.observableArrayList(productService.getAllDto()));
    }

    private void setEditCommits() {
        product_name_col.setOnEditCommit((TableColumn.CellEditEvent<ProductDto, String> tableEvent) -> {
            TablePosition<ProductDto, String> pos = tableEvent.getTablePosition();
            String newName = tableEvent.getNewValue();
            int row = pos.getRow();
            ProductDto productDto = tableEvent.getTableView().getItems().get(row);
            productDto.setProductName(newName);
        });

        product_code_col.setOnEditCommit((TableColumn.CellEditEvent<ProductDto, String> tableEvent) -> {
            TablePosition<ProductDto, String> pos = tableEvent.getTablePosition();
            String newCode = tableEvent.getNewValue();
            int row = pos.getRow();
            ProductDto productDto = tableEvent.getTableView().getItems().get(row);
            productDto.setProductCode(newCode);
        });

        product_prise_col.setOnEditCommit((TableColumn.CellEditEvent<ProductDto, Double> tableEvent) -> {
            TablePosition<ProductDto, Double> pos = tableEvent.getTablePosition();
            Double newPrise = tableEvent.getNewValue();
            int row = pos.getRow();
            ProductDto productDto = tableEvent.getTableView().getItems().get(row);
            productDto.setPrise(newPrise);
        });
    }

    private void setUpColumns() {
        product_name_col.setCellValueFactory(new PropertyValueFactory<>("productName"));
        product_name_col.setCellFactory(TextFieldTableCell.<ProductDto>forTableColumn());

        product_code_col.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        product_code_col.setCellFactory(TextFieldTableCell.<ProductDto>forTableColumn());

        product_type_col.setCellValueFactory(new PropertyValueFactory<>("productTypeName"));
        product_type_col.setCellFactory(TextFieldTableCell.<ProductDto>forTableColumn());

        product_prise_col.setCellValueFactory(new PropertyValueFactory<ProductDto, Double>("prise"));
        product_prise_col.setCellFactory(tc -> new TableCell<ProductDto, Double>() {
            @Override
            protected void updateItem(Double power, boolean empty) {
                super.updateItem(power, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.format("%f руб.", power));
                }
            }
        });
    }

    @FXML
    void add(ActionEvent event) {
        if (product_name_fd.validate()
                && product_code_fd.validate()
                && product_prise_fd.validate()
                && products_combo_box.validate()) {

            String productName = product_name_fd.getText();
            String productCode = product_code_fd.getText();
            String productType = products_combo_box.getValue();
            Double productPrise = Double.parseDouble(product_prise_fd.getText());

            if (Pattern.compile(Patterns.PRODUCER_PRODUCT_NAME_PATTERN).matcher(productName).matches()) {
                Product product = Product.builder()
                        .productName(productName)
                        .productCode(productCode)
                        .productType(productTypes.stream().filter(type -> type.getProductType().equals(productType)).findFirst().get().getIdProductType())
                        .prise(productPrise)
                        .build();
                productService.save(product);
                clearInputs();
                loadProducts();
            }
        }
    }

    private void clearInputs() {
        product_name_fd.setText("");
        product_code_fd.setText("");
        product_prise_fd.setText("0");
    }

    @FXML
    void delete(ActionEvent event) {
        ProductDto selectedItem = products_tbl.getSelectionModel().getSelectedItem();
        products_tbl.getItems().remove(selectedItem);
    }

    @FXML
    void save(ActionEvent event) {
        List<ProductDto> productDtos = products_tbl.getItems();
        productDtos = productService.saveAll(productDtos);
        refreshTable(FXCollections.observableArrayList(productDtos));
    }

    private void refreshTable(ObservableList<ProductDto> productDtos) {
        products_tbl.getItems().clear();
        products_tbl.setItems(productDtos);
        products_tbl.refresh();
    }
}
