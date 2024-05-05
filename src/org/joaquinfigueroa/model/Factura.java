/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joaquinfigueroa.model;

/**
 *
 * @author informatica
 */
public class Factura {
    private int facturaId;
    private String fecha;
    private String hora;
    private double total;
    private int clienteId;
    private String cliente;
    private int empleadoId;
    private String empleado;

    public Factura() {
    }

    public Factura(int facturaId, String fecha, String hora, double total, int clienteId, int empleadoId) {
        this.facturaId = facturaId;
        this.fecha = fecha;
        this.hora = hora;
        this.total = total;
        this.clienteId = clienteId;
        this.empleadoId = empleadoId;
    }

    public Factura(int facturaId,  String fecha, String hora, double total ,String cliente, String empleado) {
        this.facturaId = facturaId;
        this.fecha = fecha;
        this.hora = hora;
        this.total = total;
        this.cliente = cliente;
        this.empleado = empleado;
 
    }
    

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    @Override
    public String toString() {
        return "Factura{" + "facturaId=" + facturaId + ", fecha=" + fecha + ", hora=" + hora + ", total=" + total + ", clienteId=" + clienteId + ", cliente=" + cliente + ", empleadoId=" + empleadoId + ", empleado=" + empleado + '}';
    }

    
    
    
}
