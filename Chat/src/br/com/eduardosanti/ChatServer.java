package br.com.eduardosanti;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer {
	
	ServerSocket server;
	Socket socket;
	

	private class EscutaCliente implements Runnable{

		Scanner leitor;
		
		public EscutaCliente(Socket socket) {
			try {
				leitor = new Scanner(socket.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			String textoRecebido;
			while ((textoRecebido = leitor.nextLine()) != null) {
				System.out.println(textoRecebido);
				
			}
		}
		
	}
	
	public ChatServer() {
		try {
			server = new ServerSocket(5000);
			while (true) {
				socket = server.accept();
				new Thread(new EscutaCliente(socket)).start();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ChatServer();
	}

}
