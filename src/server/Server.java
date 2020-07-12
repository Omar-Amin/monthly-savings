package server;

import tables.Payment;
import tables.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Server {
    private final String connectionUrl;
    private final String user;
    private final String password;
    private final String databaseName;
    private SessionFactory sessionFactory = null;
    private Session session;
    private Configuration cfg;
    private Connection connection;

    public Server(String connectionUrl, String user, String password, String databaseName){
        this.connectionUrl = connectionUrl;
        this.user = user;
        this.password = password;
        this.databaseName = databaseName;
    }

    /**
     * Connecting to the SQL server.
     * */
    public void setupServer(){
        String url = this.connectionUrl + ";databaseName=" + this.databaseName + ";" + "user=" + this.user + ";password=" + this.password;

        System.out.print("Connecting to SQL server.Server ... ");
        try{
            connection = DriverManager.getConnection(url);
            System.out.println("Connected successfully.");
        } catch (SQLException ignored){}
    }

    /**
     * Creates hibernate configuration, adding both classes Tables.Payment and Tables.User.
     * */
    public void createHibernateConfiguration() {
        String url = this.connectionUrl + ";databaseName=" + this.databaseName;
        Configuration cfg = new Configuration()
                .setProperty("hibernate.connection.driver_class", "com.microsoft.sqlserver.jdbc.SQLServerDriver")
                .setProperty("hibernate.connection.url", url)
                .setProperty("hibernate.connection.username", this.user)
                .setProperty("hibernate.connection.password", this.password)
                .setProperty("hibernate.connection.autocommit", "true")
                .setProperty("hibernate.show_sql", "true");
        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
        cfg.setProperty("hibernate.hbm2ddl.auto", "update");

        cfg.addAnnotatedClass(User.class);
        cfg.addAnnotatedClass(Payment.class);

        this.cfg = cfg;
    }

    public void buildFactory(){
        this.sessionFactory = this.cfg.buildSessionFactory();
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public Session getSession(){
        if (null == session || !session.isOpen()) {
            session = sessionFactory.openSession();
        }
        return this.session;
    }

    public void close() throws SQLException {
        connection.close();
    }
}
