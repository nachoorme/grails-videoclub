/* AUTOR: Resp. Soporte, Resp. Desarrollo
FECHA: 17/02/2012
NOMBRE MODULO: DistribuidorController.groovy
DESCRIPCI�N: Controlador Datos Distribuidora */

package modelodatos

import org.springframework.dao.DataIntegrityViolationException

class DistribuidorController {

    static allowedMethods = [save: "POST", update: "POST"]

	/* NOMBRE FUNCI�N: index
	DESCRIPCI�N: Funci�n implementa la acci�n mostrada en el �ndice. */
    def index() {
        redirect(action: "list", params: params)
    }

	/* NOMBRE FUNCI�N: list
	DESCRIPCI�N: Funci�n lista distribuidoras (b�sica). */
    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [distribuidorInstanceList: Distribuidor.list(params), distribuidorInstanceTotal: Distribuidor.count()]
    }

	/* NOMBRE FUNCI�N: create
	DESCRIPCI�N: Funci�n crear distribuidoras (b�sica). */
    def create() {
        [distribuidorInstance: new Distribuidor(params)]
    }

	/* NOMBRE FUNCI�N: save
	DESCRIPCI�N: Funci�n guarda datos distribuidoras (b�sica). */
    def save() {
        def distribuidorInstance = new Distribuidor(params)
        if (!distribuidorInstance.save(flush: true)) {
            render(view: "crearAltaDistribuidor", model: [distribuidorInstance: distribuidorInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'distribuidor.label', default: 'Distribuidor'), distribuidorInstance.id])
        redirect(action: "show", id: distribuidorInstance.id)
    }

	/* NOMBRE FUNCI�N: show
	DESCRIPCI�N: Funci�n mostrar (b�sica). */
    def show() {
        def distribuidorInstance = Distribuidor.get(params.id)
        if (!distribuidorInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'distribuidor.label', default: 'Distribuidor'), params.id])
            redirect(action: "list")
            return
        }

        [distribuidorInstance: distribuidorInstance]
    }

	/* NOMBRE FUNCI�N: edit
	DESCRIPCI�N: Funci�n editar distribuidoras (b�sica). */
    def edit() {
        def distribuidorInstance = Distribuidor.get(params.id)
        if (!distribuidorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'distribuidor.label', default: 'Distribuidor'), params.id])
            redirect(action: "list")
            return
        }

        [distribuidorInstance: distribuidorInstance]
    }

	/* NOMBRE FUNCI�N: update
	DESCRIPCI�N: Funci�n actualizar datos distribuidoras (b�sica). */
    def update() {
        def distribuidorInstance = Distribuidor.get(params.id)
        if (!distribuidorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'distribuidor.label', default: 'Distribuidor'), params.id])
            redirect(action: "modificarDistribuidor")
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

	/* NOMBRE FUNCI�N: delete
	DESCRIPCI�N: Funci�n eliminar distribuidoras (b�sica). */
    def delete() {
        def distribuidorInstance = Distribuidor.get(params.id)
        if (!distribuidorInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'distribuidor.label', default: 'Distribuidor'), params.id])
            redirect(action: "darBajaDistribuidor")
            return
        }

        try {
            distribuidorInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'distribuidor.label', default: 'Distribuidor'), params.id])
            redirect(action: "darBajaDistribuidor")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'distribuidor.label', default: 'Distribuidor'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

	/* NOMBRE FUNCI�N: crearAltaDistribuidor
	DESCRIPCI�N: Funci�n dar Alta distribuidoras, recibe datos y crea. */
	def crearAltaDistribuidor () {
	 [distribuidorInstance: new Distribuidor(params)]
	}
	
	/* NOMBRE FUNCI�N: saveDistribuidora
	DESCRIPCI�N: Funci�n salvar datos distribuidoras. */
	def saveDistribuidora () {
		def distribuidorInstance = new Distribuidor(params)
		if (!distribuidorInstance.save(flush: true)) {
			render(view: "crearAltaDistribuidor", model: [distribuidorInstance: distribuidorInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [message(code: 'distribuidor.label', default: 'Distribuidor'), distribuidorInstance.id])
		redirect(action: "show", id: distribuidorInstance.id)
	}
	
	/* NOMBRE FUNCI�N: darBajaDistribuidor
	DESCRIPCI�N: Funci�n que muestra el listado de distribuidoras para poder eliminarlos. */
	def darBajaDistribuidor () {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[distribuidorInstanceList: Distribuidor.list(params), distribuidorInstanceTotal: Distribuidor.count()]
	}
	
	/* NOMBRE FUNCI�N: listarDistribuidor
	DESCRIPCI�N: Funci�n que muestra el listado completo de distribuidoras. */
	def listarDistribuidor () {
		redirect (action: "list")
	}
	
	/* NOMBRE FUNCI�N: mostrarDistribuidor
	DESCRIPCI�N: Funci�n que muestra el listado de distribuidoras para poder mostrar los datos de uno concreto. */
	def mostrarDistribuidor () {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[distribuidorInstanceList: Distribuidor.list(params), distribuidorInstanceTotal: Distribuidor.count()]
	}
	
	/* NOMBRE FUNCI�N: modificarDistribuidor
	DESCRIPCI�N: Funci�n que muestra el listado de distribuidoras para poder modificarlos. */
	def modificarDistribuidor () {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[distribuidorInstanceList: Distribuidor.list(params), distribuidorInstanceTotal: Distribuidor.count()]
	}
}
