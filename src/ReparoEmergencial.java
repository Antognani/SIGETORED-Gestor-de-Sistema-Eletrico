import java.time.LocalDateTime;

public class ReparoEmergencial extends Servico {

    private boolean riscoVida;
    private int nivelUrgencia;

    public ReparoEmergencial(int id, String descricao, LocalDateTime dataSolicitacao, boolean riscoDeVida, int nivelUrgencia) {
        super(id, descricao, dataSolicitacao);
        this.riscoVida = riscoDeVida;
        this.nivelUrgencia = nivelUrgencia;
    }

    @Override
    public boolean exigeTreinamentoLV() {
        return this.riscoVida; // Só exige se precisar de transformador
    }

    @Override
    public LocalDateTime calcularPrazo() {
        if (this.riscoVida || this.nivelUrgencia == 2) { // com risco de vida ou sea urgência for crítica, prazo = 2 horas
            System.out.println("ALERTA: Reparo emergencial na rede de ID " + getId() + " possui prazo máximo de 2 horas.");
            return getDataSolicitacao().plusHours(2);
        }
        else {
            System.out.println("Aviso: reparo emergencial de ID " + getId() + " possui prazo de 6 horas.");
            return getDataSolicitacao().plusHours(6);
        }
    }

    public boolean isRiscoVida() {
        return riscoVida;
    }
    public void setRiscoVida(boolean riscoVida) {
        this.riscoVida = riscoVida;
    }
    public int getNivelUrgencia() {
        return nivelUrgencia;
    }
    public void setNivelUrgencia(int nivelUrgencia) {
        this.nivelUrgencia = nivelUrgencia;
    }
}
