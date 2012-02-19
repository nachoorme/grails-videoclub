package modelodatos

import org.springframework.dao.DataIntegrityViolationException

class PeliculaController {

    static allowedMethods = [save: "POST", update: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [peliculaInstanceList: Pelicula.list(params), peliculaInstanceTotal: Pelicula.count()]
    }

    def create() {
        [peliculaInstance: new Pelicula(params)]
    }

    def save() {
        def peliculaInstance = new Pelicula(params)
        if (!peliculaInstance.save(flush: true)) {
            render(view: "create", model: [peliculaInstance: peliculaInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'pelicula.label', default: 'Pelicula'), peliculaInstance.id])
        redirect(action: "mostrar", id: peliculaInstance.id)
    }

    def show() {
        def peliculaInstance = Pelicula.get(params.id)
        if (!peliculaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'pelicula.label', default: 'Pelicula'), params.id])
            redirect(action: "list")
            return
        }

        [peliculaInstance: peliculaInstance]
    }

    def edit() {
        def peliculaInstance = Pelicula.get(params.id)
        if (!peliculaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pelicula.label', default: 'Pelicula'), params.id])
            redirect(action: "list")
            return
        }

        [peliculaInstance: peliculaInstance]
    }

    def update() {
        def peliculaInstance = Pelicula.get(params.id)
        if (!peliculaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pelicula.label', default: 'Pelicula'), params.id])
            redirect(action: "list")
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
        redirect(action: "show", id: peliculaInstance.id)
    }

    def delete() {
        def peliculaInstance = Pelicula.get(params.id)
        if (!peliculaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'pelicula.label', default: 'Pelicula'), params.id])
            redirect(action: "list")
            return
        }

        try {
            peliculaInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'pelicula.label', default: 'Pelicula'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'pelicula.label', default: 'Pelicula'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
	
	
	def altaPelicula() {
		[peliculaInstance: new Pelicula(params)]
	}
	
	def darBajaPelicula() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[peliculaInstanceList: Pelicula.list(params), peliculaInstanceTotal: Pelicula.count()]
	}
	
	def listarPelicula() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[peliculaInstanceList: Pelicula.list(params), peliculaInstanceTotal: Pelicula.count()]
	}
	
	def mostrarPelicula() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[peliculaInstanceList: Pelicula.list(params), peliculaInstanceTotal: Pelicula.count()]
	}
	
	def mostrar() {
		def peliculaInstance = Pelicula.get(params.id)
		if (!peliculaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'pelicula.label', default: 'Pelicula'), params.id])
			redirect(action: "list")
			return
		}

		[peliculaInstance: peliculaInstance]
	}
}
