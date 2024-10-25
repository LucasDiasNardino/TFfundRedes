import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Roteador {
    private String ip; // do proprio roteador
    private List<Rota> tabelaRoteamento; //lista de rotas - será populada com o arquivo de configuração

    public Roteador(String ip){
        this.ip = ip;
        this.tabelaRoteamento = new ArrayList<>();
    }

    //adiciona um roteador na tabela de roteamento
    public void adicionarRota(String ipDestino, int metrica, String ipSaida){
        Rota novarota = new Rota(ipDestino, metrica, ipSaida);
        tabelaRoteamento.add(novarota);
    }

    //imprime tabela de roteamento
    public void imprimirTabela(){
        System.out.println("Tabela de Roteamento do Roteador " + ip);
        for (Rota rota : tabelaRoteamento) {
            System.out.println(rota);
        }
    }

    //atualiza tabela com informações recebidas dos vizinhos
    public void atualizarTabela(String ipDestino, int metricaRecebida, String ipSaidaVizinho){
        boolean atualizada = false;

        for (Rota rota : tabelaRoteamento) {
            if(rota.getIpDestino().equals(ipDestino)){
                if(rota.getMetrica() > metricaRecebida + 1){
                    rota.setMetrica(metricaRecebida + 1);
                    rota.setIpSaida(ipSaidaVizinho);
                    atualizada = true;
                }
                break;
            }
        }

        if (!atualizada) {
            adicionarRota(ipDestino, metricaRecebida, ipSaidaVizinho);
        }
    }

    //envia mensagem para o roteador de destino
    public void enviarMensagemTexto(DatagramSocket socket, String origem, String destino, String mensagem,String proximoRoteador) throws IOException{
        String messagemFormatada = "&" + origem + "%" + destino + "%" + mensagem;
        byte[] buffer = messagemFormatada.getBytes();
        InetAddress ip = InetAddress.getByName(proximoRoteador);
        DatagramPacket pacote = new DatagramPacket(buffer, buffer.length, ip, 19000);
        socket.send(pacote);
    }

    //recebe (e redireciona) mensagem de outro roteador
    public void receberMensagemTexto(DatagramSocket socket, List<Rota> tabelaRoteamento) throws IOException{
        byte[] buffer = new byte[1024];
        DatagramPacket pacote = new DatagramPacket(buffer, buffer.length);
        socket.receive(pacote);

        String mensagemRecebida = new String(pacote.getData(), 0, pacote.getLength());

        //separa partes da mensagem
        if(mensagemRecebida.startsWith("&")){
            String[] partes = mensagemRecebida.split("%");
            
            String origem = partes[0].substring(1);
            String destino = partes[1];
            String mensagem = partes[2];

            if(this.ip.equals(destino)){
                System.out.println("Mensagem recebida de " + origem + ": " + mensagem);
                //COMPLETAR
            }
        }
    }    
}
