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
import javafx.scene.layout.GridPane;
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

public class UserDashboardController {
    @FXML private Button logoutButton;
    @FXML private StackPane stackPane;

    @FXML private Text titleText;

    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;

    @FXML private VBox itemContainer;
    @FXML private VBox cartContainer;
    @FXML private GridPane profileContainer;
    @FXML private VBox rentedContainer;

    @FXML private TableView<Item> itemTable;
    @FXML private TableView<Item> rentedTable;
    @FXML private TableView<Item> cartTable;

    private void initializeItemTable() throws IOException {
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

        itemTable.setItems(data);

        // https://stackoverflow.com/a/3819038
        itemTable
                .getColumns()
                .addAll(titleColumn, typeColumn, loanColumn, stockColumn, feeColumn, genreColumn);
    }

    private void initializeRentedTable() throws IOException {
        CSVDatabase database = CSVDatabase.getInstance();
        Session session = Session.getInstance();

        // setting up itemTable
        TableColumn<Item, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Item, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("rentalType"));

        TableColumn<Item, String> loanColumn = new TableColumn<>("Loan");
        loanColumn.setCellValueFactory(new PropertyValueFactory<>("loanType"));

        TableColumn<Item, String> feeColumn = new TableColumn<>("Fee");
        feeColumn.setCellValueFactory(new PropertyValueFactory<>("rentalFee"));

        TableColumn<Item, String> genreColumn = new TableColumn<>("Genre");
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

        ObservableList<Item> data =
                FXCollections.observableArrayList(session.getCurrentUser().getRentals());

        rentedTable.setItems(data);

        // https://stackoverflow.com/a/3819038
        rentedTable
                .getColumns()
                .addAll(titleColumn, typeColumn, loanColumn, feeColumn, genreColumn);
    }

    @FXML
    public void initialize() throws IOException {
        PaneUtils.setPane(stackPane, itemContainer);

        initializeItemTable();
        initializeRentedTable();
    }

    @FXML
    protected void onAllItemButtonClick() {
        titleText.setText("All items");
        PaneUtils.setPane(stackPane, itemContainer);
    }

    @FXML
    protected void onRentedItemButtonClick() {
        titleText.setText("Currently renting");
        PaneUtils.setPane(stackPane, rentedContainer);
    }

    @FXML
    protected void onProfileButtonClick() throws IOException {
        User currentUser = Session.getInstance().getCurrentUser();

        nameField.setText(currentUser.getName());
        phoneField.setText(currentUser.getPhone());
        addressField.setText(currentUser.getAddress());

        titleText.setText("Profile");
        PaneUtils.setPane(stackPane, profileContainer);
    }

    @FXML
    protected void onCartButtonClick() {
        titleText.setText("Cart");
        PaneUtils.setPane(stackPane, cartContainer);
    }

    @FXML
    protected void onSaveButtonClick() throws IOException {
        Session session = Session.getInstance();
        User currentUser = session.getCurrentUser();

        try {
            currentUser.setName(nameField.getText());
            currentUser.setPhone(phoneField.getText());
            currentUser.setAddress(addressField.getText());

            CSVDatabase.getInstance().updateUsers();
        } catch (IllegalArgumentException e) {
            Alert alert =
                    new Alert(
                            Alert.AlertType.ERROR,
                            "Update failed. Error details: " + e.getMessage());
            alert.showAndWait();
        } finally {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Update successful.");
            alert.showAndWait();
        }
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
