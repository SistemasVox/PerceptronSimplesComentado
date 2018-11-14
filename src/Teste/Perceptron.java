/*
 * Disciplina: A.I  Inteligência Artificial.
 * Trabalho: Software Perceptron AND. Com 3 entradas! 
 * Grupo: Marcelo Vieira, Marcelo Ferreira, Vladir Orlando, 7º Período.
 * Última atualização: v2.4 02/07/2018 18:00
 */
package Teste;

import javax.swing.JOptionPane;

public class Perceptron {

	// Variáveis locais de Perceptron
	private double teta;// Sempre o usuário pode mudar o valor
	private double alfa;// Sempre o usuário pode mudar o valor

	// Basicamente as mais importantes, e são as colunas da tabela de épocas.
	private double x1, x2, x3, t, resul_yent, y, w1, w2, w3, b, vw1, vw2, vw3, vb;

	// Auxiliares Calcular as VARIAÇÕES.
	private double w1Old;// Variação de PESO 1
	private double w2Old;// Variação de PESO 2
	private double w3Old;// Variação de PESO 3
	private double bOld;// Variação de BETA

	// Matrizes
	/*
	 * Matriz de Entrada, imutável sempre a mesma em todas as Épocas.
	 */
	final double entrada[][] = { { 1, 1, 1, 1, 1 }, { 1, 1, 0, 1, -1 },
			{ 1, 0, 1, 1, -1 }, { 1, 0, 0, 1, -1 }, { 0, 1, 1, 1, -1 },
			{ 0, 1, 0, 1, -1 }, { 0, 0, 1, 1, -1 }, { 0, 0, 0, 1, -1 } };
	/*
	 * Matriz de Variações, armazena as variações em cada época.
	 */
	double variacoes[][] = new double[8][4];

	// Contadora de épocas.
	private int cont;

	// Contrutor Padrão da Classe Perceptron, aqui se da valores padrão para criar um objeto do tipo Perceptron.
	public Perceptron(double alfa, double teta) {
		
		// Iniciar os Pesos Zerados
		w1 = 0;// PESO 1.
		w2 = 0;// PESO 2.
		w3 = 0;// PESO 3.
		b = 0;// BETA.
		
		// Ler os Valores.		
		this.alfa = alfa;// Valor recebido de outra classe.
		this.teta = teta;// Valor recebido de outra classe.
		
		// Chamando a função encontrarEpocas(); para encontrar TODAS AS ÉPOCAS.
		encontrarEpocas();
	}

	// Função PRINCIPAL, VOID, Encontrar todas as ÉPOCAS, PRINCIPAL LOOP do PROBLEMA.
	private void encontrarEpocas() {
		do {
			for (int i = 0; i < entrada.length; i++) {
				x1 = entrada[i][0];// Pegar o valor de entrada[i][0] colocar em X1.
				x2 = entrada[i][1];// Pegar o valor de entrada[i][1] colocar em X2.
				x3 = entrada[i][2];// Pegar o valor de entrada[i][2] colocar em X3.
				t = entrada[i][4];// Pegar o valor de t = entrada[i][4] colocar em T.

				resul_yent = ((x1 * w1) + (x2 * w2) + (x3 * w3) + b);// Calcular o valor de YENT.

				y = y();// Calcular valor de y usando função y();
				w1Old = w1;// Armazenar valor antigo de w1 (peso 1).
				w2Old = w2;// Armazenar valor antigo de w2 (peso 2).
				w3Old = w3;// Armazenar valor antigo de w3 (peso 3).
				bOld = b;// Armazenar valor antigo de BETA.

				w1 = wI(y, t, w1, x1);// Calcular novo valor de w1 (peso 1).
				w2 = wI(y, t, w2, x2);// Calcular novo valor de w2 (peso 2).
				w3 = wI(y, t, w3, x3);// Calcular novo valor de w3 (peso 3).
				b = b(y, t);// Calcular novo valor de BETA.
				vw1 = w1 - w1Old;// Calcular a variação de w1 (peso 1).
				vw2 = w2 - w2Old;// Calcular a variação de w2 (peso 2).
				vw3 = w3 - w3Old;// Calcular a variação de w3 (peso 3).
				vb = b - bOld;// Calcular a variação de BETA.

				// Atualizar Variações.
				variacoes[i][0] = vw1;// Atualizando VARIAÇÃO de PESO 1.
				variacoes[i][1] = vw2;// Atualizando VARIAÇÃO de PESO 2.
				variacoes[i][2] = vw3;// Atualizando VARIAÇÃO de PESO 3.
				variacoes[i][3] = vb;// Atualizando VARIAÇÃO de BETA.

			}
			// Incrementação de todas as épocas encontradas.
			cont++;
		} while (variacao());// Enquanto houver variações repetir todo o processo.
		// Método na linha 131
		
		// Exibir mensagens de quantas épocas foram encontradas. Após sair do LOOP.
		JOptionPane.showMessageDialog(null, cont + " épocas encontradas."
				+ "\nPeso 1: " + w1 + "\nPeso 2: " + w2 + "\nPeso 3: " + w3
				+ "\n***** B: " + b);
	}

	// Função y, mais difícil que ser montada no projeto.
	private int y() {
		if (resul_yent >= teta) {
			return 1;
		} else if (resul_yent <= (teta * -1)) {
			return -1;
		} else {
			return 0;
		}
	}

	// Função responsável por calcular sempre o valor de beta.
	private double b(double y, double t) {
		if (y != t) {
			return b + (alfa * t);
		} else {
			return b;
		}
	}

	// Função responsável por sempre calcular os pesos, 1, 2 e 3.
	private double wI(double y, double t, double w, double x) {
		if (y != t) {
			return (w + (alfa * x * t));
		} else {
			return w;
		}
	}

	// Função responsável por verificar se a Época contém variações ou não.
	private boolean variacao() {
		for (int i = 0; i < variacoes.length; i++) {
			for (int j = 0; j < 4; j++) {
				if (variacoes[i][j] != 0) {
					return true; // retorna verdadeiro se houve variação posição variacoes[i][j] diferente de 0.
				}
			}
		}
		return false;// retorna falso, se não houve varação ou seja todas as casas de variacoes[i][j] estão zeradas.
	}

}
