package fes.aragon.controlador;

import fes.aragon.modelo.Productos;
import fes.aragon.mysql.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class NuevoProductoController {

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;
    
    private Productos producto = null;

    @FXML
    void accionLimpiar(ActionEvent event) {
    		this.limpiar();
    }

    @FXML
    void guardarAccion(ActionEvent event) {
    	if(producto == null) {
    		producto = new Productos();
    	}
    	
    	if(validar()) {
    		Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    		if(producto.getId_producto() == null) {
    			almacenar();
    			alerta.setHeaderText("Product almacenado");
    			alerta.setContentText("El producto se almaceno correctamente");
    			limpiar();
    		} else {
    			modificar();
    			alerta.setHeaderText("Producto modificado");
    			alerta.setContentText("El producto se modifico correctamente");
    		}        	
			alerta.showAndWait();
    	}    
    }
    
    private void almacenar() {
    	try {
			Conexion cnn = new Conexion();
			
			producto.setNombreProducto(txtNombre.getText());
			producto.setPrecioProducto(txtPrecio.getText());
			
			cnn.almacenarProductos(producto);
		} catch (Exception e) {
			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			alerta.setHeaderText("Error al guardar los datos");
			alerta.setContentText("Revisa que los datos no esten vacios o sean validos");
			alerta.showAndWait();
			e.printStackTrace();
		}
    }
    
    private void limpiar() {
    	this.txtNombre.setText("");
    	this.txtPrecio.setText("");
    }
    
    private boolean validar() {
    	boolean validos = true;
    	
    	if(this.txtNombre.getText().isEmpty() || this.txtNombre.getText().regionMatches(0, "", 0, 1)) {
    		validos = false;
    	}
    	if(this.txtPrecio.getText().isEmpty() || this.txtPrecio.getText().regionMatches(0, "", 0, 1)) {
    		validos = false;
    	}
    	return validos;
    }
    
    public void modificarProducto(Productos producto) {
    	this.producto = producto;
    	this.txtNombre.setText(producto.getNombreProducto());
    	this.txtPrecio.setText(producto.getPrecioProducto());
    }
    
    private void modificar() {
    	try {
			Conexion cnn = new Conexion();
			
			producto.setNombreProducto(txtNombre.getText());
			producto.setPrecioProducto(txtPrecio.getText());
			
			cnn.modificarProductos(producto);
		} catch (Exception e) {
			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			alerta.setHeaderText("Error al guardar los datos");
			alerta.setContentText("Revisa que los datos no esten vacios o sean validos");
			alerta.showAndWait();
			e.printStackTrace();
		}
    }
}
