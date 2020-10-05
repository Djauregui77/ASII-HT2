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
public class Empleado {
    private int IdEmpleado;
    private String PrimerNombreEmpleado;
    private String SegundoNombreEmpleado;
    private String PrimerApellidoEmpleado;
    private String SegundoApellidoEmpleado;
    
    public Empleado(int IdEmpleado, String PrimerNombreEmpleado, String SegundoNombreEmpleado, String PrimerApellidoEmpleado, String SegundoApellidoEmpleado) {
        this.IdEmpleado = IdEmpleado;
        this.PrimerNombreEmpleado = PrimerNombreEmpleado;
        this.SegundoNombreEmpleado = SegundoNombreEmpleado;
        this.PrimerApellidoEmpleado = PrimerApellidoEmpleado;
        this.SegundoApellidoEmpleado = SegundoApellidoEmpleado;
    }
    
    public int getIdEmpleado() {
        return IdEmpleado;
    }
    public void setIdEmpleado(int idEmpleado) {
        IdEmpleado = idEmpleado;
    }
    public String getPrimerNombreEmpleado() {
        return PrimerNombreEmpleado;
    }
    public void setPrimerNombreEmpleado(String primerNombreEmpleado) {
        PrimerNombreEmpleado = primerNombreEmpleado;
    }
    public String getSegundoNombreEmpleado() {
        return SegundoNombreEmpleado;
    }
    public void setSegundoNombreEmpleado(String segundoNombreEmpleado) {
        SegundoNombreEmpleado = segundoNombreEmpleado;
    }
    public String getPrimerApellidoEmpleado() {
        return PrimerApellidoEmpleado;
    }
    public void setPrimerApellidoEmpleado(String primerApellidoEmpleado) {
        PrimerApellidoEmpleado = primerApellidoEmpleado;
    }
    public String getSegundoApellidoEmpleado() {
        return SegundoApellidoEmpleado;
    }
    public void setSegundoApellidoEmpleado(String segundoApellidoEmpleado) {
        SegundoApellidoEmpleado = segundoApellidoEmpleado;
    }
}
