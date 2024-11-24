public class FichaFactory {
    public static Ficha createFicha(String nome, String raca, String classe, int nivel, String alinhamento,
                                    int forca, int destreza, int constituicao, int inteligencia, int sabedoria, int carisma, boolean salva) {
        return new Ficha(nome, raca, classe, nivel, alinhamento, forca, destreza, constituicao, inteligencia, sabedoria, carisma, salva);
    }
}
 