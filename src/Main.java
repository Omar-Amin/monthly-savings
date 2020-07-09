public class Main {

    public static void main(String[] args) {
        String connectionUrl = "jdbc:sqlserver://localhost:host";
        String user = "sa";
        String password = "password";
        String databaseName = "databasse";

        Server server = new Server(connectionUrl,user,password,databaseName);
        server.setupServer();
    }



}
