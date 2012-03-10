import modelodatos.*;
import security.*;
class BootStrap {

    def init = { servletContext ->
		def distribuidor1
		def distribuidor2
		def pelicula1
		def pelicula2
		def pelicula3
		def pelicula4
		def pelicula5
		def pelicula6
		def pelicula7
		
		if(!Socio.count()){
			new Socio (dni: "53974233Z", nombre: "Angel", apellidos: "García Martínez",fechaRegistro:new Date(), direccion: "C/Almagro 25 4º I", poblacion: "Madrid", provincia: "Madrid", codigoPostal: "28010", telefono: "913426015", fechaNacimiento: ('Mon Jan 14 07:12:32 EST 1985'), profesion: "Banquero",codigoCuentaCorriente: "20385892863244600095").save(failOnError: true)
			new Socio (dni: "02037505A", nombre: "Tomás", apellidos: "Aguado Molina",fechaRegistro:new Date(), direccion: "C/Velázquez 7 1º D", poblacion: "Madrid", provincia: "Madrid", codigoPostal: "28001", telefono: "912750899", fechaNacimiento: ('Mon Jan 19 07:12:32 EST 1976'), profesion: "Dependiente",codigoCuentaCorriente: "20425097263255690023").save(failOnError: true)
			new Socio (dni: "X5235834D", nombre: "Isabelle", apellidos: "Poulard Lemoine",fechaRegistro:new Date(), direccion: "C/Antonio López 101 5º D", poblacion: "Madrid", provincia: "Madrid", codigoPostal: "28026", telefono: "917852288", fechaNacimiento: ('Mon Jan 15 07:12:32 EST 1968'), profesion: "Profesora",codigoCuentaCorriente: "30751094081255693500").save(failOnError: true)
		}
		
		if(!Distribuidor.count()){
			distribuidor1 = new Distribuidor (nombre: "Warner Brothers", cif: "30586220D", direccion: "C/Almagro 5", poblacion: "Madrid", provincia: "Madrid", distritoPostal: "28002", telefono: "913432823", fechaInicioContrato: new Date(), fechaFinContrato: new Date(), e_mail: "warner@warnerbrothers.com").save(failOnError: true)
			distribuidor2 = new Distribuidor (nombre: "Universal", cif: "43586255G", direccion: "C/Concha Espina 10", poblacion: "Madrid", provincia: "Madrid", distritoPostal: "28045", telefono: "917652863",  fechaInicioContrato: new Date(), fechaFinContrato: new Date(), e_mail: "universal@universalstudios.com").save(failOnError: true)
			
		}
		
		if(!Pelicula.count()){
			pelicula1 = new Pelicula (titulo: "Matrix", tipoCategoria: Categoria.OFERTA, calificacionMoral:"+15", tipoGenero: Genero.CienciaFiccion, duracion: 136, idioma: Idioma.Ingles, precioBase: 2 , distribuidor: distribuidor1).save(failOnError: true)
			pelicula2 = new Pelicula (titulo: "Los descendientes", tipoCategoria: Categoria.NOVEDAD, calificacionMoral:"+15", tipoGenero: Genero.Drama, duracion: 115, idioma: Idioma.Ingles, precioBase: 2 , distribuidor: distribuidor2).save(failOnError: true)
			pelicula3 = new Pelicula (titulo: "Avatar", tipoCategoria: Categoria.NORMAL, calificacionMoral:"+13", tipoGenero: Genero.CienciaFiccion, duracion: 162, idioma: Idioma.Ingles, precioBase: 2 , distribuidor: distribuidor2).save(failOnError: true)
			pelicula4 = new Pelicula (titulo: "El padrino", tipoCategoria: Categoria.OFERTA, calificacionMoral:"+13", tipoGenero: Genero.Crime, duracion: 175, idioma: Idioma.Ingles, precioBase: 2 , distribuidor: distribuidor2).save(failOnError: true)
			pelicula5 = new Pelicula (titulo: "La vida es bella", tipoCategoria: Categoria.OFERTA, calificacionMoral:"+13", tipoGenero: Genero.Comedia, duracion: 116, idioma: Idioma.Italiano, precioBase: 2 , distribuidor: distribuidor2).save(failOnError: true)
			pelicula6 = new Pelicula (titulo: "Transformers", tipoCategoria: Categoria.NORMAL, calificacionMoral:"+13", tipoGenero: Genero.CienciaFiccion, duracion: 144, idioma: Idioma.Ingles, precioBase: 2 , distribuidor: distribuidor2).save(failOnError: true)
			pelicula7 = new Pelicula (titulo: "La piel que habito", tipoCategoria: Categoria.NOVEDAD, calificacionMoral:"+13", tipoGenero: Genero.Thriller, duracion: 117, idioma: Idioma.Espagnol, precioBase: 2 , distribuidor: distribuidor2).save(failOnError: true)
		}
		
		if(!Soporte.count()){
			new Soporte (pelicula: pelicula1, tipoSoporte: TipoSoporte.DVD).save(failOnError: true)
			new Soporte (pelicula: pelicula1, tipoSoporte: TipoSoporte.CINTA).save(failOnError: true)
			
		}
		
		if (!Role.count()){
			def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true) 
			def userRole = new Role(authority: 'ROLE_USER').save(flush: true)
			
			def testUser = new User(username: 'admin', enabled: true, password: 'admin') 
			testUser.save(flush: true)
			
			UserRole.create testUser, adminRole, true
			
			assert User.count() == 1 
			assert Role.count() == 2 
			assert UserRole.count() == 1
		}
    }
    def destroy = {
    }
}
