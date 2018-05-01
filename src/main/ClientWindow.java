package main;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.dottorsoft.SimpleBlockChain.networking.ExecuteCommands;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClientWindow {

	
	private JFrame frame;
	private JTextArea Output = new JTextArea();
	private JTextField input;
	private ExecuteCommands client;
	private static ExecuteCommands server;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					server = new ExecuteCommands(8888);
					ClientWindow window = new ClientWindow(8889);
					window.frame.setVisible(true);
					ClientWindow window2 = new ClientWindow(8890);
					window2.frame.setVisible(true);
					ClientWindow window3 = new ClientWindow(8891);
					window3.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientWindow() {
		initialize();
	}
	public ClientWindow(int i) {
		initialize();
		client = new ExecuteCommands(i);
		client.connect("127.0.0.1", 8888);
		server.register();
		Output.setText(client.getData());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		
		input = new JTextField();
		input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if(event.getKeyCode()==KeyEvent.VK_ENTER)
				{
					client.SendAll(input.getText());
				}
			}
		});
		input.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(28)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(input, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(94, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(39)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(input, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(47, Short.MAX_VALUE))
		);
		
		scrollPane.setViewportView(Output);
		frame.getContentPane().setLayout(groupLayout);
		
		
	}
}
