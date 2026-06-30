import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Poste extends AtivoRede {

    //atributos especificos
    private String tipoMaterial;
    private double esforcoSuportado;

    //construtor
    public Poste(int id, double latitude, double longitude, LocalDate dataInst, String tipoMaterial, double esforcoSuportado){
        super(id, latitude, longitude, dataInst);

        this.tipoMaterial = tipoMaterial;
        this.esforcoSuportado = esforcoSuportado;
    }

    //sobrescrição no método abstrato da classe mãe
    @Override
    public void avaliarDesgaste() {
        long anosUso = ChronoUnit.YEARS.between(getDataInst(), LocalDate.now());

        if(tipoMaterial.equalsIgnoreCase("Madeira") && anosUso >= 5) {
            System.out.println("ALERTA CRÍTICO: Poste " + getId() + " de madeira ultrapassou 5 anos. Risco de apodrecimento. Inspeção urgente necessária!");
        } else if (tipoMaterial.equalsIgnoreCase("Concreto") && anosUso >= 15) {
            System.out.println("ALERTA CRÍTICO: Poste " + getId() + " de concreto ultrapassou 15 anos. Riscos estruturais. Inspeção urgente necessária!");
        } else {
            System.out.println("Poste " + getId() + " operando dentro da vida útil segura (" + anosUso + " anos de uso).");
        }
    }

    //getters e setters
    public String getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(String tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    public double getEsforcoSuportado() {
        return esforcoSuportado;
    }

    public void setEsforcoSuportado(double esforcoSuportado) {
        this.esforcoSuportado = esforcoSuportado;
    }
}
