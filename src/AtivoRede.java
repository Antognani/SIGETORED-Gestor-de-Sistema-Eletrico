import java.time.LocalDate;

public abstract class AtivoRede {
    private int id;
    private double latitude;
    private double longitude;
    private LocalDate dataInst;

    //construtor
    public AtivoRede (int id, double latitude, double longitude, LocalDate dataInst) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dataInst = dataInst;
    }

    // método abstrato de avaliação de desgate, cada ativo deverá declarar a sua maneira
    public abstract void avaliarDesgaste();

    // getters e setters
    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude(){
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LocalDate getDataInst(){
        return dataInst;
    }

    public void setDataInst(LocalDate dataInst) {
        this.dataInst = dataInst;
    }

}
