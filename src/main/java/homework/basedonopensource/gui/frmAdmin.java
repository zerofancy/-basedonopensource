package homework.basedonopensource.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import homework.basedonopensource.App;

import java.awt.BorderLayout;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;

public class frmAdmin extends JFrame implements ActionListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 3041306451140989724L;
	private JTable table;
	private DefaultTableModel defaultModel = null;
	private TextField textField;
	public frmAdmin(frmAdmin pre) {
		this();
		this.setBounds(pre.getBounds());
		this.setFocusableWindowState(pre.getFocusableWindowState());
	}
	public frmAdmin() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(frmAdmin.class.getResource("/com/sun/javafx/scene/web/skin/FontBackgroundColor_16x16_JFX.png")));
		setTitle("管理员管理");
		this.setBounds(200, 200, 600, 500);
		this.setVisible(true);

		if (App.level < 3) {
			this.dispose();
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		Vector columnNames = createColumnNames();
		Vector data = createTableModelData();

		defaultModel = new DefaultTableModel(data, columnNames);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		table = new JTable(defaultModel);
		table.setFont(new Font("宋体", 1, 20));
		table.setRowHeight(30);// 设置行高
		scrollPane.setViewportView(table);
		Panel panel = new Panel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		Panel panel_1 = new Panel();
		panel.add(panel_1);
		
				JButton button = new JButton("添加");
				button.setFont(new Font("宋体", Font.PLAIN, 24));
				panel_1.add(button);
				
						JButton button_1 = new JButton("提交");
						button_1.setFont(new Font("宋体", Font.PLAIN, 24));
						panel_1.add(button_1);
						
						Panel panel_2 = new Panel();
						panel.add(panel_2);
						
						JButton button_2 = new JButton("删除");
						button_2.setFont(new Font("宋体", Font.PLAIN, 24));
						button_2.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								int res=JOptionPane.showConfirmDialog(null, "是否删除id="+textField.getText(), "是否继续", JOptionPane.YES_NO_OPTION);
				                if(res==JOptionPane.YES_OPTION){ 
									try {
										Statement statement = App.con.createStatement();
					                    String sql="delete from admin where id="+textField.getText();
										statement.execute(sql);
										dispose();
										new frmAdmin();
									} catch (SQLException e1) {
										// 
										e1.printStackTrace();
									}
				                }else{
				                    return;
				                }
							}
						});
						panel_2.add(button_2);
						
					 textField = new TextField();
					 textField.setFont(new Font("Dialog", Font.PLAIN, 24));
						panel_2.add(textField);
						button_1.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								outer: for (int i = 0; i < table.getRowCount(); i++) {
									for (int i2 = 0; i2 < 4; i2++) {
//						System.out.print(table.getValueAt(i, i2)+"\t");
										if (table.getValueAt(i, i2).equals("")) {
											continue outer;
										}
									}
//					System.out.println();
									try {
										Statement statement = App.con.createStatement();
										String sql = "";
										if (table.getValueAt(i, 0).equals("add")) {
											sql = "insert into admin(ad_name,ad_pwd,ad_level) values ('" + table.getValueAt(i, 1)
													+ "','" + table.getValueAt(i, 2) + "'," + table.getValueAt(i, 3) + ")";
										} else {
											sql = "update admin set ad_name='" + table.getValueAt(i, 1) + "',ad_pwd='"
													+ table.getValueAt(i, 2) + "',ad_level=" + table.getValueAt(i, 3) + " where id="
													+ table.getValueAt(i, 0);
										}
										// System.out.println(sql);
										statement.execute(sql);

									} catch (SQLException e1) {
										// 
										e1.printStackTrace();
									}
								}
								System.out.println("命令执行完毕！");
								JOptionPane.showMessageDialog(null, "命令执行完毕", "提示", JOptionPane.INFORMATION_MESSAGE);
								dispose();
								new frmAdmin(frmAdmin.this);
							}
						});
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Vector rowData = new Vector();
						rowData.add("add");
						rowData.add("");
						rowData.add("");
						rowData.add("");
						defaultModel.insertRow(defaultModel.getRowCount(), rowData);
					}
				});

		try {
			Statement statement = App.con.createStatement();
			String sql = "select * from admin";
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				Vector rowData = new Vector();
				rowData.add(rs.getInt("id"));
				rowData.add(rs.getString("ad_name"));
				rowData.add(rs.getString("ad_pwd"));
				rowData.add(rs.getInt("ad_level"));
				defaultModel.insertRow(defaultModel.getRowCount(), rowData);

			}
		} catch (SQLException e) {
			// 
			e.printStackTrace();
		}
	}

	private Vector createColumnNames() {
		Vector columnNames = new Vector();
		columnNames.add("id");
		columnNames.add("账号");
		columnNames.add("密码");
		columnNames.add("权限");
		return columnNames;
	}

	private Vector createTableModelData() {
		Vector data = new Vector();
		return data;
	}

	public void actionPerformed(ActionEvent e) {
		// 

	}
}
