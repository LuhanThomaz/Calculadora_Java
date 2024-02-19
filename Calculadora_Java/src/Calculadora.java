// Importa bibliotecas necessárias para a interface gráfica e o tratamento de eventos
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Define a classe Calculadora, que é uma janela (JFrame) com funcionalidades de calculadora
public class Calculadora extends JFrame {

    // Componentes da interface e variáveis para controlar a lógica da calculadora
    private JTextField display; // Campo de texto para exibir entrada/saída
    private double valor1 = 0, resultado = 0; // Variáveis para armazenar os números para cálculos
    private String operacao = ""; // String para armazenar a operação atual (+, -, *, /)
    private boolean isOperacaoSelecionada = false; // Flag para controlar o fluxo de operações

    // Construtor que inicializa a interface da calculadora
    public Calculadora() {
        criarInterface();
    }

    // Configura a janela principal da calculadora, incluindo layout e botões
    private void criarInterface() {
        setTitle("Calculadora"); // Define o título da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define o comportamento de fechamento
        setLayout(new BorderLayout()); // Define o layout da janela

        // Configura o campo de texto do display
        display = new JTextField();
        display.setEditable(false); // Torna o display somente leitura
        display.setHorizontalAlignment(JTextField.RIGHT); // Alinha o texto à direita
        display.setFont(new Font("Arial", Font.BOLD, 24)); // Define a fonte do texto
        add(display, BorderLayout.NORTH); // Adiciona o display ao topo da janela

        // Cria e configura o painel para os botões da calculadora
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 5, 5)); // Define o layout como grade 4x4 com espaçamento
        // Define os rótulos dos botões em um array
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "C", "0", "=", "+"
        };

        // Cria botões com base nos rótulos e os adiciona ao painel
        for (String buttonText : buttons) {
            JButton button = new JButton(buttonText);
            button.setFont(new Font("Arial", Font.BOLD, 20)); // Define a fonte dos botões
            button.addActionListener(new ButtonClickListener()); // Adiciona ouvinte de ação
            panel.add(button); // Adiciona o botão ao painel
        }

        add(panel, BorderLayout.CENTER); // Adiciona o painel com botões ao centro da janela
        setSize(350, 400); // Define o tamanho da janela
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setVisible(true); // Torna a janela visível
    }

    // Classe interna que implementa ActionListener para tratar eventos de clique nos botões
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand(); // Obtém o comando (texto do botão) clicado

            // Trata entradas numéricas
            if (command.charAt(0) >= '0' && command.charAt(0) <= '9') {
                // Lógica para adicionar número ao display ou iniciar novo número
                if (isOperacaoSelecionada) {
                    // Se uma operação foi selecionada, inicia a entrada de um novo número
                    if (display.getText().matches(".*[+\\-*/]")) {
                        display.setText(display.getText() + command);
                    } else {
                        display.setText(command);
                    }
                    isOperacaoSelecionada = false;
                } else {
                    // Se nenhuma operação foi selecionada, continua a entrada do número atual
                    display.setText(display.getText() + command);
                }
            } else {
                // Trata cliques nos botões de operações e controle
                switch (command) {
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                        // Prepara para uma nova operação
                        isOperacaoSelecionada = true;
                        if (!operacao.isEmpty()) {
                            // Se já houver uma operação, calcula o resultado
                            calcular();
                            display.setText(resultado + command);
                        } else {
                            // Se for a primeira operação, armazena o número digitado
                            valor1 = Double.parseDouble(display.getText());
                            display.setText(display.getText() + command);
                        }
                        operacao = command;
                        break;
                    case "=":
                        // Calcula e exibe o resultado
                        calcular();
                        display.setText(String.valueOf(resultado));
                        operacao = "";
                        isOperacaoSelecionada = false;
                        break;
                    case "C":
                        // Limpa a calculadora para um novo cálculo
                        limpar();
                        break;
                }
            }
        }

        // Realiza o cálculo com base na operação selecionada
        private void calcular() {
            double valor2 = !display.getText().isEmpty() ? Double.parseDouble(display.getText().replaceAll(".*([+\\-*/])", "")) : 0;
            // Executa a operação matemática
            switch (operacao) {
                case "+":
                    resultado = valor1 + valor2;
                    break;
                case "-":
                    resultado = valor1 - valor2;
                    break;
                case "*":
                    resultado = valor1 * valor2;
                    break;
                case "/":
                    resultado = valor2 != 0 ? valor1 / valor2 : 0; // Evita divisão por zero
                    break;
            }
            valor1 = resultado; // Prepara para a próxima operação
        }

        // Limpa a calculadora, reiniciando todas as variáveis
        private void limpar() {
            display.setText("");
            valor1 = 0;
            resultado = 0;
            operacao = "";
            isOperacaoSelecionada = false;
        }
    }

    // Ponto de entrada do programa que inicia a calculadora
    public static void main(String[] args) {
        // Garante que a GUI seja criada na Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new Calculadora());
    }
}
