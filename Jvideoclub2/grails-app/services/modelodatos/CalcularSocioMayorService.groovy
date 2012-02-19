package modelodatos
import groovy.time.*


class CalcularSocioMayorService {
	
	def getmayorEdad (Date fechaNacimiento){
		use ( TimeCategory ) {
			def fechaResta = new Date ()
			fechaResta = fechaResta - 18.years
			return fechaResta >= fechaNacimiento
		}
	}
}
