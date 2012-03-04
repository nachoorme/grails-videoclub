/* AUTOR: L�der equipo, Resp. Desarrollo
FECHA: 10/02/2012
NOMBRE MODULO: SocioController.groovy
DESCRIPCI�N: Controlador Datos Socio */

package modelodatos

import org.springframework.dao.DataIntegrityViolationException

class SocioController {
	
    static allowedMethods = [save: "POST", update: "POST"]

	/* NOMBRE FUNCI�N: index
	DESCRIPCI�N: Funci�n implementa la acci�n mostrada en el �ndice. */
    def index() {
        redirect(action: "list", params: params)
    }

	/* NOMBRE FUNCI�N: list
	DESCRIPCI�N: Funci�n lista socios (b�sica). */
    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [socioInstanceList: Socio.list(params), socioInstanceTotal: Socio.count()]
    }

	/* NOMBRE FUNCI�N: create
	DESCRIPCI�N: Funci�n crear socios (b�sica). */
    def create() {
        [socioInstance: new Socio(params)]
    }

	/* NOMBRE FUNCI�N: save
	DESCRIPCI�N: Funci�n guarda datos socios (b�sica). */
    def save() {
        def socioInstance = new Socio(params)
        if (!socioInstance.save(flush: true)) {
            render(view: "create", model: [socioInstance: socioInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'socio.label', default: 'Socio'), socioInstance.id])
        redirect(action: "show", id: socioInstance.id)
    }

	/* NOMBRE FUNCI�N: show
	DESCRIPCI�N: Funci�n mostrar (b�sica). */
    def show() {
        def socioInstance = Socio.get(params.id)
        if (!socioInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'socio.label', default: 'Socio'), params.id])
            redirect(action: "list")
            return
        }

        [socioInstance: socioInstance]
    }
	
	/* NOMBRE FUNCI�N: edit
	DESCRIPCI�N: Funci�n editar socios (b�sica). */
    def edit() {
        def socioInstance = Socio.get(params.id)
        if (!socioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'socio.label', default: 'Socio'), params.id])
            redirect(action: "list")
            return
        }

        [socioInstance: socioInstance]
    }

	/* NOMBRE FUNCI�N: update
	DESCRIPCI�N: Funci�n actualizar datos socios (b�sica). */
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

	/* NOMBRE FUNCI�N: delete
	DESCRIPCI�N: Funci�n eliminar socios (b�sica). */
    def delete() {
        def socioInstance = Socio.get(params.id)
        if (!socioInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'socio.label', default: 'Socio'), params.id])
            redirect(action: "darBajaSocio")
            return
        }

        try {
            socioInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'socio.label', default: 'Socio'), params.id])
            redirect(action: "darBajaSocio")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'socio.label', default: 'Socio'), params.id])
            redirect(action: "mostrar", id: params.id)
        }
    }
	
	def calcularSocioMayorService

	/* NOMBRE FUNCI�N: crearAltaSocio
	DESCRIPCI�N: Funci�n dar Alta socios, redirige seg�n el socio sea menor o no. */
	def crearAltaSocio () {
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
	
	/* NOMBRE FUNCI�N: altaSocioMayor
	DESCRIPCI�N: Funci�n dar Alta socios mayores de edad. */
	def altaSocioMayor () {
		[socioInstance: new Socio(params)]
	}
	
	/* NOMBRE FUNCI�N: saveSocioMayor
	DESCRIPCI�N: Funci�n salvar datos socios mayores de edad. */
	def saveSocioMayor () {
		def socioInstance = new Socio(params)
		if (!socioInstance.save(flush: true)) {
			render(view: "altaSocioMayor", model: [socioInstance: socioInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [message(code: 'socio.label', default: 'Socio'), socioInstance.id])
		redirect(action: "mostrar", id: socioInstance.id)
	}
	
	/* NOMBRE FUNCI�N: saveSocioMenor
	DESCRIPCI�N: Funci�n salvar datos socios menores de edad. */
	def saveSocioMenor () {
		def socioInstance = new Socio(params)
		if (!socioInstance.save(flush: true)) {
			render(view: "altaSocioMenor", model: [socioInstance: socioInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [message(code: 'socio.label', default: 'Socio'), socioInstance.id])
		redirect(action: "mostrar", id: socioInstance.id)
	}
	
	/* NOMBRE FUNCI�N: altaSocioMenor
	DESCRIPCI�N: Funci�n dar Alta socios menores de edad. */
	def altaSocioMenor () {
		[socioInstance: new Socio(params)]
	}
	
	/* NOMBRE FUNCI�N: listarSocios
	DESCRIPCI�N: Funci�n que muestra el listado completo de socios. */
	def listarSocios () {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[socioInstanceList: Socio.list(params), socioInstanceTotal: Socio.count()]
	}
	
	/* NOMBRE FUNCI�N: darBajaSocio
	DESCRIPCI�N: Funci�n que muestra el listado de socios para poder eliminarlos. */
	def darBajaSocio () {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[socioInstanceList: Socio.list(params), socioInstanceTotal: Socio.count()]
	}
	
	/* NOMBRE FUNCI�N: mostrar
	DESCRIPCI�N: Funci�n que muestra los datos de un socio concreto. */
	def mostrar () {
		def socioInstance = Socio.get(params.id)
		if (!socioInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'socio.label', default: 'Socio'), params.id])
			redirect(action: "mostrarSocio")
			return
		}
		[socioInstance: socioInstance]
	}
	
	/* NOMBRE FUNCI�N: mostrarSocio
	DESCRIPCI�N: Funci�n que muestra el listado de socios para poder mostrar los datos de uno concreto. */
	def mostrarSocio () {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[socioInstanceList: Socio.list(params), socioInstanceTotal: Socio.count()]
	}
	
	/* NOMBRE FUNCI�N: actualizarSocio
	DESCRIPCI�N: Funci�n que actualiza los datos de un socio modificados previamente. */
	def actualizarSocio () {
		def socioInstance = Socio.get(params.id)
		if (!socioInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'socio.label', default: 'Socio'), params.id])
			redirect(action: "modificarSocio")
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
		redirect(action: "mostrar", id: socioInstance.id)
	}
	
	/* NOMBRE FUNCI�N: modificar
	DESCRIPCI�N: Funci�n que modificados los datos de un socio concreto. */
	def modificar () {
		def socioInstance = Socio.get(params.id)
		if (!socioInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'socio.label', default: 'Socio'), params.id])
			redirect(action: "modificarSocio")
			return
		}

		[socioInstance: socioInstance]
	}
	
	/* NOMBRE FUNCI�N: modificarSocio
	DESCRIPCI�N: Funci�n que muestra el listado de socios para poder modificarlos. */
	def modificarSocio () {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[socioInstanceList: Socio.list(params), socioInstanceTotal: Socio.count()]
	}
	
	/* NOMBRE FUNCI�N: imprimir
	DESCRIPCI�N: Funci�n que imprimir el carnet de un socio concreto. */
	def imprimir () {
		def socioInstance = Socio.get(params.id)
		if (!socioInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'socio.label', default: 'Socio'), params.id])
			redirect(action: "imprimirCarnet")
			return
		}
		[socioInstance: socioInstance]
	}
	
	/* NOMBRE FUNCI�N: imprimirCarnet
	DESCRIPCI�N: Funci�n que muestra el listado de socios para poder imprimir su carnet. */
	def imprimirCarnet () {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[socioInstanceList: Socio.list(params), socioInstanceTotal: Socio.count()]
	}
	
}
