package br.com.eduardosanti;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient extends JFrame{
	
	JTextField textoParaEnviar;
	JButton botaoEnviar;
	Container envio;
	JTextArea textoRecebido;
	JScrollPane scroll;
	
	Socket socket;
	PrintWriter escritor;
	Scanner leitor;
	
	String nome;
	
	public ChatClient(String nome){
		super("Chat: " + nome);
		this.nome = nome;
		
		initElementos();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
	}
	
	private void initElementos(){
		textoParaEnviar = new JTextField();
		textoRecebido = new JTextArea();
		textoRecebido.setEditable(false);
		scroll = new JScrollPane(textoRecebido);
		botaoEnviar = new JButton("Enviar");
		botaoEnviar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				escritor.println(nome + ": " + textoParaEnviar.getText());
				escritor.flush();
				textoParaEnviar.setText("");
				textoParaEnviar.requestFocus();
			}
		});
		envio = new JPanel();
		envio.setLayout(new BorderLayout());
		envio.add(BorderLayout.CENTER, textoParaEnviar);
		envio.add(BorderLayout.EAST, botaoEnviar);
		getContentPane().add(BorderLayout.CENTER, scroll);
		getContentPane().add(BorderLayout.SOUTH, envio);
		
		configRede();
	}
	
	private void configRede(){
		try {
			socket = new Socket("127.0.0.1", 5000);
			escritor = new PrintWriter(socket.getOutputStream());
			leitor = new Scanner(socket.getInputStream()); //captura as informações do server
			new Thread(new EscutaServidor()).start(); //cria thread para ouvir(private class EscutaServidor) o server
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ChatClient("Santi");
		new ChatClient("Schwab");
	}

	
	
	private class EscutaServidor implements Runnable {
		
		@Override
		public void run() {
			try {
				String texto;
				while ((texto = leitor.nextLine()) != null){
					textoRecebido.append(texto + "\n");
				}
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		
	}
	
	
}
