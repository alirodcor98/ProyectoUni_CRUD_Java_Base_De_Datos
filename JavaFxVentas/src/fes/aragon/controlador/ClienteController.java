package fes.aragon.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fes.aragon.modelo.Clientes;
import fes.aragon.mysql.Conexion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class ClienteController implements Initializable {
	
	@FXML
	private TableView<Clientes> tblTablaCliente;

    @FXML
    private TableColumn<Clientes, String> clienteApellidoPaterno;

    @FXML
    private TableColumn<Clientes, Integer> clienteID;

    @FXML
    private TableColumn<Clientes, String> clienteNombre;

    @FXML
    private TableColumn<Clientes, String> comando;

    @FXML
    void nuevoCliente(MouseEvent event) {
    	try {
			Parent parent = FXMLLoader.load(getClass().getResource("/fes/aragon/vista/NuevoUsuario.fxml"));
			Scene escena = new Scene(parent);
			Stage escenario = new Stage();
			
			escenario.initModality(Modality.APPLICATION_MODAL);
			escenario.initOwner(tblTablaCliente.getScene().getWindow());
			escenario.setScene(escena);
			escenario.initStyle(StageStyle.UTILITY);
			escenario.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    @FXML
    void refrescar(MouseEvent event) {
    	this.traerDatos();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.clienteID.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.clienteNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		this.clienteApellidoPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
		
		Callback<TableColumn<Clientes, String>, TableCell<Clientes, String>>
		celda = (TableColumn<Clientes, String> parametros) -> {
			final TableCell<Clientes, String> cel = new TableCell<Clientes, String> () {

				@Override
				protected void updateItem(String arg0, boolean arg1) {
					// TODO Auto-generated method stub
					super.updateItem(arg0, arg1);
					if(arg1) {
						setGraphic(null);
						setText(null);
					} else {
						FontAwesomeIconView borrarIcono = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
						FontAwesomeIconView modificarIcono = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
						
						borrarIcono.setGlyphStyle("-fx-fill:#FF0077;-glyph-size:25px;-fx-cursor:hand;");
						modificarIcono.setGlyphStyle("-fx-fill:#FF0077;-glyph-size:25px;-fx-cursor:hand;");
						
						borrarIcono.setOnMouseClicked((MouseEvent evento)->{
							Clientes cliente = tblTablaCliente.getSelectionModel().getSelectedItem();
							
							borrar(cliente.getId());
						});
						modificarIcono.setOnMouseClicked((MouseEvent evento)->{
							Clientes cliente = tblTablaCliente.getSelectionModel().getSelectedItem();
							
							modificarCliente(cliente);
						});
						HBox hbox = new HBox(borrarIcono, modificarIcono);
						hbox.setStyle("-fx-alignment:center");
						HBox.setMargin(borrarIcono, new Insets(2, 2, 0, 3));
						HBox.setMargin(modificarIcono, new Insets(2, 3, 0, 2));
						setGraphic(hbox);
						setText(null);
					}
				}
				
			};
			return cel;
		};
		this.comando.setCellFactory(celda);
		this.traerDatos();
	}
	
	private void traerDatos() {
		try {
			Conexion cnn = new Conexion();
			this.tblTablaCliente.getItems().clear();
			this.tblTablaCliente.setItems(cnn.todosClientes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Alert alerta = new Alert(Alert.AlertType.WARNING);
			alerta.setTitle("Problema en B.D");
			alerta.setHeaderText("Error en la aplicacion");
			alerta.setContentText("Consulta al fabricante, por favor.");
			alerta.showAndWait();
			e.printStackTrace();
		}		
	}
	
	private void borrar(int id) {
		try {
			Conexion cnn = new Conexion();
			cnn.eliminarClientes(id);
			this.traerDatos();
		} catch (Exception e) {
			Alert alerta = new Alert(Alert.AlertType.WARNING);
			alerta.setTitle("Problema en B.D");
			alerta.setHeaderText("Error en la aplicacion");
			alerta.setContentText("Consulta al fabricante, por favor.");
			alerta.showAndWait();
			e.printStackTrace();
		}
	}
	
	private void modificarCliente(Clientes cliente) {
		try {
			FXMLLoader alta = new FXMLLoader(getClass().getResource("/fes/aragon/vista/NuevoUsuario.fxml"));
			Parent parent = (Parent)alta.load();
			((NuevoClienteController)alta.getController()).modificarCliente(cliente);
			Scene escena = new Scene(parent);
			Stage escenario = new Stage();
			
			escenario.initModality(Modality.APPLICATION_MODAL);
			escenario.initOwner(tblTablaCliente.getScene().getWindow());
			escenario.setScene(escena);
			escenario.initStyle(StageStyle.UTILITY);
			escenario.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
