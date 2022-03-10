package fes.aragon.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class PrincipalController {
	@FXML
	private BorderPane IdPrincipal;

    @FXML
    void accionCliente(ActionEvent event) {
    	Contenido contenido = new Contenido("/fes/aragon/vista/Cliente.fxml");
    	IdPrincipal.setCenter(contenido);
    }

    @FXML
    void accionFactProd(ActionEvent event) {
    	Contenido contenido = new Contenido("/fes/aragon/vista/FactProd.fxml");
    	IdPrincipal.setCenter(contenido);
    }

    @FXML
    void accionFactura(ActionEvent event) {
    	Contenido contenido = new Contenido("/fes/aragon/vista/Factura.fxml");
    	IdPrincipal.setCenter(contenido);
    }

    @FXML
    void accionProducto(ActionEvent event) {
    	Contenido contenido = new Contenido("/fes/aragon/vista/Producto.fxml");
    	IdPrincipal.setCenter(contenido);
    }

}
