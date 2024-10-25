import java.io.IOException;
import java.net.*;

public class ProtocoloUDP{
    private DatagramSocket socket;
    private int porta;

    public ProtocoloUDP(int porta) throws SocketException {
        this.porta = porta;
        this.socket = new DatagramSocket(porta);
    }

    //envio de pacotes
    public void enviar(String mensagem, String ipDestino) throws Exception {
        byte[] buffer = mensagem.getBytes();
        InetAddress ip = InetAddress.getByName(ipDestino);
        DatagramPacket pacote = new DatagramPacket(buffer, buffer.length, ip, porta);
        socket.send(pacote);
    }

    //recebimento de pacotes
    public String receber() throws IOException{
        byte[] buffer = new byte[1024];
        DatagramPacket pacote = new DatagramPacket(buffer, buffer.length);
        socket.receive(pacote);

        return new String(pacote.getData(), 0, pacote.getLength()); 
    }

    //fecha o socket
    public void fechaSocket(){
        socket.close();
    }
}