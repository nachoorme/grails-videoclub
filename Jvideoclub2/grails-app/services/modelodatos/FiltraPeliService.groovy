package modelodatos

class FiltraPeliService {

	static transactional = true
	
    def filtraPeliDis(String id) {
		Distribuidor distribResult= Distribuidor.findById(id)
		return distribResult?.pelicula;

    }
}
