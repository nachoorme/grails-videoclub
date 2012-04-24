/* AUTOR: L�der equipo, Resp. Desarrollo
FECHA: 11/04/2012
NOMBRE MODULO: FacturaController.groovy
DESCRIPCI�N: Controlador Datos Factura */

package modelodatos

import org.springframework.dao.DataIntegrityViolationException

class FacturaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
    def buscaSocioService;
	
	/* NOMBRE FUNCI�N: index
	DESCRIPCI�N: Funci�n implementa la acci�n mostrada en el �ndice. */
    def index() {
        redirect(action: "list", params: params)
    }

	/* NOMBRE FUNCI�N: list
	DESCRIPCI�N: Funci�n lista facturas (b�sica). */
    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [facturaInstanceList: Factura.list(params), facturaInstanceTotal: Factura.count()]
    }

	/* NOMBRE FUNCI�N: create
	DESCRIPCI�N: Funci�n crear facturas (b�sica). */
    def create() {
        [facturaInstance: new Factura(params)]
    }

	/* NOMBRE FUNCI�N: save
	DESCRIPCI�N: Funci�n guarda datos facturas (b�sica). */
    def save() {
        def facturaInstance = new Factura(params)
        if (!facturaInstance.save(flush: true)) {
            render(view: "create", model: [facturaInstance: facturaInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'factura.label', default: 'Factura'), facturaInstance.id])
        redirect(action: "show", id: facturaInstance.id)
    }

	/* NOMBRE FUNCI�N: show
	DESCRIPCI�N: Funci�n mostrar facturas (b�sica). */
    def show() {
        def facturaInstance = Factura.get(params.id)
        if (!facturaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'factura.label', default: 'Factura'), params.id])
            redirect(action: "list")
            return
        }

        [facturaInstance: facturaInstance]
    }

	/* NOMBRE FUNCI�N: edit
	DESCRIPCI�N: Funci�n editar facturas (b�sica). */
    def edit() {
        def facturaInstance = Factura.get(params.id)
        if (!facturaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'factura.label', default: 'Factura'), params.id])
            redirect(action: "list")
            return
        }

        [facturaInstance: facturaInstance]
    }

	/* NOMBRE FUNCI�N: update
	DESCRIPCI�N: Funci�n actualizar datos facturas (b�sica). */
    def update() {
        def facturaInstance = Factura.get(params.id)
        if (!facturaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'factura.label', default: 'Factura'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (facturaInstance.version > version) {
                facturaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'factura.label', default: 'Factura')] as Object[],
                          "Another user has updated this Factura while you were editing")
                render(view: "edit", model: [facturaInstance: facturaInstance])
                return
            }
        }

        facturaInstance.properties = params

        if (!facturaInstance.save(flush: true)) {
            render(view: "edit", model: [facturaInstance: facturaInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'factura.label', default: 'Factura'), facturaInstance.id])
        redirect(action: "show", id: facturaInstance.id)
    }

	/* NOMBRE FUNCI�N: delete
	DESCRIPCI�N: Funci�n eliminar facturas (b�sica). */
    def delete() {
        def facturaInstance = Factura.get(params.id)
        if (!facturaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'factura.label', default: 'Factura'), params.id])
            redirect(action: "list")
            return
        }

        try {
            facturaInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'factura.label', default: 'Factura'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'factura.label', default: 'Factura'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

	/* NOMBRE FUNCI�N: pagarFactura
	DESCRIPCI�N: Funci�n realiza el pago de la factura de un alquiler. */
	def pagarFactura(){
		def facturaInstance = Factura.get(params.id);
		facturaInstance.estaPendiente=false;
        if (!facturaInstance.save(flush: true)) {
            render(view: "create", model: [facturaInstance: facturaInstance])
            return
        }

		flash.message = "Factura pagada"
        redirect(action: "show", id: facturaInstance.id)
	}

	
	/* NOMBRE FUNCI�N: misfacturas
	DESCRIPCI�N: Funci�n realiza la lista de facturas pendientes de un socio determinado. */
	def misfacturas(){
		println params
		def socio = Socio.get(params.id)
		def listaFacturas = Factura.findAllBySocioAndEstaPendiente(socio,true);
		float importeTotal = 0;
		listaFacturas.each {factura -> importeTotal+=factura.importe};
		[facturaInstanceList: listaFacturas, facturaInstanceTotal: listaFacturas.size(),socioInstance:socio,importeTotal:importeTotal]
	}
	
	/* NOMBRE FUNCI�N: pagarTodas
	DESCRIPCI�N: Funci�n realiza el pago de todas las facturas penddientes. */
	def pagarTodas(){
		def socio = Socio.get(params.id)
		def listaFacturasPendientes = Factura.findAllBySocioAndEstaPendiente(socio,true)
		listaFacturasPendientes.each{factura ->
			factura.estaPendiente=false;
			factura.save(flush:true);
		}
		flash.message = "Facturas pagadas"
		redirect(action: "facturaspendientes")
	}
	
	/* NOMBRE FUNCI�N: cargoCuenta
	DESCRIPCI�N: Funci�n realiza un cargo a la cuenta cuando el importe total de facturas es superior a 18 euros. */
	def cargoCuenta(){
		def socio = Socio.get(params.id)
		def listaFacturasPendientes = Factura.findAllBySocioAndEstaPendiente(socio,true)
		listaFacturasPendientes.each{factura ->
			factura.estaPendiente=false;
			factura.save(flush:true);
		}
		flash.message = "Facturas pagadas"
		redirect(controller: "alquiler", action: "create")
	}
	
	def facturaspendientes(){
		[listaSociosFacturasPendientes:buscaSocioService.sociosConFacturasPendientes()]
	}

}
