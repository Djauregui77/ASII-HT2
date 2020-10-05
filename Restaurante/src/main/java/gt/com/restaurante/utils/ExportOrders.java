/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.com.restaurante.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import gt.com.restaurante.servicios.OrdenesService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import gt.com.restaurante.model.Orden;

/**
 *
 * @author elunadanilo
 */
public class ExportOrders {
       public static void toExport(HttpServletResponse response, OrdenesService ordenesService,String idOrder,String docType){
       String filename = "";

       JSONObject ordenes= null;
       JSONArray detalle=null;
       try {
           ordenes = new JSONObject(new Gson().toJson(ordenesService.getToReport(idOrder),Orden.class));

           ordenes=new JSONObject(ordenes.toString());

           detalle=ordenes.getJSONArray("detalle");
           JSONObject cliente=ordenes.getJSONObject("cliente");

           ordenes.remove("cliente");
           ordenes.remove("Pagada");
           ordenes.remove("Cocinada");
           ordenes.remove("Entregada");

           ordenes.put("Cliente",cliente.getString("PrimerNombre")+" "+cliente.getString("SegundoNombre")+" "+
                   cliente.getString("PrimerApellido")+" "+cliente.getString("SegundoApellido"));
           ordenes.put("NIT",cliente.getString("NIT"));

           for(int count=0;count< Objects.requireNonNull(detalle).length();count++){
               try {
                   JSONObject detalleItem=detalle.getJSONObject(count);
                   JSONObject menu=detalleItem.getJSONObject("menu");
                   JSONArray productos=menu.getJSONArray("productos");

                   for(int count2=0;count2<productos.length();count2++){
                       JSONObject producto=productos.getJSONObject(count2);
                       JSONObject productoItem=producto.getJSONObject("producto");

                       productos.put(count2,productoItem);
                   }

                   detalle.put(count,menu);
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }

           PrintWriter out=response.getWriter();

           switch (docType){
               case "json":
                   filename = "orden.json";

                   response.setContentType("text/json");
                   response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                           "attachment; filename=\"" + filename + "\"");

                   out.write(ordenes.toString());
                   break;
               case "xml":
                   filename = "orden.xml";

                   response.setContentType("text/xml");
                   response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                           "attachment; filename=\"" + filename + "\"");


                   XmlMapper mapper = new XmlMapper();

                   JSONObject newOrden=new JSONObject();
                   newOrden.put("orden",ordenes);

                   out.write(XML.toString(newOrden));
                   break;
               case "txt":
                   filename="orden.txt";

                   response.setContentType("text/plain");
                   response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                           "attachment; filename=\"" + filename + "\"");

                   String[] labels={"${IdOrden}","${Fecha}","${Cliente}","${NIT}","${total}"};

                   String detalleCompra="";
                   String format="========================================================\n" +
                           "=\n" +
                           "Orden:\t\t${IdOrden}\n" +
                           "Fecha/Hora:\t${Fecha}\n" +
                           "Cliente:\t${Cliente}\n" +
                           "NIT:\t\t${NIT}\n" +
                           "--------------------------------------------------------\n" +
                           "=\n" +
                           "${detalleCompra}" +
                           "--------------------------------------------------------\n" +
                           "=\n" +
                           "Extras:\n\n" +
                           "--------------------------------------------------------\n" +
                           "Total:\t\t\tQ${total}\n" +
                           "========================================================\n";

                   for(Object item:ordenes.getJSONArray("detalle")){
                       JSONObject parsedItem=(JSONObject) item;

                       detalleCompra=detalleCompra.concat(parsedItem.getString("Descripcion").concat("\n"));

                       for(Object productItem:parsedItem.getJSONArray("productos")){
                           JSONObject parsedProduct=(JSONObject) productItem;

                           detalleCompra=detalleCompra.concat("\t- ").concat(parsedProduct.getString("Descripcion").concat("\n"));
                       }
                   }

                   format=format.replace("${detalleCompra}",detalleCompra);

                   for (String label:labels){
                       format=format.replace(label,String.valueOf(ordenes.get(label.replaceAll("(\\{|\\}|\\$)","").trim())));
                   }

                   out.write(format);

                   break;
           }

           out.close();
       } catch (JSONException | IOException e) {
           e.printStackTrace();
       }
    }
    
    /*public static void toCsv(HttpServletResponse response,String[] headers_array,OrdenServiceImpl OrdenService){
        String filename = "cursos.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        try {

            ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
                    CsvPreference.STANDARD_PREFERENCE);

            csvWriter.writeHeader(headers_array);

            for (Orden course : OrdenService.listar()) {
                csvWriter.write(course, headers_array);
            }
            csvWriter.close();


        } catch (IOException ex) {

            System.out.println(ex);
        }
    }
    
    public static void toXml(HttpServletResponse response,String[] headers_array,OrdenServiceImpl OrdenService){
        String filename = "cursos.xml";

        response.setContentType("text/xml");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        List<Map<String,String>> temp=new ArrayList<>();

        for (Orden item:OrdenService.listar()){
            Map<String,String> newMap=new HashMap<>();
            newMap.put("IdOrden",String.valueOf(item.getIdOrden()));
            newMap.put("Fecha",String.valueOf(item.getFecha()));
            newMap.put("Pagada",String.valueOf(item.getPagada()));
            temp.add(newMap);
        }

        List<Map<String,String>> filteredList=new ArrayList<>();
        for (Map<String,String> item:
                temp) {
            //System.out.println(item);
            Map<String,String> filteredMap=new HashMap<>();
            for (String key:item.keySet()){
                System.out.println(Arrays.asList(headers_array).contains(key));
                if(Arrays.asList(headers_array).contains(key)){
                    filteredMap.put(key,item.get(key));

                }
            }
            filteredList.add(filteredMap);
        }

        XmlMapper mapper = new XmlMapper();

        try {
            PrintWriter out=response.getWriter();
            out.write(mapper.writeValueAsString(filteredList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
