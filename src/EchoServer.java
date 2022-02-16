import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(8080)){
            System.out.println("Сервер запущен");
            Socket socket = serverSocket.accept();
            System.out.println("Клиент подключился");
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            //ожидание сообщения от клиента
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String messageFromClient = in.readUTF();
                            System.out.println(messageFromClient);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            //считывание сообщения из консоли, отправка клиенту
            while(true){
                Scanner scanner = new Scanner(System.in);
                String text = scanner.nextLine();
                out.writeUTF(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

