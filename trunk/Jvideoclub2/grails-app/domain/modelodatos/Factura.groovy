package modelodatos

class Factura {
	static reportable = [:]
    static constraints = {
    }
	
	static belongsTo = [socio:Socio]
	float importe
	boolean estaPendiente = false
    Date fechaFactura		
}
