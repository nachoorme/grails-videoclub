package modelodatos

class InformesController {

    def listaDistribuidor() { 
		
		[listaDistribuidor:Distribuidor.list()]
	}
	
	
}
