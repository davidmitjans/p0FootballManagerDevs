package other;

public class ValorCombo {
	private int id;
	private String texto;

	public ValorCombo(int id, String texto) {
		this.id = id;
		this.texto = texto;
	}

	public int idProperty() {
		return id;
	}

	public String textoProperty() {
		return texto;
	}

	@Override
	public String toString() {
		return texto;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (texto != null ? texto.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		String otherTCountryCode = "";
		if (object instanceof ValorCombo) {
			otherTCountryCode = ((ValorCombo) object).texto;
		} else if (object instanceof String) {
			otherTCountryCode = (String) object;
		} else {
			return false;
		}

		if ((this.texto == null && otherTCountryCode != null)
				|| (this.texto != null && !this.texto.equals(otherTCountryCode))) {
			return false;
		}
		return true;
	}
}