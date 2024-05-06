/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joaquinfigueroa.controller;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.joaquinfigueroa.dao.Conexion;
import org.joaquinfigueroa.model.CategoriaProducto;
import org.joaquinfigueroa.model.Distribuidor;
import org.joaquinfigueroa.model.Producto;
import org.joaquinfigueroa.system.Main;

/**
 * FXML Controller class
 *
 * @author Joaki
 */
public class MenuProductosController implements Initializable {
    
    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultset = null;
    
    @FXML
    TableView tblProductos;
    
    @FXML
    TextField tfNombre, tfStock, tfPunitario, tfPMayor, tfPCompra, tfProductoId, tfBuscar;
    
    @FXML
    TextArea taDescripcion;
    
    @FXML
    ComboBox cmbDistribuidor, cmbCategoria;
    
    @FXML
    Button btnGuardar, btnEliminar, btnVaciar, btnRegresar;
    
    @FXML
    TableColumn colProductoId, colNombre, colDescripcion, colStock, colPUnitario, colPMayor, colPCompra, colDistribuidor, colCategoria;
    
    @FXML
    ImageView imgProductos;
    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfProductoId.getText().equals("")){
                //agregarTicket();
                cargarDatos();
            }else{
                //editarTicket();
            }
        }else if(event.getSource() == btnVaciar){
            vaciarCampos();
        }
    }
    
    public void vaciarCampos(){
        tfProductoId.clear();
        tfNombre.clear();
        tfStock.clear();
        tfPunitario.clear();
        tfPMayor.clear();
        tfPCompra.clear();
        taDescripcion.clear();
        cmbDistribuidor.getSelectionModel().clearSelection();
        cmbCategoria.getSelectionModel().clearSelection();
        
    }

    
    public void cargarDatos(){
        tblProductos.setItems(listarProductos());
        colProductoId.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("productoId"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombreProducto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Producto, String>("descripcionProducto"));
        colStock.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("cantidadStock"));
        colPUnitario.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioVentaUnitario"));
        colPMayor.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioVentaMayor"));
        colPCompra.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioCompra"));
        colDistribuidor.setCellValueFactory(new PropertyValueFactory<Producto, String>("distribuidor"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<Producto, String>("categoriaProductos"));
        tblProductos.getSortOrder().add(colProductoId);

    }
    
    
    
    public ObservableList<Producto> listarProductos(){
        ArrayList<Producto> productos = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarProducto()";
            statement = conexion.prepareStatement(sql);
            resultset = statement.executeQuery();
            
            while(resultset.next()){
                int productoId = resultset.getInt("productoId");
                String nombre = resultset.getString("nombreProducto");
                String descripcion = resultset.getString("descripcionProducto");
                int stock = resultset.getInt("cantidadStock");
                double precioVentaUnitario = resultset.getDouble("precioVentaUnitario");
                double precioVentaMayor = resultset.getDouble("precioVentaMayor");
                double precioCompra = resultset.getDouble("precioCompra");
               // byte[] bytesImagen = resultset.getBytes("imagenProducto");
                String distribuidor = resultset.getString("distribuidor");
                String categoriaProductoS = resultset.getString("categoriaProductos");
                
                productos.add(new Producto(productoId, nombre, descripcion, stock, precioVentaUnitario, precioVentaMayor, precioCompra, distribuidor, categoriaProductoS));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultset != null){
                    resultset.close();
                }
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
        return FXCollections.observableList(productos);
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    public int obtenerIndexDistribuidor(){
        int index = 0;
        for(int i = 0 ; i <= cmbDistribuidor.getItems().size() ; i++){
            String distribuidorCmb = cmbDistribuidor.getItems().get(i).toString();
            String distribuidorTbl = ((Producto)tblProductos.getSelectionModel().getSelectedItems()).getDistribuidor();
            if(distribuidorCmb.equals(distribuidorTbl)){
                index = i;
                break;
            }
            
        }
        return index;
    }
    
    public int obtenerIndexCategoriaProductos(){
        int index = 0;
        for(int i = 0 ; i <= cmbCategoria.getItems().size() ; i++){
            String categoriaCmb = cmbCategoria.getItems().get(i).toString();
            String categoriaTbl = ((Producto)tblProductos.getSelectionModel().getSelectedItems()).getCategoriaProdcutoS();
            if(categoriaCmb.equals(categoriaTbl)){
                index = i;
                break;
            }
            
        }
        return index;
    }    
    
    public ObservableList<CategoriaProducto> listarCategoriaProductos(){
        ArrayList<CategoriaProducto> categoriaProductos = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCategoriaProductos()";
            statement = conexion.prepareStatement(sql);
            resultset = statement.executeQuery();
            
            while(resultset.next()){
                int categoriaProductosId = resultset.getInt("categoriaProductosId");
                String nombreCategoria = resultset.getString("nombreCategoria");
                String descripcionCategoria = resultset.getString("descripcionCategoria");
                categoriaProductos.add(new CategoriaProducto(categoriaProductosId, nombreCategoria, descripcionCategoria));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultset != null){
                    resultset.close();
                }
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
        return FXCollections.observableList(categoriaProductos);
    }
    public ObservableList<Distribuidor> listarDistribuidores(){
        ArrayList<Distribuidor> distribuidores = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarDistribuidores()";
            statement = conexion.prepareStatement(sql);
            resultset = statement.executeQuery();
            
            while(resultset.next()){
                int distribuidorId = resultset.getInt("distribuidorId");
                String nombre = resultset.getString("nombreDistribuidor");
                String direccion = resultset.getString("direccionDistribuidor");
                String nit = resultset.getString("nitDistribuidor");
                String telefono = resultset.getString("telefonoDistribuidor");
                String web = resultset.getString("web");
                
                distribuidores.add(new Distribuidor(distribuidorId, nombre, direccion, nit, telefono, web));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultset != null){
                    resultset.close();
                }
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
        return FXCollections.observableList(distribuidores);
    }
    
    public void agregarProductos(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarProducto(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombre.getText());
            statement.setString(2, taDescripcion.getText());
            statement.setInt(3, Integer.parseInt(tfStock.getText()));
            statement.setDouble(4, Double.parseDouble(tfPunitario.getText()));
            statement.setDouble(5, Double.parseDouble(tfPMayor.getText()));
            statement.setDouble(6, Double.parseDouble(tfPCompra.getText()));
            // statement.setBlob(7, imgProductos.getImage());
            statement.setInt(8, ((Distribuidor)cmbDistribuidor.getSelectionModel().getSelectedItem()).getDistribuidorId());
            statement.setInt(9, ((CategoriaProducto)cmbCategoria.getSelectionModel().getSelectedItem()).getCategoriaProductosId());
            statement.execute();
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
    
    public void editarProductos(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarProducto(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfProductoId.getText()));
            statement.setString(2, tfNombre.getText());
            statement.setString(3, taDescripcion.getText());
            statement.setInt(4, Integer.parseInt(tfStock.getText()));
            statement.setDouble(5, Double.parseDouble(tfPunitario.getText()));
            statement.setDouble(6, Double.parseDouble(tfPMayor.getText()));
            statement.setDouble(7, Double.parseDouble(tfPCompra.getText()));
            //statement.setBlob(8, Blob.parseBlob(imgProductos.getImage()));
            statement.setInt(9, ((Distribuidor)cmbDistribuidor.getSelectionModel().getSelectedItem()).getDistribuidorId());
            statement.setInt(10, ((CategoriaProducto)cmbCategoria.getSelectionModel().getSelectedItem()).getCategoriaProductosId());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(conexion != null){
                conexion.close();
                }
                if(statement != null){
                    statement.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbCategoria.setItems(listarCategoriaProductos());
        cmbDistribuidor.setItems(listarDistribuidores());
        cargarDatos();
    }
    
    
}
