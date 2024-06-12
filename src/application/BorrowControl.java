package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowControl {
    // Navbar
    @FXML
    Label staffName;
    @FXML
    Button btnLoan;
    @FXML
    Button btnBook;
    @FXML
    Button btnDashboard;
    @FXML
    Button btnReturn;
    @FXML
    Button btnCustomer;
    @FXML
    Button btnCategory;
    @FXML
    Button btnLogout;

    // Functional buttons
    @FXML
    TextField txtSearch;
    @FXML
    Button btnSearch;
    @FXML
    Button btnAddBorrow;
    @FXML
    Button btnUpdateBorrow;
    @FXML
    Button btnDeleteBorrow;

    // TableView
    @FXML
    TableView<Borrow> tbBorrow;
    @FXML
    TableColumn<Borrow, Integer> borrowIDColumn;
    @FXML
    TableColumn<Borrow, String> customerNameColumn;
    @FXML
    TableColumn<Borrow, String> staffNameColumn;
    @FXML
    TableColumn<Borrow, String> bookTitleColumn;
    @FXML
    TableColumn<Borrow, Integer> quantityColumn;
    @FXML
    TableColumn<Borrow, String> borrowDateColumn;
    @FXML
    TableColumn<Borrow, String> returnDateColumn;

    @FXML
    public void initialize() {
        borrowIDColumn.setCellValueFactory(new PropertyValueFactory<>("borrowID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("cusName"));
        staffNameColumn.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("released_date"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("return_date"));

        // Load data into the table
        loadBorrows();
    }

    @FXML
    public void DashboardClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Dashboard.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnDashboard.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    public void LoanClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Loan.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnLoan.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Loan Information");
        stage.show();
    }

    @FXML
    public void BookClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Book.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnBook.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Book Information");
        stage.show();
    }

    @FXML
    public void ReturnClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Return.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnReturn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Return Information");
        stage.show();
    }

    @FXML
    public void CustomerClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Customer.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnCustomer.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Customer Information");
        stage.show();
    }

    @FXML
    public void CategoryClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Category.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnCategory.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Category Information");
        stage.show();
    }

    @FXML
    public void LogoutClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    @FXML
    public void loadBorrows() {
        ObservableList<Borrow> borrows = FXCollections.observableArrayList();

        String query = "SELECT br.borrowID, c.cusName, s.staffName, b.title, br.quantity, br.released_date, br.return_date " +
                       "FROM borrow_record br " +
                       "JOIN customer c ON br.cusID = c.cusID " +
                       "JOIN staff s ON br.staffID = s.staffID " +
                       "JOIN book b ON br.bookID = b.bookID";

        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int borrowID = rs.getInt("borrowID");
                String cusName = rs.getString("cusName");
                String staffName = rs.getString("staffName");
                String title = rs.getString("title");
                int quantity = rs.getInt("quantity");
                String released_date = rs.getString("released_date");
                String return_date = rs.getString("return_date");

                Borrow borrow = new Borrow(borrowID, cusName, staffName, title, quantity, released_date, return_date);
                borrows.add(borrow);
            }

            tbBorrow.setItems(borrows);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error loading borrows: " + e.getMessage());
        }
    }


    @FXML
    public void SearchClick(MouseEvent event) {
        String searchText = txtSearch.getText();
        ObservableList<Borrow> borrows = FXCollections.observableArrayList();

        String query = "SELECT br.borrowID, c.cusName, s.staffName, b.title, br.quantity, br.released_date, br.return_date " +
                       "FROM borrow_record br " +
                       "JOIN customer c ON br.cusID = c.cusID " +
                       "JOIN staff s ON br.staffID = s.staffID " +
                       "JOIN book b ON br.bookID = b.bookID " +
                       "WHERE br.borrowID LIKE ? OR c.cusName LIKE ? OR s.staffName LIKE ? OR b.title LIKE ? OR br.quantity LIKE ? " +
                       "OR br.released_date LIKE ? OR br.return_date LIKE ?";

        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            String searchPattern = "%" + searchText + "%";
            for (int i = 1; i <= 7; i++) {
                pstmt.setString(i, searchPattern);
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int borrowID = rs.getInt("borrowID");
                String cusName = rs.getString("cusName");
                String staffName = rs.getString("staffName");
                String title = rs.getString("title");
                int quantity = rs.getInt("quantity");
                String released_date = rs.getString("released_date");
                String return_date = rs.getString("return_date");

                Borrow borrow = new Borrow(borrowID, cusName, staffName, title, quantity, released_date, return_date);
                borrows.add(borrow);
            }

            tbBorrow.setItems(borrows);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error fetching borrows: " + e.getMessage());
        }
    }

    @FXML
    public void AddBorrowClick(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/NewBorrow.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add New Borrow Information");

        primaryStage.show();
    }

    @FXML
    public void UpdateBorrowClick(MouseEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/ui/UpdateBorrow.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Update Borrow Information");
        primaryStage.show();
    }

    @FXML
    public void DeleteBorrowClick(MouseEvent event) {
        Borrow selectedBorrow = tbBorrow.getSelectionModel().getSelectedItem();
        if (selectedBorrow != null) {
            String query = "DELETE FROM borrow_record WHERE borrowID = ?";
            try (Connection conn = Connect.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setInt(1, selectedBorrow.getBorrowID());
                pstmt.executeUpdate();
                tbBorrow.getItems().remove(selectedBorrow);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Borrow record deleted successfully.");

                // Refresh the borrow list in the main view
                loadBorrows();
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Error deleting borrow record: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a borrow record to delete.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}