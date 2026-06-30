import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Transformador extends AtivoRede {

    private int qtdFases;
    private int cargaKva;
    private int nivelOleo;

public Transformador(int id, double latitude, double longitude, LocalDate dataInst, int qtdFases, int cargaKva, int nivelOleo) {
    super(id, latitude, longitude, dataInst);

    this.qtdFases = qtdFases;
    this.cargaKva = cargaKva;
    this.nivelOleo = nivelOleo;
}

//sobrescrição no método abstrato da classe mãe
@Override
public void avaliarDesgaste() {
    long anosUso = ChronoUnit.YEARS.between(getDataInst(), LocalDate.now());

    if (qtdFases == 3 && cargaKva >= 45 && nivelOleo < 40) {
        if (anosUso > 15) {
            System.out.println("ALERTA CRÍTICO MÁXIMO: Transformador trifásico " + getId() + " antigo (" + anosUso + " anos) com óleo abaixo de 40%. Risco iminente de explosão!");
        } else {
            System.out.println("ALERTA CRÍTICO: Transformador trifásico " + getId() + " está com óleo abaixo de 40%. Inspeção urgente necessária!");
        }
    } else if (qtdFases == 1 && cargaKva >= 15 && nivelOleo < 25) {
        if (anosUso > 15) {
            System.out.println("ALERTA CRÍTICO MÁXIMO: Transformador monofásico " + getId() + " antigo (" + anosUso + " anos) com óleo abaixo de 25%. Risco de queima!");
        } else {
            System.out.println("ALERTA CRÍTICO: Transformador monofásico " + getId() + " está com óleo abaixo de 25%. Inspeção urgente necessária!");
        }
    } else if (anosUso > 25) { //Verificação de idade
        System.out.println("AVISO PREVENTIVO: Transformador " + getId() + " operando com óleo normal (" + nivelOleo + "%), mas ultrapassou 25 anos de uso.");
    } else {
        System.out.println("Transformador " + getId() + " operando com " + nivelOleo + "% de óleo, dentro da faixa segura (" + anosUso + " anos de uso).");
    }
}

//getters e setters
public int getCargaKva() {
    return cargaKva;
}
public void setCargaKva(int cargaKva) {
    this.cargaKva = cargaKva;
}
public int getNivelOleo() {
    return nivelOleo;
}
public void setNivelOleo(int nivelOleo) {
    this.nivelOleo = nivelOleo;
}

}
