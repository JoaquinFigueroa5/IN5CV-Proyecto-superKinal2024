/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joaquinfigueroa.controller;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.joaquinfigueroa.dao.Conexion;
import org.joaquinfigueroa.model.Producto;
import org.joaquinfigueroa.model.Promocion;
import org.joaquinfigueroa.system.Main;

/**
 * FXML Controller class
 *
 * @author Joaki
 */
public class MenuPromocionesController implements Initializable {
    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultset = null;
    
    @FXML
    TextField tfPromocionId, tfPrecio, tfFechaInicio, tfFechaFinalizacion;
    
    @FXML
    TextArea taDescripcion;
    
    @FXML
    ComboBox cmbProducto;
    
    @FXML
    TableView tblPromociones;
    
    @FXML
    TableColumn colPromocionId, colPrecio, colDescripcion, colFechaInicio, colFechaFinalizacion, colProducto;
    
    @FXML
    Button btnGuardar, btnEliminar, btnRegresar, btnVaciar;


    
    public ObservableList<Promocion> listarPromociones(){
        ArrayList<Promocion> promociones = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarPromociones()";
            statement = conexion.prepareStatement(sql);
            resultset = statement.executeQuery();
            
            while(resultset.next()){
                int promocionId = resultset.getInt("promocionId");
                String precioPromocion = resultset.getString("precioPromocion");
                String descripcionPromocion = resultset.getString("descripcionPromocion");
                String fechaInicio = resultset.getString("fechaInicio");
                String fechaFinalizacion = resultset.getString("fechaFInalizacion");
                String nombreProducto = resultset.getString("nombreProducto");
                
                promociones.add(new Promocion(promocionId, precioPromocion, descripcionPromocion, fechaInicio, fechaFinalizacion, nombreProducto));
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
                
            }
        }
        
        return FXCollections.observableList(promociones);
    }
    
    public void cargarDatos(){
        tblPromociones.setItems(listarPromociones());
        colPromocionId.setCellValueFactory(new PropertyValueFactory<Promocion, Integer>("promocionId"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Promocion, Double>("precioPromocion"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Promocion, String>("descripcionPromocion"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<Promocion, String>("fechaInicio"));
        colFechaFinalizacion.setCellValueFactory(new PropertyValueFactory<Promocion, Integer>("fechaFinalizacion"));
        colProducto.setCellValueFactory(new PropertyValueFactory<Promocion, String>("nombreProducto"));
        tblPromociones.getSortOrder().add(colPromocionId);


    }
    
    public void cargarDatosEditar(){
        Promocion pn = (Promocion)tblPromociones.getSelectionModel().getSelectedItem();
        if(pn != null){
            tfPromocionId.setText(Integer.toString(pn.getPromocionId()));
            tfPrecio.setText(pn.getPrecio());
            taDescripcion.setText(pn.getDescripcion());
            tfFechaInicio.setText(pn.getFechaInicio());
            tfFechaFinalizacion.setText(pn.getFechaFinalizacion());
            cmbProducto.getSelectionModel().select(obtenerIndexProducto());
        }
    }
    
    public int obtenerIndexProducto(){
        int index = 0;
        for(int i = 0 ; i <= cmbProducto.getItems().size() ; i++){
            String productoCmb = cmbProducto.getItems().get(i).toString();
            String productoTbl = ((Promocion)tblPromociones.getSelectionModel().getSelectedItems()).getProducto();
            if(productoCmb.equals(productoTbl)){
                index = i;
                break;
            }
            
        }
        return index;
    }
    
    public void agregarPromociones(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarPromociones(?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setDouble(1, Double.parseDouble(tfPrecio.getText()));
            statement.setString(2, taDescripcion.getText());
            statement.setString(3, tfFechaInicio.getText());
            statement.setString(4, tfFechaFinalizacion.getText());
            statement.setInt(5, ((Producto)cmbProducto.getSelectionModel().getSelectedItem()).getProductoId());
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
    
    public void editarPromociones(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarPromociones(?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfPromocionId.getText()));
            statement.setDouble(2, Double.parseDouble(tfPrecio.getText()));
            statement.setString(3, taDescripcion.getText());
            statement.setString(4, tfFechaInicio.getText());
            statement.setString(5, tfFechaFinalizacion.getText());
            statement.setInt(6, 0);
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
                String distribuidor = resultset.getString("nombreDistribuidor");
                String categoriaProductoS = resultset.getString("nombreCategoria");
                
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
    
    public void vaciarCampos(){
        tfPromocionId.clear();
        tfPrecio.clear();
        taDescripcion.clear();
        tfFechaInicio.clear();
        tfFechaFinalizacion.clear();
        cmbProducto.getSelectionModel().clearSelection();
        
    }
    
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfPromocionId.getText().equals("")){
                agregarPromociones();
                cargarDatos();
            }else{
                editarPromociones();
            }
        }else if(event.getSource() == btnVaciar){
            vaciarCampos();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cmbProducto.setItems(listarProductos());
        
    }    
    
}
