package gt.com.restaurante.svlt;

import com.google.gson.Gson;
import gt.com.restaurante.servicios.ProductosService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Productos", urlPatterns = {"/Productos"})
public class ProductosServlet extends HttpServlet {
    private ProductosService productosService=new ProductosService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out= resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            out.write(new Gson().toJson(productosService.getAll()));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
