/* AUTOR: Resp. Soporte, Resp. Desarrollo
FECHA: 17/02/2012
NOMBRE MODULO: Categoria.groovy
DESCRIPCI�N: Dominio Enumerado atributo Categor�a */

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