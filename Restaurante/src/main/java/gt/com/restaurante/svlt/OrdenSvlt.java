/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.com.restaurante.svlt;

import gt.com.restaurante.utils.ToJson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.math.BigDecimal;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.Date;

/**
 *
 * @author elunadanilo
 */
@WebServlet(name = "OrdenSvlt", urlPatterns = {"/OrdenSvlt"})
public class OrdenSvlt extends HttpServlet {
public Connection makeConnection() throws SQLException{
        Connection con=null;
        try{
            InitialContext ic=new InitialContext();
            DataSource ds=(DataSource)ic.lookup("jdbc/javaAntiguaBurguer");
            con = ds.getConnection();
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return con;
    }
    public void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{ 
        try{
            JSONArray json = new JSONArray();
            Connection con=makeConnection();
            
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select * from Orden limit 10;");
            System.out.println("El rs es: " + rs);
            //String ordenJson=new Gson().toJson(rs);

            json= ToJson.mapResultSet(rs);

            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(json);
            out.flush(); 
            con.close();
        }catch (Exception ex) {
            System.out.println("El error en service() es: " + ex);
        }
    }
   
     /* protected void listado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Orden> lista = ordenService.listar();
        request.setAttribute("lista", lista);
        request.setAttribute("totalRec", lista.size());

        String ordenJson=new Gson().toJson(lista);
        System.out.println("El rs con jpa es: " + ordenJson);
    }*/
      
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        service(request, response);
        //listado(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>   

}
