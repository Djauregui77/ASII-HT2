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
public class Cliente {
    private int IdCliente;
    private String PrimerNombre;
    private String SegundoNombre;
    private String PrimerApellido;
    private String SegundoApellido;
    private String DPI;
    private String NIT;

    public Cliente(int IdCliente, String PrimerNombre, String SegundoNombre, String PrimerApellido, String SegundoApellido, String DPI, String NIT) {
        this.IdCliente = IdCliente;
        this.PrimerNombre = PrimerNombre;
        this.SegundoNombre = SegundoNombre;
        this.PrimerApellido = PrimerApellido;
        this.SegundoApellido = SegundoApellido;
        this.DPI = DPI;
        this.NIT = NIT;
    }
     
    public int getIdCliente() {
            return IdCliente;
    }
    public void setIdCliente(int idCliente) {
            IdCliente = idCliente;
    }
    public String getIPrimerNombre() {
            return PrimerNombre;
    }
    public void setPrimerNombre(String primernombre) {
            PrimerNombre = primernombre;
    }
    public String getSegundoNombre() {
            return SegundoNombre;
    }
    public void setSegundoNombre(String segundonombre) {
            SegundoNombre = segundonombre;
    }
     public String getPrimerApellido() {
            return PrimerApellido;
    }
    public void setPrimerApellido(String primerapellido) {
            PrimerApellido = primerapellido;
    }
     public String getSegundoApellido() {
            return SegundoApellido;
    }
    public void setSegundoApellido(String segundoapellido) {
            SegundoApellido = segundoapellido;
    }
     public String getDPI() {
            return DPI;
    }
    public void setDPI(String dpi) {
            DPI = dpi;
    }
       public String getNIT() {
            return NIT;
    }
    public void setNIT(String nit) {
            NIT = nit;
    }
}
