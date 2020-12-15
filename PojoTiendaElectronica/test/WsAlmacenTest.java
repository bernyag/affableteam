/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author jaimelimonsamperio
 */
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import helpers.DatabaseHelper;
import java.sql.SQLException;

public class WsAlmacenTest {
    private webservices.WSAlmacen ws;
    private DatabaseHelper db;
    
    @Before
    public void init() throws ClassNotFoundException, SQLException {
        db = new DatabaseHelper();
        db.givenEmptyDatabase();
        webservices.WSAlmacenService service = new webservices.WSAlmacenService();
        ws = service.getWSAlmacenPort();
    }
    
    @Test()
    public void findByIsbn() {
        java.util.List<webservices.Books> listBooks = ws.findByIsbn(1);
        assertEquals(0, listBooks.size());
    }
    
}
