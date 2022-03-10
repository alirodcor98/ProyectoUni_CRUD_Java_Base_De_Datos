package fes.aragon.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fes.aragon.modelo.Productos;
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

public class ProductoController implements Initializable{

    @FXML
    private TableColumn<Productos, String> comandoProducto;

    @FXML
    private TableColumn<Productos, Integer> productoID;

    @FXML
    private TableColumn<Productos, String> productoNombre;

    @FXML
    private TableColumn<Productos, Integer> productoPrecio;

    @FXML
    private TableView<Productos> tblTablaProducto;

    @FXML
    void nuevoProducto(MouseEvent event) {
    	try {
			Parent parent = FXMLLoader.load(getClass().getResource("/fes/aragon/vista/NuevoProducto.fxml"));
			Scene escena = new Scene(parent);
			Stage escenario = new Stage();
			
			escenario.initModality(Modality.APPLICATION_MODAL);
			escenario.initOwner(tblTablaProducto.getScene().getWindow());
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
		this.productoID.setCellValueFactory(new PropertyValueFactory<>("id_producto"));
		this.productoNombre.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
		this.productoPrecio.setCellValueFactory(new PropertyValueFactory<>("precioProducto"));
		
		Callback<TableColumn<Productos, String>, TableCell<Productos, String>>
		celda = (TableColumn<Productos, String> parametros) -> {
			final TableCell<Productos, String> cel = new TableCell<Productos, String> () {

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
							Productos producto = tblTablaProducto.getSelectionModel().getSelectedItem();
							
							borrar(producto.getId_producto());
						});
						modificarIcono.setOnMouseClicked((MouseEvent evento)->{
							Productos producto = tblTablaProducto.getSelectionModel().getSelectedItem();
							
							modificarProducto(producto);
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
		this.comandoProducto.setCellFactory(celda);
		this.traerDatos();
		
	}
	
	private void traerDatos() {
		try {
			Conexion cnn = new Conexion();
			this.tblTablaProducto.getItems().clear();
			this.tblTablaProducto.setItems(cnn.todosProductos());
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
			cnn.eliminarProductos(id);
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
	
	private void modificarProducto(Productos producto) {
		try {
			FXMLLoader alta = new FXMLLoader(getClass().getResource("/fes/aragon/vista/NuevoProducto.fxml"));
			Parent parent = (Parent)alta.load();
			((NuevoProductoController)alta.getController()).modificarProducto(producto);
			Scene escena = new Scene(parent);
			Stage escenario = new Stage();
			
			escenario.initModality(Modality.APPLICATION_MODAL);
			escenario.initOwner(tblTablaProducto.getScene().getWindow());
			escenario.setScene(escena);
			escenario.initStyle(StageStyle.UTILITY);
			escenario.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
