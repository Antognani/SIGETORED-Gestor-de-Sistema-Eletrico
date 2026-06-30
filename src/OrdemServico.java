import java.time.LocalDateTime;

public class OrdemServico {

    private int numeroOS;
    private Servico servicoAssociado;
    private EquipeCampo equipeResponsavel;
    private String statusAtual; // ABERTA, DESPACHADA, EM EXECUCAO, CONCLUIDA
    private String relatorioTecnico;
    private LocalDateTime dataAbertura;

    public OrdemServico(int numeroOS, Servico servicoAssociado) {
        this.numeroOS = numeroOS;
        this.servicoAssociado = servicoAssociado;
        this.statusAtual = "ABERTA";
        this.dataAbertura = LocalDateTime.now();
    }
    
    //regras de transição do estado da OS
    public void despachar(EquipeCampo equipe) {
        if (!this.statusAtual.equals("ABERTA")) {
            System.out.println("Erro: só é possível despachar uma OS que esteja ABERTA.");
            return;
        }

        this.equipeResponsavel = equipe;
        this.equipeResponsavel.setOcupada(true);
        this.statusAtual = "DESPACHADA";
        System.out.println("OS " + numeroOS + " DESPACHADA para a equipe: " + equipe.getNome());
    }

    public void iniciarExecucao() {
        if (!this.statusAtual.equals("DESPACHADA")) {
            System.out.println("Erro: a OS precisa estar DESPACHADA para iniciar execução.");
            return;
        }

        this.statusAtual = "EM EXECUCAO";
        System.out.println("OS " + numeroOS + " está agora EM EXECUÇÃO no local.");
    }

    public void concluir(String relatorio) {
        if (!this.statusAtual.equals("EM EXECUCAO")) {
            System.out.println("Erro: não é possível concluir uma OS que não está em execução.");
            return;
        }
        if (relatorio == null || relatorio.trim().isEmpty()) {
            System.out.println("Erro: transição negada. Um relatório técnico é obrigatório para concluir a OS.");
            return;
        }

        this.relatorioTecnico = relatorio;
        this.statusAtual = "CONCLUIDA";
        this.equipeResponsavel.setOcupada(false); // libera a equipe
        System.out.println("OS " + numeroOS + " CONCLUÍDA com sucesso. Equipe liberada.");
    }

    public int getNumeroOS() { return numeroOS; }
    public String getStatusAtual() { return statusAtual; }
    public Servico getServicoAssociado() { return servicoAssociado; }
    public EquipeCampo getEquipeResponsavel() { return equipeResponsavel; }
    public String getRelatorioTecnico() { return relatorioTecnico; }
}
