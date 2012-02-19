package modelodatos

class FiltraPeliService {

	static transactional = true
	
    def filtraPeliDis(String id) {
		Distribuidor distribResult= Distribuidor.findByid(id)
		return distribResult?.pelicula;

    }
}
