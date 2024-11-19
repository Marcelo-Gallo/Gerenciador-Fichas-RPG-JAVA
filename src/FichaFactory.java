
public class FichaFactory {
	public static Ficha createFicha(String Nome, String fichaData) {
		return new Ficha(Nome, fichaData);
	}
}