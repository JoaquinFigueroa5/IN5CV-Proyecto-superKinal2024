/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joaquinfigueroa.model;

/**
 *
 * @author Joaki
 */
public class Promocion {
    private int promocionId;
    private String precioPromocion;
    private String descripcionPromocion;
    private String fechaInicio;
    private String fechaFinalizacion;
    private int productoId;
    private String producto;

    public Promocion() {
    }

    public Promocion(int promocionId, String precioPromocion, String descripcionPromocion, String fechaInicio, String fechaFinalizacion, int productoId) {
        this.promocionId = promocionId;
        this.precioPromocion = precioPromocion;
        this.descripcionPromocion = descripcionPromocion;
        this.fechaInicio = fechaInicio;
        this.fechaFinalizacion = fechaFinalizacion;
        this.productoId = productoId;
    }

    public Promocion(int promocionId, String precioPromocion, String descripcionPromocion, String fechaInicio, String fechaFinalizacion, String producto) {
        this.promocionId = promocionId;
        this.precioPromocion = precioPromocion;
        this.descripcionPromocion = descripcionPromocion;
        this.fechaInicio = fechaInicio;
        this.fechaFinalizacion = fechaFinalizacion;
        this.producto = producto;
    }

    public int getPromocionId() {
        return promocionId;
    }

    public void setPromocionId(int promocionId) {
        this.promocionId = promocionId;
    }

    public String getPrecioPromocion() {
        return precioPromocion;
    }

    public void setPrecioPromocion(String precioPromocion) {
        this.precioPromocion = precioPromocion;
    }

    public String getDescripcionPromocion() {
        return descripcionPromocion;
    }

    public void setDescripcionPromocion(String descripcionPromocion) {
        this.descripcionPromocion = descripcionPromocion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(String fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "Promocion{" + "promocionId=" + promocionId + ", precio=" + precioPromocion + ", descripcion=" + descripcionPromocion + ", fechaInicio=" + fechaInicio + ", fechaFinalizacion=" + fechaFinalizacion + ", productoId=" + productoId + ", producto=" + producto + '}';
    }
    
    
}
