package gt.com.restaurante.servicios;

import gt.com.restaurante.dao.ProductosDao;
import gt.com.restaurante.model.Producto;
import gt.com.restaurante.utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductosService implements ProductosDao {
    private DBConnection connection=new DBConnection();


    @Override
    public Optional get(long id) {
        return Optional.empty();
    }

    @Override
    public List getAll() {
        List<Producto> productos=new ArrayList<>();

        try{
            PreparedStatement preparedStatement=connection.doConnection().prepareStatement("select * from Producto limit 10");
            ResultSet resultSet=preparedStatement.executeQuery();

            while(resultSet.next()){
                productos.add(new Producto(
                        resultSet.getInt("IdProducto"),
                        resultSet.getString("Descripcion"),
                        resultSet.getFloat("Valor"),
                        resultSet.getInt("Activo")==1
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //connection.closeConnection();
        }
        return productos;
    }

    @Override
    public void save(Object object) {

    }

    @Override
    public void update(Object object) {

    }

    @Override
    public void delete(Object object) {

    }
}
