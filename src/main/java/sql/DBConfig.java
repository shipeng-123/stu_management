package sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

public class DBConfig {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Properties prop = new Properties();
            InputStream inputStream = DBConfig.class.getClassLoader().getResourceAsStream("database.properties");
            prop.load(inputStream);
            String driver = prop.getProperty("database.driver");
            String url = prop.getProperty("database.url");
            String user = prop.getProperty("database.user");
            String password = prop.getProperty("database.password");
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
