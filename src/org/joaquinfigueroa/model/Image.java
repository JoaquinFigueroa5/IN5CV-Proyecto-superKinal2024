/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joaquinfigueroa.model;

import java.io.InputStream;

/**
 *
 * @author Joaki
 */
public class Image {
    private String codImg;
    private String nombre;
    InputStream imagen1;
    private byte[] imagen2;

    public Image() {
    }

    public String getCodImg() {
        return codImg;
    }

    public void setCodImg(String codImg) {
        this.codImg = codImg;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public InputStream getImagen1() {
        return imagen1;
    }

    public void setImagen1(InputStream imagen1) {
        this.imagen1 = imagen1;
    }

    public byte[] getImagen2() {
        return imagen2;
    }

    public void setImagen2(byte[] imagen2) {
        this.imagen2 = imagen2;
    }
    
    
}
