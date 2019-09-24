package Controller;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class RadioButton {

	private static String execucao;

	public static String getExecucao() {
		return execucao;
	}

	public static void setExecucao(String execucao) {
		RadioButton.execucao = execucao;
	}

	public static void setRobo() {

		JFrame frame = new JFrame("Selecione a execução");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel(new GridLayout(0, 1));

		ButtonGroup group = new ButtonGroup();
		JRadioButton aRadioButton = new JRadioButton("testarServicosTravada");
		JRadioButton bRadioButton = new JRadioButton("validarPortaAcesso");
		JRadioButton cRadioButton = new JRadioButton("rfsComStatusInstalando");
		JRadioButton dRadioButton = new JRadioButton("validarAcessoWaiting");
		JRadioButton eRadioButton = new JRadioButton("devolveAcesso");
		JRadioButton fRadioButton = new JRadioButton("aguardarCanceOp");
		ActionListener sliceActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton aButton = (AbstractButton) actionEvent.getSource();
				execucao = (aButton.getText());
				frame.dispose();
			}
		};

		panel.add(aRadioButton);
		group.add(aRadioButton);
		panel.add(bRadioButton);
		group.add(bRadioButton);
		panel.add(cRadioButton);
		group.add(cRadioButton);
		panel.add(dRadioButton);
		group.add(dRadioButton);
		panel.add(eRadioButton);
		group.add(eRadioButton);	
		panel.add(fRadioButton);
		group.add(fRadioButton);
		
		aRadioButton.addActionListener(sliceActionListener);
		bRadioButton.addActionListener(sliceActionListener);
		cRadioButton.addActionListener(sliceActionListener);
		dRadioButton.addActionListener(sliceActionListener);
		eRadioButton.addActionListener(sliceActionListener);
		fRadioButton.addActionListener(sliceActionListener);

		frame.add(panel);
		frame.setSize(400, 200);
		frame.setVisible(true);
	}
}