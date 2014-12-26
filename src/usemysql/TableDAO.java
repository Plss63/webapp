package usemysql;

import java.sql.SQLException;
import java.util.List;

public interface TableDAO {

    /*
     * 
     */
    public void createtable() throws SQLException;
    /*
     * create new record in db
     */
    public void create(Table table) throws SQLException;
    

    /*
     * show all
     */
    public List<Table> getAll() throws SQLException;
    
    
    /*
     * show data on the page
     */
    public List<Table> showpage(List<Table> list, int numpage) throws SQLException;
    
    /*
     * return count page
     */
    public int countpage(List<Table> list);
    
}
