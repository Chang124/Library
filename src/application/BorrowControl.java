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

public class BorrowControl {
	// navbar
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

	// func
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
	
	//view
	@FXML
	TableView tbBorrow;
	
	@FXML
	public void DashboardClick(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Dashboard.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnDashboard.getScene().getWindow(); //Get the current window
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
	public void ReturnClick(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Return.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnReturn.getScene().getWindow(); //Get the current window
        Scene scene = new Scene(root); // Set the new scene to the window
        stage.setScene(scene);
        stage.setTitle("Return Information");
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
	
	//display tbBorrow
	
	// SearchClick
	public void SearchClick(MouseEvent event) throws IOException {
		
	}

	// AddBorrowClick
	public void AddBorrowClick(MouseEvent event) throws IOException {
		Parent root =FXMLLoader.load(getClass().getResource("/ui/NewBorrow.fxml"));
		Scene scene = new Scene(root);
		
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.setTitle("Add New Borrow Information");

		primaryStage.show();
	}
	
	// UpdateBorrowClick
	public void UpdateBorrowClick(MouseEvent event) throws IOException {
		Parent root =FXMLLoader.load(getClass().getResource("/ui/UpdateBorrow.fxml"));
		Scene scene = new Scene(root);
		
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.setTitle("Update Borrow Information");

		primaryStage.show();
	}
	
	// DeleteBorrowClick
	public void DeleteBorrowClick(MouseEvent event) throws IOException {
		
	}
}
