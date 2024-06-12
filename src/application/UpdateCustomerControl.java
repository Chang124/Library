package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class UpdateCustomerControl {

    @FXML
    private TextField txtCustomerID; 
    @FXML
    private TextField txtCustomerName; 
    @FXML
    private TextField txtPhone; 
    @FXML
    private Button btnUpdate; 
    @FXML
    private Button btnCancel; 

    private Customer selectedCustomer;
    
    private int cusID;

    // Method to initialize selectedCustomer and populate the text fields
    public void initData(int cusID, String cusName, String phone)  {
            this.cusID = cusID;
            txtCustomerID.setText(String.valueOf(cusID));
            txtCustomerName.setText(cusName);
            txtPhone.setText(phone);
        } 

    @FXML
    public void UpdateClick(MouseEvent event) {
     
        String customerName = txtCustomerName.getText().trim();
        String phone = txtPhone.getText().trim();
        int customerID =Integer.parseInt(txtCustomerID.getText());

        if (customerName.isEmpty() || phone.isEmpty()) {
            showAlert(AlertType.WARNING, "Validation Error", "Please fill in all fields.");
            return;
        }

        String query = "UPDATE customer SET cusName = ?, phone = ? WHERE cusID = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customerName);
            pstmt.setString(2, phone);
            pstmt.setInt(3, customerID);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // Show success message
                showAlert(AlertType.INFORMATION, "Success", "Customer updated successfully.");

                // Close the current stage
                Stage stage = (Stage) btnUpdate.getScene().getWindow();
                stage.close();

                // Refresh the customer list in the main view
                CustomerControl controller = new CustomerControl();
                controller.loadCustomers();

            } else {
                showAlert(AlertType.ERROR, "Error", "Error updating customer. Customer ID might not exist.");
            }
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Database Error", "Error updating customer: " + e.getMessage());
        }
    }

    @FXML
    public void CancelClick(MouseEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
