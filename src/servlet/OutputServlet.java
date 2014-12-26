package servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import usemysql.MySQLConnection;
import usemysql.Table;
import usemysql.TableDAOImp;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

public class OutputServlet extends HttpServlet {
    private static final Pattern intp = Pattern.compile("\\d{1,3}");
    private MySQLConnection connection = new MySQLConnection();
    private TableDAOImp tab = new TableDAOImp(connection);
    private int nump;
    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
     // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        String numpage = request.getParameter("page");
        nump = retnp(numpage);
        JsonFactory jf = new JsonFactory();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        JsonGenerator jgen = jf.createGenerator(stream);

        try {
            jgen.writeStartObject();
            jgen.writeFieldName("records");
            jgen.writeStartArray();
            for (Table t : tab.showpage(tab.getAll(), nump)) {
                jgen.writeStartObject();
                jgen.writeNumberField("ID", t.getID());
                jgen.writeStringField("localName", t.getLocalName());
                jgen.writeStringField("generateName", t.getGenerateName());
                jgen.writeEndObject();
            }
            jgen.writeEndArray();
            jgen.writeNumberField("page", tab.countpage(tab.getAll()));
            jgen.writeEndObject();
            jgen.close();

        } catch (SQLException e) {
            System.out.println("Error adding records");
            e.printStackTrace();
        }
        String JSON = new String(stream.toByteArray());
        response.setContentType("text/json");
        PrintWriter pw = response.getWriter();
        pw.print(JSON);
        pw.flush();
    }

    public boolean checkInt(String str) {
        Matcher m = intp.matcher(str);
        return m.matches();
    }
    
    public int retnp(String s){
        int num = 0;
        if (s == null) num = 1;
        else {
            if (checkInt(s))
                num = Integer.parseInt(s);
        }
       return num; 
    }

}
