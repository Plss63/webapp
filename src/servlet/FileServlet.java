package servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import usemysql.MySQLConnection;
import usemysql.Table;
import usemysql.TableDAOImp;

public class FileServlet extends HttpServlet {
    private static final String directory="C:/Mydir/";
    private MySQLConnection connection = new MySQLConnection();
    private TableDAOImp tab = new TableDAOImp(connection);
    private Table table = new Table();
    
    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) {
                } else {
                    // Process form file field (input type="file").
                    // String fieldname = item.getFieldName();
                    String filename = FilenameUtils.getName(item.getName());
                    InputStream filecontent = item.getInputStream();
                    FileOutputStream writer = new FileOutputStream(directory + filename);
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = filecontent.read(buffer)) != -1) {
                        writer.write(buffer, 0, bytesRead);
                    }
                    UUID uid = UUID.randomUUID();   
                    try {
                        tab.createtable();
                        table.setLocalName(filename);
                        table.setGenerateName(uid.toString());
                        tab.create(table);
                    } catch (SQLException e) {
                        System.out.println("Error adding records");
                        e.printStackTrace();
                    }
                    writer.flush();
                    writer.close();
                    filecontent.close();
                    //request.setAttribute("item", filename);
                    RequestDispatcher disp = getServletContext().getRequestDispatcher("/NewFile.jsp");///outputServlet
                    disp.forward(request, response);
                }
            }
        } catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request.", e);
        }

    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    
    

}
