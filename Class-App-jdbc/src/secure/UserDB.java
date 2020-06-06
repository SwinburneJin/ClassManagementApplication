package secure;

import java.io.IOException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDB {

    static String url;
    static String username;
    static String password;

    static final String DB_TABLE = "CMA_STUDENT";
    static final String DB_PK_CONSTRAINT = "PK_" + DB_TABLE;

    public UserDB() {
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
                    + "(USERID CHAR(5) CONSTRAINT " + DB_PK_CONSTRAINT + " PRIMARY KEY, "
                    + "NAME VARCHAR(64) NOT NULL, "
                    + "PHONE VARCHAR(10), "
                    + "ADDRESS VARCHAR(64), "
                    + "EMAIL VARCHAR(64) NOT NULL, "
                    + "PASSWORD VARCHAR(128) NOT NULL, "
                    + "USERGROUP VARCHAR(32), "
                    + "ACTIVE BOOLEAN)");
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
    public void addRecords(ArrayList<User> userList) {

        Connection cnnct = null;

        PreparedStatement pStmnt = null;

        try {
            cnnct = getConnection();

            String preQueryStatement = "INSERT INTO " + DB_TABLE
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            for (User user : userList) {

                pStmnt = cnnct.prepareStatement(preQueryStatement);

                pStmnt.setString(1, user.getUserid());
                pStmnt.setString(2, user.getName());
                pStmnt.setString(3, user.getPhone());
                pStmnt.setString(4, user.getAddress());
                pStmnt.setString(5, user.getEmail());
                pStmnt.setString(6, user.getPassword());
                pStmnt.setString(7, user.getUserGroup());
                pStmnt.setBoolean(8, user.isActive());

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
