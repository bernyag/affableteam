/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

/**
 *
 * @author jaimelimonsamperio
 */
public class Bootstrap {
    public static void main(String args[]) throws Exception {
        DatabaseHelper db = new DatabaseHelper();
        
        db.removeTables();
        db.createTables();
        db.seedDatabase();
        
        
   }
}
