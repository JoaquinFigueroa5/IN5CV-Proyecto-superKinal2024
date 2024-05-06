/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joaquinfigueroa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.joaquinfigueroa.model.Image;

/**
 *
 * @author Joaki
 */
public class ImagenDAO {
    private static Connection conexion = null;
    private static PreparedStatement statement = null;


    
    public void agregarImagen(Image img){
        Conexion cn = new Conexion();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "INSERT INTO IMAGEN VALUES(?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, img.getCodImg());
            statement.setString(2, img.getNombre());
            statement.setBlob(3, img.getImagen1());
            
            statement.executeUpdate();

            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
                               
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
