package Source;
import java.sql.*;
public class DBFacade {

    public static class DBNotConnectedException extends Exception
    {
        String message;
        public DBNotConnectedException()
        {
            message = "Error: database was not connected";
        }
    }

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/dnd";
    private static final String USER = "postgres";
    private static final String PASS = "admin";

    private Connection conn;

    public void ConnectDB()
    {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }
        if (conn != null)
            System.out.println("You successfully connected to database now");
        else
            System.out.println("Failed to make connection to database");
    }

    public Statement GetStatement() throws DBNotConnectedException
    {
        if(conn == null)
            throw new DBNotConnectedException();
        try {
            return conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet ExcecuteQuery(String query) throws DBNotConnectedException
    {
        if(conn == null)
            throw new DBNotConnectedException();

        Statement stmt = GetStatement();
        try {
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public PreparedStatement InsertQuery(String sqlQuery, Object[] values)
            throws DBNotConnectedException
    {
        if(conn == null)
            throw new DBNotConnectedException();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery,
                    Statement.RETURN_GENERATED_KEYS);
            for(int i = 1; i <= values.length; i++)
            {
                pstmt.setObject(i, values[i-1]);
            }
            return pstmt;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static int GetRowCount(ResultSet set)
    {
        int size = 0;
        try {
            set.last();
            size = set.getRow();
            set.beforeFirst();
        }
        catch(Exception ex) {
            return 0;
        }
        return size;
    }

}
