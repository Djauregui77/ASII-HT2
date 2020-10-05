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
public class Producto {
    private int IdProducto;
    private String Descripcion;
    private float Valor;
    private boolean Activo;
    
      public Producto(int IdProducto, String Descripcion, float Valor, boolean Activo) {
        this.IdProducto = IdProducto;
        this.Descripcion = Descripcion;
        this.Valor = Valor;
        this.Activo = Activo;
    }
    
    public void setIdProducto(int idProducto) {
	this.IdProducto = idProducto;
    }
    public int getIdDetalle() {
	return IdProducto;
    }
    
    public String getDescripcion() {
        return Descripcion;
    }
    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
    public void setValor(float valor) {
	this.Valor = Valor;
    }
    public float getValor() {
	return Valor;
    }
    public boolean getActivo() {
	return Activo;
    }

    public void setActivo(boolean activo) {
	this.Activo = activo;
    }
}
