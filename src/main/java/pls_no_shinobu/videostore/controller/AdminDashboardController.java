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
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import pls_no_shinobu.videostore.VideoStoreApplication;
import pls_no_shinobu.videostore.controller.utils.PaneUtils;
import pls_no_shinobu.videostore.controller.utils.SceneUtils;
import pls_no_shinobu.videostore.core.CSVDatabase;
import pls_no_shinobu.videostore.core.Session;
import pls_no_shinobu.videostore.model.Item;
import pls_no_shinobu.videostore.model.User;

import java.io.IOException;
import java.util.Optional;

public class AdminDashboardController {
    private enum SearchBy {
        NAME,
        ID
    }

    private FilteredList<User> filteredUsers;
    private FilteredList<Item> filteredStocks;
    private FilteredList<User> filteredRentals;

    @FXML private TextField accountSearchField;
    @FXML private TextField itemSearchField;

    @FXML private ComboBox<User.UserType> roleComboBox;
    @FXML private ComboBox<SearchBy> accountSearchByBox;
    @FXML private ComboBox<SearchBy> itemSearchByBox;

    @FXML private Button logoutButton;
    @FXML private StackPane stackPane;

    @FXML private Text titleText;

    @FXML private VBox accountContainer;
    @FXML private VBox stockContainer;
    @FXML private VBox rentalContainer;

    @FXML private TableView<User> accountTable;
    @FXML private TableView<Item> stockTable;
    @FXML private TableView<User> rentalTable;

    private void editUser(User user) {
        try {
            FXMLLoader loader =
                    new FXMLLoader(VideoStoreApplication.class.getResource("updateUser.fxml"));

            Scene scene = new Scene(loader.load());

            UpdateUserController controller = loader.getController();
            controller.setUser(user);

            Stage stage = new Stage();
            stage.setTitle("Update " + user.getUsername());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    private void editItem(Item item) {
        try {
            FXMLLoader loader =
                    new FXMLLoader(VideoStoreApplication.class.getResource("updateItem.fxml"));

            Scene scene = new Scene(loader.load());

            UpdateItemController controller = loader.getController();
            controller.setItem(item);

            Stage stage = new Stage();
            stage.setTitle("Update " + item.getTitle());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

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

        TableColumn<User, String> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellValueFactory(new PropertyValueFactory<>(""));

        Callback<TableColumn<User, String>, TableCell<User, String>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<User, String> call(final TableColumn<User, String> param) {
                        return new TableCell<>() {

                            final Button btn = new Button("Edit");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(
                                            event ->
                                                    editUser(
                                                            getTableView()
                                                                    .getItems()
                                                                    .get(getIndex())));
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                    }
                };

        actionColumn.setCellFactory(cellFactory);

        filteredUsers =
                new FilteredList<>(
                        FXCollections.observableArrayList(database.getUsers().getEntities()));

        accountTable.setItems(filteredUsers);

        // https://stackoverflow.com/a/3819038
        accountTable
                .getColumns()
                .addAll(
                        idColumn,
                        usernameColumn,
                        nameColumn,
                        roleColumn,
                        addressColumn,
                        phoneColumn,
                        actionColumn);
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

        TableColumn<Item, String> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellValueFactory(new PropertyValueFactory<>(""));

        Callback<TableColumn<Item, String>, TableCell<Item, String>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Item, String> call(final TableColumn<Item, String> param) {
                        return new TableCell<>() {

                            final Button btn = new Button("Edit");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(
                                            event ->
                                                    editItem(
                                                            getTableView()
                                                                    .getItems()
                                                                    .get(getIndex())));
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                    }
                };

        actionColumn.setCellFactory(cellFactory);

        filteredStocks =
                new FilteredList<>(
                        FXCollections.observableArrayList(database.getItems().getEntities()));

        stockTable.setItems(filteredStocks);

        // https://stackoverflow.com/a/3819038
        stockTable
                .getColumns()
                .setAll(
                        titleColumn,
                        typeColumn,
                        loanColumn,
                        stockColumn,
                        feeColumn,
                        genreColumn,
                        actionColumn);
    }

    private void initializeRentalTable() throws IOException {
        CSVDatabase database = CSVDatabase.getInstance();

        TableColumn<User, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<User, String> rentalsColumn = new TableColumn<>("Rentals");
        rentalsColumn.setCellValueFactory(new PropertyValueFactory<>("rentals"));

        filteredRentals = new FilteredList<>(FXCollections.observableArrayList(database.getUsers().getEntities()));

        rentalTable.setItems(filteredRentals);

        // https://stackoverflow.com/a/3819038
        rentalTable.getColumns().setAll(idColumn, nameColumn, rentalsColumn);
    }

    @FXML
    public void initialize() throws IOException {
        roleComboBox.setItems(
                FXCollections.observableArrayList(
                        User.UserType.GUEST,
                        User.UserType.REGULAR,
                        User.UserType.VIP,
                        User.UserType.ADMIN));
        accountSearchByBox.setItems(FXCollections.observableArrayList(SearchBy.ID, SearchBy.NAME));
        itemSearchByBox.setItems(FXCollections.observableArrayList(SearchBy.ID, SearchBy.NAME));

        initializeAccountTable();
        initializeStockTable();
        initializeRentalTable();
    }

    @FXML
    protected void onAddButtonClick() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(VideoStoreApplication.class.getResource("addItem.fxml"));

            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setTitle("Add new item");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
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

    public void onAccountSearchAction() {
        String input = accountSearchField.getText().toLowerCase().trim();

        if (input.isEmpty()) {
            filteredUsers.setPredicate(user -> true);
        } else if (accountSearchByBox.getValue() == SearchBy.ID) {
            filteredUsers.setPredicate(user -> user.getId().toLowerCase().contains(input));
        } else if (accountSearchByBox.getValue() == SearchBy.NAME) {
            filteredUsers.setPredicate(user -> user.getName().toLowerCase().contains(input));
        }
    }

    public void onRoleComboBoxAction() {
        if (roleComboBox.getValue() == null) {
            filteredUsers.setPredicate(user -> true);
        } else {
            filteredUsers.setPredicate(user -> user.getRole() == roleComboBox.getValue());
        }
    }

    public void onItemSearchAction() {
        String input = itemSearchField.getText().toLowerCase().trim();

        if (input.isEmpty()) {
            filteredStocks.setPredicate(item -> true);
        } else if (itemSearchByBox.getValue() == SearchBy.ID) {
            filteredStocks.setPredicate(item -> item.getId().toLowerCase().contains(input));
        } else if (itemSearchByBox.getValue() == SearchBy.NAME) {
            filteredStocks.setPredicate(item -> item.getTitle().toLowerCase().contains(input));
        }
    }
}
