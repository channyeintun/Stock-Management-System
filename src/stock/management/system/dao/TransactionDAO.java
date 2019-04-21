/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.management.system.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import stock.management.system.database.Database;
import stock.management.system.model.Product;
import stock.management.system.model.Transaction;

/**
 *
 * @author Sithu
 */
public class TransactionDAO {
    
    public int saveTransaction(Transaction transaction) throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        String sql = "insert into smsdb.transactions (type,product_id,quantity,remark,date) values (?,?,?,?,now())";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, transaction.getType());
        stmt.setInt(2, transaction.getProductId());
        stmt.setInt(3, transaction.getQuantity());
        stmt.setString(4, transaction.getRemark());
        int rows = stmt.executeUpdate();
        return rows;
    }
    
    
    public List<Transaction> getTransactions(Date startDate,Date endDate) throws SQLException{
         Connection conn = Database.getInstance().getConnection();
         String sql = "select transactions.id,transactions.type,products.name,transactions.quantity,transactions.date,transactions.remark from smsdb.transactions left join smsdb.products on transactions.product_id=products.id where date between ? and ?";
         PreparedStatement stmt = conn.prepareStatement(sql);
         stmt.setDate(1, startDate);
         stmt.setDate(2, endDate);
         ResultSet result = stmt.executeQuery();
         List<Transaction> transactions = new ArrayList<>();
         
         while(result.next()){
             int id = result.getInt("id");
             String type = result.getString("type");
             String productName = result.getString("name");
             int quantity  = result.getInt("quantity");
             String remark = result.getString("remark");
             Timestamp date = result.getTimestamp("date");
             
             Transaction transaction = new Transaction(id, type,productName, quantity, remark, date.toString());
             transactions.add(transaction);
         }
         return transactions;
    }
    
}
