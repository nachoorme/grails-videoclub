package modelodatos

import org.springframework.dao.DataIntegrityViolationException

class SocioController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [socioInstanceList: Socio.list(params), socioInstanceTotal: Socio.count()]
    }

    def create() {
        [socioInstance: new Socio(params)]
    }

    def save() {
        def socioInstance = new Socio(params)
        if (!socioInstance.save(flush: true)) {
            render(view: "create", model: [socioInstance: socioInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'socio.label', default: 'Socio'), socioInstance.id])
        redirect(action: "show", id: socioInstance.id)
    }

    def show() {
        def socioInstance = Socio.get(params.id)
        if (!socioInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'socio.label', default: 'Socio'), params.id])
            redirect(action: "list")
            return
        }

        [socioInstance: socioInstance]
    }

    def edit() {
        def socioInstance = Socio.get(params.id)
        if (!socioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'socio.label', default: 'Socio'), params.id])
            redirect(action: "list")
            return
        }

        [socioInstance: socioInstance]
    }

    def update() {
        def socioInstance = Socio.get(params.id)
        if (!socioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'socio.label', default: 'Socio'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (socioInstance.version > version) {
                socioInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'socio.label', default: 'Socio')] as Object[],
                          "Another user has updated this Socio while you were editing")
                render(view: "edit", model: [socioInstance: socioInstance])
                return
            }
        }

        socioInstance.properties = params

        if (!socioInstance.save(flush: true)) {
            render(view: "edit", model: [socioInstance: socioInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'socio.label', default: 'Socio'), socioInstance.id])
        redirect(action: "show", id: socioInstance.id)
    }

    def delete() {
        def socioInstance = Socio.get(params.id)
        if (!socioInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'socio.label', default: 'Socio'), params.id])
            redirect(action: "list")
            return
        }

        try {
            socioInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'socio.label', default: 'Socio'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'socio.label', default: 'Socio'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
	
	def buscaSocioService;
	def calcularSocioMayorService

	
	def crearAltaSocio = {
		if (params.fechaNacimiento!=null){
			def edadResult=true
			edadResult=calcularSocioMayorService.getmayorEdad(params.fechaNacimiento)
			if (edadResult) {
				redirect (action:"altaSocioMayor")
			}
			else {
				redirect (action:"altaSocioMenor")
			}
		}
	}
	
	def altaSocioMayor = {
		[socioInstance: new Socio(params)]
	}
	
	def saveSocioMayor = {
		def socioInstance = new Socio(params)
		if (!socioInstance.save(flush: true)) {
			render(view: "altaSocioMayor", model: [socioInstance: socioInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [message(code: 'socio.label', default: 'Socio'), socioInstance.id])
		redirect(action: "mostrar", id: socioInstance.id)
	}
	
	def saveSocioMenor = {
		def socioInstance = new Socio(params)
		if (!socioInstance.save(flush: true)) {
			render(view: "altaSocioMenor", model: [socioInstance: socioInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [message(code: 'socio.label', default: 'Socio'), socioInstance.id])
		redirect(action: "mostrar", id: socioInstance.id)
	}
	
	def altaSocioMenor = {
		[socioInstance: new Socio(params)]
	}
	
	def listarSocios = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[socioInstanceList: Socio.list(params), socioInstanceTotal: Socio.count()]
	}
	
	def darBajaSocio = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[socioInstanceList: Socio.list(params), socioInstanceTotal: Socio.count()]
	}
	
	def mostrar = {
		def socioInstance = Socio.get(params.id)
		if (!socioInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'socio.label', default: 'Socio'), params.id])
			redirect(action: "mostrarSocio")
			return
		}
		[socioInstance: socioInstance]
	}
	
	def mostrarSocio = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[socioInstanceList: Socio.list(params), socioInstanceTotal: Socio.count()]
	}
	
	def actualizarSocio = {
		def socioInstance = Socio.get(params.id)
		if (!socioInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'socio.label', default: 'Socio'), params.id])
			redirect(action: "listarSocios")
			return
		}

		if (params.version) {
			def version = params.version.toLong()
			if (socioInstance.version > version) {
				socioInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						  [message(code: 'socio.label', default: 'Socio')] as Object[],
						  "Another user has updated this Socio while you were editing")
				render(view: "modificar", model: [socioInstance: socioInstance])
				return
			}
		}

		socioInstance.properties = params

		if (!socioInstance.save(flush: true)) {
			render(view: "modificar", model: [socioInstance: socioInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'socio.label', default: 'Socio'), socioInstance.id])
		redirect(action: "modificarSocio", id: socioInstance.id)
	}
	
	def modificar = {
		def socioInstance = Socio.get(params.id)
		if (!socioInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'socio.label', default: 'Socio'), params.id])
			redirect(action: "list")
			return
		}

		[socioInstance: socioInstance]
	}
	
	def modificarSocio = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[socioInstanceList: Socio.list(params), socioInstanceTotal: Socio.count()]
	}
	
	def imprimir = {
		def socioInstance = Socio.get(params.id)
		if (!socioInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'socio.label', default: 'Socio'), params.id])
			redirect(action: "imprimirCarnet")
			return
		}
		[socioInstance: socioInstance]
	}
	
	def imprimirCarnet = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[socioInstanceList: Socio.list(params), socioInstanceTotal: Socio.count()]
	}
	
}
