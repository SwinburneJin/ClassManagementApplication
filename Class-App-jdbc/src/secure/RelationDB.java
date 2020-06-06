package secure;

import java.io.IOException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class RelationDB {

    static String url;
    static String username;
    static String password;

    static final String DB_TABLE = "CMA_STUDENT_CLASS_RELATION";
    static final String DB_PK_CONSTRAINT = "PK_" + DB_TABLE;

    public RelationDB() {
        
        url = "jdbc:derby://localhost/sun-appserv-samples;create=true";
        username = "APP";
        password = "APP";
    }
    
    public static Connection getConnection()
            throws SQLException, IOException {
        
        System.setProperty("jdbc.drivers",
                "org.apache.derby.jdbc.ClientDriver");
        
        return DriverManager.getConnection(url, username, password);
    }
    
    public void createDBTable() {
        Connection cnnct = null;
        Statement stmnt = null;

        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            stmnt.execute("CREATE TABLE " + DB_TABLE
                    + "(STUDENTID CHAR(5) NOT NULL, "
                    + "CLASSID CHAR(5) NOT NULL)");
            
        } catch (SQLException ex) {
        } catch (IOException ex) {
        } finally {
            if (stmnt != null) {
                try {
                    stmnt.close();
                } catch (SQLException e) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }
    }
    public void destroyDBTable() {
        Connection cnnct = null;
        Statement stmnt = null;

        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            stmnt.execute("DROP TABLE " + DB_TABLE);
        } catch (SQLException ex) {
        } catch (IOException ex) {
        } finally {
            if (stmnt != null) {
                try {
                    stmnt.close();
                } catch (SQLException e) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }
    }

    public void addRecords(ArrayList<Relation> relationList) {

        Connection cnnct = null;

        PreparedStatement pStmnt = null;

        try {
            cnnct = getConnection();

            String preQueryStatement = "INSERT INTO " + DB_TABLE
                    + " VALUES (?, ?)";

            for (Relation r : relationList) {

                pStmnt = cnnct.prepareStatement(preQueryStatement);

                pStmnt.setString(1, r.getStudentid());
                pStmnt.setString(2, r.getClassid());

                int rowCount = pStmnt.executeUpdate();

                if (rowCount == 0) {
                    throw new SQLException("Cannot insert records!");
                }
            }
        } catch (SQLException ex) {
        } catch (IOException ex) {
        } finally {
            if (pStmnt != null) {
                try {
                    pStmnt.close();
                } catch (SQLException e) {
                }
            }

            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }
    }
}
