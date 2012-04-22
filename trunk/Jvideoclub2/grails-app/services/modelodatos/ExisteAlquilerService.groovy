package modelodatos

class ExisteAlquilerService {

    def hayAlquilerPendiente(Socio socio) {
		boolean resultado=Alquiler.findAllBySocioAndFechaEntrega(socio,null)
		return resultado;
    }
}
