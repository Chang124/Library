package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class UpdateCategoryControl {
    @FXML
    private TextField txtCategoryID;
    @FXML
    private TextField txtCategoryName;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnCancel;

    @FXML
    public void UpdateClick(MouseEvent event) {
        String categoryID = txtCategoryID.getText();
        String categoryName = txtCategoryName.getText();

        if (categoryID.isEmpty() || categoryName.isEmpty()) {
            showAlert(AlertType.WARNING, "Validation Error", "Please enter both category ID and name.");
            return;
        }

        String query = "UPDATE category SET cate = ? WHERE cateID = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, categoryName);
            pstmt.setString(2, categoryID);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // Show success message
                showAlert(AlertType.INFORMATION, "Success", "Category updated successfully.");

                // Close the current stage
                Stage stage = (Stage) btnUpdate.getScene().getWindow();
                stage.close();

                // Refresh the category list in the main view
                CategoryControl controller = new CategoryControl();
                controller.loadCategories();
            } else {
                showAlert(AlertType.ERROR, "Error", "Error updating category. Category ID might not exist.");
            }
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Database Error", "Error updating category: " + e.getMessage());
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void CancelClick(MouseEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
