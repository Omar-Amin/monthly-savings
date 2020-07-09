import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Server {
    private String connectionUrl;
    private String user;
    private String password;
    private String databaseName;

    Server(String connectionUrl, String user, String password, String databaseName){
        this.connectionUrl = connectionUrl;
        this.user = user;
        this.password = password;
        this.databaseName = databaseName;
    }

    void setupServer(){
        String url = this.connectionUrl + ";databaseName=master;" + "user=" + this.user + ";password=" + this.password;

        System.out.print("Connecting to SQL Server ... ");
        try (Connection connection = DriverManager.getConnection(url)) {
            System.out.println("Connected successfully.");
        } catch (SQLException ignored){}
    }
}
