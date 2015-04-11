package br.com.eduardosanti;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatServer {
	
	List<PrintWriter> escritores = new ArrayList<>();
	ServerSocket server;
	Socket socket;
	

	
	
	public ChatServer() {
		try {
			server = new ServerSocket(5000);
			while (true) {
				socket = server.accept();
				new Thread(new EscutaCliente(socket)).start();
				PrintWriter printer = new PrintWriter(socket.getOutputStream());
				escritores.add(printer);
			}
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	private void encaminharParaTodos(String texto){
		for (PrintWriter print : escritores){
			try {
				print.println(texto);
				print.flush();
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new ChatServer();
	}
	
	
	private class EscutaCliente implements Runnable{

		Scanner leitor;
		
		public EscutaCliente(Socket socket) {
			try {
				leitor = new Scanner(socket.getInputStream());
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			String textoRecebido;
			while ((textoRecebido = leitor.nextLine()) != null) {
				System.out.println(textoRecebido);
				encaminharParaTodos(textoRecebido);
				
			}
		}
		
	}

}
