package homework.basedonopensource.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class frmAbout extends JFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = 7052773188673441884L;

	public frmAbout() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(frmAbout.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Justify@2x.png")));
		setType(Type.POPUP);
		setAlwaysOnTop(true);
		setTitle("关于");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JTextPane txtpnCopyright = new JTextPane();
		txtpnCopyright.setEditable(false);
		txtpnCopyright.setFont(new Font("华文楷体", Font.PLAIN, 20));
		txtpnCopyright.setText("商品管理系统v1.0\r\n\r\nCopyright © 2019 刘海鑫 All Rights Reserved \r\n");
		scrollPane.setViewportView(txtpnCopyright);
		this.setVisible(true);
		this.setBounds(100, 100, 626, 429);

	}

}
