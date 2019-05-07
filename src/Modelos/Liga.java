package Modelos;

public class Liga {
private Integer id;
private String nombre;

public Liga(String nombre) {
	
	this.nombre = nombre;
	
}
public Liga() {
	
	this.nombre = "";
	
}
@Override
public String toString() {
	return "Liga [id=" + id + ", nombre=" + nombre +"]";
}

public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}

}
