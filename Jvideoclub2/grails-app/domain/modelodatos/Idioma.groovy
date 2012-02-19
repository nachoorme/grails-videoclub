package modelodatos

public enum Idioma {

	Chino("Chino"),
	Ingles("Inglés"),
	Indio("Indio"),
	Espagnol("Español"),
	Portugues("Portugués"),
	Ruso("Ruso"),
	Arabe("Árabe"),
	Japones("Japonés"),
	Frances("Francés"),
	Aleman("Alemán"),
	Coreano("Coreano"),
	Italiano("Italiano"),
	Cantones("Cantonés"),
	Ucraniano("Ucraniano"),
	Holandes("Holandés")

	String tipo
	Idioma(String tipo) {
		this.tipo = tipo
	}
	
	String toString(){
		return tipo;
	}
}
