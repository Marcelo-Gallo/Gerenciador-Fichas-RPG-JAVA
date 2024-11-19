import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;


public class FichaApp {
    private JFrame frame;
    private JTextField txtNome;
    private JTextArea txtDadosFicha;
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
        frame.setBounds(100, 100, 600, 500);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Evitar fechamento direto
        frame.getContentPane().setLayout(null);

        // Intercepta o evento de fechamento
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                verificarFichasNaoSalvas();
            }
        });

        // Label e campo de nome
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(10, 10, 100, 25);
        frame.getContentPane().add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(120, 10, 300, 25);
        frame.getContentPane().add(txtNome);

        // Label e campo de dados da ficha
        JLabel lblDados = new JLabel("Dados:");
        lblDados.setBounds(10, 50, 100, 25);
        frame.getContentPane().add(lblDados);

        txtDadosFicha = new JTextArea();
        JScrollPane scrollPaneDados = new JScrollPane(txtDadosFicha);
        scrollPaneDados.setBounds(120, 50, 300, 100);
        frame.getContentPane().add(scrollPaneDados);

        // Botão para adicionar ficha
        JButton btnAdd = new JButton("Adicionar Ficha");
        btnAdd.setBounds(10, 160, 180, 30);
        frame.getContentPane().add(btnAdd);

        // Botão para salvar fichas no banco
        JButton btnSalvar = new JButton("Salvar no Banco");
        btnSalvar.setBounds(200, 160, 180, 30);
        frame.getContentPane().add(btnSalvar);

        // Tabela para exibir fichas com status
        modeloTabela = new DefaultTableModel(new Object[]{"Nome", "Dados", "Status"}, 0);
        tabelaFichas = new JTable(modeloTabela);
        JScrollPane scrollPaneTabela = new JScrollPane(tabelaFichas);
        scrollPaneTabela.setBounds(10, 200, 560, 240);
        frame.getContentPane().add(scrollPaneTabela);

        // Ações dos botões
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFicha();
            }
        });

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarFichasNoBanco();
                carregarDoBanco();
            }
        });
    }

    private void addFicha() {
        String nome = txtNome.getText();
        String dados = txtDadosFicha.getText();

        if (!nome.isEmpty() && !dados.isEmpty()) {
            Ficha ficha = FichaFactory.createFicha(nome, dados);
            fichasInseridas.add(ficha);
            atualizarTabela();
            txtNome.setText("");
            txtDadosFicha.setText("");
        } else {
            JOptionPane.showMessageDialog(frame, "Por favor, preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0); // Limpa a tabela

        // Adiciona fichas do banco à tabela
        for (Ficha ficha : fichasDoBanco) {
            modeloTabela.addRow(new Object[]{ficha.getNome(), ficha.getFichaData(), "Salvo"});
        }

        // Adiciona fichas inseridas à tabela
        for (Ficha ficha : fichasInseridas) {
            modeloTabela.addRow(new Object[]{ficha.getNome(), ficha.getFichaData(), "Não salvo"});
        }
    }

    private void salvarFichasNoBanco() {
        if (fichasInseridas.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Não há fichas para salvar!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try (Connection conn = Conexao.getInstance().getConnection()) {
            for (Ficha ficha : fichasInseridas) {
                String sql = "INSERT INTO fichas (nome, dados) VALUES (?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, ficha.getNome());
                    stmt.setString(2, ficha.getFichaData());
                    stmt.executeUpdate();
                }
            }
            JOptionPane.showMessageDialog(frame, "Fichas salvas com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            fichasInseridas.clear();
            carregarDoBanco();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao salvar no banco: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarDoBanco() {
        try (Connection conn = Conexao.getInstance().getConnection()) {
            String sql = "SELECT nome, dados FROM fichas";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                fichasDoBanco.clear(); // Limpa fichas do banco
                while (rs.next()) {
                    String nome = rs.getString("nome");
                    String dados = rs.getString("dados");
                    Ficha ficha = FichaFactory.createFicha(nome, dados);
                    fichasDoBanco.add(ficha);
                }
                atualizarTabela();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao carregar do banco: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verificarFichasNaoSalvas() {
        if (!fichasInseridas.isEmpty()) {
            int confirmacao = JOptionPane.showConfirmDialog(
                frame,
                "Existem fichas não salvas. Deseja salvar antes de sair?",
                "Confirmação",
                JOptionPane.YES_NO_CANCEL_OPTION
            );

            if (confirmacao == JOptionPane.YES_OPTION) {
                salvarFichasNoBanco();
            } else if (confirmacao == JOptionPane.CANCEL_OPTION) {
                return; // Cancela o fechamento
            }
        }
        frame.dispose(); // Fecha a aplicação
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
