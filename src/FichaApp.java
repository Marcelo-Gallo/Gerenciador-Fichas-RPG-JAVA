import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class FichaApp {
    private JFrame frame;
    private JTextField txtNome, txtRaca, txtClasse, txtAlinhamento;
    private JSpinner spnNivel, spnForca, spnDestreza, spnConstituicao, spnInteligencia, spnSabedoria, spnCarisma;
    private JTable tabelaFichas;
    private DefaultTableModel modeloTabela;
    private ArrayList<Ficha> fichas;

    public FichaApp() {
        fichas = new ArrayList<>();
        initialize();
        carregarDoBanco();
    }

    private void initialize() {
        frame = new JFrame("Gerenciador de Fichas de RPG");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(10, 10, 100, 25);
        frame.getContentPane().add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(120, 10, 200, 25);
        frame.getContentPane().add(txtNome);

        JLabel lblRaca = new JLabel("Raça:");
        lblRaca.setBounds(10, 50, 100, 25);
        frame.getContentPane().add(lblRaca);

        txtRaca = new JTextField();
        txtRaca.setBounds(120, 50, 200, 25);
        frame.getContentPane().add(txtRaca);

        JLabel lblClasse = new JLabel("Classe:");
        lblClasse.setBounds(10, 90, 100, 25);
        frame.getContentPane().add(lblClasse);

        txtClasse = new JTextField();
        txtClasse.setBounds(120, 90, 200, 25);
        frame.getContentPane().add(txtClasse);

        JLabel lblAlinhamento = new JLabel("Alinhamento:");
        lblAlinhamento.setBounds(10, 130, 100, 25);
        frame.getContentPane().add(lblAlinhamento);

        txtAlinhamento = new JTextField();
        txtAlinhamento.setBounds(120, 130, 200, 25);
        frame.getContentPane().add(txtAlinhamento);

        JLabel lblNivel = new JLabel("Nível:");
        lblNivel.setBounds(10, 170, 100, 25);
        frame.getContentPane().add(lblNivel);

        spnNivel = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        spnNivel.setBounds(120, 170, 50, 25);
        frame.getContentPane().add(spnNivel);

        // Campos de atributos
        JLabel lblForca = new JLabel("Força:");
        lblForca.setBounds(350, 10, 100, 25);
        frame.getContentPane().add(lblForca);

        spnForca = new JSpinner(new SpinnerNumberModel(10, 1, 20, 1));
        spnForca.setBounds(450, 10, 50, 25);
        frame.getContentPane().add(spnForca);

        JLabel lblDestreza = new JLabel("Destreza:");
        lblDestreza.setBounds(350, 50, 100, 25);
        frame.getContentPane().add(lblDestreza);

        spnDestreza = new JSpinner(new SpinnerNumberModel(10, 1, 20, 1));
        spnDestreza.setBounds(450, 50, 50, 25);
        frame.getContentPane().add(spnDestreza);

        JLabel lblConstituicao = new JLabel("Constituição:");
        lblConstituicao.setBounds(350, 90, 100, 25);
        frame.getContentPane().add(lblConstituicao);

        spnConstituicao = new JSpinner(new SpinnerNumberModel(10, 1, 20, 1));
        spnConstituicao.setBounds(450, 90, 50, 25);
        frame.getContentPane().add(spnConstituicao);

        JLabel lblInteligencia = new JLabel("Inteligência:");
        lblInteligencia.setBounds(350, 130, 100, 25);
        frame.getContentPane().add(lblInteligencia);

        spnInteligencia = new JSpinner(new SpinnerNumberModel(10, 1, 20, 1));
        spnInteligencia.setBounds(450, 130, 50, 25);
        frame.getContentPane().add(spnInteligencia);

        JLabel lblSabedoria = new JLabel("Sabedoria:");
        lblSabedoria.setBounds(350, 170, 100, 25);
        frame.getContentPane().add(lblSabedoria);

        spnSabedoria = new JSpinner(new SpinnerNumberModel(10, 1, 20, 1));
        spnSabedoria.setBounds(450, 170, 50, 25);
        frame.getContentPane().add(spnSabedoria);

        JLabel lblCarisma = new JLabel("Carisma:");
        lblCarisma.setBounds(350, 210, 100, 25);
        frame.getContentPane().add(lblCarisma);

        spnCarisma = new JSpinner(new SpinnerNumberModel(10, 1, 20, 1));
        spnCarisma.setBounds(450, 210, 50, 25);
        frame.getContentPane().add(spnCarisma);

        // Botão para adicionar ficha
        JButton btnAdd = new JButton("Adicionar Ficha");
        btnAdd.setBounds(10, 250, 200, 30);
        frame.getContentPane().add(btnAdd);

        // Botão para salvar ficha
        JButton btnSave = new JButton("Salvar Ficha");
        btnSave.setBounds(220, 250, 200, 30);
        frame.getContentPane().add(btnSave);

        modeloTabela = new DefaultTableModel(new Object[]{"Nome", "Raça", "Classe", "Nível", "Status"}, 0);
        tabelaFichas = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaFichas);
        scrollPane.setBounds(10, 300, 760, 250);
        frame.getContentPane().add(scrollPane);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFicha();
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarFichas();
            }
        });

        // Adiciona listener para atualizar ficha
        tabelaFichas.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tabelaFichas.getSelectedRow() != -1) {
                atualizarFicha(tabelaFichas.getSelectedRow());
            }
        });
    }

    private void addFicha() {
        String nome = txtNome.getText();
        String raca = txtRaca.getText();
        String classe = txtClasse.getText();
        int nivel = (int) spnNivel.getValue();
        String alinhamento = txtAlinhamento.getText();
        int forca = (int) spnForca.getValue();
        int destreza = (int) spnDestreza.getValue();
        int constituicao = (int) spnConstituicao.getValue();
        int inteligencia = (int) spnInteligencia.getValue();
        int sabedoria = (int) spnSabedoria.getValue();
        int carisma = (int) spnCarisma.getValue();

        // Cria e adiciona a ficha
        Ficha ficha = new Ficha(nome, raca, classe, nivel, alinhamento, forca, destreza, constituicao, inteligencia, sabedoria, carisma, false);
        fichas.add(ficha);

        // Atualiza tabela
        modeloTabela.addRow(new Object[]{nome, raca, classe, nivel, "Não salvo"});
    }

    private void atualizarFicha(int rowIndex) {
        Ficha ficha = fichas.get(rowIndex);
        txtNome.setText(ficha.getNome());
        txtRaca.setText(ficha.getRaca());
        txtClasse.setText(ficha.getClasse());
        spnNivel.setValue(ficha.getNivel());
        txtAlinhamento.setText(ficha.getAlinhamento());
        spnForca.setValue(ficha.getForca());
        spnDestreza.setValue(ficha.getDestreza());
        spnConstituicao.setValue(ficha.getConstituicao());
        spnInteligencia.setValue(ficha.getInteligencia());
        spnSabedoria.setValue(ficha.getSabedoria());
        spnCarisma.setValue(ficha.getCarisma());
    }

    private void salvarFichas() {
        try (Connection conn = Conexao.getInstance().getConnection()) {
            String sql = "INSERT INTO personagens (nome, raca, classe, nivel, alinhamento, forca, destreza, constituicao, inteligencia, sabedoria, carisma) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            for (Ficha ficha : fichas) {
                if (!ficha.isSalvaNoBanco()) {
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, ficha.getNome());
                        pstmt.setString(2, ficha.getRaca());
                        pstmt.setString(3, ficha.getClasse());
                        pstmt.setInt(4, ficha.getNivel());
                        pstmt.setString(5, ficha.getAlinhamento());
                        pstmt.setInt(6, ficha.getForca());
                        pstmt.setInt(7, ficha.getDestreza());
                        pstmt.setInt(8, ficha.getConstituicao());
                        pstmt.setInt(9, ficha.getInteligencia());
                        pstmt.setInt(10, ficha.getSabedoria());
                        pstmt.setInt(11, ficha.getCarisma());
                        pstmt.executeUpdate();
                        ficha.setSalvaNoBanco(true);
                    }
                }
            }
            carregarDoBanco();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void carregarDoBanco() {
        fichas.clear();
        modeloTabela.setRowCount(0);
        try (Connection conn = Conexao.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM personagens")) {

            while (rs.next()) {
                Ficha ficha = new Ficha(
                        rs.getString("nome"),
                        rs.getString("raca"),
                        rs.getString("classe"),
                        rs.getInt("nivel"),
                        rs.getString("alinhamento"),
                        rs.getInt("forca"),
                        rs.getInt("destreza"),
                        rs.getInt("constituicao"),
                        rs.getInt("inteligencia"),
                        rs.getInt("sabedoria"),
                        rs.getInt("carisma"),
                        true
                );
                fichas.add(ficha);
                modeloTabela.addRow(new Object[]{
                        ficha.getNome(),
                        ficha.getRaca(),
                        ficha.getClasse(),
                        ficha.getNivel(),
                        ficha.isSalvaNoBanco() ? "Salvo" : "Não salvo"
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                FichaApp window = new FichaApp();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}