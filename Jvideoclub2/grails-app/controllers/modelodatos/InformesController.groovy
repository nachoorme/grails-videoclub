/* AUTOR: Resp. Soporte, Resp. Desarrollo
FECHA: 19/04/2012
NOMBRE MODULO:InformesController.groovy
DESCRIPCI�N: Controlador Datos Informes */

package modelodatos

class InformesController {

	/* NOMBRE FUNCI�N: listaDistribuidor
	DESCRIPCI�N: Devuelve la lista de distribuidoras disponibles. */
    def listaDistribuidor() { 
		
		[listaDistribuidor:Distribuidor.list()]
	}
	
	
}
