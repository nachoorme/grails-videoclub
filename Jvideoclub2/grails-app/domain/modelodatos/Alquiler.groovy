package modelodatos

import java.util.Date;
import groovy.time.*;

class Alquiler {
	static constraints = {
		
		fechaAlquiler blank:false
		fechaEntrega nullable:true
	}
	
	static reportable = [:]
	static belongsTo = [socio:Socio,soporte:Soporte]
	
	Date fechaAlquiler
	Date fechaEntrega
	
	static transients =  ['fechaDevolucion']
	
	Date getFechaDevolucion (){
		use ( TimeCategory ) {
		Date resultado = fechaAlquiler + 3.days
		return resultado;
	   } 
	}
	

}
