import java.sql.*;

public class DatabaseLogger {
    private static final String URL = "jdbc:postgresql://localhost:5432/calculator_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "devil179@";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void logCalculation(String expression, double result) {
        String sql = "INSERT INTO calculations (expression, result) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, expression);
            stmt.setDouble(2, result);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
