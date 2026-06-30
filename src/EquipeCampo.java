public class EquipeCampo {

    private int id;
    private String nome;
    private boolean temTreinamentoLV; // norma regulatoria
    private boolean ocupada; // controle de disponibilidade

    public EquipeCampo(int id, String nome, boolean temTreinamentoLV) {
        this.id = id;
        this.nome = nome;
        this.temTreinamentoLV = temTreinamentoLV;
        this.ocupada = false;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public boolean temTreinamentoLV() { return temTreinamentoLV; }
    public boolean isOcupada() { return ocupada; }
    public void setOcupada(boolean ocupada) { this.ocupada = ocupada; }
}
