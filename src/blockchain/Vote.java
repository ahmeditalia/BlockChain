package blockchain;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.activation.FileDataSource;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import P2P.NetInfo;
import P2P.Peer;

public class Vote {

	private JFrame frame;
	private JTextField name;
	private int co = 0;
	private int N = 3;
	private Peer peer;
	public static List<String>Data=new ArrayList<>(); 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vote window1 = new Vote(8881);
					window1.getPeer().setbCastSendPort(NetInfo.receivePort);//bCast to 4444
					window1.getPeer().setbCastRecPort(NetInfo.receivePort); //lesten to 4444
					window1.getPeer().start();
					window1.frame.setVisible(true);
					window1.frame.setTitle("peer_1");
//					Vote window2 = new Vote(8882);

//					window2.getPeer().setbCastSendPort(4444);//bCast to 4444
//					window2.getPeer().setbCastRecPort(3333); //lesten to 3333
//					window2.getPeer().start();
//					window2.frame.setTitle("peer_2");
//					window2.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	protected Peer getPeer() {
		return peer;
	}

	/**
	 * Create the application.
	 */
	public Vote(int i) {
		initialize();
		try {
			peer=new Peer(i);
			//peer.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 554, 299);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ButtonGroup buttonGroup = new ButtonGroup();

		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(274, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(panel,
				GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE));

		JRadioButton radioButton_2 = new JRadioButton("Ch1", true);
		radioButton_2.setActionCommand("Ch1");
		JRadioButton radioButton_3 = new JRadioButton("Ch3");
		radioButton_3.setActionCommand("Ch3");
		JRadioButton radioButton_4 = new JRadioButton("Ch2");
		radioButton_4.setActionCommand("Ch2");
		JRadioButton radioButton = new JRadioButton("Ch5");
		radioButton.setActionCommand("Ch5");
		JRadioButton radioButton_1 = new JRadioButton("Ch4");
		radioButton_1.setActionCommand("Ch4");

		buttonGroup.add(radioButton_2);
		buttonGroup.add(radioButton_3);
		buttonGroup.add(radioButton_4);
		buttonGroup.add(radioButton);
		buttonGroup.add(radioButton_1);

		JLabel label = new JLabel("Name :");
		JButton button = new JButton("Vote");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (name.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Enter Your Name!!!");
					return;
				}
				String vote = name.getText() + " voted to " + buttonGroup.getSelection().getActionCommand() + "\n";
				try {
					peer.sendLineAll(vote);
				} catch (IOException e) {
					e.printStackTrace();
				}
				Data.add(vote);
				//FileDate.writeVote(vote);
				name.setText("");
				radioButton_2.setSelected(true);

				System.out.println("---------------------------------------------------------------");
				System.out.println(FileDate.getData());
				++co;
				if (Data.size() == N) {
					try {
						peer.sendBlockAll(BlockChain.addBlock().toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
					co = 0;
				}
			}
		});

		name = new JTextField();
		name.setColumns(10);

		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent arg0) {
				for (Block block : BlockChain.getBlockchain()) {
					System.out.println("block : \n" + block.toString());
				}
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addContainerGap().addGroup(gl_panel
								.createParallelGroup(Alignment.LEADING).addComponent(btnNewButton, Alignment.TRAILING)
								.addComponent(radioButton_1, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
								.addComponent(radioButton_4, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
								.addComponent(radioButton_2, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
								.addComponent(radioButton_3, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 244,
										Short.MAX_VALUE)
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(label, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(name, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
								.addComponent(radioButton, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)))
						.addGroup(gl_panel.createSequentialGroup().addGap(82).addComponent(button,
								GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel
				.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(label).addComponent(name,
						GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(21).addComponent(radioButton_2).addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(radioButton_4).addPreferredGap(ComponentPlacement.RELATED).addComponent(radioButton_3)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(radioButton_1)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(radioButton).addGap(4)
				.addComponent(btnNewButton).addGap(18)
				.addComponent(button, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE).addContainerGap()));
		panel.setLayout(gl_panel);
		frame.getContentPane().setLayout(groupLayout);
	}
}
