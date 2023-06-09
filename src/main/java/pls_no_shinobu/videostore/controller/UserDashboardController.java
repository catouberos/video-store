/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.controller;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
import pls_no_shinobu.videostore.errors.OutOfStockException;
import pls_no_shinobu.videostore.errors.RentLimitException;
import pls_no_shinobu.videostore.manager.ItemManager;
import pls_no_shinobu.videostore.model.Item;
import pls_no_shinobu.videostore.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Random;

public class UserDashboardController {
    private enum SearchBy {
        NAME,
        ID
    }

    private enum RentalType {
        ALL(null),
        RECORD(Item.RentalType.RECORD),
        DVD(Item.RentalType.DVD),
        GAME(Item.RentalType.GAME);

        final Item.RentalType type;

        RentalType(Item.RentalType type) {
            this.type = type;
        }

        public Item.RentalType getType() {
            return type;
        }
    }

    private Random rand = new Random();

    private FilteredList<Item> filteredItems;
    private FilteredList<Item> filteredRentals;
    private Item randomItem;

    @FXML private Button logoutButton;
    @FXML private StackPane stackPane;

    @FXML private Text titleText;
    @FXML private Text usernameText;
    @FXML private Text randomTitleText;
    @FXML private Text rentalCountText;
    @FXML private Text currentRoleText;
    @FXML private Text nextRoleText;
    @FXML private Text roleDetailsText;

    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private TextField itemSearchField;
    @FXML private TextField rentalsSearchField;

    @FXML private VBox homeContainer;
    @FXML private VBox itemContainer;
    @FXML private VBox profileContainer;
    @FXML private VBox rentedContainer;
    @FXML private HBox randomContainer;
    @FXML private TabPane helpContainer;

    @FXML private TableView<Item> itemTable;
    @FXML private TableView<Item> rentedTable;

    @FXML private ComboBox<SearchBy> itemSearchByBox;
    @FXML private ComboBox<SearchBy> rentalsSearchByBox;
    @FXML private ComboBox<RentalType> itemTypeComboBox;
    @FXML private ComboBox<RentalType> rentalsTypeComboBox;

    @FXML private ImageView randomTitleImage;

    @FXML private ProgressBar roleProgress;

