package usemysql;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TableDAOImp implements TableDAO {
    
    private static final String TABLE_SHOW = "SELECT * FROM modelTable";
    private static final String TABLE_INSERT = "INSERT INTO modelTable (localName, generateName) VALUES (?, ?)";
    private static final String CREATE_TABLE="CREATE TABLE IF NOT EXISTS modelTable (ID INT AUTO_INCREMENT PRIMARY KEY, localName VARCHAR(30), generateName VARCHAR(50));";
    private static final String SHOW_PAGE = "SELECT * FROM modelTable LIMIT ?, ?";
    private static final int count = 5;
    private ConnectionSource connectionSource;
    
    public TableDAOImp(ConnectionSource connection) {
        connectionSource = connection;
    }
    
    @Override
    public void create(Table table) throws SQLException {
        Connection connection = connectionSource.getConnection();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(TABLE_INSERT);
            stm.setString(1, table.getLocalName());
            stm.setString(2, table.getGenerateName());
            if (stm.executeUpdate() == 0)
                System.out.println("Result is empty");
        } finally {
            if (stm != null)
                stm.close();
            connection.close();
        }
        
    }

    @Override
    public void createtable() throws SQLException {
        Connection connection = connectionSource.getConnection();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(CREATE_TABLE);
            stm.execute();
        } finally {
            if (stm != null)
                stm.close();
            connection.close();
        }
        
        
    }
    
    @Override
    public List<Table> getAll() throws SQLException {
        List<Table> list = new LinkedList<Table>();
        Connection connection = connectionSource.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(TABLE_SHOW);
            rs = stm.executeQuery();
            while (rs.next()) {
                Table table = new Table();
                table.setID(rs.getInt("ID"));
                table.setLocalName(rs.getString("localName"));
                table.setGenerateName(rs.getString("generateName"));
                list.add(table);
            }
        } finally {
            if (rs != null)
                rs.close();
            if (stm != null)
                stm.close();
            connection.close();
        }
        return list;
    }

    @Override
    public List<Table> showpage(List<Table> list, int numpage) throws SQLException {
        List<Table> listpage = new LinkedList<Table>();
        Connection connection = connectionSource.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        int startrec = (numpage - 1)*count;
        int endrec = count;
        try {
            stm = connection.prepareStatement(SHOW_PAGE);
            stm.setInt(1, startrec);
            stm.setInt(2, endrec);
            rs = stm.executeQuery();
            while (rs.next()) {
                Table table = new Table();
                table.setID(rs.getInt("ID"));
                table.setLocalName(rs.getString("localName"));
                table.setGenerateName(rs.getString("generateName"));
                listpage.add(table);
            }
        } finally {
            if (rs != null)
                rs.close();
            if (stm != null)
                stm.close();
            connection.close();
        }
        return listpage;
    }

    @Override
    public int countpage(List<Table> list) {
           int size = list.size();
           int page = size/count;
           if (size%count != 0) page++;
        return page;
    }
        

}
