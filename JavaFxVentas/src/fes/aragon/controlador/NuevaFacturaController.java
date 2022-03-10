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

public class NuevaFacturaController implements Initializable {

	@FXML
	private Button btnBuscar;

	@FXML
	private Button btnGuardar;

	@FXML
	private Button btnLimpiar;

	@FXML
	private TableColumn<Clientes, String> nuevaFacturaApellidoCliente;

	@FXML
	private TableColumn<Clientes, Integer> nuevaFacturaIDCliente;

	@FXML
	private TableColumn<Clientes, String> nuevaFacturaNombreCliente;

	@FXML
	private DatePicker pickDate;

	@FXML
	private TableView<Clientes> tablaNuevaFactura;

	@FXML
	private TextField txtBusquedaCliente;

	@FXML
	private TextField txtReferencia;

	private Facturas factura = new Facturas();
	private Clientes cliente = new Clientes();

	@FXML
	void accionLimpiar(ActionEvent event) {
		this.limpiar();
	}

	@FXML
	void buscarCliente(ActionEvent event) {
		this.traerDatos();
	}

	@FXML
	void guardarAccion(ActionEvent event) {
		
		if (validar() && factura != null) {
			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			if (factura.getId() == null) {
				almacenar();
				alerta.setHeaderText("Factura almacenada");
				alerta.setContentText("La factura se almaceno correctamente");
				limpiar();
			} else {
				alerta.setHeaderText("Ha ocurrido un error");
				alerta.setContentText("Se ha presentado un error al guardar datos");
			}
			alerta.showAndWait();
		}
	}

	private void almacenar() {
		try {
			//cambios
			Conexion cnn = new Conexion();
			factura.setReferencia(this.txtReferencia.getText());

			factura.setFecha(this.pickDate.getValue());

			cnn.almacenarFacturas(factura);

		} catch (Exception e) {
			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			alerta.setHeaderText("Error al guardar los datos");
			alerta.setContentText("Revisa que los datos no esten vacios o sean validos");
			alerta.showAndWait();
			e.printStackTrace();
		}
	}

	private void limpiar() {
		this.txtReferencia.setText("");
		this.pickDate.getEditor().clear();
	}

	private boolean validar() {
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.nuevaFacturaIDCliente.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.nuevaFacturaNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		this.nuevaFacturaApellidoCliente.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
		//cambios
		this.tablaNuevaFactura.getSelectionModel().selectedItemProperty()
				.addListener((obj, oldSeleccion, newSeleccion) -> {
					if (newSeleccion != null) {
						cliente = tablaNuevaFactura.getSelectionModel().getSelectedItem();
						factura.setCliente(cliente);

					} else {
						System.out.print("Saliendo!");
					}
				});

		this.traerDatos();
	}

	private void traerDatos() {
		try {
			Conexion cnn = new Conexion();
			this.tablaNuevaFactura.getItems().clear();
			this.tablaNuevaFactura.setItems(cnn.todosClientes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Alert alerta = new Alert(Alert.AlertType.WARNING);
			alerta.setTitle("Problema en B.D");
			alerta.setHeaderText("Error al traer las facturas");
			alerta.setContentText("Consulta al fabricante, por favor. (FACTURAS)");
			alerta.showAndWait();
			e.printStackTrace();
		}
	}

}
