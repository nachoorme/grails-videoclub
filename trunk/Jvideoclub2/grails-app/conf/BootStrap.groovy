import modelodatos.*;

class BootStrap {

    def init = { servletContext ->
		
		if(!Socio.count()){
			new Socio (dni: "53974233Z", nombre: "Billy", apellidos: "Gates",fechaRegistro:new Date(), direccion: "C/Gran Vía 25 2º D", poblacion: "Madrid", provincia: "Madrid", codigoPostal: "28004", telefono: "915557015", fechaNacimiento: new Date(), profesion: "Millonario",codigoCuentaCorriente: "20385892863244600095").save(failOnError: true)
			new Socio (dni: "03563238H", nombre: "Mickey", apellidos: "Mouse",fechaRegistro:new Date(), direccion: "DisneyLand", poblacion: "Paris", provincia: "Paris", codigoPostal: "28004", telefono: "915792202", fechaNacimiento: new Date(), profesion: "Dibujo Animado",codigoCuentaCorriente: "20383552863244600022").save(failOnError: true)
		}
		
		
		
    }
    def destroy = {
    }
}
