package homework.basedonopensource.gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import homework.basedonopensource.App;

public class frmSettings extends JFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = -5524829654466644009L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	public frmSettings() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(frmSettings.class.getResource("/javax/swing/plaf/metal/icons/ocean/menu.gif")));
		setTitle("设置");
		getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNewLabel = new JLabel("驱动：");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblurl = new JLabel("数据库连接url：");
		lblurl.setFont(new Font("宋体", Font.PLAIN, 20));
		panel_1.add(lblurl);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		panel_1.add(textField_1);
		
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel label_1 = new JLabel("数据库用户名：");
		label_1.setFont(new Font("宋体", Font.PLAIN, 20));
		panel_2.add(label_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		panel_2.add(textField_2);
		
		JPanel panel_3 = new JPanel();
		getContentPane().add(panel_3);
		panel_3.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel label_2 = new JLabel("数据库密码：");
		label_2.setFont(new Font("宋体", Font.PLAIN, 20));
		panel_3.add(label_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		panel_3.add(textField_3);
		
		JPanel panel_4 = new JPanel();
		getContentPane().add(panel_4);
		panel_4.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton btnNewButton = new JButton("保存");
		btnNewButton.setIcon(new ImageIcon(frmSettings.class.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")));
		btnNewButton.setFont(new Font("华文行楷", Font.PLAIN, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				App.set1.writeConfig("driver", textField.getText());
				App.set1.writeConfig("dburl", textField_1.getText());
				App.set1.writeConfig("user", textField_2.getText());
				App.set1.writeConfig("password", textField_3.getText());
				App.set1.writeConfig("exists", 1);
				App.set1.saveConfig();
				JOptionPane.showMessageDialog(null, "设置保存成功，请重启系统！", "Tip", 0);
				frmSettings.this.dispose();
			}
		});
		panel_4.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("取消");
		btnNewButton_1.setIcon(new ImageIcon(frmSettings.class.getResource("/com/sun/java/swing/plaf/motif/icons/Error.gif")));
		btnNewButton_1.setFont(new Font("华文行楷", Font.PLAIN, 20));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSettings.this.dispose();
			}
		});
		panel_4.add(btnNewButton_1);
		this.setBounds(100, 100, 518, 371);
		setVisible(true);
		textField.setText(App.set1.getConfig("driver").toString());
		textField_1.setText(App.set1.getConfig("dburl").toString());
		textField_2.setText(App.set1.getConfig("user").toString());
		textField_3.setText(App.set1.getConfig("password").toString());
	}
}
