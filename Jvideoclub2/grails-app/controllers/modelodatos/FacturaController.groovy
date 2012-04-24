package modelodatos

import org.springframework.dao.DataIntegrityViolationException

class FacturaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
    def buscaSocioService;
	
    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [facturaInstanceList: Factura.list(params), facturaInstanceTotal: Factura.count()]
    }

    def create() {
        [facturaInstance: new Factura(params)]
    }

    def save() {
        def facturaInstance = new Factura(params)
        if (!facturaInstance.save(flush: true)) {
            render(view: "create", model: [facturaInstance: facturaInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'factura.label', default: 'Factura'), facturaInstance.id])
        redirect(action: "show", id: facturaInstance.id)
    }

    def show() {
        def facturaInstance = Factura.get(params.id)
        if (!facturaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'factura.label', default: 'Factura'), params.id])
            redirect(action: "list")
            return
        }

        [facturaInstance: facturaInstance]
    }

    def edit() {
        def facturaInstance = Factura.get(params.id)
        if (!facturaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'factura.label', default: 'Factura'), params.id])
            redirect(action: "list")
            return
        }

        [facturaInstance: facturaInstance]
    }

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

	def misfacturas(){
		println params
		def socio = Socio.get(params.id)
		def listaFacturas = Factura.findAllBySocioAndEstaPendiente(socio,true);
		float importeTotal = 0;
		listaFacturas.each {factura -> importeTotal+=factura.importe};
		[facturaInstanceList: listaFacturas, facturaInstanceTotal: listaFacturas.size(),socioInstance:socio,importeTotal:importeTotal]
	}
	
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
