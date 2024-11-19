public class Ficha {
    private String Nome;
    private String fichaData;

    public Ficha() {
    }

    public Ficha(String Nome, String fichaData) {
        this.Nome = Nome;
        this.fichaData = fichaData;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getFichaData() {
        return fichaData;
    }

    public void setFichaData(String fichaData) {
        this.fichaData = fichaData;
    }
}
