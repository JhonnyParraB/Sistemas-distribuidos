package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import Cliente.Cliente;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Resgistrar {

	private JFrame frame;
	private JTextField ubicatext;
	private JTextField tipProductext;
	private JTextField tamText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Resgistrar window = new Resgistrar();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Resgistrar() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		ubicatext = new JTextField();
		ubicatext.setBounds(175, 79, 134, 28);
		frame.getContentPane().add(ubicatext);
		ubicatext.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Registre  la informacion de su cultivo");
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 17));
		lblNewLabel.setBounds(51, 19, 369, 37);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Ubicacion:");
		lblNewLabel_1.setBounds(51, 85, 75, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblTipoDeProducto = new JLabel("Tipo de producto:");
		lblTipoDeProducto.setBounds(51, 125, 119, 16);
		frame.getContentPane().add(lblTipoDeProducto);
		
		tipProductext = new JTextField();
		tipProductext.setColumns(10);
		tipProductext.setBounds(175, 119, 134, 28);
		frame.getContentPane().add(tipProductext);
		
		JLabel lblTamao = new JLabel("Tamaño:");
		lblTamao.setBounds(51, 162, 75, 16);
		frame.getContentPane().add(lblTamao);
		
		tamText = new JTextField();
		tamText.setColumns(10);
		tamText.setBounds(175, 156, 134, 28);
		frame.getContentPane().add(tamText);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cliente cliente = new Cliente("localhost", 1234, ubicatext.getText(), tipProductext.getText(), Integer.parseInt(tamText.getText()));
				
			}
		});
		btnRegistrar.setBackground(Color.LIGHT_GRAY);
		btnRegistrar.setForeground(Color.DARK_GRAY);
		btnRegistrar.setBounds(303, 222, 117, 29);
		frame.getContentPane().add(btnRegistrar);
	}
}
