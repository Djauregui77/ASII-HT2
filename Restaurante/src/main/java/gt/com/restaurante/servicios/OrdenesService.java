package gt.com.restaurante.servicios;

import com.sun.org.apache.xpath.internal.operations.Or;
import gt.com.restaurante.model.*;
import gt.com.restaurante.utils.DBConnection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdenesService implements gt.com.restaurante.dao.OrdenesDao {
    private DBConnection connection=new DBConnection();

    @Override
    public Optional get(long id) {
        return Optional.empty();
    }

    @Override
    public List getAll() {
        List<Orden> ordens=new ArrayList<>();
        try {
            PreparedStatement statement=connection.doConnection().prepareStatement("select * from Orden limit 10");
            ResultSet resultSet=statement.executeQuery();

            while (resultSet.next()){
                PreparedStatement statement2=connection.getConnection().prepareStatement("select IdDetalle,IdOrden,Menu.IdMenu,Menu.Descripcion as Menu_Descripcion,Menu.ValorMenu,Producto.IdProducto,Producto.Descripcion as Producto_Descripcion,Producto.Valor,Producto.Activo from OrdenDetalles\n" +
                        "left join Menu on OrdenDetalles.IdMenu=Menu.IdMenu " +
                        "left join Producto on OrdenDetalles.IdProducto=Producto.IdProducto where IdOrden=?");
                statement2.setInt(1,resultSet.getInt("IdOrden"));

                ResultSet resultSet2=statement2.executeQuery();

                List<OrdenDetalles> detalle=new ArrayList<>();

                while (resultSet2.next()){
                    detalle.add(new OrdenDetalles(
                            resultSet2.getInt("IdDetalle"),
                            resultSet.getInt("IdOrden"),
                            new Producto(resultSet2.getInt("IdProducto"),resultSet2.getString("Producto_Descripcion"),resultSet2.getFloat("Valor"),true),
                            new Menu(resultSet2.getInt("IdMenu"),resultSet2.getString("Menu_Descripcion"),resultSet2.getFloat("ValorMenu"),new ArrayList<>())
                    ));
                }

                ordens.add(
                    new Orden(
                        resultSet.getInt("IdOrden"),
                        resultSet.getDate("Fecha"),
                            resultSet.getInt("Pagada") == 1,
                            resultSet.getInt("Cocinada") == 1,
                        resultSet.getInt("IdCliente"),
                        detalle
                    )
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //connection.closeConnection();
        }

        return ordens;
    }

    @Override
    public void save(Object object) {
        JSONArray jsonArray=(JSONArray) object;

        try {
            PreparedStatement preparedStatement=connection.doConnection().prepareStatement("insert into Orden (Fecha,Pagada,Cocinada,IdCliente,Entregada)" +
                    " values(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);


            Timestamp currentTimestamp=Timestamp.from(Instant.now());
            preparedStatement.setTimestamp(1,currentTimestamp);
            preparedStatement.setInt(2,0);
            preparedStatement.setInt(3,0);
            preparedStatement.setInt(4,1);
            preparedStatement.setInt(5,0);

            preparedStatement.executeUpdate();

            ResultSet generatedKey=preparedStatement.getGeneratedKeys();
            generatedKey.next();

            int insertedOrderKey=generatedKey.getInt(1);

            Orden orden=new Orden(insertedOrderKey,currentTimestamp,false,false,1,null);

            List<OrdenDetalles> ordenDetalles=new ArrayList<>();

            for(int count=0;count<jsonArray.length();count++){
                JSONObject jsonObject=jsonArray.getJSONObject(count);

                OrdenDetalles ordenDetalle=new OrdenDetalles(0,insertedOrderKey,null,
                        new Menu(jsonObject.getInt("IdMenu"),jsonObject.getString("Descripcion"),jsonObject.getInt("ValorMenu"),null));

                PreparedStatement preparedStatement2=connection.getConnection().prepareStatement("insert into OrdenDetalles (IdOrden,IdMenu,Observaciones)" +
                        " values(?,?,?)",Statement.RETURN_GENERATED_KEYS);

                preparedStatement2.setInt(1,ordenDetalle.getIdOrden());
                preparedStatement2.setInt(2,ordenDetalle.getMenu().getIdMenu());
                preparedStatement2.setString(3, jsonObject.getString("Observacion"));

                preparedStatement2.executeUpdate();

                ResultSet generatedDetalleKey=preparedStatement2.getGeneratedKeys();

                generatedDetalleKey.next();

                int insertedDetalle=generatedDetalleKey.getInt(1);

                ordenDetalle.setIdDetalle(insertedDetalle);

                ordenDetalles.add(ordenDetalle);
            }

            orden.setDetalle(ordenDetalles);

        }catch (Exception e){
            System.out.println("puta madre se cago");
            e.printStackTrace();
        }finally {

        }
    }

    @Override
    public void update(Object object) {
        Orden orden=(Orden) object;

        try {
            PreparedStatement preparedStatement=connection.doConnection().prepareStatement("update Orden set Pagada=true where IdOrden=?");

            preparedStatement.setInt(1,orden.getIdOrden());

            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Object object) {

    }

    @Override
    public Orden getToReport(String id) {
        Orden orden=null;

        try {
            PreparedStatement statement=connection.doConnection().prepareStatement("select IdOrden,Fecha,Pagada,Cocinada,Entregada,Orden.IdCliente,PrimerNombre," +
                    "SegundoNombre,PrimerApellido,SegundoApellido,DPI,NIT from Orden " +
                    "inner join Cliente on Orden.IdCliente=Cliente.IdCliente where IdOrden=?");

            statement.setInt(1,Integer.parseInt(id));

            ResultSet resultSet=statement.executeQuery();

            if(resultSet.next()) {
                PreparedStatement statement2 = connection.getConnection().prepareStatement("select IdDetalle,IdOrden,Menu.IdMenu,Menu.Descripcion as Menu_Descripcion,Menu.ValorMenu,Producto.IdProducto,Producto.Descripcion as Producto_Descripcion,Producto.Valor,Producto.Activo from OrdenDetalles\n" +
                        "left join Menu on OrdenDetalles.IdMenu=Menu.IdMenu " +
                        "left join Producto on OrdenDetalles.IdProducto=Producto.IdProducto where IdOrden=?");

                statement2.setInt(1, resultSet.getInt("IdOrden"));

                ResultSet resultSet2 = statement2.executeQuery();


                List<OrdenDetalles> detalle = new ArrayList<>();
                float totalOrder = 0;
                while (resultSet2.next()) {
                    PreparedStatement preparedStatement3=connection.getConnection().prepareStatement("select * from DetalleMenu inner join Producto on DetalleMenu.IdProducto=Producto.IdProducto where IdMenu=?");
                    preparedStatement3.setInt(1,resultSet2.getInt("IdMenu"));

                    ResultSet resultSet3=preparedStatement3.executeQuery();

                    List<DetalleMenu> detalleMenus=new ArrayList<>();

                    while (resultSet3.next()){
                        DetalleMenu detalleMenu=new DetalleMenu(0,0,null,null);
                        detalleMenu.setProducto(new Producto(
                                resultSet3.getInt("IdProducto"),
                                resultSet3.getString("Descripcion"),
                                resultSet3.getFloat("Valor"),
                                resultSet3.getInt("Activo")==1
                        ));

                        detalleMenus.add(detalleMenu);
                    }

                    detalle.add(new OrdenDetalles(
                            resultSet2.getInt("IdDetalle"),
                            resultSet.getInt("IdOrden"),
                            new Producto(resultSet2.getInt("IdProducto"), resultSet2.getString("Producto_Descripcion"), resultSet2.getFloat("Valor"), true),
                            new Menu(resultSet2.getInt("IdMenu"), resultSet2.getString("Menu_Descripcion"), resultSet2.getFloat("ValorMenu"), detalleMenus)
                    ));

                    totalOrder += resultSet2.getFloat("ValorMenu");


                }


                orden = new Orden(
                        resultSet.getInt("IdOrden"),
                        resultSet.getDate("Fecha"),
                        resultSet.getInt("Pagada") == 1,
                        resultSet.getInt("Cocinada") == 1,
                        resultSet.getInt("IdCliente"),
                        detalle
                );

                orden.setTotal(totalOrder);
                orden.setCliente(new Cliente(
                        resultSet.getInt("IdCliente"),
                        resultSet.getString("PrimerNombre"),
                        resultSet.getString("SegundoNombre"),
                        resultSet.getString("PrimerApellido"),
                        resultSet.getString("SegundoApellido"),
                        resultSet.getString("DPI"),
                        resultSet.getString("NIT")
                ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //connection.closeConnection();
        }

        return orden;
    }
}
