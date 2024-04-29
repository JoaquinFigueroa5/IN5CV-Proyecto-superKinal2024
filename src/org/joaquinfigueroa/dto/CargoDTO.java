/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joaquinfigueroa.dto;

/**
 *
 * @author Joaki
 */
public class CargoDTO {
    private static CargoDTO instance;

    public CargoDTO() {
    }
    
    public static CargoDTO getCargoDTO(){
        if(instance == null){
            instance = new CargoDTO();
        }
        return instance;
    }

    public static CargoDTO getInstance() {
        return instance;
    }

    public static void setInstance(CargoDTO instance) {
        CargoDTO.instance = instance;
    }
    
    
}
