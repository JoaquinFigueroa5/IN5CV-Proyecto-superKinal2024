/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joaquinfigueroa.report;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.joaquinfigueroa.dao.Conexion;
import win.zqxu.jrviewer.JRViewerFX;

/**
 *
 * @author informatica
 */
public class GenerarReporte {
    private static GenerarReporte instance;
    private static Connection conexion;
    
    private GenerarReporte(){
        
    }
    
    public static GenerarReporte getInstance(){
        if(instance == null){
            instance = new GenerarReporte();
        }
        
        return instance;
    }
    
    public void generarFactura(int factId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            Map<String, Object>parametros = new HashMap<>();
            parametros.put("factId", factId);
            InputStream jasperPath = GenerarReporte.class.getResourceAsStream("/org/joaquinfigueroa/report/Factura.jasper");
            JasperPrint reporte = JasperFillManager.fillReport(jasperPath, parametros, conexion);
            Stage reportStage = new Stage();
            JRViewerFX reportViewer = new JRViewerFX(reporte);
            Pane root = new Pane();
            root.getChildren().add(reportViewer);
            reportViewer.setPrefSize(1000, 800);
            Scene scene = new Scene(root);
            reportStage.setScene(scene);
            reportStage.setTitle("factura");
            reportStage.show();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void generarClientes(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            Map<String, Object>parametros = new HashMap<>();
            InputStream jasperPath = GenerarReporte.class.getResourceAsStream("/org/joaquinfigueroa/report/Clientes.jasper");
            JasperPrint reporte = JasperFillManager.fillReport(jasperPath, parametros, conexion);
            Stage reportStage = new Stage();
            JRViewerFX reportViewer = new JRViewerFX(reporte);
            Pane root = new Pane();
            root.getChildren().add(reportViewer);
            reportViewer.setPrefSize(1000, 800);
            Scene scene = new Scene(root);
            reportStage.setScene(scene);
            reportStage.setTitle("Cliente");
            reportStage.show();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void generarProductos(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            Map<String, Object>parametros = new HashMap<>();
            InputStream jasperPath = GenerarReporte.class.getResourceAsStream("/org/joaquinfigueroa/report/Productos.jasper");
            JasperPrint reporte = JasperFillManager.fillReport(jasperPath, parametros, conexion);
            Stage reportStage = new Stage();
            JRViewerFX reportViewer = new JRViewerFX(reporte);
            Pane root = new Pane();
            root.getChildren().add(reportViewer);
            reportViewer.setPrefSize(1000, 800);
            Scene scene = new Scene(root);
            reportStage.setScene(scene);
            reportStage.setTitle("Productos");
            reportStage.show();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
