package stock.management.system.database;

import stock.management.system.model.DbConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import stock.management.system.util.DbConfigLoader;

public class Database {

    private String user = "root";
    private String password = "root";
    private String host = "localhost";
    private int port = 3306;
    private static Database db;

    private Connection conn;

    private Database() throws SQLException, ClassNotFoundException {
        connect();
        createDatabase();
        createTables();
    }

    public static Database getInstance() throws SQLException, ClassNotFoundException {
        if (db == null) {
            db = new Database();
        }
        return db;
    }

    private void connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        DbConfig dbConfig = DbConfigLoader.loadDbConfig();

        if (dbConfig != null) {
            host = dbConfig.getHost();
            port = dbConfig.getPort();
            user = dbConfig.getUser();
            password = dbConfig.getPassword();
        }

        String url = "jdbc:mysql://" + host + ":" + port + "/";

        conn = DriverManager.getConnection(url, user, password);
    }

    public void createDatabase() throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "create database if not exists smsdb";
        stmt.execute(sql);
    }

    public void createTables() throws SQLException {
        Statement stmt1 = conn.createStatement();
        String sql1 = "create table if not exists smsdb.products (id int,name varchar(60),price double,stock int)";
        stmt1.execute(sql1);

        Statement stmt2 = conn.createStatement();
        String sql2 = "create table if not exists smsdb.transactions (id int primary key auto_increment,type varchar(10),product_id int,quantity int,remark varchar(255),date timestamp)";
        stmt2.execute(sql2);
    }

    public Connection getConnection() {
        return conn;
    }

}
