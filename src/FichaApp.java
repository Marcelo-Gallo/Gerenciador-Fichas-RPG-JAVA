import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;


public class FichaApp {
    // Componentes da interface gráfica para os novos atributos
    private JTextField txtRaca, txtClasse, txtNivel, txtPontosDeVida, txtPontosDeMana;
    private JTextField txtForca, txtDestreza, txtConstituicao, txtInteligencia, txtSabedoria, txtCarisma;
    private JTextField txtAlinhamento;
    private JTextArea txtHistoria;

    // Outros componentes e atributos
    private JFrame frame;
    private JTextField txtNome;
    private JTable tabelaFichas;
    private DefaultTableModel modeloTabela;
    private ArrayList<Ficha> fichasInseridas;
    private ArrayList<Ficha> fichasDoBanco;

    public FichaApp() {
        fichasInseridas = new ArrayList<>();
        fichasDoBanco = new ArrayList<>();
        initialize();
        carregarDoBanco();
    }

    private void initialize() {
        // Configurações da janela principal
        frame = new JFrame("Gerenciador de Fichas");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Evitar fechamento direto
        frame.getContentPane().setLayout(null);

        // Intercepta o evento de fechamento
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                verificarFichasNaoSalvas();
            }
        });

        // Inicialização dos componentes
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(10, 10, 100, 25);
        frame.getContentPane().add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(120, 10, 200, 25);
        frame.getContentPane().add(txtNome);

        JLabel lblRaca = new JLabel("Raça:");
        lblRaca.setBounds(10, 40, 100, 25);
        frame.getContentPane().add(lblRaca);

        txtRaca = new JTextField();
        txtRaca.setBounds(120, 40, 200, 25);
        frame.getContentPane().add(txtRaca);

        JLabel lblClasse = new JLabel("Classe:");
        lblClasse.setBounds(10, 70, 100, 25);
        frame.getContentPane().add(lblClasse);

        txtClasse = new JTextField();
        txtClasse.setBounds(120, 70, 200, 25);
        frame.getContentPane().add(txtClasse);

        JLabel lblNivel = new JLabel("Nível:");
        lblNivel.setBounds(10, 100, 100, 25);
        frame.getContentPane().add(lblNivel);

        txtNivel = new JTextField();
        txtNivel.setBounds(120, 100, 200, 25);
        frame.getContentPane().add(txtNivel);

        JLabel lblPontosDeVida = new JLabel("Pontos de Vida:");
        lblPontosDeVida.setBounds(10, 130, 100, 25);
        frame.getContentPane().add(lblPontosDeVida);

        txtPontosDeVida = new JTextField();
        txtPontosDeVida.setBounds(120, 130, 200, 25);
        frame.getContentPane().add(txtPontosDeVida);

        JLabel lblPontosDeMana = new JLabel("Pontos de Mana:");
        lblPontosDeMana.setBounds(10, 160, 100, 25);
        frame.getContentPane().add(lblPontosDeMana);

        txtPontosDeMana = new JTextField();
        txtPontosDeMana.setBounds(120, 160, 200, 25);
        frame.getContentPane().add(txtPontosDeMana);

        JLabel lblForca = new JLabel("Força:");
        lblForca.setBounds(10, 190, 100, 25);
        frame.getContentPane().add(lblForca);

        txtForca = new JTextField();
        txtForca.setBounds(120, 190, 200, 25);
        frame.getContentPane().add(txtForca);

        JLabel lblDestreza = new JLabel("Destreza:");
        lblDestreza.setBounds(10, 220, 100, 25);
        frame.getContentPane().add(lblDestreza);

        txtDestreza = new JTextField();
        txtDestreza.setBounds(120, 220, 200, 25);
        frame.getContentPane().add(txtDestreza);

        JLabel lblConstituicao = new JLabel("Constituição:");
        lblConstituicao.setBounds(10, 250, 100, 25);
        frame.getContentPane().add(lblConstituicao);

        txtConstituicao = new JTextField();
        txtConstituicao.setBounds(120, 250, 200, 25);
        frame.getContentPane().add(txtConstituicao);

        JLabel lblInteligencia = new JLabel("Inteligência:");
        lblInteligencia.setBounds(10, 280, 100, 25);
        frame.getContentPane().add(lblInteligencia);

        txtInteligencia = new JTextField();
        txtInteligencia.setBounds(120, 280, 200, 25);
        frame.getContentPane().add(txtInteligencia);

        JLabel lblSabedoria = new JLabel("Sabedoria:");
        lblSabedoria.setBounds(10, 310, 100, 25);
        frame.getContentPane().add(lblSabedoria);

        txtSabedoria = new JTextField();
        txtSabedoria.setBounds(120, 310, 200, 25);
        frame.getContentPane().add(txtSabedoria);

        JLabel lblCarisma = new JLabel("Carisma:");
        lblCarisma.setBounds(10, 340, 100, 25);
        frame.getContentPane().add(lblCarisma);

        txtCarisma = new JTextField();
        txtCarisma.setBounds(120, 340, 200, 25);
        frame.getContentPane().add(txtCarisma);

        JLabel lblAlinhamento = new JLabel("Alinhamento:");
        lblAlinhamento.setBounds(10, 370, 100, 25);
        frame.getContentPane().add(lblAlinhamento);

        txtAlinhamento = new JTextField();
        txtAlinhamento.setBounds(120, 370, 200, 25);
        frame.getContentPane().add(txtAlinhamento);

        JLabel lblHistoria = new JLabel("História:");
        lblHistoria.setBounds(10, 400, 100, 25);
        frame.getContentPane().add(lblHistoria);

        txtHistoria = new JTextArea();
        txtHistoria.setLineWrap(true); // Habilita quebra de linha automática
        txtHistoria.setWrapStyleWord(true); // Quebra somente entre palavras (não no meio delas)

        // Adiciona o JTextArea a um JScrollPane
        JScrollPane scrollHistoria = new JScrollPane(txtHistoria);
        scrollHistoria.setBounds(120, 400, 200, 75); // Define o tamanho do scrollPane
        frame.getContentPane().add(scrollHistoria);


        JButton btnSalvarAtualizar = new JButton("Salvar/Atualizar");
        btnSalvarAtualizar.setBounds(10, 500, 150, 25);
        frame.getContentPane().add(btnSalvarAtualizar);
        btnSalvarAtualizar.addActionListener(e -> salvarOuAtualizarFicha());
        

        JButton btnDeletar = new JButton("Deletar Ficha");
        btnDeletar.setBounds(330, 500, 150, 25);
        frame.getContentPane().add(btnDeletar);
        btnDeletar.addActionListener(e -> deletarFicha());

        // Inicialização da tabela
        tabelaFichas = new JTable();
        modeloTabela = new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nome", "Raça", "Classe", "Nível" });

        tabelaFichas.setModel(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaFichas);
        scrollPane.setBounds(350, 10, 400, 450);
        frame.getContentPane().add(scrollPane);
        tabelaFichas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tabelaFichas.getSelectedRow();
                if (selectedRow >= 0) {
                    int fichaId = (int) modeloTabela.getValueAt(selectedRow, 0); // Obtém o ID da ficha selecionada
                    carregarFicha(fichaId);
                }
            }
        });

        // Botão para carregar as fichas do banco de dados
        JButton cancelar = new JButton("Cancelar");
        cancelar.setBounds(170, 500, 150, 25);
        frame.getContentPane().add(cancelar);
        cancelar.addActionListener(e -> limparCampos());
    }
    
    private void salvarOuAtualizarFicha() {
        String nome = txtNome.getText();
        String raca = txtRaca.getText();
        String classe = txtClasse.getText();
        String nivelText = txtNivel.getText();
        String pontosDeVidaText = txtPontosDeVida.getText();
        String pontosDeManaText = txtPontosDeMana.getText();
        String forcaText = txtForca.getText();
        String destrezaText = txtDestreza.getText();
        String constituicaoText = txtConstituicao.getText();
        String inteligenciaText = txtInteligencia.getText();
        String sabedoriaText = txtSabedoria.getText();
        String carismaText = txtCarisma.getText();
        String alinhamento = txtAlinhamento.getText();
        String historia = txtHistoria.getText();

        // Verificar se algum campo obrigatório está vazio
        if (nome.isEmpty() || raca.isEmpty() || classe.isEmpty() || nivelText.isEmpty() || pontosDeVidaText.isEmpty() ||
            pontosDeManaText.isEmpty() || forcaText.isEmpty() || destrezaText.isEmpty() || constituicaoText.isEmpty() ||
            inteligenciaText.isEmpty() || sabedoriaText.isEmpty() || carismaText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Por favor, preencha todos os campos obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int nivel = Integer.parseInt(nivelText);
        int pontosDeVida = Integer.parseInt(pontosDeVidaText);
        int pontosDeMana = Integer.parseInt(pontosDeManaText);
        int forca = Integer.parseInt(forcaText);
        int destreza = Integer.parseInt(destrezaText);
        int constituicao = Integer.parseInt(constituicaoText);
        int inteligencia = Integer.parseInt(inteligenciaText);
        int sabedoria = Integer.parseInt(sabedoriaText);
        int carisma = Integer.parseInt(carismaText);

        // Verifica se há uma ficha selecionada
        int selectedRow = tabelaFichas.getSelectedRow();
        if (selectedRow >= 0) {
            // Atualiza a ficha existente
            int fichaId = (int) modeloTabela.getValueAt(selectedRow, 0);
            atualizarFicha(fichaId, nome, raca, classe, nivel, pontosDeVida, pontosDeMana, forca, destreza, constituicao, inteligencia, sabedoria, carisma, alinhamento, historia);
        } else {
            // Salva uma nova ficha
            salvarNovaFicha(nome, raca, classe, nivel, pontosDeVida, pontosDeMana, forca, destreza, constituicao, inteligencia, sabedoria, carisma, alinhamento, historia);
        }

        // Recarrega os dados na tabela
        carregarDoBanco();
    }

    
    private void carregarFicha(int fichaId) {
        String query = "SELECT * FROM fichas WHERE id=?";
        try (Connection conn = Conexao.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, fichaId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    txtNome.setText(rs.getString("nome"));
                    txtRaca.setText(rs.getString("raca"));
                    txtClasse.setText(rs.getString("classe"));
                    txtNivel.setText(String.valueOf(rs.getInt("nivel")));
                    txtPontosDeVida.setText(String.valueOf(rs.getInt("pontos_de_vida")));
                    txtPontosDeMana.setText(String.valueOf(rs.getInt("pontos_de_mana")));
                    txtForca.setText(String.valueOf(rs.getInt("forca")));
                    txtDestreza.setText(String.valueOf(rs.getInt("destreza")));
                    txtConstituicao.setText(String.valueOf(rs.getInt("constituicao")));
                    txtInteligencia.setText(String.valueOf(rs.getInt("inteligencia")));
                    txtSabedoria.setText(String.valueOf(rs.getInt("sabedoria")));
                    txtCarisma.setText(String.valueOf(rs.getInt("carisma")));
                    txtAlinhamento.setText(rs.getString("alinhamento"));
                    txtHistoria.setText(rs.getString("historia"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erro ao carregar os dados da ficha!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private void atualizarFicha(int fichaId, String nome, String raca, String classe, int nivel, int pontosDeVida, int pontosDeMana, int forca, int destreza, int constituicao, int inteligencia, int sabedoria, int carisma, String alinhamento, String historia) {
        String query = "UPDATE fichas SET nome=?, raca=?, classe=?, nivel=?, pontos_de_vida=?, pontos_de_mana=?, forca=?, destreza=?, constituicao=?, inteligencia=?, sabedoria=?, carisma=?, alinhamento=?, historia=? WHERE id=?";
        try (Connection conn = Conexao.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, raca);
            pstmt.setString(3, classe);
            pstmt.setInt(4, nivel);
            pstmt.setInt(5, pontosDeVida);
            pstmt.setInt(6, pontosDeMana);
            pstmt.setInt(7, forca);
            pstmt.setInt(8, destreza);
            pstmt.setInt(9, constituicao);
            pstmt.setInt(10, inteligencia);
            pstmt.setInt(11, sabedoria);
            pstmt.setInt(12, carisma);
            pstmt.setString(13, alinhamento);
            pstmt.setString(14, historia);
            pstmt.setInt(15, fichaId);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Ficha atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erro ao atualizar a ficha!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarNovaFicha(String nome, String raca, String classe, int nivel, int pontosDeVida, int pontosDeMana, int forca, int destreza, int constituicao, int inteligencia, int sabedoria, int carisma, String alinhamento, String historia) {
        String query = "INSERT INTO fichas (nome, raca, classe, nivel, pontos_de_vida, pontos_de_mana, forca, destreza, constituicao, inteligencia, sabedoria, carisma, alinhamento, historia) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, raca);
            pstmt.setString(3, classe);
            pstmt.setInt(4, nivel);
            pstmt.setInt(5, pontosDeVida);
            pstmt.setInt(6, pontosDeMana);
            pstmt.setInt(7, forca);
            pstmt.setInt(8, destreza);
            pstmt.setInt(9, constituicao);
            pstmt.setInt(10, inteligencia);
            pstmt.setInt(11, sabedoria);
            pstmt.setInt(12, carisma);
            pstmt.setString(13, alinhamento);
            pstmt.setString(14, historia);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Nova ficha salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erro ao salvar a nova ficha!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    

    private void limparCampos() {
        txtNome.setText("");
        txtRaca.setText("");
        txtClasse.setText("");
        txtNivel.setText("");
        txtPontosDeVida.setText("");
        txtPontosDeMana.setText("");
        txtForca.setText("");
        txtDestreza.setText("");
        txtConstituicao.setText("");
        txtInteligencia.setText("");
        txtSabedoria.setText("");
        txtCarisma.setText("");
        txtAlinhamento.setText("");
        txtHistoria.setText("");
    }

    private void carregarDoBanco() {
        fichasDoBanco.clear();
        modeloTabela.setRowCount(0);

        String query = "SELECT * FROM fichas";
        try (Connection conn = Conexao.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Ficha ficha = new Ficha(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("raca"),
                    rs.getString("classe"),
                    rs.getInt("nivel"),
                    rs.getInt("pontos_de_vida"),
                    rs.getInt("pontos_de_mana"),
                    rs.getInt("forca"),
                    rs.getInt("destreza"),
                    rs.getInt("constituicao"),
                    rs.getInt("inteligencia"),
                    rs.getInt("sabedoria"),
                    rs.getInt("carisma"),
                    rs.getString("alinhamento"),
                    rs.getString("historia")
                );
                fichasDoBanco.add(ficha);
                modeloTabela.addRow(new Object[] { ficha.getId(), ficha.getNome(), ficha.getRaca(), ficha.getClasse(), ficha.getNivel() });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erro ao carregar dados do banco de dados!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private void deletarFicha() {
        int selectedRow = tabelaFichas.getSelectedRow();
        if (selectedRow >= 0) {
            int fichaId = (int) modeloTabela.getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(frame, "Você tem certeza que deseja excluir esta ficha?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String query = "DELETE FROM fichas WHERE id=?";
                try (Connection conn = Conexao.getInstance().getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setInt(1, fichaId);
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(frame, "Ficha excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparCampos();
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Erro ao excluir a ficha!", "Erro", JOptionPane.ERROR_MESSAGE);
                }

                // Recarrega os dados na tabela
                carregarDoBanco();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Por favor, selecione uma ficha para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void verificarFichasNaoSalvas() {
        if (!fichasInseridas.isEmpty()) {
            int confirm = JOptionPane.showOptionDialog(frame, "Você tem fichas não salvas. Deseja sair sem salvar?",
                    "Confirmação de Saída", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
            if (confirm == JOptionPane.YES_OPTION) {
                frame.dispose();
            }
        } else {
            frame.dispose();
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
