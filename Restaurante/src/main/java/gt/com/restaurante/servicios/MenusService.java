package gt.com.restaurante.servicios;

import gt.com.restaurante.dao.MenusDao;
import gt.com.restaurante.model.DetalleMenu;
import gt.com.restaurante.model.Menu;
import gt.com.restaurante.model.Producto;
import gt.com.restaurante.utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class MenusService implements MenusDao {
    private DBConnection connection=new DBConnection();

    @Override
    public Optional get(long id) {
        return Optional.empty();
    }

    @Override
    public List getAll() {
        List<Menu> menus=new ArrayList<>();

        try {
            PreparedStatement preparedStatement=connection.doConnection().prepareStatement("select * from Menu limit 10");
            ResultSet resultSet=preparedStatement.executeQuery();

            while (resultSet.next()) {
                PreparedStatement preparedStatement2=connection.getConnection().prepareStatement("select IdDetalleMenu, IdMenu,Producto.IdProducto,Producto.Descripcion,Producto.Valor,FechaAsignacion from DetalleMenu" +
                        " inner join Producto on DetalleMenu.IdProducto=Producto.IdProducto where IdMenu=? order by IdDetalleMenu");

                preparedStatement2.setInt(1,resultSet.getInt("IdMenu"));
                ResultSet resultSet2=preparedStatement2.executeQuery();

                List<DetalleMenu> detalleMenus=new ArrayList<>();

                while (resultSet2.next()){
                    detalleMenus.add(new DetalleMenu(
                            resultSet2.getInt("IdDetalleMenu"),
                            resultSet2.getInt("IdMenu"),
                            resultSet2.getDate("FechaAsignacion"),
                            new Producto(
                                    resultSet2.getInt("IdProducto"),
                                    resultSet2.getString("Descripcion"),
                                    resultSet2.getFloat("Valor"),
                                    true
                            )
                    ));
                }

                menus.add(
                        new Menu(
                                resultSet.getInt("IdMenu"),
                                resultSet.getString("Descripcion"),
                                resultSet.getFloat("ValorMenu"),
                                detalleMenus
                        )
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //connection.closeConnection();
        }
        return menus;
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
