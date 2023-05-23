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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import pls_no_shinobu.videostore.controller.utils.PaneUtils;
import pls_no_shinobu.videostore.controller.utils.SceneUtils;
import pls_no_shinobu.videostore.core.CSVDatabase;
import pls_no_shinobu.videostore.core.Session;
import pls_no_shinobu.videostore.errors.OutOfStockException;
import pls_no_shinobu.videostore.errors.RentLimitException;
import pls_no_shinobu.videostore.model.Item;
import pls_no_shinobu.videostore.model.User;

import java.io.IOException;
import java.util.Optional;

public class UserDashboardController {
    private enum SearchBy {
        NAME,
        ID
    }

    private ObservableList<Item> items;
    private ObservableList<Item> rentals;
    private FilteredList<Item> filteredItems;
    private FilteredList<Item> filteredRentals;

    @FXML private Button logoutButton;
    @FXML private StackPane stackPane;

    @FXML private Text titleText;

    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private TextField itemSearchField;
    @FXML private TextField rentalsSearchField;

    @FXML private VBox itemContainer;
    @FXML private GridPane profileContainer;
    @FXML private VBox rentedContainer;

    @FXML private TableView<Item> itemTable;
    @FXML private TableView<Item> rentedTable;

    @FXML private ComboBox<SearchBy> itemSearchByBox;
    @FXML private ComboBox<SearchBy> rentalsSearchByBox;

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

        TableColumn<Item, String> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellValueFactory(new PropertyValueFactory<>(""));

