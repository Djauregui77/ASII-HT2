package gt.com.restaurante.svlt;

import com.google.gson.Gson;
import com.sun.org.apache.xpath.internal.operations.Or;
import gt.com.restaurante.model.Orden;
import gt.com.restaurante.model.OrdenDetalles;
import gt.com.restaurante.servicios.OrdenesService;
import gt.com.restaurante.utils.ToJson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "Ordenes",urlPatterns = {"/Ordenes"})
public class OrdenesServlet extends HttpServlet {
    private OrdenesService ordenesService=new OrdenesService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        PrintWriter out= resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String json=new Gson().toJson(ordenesService.getAll());

            out.write(json);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out= resp.getWriter();


        try {
            JSONArray jsonArray=new JSONArray(req.getParameter("myJson"));
            ordenesService.save(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            out.close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out= resp.getWriter();

        try{
            out.write(req.getParameter("accion"));
            Orden orden=new Orden(Integer.parseInt(req.getParameter("idOrden")),null,true,false,0,null);

            ordenesService.update(orden);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.close();
        }
    }
}
