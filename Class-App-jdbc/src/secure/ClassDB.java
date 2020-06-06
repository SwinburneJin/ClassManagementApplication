package secure;

import java.io.IOException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ClassDB {

    static String url;
    static String username;
    static String password;

    static final String DB_TABLE = "CMA_CLASS";
    static final String DB_PK_CONSTRAINT = "PK_" + DB_TABLE;

    public ClassDB() {
        
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
                    + "(CLASSID CHAR(5) CONSTRAINT " + DB_PK_CONSTRAINT + " PRIMARY KEY, "
                    + "CLASSNAME VARCHAR(32) NOT NULL, "
                    + "STARTTIME TIME, "
                    + "DURATION TIME, "
                    + "ACTIVE BOOLEAN default TRUE"
                    + "TEACHERID CHAR(8) NOT NULL");
            
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

    public void addRecords(ArrayList<Class> classList) {

        Connection cnnct = null;

        PreparedStatement pStmnt = null;

        try {
            cnnct = getConnection();

            String preQueryStatement = "INSERT INTO " + DB_TABLE
                    + " VALUES (?, ?, ?, ?, ?, ?)";

            for (Class c : classList) {

                pStmnt = cnnct.prepareStatement(preQueryStatement);

                pStmnt.setString(1, c.getClassId());
                pStmnt.setString(2, c.getClassName());
                pStmnt.setString(3, c.getStartTime());
                pStmnt.setString(4, c.getDuration());
                pStmnt.setBoolean(5, c.isActive());
                pStmnt.setString(6, c.getTeacherId());

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