        Callback<TableColumn<Item, String>, TableCell<Item, String>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell call(final TableColumn<Item, String> param) {
                        final TableCell<Item, String> cell =
                                new TableCell<Item, String>() {

                                    final Button btn = new Button("Rent");

                                    @Override
                                    public void updateItem(String item, boolean empty) {
                                        super.updateItem(item, empty);
                                        if (empty) {
                                            setGraphic(null);
                                            setText(null);
                                        } else {
                                            btn.setOnAction(
                                                    event -> {
                                                        Item cItem =
                                                                getTableView()
                                                                        .getItems()
                                                                        .get(getIndex());
                                                        try {
                                                            Session.getInstance()
                                                                    .getCurrentUser()
                                                                    .addRental(cItem);

                                                            CSVDatabase.getInstance().updateUsers();
                                                            CSVDatabase.getInstance().updateItems();

                                                            initialize();
                                                        } catch (IOException e) {
                                                            Alert alert =
                                                                    new Alert(
                                                                            Alert.AlertType.ERROR,
                                                                            "Session not available."
                                                                                    + " Details: "
                                                                                    + e
                                                                                            .getMessage());
                                                            alert.showAndWait();
                                                        } catch (OutOfStockException e) {
                                                            Alert alert =
                                                                    new Alert(
                                                                            Alert.AlertType.ERROR,
                                                                            "Item is out of"
                                                                                    + " stock.");
                                                            alert.showAndWait();
                                                        } catch (RentLimitException e) {
                                                            Alert alert =
                                                                    new Alert(
                                                                            Alert.AlertType.ERROR,
                                                                            "Exceeded rent limit.");
                                                            alert.showAndWait();
                                                        } catch (Exception e) {
                                                            Alert alert =
                                                                    new Alert(
                                                                            Alert.AlertType.ERROR,
                                                                            "Something went wrong."
                                                                                    + " Details: "
                                                                                    + e
                                                                                            .getMessage());
                                                            alert.showAndWait();
                                                        } finally {
                                                            Alert alert =
                                                                    new Alert(
                                                                            Alert.AlertType
                                                                                    .INFORMATION,
                                                                            "Rent successful.");
                                                            alert.showAndWait();
                                                        }
                                                    });
                                            setGraphic(btn);
                                            setText(null);
                                        }
                                    }
                                };
                        return cell;
                    }
                };

        actionColumn.setCellFactory(cellFactory);

        items =
                FXCollections.observableArrayList(database.getItems().getEntities());
        filteredItems = new FilteredList<>(items);

        itemTable.setItems(filteredItems);

        // https://stackoverflow.com/a/3819038
        itemTable
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

    private void initializeRentedTable() throws IOException {
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

        TableColumn<Item, String> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellValueFactory(new PropertyValueFactory<>(""));

        Callback<TableColumn<Item, String>, TableCell<Item, String>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell call(final TableColumn<Item, String> param) {
                        final TableCell<Item, String> cell =
                                new TableCell<Item, String>() {

                                    final Button btn = new Button("Return");

                                    @Override
                                    public void updateItem(String item, boolean empty) {
                                        super.updateItem(item, empty);
                                        if (empty) {
                                            setGraphic(null);
                                            setText(null);
                                        } else {
                                            btn.setOnAction(
                                                    event -> {
                                                        Item cItem =
                                                                getTableView()
                                                                        .getItems()
                                                                        .get(getIndex());
                                                        try {
                                                            Session.getInstance()
                                                                    .getCurrentUser()
                                                                    .removeRental(cItem);

                                                            CSVDatabase.getInstance().updateUsers();
                                                            CSVDatabase.getInstance().updateItems();

                                                            initialize();
                                                        } catch (IOException e) {
                                                            Alert alert =
                                                                    new Alert(
                                                                            Alert.AlertType.ERROR,
                                                                            "Session not available."
                                                                                    + " Details: "
                                                                                    + e
                                                                                            .getMessage());
                                                            alert.showAndWait();
                                                        } catch (IllegalArgumentException e) {
                                                            Alert alert =
                                                                    new Alert(
                                                                            Alert.AlertType.ERROR,
                                                                            "Rental not found."
                                                                                    + " Details: "
                                                                                    + e
                                                                                            .getMessage());
                                                            alert.showAndWait();
                                                        } catch (Exception e) {
                                                            Alert alert =
                                                                    new Alert(
                                                                            Alert.AlertType.ERROR,
                                                                            "Something went wrong."
                                                                                    + " Details: "
                                                                                    + e
                                                                                            .getMessage());
                                                            alert.showAndWait();
                                                        } finally {
                                                            Alert alert =
                                                                    new Alert(
                                                                            Alert.AlertType
                                                                                    .INFORMATION,
                                                                            "Rent successful.");
                                                            alert.showAndWait();
                                                        }
                                                    });
                                            setGraphic(btn);
                                            setText(null);
                                        }
                                    }
                                };
                        return cell;
                    }
                };

        actionColumn.setCellFactory(cellFactory);

        rentals =
                FXCollections.observableArrayList(session.getCurrentUser().getRentals());
        filteredRentals = new FilteredList<>(rentals);

        rentedTable.setItems(filteredRentals);

        // https://stackoverflow.com/a/3819038
        rentedTable
                .getColumns()
                .setAll(titleColumn, typeColumn, loanColumn, feeColumn, genreColumn, actionColumn);
    }

    @FXML
    public void initialize() throws IOException {
        itemSearchByBox.setItems(FXCollections.observableArrayList(SearchBy.ID, SearchBy.NAME));
        rentalsSearchByBox.setItems(FXCollections.observableArrayList(SearchBy.ID, SearchBy.NAME));

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

    @FXML
    protected void onItemSearchAction() {
        String input = itemSearchField.getText().toLowerCase().trim();

        if (input.isEmpty()) {
            filteredItems.setPredicate(item -> true);
        } else if (itemSearchByBox.getValue() == SearchBy.ID) {
            filteredItems.setPredicate(item -> item.getId().toLowerCase().contains(input));
        } else if (itemSearchByBox.getValue() == SearchBy.NAME) {
            filteredItems.setPredicate(item -> item.getTitle().toLowerCase().contains(input));
        }
    }

    @FXML
    protected void onRentalsSearchAction() {
        String input = rentalsSearchField.getText().toLowerCase().trim();

        if (input.isEmpty()) {
            filteredRentals.setPredicate(item -> true);
        } else if (rentalsSearchByBox.getValue() == SearchBy.ID) {
            filteredRentals.setPredicate(item -> item.getId().toLowerCase().contains(input));
        } else if (rentalsSearchByBox.getValue() == SearchBy.NAME) {
            filteredRentals.setPredicate(item -> item.getTitle().toLowerCase().contains(input));
        }
    }
}
