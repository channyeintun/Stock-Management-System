/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.management.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import stock.management.system.database.Database;
import stock.management.system.model.Product;

/**
 *
 * @author Sithu
 */
public class ProductDAO {

    // CRUD
    public int save(Product product) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "insert into smsdb.products (id,name,price,stock) value (?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, product.getId());
        stmt.setString(2, product.getName());
        stmt.setDouble(3, product.getPrice());
        stmt.setInt(4, product.getStock());
        int rows = stmt.executeUpdate();
        return rows;
    }

    public List<Product> getProducts() throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "select * from smsdb.products";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        List<Product> products = new ArrayList<>();
        while (result.next()) {
            int id = result.getInt("id");
            String name = result.getString("name");
            double price = result.getDouble("price");
            int stock = result.getInt("stock");
            Product product = new Product(id, name, price, stock);
            products.add(product);
        }
        return products;
    }

    public List<Product> getLowStockProducts() throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "select * from smsdb.products where stock <= 5";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        List<Product> products = new ArrayList<>();
        while (result.next()) {
            int id = result.getInt("id");
            String name = result.getString("name");
            double price = result.getDouble("price");
            int stock = result.getInt("stock");
            Product product = new Product(id, name, price, stock);
            products.add(product);
        }
        return products;
    }

    public Product getProduct(int id) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "select * from smsdb.products where id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet result = stmt.executeQuery();
        Product product = null;
        if (result.next()) {
            String name = result.getString("name");
            double price = result.getDouble("price");
            int stock = result.getInt("stock");
            product = new Product(id, name, price, stock);
        }
        return product;
    }

    public int updateProduct(Product product) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "update smsdb.products set name=?,price=?,stock=? where id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, product.getName());
        stmt.setDouble(2, product.getPrice());
        stmt.setInt(3, product.getStock());
        stmt.setInt(4, product.getId());
        int rows = stmt.executeUpdate();
        return rows;
    }

    public int deleteProduct(int id) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "delete from smsdb.products where id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        int rows = stmt.executeUpdate();
        return rows;
    }

    public List<Product> getProductsByName(String query) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "select * from smsdb.products where name like ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + query + "%");
        ResultSet result = stmt.executeQuery();
        List<Product> products = new ArrayList<>();
        while (result.next()) {
            int id = result.getInt("id");
            String name = result.getString("name");
            double price = result.getDouble("price");
            int stock = result.getInt("stock");
            Product product = new Product(id, name, price, stock);
            products.add(product);
        }
        return products;
    }

    public boolean isProductExist(int id) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "select count(*) as count from smsdb.products where id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet result = stmt.executeQuery();
        if (result.next()) {
            int count = result.getInt("count");
            if (count == 0) {
                return false;
            }
        }

        return true;
    }

    public int countProducts() throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "select count(*) as count from smsdb.products";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        int count = 0;
        if (result.next()) {
            count = result.getInt("count");
        }
        return count;
    }

    public int countLowStockProducts() throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "select count(*) as count from smsdb.products where stock<=5";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        int count = 0;
        if (result.next()) {
            count = result.getInt("count");
        }
        return count;
    }

}
