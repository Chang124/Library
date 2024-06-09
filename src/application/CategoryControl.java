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

public class CategoryControl {
    // Navbar
    @FXML
    Label staffName;
    @FXML
    Button btnLoan;
    @FXML
    Button btnDashboard;
    @FXML
    Button btnBorrow;
    @FXML
    Button btnReturn;
    @FXML
    Button btnCustomer;
    @FXML
    Button btnBook;
    @FXML
    Button btnLogout;

    // Func
    @FXML
    TextField txtSearch;
    @FXML
    Button btnSearch;
    @FXML
    Button btnAddCategory;
    @FXML
    Button btnUpdateCategory;
    @FXML
    Button btnDeleteCategory;

    // View
    @FXML
    private TableView<Category> tbCategory;

    @FXML
    private TableColumn<Category, Integer> colCateID;
    @FXML
    private TableColumn<Category, String> colCate;

    private ObservableList<Category> categoryList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize columns
        colCateID.setCellValueFactory(new PropertyValueFactory<>("cateID"));
        colCate.setCellValueFactory(new PropertyValueFactory<>("cate"));

        // Load data into TableView
        loadCategories();
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
    public void loadCategories() {
        ObservableList<Category> categories = FXCollections.observableArrayList();

        String query = "SELECT * FROM category";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int cateID = rs.getInt("cateID");
                String cate = rs.getString("cate");

                Category category = new Category(cateID, cate);
                categories.add(category);
            }

            tbCategory.setItems(categories);
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Error", "Error loading categories: " + e.getMessage());
        }
    }
    @FXML
    public void SearchClick(MouseEvent event) {
    	String searchText = txtSearch.getText();
    	ObservableList<Category> categories = FXCollections.observableArrayList();

        String query = "SELECT * FROM category WHERE cate LIKE ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + searchText + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	int cateID = rs.getInt("cateID");
                String cate = rs.getString("cate");
             
                Category category = new Category(cateID, cate);
                categories.add(category);
            }

            tbCategory.setItems(categories);
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Error", "Error fetching books: " + e.getMessage());
        }
    }

    @FXML
    public void AddCategoryClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/NewCategory.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Add New Category Information");
        stage.showAndWait(); // Wait for the new category window to close
        loadCategories(); // Refresh the category list after adding
    }

    @FXML
    public void UpdateCategoryClick(MouseEvent event) throws IOException {
        Category selectedCategory = tbCategory.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/UpdateCategory.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Update Category Information");
            stage.showAndWait(); // Wait for the update category window to close
            loadCategories(); // Refresh the category list after updating
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a category to update.");
        }
    }

    @FXML
    public void DeleteCategoryClick(MouseEvent event) {
        Category selectedCategory = tbCategory.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            String query = "DELETE FROM category WHERE cateID = ?";
            try (Connection conn = Connect.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setInt(1, selectedCategory.getCateID());
                pstmt.executeUpdate();
                categoryList.remove(selectedCategory);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Category deleted successfully.");
                
                // Refresh the category list in the main view
    		    CategoryControl controller = new CategoryControl();
    		    controller.loadCategories();
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Error deleting category: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a category to delete.");
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
