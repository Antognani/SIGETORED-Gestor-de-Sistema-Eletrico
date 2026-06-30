import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GerenciadorRede gerenciador = new GerenciadorRede();
        GerenciadorArquivos arquivos = new GerenciadorArquivos();

        Queue<OrdemServico> filaOs = new LinkedList<>();
        List<OrdemServico> OSAtivas = new ArrayList<>();

        System.out.println("Iniciando o SIGATORED...");

        List<EquipeCampo> equipesCarregadas = arquivos.carregarEquipes();
        for (EquipeCampo eq : equipesCarregadas) {
            gerenciador.adicionarEquipe(eq);
        }

        boolean rodando = true;

        while (rodando) {
            System.out.println("\n==============================================");
            System.out.println("          MENU PRINCIPAL - SIGATORED          ");
            System.out.println("==============================================");
            System.out.println("1. Cadastrar nova equipe");
            System.out.println("2. Listar equipes e fila de OS");
            System.out.println("3. Atualizar status da equipe");
            System.out.println("4. Remover equipe");
            System.out.println("5. Cadastrar Serviço (Gerar OS)");
            System.out.println("6. Atualizar status da OS (Executar/Concluir)");
            System.out.println("0. Sair e salvar dados");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o ID da equipe: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Digite o nome da equipe: ");
                    String nome = scanner.nextLine();
                    System.out.print("Equipe possui treinamento para linha viva? (true/false): ");
                    boolean credencial = scanner.nextBoolean();

                    EquipeCampo novaEquipe = new EquipeCampo(id, nome, credencial);
                    gerenciador.adicionarEquipe(novaEquipe);
                    System.out.println("Equipe cadastrada com sucesso!");

                    processarFila(novaEquipe, filaOs, OSAtivas, gerenciador);
                    break;

                case 2:
                    System.out.println("\n--- Lista de equipes ---");
                    List<EquipeCampo> lista = gerenciador.getEquipesDisponiveis();
                    if (lista.isEmpty()) {
                        System.out.println("Nenhuma equipe cadastrada.");
                    } else {
                        for (EquipeCampo eq : lista) {
                            System.out.println("ID: " + eq.getId() + " | Nome: " + eq.getNome() +
                                    " | Ocupada: " + eq.isOcupada() +
                                    " | Linha viva: " + eq.temTreinamentoLV());
                        }
                    }

                    System.out.println("\n-> OS ativas (" + OSAtivas.size() + "):");
                    for (OrdemServico osAtiva : OSAtivas) {
                        System.out.println("   OS: " + osAtiva.getNumeroOS() + " | Status: " + osAtiva.getStatusAtual() + " | Equipe: " + osAtiva.getEquipeResponsavel().getNome());
                    }

                    System.out.println("\n-> Fila de espera (" + filaOs.size() + " OS aguardando equipe livre):");
                    for (OrdemServico osFila : filaOs) {
                        System.out.println("   OS: " + osFila.getNumeroOS() + " | Status: " + osFila.getStatusAtual());
                    }
                    break;

                case 3:
                    System.out.print("Digite o ID da equipe que deseja atualizar: ");
                    int idUpdate = scanner.nextInt();
                    EquipeCampo equipeParaAtualizar = null;

                    for (EquipeCampo eq : gerenciador.getEquipesDisponiveis()) {
                        if (eq.getId() == idUpdate) {
                            equipeParaAtualizar = eq;
                            break;
                        }
                    }

                    if (equipeParaAtualizar != null) {
                        System.out.print("Alterar status de ocupação para (true/false): ");
                        boolean novoStatus = scanner.nextBoolean();
                        equipeParaAtualizar.setOcupada(novoStatus);
                        System.out.println("Status atualizado com sucesso!");

                        if (!novoStatus) {
                            processarFila(equipeParaAtualizar, filaOs, OSAtivas, gerenciador);
                        }
                    } else {
                        System.out.println("Equipe não encontrada.");
                    }
                    break;

                case 4:
                    System.out.print("Digite o ID da equipe que deseja remover: ");
                    int idRemove = scanner.nextInt();
                    boolean removido = gerenciador.getEquipesDisponiveis().removeIf(eq -> eq.getId() == idRemove);

                    if (removido) {
                        System.out.println("Equipe removida com sucesso!");
                    } else {
                        System.out.println("Equipe não encontrada.");
                    }
                    break;

                case 5:
                    System.out.println("\n--- Cadastrar Novo Serviço ---");
                    System.out.print("Digite o número da nova OS: ");
                    int numOs = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Selecione o tipo de serviço:");
                    System.out.println("1 - Ligação Nova");
                    System.out.println("2 - Desligamento");
                    System.out.println("3 - Reparo Emergencial");
                    System.out.print("Opção: ");
                    int tipoServico = scanner.nextInt();
                    scanner.nextLine();

                    Servico servico = null;

                    if (tipoServico == 1) {
                        System.out.print("Quantidade de postes necessários: ");
                        int qtdPostes = scanner.nextInt();
                        System.out.print("Necessita de transformador? (true/false): ");
                        boolean precisaTrafo = scanner.nextBoolean();
                        servico = new LigacaoNova(numOs, "Ligação Nova", LocalDateTime.now(), qtdPostes, precisaTrafo);

                    } else if (tipoServico == 2) {
                        System.out.print("Motivo (Inadimplencia/Furto/Pedido Cliente): ");
                        String motivo = scanner.nextLine();

                        boolean apoioPolicial = motivo.equalsIgnoreCase("Furto") || motivo.equalsIgnoreCase("Furto de energia");

                        if (apoioPolicial) {
                            System.out.println("-> Alerta de furto de energia: apoio policial acionado automaticamente no sistema.");
                        }

                        servico = new Desligamento(numOs, "Desligamento de rede", LocalDateTime.now(), motivo, apoioPolicial);

                    } else if (tipoServico == 3) {
                        System.out.print("Descrição do problema: ");
                        String descricao = scanner.nextLine();
                        System.out.print("Há risco de vida? (true/false): ");
                        boolean risco = scanner.nextBoolean();
                        System.out.print("Nível de urgência (1-Alta, 2-Crítica): ");
                        int urgencia = scanner.nextInt();
                        servico = new ReparoEmergencial(numOs, descricao, LocalDateTime.now(), risco, urgencia);
                    } else {
                        System.out.println("Tipo de serviço inválido. Operação cancelada.");
                        break;
                    }

                    System.out.println("\n--- Avaliação de prazo e risco ---");
                    servico.calcularPrazo();

                    OrdemServico novaOs = new OrdemServico(numOs, servico);

                    EquipeCampo equipeLivre = null;
                    for (EquipeCampo eq : gerenciador.getEquipesDisponiveis()) {
                        if (!eq.isOcupada()) {
                            if (servico.exigeTreinamentoLV() && !eq.temTreinamentoLV()) {
                                continue;
                            }
                            equipeLivre = eq;
                            break;
                        }
                    }

                    if (equipeLivre != null) {
                        try {
                            gerenciador.despacharOS(novaOs, equipeLivre);
                            OSAtivas.add(novaOs);
                        } catch (EquipeIndisponivelException e) {
                            System.out.println("Erro ao despachar: " + e.getMessage());
                            filaOs.add(novaOs);
                            System.out.println("-> A OS " + numOs + " foi redirecionada para a fila de espera.");
                        }
                    } else {
                        filaOs.add(novaOs);
                        System.out.println("Todas as equipes estão ocupadas. OS " + numOs + " foi adicionada a fila de espera.");
                    }
                    break;

                case 6:
                    System.out.println("\n--- Atualizar status de OS ---");
                    if (OSAtivas.isEmpty()) {
                        System.out.println("Nenhuma OS ativa no momento.");
                        break;
                    }

                    System.out.println("OS Ativas:");
                    for (OrdemServico osAtiva : OSAtivas) {
                        System.out.println("OS: " + osAtiva.getNumeroOS() + " | Status: " + osAtiva.getStatusAtual() + " | Equipe: " + osAtiva.getEquipeResponsavel().getNome());
                    }

                    System.out.print("Digite o número da OS que deseja atualizar: ");
                    int idOs = scanner.nextInt();
                    scanner.nextLine();

                    OrdemServico osAlvo = null;
                    for (OrdemServico o : OSAtivas) {
                        if (o.getNumeroOS() == idOs) {
                            osAlvo = o;
                            break;
                        }
                    }

                    if (osAlvo != null) {
                        if (osAlvo.getStatusAtual().equals("DESPACHADA")) {
                            osAlvo.iniciarExecucao();
                        } else if (osAlvo.getStatusAtual().equals("EM_EXECUCAO")) {
                            EquipeCampo equipeLiberada = osAlvo.getEquipeResponsavel();

                            osAlvo.concluir("Serviço finalizado pelo terminal.");
                            OSAtivas.remove(osAlvo);

                            processarFila(equipeLiberada, filaOs, OSAtivas, gerenciador);
                        } else {
                            System.out.println("A OS já está concluída ou em estado inválido.");
                        }
                    } else {
                        System.out.println("OS não encontrada nas ativas.");
                    }
                    break;

                case 0:
                    System.out.println("Salvando dados nos arquivos...");
                    arquivos.salvarEquipes(gerenciador.getEquipesDisponiveis());
                    System.out.println("SIGATORED encerrado. Até logo!");
                    rodando = false;
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        scanner.close();
    }

    private static void processarFila(EquipeCampo equipeLivre, Queue<OrdemServico> filaOs, List<OrdemServico> OSAtivas, GerenciadorRede gerenciador) {
        if (filaOs.isEmpty()) return;

        OrdemServico osCompativel = null;
        for (OrdemServico os : filaOs) {
            if (!os.getServicoAssociado().exigeTreinamentoLV() || equipeLivre.temTreinamentoLV()) {
                osCompativel = os;
                break;
            }
        }

        if (osCompativel != null) {
            filaOs.remove(osCompativel);
            try {
                System.out.println("-> Puxando OS " + osCompativel.getNumeroOS() + " da fila de espera...");
                gerenciador.despacharOS(osCompativel, equipeLivre);
                OSAtivas.add(osCompativel);
            } catch (EquipeIndisponivelException e) {
                System.out.println("Erro ao despachar fila: " + e.getMessage());
                filaOs.add(osCompativel);
            }
        }
    }
}
