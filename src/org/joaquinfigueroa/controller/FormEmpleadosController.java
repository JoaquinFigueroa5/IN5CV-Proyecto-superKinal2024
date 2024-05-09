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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.joaquinfigueroa.dao.Conexion;
import org.joaquinfigueroa.dto.EmpleadoDTO;
import org.joaquinfigueroa.model.Cargo;
import org.joaquinfigueroa.model.Empleado;
import org.joaquinfigueroa.system.Main;

/**
 * FXML Controller class
 *
 * @author Joaki
 */
public class FormEmpleadosController implements Initializable {
    private Main stage;    
    private int op;
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultset = null;

    
    @FXML
    TextField tfEmpleadoId, tfNombre, tfApellido, tfSueldo, tfEntrada, tfSalida;
    
    @FXML
    Button btnAgregar, btnCancelar;
    
    @FXML
    ComboBox cmbCargo, cmbEncargado;
    
    @FXML
    TableView tblEmpleados;
    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(EmpleadoDTO.getEmpleadoDTO().getEmpleado() != null){
            cargarDatos(EmpleadoDTO.getEmpleadoDTO().getEmpleado());
        }
        cmbCargo.setItems(listarCargos());
        cmbEncargado.setItems(listarEncargados());
        
    }    
    
    public ObservableList<Cargo> listarCargos(){
        ArrayList<Cargo> cargos = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCargos()";
            statement = conexion.prepareStatement(sql);
            resultset = statement.executeQuery();
            
            while(resultset.next()){
                int cargoId = resultset.getInt("cargoId");
                String nombreCargo = resultset.getString("nombreCargo");
                String descripcion = resultset.getString("descripcionCargo");
                
                cargos.add(new Cargo(cargoId, nombreCargo, descripcion));
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
        return FXCollections.observableList(cargos);
    }
    
    public ObservableList<Empleado> listarEncargados(){
        ArrayList<Empleado> encargados = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarEncargados()";
            statement = conexion.prepareStatement(sql);
            resultset = statement.executeQuery();
            
            while(resultset.next()){
                int empleadoId = resultset.getInt("empleadoId");
                String nombreEmpleado = resultset.getString("nombreEmpleado");
                String encargado = resultset.getString("encargado");
                
                encargados.add(new Empleado(empleadoId, nombreEmpleado, encargado));
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
        return FXCollections.observableList(encargados);
    }
    
    
    
    
    public void cargarDatos(Empleado empleado){
        tfEmpleadoId.setText(Integer.toString(empleado.getEmpleadoId()));
        tfNombre.setText(empleado.getNombreEmpleado());
        tfApellido.setText(empleado.getApellidoEmpleado());
        tfSueldo.setText(Double.toString(empleado.getSueldo()));
        tfEntrada.setText(empleado.getHoraEntrada());
        tfSalida.setText(empleado.getHoraSalida());
        cmbCargo.getSelectionModel().select(obtenerIndexCargo());
        cmbEncargado.getSelectionModel().select(obtenerIndexEncargado());

    }
    
    public int obtenerIndexCargo(){
        int index = 0;
        for(int i = 0 ; i <= cmbCargo.getItems().size() ; i++){
            String cargoCmb = cmbCargo.getItems().get(i).toString();
            String cargoTbl = ((Empleado)tblEmpleados.getSelectionModel().getSelectedItems()).getNombreCargo();
            if(cargoCmb.equals(cargoTbl)){
                index = i;
                break;
            }
            
        }
        return index;
    }
    public int obtenerIndexEncargado(){
        int index = 0;
        for(int i = 0 ; i <= cmbEncargado.getItems().size() ; i++){
            String encargadoCmb = cmbEncargado.getItems().get(i).toString();
            String encargadoTbl = ((Empleado)tblEmpleados.getSelectionModel().getSelectedItems()).getEncargado();
            if(encargadoCmb.equals(encargadoTbl)){
                index = i;
                break;
            }
            
        }
        return index;
    }
    
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    public void setOp(int op) {
        this.op = op;
    }
    
    public void agregarEmpleado(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarEmpleados(?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombre.getText());
            statement.setString(2, tfApellido.getText());
            statement.setString(3, tfSueldo.getText());
            statement.setString(4, tfEntrada.getText());
            statement.setString(5, tfSalida.getText());
            statement.setInt(6, ((Cargo)cmbCargo.getSelectionModel().getSelectedItem()).getCargoId());
            statement.setInt(7, ((Empleado)cmbEncargado.getSelectionModel().getSelectedItem()).getEncargadoId());

            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(conexion != null){
                    conexion.close();
                }else if(statement != null){
                    statement.close();
                }
            }catch(SQLException e){
                
            }
        }
    }
    
    public void editarEmpleado(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarEmpleados(?,?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfEmpleadoId.getText()));
            statement.setString(2, tfNombre.getText());
            statement.setString(3, tfApellido.getText());
            statement.setString(4, tfSueldo.getText());
            statement.setString(5, tfEntrada.getText());
            statement.setString(6, tfSalida.getText());
            statement.setInt(7, ((Cargo)cmbCargo.getSelectionModel().getSelectedItem()).getCargoId());
            statement.setInt(8, ((Empleado)cmbEncargado.getSelectionModel().getSelectedItem()).getEncargadoId());
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
            }
        }
    }
    
    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar){
            stage.menuEmpleadosView();
        }else if(event.getSource() == btnAgregar){
            if(op == 1){
                agregarEmpleado();
                EmpleadoDTO.getEmpleadoDTO().setEmpleado(null);
                stage.menuEmpleadosView();
            }else if(op == 2){
                editarEmpleado();
                EmpleadoDTO.getEmpleadoDTO().setEmpleado(null);
                stage.menuEmpleadosView();
            }
            
            stage.menuEmpleadosView();
        }
      
    }
        
    
}
