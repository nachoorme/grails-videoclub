/* AUTOR: Resp. Soporte, Resp. Desarrollo
FECHA: 17/02/2012
NOMBRE MODULO: SoporteController.groovy
DESCRIPCI�N: Controlador Datos Soporte */

package modelodatos

import org.springframework.dao.DataIntegrityViolationException

class SoporteController {

	static allowedMethods = [save: "POST", update: "POST"]

	/* NOMBRE FUNCI�N: index
	DESCRIPCI�N: Funci�n implementa la acci�n mostrada en el �ndice. */
	def index() {
		redirect(action: "list", params: params)
	}

	/* NOMBRE FUNCI�N: list
	DESCRIPCI�N: Funci�n lista soportes (b�sica). */
	def list() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[soporteInstanceList: Soporte.list(params), soporteInstanceTotal: Soporte.count()]
	}

	/* NOMBRE FUNCI�N: create
	DESCRIPCI�N: Funci�n crear soportes (b�sica). */
	def create() {
		[soporteInstance: new Soporte(params)]
	}

	/* NOMBRE FUNCI�N: save
	DESCRIPCI�N: Funci�n guarda datos soportes (b�sica). */
	def save() {
		def soporteInstance = new Soporte(params)
		if (!soporteInstance.save(flush: true)) {
			render(view: "darAltaSoporte", model: [soporteInstance: soporteInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [message(code: 'soporte.label', default: 'Soporte'), soporteInstance.id])
		redirect(action: "darAltaSoporte", id: soporteInstance.id)
	}

	/* NOMBRE FUNCI�N: show
	DESCRIPCI�N: Funci�n mostrar (b�sica). */
	def show() {
		def soporteInstance = Soporte.get(params.id)
		if (!soporteInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'soporte.label', default: 'Soporte'), params.id])
			redirect(action: "list")
			return
		}

		[soporteInstance: soporteInstance]
	}

	/* NOMBRE FUNCI�N: edit
	DESCRIPCI�N: Funci�n editar soportes (b�sica). */
	def edit() {
		def soporteInstance = Soporte.get(params.id)
		if (!soporteInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'soporte.label', default: 'Soporte'), params.id])
			redirect(action: "list")
			return
		}

		[soporteInstance: soporteInstance]
	}

	/* NOMBRE FUNCI�N: update
	DESCRIPCI�N: Funci�n actualizar datos soportes (b�sica). */
	def update() {
		def soporteInstance = Soporte.get(params.id)
		if (!soporteInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'soporte.label', default: 'Soporte'), params.id])
			redirect(action: "list")
			return
		}

		if (params.version) {
			def version = params.version.toLong()
			if (soporteInstance.version > version) {
				soporteInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						  [message(code: 'soporte.label', default: 'Soporte')] as Object[],
						  "Another user has updated this Soporte while you were editing")
				render(view: "edit", model: [soporteInstance: soporteInstance])
				return
			}
		}

		soporteInstance.properties = params

		if (!soporteInstance.save(flush: true)) {
			render(view: "edit", model: [soporteInstance: soporteInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'soporte.label', default: 'Soporte'), soporteInstance.id])
		redirect(action: "mostrar", id: soporteInstance.id)
	}

	/* NOMBRE FUNCI�N: delete
	DESCRIPCI�N: Funci�n eliminar soportes (b�sica). */
	def delete() {
		def soporteInstance = Soporte.get(params.id)
		if (!soporteInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'soporte.label', default: 'Soporte'), params.id])
			redirect(action: "darBajaSoporte")
			return
		}

		try {
			soporteInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'soporte.label', default: 'Soporte'), params.id])
			redirect(action: "darBajaSoporte")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'soporte.label', default: 'Soporte'), params.id])
			redirect(action: "darBajaSoporte", id: params.id)
		}
	}
	
	/* NOMBRE FUNCI�N: darAltaSoporte
	DESCRIPCI�N: Funci�n dar Alta soportes, recibe datos y crea. */
	def darAltaSoporte() {
		[soporteInstance: new Soporte(params)]
	}
	
	/* NOMBRE FUNCI�N: darBajaSoporte
	DESCRIPCI�N: Funci�n que muestra el listado de soportes para poder eliminarlos. */
	def darBajaSoporte() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[soporteInstanceList: Soporte.list(params), soporteInstanceTotal: Soporte.count()]
	}

	/* NOMBRE FUNCI�N: listarSoporte
	DESCRIPCI�N: Funci�n que muestra el listado completo de soportes. */
	def listarSoporte() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[soporteInstanceList: Soporte.list(params), soporteInstanceTotal: Soporte.count()]
	}

	/* NOMBRE FUNCI�N: mostrarSoporte
	DESCRIPCI�N: Funci�n que muestra el listado de soportes para poder mostrar los datos de uno concreto. */
	def mostrarSoporte() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[soporteInstanceList: Soporte.list(params), soporteInstanceTotal: Soporte.count()]
	}

	/* NOMBRE FUNCI�N: mostrar
	DESCRIPCI�N: Funci�n que muestra los datos de un soporte concreto. */
	def mostrar() {
		def soporteInstance = Soporte.get(params.id)
		if (!soporteInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'soporte.label', default: 'Soporte'), params.id])
			redirect(action: "list")
			return
		}

		[soporteInstance: soporteInstance]
	}
}
