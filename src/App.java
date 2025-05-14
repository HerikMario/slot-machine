import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n======================================================");
        System.out.println("                    Slot Machine");
        System.out.println("Símbolos: [cereja] [melancia] [limão] [sino] [estrela]");
        System.out.println("======================================================\n");

        double valorAtual = 100.0;
        double aposta = 0.0;
        boolean continuar = true;
        double multiplicador;
        String[] roleta = new String[3];
        int quantosIguais;
        char resposta;

        while (continuar) {
            System.out.printf("\nValor na conta: R$ %.2f\n", valorAtual);
            System.out.print("\nDigite o valor da aposta (R$): ");
            String apostaString = scan.next().replace(',', '.');

            // Validação de entrada do usuário - Início
            try {
                aposta = Double.parseDouble(apostaString);
            } catch (NumberFormatException e) {
                System.out.println("\nFormato de aposta inválido! Tente novamente...");
                continue;
            }
            if (aposta <= 0) {
                System.out.println("\nValor de aposta inválido! Tente novamente...");
                continue;
            }

            if (aposta > valorAtual) {
                System.out.println("\nVocê não possui dinheiro suficiente pra fazer essa aposta!\n");
                continue;
            }
            // Validação de entrada do usuário - Fim
            valorAtual -= aposta;

            System.out.println("\nGirando roleta...");
            roleta = girar();
            System.out.printf("\nRoleta: %s %s %s\n", roleta[0], roleta[1], roleta[2] );

            quantosIguais = quantosIguais(roleta);

            if (quantosIguais > 0) {
                multiplicador = calcularMultiplicador(quantosIguais);
                valorAtual += (aposta + aposta * multiplicador);
                System.out.printf("\nVocê ganhou R$ %.2f!\n", (aposta + aposta * multiplicador));
                System.out.printf("\nValor na conta após aposta: R$ %.2f\n", valorAtual);
            } else {
                System.out.println("\nVocê perdeu!\n");
                // Verifica se acabou o dinheiro - Início
                if (valorAtual == 0) {
                    break;
                }
                // Verifica se acabou o dinheiro - Fim
                System.out.printf("\nValor na conta após aposta: R$ %.2f\n", valorAtual);
            }

            // Previne entradas erradas do usuário - Início
            do {
                System.out.print("\nDeseja continuar (s/n)? ");
                resposta = scan.next().charAt(0);
            } while (resposta != 's' && resposta != 'n');
            // Previne entradas erradas do usuário - Início
            if (resposta == 'n') {
                continuar = false;
            }
            System.out.print("\n======================\n");
        }
        System.out.println("\nGAME OVER! Valor total: R$ " + valorAtual + "\n");
    }

    public static double calcularMultiplicador(int quantosIguais) {
        switch (quantosIguais) {
            case 3 -> {
                return 3.0;
            }
            case 2 -> {
                return 0.5;
            }
            default -> {
                return -999.0;
            }
        }
    }

    public static int quantosIguais(String[] roleta) {
        if (roleta[0].equals(roleta[1]) && roleta[1].equals(roleta[2])) { //os 3 elementos são iguais
            return 3;
        }
        else if ((roleta[0].equals(roleta[1]) && !roleta[0].equals(roleta[2])) || (roleta[0].equals(roleta[2]) && !roleta[0].equals(roleta[1])) || (roleta[1].equals(roleta[2]) && !roleta[1].equals(roleta[0]))) { //2 elementos são iguais
            return 2;
        } 
        else { // nenhum é igual
            return 0;
        }
    }

    public static String[] girar() {
        Random rand = new Random();
        String[] roleta = new String[3];
        int sorteio;
        for (int i = 0; i < roleta.length; i++) {
            sorteio = rand.nextInt(1,6);
            roleta[i] = definirSimbolo(sorteio);
        } // [cereja] [limao] [sino] (exemplo)
        return roleta;
    }

    public static String definirSimbolo(int numeroSorteado) {
        switch (numeroSorteado) {
            case 1 -> {
                return "[cereja]";
            }
            case 2 -> {
                return "[melancia]";
            }
            case 3 -> {
                return "[limão]";
            }
            case 4 -> {
                return "[sino]";
            }
            case 5 -> {
                return "[estrela]";
            }
            default -> {
                return "Error";
            }
        }
    }
}
