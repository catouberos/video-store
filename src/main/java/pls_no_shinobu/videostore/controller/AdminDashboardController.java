/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import pls_no_shinobu.videostore.controller.utils.PaneUtils;
import pls_no_shinobu.videostore.controller.utils.SceneUtils;
import pls_no_shinobu.videostore.core.CSVDatabase;
import pls_no_shinobu.videostore.core.Session;
import pls_no_shinobu.videostore.model.Item;
import pls_no_shinobu.videostore.model.User;

import java.io.IOException;
import java.util.Optional;

public class AdminDashboardController {
    @FXML private Button logoutButton;
    @FXML private StackPane stackPane;

    @FXML private Text titleText;

    @FXML private VBox accountContainer;
    @FXML private VBox stockContainer;
    @FXML private VBox rentalContainer;

    @FXML private TableView<User> accountTable;
    @FXML private TableView<Item> stockTable;
    @FXML private TableView<User> rentalTable;

    private void initializeAccountTable() throws IOException {
        CSVDatabase database = CSVDatabase.getInstance();

        TableColumn<User, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn<User, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<User, String> phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        ObservableList<User> data =
                FXCollections.observableArrayList(database.getUsers().getEntities());

        accountTable.setItems(data);

        // https://stackoverflow.com/a/3819038
        accountTable
                .getColumns()
                .addAll(
                        idColumn,
                        usernameColumn,
                        nameColumn,
                        roleColumn,
                        addressColumn,
                        phoneColumn);
    }

    private void initializeStockTable() throws IOException {
        CSVDatabase database = CSVDatabase.getInstance();

        // setting up itemTable
        TableColumn<Item, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Item, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("rentalType"));

        TableColumn<Item, String> loanColumn = new TableColumn<>("Loan");
        loanColumn.setCellValueFactory(new PropertyValueFactory<>("loanType"));

        TableColumn<Item, String> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<Item, String> feeColumn = new TableColumn<>("Fee");
        feeColumn.setCellValueFactory(new PropertyValueFactory<>("rentalFee"));

        TableColumn<Item, String> genreColumn = new TableColumn<>("Genre");
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

        ObservableList<Item> data =
                FXCollections.observableArrayList(database.getItems().getEntities());

        stockTable.setItems(data);

        // https://stackoverflow.com/a/3819038
        stockTable
                .getColumns()
                .addAll(titleColumn, typeColumn, loanColumn, stockColumn, feeColumn, genreColumn);
    }

    private void initializeRentalTable() throws IOException {
        CSVDatabase database = CSVDatabase.getInstance();

        TableColumn<User, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<User, String> rentalsColumn = new TableColumn<>("Rentals");
        rentalsColumn.setCellValueFactory(new PropertyValueFactory<>("rentals"));

        ObservableList<User> data =
                FXCollections.observableArrayList(database.getUsers().getEntities());

        rentalTable.setItems(data);

        // https://stackoverflow.com/a/3819038
        rentalTable.getColumns().addAll(idColumn, nameColumn, rentalsColumn);
    }

    @FXML
    public void initialize() throws IOException {
        PaneUtils.setPane(stackPane, accountContainer);

        initializeAccountTable();
        initializeStockTable();
        initializeRentalTable();
    }

    @FXML
    protected void onAccountButtonClick() {
        titleText.setText("Account manager");
        PaneUtils.setPane(stackPane, accountContainer);
    }

    @FXML
    protected void onStockButtonClick() {
        titleText.setText("Stock manager");
        PaneUtils.setPane(stackPane, stockContainer);
    }

    @FXML
    protected void onRentalButtonClick() {
        titleText.setText("Rental manager");
        PaneUtils.setPane(stackPane, rentalContainer);
    }

    @FXML
    protected void onLogoutButtonClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Logout confirmation");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("We will miss you a lot.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                Session session = Session.getInstance();
                session.logout();

                SceneUtils.switchScene((Stage) logoutButton.getScene().getWindow(), "signin.fxml");
            } catch (IOException e) {
                // catch any occurred errors
            }
        }
    }
}
