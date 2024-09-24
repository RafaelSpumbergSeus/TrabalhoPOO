package aplicacao;

import dados.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ACMERobots extends JFrame {

    private List<Robo> robos = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    private List<Locacao> locacoes = new ArrayList<>();
    private List<Locacao> filaPendentes = new ArrayList<>();

    public ACMERobots() {
        setTitle("ACMERobots - Sistema de Locação");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(11, 1));

        JButton cadastrarRoboButton = new JButton("Cadastrar Novo Robo");
        JButton cadastrarClienteButton = new JButton("Cadastrar Novo Cliente");
        JButton cadastrarLocacaoButton = new JButton("Cadastrar Nova Locação");
        JButton processarLocacoesButton = new JButton("Processar Locações");
        JButton mostrarRelatorioButton = new JButton("Mostrar Relatório Geral");
        JButton consultarLocacoesButton = new JButton("Consultar Todas as Locações");
        JButton alterarSituacaoLocacaoButton = new JButton("Alterar Situação de Locação");
        JButton carregarDadosButton = new JButton("Carregar Dados Iniciais");
        JButton salvarDadosButton = new JButton("Salvar Dados");
        JButton finalizarSistemaButton = new JButton("Finalizar Sistema");

        cadastrarRoboButton.addActionListener(e -> cadastrarRobo());
        cadastrarClienteButton.addActionListener(e -> cadastrarCliente());
        cadastrarLocacaoButton.addActionListener(e -> cadastrarLocacao());
        processarLocacoesButton.addActionListener(e -> processarLocacoes());
        mostrarRelatorioButton.addActionListener(e -> mostrarRelatorio());
        consultarLocacoesButton.addActionListener(e -> consultarLocacoes());
        alterarSituacaoLocacaoButton.addActionListener(e -> alterarSituacaoLocacao());
        carregarDadosButton.addActionListener(e -> carregarDados());
        salvarDadosButton.addActionListener(e -> salvarDados());
        finalizarSistemaButton.addActionListener(e -> finalizarSistema());

        add(cadastrarRoboButton);
        add(cadastrarClienteButton);
        add(cadastrarLocacaoButton);
        add(processarLocacoesButton);
        add(mostrarRelatorioButton);
        add(consultarLocacoesButton);
        add(alterarSituacaoLocacaoButton);
        add(carregarDadosButton);
        add(salvarDadosButton);
        add(finalizarSistemaButton);

        setVisible(true);
    }

    private void cadastrarRobo() {
        try {
            JTextField idField = new JTextField();
            JTextField modeloField = new JTextField();
            String[] tipos = {"Doméstico", "Industrial", "Agrícola"};
            JComboBox<String> tipoCombo = new JComboBox<>(tipos);
            JTextField nivelSetorAreaField = new JTextField();
            JTextField usoField = new JTextField();

            Object[] message = {
                    "ID:", idField,
                    "Modelo:", modeloField,
                    "Tipo:", tipoCombo,
                    "Nível/Setor/Área:", nivelSetorAreaField,
                    "Uso:", usoField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Cadastrar Robo", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                int id = Integer.parseInt(idField.getText());
                String modelo = modeloField.getText();
                String tipo = (String) tipoCombo.getSelectedItem();
                String nivelSetorArea = nivelSetorAreaField.getText();

                Robo robo = null;
                switch (tipo) {
                    case "Doméstico":
                        int nivel = Integer.parseInt(nivelSetorArea);
                        robo = new Domestico(id, modelo, nivel);
                        break;
                    case "Industrial":
                        robo = new Industrial(id, modelo, nivelSetorArea);
                        break;
                    case "Agrícola":
                        double area = Double.parseDouble(nivelSetorArea);
                        String uso = usoField.getText();
                        robo = new Agricola(id, modelo, area, uso);
                        break;
                }

                if (robo != null) {
                    robos.add(robo);
                    robos.sort(Comparator.comparingInt(Robo::getId));
                    JOptionPane.showMessageDialog(null, "Robo cadastrado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar robo!");
                }
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar robo!");
        }
    }

    private void cadastrarCliente() {
        try {
            JTextField codigoField = new JTextField();
            JTextField nomeField = new JTextField();
            String[] tipos = {"Individual", "Empresarial"};
            JComboBox<String> tipoCombo = new JComboBox<>(tipos);
            JTextField cpfAnoField = new JTextField();

            Object[] message = {
                    "Código:", codigoField,
                    "Nome:", nomeField,
                    "Tipo:", tipoCombo,
                    "CPF/Ano de Fundação:", cpfAnoField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Cadastrar Cliente", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                int codigo = Integer.parseInt(codigoField.getText());
                String nome = nomeField.getText();
                String tipo = (String) tipoCombo.getSelectedItem();
                String cpfAno = cpfAnoField.getText();

                Cliente cliente = null;
                switch (tipo) {
                    case "Individual":
                        cliente = new Individual(codigo, nome, cpfAno);
                        break;
                    case "Empresarial":
                        int anoFundacao = Integer.parseInt(cpfAno);
                        cliente = new Empresarial(codigo, nome, anoFundacao);
                        break;
                }

                if (cliente != null) {
                    clientes.add(cliente);
                    clientes.sort(Comparator.comparingInt(Cliente::getCodigo));
                    JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar cliente!");
                }
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar cliente!");
        }
    }

    private void cadastrarLocacao() {
       try {
           if (clientes.isEmpty()) {
               JOptionPane.showMessageDialog(null, "Nenhum cliente cadastrado.");
               return;
           }

           if (robos.isEmpty()) {
               JOptionPane.showMessageDialog(null, "Nenhum robô cadastrado.");
               return;
           }

           String[] clienteNomes = clientes.stream().map(Cliente::getNome).toArray(String[]::new);
           JComboBox<String> clienteCombo = new JComboBox<>(clienteNomes);
           JTextField numeroField = new JTextField();
           JTextField dataInicioField = new JTextField();
           JTextField dataFimField = new JTextField();

           Object[] message = {
                   "Cliente:", clienteCombo,
                   "Número:", numeroField,
                   "Data de Início (dd/MM/yyyy):", dataInicioField,
                   "Data de Fim (dd/MM/yyyy):", dataFimField
           };

           int option = JOptionPane.showConfirmDialog(null, message, "Cadastrar Locação", JOptionPane.OK_CANCEL_OPTION);
           if (option == JOptionPane.OK_OPTION) {
               Cliente cliente = clientes.get(clienteCombo.getSelectedIndex());
               int numero = Integer.parseInt(numeroField.getText());
               LocalDate dataInicio = LocalDate.parse(dataInicioField.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
               LocalDate dataFim = LocalDate.parse(dataFimField.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

               Locacao locacao = new Locacao(numero, cliente, dataInicio, dataFim);

               String[] roboIds = robos.stream().map(robo -> String.valueOf(robo.getId())).toArray(String[]::new);
               JList<String> roboList = new JList<>(roboIds);
               roboList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
               JScrollPane roboScrollPane = new JScrollPane(roboList);

               Object[] roboMessage = {
                       "Selecione os Robôs:", roboScrollPane
               };

               int roboOption = JOptionPane.showConfirmDialog(null, roboMessage, "Selecionar Robôs", JOptionPane.OK_CANCEL_OPTION);
               if (roboOption == JOptionPane.OK_OPTION) {
                   int[] selectedIndices = roboList.getSelectedIndices();
                   for (int index : selectedIndices) {
                       locacao.adicionarRobo(robos.get(index));
                   }

                   filaPendentes.add(locacao);
                   JOptionPane.showMessageDialog(null, "Locação cadastrada com sucesso!");
               }
           }
       }
       catch (Exception e)
       {
           JOptionPane.showMessageDialog(null, "Erro ao cadastrar a locação!");
       }
    }

    private void processarLocacoes() {
        if (filaPendentes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há locações pendentes.");
            return;
        }

        List<Locacao> locacoesProcessadas = new ArrayList<>();

        for (Locacao locacao : filaPendentes) {
            boolean todosDisponiveis = true;
            for (Robo robo : locacao.getRobos()) {
                if (!robo.isDisponivel()) {
                    todosDisponiveis = false;
                    break;
                }
            }

            if (todosDisponiveis) {
                locacao.setSituacao(Locacao.Status.EXECUTANDO);
                for (Robo robo : locacao.getRobos()) {
                    robo.setDisponivel(false);
                }
                locacoesProcessadas.add(locacao);
            }
        }

        locacoes.addAll(locacoesProcessadas);
        filaPendentes.removeAll(locacoesProcessadas);

        JOptionPane.showMessageDialog(null, "Locações processadas com sucesso!");
    }

    private void mostrarRelatorio() {
        StringBuilder relatorio = new StringBuilder();

        relatorio.append("Robôs:\n");
        for (Robo robo : robos) {
            relatorio.append(robo).append("\n");
        }

        relatorio.append("\nClientes:\n");
        for (Cliente cliente : clientes) {
            relatorio.append(cliente).append("\n");
        }

        relatorio.append("\nLocações:\n");
        for (Locacao locacao : locacoes) {
            relatorio.append(locacao).append("\n");
        }

        JTextArea textArea = new JTextArea(relatorio.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new Dimension(350, 400));

        JOptionPane.showMessageDialog(null, scrollPane, "Relatório Geral", JOptionPane.INFORMATION_MESSAGE);
    }

    private void consultarLocacoes() {
        if (locacoes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há locações cadastradas.");
            return;
        }

        StringBuilder relatorio = new StringBuilder();
        for (Locacao locacao : locacoes) {
            relatorio.append(locacao).append("\n");
            relatorio.append("Cliente: ").append(locacao.getCliente()).append("\n");
            relatorio.append("Robôs:\n");
            for (Robo robo : locacao.getRobos()) {
                relatorio.append(robo).append("\n");
            }
            relatorio.append("Valor Final: ").append(locacao.calculaValorFinal()).append("\n\n");
        }

        JTextArea textArea = new JTextArea(relatorio.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new Dimension(350, 400));

        JOptionPane.showMessageDialog(null, scrollPane, "Consultas de Locações", JOptionPane.INFORMATION_MESSAGE);
    }

    private void alterarSituacaoLocacao() {
        if (locacoes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há locações cadastradas.");
            return;
        }

        String[] locacaoNumeros = locacoes.stream().map(locacao -> String.valueOf(locacao.getNumero())).toArray(String[]::new);
        JComboBox<String> locacaoCombo = new JComboBox<>(locacaoNumeros);

        JTextField situacaoField = new JTextField();

        Object[] message = {
                "Número da Locação:", locacaoCombo,
                "Nova Situação:", situacaoField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Alterar Situação de Locação", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int numero = Integer.parseInt((String) locacaoCombo.getSelectedItem());
            String novaSituacao = situacaoField.getText();

            Locacao locacao = locacoes.stream().filter(l -> l.getNumero() == numero).findFirst().orElse(null);
            if (locacao != null) {
                try {
                    Locacao.Status status = Locacao.Status.valueOf(novaSituacao.toUpperCase());
                    locacao.setSituacao(status);
                    JOptionPane.showMessageDialog(null, "Situação alterada com sucesso!");
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, "Situação inválida.");
                }
            }
        }
    }


    private void carregarDados() {
        String nomeArquivo = JOptionPane.showInputDialog("Nome do arquivo (sem extensão):");
        if (nomeArquivo != null && !nomeArquivo.trim().isEmpty()) {
            String caminhoArquivo = nomeArquivo + ".xml";

            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new File(caminhoArquivo));

                // Processar o documento XML
                Element root = document.getDocumentElement();

                // Exemplo de processamento: ler elementos e atributos do XML
                NodeList nodeList = root.getElementsByTagName("elemento");
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element elemento = (Element) nodeList.item(i);
                    // Processar cada elemento conforme necessário
                }

                JOptionPane.showMessageDialog(null, "Dados carregados com sucesso.");
            } catch (ParserConfigurationException | SAXException | IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo XML: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nome do arquivo inválido.");
        }
    }

    private void salvarDados() {
        String nomeArquivo = JOptionPane.showInputDialog("Nome do arquivo (sem extensão):");
        if (nomeArquivo != null && !nomeArquivo.trim().isEmpty()) {
            String caminhoArquivo = nomeArquivo + ".xml";

            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.newDocument();

                // Criar elementos e estrutura do documento XML
                Element rootElement = document.createElement("root");
                document.appendChild(rootElement);

                // Exemplo de adição de elementos ao XML
                Element elemento = document.createElement("elemento");
                elemento.setAttribute("atributo", "valor");
                rootElement.appendChild(elemento);

                // Escrever o documento XML no arquivo
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(new File(caminhoArquivo));
                transformer.transform(source, result);

                JOptionPane.showMessageDialog(null, "Dados salvos com sucesso.");
            } catch (ParserConfigurationException | TransformerException e) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar o arquivo XML: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nome do arquivo inválido.");
        }
    }

    private void finalizarSistema() {
        System.exit(0);
    }
}