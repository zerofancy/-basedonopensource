package homework.basedonopensource.gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import homework.basedonopensource.App;

public class frmSale extends JFrame implements ActionListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 4157325716628344401L;
	private JTextField textField;
	private	JList list;
	private	DefaultListModel  listModel1 = new DefaultListModel();
    private	JLabel label_1;
    private JSplitPane splitPane;
    private JButton button;
	public frmSale(frmSale pre) {
		this();
		this.setBounds(pre.getBounds());
		this.setFocusableWindowState(pre.getFocusableWindowState());
		}
	public frmSale() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(frmSale.class.getResource("/com/sun/javafx/scene/control/skin/caspian/pattern-transparent.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("商品出售");
		this.setBounds(100, 100, 636, 473);
		getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] {0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, 0.0};
		gbl_panel_1.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		 splitPane = new JSplitPane();
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.insets = new Insets(0, 0, 0, 5);
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 0;
		panel_1.add(splitPane, gbc_splitPane);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		textField = new JTextField();
		textField.setFont(new Font("宋体", Font.PLAIN, 30));
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) // 判断按下的键是否是回车键
				{
					Statement statement;
					String go_name="";
					String go_num=textField.getText();
					textField.setText("");

					double go_price=0;
					try {
						statement = App.con.createStatement();
						String sql = "select * from goods where go_num='" +go_num  + "'";
						ResultSet rs = statement.executeQuery(sql);
						if (rs.next()) {
							go_name=rs.getString("go_name");
							go_price=rs.getDouble("go_price");
							listModel1.addElement(go_num+"|"+go_name+"|"+go_price);
						}else {
							System.out.println("您要的商品"+go_num+"没找到。");
							return;
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}finally {
						priRecul();
					}
					
				}
			}
		});
		splitPane.setLeftComponent(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);
		
		list = new JList(listModel1);
		list.setFont(new Font("宋体", Font.PLAIN, 20));
		scrollPane.setViewportView(list);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		panel_1.add(panel, gbc_panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel label = new JLabel("合计：");
		label.setFont(new Font("宋体", Font.PLAIN, 30));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(label);
		
		 label_1 = new JLabel("0");
		label_1.setFont(new Font("宋体", Font.PLAIN, 30));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(label_1);
		
		JLabel label_4 = new JLabel("元");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setFont(new Font("宋体", Font.PLAIN, 34));
		panel.add(label_4);
		
		 button = new JButton("结算");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Statement statement = App.con.createStatement();
					String sql="";
					for(int i=0;i<listModel1.getSize();i++) {
						sql="update gohub set go_qual=go_qual-1 where go_num='"+listModel1.getElementAt(i).toString().split("\\|")[0]+"'";
						statement.addBatch(sql);
					}
					statement.executeBatch();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}finally {
					button.setEnabled(false);
					splitPane.setEnabled(false);
					list.setEnabled(false);
					textField.setEnabled(false);
					System.out.println("结算完毕，共计"+label_1.getText());
				}

				
			}
		});
		
		JLabel label_5 = new JLabel(" ");
		panel.add(label_5);
		button.setFont(new Font("宋体", Font.PLAIN, 30));
		panel.add(button);
		
		JButton button_1 = new JButton("重置");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new frmSale(frmSale.this);
			}
		});
		
		JLabel label_2 = new JLabel(" ");
		panel.add(label_2);
		
		JLabel label_3 = new JLabel(" ");
		panel.add(label_3);
		button_1.setFont(new Font("宋体", Font.PLAIN, 30));
		panel.add(button_1);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					//System.out.println(list.getSelectedIndex());
					listModel1.remove(list.getSelectedIndex());
					priRecul();
				}
			}
		});
		setVisible(true);
		/*
		listModel1.addElement("test");
		listModel1.addElement("test2");
		listModel1.addElement("test3");
		*/
	}

	private void priRecul() {
		DecimalFormat df = new DecimalFormat("#.00");
		double sum=0;
		for(int i=0;i<listModel1.getSize();i++) {
			sum+=Double.parseDouble(listModel1.getElementAt(i).toString().split("\\|")[2]);
		}
		label_1.setText(df.format(sum)+"");
	}
	
	public void actionPerformed(ActionEvent e) {
	}
}
