package fes.aragon.modelo;

public class Productos {
	private Integer id_producto;
	private String nombreProducto;
	private String precioProducto;
	
	public Productos() {
		// TODO Auto-generated constructor stub
	}

	public Productos(int id_producto, String nombreProducto, String precioProducto) {
		this.id_producto = id_producto;
		this.nombreProducto = nombreProducto;
		this.precioProducto = precioProducto;
	}

	public Integer getId_producto() {
		return id_producto;
	}

	public void setId_producto(Integer id_producto) {
		this.id_producto = id_producto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getPrecioProducto() {
		return precioProducto;
	}

	public void setPrecioProducto(String precioProducto) {
		this.precioProducto = precioProducto;
	}
	
	 
}
