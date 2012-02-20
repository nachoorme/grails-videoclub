package modelodatos

import org.springframework.dao.DataIntegrityViolationException

class SoporteController {

	static allowedMethods = [save: "POST", update: "POST"]

	def index() {
		redirect(action: "list", params: params)
	}

	def list() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[soporteInstanceList: Soporte.list(params), soporteInstanceTotal: Soporte.count()]
	}

	def create() {
		[soporteInstance: new Soporte(params)]
	}

	def save() {
		def soporteInstance = new Soporte(params)
		if (!soporteInstance.save(flush: true)) {
			render(view: "darAltaSoporte", model: [soporteInstance: soporteInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [message(code: 'soporte.label', default: 'Soporte'), soporteInstance.id])
		redirect(action: "darAltaSoporte", id: soporteInstance.id)
	}

	def show() {
		def soporteInstance = Soporte.get(params.id)
		if (!soporteInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'soporte.label', default: 'Soporte'), params.id])
			redirect(action: "list")
			return
		}

		[soporteInstance: soporteInstance]
	}

	def edit() {
		def soporteInstance = Soporte.get(params.id)
		if (!soporteInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'soporte.label', default: 'Soporte'), params.id])
			redirect(action: "list")
			return
		}

		[soporteInstance: soporteInstance]
	}

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
	
	def darAltaSoporte() {
		[soporteInstance: new Soporte(params)]
	}
	
	def darBajaSoporte() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[soporteInstanceList: Soporte.list(params), soporteInstanceTotal: Soporte.count()]
	}

	def listarSoporte() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[soporteInstanceList: Soporte.list(params), soporteInstanceTotal: Soporte.count()]
	}

	def mostrarSoporte() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[soporteInstanceList: Soporte.list(params), soporteInstanceTotal: Soporte.count()]
	}

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
