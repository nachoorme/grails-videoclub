package modelodatos

public enum Genero {

	Accion("Acci�n"),
	Aventura("Aventura"),
	Animacion("Animaci�n"),
	Biografica("Biogr�fica"),
	Comedia("Comedia"),
	Crime("Crime"),
	Documental("Documental"),
	Drama("Drama"),
	Familiar("Familiar"),
	Fantastica("Fant�stica"),
	CineNegro("Cine Negro"),
	Historica("Hist�rica"),
	Terror("Terror"),
	Musica("M�sica"),
	Musical("Musical"),
	Misterio("Misterio"),
	Noticias("Noticias"),
	RealityTV("RealityTV"),
	Romantica("Rom�ntica"),
	CienciaFiccion("Ciencia-Ficci�n"),
	Deportes("Deportes"),
	TalkShow("TalkShow"),
	Thriller("Thriller"),
	Belica("B�lica"),
	Western("Western")

	String tipo
	Genero(String tipo) {
		this.tipo = tipo
	}
}