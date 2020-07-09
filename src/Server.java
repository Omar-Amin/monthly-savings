import org.hibernate.cfg.Configuration;

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
        String url = this.connectionUrl + ";databaseName=" + this.databaseName + ";" + "user=" + this.user + ";password=" + this.password;

        System.out.print("Connecting to SQL Server ... ");
        try (Connection connection = DriverManager.getConnection(url)) {
            System.out.println("Connected successfully.");
        } catch (SQLException ignored){}
    }

    Configuration createHibernateConfiguration() {
        String url = this.connectionUrl + ";databaseName=" + this.databaseName;
        Configuration cfg = new Configuration()
                .setProperty("hibernate.connection.driver_class", "com.microsoft.sqlserver.jdbc.SQLServerDriver")
                .setProperty("hibernate.connection.url", url)
                .setProperty("hibernate.connection.username", this.user)
                .setProperty("hibernate.connection.password", this.password)
                .setProperty("hibernate.connection.autocommit", "true")
                .setProperty("hibernate.show_sql", "false");
        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
        cfg.setProperty("hibernate.show_sql", "false");
        cfg.setProperty("hibernate.hbm2ddl.auto", "update");

        cfg.addAnnotatedClass(User.class);

        return cfg;
    }
}
