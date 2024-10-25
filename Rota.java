public class Rota {
    private String ipDestino;
    private int metrica;
    private String ipSaida;

    public Rota(String ipDestino, int metrica, String ipSaida){
        this.ipDestino = ipDestino;
        this.metrica = metrica;
        this.ipSaida = ipSaida;
    }

    
    public String getIpDestino() {return ipDestino;}
    public int getMetrica() {return metrica;}
    public String getIpSaida() {return ipSaida;}

    public void setIpDestino(String ipDestino) {this.ipDestino = ipDestino;}
    public void setMetrica(int metrica) {this.metrica = metrica;}
    public void setIpSaida(String ipSaida) {this.ipSaida = ipSaida;}

    @Override
    public String toString() {
        return "Rota{" +
                "ipDestino='" + ipDestino + '\'' +
                ", metrica=" + metrica +
                ", ipSaida='" + ipSaida + '\'' +
                '}';
    }
}
