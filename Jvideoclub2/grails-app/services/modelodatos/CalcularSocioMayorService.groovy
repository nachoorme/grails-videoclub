/* AUTOR: L�der equipo, Resp. Desarrollo
FECHA: 10/02/2012
NOMBRE MODULO: CalcularSocioMayorService.groovy
DESCRIPCI�N: Servicio Socio que calcula si la fecha introducida s mayor de 18 a�os */

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
