package lamego.lootzone.entity;

public class Comprador extends Usuario{
    private float credito;

    //GETTERS N SETTERS
    public float getCredito() {
        return credito;
    }

    public void setCredito(float credito) {
        this.credito = credito;
    }
}
