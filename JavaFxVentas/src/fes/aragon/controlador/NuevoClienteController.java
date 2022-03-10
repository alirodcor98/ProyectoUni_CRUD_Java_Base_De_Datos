package fes.aragon.controlador;

import fes.aragon.modelo.Clientes;
import fes.aragon.mysql.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class NuevoClienteController {

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtNombre;
    
    private Clientes cliente = null;

    @FXML
    void accionLimpiar(ActionEvent event) {
    	this.limpiar();
    }

    @FXML
    void guardarAccion(ActionEvent event) {
    	if(cliente == null) {
    		cliente = new Clientes();
    	}
    	
    	if(validar()) {
    		Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    		if(cliente.getId() == null) {
    			almacenar();
    			alerta.setHeaderText("Cliente almacenado");
    			alerta.setContentText("El cliente se almaceno correctamente");
    			limpiar();
    		} else {
    			modificar();
    			alerta.setHeaderText("Cliente modificado");
    			alerta.setContentText("El cliente se modifico correctamente");
    		}        	
			alerta.showAndWait();
    	}    	
    }
    
    private void almacenar() {
    	try {
			Conexion cnn = new Conexion();
			
			cliente.setNombre(txtNombre.getText());
			cliente.setApellidoPaterno(txtApellido.getText());
			
			cnn.almacenarClientes(cliente);
		} catch (Exception e) {
			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			alerta.setHeaderText("Error al guardar los d	atos");
			alerta.setContentText("Revisa que los datos no esten vacios o sean validos");
			alerta.showAndWait();
			e.printStackTrace();
		}
    }
    
    private void limpiar() {
    	this.txtNombre.setText("");
    	this.txtApellido.setText("");
    }
    
    private boolean validar() {
    	boolean validos = true;
    	
    	if(this.txtNombre.getText().isEmpty() || this.txtNombre.getText().regionMatches(0, "", 0, 1)) {
    		validos = false;
    	}
    	if(this.txtApellido.getText().isEmpty() || this.txtApellido.getText().regionMatches(0, "", 0, 1)) {
    		validos = false;
    	}
    	return validos;
    }
    
    public void modificarCliente(Clientes cliente) {
    	this.cliente = cliente;
    	this.txtNombre.setText(cliente.getNombre());
    	this.txtApellido.setText(cliente.getApellidoPaterno());
    }
    
    private void modificar() {
    	try {
			Conexion cnn = new Conexion();
			
			cliente.setNombre(txtNombre.getText());
			cliente.setApellidoPaterno(txtApellido.getText());
			
			cnn.modificarClientes(cliente);
		} catch (Exception e) {
			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			alerta.setHeaderText("Error al guardar los datos");
			alerta.setContentText("Revisa que los datos no esten vacios o sean validos");
			alerta.showAndWait();
			e.printStackTrace();
		}
    }
}