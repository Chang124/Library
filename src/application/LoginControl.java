package application;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginControl {
	@FXML
	TextField txtName;
	@FXML
	PasswordField txtPass;
	@FXML
	Button btnLogIn;
	
//    Database dbManager;
	
    @FXML
	public void initialize() {
//		dbManager = new Database();
		txtName.setText("Chang124");
		txtPass.setText("123");
	}

	@FXML
	public void LoginClick(MouseEvent event) throws IOException {
		String name = txtName.getText();
		String pass = txtPass.getText();

		if(name.equals("Chang124") && pass.equals("123")) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnLogIn.getScene().getWindow(); //Get the current window
            Scene scene = new Scene(root); // Set the new scene to the window
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.show();
		}
	}
	
//		if (dbManager.validateCredentials(name, pass)) {
//			showAlert(AlertType.INFORMATION, "Login Successful", "Welcome, " + name + "!");
//		    } else {
//		    	showAlert(AlertType.ERROR, "Login Failed", "Invalid username or password.");
//		      }
//	}

//	private void showAlert(AlertType alertType, String title, String message) {
//		Alert alert = new Alert(alertType);
//		alert.setTitle(title);
//		alert.setHeaderText(null);
//		alert.setContentText(message);
//		alert.showAndWait();
//    }
}
