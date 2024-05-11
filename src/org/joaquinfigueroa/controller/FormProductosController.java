/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joaquinfigueroa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import org.joaquinfigueroa.dao.Conexion;
import org.joaquinfigueroa.dto.ProductoDTO;
import org.joaquinfigueroa.model.CategoriaProducto;
import org.joaquinfigueroa.model.Distribuidor;
import org.joaquinfigueroa.model.Producto;
import org.joaquinfigueroa.system.Main;

/**
 * FXML Controller class
 *
 * @author Joaki
 */
public class FormProductosController implements Initializable {
    private Main stage;    
    private int op;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultset = null;
    private List<File> files = null;
    
    @FXML
    TextField tfProductoId, tfNombre, tfStock, tfPUnitario, tfPMayor, tfPCompra;
    
    @FXML
    TextArea taDescripcion;
    
    @FXML
    ComboBox cmbDistribuidor, cmbCategoria;
    
    @FXML
    TableView tblProductos;
    
    @FXML
    Button btnCargar, btnRegresar;
    
    @FXML
    ImageView imgCarga, imgMostrar;
   

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    public void setOp(int op) {
        this.op = op;
    }
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(ProductoDTO.getProductoDTO().getProducto() != null){
            cargarDatoss(ProductoDTO.getProductoDTO().getProducto());
        }
        cmbCategoria.setItems(listarCategoriaProductos());
        cmbDistribuidor.setItems(listarDistribuidores());
    }    
    
    public void cargarDatoss(Producto producto){
        tfProductoId.setText(Integer.toString(producto.getProductoId()));
        tfNombre.setText(producto.getNombreProducto());
        taDescripcion.setText(producto.getDescripcionProducto());
        tfStock.setText(Integer.toString(producto.getCantidadStock()));
        tfPUnitario.setText(Double.toString(producto.getPrecioVentaUnitario()));
        tfPMayor.setText(Double.toString(producto.getPrecioVenteMayor()));
        tfPCompra.setText(Double.toString(producto.getPrecioCompra()));
        cmbDistribuidor.getSelectionModel().select(obtenerIndexDistribuidor());
        cmbCategoria.getSelectionModel().select(obtenerIndexCategoria());

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
    
    public int obtenerIndexCategoria(){
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
    
    public void agregarProductos(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarProducto(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombre.getText());
            statement.setString(2, taDescripcion.getText());
            statement.setInt(3, Integer.parseInt(tfStock.getText()));
            statement.setDouble(4, Double.parseDouble(tfPUnitario.getText()));
            statement.setDouble(5, Double.parseDouble(tfPMayor.getText()));
            statement.setDouble(6, Double.parseDouble(tfPCompra.getText()));
            InputStream img = new FileInputStream(files.get(0));
            statement.setBinaryStream(7, img);
            statement.setInt(8, ((Distribuidor)cmbDistribuidor.getSelectionModel().getSelectedItem()).getDistribuidorId());
            statement.setInt(9, ((CategoriaProducto)cmbCategoria.getSelectionModel().getSelectedItem()).getCategoriaProductosId());
            statement.execute();
        }catch(Exception e){
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
            statement.setDouble(5, Double.parseDouble(tfPUnitario.getText()));
            statement.setDouble(6, Double.parseDouble(tfPMayor.getText()));
            statement.setDouble(7, Double.parseDouble(tfPCompra.getText()));
            InputStream img = new FileInputStream(files.get(0));
            statement.setBinaryStream(8, img);
            statement.setInt(9, ((Distribuidor)cmbDistribuidor.getSelectionModel().getSelectedItem()).getDistribuidorId());
            statement.setInt(10, ((CategoriaProducto)cmbCategoria.getSelectionModel().getSelectedItem()).getCategoriaProductosId());
            statement.execute();
        }catch(Exception e){
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
    
    @FXML
    public void handleOnDrag(DragEvent event){
        if(event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
        }
    }
    
    @FXML
    public void handleOnDrop(DragEvent event){
        try{
            files = event.getDragboard().getFiles();
            FileInputStream file = new FileInputStream(files.get(0));
            Image image = new Image(file);
            imgCarga.setImage(image);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuProductoView();
        }else if(event.getSource() == btnCargar){
            if(op == 1){
                agregarProductos();
                ProductoDTO.getProductoDTO().setProducto(null);
                stage.menuProductoView();
            }else if(op == 2){
                editarProductos();
                ProductoDTO.getProductoDTO().setProducto(null);
                stage.menuProductoView();
            }
            
            stage.menuProductoView();
        }
      
    }
    
}