    private void rentAnItem(Item item) {
        try {
            Alert confirmation =
                    new Alert(
                            Alert.AlertType.CONFIRMATION,
                            String.format("Rent %s?", item.getTitle()));

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Session.getInstance().getCurrentUser().addRental(item);

                CSVDatabase.getInstance().updateUsers();
                CSVDatabase.getInstance().updateItems();

                initialize();

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Rent successfully.");
                alert.showAndWait();
            }
        } catch (IOException e) {
            Alert alert =
                    new Alert(
                            Alert.AlertType.ERROR,
                            "Session not available." + " Details: " + e.getMessage());
            alert.showAndWait();
        } catch (OutOfStockException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Item is out of" + " stock.");
            alert.showAndWait();
        } catch (RentLimitException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Exceeded rent limit.");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert =
                    new Alert(
                            Alert.AlertType.ERROR,
                            "Something went wrong." + " Details: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void returnAnItem(Item item) {
        try {
            Alert confirmation =
                    new Alert(
                            Alert.AlertType.CONFIRMATION,
                            String.format("Return %s?", item.getTitle()));

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Session.getInstance().getCurrentUser().removeRental(item);

                CSVDatabase.getInstance().updateUsers();
                CSVDatabase.getInstance().updateItems();

                initialize();

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Return rental successfully.");
                alert.showAndWait();
            }
        } catch (IOException e) {
            Alert alert =
                    new Alert(
                            Alert.AlertType.ERROR,
                            "Session not available." + " Details: " + e.getMessage());
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            Alert alert =
                    new Alert(
                            Alert.AlertType.ERROR,
                            "Rental not found." + " Details: " + e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert =
                    new Alert(
                            Alert.AlertType.ERROR,
                            "Something went wrong." + " Details: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void setRandomItem() {
        try {
            ItemManager items = CSVDatabase.getInstance().getItems();

            int randomNumber = rand.nextInt(items.getEntities().size());
            randomItem = items.getEntities().get(randomNumber);

            InputStream imageStream =
                    VideoStoreApplication.class.getResourceAsStream(
                            String.format(
                                    "img/%s.jpg",
                                    randomItem.getRentalType().toString().toLowerCase()));

            if (imageStream != null) randomTitleImage.setImage(new Image(imageStream));
            randomTitleText.setText(randomItem.getTitle());
        } catch (IOException e) {
            // something went wrong, hide the container
            randomContainer.setVisible(false);
            e.printStackTrace();
        }
    }

    private <T> void setCenterLeftCellFactory(TableColumn<Item, T> column) {
        column.setCellFactory(
                col -> {
                    TableCell<Item, T> cell =
                            new TableCell<Item, T>() {
                                @Override
                                protected void updateItem(T item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (item == null || empty) {
                                        setText(null);
                                    } else {
                                        setText(item.toString());
                                    }
                                }
                            };
                    cell.setAlignment(Pos.CENTER_LEFT);
                    return cell;
                });
    }

    private void initializeItemTable() throws IOException {

        CSVDatabase database = CSVDatabase.getInstance();

        // setting up itemTable
        TableColumn<Item, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        setCenterLeftCellFactory(titleColumn);

        TableColumn<Item, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("rentalType"));
        setCenterLeftCellFactory(typeColumn);

        TableColumn<Item, String> loanColumn = new TableColumn<>("Loan");
        loanColumn.setCellValueFactory(new PropertyValueFactory<>("loanType"));
        setCenterLeftCellFactory(loanColumn);

        TableColumn<Item, String> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        setCenterLeftCellFactory(stockColumn);

        TableColumn<Item, String> feeColumn = new TableColumn<>("Fee");
        feeColumn.setCellValueFactory(new PropertyValueFactory<>("rentalFee"));
        setCenterLeftCellFactory(feeColumn);

        TableColumn<Item, String> genreColumn = new TableColumn<>("Genre");
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        setCenterLeftCellFactory(genreColumn);

        TableColumn<Item, String> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellValueFactory(new PropertyValueFactory<>(""));

        Callback<TableColumn<Item, String>, TableCell<Item, String>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Item, String> call(final TableColumn<Item, String> param) {
                        return new TableCell<>() {

                            final Button btn = new Button("Rent");

                            {
                                setAlignment(Pos.CENTER);
                            }

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setMaxWidth(Double.MAX_VALUE);
                                    btn.setMaxHeight(Double.MAX_VALUE);
                                    btn.setOnAction(
                                            event ->
                                                    rentAnItem(
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

        filteredItems =
                new FilteredList<>(
                        FXCollections.observableArrayList(database.getItems().getEntities()));

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
        setCenterLeftCellFactory(titleColumn);

        TableColumn<Item, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("rentalType"));
        setCenterLeftCellFactory(typeColumn);

        TableColumn<Item, String> loanColumn = new TableColumn<>("Loan");
        loanColumn.setCellValueFactory(new PropertyValueFactory<>("loanType"));
        setCenterLeftCellFactory(loanColumn);

        TableColumn<Item, String> feeColumn = new TableColumn<>("Fee");
        feeColumn.setCellValueFactory(new PropertyValueFactory<>("rentalFee"));
        setCenterLeftCellFactory(feeColumn);

        TableColumn<Item, String> genreColumn = new TableColumn<>("Genre");
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        setCenterLeftCellFactory(genreColumn);

        TableColumn<Item, String> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellValueFactory(new PropertyValueFactory<>(""));

        Callback<TableColumn<Item, String>, TableCell<Item, String>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Item, String> call(final TableColumn<Item, String> param) {
                        return new TableCell<>() {

                            final Button btn = new Button("Return");

                            {
                                setAlignment(Pos.CENTER);
                            }

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setMaxWidth(Double.MAX_VALUE);
                                    btn.setMaxHeight(Double.MAX_VALUE);
                                    btn.setOnAction(
                                            event ->
                                                    returnAnItem(
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

        filteredRentals =
                new FilteredList<>(
                        FXCollections.observableArrayList(session.getCurrentUser().getRentals()));

        rentedTable.setItems(filteredRentals);

        // https://stackoverflow.com/a/3819038
        rentedTable
                .getColumns()
                .setAll(titleColumn, typeColumn, loanColumn, feeColumn, genreColumn, actionColumn);
    }

    @FXML
    public void initialize() throws IOException {
        User user = Session.getInstance().getCurrentUser();
        usernameText.setText(String.format("%s (%s)", user.getUsername(), user.getRole()));

        rentalCountText.setText(
                user.getRentals().size() != 1
                        ? String.format("%d items", user.getRentals().size())
                        : String.format("%d item", user.getRentals().size()));

        itemSearchByBox.setItems(FXCollections.observableArrayList(SearchBy.ID, SearchBy.NAME));
        rentalsSearchByBox.setItems(FXCollections.observableArrayList(SearchBy.ID, SearchBy.NAME));
        itemTypeComboBox.setItems(
                FXCollections.observableArrayList(
                        RentalType.ALL, RentalType.RECORD, RentalType.DVD, RentalType.GAME));
        rentalsTypeComboBox.setItems(
                FXCollections.observableArrayList(
                        RentalType.ALL, RentalType.RECORD, RentalType.DVD, RentalType.GAME));

        initializeItemTable();
        initializeRentedTable();
        setRandomItem();
    }

    @FXML
    public void onHelpButtonClick() {
        titleText.setText("Info and FAQs");
        PaneUtils.setPane(stackPane, helpContainer);
    }

    @FXML
    public void onHomeButtonClick() {
        titleText.setText("Welcome");
        PaneUtils.setPane(stackPane, homeContainer);

        setRandomItem();
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

        currentRoleText.setText(currentUser.getRole().toString());

        if (currentUser.getRole() == User.UserType.GUEST) {
            nextRoleText.setText("REGULAR");
            roleDetailsText.setText(
                    String.format("%d rentals left", 3 - currentUser.getRentalCount()));
            roleProgress.setProgress(((double) currentUser.getRentalCount() / 3));
        } else if (currentUser.getRole() == User.UserType.REGULAR) {
            nextRoleText.setText("VIP");
            roleDetailsText.setText(
                    String.format("%d rentals left", 5 - currentUser.getRentalCount()));
            roleProgress.setProgress(((double) (currentUser.getRentalCount() - 3) / 5));
        } else {
            nextRoleText.setText("");
            roleDetailsText.setText(
                    String.format("Currently have %d points", currentUser.getPoint()));
            roleProgress.setProgress(1.00);
        }

        titleText.setText("Profile");
        PaneUtils.setPane(stackPane, profileContainer);
    }

    @FXML
    protected void onSaveButtonClick() throws IOException {
        Session session = Session.getInstance();
        User currentUser = session.getCurrentUser();

        try {
            if (currentUser.getName() == null || !currentUser.getName().equals(nameField.getText()))
                currentUser.setName(nameField.getText());
            if (currentUser.getPhone() == null
                    || !currentUser.getPhone().equals(phoneField.getText()))
                currentUser.setPhone(phoneField.getText());
            if (currentUser.getAddress() == null
                    || !currentUser.getAddress().equals(addressField.getText()))
                currentUser.setAddress(addressField.getText());

            CSVDatabase.getInstance().updateUsers();

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Update successful.");
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            Alert alert =
                    new Alert(
                            Alert.AlertType.ERROR,
                            "Update failed. Error details: " + e.getMessage());
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
            filteredItems.setPredicate(
                    item ->
                            itemTypeComboBox.getValue() == null
                                    || itemTypeComboBox.getValue() == RentalType.ALL
                                    || item.getRentalType()
                                            == itemTypeComboBox.getValue().getType());
        } else if (itemSearchByBox.getValue() == SearchBy.ID) {
            filteredItems.setPredicate(
                    item ->
                            item.getId().toLowerCase().contains(input)
                                    && (itemTypeComboBox.getValue() == null
                                            || itemTypeComboBox.getValue() == RentalType.ALL
                                            || item.getRentalType()
                                                    == itemTypeComboBox.getValue().getType()));
        } else if (itemSearchByBox.getValue() == SearchBy.NAME) {
            filteredItems.setPredicate(
                    item ->
                            item.getTitle().toLowerCase().contains(input)
                                    && (itemTypeComboBox.getValue() == null
                                            || itemTypeComboBox.getValue() == RentalType.ALL
                                            || item.getRentalType()
                                                    == itemTypeComboBox.getValue().getType()));
        }
    }

    @FXML
    protected void onRentalsSearchAction() {
        String input = rentalsSearchField.getText().toLowerCase().trim();

        if (input.isEmpty()) {
            filteredRentals.setPredicate(
                    item ->
                            rentalsTypeComboBox.getValue() == null
                                    || rentalsTypeComboBox.getValue() == RentalType.ALL
                                    || item.getRentalType()
                                            == rentalsTypeComboBox.getValue().getType());
        } else if (rentalsSearchByBox.getValue() == SearchBy.ID) {
            filteredRentals.setPredicate(
                    item ->
                            item.getId().toLowerCase().contains(input)
                                    && (rentalsTypeComboBox.getValue() == null
                                            || rentalsTypeComboBox.getValue() == RentalType.ALL
                                            || item.getRentalType()
                                                    == rentalsTypeComboBox.getValue().getType()));
        } else if (rentalsSearchByBox.getValue() == SearchBy.NAME) {
            filteredRentals.setPredicate(
                    item ->
                            item.getTitle().toLowerCase().contains(input)
                                    && (rentalsTypeComboBox.getValue() == null
                                            || rentalsTypeComboBox.getValue() == RentalType.ALL
                                            || item.getRentalType()
                                                    == rentalsTypeComboBox.getValue().getType()));
        }
    }

    @FXML
    public void onItemTypeComboBoxAction() {
        onItemSearchAction();
    }

    @FXML
    public void onRentalsTypeComboBoxAction() {
        onRentalsSearchAction();
    }

    @FXML
    protected void onRentAction() {
        rentAnItem(randomItem);
    }

    @FXML
    protected void onRefreshRandomAction() {
        setRandomItem();
    }
}
