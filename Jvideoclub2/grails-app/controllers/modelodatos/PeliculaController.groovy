/* AUTOR: Resp. Soporte, Resp. Desarrollo
FECHA: 17/02/2012
NOMBRE MODULO: Pelicula Controller.groovy
DESCRIPCI�N: Controlador Datos Pel�cula */

package modelodatos

import org.springframework.dao.DataIntegrityViolationException

class PeliculaController {

	static allowedMethods = [save: "POST", update: "POST"]

	
	/* NOMBRE FUNCI�N: index
	DESCRIPCI�N: Funci�n implementa la acci�n mostrada en el �ndice. */
	def index() {
		redirect(action: "list", params: params)
	}

	/* NOMBRE FUNCI�N: list
	DESCRIPCI�N: Funci�n lista pel�culas (b�sica). */
	def list() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[peliculaInstanceList: Pelicula.list(params), peliculaInstanceTotal: Pelicula.count()]
	}

	/* NOMBRE FUNCI�N: create
	DESCRIPCI�N: Funci�n crear pel�culas (b�sica). */
	def create() {
		[peliculaInstance: new Pelicula(params)]
	}

	/* NOMBRE FUNCI�N: save
	DESCRIPCI�N: Funci�n guarda datos pel�culas (b�sica). */
	def save() {
		def peliculaInstance = new Pelicula(params)
		if (!peliculaInstance.save(flush: true)) {
			render(view: "altaPelicula", model: [peliculaInstance: peliculaInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [message(code: 'pelicula.label', default: 'Pelicula'), peliculaInstance.id])
		redirect(action: "mostrar", id: peliculaInstance.id)
	}

	/* NOMBRE FUNCI�N: show
	DESCRIPCI�N: Funci�n mostrar (b�sica). */
	def show() {
		def peliculaInstance = Pelicula.get(params.id)
		if (!peliculaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'pelicula.label', default: 'Pelicula'), params.id])
			redirect(action: "list")
			return
		}

		[peliculaInstance: peliculaInstance]
	}

	/* NOMBRE FUNCI�N: edit
	DESCRIPCI�N: Funci�n editar pel�culas (b�sica). */
	def edit() {
		def peliculaInstance = Pelicula.get(params.id)
		if (!peliculaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'pelicula.label', default: 'Pelicula'), params.id])
			redirect(action: "modificarPelicula")
			return
		}

		[peliculaInstance: peliculaInstance]
	}

	/* NOMBRE FUNCI�N: update
	DESCRIPCI�N: Funci�n actualizar datos pel�culas (b�sica). */
	def update() {
		def peliculaInstance = Pelicula.get(params.id)
		if (!peliculaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'pelicula.label', default: 'Pelicula'), params.id])
			redirect(action: "modificarPelicula")
			return
		}

		if (params.version) {
			def version = params.version.toLong()
			if (peliculaInstance.version > version) {
				peliculaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						  [message(code: 'pelicula.label', default: 'Pelicula')] as Object[],
						  "Another user has updated this Pelicula while you were editing")
				render(view: "edit", model: [peliculaInstance: peliculaInstance])
				return
			}
		}

		peliculaInstance.properties = params

		if (!peliculaInstance.save(flush: true)) {
			render(view: "edit", model: [peliculaInstance: peliculaInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'pelicula.label', default: 'Pelicula'), peliculaInstance.id])
		redirect(action: "mostrar", id: peliculaInstance.id)
	}

	/* NOMBRE FUNCI�N: delete
	DESCRIPCI�N: Funci�n eliminar pel�culas (b�sica). */
	def delete() {
		def peliculaInstance = Pelicula.get(params.id)
		if (!peliculaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'pelicula.label', default: 'Pelicula'), params.id])
			redirect(action: "darBajaPelicula")
			return
		}

		try {
			peliculaInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'pelicula.label', default: 'Pelicula'), params.id])
			redirect(action: "darBajaPelicula")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'pelicula.label', default: 'Pelicula'), params.id])
			redirect(action: "mostrar", id: params.id)
		}
	}
	
	def filtraPeliService

	/* NOMBRE FUNCI�N: altaPelicula
	DESCRIPCI�N: Funci�n dar Alta pel�culas, recibe datos y crea. */
	def altaPelicula() {
		[peliculaInstance: new Pelicula(params)]
	}
	
	/* NOMBRE FUNCI�N: darBajaPelicula
	DESCRIPCI�N: Funci�n que muestra el listado de pel�culas para poder eliminarlos. */
	def darBajaPelicula() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[peliculaInstanceList: Pelicula.list(params), peliculaInstanceTotal: Pelicula.count()]
	}
	
	/* NOMBRE FUNCI�N: listarPelicula
	DESCRIPCI�N: Funci�n que muestra el listado completo de pel�culas. */
	def listarPelicula() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[peliculaInstanceList: Pelicula.list(params), peliculaInstanceTotal: Pelicula.count()]
	}
	
	/* NOMBRE FUNCI�N: mostrarPelicula
	DESCRIPCI�N: Funci�n que muestra el listado de pel�culas para poder mostrar los datos de uno concreto. */
	def mostrarPelicula() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[peliculaInstanceList: Pelicula.list(params), peliculaInstanceTotal: Pelicula.count()]
	}
	
	/* NOMBRE FUNCI�N: mostrar
	DESCRIPCI�N: Funci�n que muestra los datos de un pel�cula concreto. */
	def mostrar() {
		def peliculaInstance = Pelicula.get(params.id)
		if (!peliculaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'pelicula.label', default: 'Pelicula'), params.id])
			redirect(action: "mostrarPelicula")
			return
		}

		[peliculaInstance: peliculaInstance]
	}
	
	/* NOMBRE FUNCI�N: modificarPelicula
	DESCRIPCI�N: Funci�n que muestra el listado de pel�culas para poder modificarlos. */
	def modificarPelicula () {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[peliculaInstanceList: Pelicula.list(params), peliculaInstanceTotal: Pelicula.count()]
	}
	
	/* NOMBRE FUNCI�N: crearListaPeliDis
	DESCRIPCI�N: Funci�n que muestra el listado de pel�culas suministradas por una distribuidora concreta. */
	def crearListaPeliDis () {
		println params
		if (params.distribuidor!=null){
		def distribuidorpelis = filtraPeliService.filtraPeliDis(params.distribuidor.id)
		render(view: "listarPelicula", model: [peliculaInstanceList: distribuidorpelis, peliculaInstanceTotal: distribuidorpelis.size()])
		
		}
	}
}
