//package application;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class Database {
//    private static final String DB_ServerName = "DESKTOP-57OK3NN";
//    private static final String DB_login = "sa";
//    private static final String DB_password = "123";
//    private static final String DB_databaseName = "Library";
//
//    public static Connection getConnection() {
//        try {
//            // Load the SQL Server JDBC driver
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//
//            // Corrected connection URL
//            String DB_URL = "jdbc:sqlserver://" + DB_ServerName + ";databaseName=" + DB_databaseName + ";encrypt=false;trustServerCertificate=true";
//
//            // Return the database connection
//            return DriverManager.getConnection(DB_URL, DB_login, DB_password);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public boolean validateCredentials(String username, String password) {
//        String query = "SELECT * FROM Staff WHERE staffName = ? AND password = ?";
//
//        try (Connection connection = getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//
//            statement.setString(1, username);
//            statement.setString(2, password);
//
//            try (ResultSet resultSet = statement.executeQuery()) {
//                return resultSet.next();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//}
