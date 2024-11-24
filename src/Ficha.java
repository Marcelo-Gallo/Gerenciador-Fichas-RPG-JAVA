class Ficha {
    private String nome, raca, classe, alinhamento;
    private int nivel, forca, destreza, constituicao, inteligencia, sabedoria, carisma;
    private boolean salvaNoBanco;

    public Ficha(String nome, String raca, String classe, int nivel, String alinhamento, int forca, int destreza, int constituicao, int inteligencia, int sabedoria, int carisma, boolean salva) {
        this.nome = nome;
        this.raca = raca;
        this.classe = classe;
        this.nivel = nivel;
        this.alinhamento = alinhamento;
        this.forca = forca;
        this.destreza = destreza;
        this.constituicao = constituicao;
        this.inteligencia = inteligencia;
        this.sabedoria = sabedoria;
        this.carisma = carisma;
        this.salvaNoBanco = salvaNoBanco;
    }

    public String getNome() { return nome; }
    public String getRaca() { return raca; }
    public String getClasse() { return classe; }
    public int getNivel() { return nivel; }
    public String getAlinhamento() { return alinhamento; }
    public int getForca() { return forca; }
    public int getDestreza() { return destreza; }
    public int getConstituicao() { return constituicao; }
    public int getInteligencia() { return inteligencia; }
    public int getSabedoria() { return sabedoria; }
    public int getCarisma() { return carisma; }
    public boolean isSalvaNoBanco() { return salvaNoBanco; }
    public void setSalvaNoBanco(boolean salvaNoBanco) { this.salvaNoBanco = salvaNoBanco; }
}