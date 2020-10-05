/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.com.restaurante.model;

import java.util.List;

/**
 *
 * @author elunadanilo
 */
public class Menu {
    private int IdMenu;
    private String Descripcion;
    private float ValorMenu;
    private List<DetalleMenu> productos;

    public Menu(int idMenu, String descripcion, float valorMenu, List<DetalleMenu> productos) {
        IdMenu = idMenu;
        Descripcion = descripcion;
        ValorMenu = valorMenu;
        this.productos = productos;
    }

    public int getIdMenu() {
        return IdMenu;
    }

    public void setIdMenu(int idMenu) {
        IdMenu = idMenu;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public float getValorMenu() {
        return ValorMenu;
    }

    public void setValorMenu(float valorMenu) {
        ValorMenu = valorMenu;
    }

    public List<DetalleMenu> getProductos() {
        return productos;
    }

    public void setProductos(List<DetalleMenu> productos) {
        this.productos = productos;
    }
}
