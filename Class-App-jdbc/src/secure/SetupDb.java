/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package secure;

import java.util.ArrayList;

/**
 *
 * @author elau
 */
public class SetupDb {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // the database object to access the actual database
        UserDB udb = new UserDB();
        ClassDB cdb = new ClassDB();
        RelationDB rdb = new RelationDB();

        // make sure no name conflicts
        try {
            udb.destroyDBTable();
            cdb.destroyDBTable();
            rdb.destroyDBTable();
        } catch (Exception ex) {
        }
    }
}
