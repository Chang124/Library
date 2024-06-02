package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ReturnControl {
	// navbar
	@FXML
	Label staffName;
	@FXML
	Button btnLoan;
	@FXML
	Button btnBook;
	@FXML
	Button btnBorrow;
	@FXML
	Button btnDashboard;
	@FXML
	Button btnCustomer;
	@FXML
	Button btnCategory;
	@FXML
	Button btnLogout;

	// func
	@FXML
	TextField txtSearch;
	@FXML
	Button btnSearch;
	@FXML
	Button btnAddReturn;
	@FXML
	Button btnUpdateReturn;
	@FXML
	Button btnDeleteReturn;
	
	//view
	@FXML
	TableView tbReturn;
	
	@FXML
	public void DashboardClick(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Dashboard.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnBorrow.getScene().getWindow(); //Get the current window
        Scene scene = new Scene(root); // Set the new scene to the window
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
	}
	
	@FXML
	public void LoanClick(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Loan.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnLoan.getScene().getWindow(); //Get the current window
        Scene scene = new Scene(root); // Set the new scene to the window
        stage.setScene(scene);
        stage.setTitle("Loan Information");
        stage.show();
	}
	
	@FXML
	public void BookClick(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Book.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnBook.getScene().getWindow(); //Get the current window
        Scene scene = new Scene(root); // Set the new scene to the window
        stage.setScene(scene);
        stage.setTitle("Book Information");
        stage.show();
	}
	
	@FXML
	public void BorrowClick(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Borrow.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnBorrow.getScene().getWindow(); //Get the current window
        Scene scene = new Scene(root); // Set the new scene to the window
        stage.setScene(scene);
        stage.setTitle("Borrow Information");
        stage.show();
	}
	
	@FXML
	public void CustomerClick(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Customer.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnCustomer.getScene().getWindow(); //Get the current window
        Scene scene = new Scene(root); // Set the new scene to the window
        stage.setScene(scene);
        stage.setTitle("Customer Information");
        stage.show();
	}
	
	@FXML
	public void CategoryClick(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Category.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnCategory.getScene().getWindow(); //Get the current window
        Scene scene = new Scene(root); // Set the new scene to the window
        stage.setScene(scene);
        stage.setTitle("Category Information");
        stage.show();
	}
	
	@FXML
	public void LogoutClick(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnLogout.getScene().getWindow(); //Get the current window
        Scene scene = new Scene(root); // Set the new scene to the window
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
	}
	
	//display tbReturn
	
	// SearchClick
	public void SearchClick(MouseEvent event) throws IOException {

	}
	
	// AddReturnClick
	public void AddReturnClick(MouseEvent event) throws IOException {
		Parent root =FXMLLoader.load(getClass().getResource("/ui/NewReturn.fxml"));
		Scene scene = new Scene(root);
		
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.setTitle("Add New Return Information");

		primaryStage.show();
	}
	// UpdateReturnClick
	public void UpdateReturnClick(MouseEvent event) throws IOException {
		Parent root =FXMLLoader.load(getClass().getResource("/ui/UpdateReturn.fxml"));
		Scene scene = new Scene(root);
		
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.setTitle("Update Return Information");

		primaryStage.show();
	}
	
	// DeleteReturnClick
	public void DeleteReturnClick(MouseEvent event) throws IOException {
		
	}
}
