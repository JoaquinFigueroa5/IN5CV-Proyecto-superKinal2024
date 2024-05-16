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
import java.sql.Blob;
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
import javafx.scene.control.Label;
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
    private List<File> files = null;
    
    @FXML
    TableView tblProductos;
    
    @FXML
    TextField tfNombre, tfStock, tfPunitario, tfPMayor, tfPCompra, tfProductoId, tfBuscar;
    
    @FXML
    TextArea taDescripcion;
    
    @FXML
    ComboBox cmbDistribuidor, cmbCategoria;
    
    @FXML
    Button btnAgregar, btnEliminar, btnRegresar, btnEditar, btnBuscar;
    
    
    @FXML
    ImageView imgCargar, imgMostrar;
    
    @FXML
    Label lblNombre;
    
    @FXML
    TableColumn colProductoId, colNombre, colDescripcion, colStock, colPUnitario, colPMayor, colPCompra, colDistribuidor, colCategoria;
    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        try{
            if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
            }else if(event.getSource() == btnAgregar){
                stage.formProductoView(1);
            }else if(event.getSource() == btnEditar){
                ProductoDTO.getProductoDTO().setProducto((Producto)tblProductos.getSelectionModel().getSelectedItem());
                stage.formProductoView(2);
            }else if(event.getSource() == btnEliminar){
                int proId = ((Producto)tblProductos.getSelectionModel().getSelectedItem()).getProductoId();
                eliminarProducto(proId);
                cargarDatos();
            }else if(event.getSource() == btnBuscar){
                Producto producto = buscarProducto();
                if(producto != null){
                    cargarDatos();
                    tblProductos.getItems().clear();
                    lblNombre.setText(producto.getNombreProducto());
                    InputStream file = producto.getImagenProducto().getBinaryStream();
                    Image image = new Image(file);
                    imgMostrar.setImage(image);
                    tblProductos.getItems().add(buscarProducto());
                    colProductoId.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("productoId"));
                    colNombre.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombreProducto"));
                    colDescripcion.setCellValueFactory(new PropertyValueFactory<Producto, String>("descripcionProducto"));
                    colStock.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("cantidadStock"));
                    colPUnitario.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioVentaUnitario"));
                    colPMayor.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioVentaMayor"));
                    colPCompra.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioCompra"));
                    colDistribuidor.setCellValueFactory(new PropertyValueFactory<Producto, String>("distribuidor"));
                    colCategoria.setCellValueFactory(new PropertyValueFactory<Producto, String>("categoria"));
                }
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
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
            imgCargar.setImage(image);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void cargarDatos(){
        try{
            tblProductos.setItems(listarProductos());
            colProductoId.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("productoId"));
            colNombre.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombreProducto"));
            colDescripcion.setCellValueFactory(new PropertyValueFactory<Producto, String>("descripcionProducto"));
            colStock.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("cantidadStock"));
            colPUnitario.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioVentaUnitario"));
            colPMayor.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioVentaMayor"));
            colPCompra.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioCompra"));
            colDistribuidor.setCellValueFactory(new PropertyValueFactory<Producto, String>("distribuidor"));
            colCategoria.setCellValueFactory(new PropertyValueFactory<Producto, String>("categoria"));
        }catch(Exception e){
            e.printStackTrace();
        }
    
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
                Blob imagenProducto = resultset.getBlob("imagenProducto");
                String distribuidor = resultset.getString("distribuidor");
                String categoria = resultset.getString("categoria");
                
                productos.add(new Producto(productoId, nombre, descripcion, stock, precioVentaUnitario, precioVentaMayor, precioCompra, imagenProducto, distribuidor, categoria));
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
            String categoriaTbl = ((Producto)tblProductos.getSelectionModel().getSelectedItems()).getCategoria();
            if(categoriaCmb.equals(categoriaTbl)){
                index = i;
                break;
            }
            
        }
        return index;
    }    
    

    
    public Producto buscarProducto(){
        Producto producto = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarProducto(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfBuscar.getText()));
            resultset = statement.executeQuery();
            if(resultset.next()){
                int productoId = resultset.getInt("productoId");
                String nombre = resultset.getString("nombreProducto");
                String descripcion = resultset.getString("descripcionProducto");
                int stock = resultset.getInt("cantidadStock");
                Double PVUnitario = resultset.getDouble("precioVentaUnitario");
                Double PVMayor = resultset.getDouble("precioVentaMayor");
                Double PCompra = resultset.getDouble("precioCompra");
                Blob imagen = resultset.getBlob("imagenProducto");
                String distribuidor = resultset.getString("distribuidor");
                String categoria = resultset.getString("categoria");
                
                producto = new Producto(productoId, nombre, descripcion, stock, PVUnitario, PVMayor, PCompra, imagen, distribuidor, categoria);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return producto;
    } 
    
    public void eliminarProducto(int proId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarProducto(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, proId);
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

    
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }
    
    
}
