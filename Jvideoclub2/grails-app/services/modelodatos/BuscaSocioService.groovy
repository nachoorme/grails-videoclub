package modelodatos

class BuscaSocioService {

   static transactional = true

    def buscaIdPorDni(String dni) {
			Socio socioResult= Socio.findByDni(dni)
			return socioResult?.id;
    }
}
