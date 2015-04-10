package br.com.eduardosanti;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChatClient extends JFrame{
	
	JTextField textoParaEnviar;
	JButton botaoEnviar;
	Container envio;
	
	Socket socket;
	PrintWriter escritor;
	
	private void initElementos(){
		textoParaEnviar = new JTextField();
		botaoEnviar = new JButton("Enviar");
		botaoEnviar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				escritor.println(textoParaEnviar.getText());
				escritor.flush();
				textoParaEnviar.setText("");
				textoParaEnviar.requestFocus();
			}
		});
		envio = new JPanel();
		envio.setLayout(new BorderLayout());
		envio.add(BorderLayout.CENTER, textoParaEnviar);
		envio.add(BorderLayout.EAST, botaoEnviar);
		getContentPane().add(BorderLayout.SOUTH, envio);
		
		configRede();
	}
	
	private void configRede(){
		try {
			socket = new Socket("127.0.0.1", 5000);
			escritor = new PrintWriter(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ChatClient(){
		super("Chat");
		
		initElementos();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setVisible(true);
	}

	public static void main(String[] args) {
		new ChatClient();
	}

}
