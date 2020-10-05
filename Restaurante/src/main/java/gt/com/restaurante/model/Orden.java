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
public class Orden {
    private int IdOrden;
    private Date Fecha;
    private boolean Pagada;
    private boolean Cocinada;
    private int IdCliente;
	private float total;
	private Cliente cliente;
	private List<OrdenDetalles> detalle;

	public Orden(int idOrden, Date fecha, boolean pagada, boolean cocinada, int idCliente, List<OrdenDetalles> detalle) {
		IdOrden = idOrden;
		Fecha = fecha;
		Pagada = pagada;
		Cocinada = cocinada;
		IdCliente = idCliente;
		this.detalle = detalle;
	}


	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public void setIdOrden(int idOrden) {
		this.IdOrden = idOrden;
	}
        public int getIdOrden() {
		return IdOrden;
	}

	public void setFecha(Date fecha) {
		this.Fecha = fecha;
	}
        public Date getFecha() {
		return Fecha;
	}

	public void setPagada(boolean pagada) {
		this.Pagada = pagada;
	}
        
        public boolean getPagada() {
		return Pagada;
	}
        
        public boolean getCocinada() {
		return Cocinada;
	}

	public void setCocinada(boolean cocinada) {
		this.Cocinada = cocinada;
	}
        
        public void setIdCliente(int idCliente) {
		this.IdCliente = idCliente;
	}
        public int getIdCliente() {
		return IdCliente;
	}

	public boolean isPagada() {
		return Pagada;
	}

	public boolean isCocinada() {
		return Cocinada;
	}

	public List<OrdenDetalles> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<OrdenDetalles> detalle) {
		this.detalle = detalle;
	}
}
