//用于商品入库
package homework.basedonopensource.gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import homework.basedonopensource.App;

public class frmAdd extends JFrame implements ActionListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 6018504461238186199L;
	private JTextField textField;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private JSpinner spinner;
	private JCheckBox checkBox;

	public frmAdd(frmAdd pre) {
		this();
		this.setBounds(pre.getBounds());
		this.setFocusableWindowState(pre.getFocusableWindowState());
	}

	public frmAdd() {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(frmAdd.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("商品入库");
		getContentPane().setLayout(new GridLayout(0, 1, 0, 0));

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		getContentPane().add(splitPane_1);

		JPanel panel = new JPanel();
		splitPane_1.setLeftComponent(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));

		checkBox = new JCheckBox("默认数量");
		checkBox.setFont(new Font("宋体", Font.PLAIN, 24));
		checkBox.setSelected(true);
		panel.add(checkBox);

		spinner = new JSpinner();
		spinner.setFont(new Font("宋体", Font.PLAIN, 24));
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		panel.add(spinner);

		JSplitPane splitPane = new JSplitPane();
		splitPane_1.setRightComponent(splitPane);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) // 判断按下的键是否是回车键
				{
					int addNum = 1;
					if (!checkBox.isSelected()) {
						addNum = (Integer) spinner.getValue();
					}
					String adName = "";
					try {
						Statement statement = App.con.createStatement();
						String sql = "select * from goods where go_num='" + textField.getText() + "'";
						ResultSet rs = statement.executeQuery(sql);
						if (rs.next()) {
							adName = rs.getString("go_name");
							String sql2 = "select * from gohub where go_num='" + textField.getText() + "'";
							ResultSet rs2 = statement.executeQuery(sql2);
							String sql3 = "";
							if (rs2.next()) {
								sql3 = "update gohub set go_qual=go_qual+" + addNum + " where go_num='"
										+ textField.getText() + "'";
							} else {
								sql3 = "insert into gohub(go_num,go_qual)values('" + textField.getText() + "'," + addNum
										+ ")";
							}
							statement.execute(sql3);
						} else {
							textArea.append("找不到编号" + textField.getText() + "对应的商品。\n");
							return;
						}
					} catch (SQLException e1) {
						// 
						e1.printStackTrace();
					} finally {
						textField.setText("");
					}
					textArea.append("添加`" + adName + "`" + addNum + "件。\n");

				}
			}
		});
		textField.setFont(new Font("宋体", Font.PLAIN, 24));
		splitPane.setLeftComponent(textField);
		textField.setColumns(10);

		scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 24));

		textArea.setFocusable(true);

		setVisible(true);
		this.setBounds(100, 100, 602, 481);
	}

	public void actionPerformed(ActionEvent e) {
		// 

	}
}
