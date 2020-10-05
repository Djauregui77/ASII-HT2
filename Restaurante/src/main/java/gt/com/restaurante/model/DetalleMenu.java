/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.com.restaurante.model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author elunadanilo
 */
public class DetalleMenu {
    private int IdDetalleMenu;
    private int IdMenu;
    private Date FechaAsignacion;
    private Producto producto;

    public DetalleMenu(int idDetalleMenu, int idMenu, Date fechaAsignacion, Producto producto) {
        IdDetalleMenu = idDetalleMenu;
        IdMenu = idMenu;
        FechaAsignacion = fechaAsignacion;
        this.producto = producto;
    }


    public int getIdDetalleMenu() {
        return IdDetalleMenu;
    }

    public void setIdDetalleMenu(int idDetalleMenu) {
        IdDetalleMenu = idDetalleMenu;
    }

    public int getIdMenu() {
        return IdMenu;
    }

    public void setIdMenu(int idMenu) {
        IdMenu = idMenu;
    }

    public Date getFechaAsignacion() {
        return FechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        FechaAsignacion = fechaAsignacion;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }


}
