/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import stock.management.system.dao.TransactionDAO;
import stock.management.system.model.Transaction;

/**
 *
 * @author Sithu
 */
public class TransactionDAOTest {
    
    public TransactionDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void testSave() throws SQLException {
         TransactionDAO transactionDAO = new TransactionDAO();
         Transaction transaction = new Transaction("IN",12,10,"buy");
         assertEquals(1, transactionDAO.saveTransaction(transaction));
         
     }
}
