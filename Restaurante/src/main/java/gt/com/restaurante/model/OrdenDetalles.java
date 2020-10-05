/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.com.restaurante.model;

/**
 *
 * @author elunadanilo
 */
public class OrdenDetalles {

    private int IdDetalle;
    private int IdOrden;
    private Producto producto;
    private Menu menu;

    public OrdenDetalles(int idDetalle, int idOrden, Producto producto, Menu menu) {
        IdDetalle = idDetalle;
        IdOrden = idOrden;
        this.producto = producto;
        this.menu = menu;
    }

    public int getIdDetalle() {
        return IdDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        IdDetalle = idDetalle;
    }

    public int getIdOrden() {
        return IdOrden;
    }

    public void setIdOrden(int idOrden) {
        IdOrden = idOrden;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

}
