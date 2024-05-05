/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joaquinfigueroa.controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import org.joaquinfigueroa.dto.CargoDTO;
import org.joaquinfigueroa.model.Cargo;
import org.joaquinfigueroa.system.Main;
/**
 *
 * @author Joaki
 */
public class MenuPrincipalController implements Initializable{
    private Main stage;
    
    @FXML
    MenuItem btnMenuClientes, btnMenuCargos, btnTicketSoporte, btnDistribuidores, btnCategoriaProductos, btnEmpleados, btnFacturas, btnCompras, btnPromociones;
    
    @FXML
    TableView tblCargos;
    
    

    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    @FXML
    
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnMenuClientes){
            stage.menuClientesView();
        }else if(event.getSource() == btnTicketSoporte){
            stage.menuTicketSoporteView();
        }else if(event.getSource() == btnMenuCargos){
            stage.menuCargosView();
        }else if(event.getSource() == btnDistribuidores){
            stage.menuDistribuidoresView();
        }else if(event.getSource() == btnCategoriaProductos){
            stage.menuCategoriaProductosView();
        }else if(event.getSource() == btnEmpleados){
            stage.menuEmpleadosView();
        }else if(event.getSource() == btnFacturas){
            stage.menuFacturaView();
        }else if(event.getSource() == btnCompras){
            stage.menuCompraView();
        }else if(event.getSource() == btnPromociones){
            stage.menuPromocionView();
        }
        
    }

}
