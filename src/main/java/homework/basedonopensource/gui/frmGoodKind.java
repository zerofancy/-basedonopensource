package homework.basedonopensource.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import homework.basedonopensource.App;

public class frmGoodKind extends JFrame implements ActionListener {
	private JTable table;
	private DefaultTableModel defaultModel = null;
	private Vector data = createTableModelData();
	private TextField textField;
	private JSpinner spinner;
	private JSpinner spinner_1;

	private int pageNum = 1;
	private Object prePageValue=1;
	private Object prePageCountValue=10;

	public frmGoodKind(frmGoodKind pre) {
		this();
		this.setBounds(pre.getBounds());
		this.setFocusableWindowState(pre.getFocusableWindowState());
		this.prePageCountValue=pre.prePageCountValue;
		this.prePageValue=pre.prePageValue;
		this.spinner.setValue(prePageValue);
		this.spinner_1.setValue(prePageCountValue);
		
	}

	public frmGoodKind() {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(frmGoodKind.class.getResource("/com/sun/java/swing/plaf/motif/icons/Inform.gif")));
		setTitle("商品种类管理");
		this.setBounds(200, 200, 712, 500);
		this.setVisible(true);

		if (App.level < 1) {
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
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 3, 0, 0));

		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel label_1 = new JLabel("每页记录：");
		label_1.setFont(new Font("宋体", Font.PLAIN, 24));
		panel_3.add(label_1);
		
		 spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(10, 1, 100, 1));
		spinner_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if((Integer)spinner_1.getValue()<1||(Integer)spinner_1.getValue()>100) {
					spinner_1.setValue(prePageCountValue);
				}
				prePageCountValue=spinner_1.getValue();
				for(ChangeListener i:spinner.getChangeListeners()) {
				i.stateChanged(new ChangeEvent(spinner));}
			}
		});
		spinner_1.setFont(spinner_1.getFont().deriveFont(spinner_1.getFont().getSize() + 6f));
		panel_3.add(spinner_1);

		JLabel label = new JLabel("页码：");
		label.setFont(new Font("宋体", Font.PLAIN, 24));
		panel_3.add(label);

		spinner = new JSpinner();
		panel_3.add(spinner);
		spinner.setFont(spinner.getFont().deriveFont(spinner.getFont().getSize() + 6f));
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				boolean exLoop=true;//是否执行了循环，即是否有记录
				pageNum = (Integer) ((JSpinner) e.getSource()).getValue();
				try {
					Statement statement = App.con.createStatement();
					String sql = "select * from goods limit " + (pageNum-1) * (Integer)prePageCountValue + ","+prePageCountValue.toString();
					ResultSet rs = statement.executeQuery(sql);
					while (rs.next()) {
						if(exLoop) {
							while (defaultModel.getRowCount() > 0) {
								defaultModel.removeRow(0);
							}
							exLoop=false;
						}
						Vector rowData = new Vector();
						rowData.add(rs.getInt("id"));
						rowData.add(rs.getString("go_name"));
						rowData.add(rs.getString("go_num"));
						rowData.add(rs.getDouble("go_price"));
						defaultModel.insertRow(defaultModel.getRowCount(), rowData);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}finally {
					if(exLoop) {
						//如果没有数据，此次设置无效
						spinner.setValue(prePageValue);
					}
					prePageValue=spinner.getValue();
				}
			}
		});

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
				int res = JOptionPane.showConfirmDialog(null, "是否删除id=" + textField.getText(), "是否继续",
						JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					try {
						Statement statement = App.con.createStatement();
						String sql = "delete from goods where id=" + textField.getText();
						statement.execute(sql);
						dispose();
						new frmGoodKind();
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
				} else {
					return;
				}
			}
		});
		panel_2.add(button_2);

		JLabel lblId = new JLabel("id=");
		lblId.setFont(new Font("宋体", Font.PLAIN, 24));
		panel_2.add(lblId);

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
							sql = "insert into goods(go_name,go_num,go_price) values ('" + table.getValueAt(i, 1)
									+ "','" + table.getValueAt(i, 2) + "'," + table.getValueAt(i, 3) + ")";
						} else {
							sql = "update goods set go_name='" + table.getValueAt(i, 1) + "',go_num='"
									+ table.getValueAt(i, 2) + "',go_price=" + table.getValueAt(i, 3) + " where id="
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
				new frmGoodKind(frmGoodKind.this);
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
			String sql = "select * from goods limit " + (pageNum-1) * (Integer)prePageCountValue + ","+prePageCountValue.toString();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				Vector rowData = new Vector();
				rowData.add(rs.getInt("id"));
				rowData.add(rs.getString("go_name"));
				rowData.add(rs.getString("go_num"));
				rowData.add(rs.getDouble("go_price"));
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
		columnNames.add("商品名称");
		columnNames.add("商品编号");
		columnNames.add("商品单价");
		return columnNames;
	}

	private Vector createTableModelData() {
		Vector data = new Vector();
		return data;
	}

	public void actionPerformed(ActionEvent e) {

	}
}
