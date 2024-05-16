/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joaquinfigueroa.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.joaquinfigueroa.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class LoginController implements Initializable {
    private Main stage;
    
    @FXML
    TextField tfUser;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    
}
