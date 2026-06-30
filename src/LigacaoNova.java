import java.time.LocalDateTime;

public class LigacaoNova extends Servico {

    private int qtdPostes;
    private boolean precisaTrafo;

    public LigacaoNova(int id, String descricao, LocalDateTime dataSolicitacao, int qtdPostes, boolean precisaTrafo) {
        super(id, descricao, dataSolicitacao);// envia os dados básicos para a superclasse Servico
        this.qtdPostes = qtdPostes;
        this.precisaTrafo = precisaTrafo;
    }

    @Override
    public boolean exigeTreinamentoLV() {
        return this.precisaTrafo; // só se precisar de trafo
    }

    @Override
    public LocalDateTime calcularPrazo() {
        int diasPrazo = 5; //prazo padrão regulatório

        if (this.qtdPostes > 0) {
            diasPrazo += (this.qtdPostes * 2); //cada poste aumenta 2 dias
        }

        if (this.precisaTrafo) {
            diasPrazo += 10; // cada trafo aumenta 10 dias
            System.out.println("Aviso: Ligação nova de ID " + getId() + " classificada como MÉDIA/ALTA complexidade (Requer instalação de trafo).");
        } else {
            System.out.println("Aviso: Ligação nova de ID " + getId() + " classificada como BAIXA complexidade.");
        }

        System.out.println("-> Prazo total da obra: " + diasPrazo + " dias a partir da data de solicitação.");

        // calcula o prazo adicionando os dias à data de solicitação
        return getDataSolicitacao().plusDays(diasPrazo);
    }

    public int getQtdPostes() {
        return qtdPostes;
    }
    public void setQtdPostes(int qtdPostes) {
        this.qtdPostes = qtdPostes;
    }

    public boolean isPrecisaTrafo() {
        return precisaTrafo;
    }
    public void setPrecisaTrafo(boolean precisaTrafo) {
        this.precisaTrafo = precisaTrafo;
    }
}
