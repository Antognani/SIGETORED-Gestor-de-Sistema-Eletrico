import java.time.LocalDateTime;

public class Desligamento extends Servico {

    private String motivo;
    private boolean requerApoioPolicial;

    public Desligamento(int id, String descricao, LocalDateTime dataSolicitacao, String motivo, boolean requerApoioPolicial) {
        super(id, descricao, dataSolicitacao);
        this.motivo = motivo;
        this.requerApoioPolicial = requerApoioPolicial;
    }

    @Override
    public boolean exigeTreinamentoLV() {
        return false; // Só exige se precisar de transformador
    }

    @Override
    public LocalDateTime calcularPrazo() {

        // furto de energia
        if (this.motivo.equalsIgnoreCase("Furto") || this.requerApoioPolicial) {
            System.out.println("ALERTA: Desligamento de ID " + getId() + " por suspeita de furto de energia.");
            if (this.requerApoioPolicial) {
                System.out.println("-> Necessário acionar a Polícia Militar para acompanhamento da execução.");
            }
            System.out.println("-> Prazo máximo: 12 horas para execução.");
            return getDataSolicitacao().plusHours(12);
        }

        // inandimplência
        else if (this.motivo.equalsIgnoreCase("Inadimplência")) {
            System.out.println("Aviso: Desligamento de ID " + getId() + " por corte de inadimplência. Prazo: 3 dias.");
            return getDataSolicitacao().plusDays(3);
        }

        // pedido do cliente
        else {
            System.out.println("Aviso: Desligamento de ID " + getId() + " a pedido do cliente. Prazo: 5 dias.");
            return getDataSolicitacao().plusDays(5);
        }
    }

    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public boolean isRequerApoioPolicial() {
        return requerApoioPolicial;
    }
    public void setRequerApoioPolicial(boolean requerApoioPolicial) {
        this.requerApoioPolicial = requerApoioPolicial;
    }
}
