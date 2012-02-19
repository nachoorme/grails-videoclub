package modelodatos

import org.springframework.dao.DataIntegrityViolationException

class DistribuidorController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [distribuidorInstanceList: Distribuidor.list(params), distribuidorInstanceTotal: Distribuidor.count()]
    }

    def create() {
        [distribuidorInstance: new Distribuidor(params)]
    }

    def save() {
        def distribuidorInstance = new Distribuidor(params)
        if (!distribuidorInstance.save(flush: true)) {
            render(view: "create", model: [distribuidorInstance: distribuidorInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'distribuidor.label', default: 'Distribuidor'), distribuidorInstance.id])
        redirect(action: "show", id: distribuidorInstance.id)
    }

    def show() {
        def distribuidorInstance = Distribuidor.get(params.id)
        if (!distribuidorInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'distribuidor.label', default: 'Distribuidor'), params.id])
            redirect(action: "list")
            return
        }

        [distribuidorInstance: distribuidorInstance]
    }

    def edit() {
        def distribuidorInstance = Distribuidor.get(params.id)
        if (!distribuidorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'distribuidor.label', default: 'Distribuidor'), params.id])
            redirect(action: "list")
            return
        }

        [distribuidorInstance: distribuidorInstance]
    }

    def update() {
        def distribuidorInstance = Distribuidor.get(params.id)
        if (!distribuidorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'distribuidor.label', default: 'Distribuidor'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (distribuidorInstance.version > version) {
                distribuidorInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'distribuidor.label', default: 'Distribuidor')] as Object[],
                          "Another user has updated this Distribuidor while you were editing")
                render(view: "edit", model: [distribuidorInstance: distribuidorInstance])
                return
            }
        }

        distribuidorInstance.properties = params

        if (!distribuidorInstance.save(flush: true)) {
            render(view: "edit", model: [distribuidorInstance: distribuidorInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'distribuidor.label', default: 'Distribuidor'), distribuidorInstance.id])
        redirect(action: "show", id: distribuidorInstance.id)
    }

    def delete() {
        def distribuidorInstance = Distribuidor.get(params.id)
        if (!distribuidorInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'distribuidor.label', default: 'Distribuidor'), params.id])
            redirect(action: "list")
            return
        }

        try {
            distribuidorInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'distribuidor.label', default: 'Distribuidor'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'distribuidor.label', default: 'Distribuidor'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

		
	def crearAltaDistribuidor = {
	 [distribuidorInstance: new Distribuidor(params)]
	}
	
	
	
	
	
}
