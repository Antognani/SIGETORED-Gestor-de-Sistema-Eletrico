import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class Servico {
    private int id;
    private String descricao;
    private LocalDateTime dataSolicitacao;

    public Servico(int id, String descricao, LocalDateTime dataSolicitacao){
        this.id = id;
        this.descricao = descricao;
        this.dataSolicitacao = dataSolicitacao;
    }

    public abstract LocalDateTime calcularPrazo();
    public abstract boolean exigeTreinamentoLV();

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }
    public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }
}
