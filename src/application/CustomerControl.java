package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CustomerControl {
    // Navbar
    @FXML
    Label staffName;
    @FXML
    Button btnLoan;
    @FXML
    Button btnBook;
    @FXML
    Button btnBorrow;
    @FXML
    Button btnReturn;
    @FXML
    Button btnDashboard;
    @FXML
    Button btnCategory;
    @FXML
    Button btnLogout;
    
    // Func
    @FXML
    TextField txtSearch;
    @FXML
    Button btnSearch;
    @FXML
    Button btnAddCustomer;
    @FXML
    Button btnUpdateCustomer;
    @FXML
    Button btnDeleteCustomer;
    
    // View
    @FXML
    TableView<Customer> tbCustomer;
    @FXML
    private TableColumn<Customer, Integer> colCusID;
    @FXML
    private TableColumn<Customer, String> colCusName;
    @FXML
    private TableColumn<Customer, String> colPhone;

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
	private String loggedInUserName;

    @FXML
    public void initialize() {
        // Initialize columns
        colCusID.setCellValueFactory(new PropertyValueFactory<>("cusID"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("cusName"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        // Load data into TableView
        loadCustomers();
    }


    public void setLoggedInUserName(String userName) {
        this.loggedInUserName = userName;
        staffName.setText("Staff: " + loggedInUserName); // Set the label text here
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
    public void BorrowClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Borrow.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnBorrow.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Borrow Information");
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
    public void loadCustomers() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        String query = "SELECT * FROM customer";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int cusID = rs.getInt("cusID");
                String cusName = rs.getString("cusName");
                String phone = rs.getString("phone");

                Customer customer = new Customer(cusID, cusName, phone);
                customers.add(customer);
            }

            tbCustomer.setItems(customers);
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Error", "Error loading customers: " + e.getMessage());
        }
    }
    
    @FXML
    public TableView<Customer> getTbCustomer() {
        return tbCustomer;
    }
    
    @FXML
    public void SearchClick(MouseEvent event) {
        String searchText = txtSearch.getText();
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        String query = "SELECT * FROM customer WHERE cusName LIKE ? OR phone LIKE ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            String searchPattern = "%" + searchText + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int cusID = rs.getInt("cusID");
                String cusName = rs.getString("cusName");
                String phone = rs.getString("phone");

                Customer customer = new Customer(cusID, cusName, phone);
                customers.add(customer);
            }

            tbCustomer.setItems(customers);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error fetching customers: " + e.getMessage());
        }
    }


    @FXML
    public void AddCustomerClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/NewCustomer.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Add New Customer Information");
        stage.showAndWait(); // Wait for the new customer window to close
        loadCustomers(); // Refresh the customer list after adding
    }

    @FXML
    public void UpdateCustomerClick(MouseEvent event) throws IOException {
        Customer selectedCustomer = tbCustomer.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/UpdateCustomer.fxml"));
            Parent root = loader.load();
            
            UpdateCustomerControl controller = loader.getController();
            controller.setTbCustomer(tbCustomer);
            
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Update Customer Information");
            stage.showAndWait(); // Wait for the update customer window to close
            loadCustomers(); // Refresh the customer list after updating
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a customer to update.");
        }
    }

    @FXML
    public void DeleteCustomerClick(MouseEvent event) {
        Customer selectedCustomer = tbCustomer.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            String query = "DELETE FROM customer WHERE cusID = ?";
            try (Connection conn = Connect.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setInt(1, selectedCustomer.getCusID());
                pstmt.executeUpdate();
                tbCustomer.getItems().remove(selectedCustomer);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Customer deleted successfully.");

                // Refresh the customer list in the main view
                CustomerControl controller = new CustomerControl();
                controller.loadCustomers();
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Error deleting customer: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a customer to delete.");
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
