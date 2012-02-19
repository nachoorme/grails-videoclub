package modelodatos

public enum Categoria {

	NOVEDAD ("NOVEDAD"),
	NORMAL ("NORMAL"),
	OFERTA ("OFERTA")
	
	
	String tipo
	Categoria(String tipo) {
	   this.tipo = tipo
	   
	  }
}