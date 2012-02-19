package modelodatos

class FiltraPeliService {

	static transactional = true
	
    def filtraPeliDis(String id) {
		def peliDisInstance
		return peliDisInstance = pelicula.list(id)

    }
}
