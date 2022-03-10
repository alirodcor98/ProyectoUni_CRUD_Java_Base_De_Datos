package fes.aragon.controlador;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import fes.aragon.modelo.Clientes;
import fes.aragon.modelo.Facturas;
import fes.aragon.mysql.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ModificarFacturaController implements Initializable{

    private static final URL URL = null;

	private static final ResourceBundle ResourceBundle = null;

	@FXML
    private TableColumn<Clientes, String> MFApellidoPaterno;

    @FXML
    private TableColumn<Clientes, Integer> MFID;

    @FXML
    private TableColumn<Clientes, String> MFNombreCliente;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private DatePicker pickDate;

    @FXML
    private TableView<Clientes> tablaMFClientes;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtBusqueda;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtReferencia;
    
    Clientes cliente = new Clientes();
    Facturas factura = new Facturas();

    @FXML
    void accionLimpiar(ActionEvent event) {
    	this.limpiar();
    }

    @FXML
    void buscarCliente(ActionEvent event) {

    }

    @FXML
    void guardarAccion(ActionEvent event) {
    	if(validar() && this.cliente.getId()!=null) {
    		Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			if (factura.getId() != null) {
				modificar(factura);
				alerta.setHeaderText("Factura almacenada");
				alerta.setContentText("Factura almacenada de manera correcta!");
				limpiar();
			} else {
				alerta.setHeaderText("Ha ocurrido un error");
				alerta.setContentText("Se ha presentado un error al guardar datos");
			}
			alerta.showAndWait();
    	}
    }
    public void modificarFactura(Facturas factura) {
    	this.factura=factura;
    	this.txtReferencia.setText(factura.getReferencia());
    	this.pickDate.setValue(factura.getFecha());
    	this.txtNombre.setText(factura.getCliente().getNombre());
    	this.txtApellido.setText(factura.getCliente().getApellidoPaterno());
    }
    
    
    private void limpiar() {
		this.txtReferencia.setText("");
		this.pickDate.getEditor().clear();
	}
	
	private void traerDatos() {
		try {
			Conexion cnn = new Conexion();
			this.tablaMFClientes.getItems().clear();
			this.tablaMFClientes.setItems(cnn.todosClientes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Alert alerta = new Alert(Alert.AlertType.WARNING);
			alerta.setTitle("Problema en B.D");
			alerta.setHeaderText("Error al presentar las facturas");
			alerta.setContentText("Consulta al fabricante, por favor");
			alerta.showAndWait();
			e.printStackTrace();
		}
	}
	
	public void modificar(Facturas factura) {
		try {
			
			Conexion cnn = new Conexion();
			factura.setReferencia(this.txtReferencia.getText());
			factura.setFecha(this.pickDate.getValue());

			cnn.modificarFacturas(factura);
		} catch (Exception e) {
			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			alerta.setHeaderText("Error al guardar los datos");
			alerta.setContentText("Revisa que los datos no esten vacios o sean validos");
			alerta.showAndWait();
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		// TODO Auto-generated method stub
				this.MFID.setCellValueFactory(new PropertyValueFactory<>("id"));
				this.MFNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombre"));
				this.MFApellidoPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
				
				this.tablaMFClientes.getSelectionModel().selectedItemProperty()
						.addListener((obj, oldSeleccion, newSeleccion) -> {
							if (newSeleccion != null) {
								
								cliente = tablaMFClientes.getSelectionModel().getSelectedItem();
								this.txtNombre.setText(cliente.getNombre());
								this.txtApellido.setText(cliente.getApellidoPaterno());
								factura.setCliente(cliente);
							} else {
								System.out.print("Saliendo!");
							}
						});

				this.traerDatos();
	}
		
	private boolean validar(){
		boolean validos = true;
		LocalDate date = this.pickDate.getValue();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date fecha = Date.from(date.atStartOfDay(defaultZoneId).toInstant());
		if (this.txtReferencia.getText().isEmpty() || this.txtReferencia.getText().regionMatches(0, " ", 0, 1)) {
			validos = false;
		}
		if (fecha == null) {
			validos = false;
		}
		return validos;
	}

}
