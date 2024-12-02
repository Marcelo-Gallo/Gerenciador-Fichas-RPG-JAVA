public class FichaFactory {
    public static Ficha createFicha(int id, String nome, String raca, String classe, int nivel, int pontosDeVida, int pontosDeMana,
                                    int forca, int destreza, int constituicao, int inteligencia, int sabedoria, int carisma,
                                    String alinhamento, String historia) {
        return new Ficha(id, nome, raca, classe, nivel, pontosDeVida, pontosDeMana, forca, destreza, constituicao,
                         inteligencia, sabedoria, carisma, alinhamento, historia);
    }
}
