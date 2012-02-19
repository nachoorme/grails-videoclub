package modelodatos

class Soporte {

    static constraints = {
    }
	
	
	static belongsTo = [pelicula:Pelicula]
	Pelicula pelicula;
	
	TipoSoporte tipoSoporte  //CD o DVD
	
	static transients = ['precioAlquiler']  //Porque no queremos almacenar el precio en BD ya que es un valor calculado
	
	float getPrecioAlquiler (){
		float resultado = pelicula.precioBase;
		
		if (pelicula.tipoGenero == Categoria.NOVEDAD){
			resultado += pelicula.precioBase*0.3;
		}
		if (pelicula.tipoGenero == Categoria.NORMAL){
			resultado += pelicula.precioBase*0.2;
		}
		
		if (tipoSoporte == TipoSoporte.DVD){
			resultado += pelicula.precioBase*0.3;
		}
		if (tipoSoporte == TipoSoporte.CINTA){
			resultado += pelicula.precioBase*0.2;
		}
		
		return resultado;
	}
	
}