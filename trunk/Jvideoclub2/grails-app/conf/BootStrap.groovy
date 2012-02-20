import modelodatos.*;

class BootStrap {

    def init = { servletContext ->
		def distribuidor1
		def distribuidor2
		def pelicula1
		
		if(!Socio.count()){
			new Socio (dni: "53974233Z", nombre: "Bill", apellidos: "Gates",fechaRegistro:new Date(), direccion: "C/Gran Vía 25 2º D", poblacion: "Madrid", provincia: "Madrid", codigoPostal: "28004", telefono: "915557015", fechaNacimiento: new Date(), profesion: "Millonario",codigoCuentaCorriente: "20385892863244600095").save(failOnError: true)
			new Socio (dni: "03563238H", nombre: "Mickey", apellidos: "Mouse",fechaRegistro:new Date(), direccion: "DisneyLand", poblacion: "Paris", provincia: "Paris", codigoPostal: "28004", telefono: "915792202", fechaNacimiento: new Date(), profesion: "Dibujo Animado",codigoCuentaCorriente: "20383552863244600022").save(failOnError: true)
		}
		
		if(!Distribuidor.count()){
			distribuidor1 = new Distribuidor (nombre: "Warner Brothers", cif: "30586220D", direccion: "C/Almagro 5", poblacion: "Madrid", provincia: "Madrid", distritoPostal: "28002", telefono: "913432823", fechaInicioContrato: new Date(), fechaFinContrato: new Date(), e_mail: "warner@warnerbrothers.com").save(failOnError: true)
			distribuidor2 = new Distribuidor (nombre: "Universal", cif: "43586255G", direccion: "C/Concha Espina 10", poblacion: "Madrid", provincia: "Madrid", distritoPostal: "28045", telefono: "917652863",  fechaInicioContrato: new Date(), fechaFinContrato: new Date(), e_mail: "universal@universalstudios.com").save(failOnError: true)
			
		}
		
		if(!Pelicula.count()){
			pelicula1 = new Pelicula (titulo: "Matrix", tipoCategoria: Categoria.OFERTA, tipoGenero: Genero.CienciaFiccion, calificacionMoral:"+18", idioma: Idioma.Ingles, precioBase: 2, distribuidor: distribuidor1).save(failOnError: true)
		}
		
		if(!Soporte.count()){
			new Soporte (pelicula: pelicula1, tipoSoporte: TipoSoporte.DVD).save(failOnError: true)
			new Soporte (pelicula: pelicula1, tipoSoporte: TipoSoporte.CINTA).save(failOnError: true)
			
		}
    }
    def destroy = {
    }
}
