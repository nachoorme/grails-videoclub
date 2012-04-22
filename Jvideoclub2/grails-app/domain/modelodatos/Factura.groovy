package modelodatos

class Factura {

    static constraints = {
    }
	
	static belongsTo = [socio:Socio]
	float importe
	boolean estaPendiente = false
    Date fechaFactura		
}
