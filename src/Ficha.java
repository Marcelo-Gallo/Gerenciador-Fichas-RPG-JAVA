public class Ficha {
	private int id;
    private String nome;
    private String raca;
    private String classe;
    private int nivel;
    private int pontosDeVida;
    private int pontosDeMana;
    private int forca;
    private int destreza;
    private int constituicao;
    private int inteligencia;
    private int sabedoria;
    private int carisma;
    private String alinhamento;
    private String historia;

    public Ficha() {
    }

    public Ficha(int id, String nome, String raca, String classe, int nivel, int pontosDeVida, int pontosDeMana,
                 int forca, int destreza, int constituicao, int inteligencia, int sabedoria, int carisma,
                 String alinhamento, String historia) {
    	this.id = id;
        this.nome = nome;
        this.raca = raca;
        this.classe = classe;
        this.nivel = nivel;
        this.pontosDeVida = pontosDeVida;
        this.pontosDeMana = pontosDeMana;
        this.forca = forca;
        this.destreza = destreza;
        this.constituicao = constituicao;
        this.inteligencia = inteligencia;
        this.sabedoria = sabedoria;
        this.carisma = carisma;
        this.alinhamento = alinhamento;
        this.historia = historia;
    }

    // Getters e setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getPontosDeVida() {
        return pontosDeVida;
    }

    public void setPontosDeVida(int pontosDeVida) {
        this.pontosDeVida = pontosDeVida;
    }

    public int getPontosDeMana() {
        return pontosDeMana;
    }

    public void setPontosDeMana(int pontosDeMana) {
        this.pontosDeMana = pontosDeMana;
    }

    public int getForca() {
        return forca;
    }

    public void setForca(int forca) {
        this.forca = forca;
    }

    public int getDestreza() {
        return destreza;
    }

    public void setDestreza(int destreza) {
        this.destreza = destreza;
    }

    public int getConstituicao() {
        return constituicao;
    }

    public void setConstituicao(int constituicao) {
        this.constituicao = constituicao;
    }

    public int getInteligencia() {
        return inteligencia;
    }

    public void setInteligencia(int inteligencia) {
        this.inteligencia = inteligencia;
    }

    public int getSabedoria() {
        return sabedoria;
    }

    public void setSabedoria(int sabedoria) {
        this.sabedoria = sabedoria;
    }

    public int getCarisma() {
        return carisma;
    }

    public void setCarisma(int carisma) {
        this.carisma = carisma;
    }

    public String getAlinhamento() {
        return alinhamento;
    }

    public void setAlinhamento(String alinhamento) {
        this.alinhamento = alinhamento;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
