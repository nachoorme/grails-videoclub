package modelodatos

class FacturasPendientesService {

    def hayFacturasPendientes(Socio socio) {
		def listaFacturas = Factura.findAllBySocioAndEstaPendiente(socio,true)
		def importependientedepago =0;
		listaFacturas.each{factura ->
			importependientedepago += factura.importe;
		}
		
		return importependientedepago>=18; 
    }
}
