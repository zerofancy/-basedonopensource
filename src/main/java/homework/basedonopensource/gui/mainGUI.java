package homework.basedonopensource.gui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import homework.basedonopensource.App;
import homework.basedonopensource.utils.StringUtils;

public class mainGUI extends JFrame implements ActionListener {
	/**
	 *
	 */
	private static final long serialVersionUID = -776594541126886214L;
	protected int xOld;
	protected int yOld;

	public mainGUI() {
		// setIconImage(Toolkit.getDefaultToolkit()
				// .getImage(mainGUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int xOnScreen = e.getXOnScreen();
				int yOnScreen = e.getYOnScreen();
				int xx = xOnScreen - xOld;
				int yy = yOnScreen - yOld;
				mainGUI.this.setLocation(xx, yy);// 设置拖拽后，窗口的位置
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xOld = e.getX();// 记录鼠标按下时的坐标
				yOld = e.getY();
			}
		});
		this.setUndecorated(true);
		this.rootPane.setBorder(new LineBorder(Color.gray, 4));
		this.setFocusableWindowState(isMaximumSizeSet());
		setTitle("商品管理系统");
//		this.setResizable(false);
		this.setBounds(100, 100, 755, 501);
		this.setVisible(true);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		InputStream btBack = this.getClass().getClassLoader().getResourceAsStream("back.png");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int b = 0;
		try {
			while ((b = btBack.read()) != -1) {
				baos.write(b);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// 背景图片
		ImageIcon background = new ImageIcon(baos.toByteArray());
		// 把背景图片显示在一个标签里面
		JLabel label = new JLabel(background);
		// 把标签的大小位置设置为图片刚好填充整个面板
		label.setBounds(0, 0, this.getWidth(), this.getHeight());
		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
		JPanel imagePanel = (JPanel) this.getContentPane();
		getContentPane().setLayout(null);

		JButton button_1 = new JButton("商品种类管理");
		// button_1.setIcon(new ImageIcon(mainGUI.class.getResource("/com/sun/java/swing/plaf/motif/icons/Inform.gif")));
		button_1.setBackground(Color.LIGHT_GRAY);
		button_1.setBounds(256, 42, 191, 35);
		getContentPane().add(button_1);
		button_1.setFont(new Font("华文行楷", Font.PLAIN, 24));

		JButton button_2 = new JButton("商品入库");
		// button_2.setIcon(
				// new ImageIcon(mainGUI.class.getResource("/com/sun/java/swing/plaf/windows/icons/Directory.gif")));
		button_2.setBackground(Color.LIGHT_GRAY);
		button_2.setBounds(320, 113, 191, 35);
		getContentPane().add(button_2);
		button_2.setFont(new Font("华文行楷", Font.PLAIN, 24));

		JButton button_3 = new JButton("商品出售");
		// button_3.setIcon(new ImageIcon(
				// mainGUI.class.getResource("/com/sun/javafx/scene/control/skin/modena/pattern-transparent.png")));
		button_3.setBackground(Color.LIGHT_GRAY);
		button_3.setBounds(368, 171, 191, 35);
		getContentPane().add(button_3);
		button_3.setFont(new Font("华文行楷", Font.PLAIN, 24));

		JButton button = new JButton("管理员管理");
		// button.setIcon(new ImageIcon(
				// mainGUI.class.getResource("/com/sun/javafx/scene/web/skin/FontBackgroundColor_16x16_JFX.png")));
		button.setBackground(Color.LIGHT_GRAY);
		button.setBounds(402, 232, 191, 35);
		getContentPane().add(button);
		button.setFont(new Font("华文行楷", Font.PLAIN, 24));

		JButton button_5 = new JButton("关于");
		// button_5.setIcon(new ImageIcon(
				// mainGUI.class.getResource("/com/sun/javafx/scene/web/skin/AlignJustified_16x16_JFX.png")));
		button_5.setBackground(Color.LIGHT_GRAY);
		button_5.setBounds(273, 376, 191, 35);
		getContentPane().add(button_5);
		button_5.setFont(new Font("华文行楷", Font.PLAIN, 24));

		JButton button_4 = new JButton("退出系统");
		// button_4.setIcon(new ImageIcon(mainGUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/close.gif")));
		button_4.setBackground(Color.LIGHT_GRAY);
		button_4.setBounds(170, 424, 191, 35);
		getContentPane().add(button_4);
		button_4.setFont(new Font("华文行楷", Font.PLAIN, 24));

		JButton button_6 = new JButton("系统设置");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new frmSettings();
			}
		});
		// button_6.setIcon(new ImageIcon(mainGUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/menu.gif")));
		button_6.setFont(new Font("华文行楷", Font.PLAIN, 24));
		button_6.setBackground(Color.LIGHT_GRAY);
		button_6.setBounds(453, 280, 191, 35);
		getContentPane().add(button_6);

		JButton button_7 = new JButton("生成报告");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringUtils.saveTxtFile(makeReport(), "report.html");
				File directory = new File("");//设定为当前文件夹 
//				try{ 
//				    System.out.println(directory.getCanonicalPath());//获取标准的路径 
//				    System.out.println(directory.getAbsolutePath());//获取绝对路径 
//				}catch(Exception e1){} 

				Desktop desktop = Desktop.getDesktop();   
				URI uri = null;
				try {
					uri = new File(directory.getCanonicalPath()+"\\report.html").toURI();
					desktop.browse(uri);
				} catch (Exception e1) {
					e1.printStackTrace();
				} //使用默认浏览器打开超链接

			}
		});
		// button_7.setIcon(new ImageIcon(mainGUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));
		button_7.setFont(new Font("华文行楷", Font.PLAIN, 24));
		button_7.setBackground(Color.LIGHT_GRAY);
		button_7.setBounds(368, 328, 191, 35);
		getContentPane().add(button_7);
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					App.con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new frmAbout();
			}
		});
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new frmAdmin();
			}
		});
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new frmSale();
			}
		});
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new frmAdd();
			}
		});
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new frmGoodKind();
			}
		});
		imagePanel.setOpaque(false);
		// 把背景图片添加到分层窗格的最底层作为背景
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));

		new frmTaskBar(this);
//      --------------------- 
//      作者：cannel_2020 
//      来源：CSDN 
//      原文：https://blog.csdn.net/cannel_2020/article/details/7063366 
//      版权声明：本文为博主原创文章，转载请附上博文链接！
	}

	public void actionPerformed(ActionEvent e) {

	}

	public String makeReport() {
		String tmpReturn = "<html><head><title>商品报告__商品管理系统</title><script src=\"https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js\">\r\n" + 
				"</script>\r\n" + 
				"<script>\r\n" + 
				"$(document).ready(function(){\r\n" + 
				"	$.get(\"https://v1.hitokoto.cn/\",function(data,status){\r\n" + 
				"		$(\"#div1\").text(data.hitokoto+'——'+data.from);\r\n" + 
				"	});\r\n" + 
				"});\r\n" + 
				"</script></head><body><h1>商品报告</h1><div id=\"div1\"><h2>使用 jQuery AJAX 修改文本内容</h2></div><table border=\"1\"><tr><th>id</th><th>名称</th><th>编号</th><th>价格</th><th>剩余数量</th></tr>";
		try {
			Statement statement = App.con.createStatement();
			String sql = "select * from goods";
			ResultSet rs = statement.executeQuery(sql);
			HashMap data=new HashMap();
			while (rs.next()) {
				HashMap tmp=new HashMap();
				tmp.put("name", rs.getString("go_name"));
				tmp.put("num", rs.getString("go_num"));
				tmp.put("price", rs.getDouble("go_price"));
				data.put(rs.getInt("id"), tmp);
			}
			for(Object i:data.keySet()) {
				tmpReturn += "<tr>";
				tmpReturn += "<td>" + i.toString() + "</td>";
				tmpReturn += "<td>" + ((HashMap)data.get(i)).get("name") + "</td>";
				tmpReturn += "<td>" + ((HashMap)data.get(i)).get("num") + "</td>";
				tmpReturn += "<td>" + ((HashMap)data.get(i)).get("price") + "</td>";
				String sql2 = "select go_qual from gohub where go_num='" + i + "'";
				ResultSet rs2 = statement.executeQuery(sql2);
				if (rs2.next()) {
					tmpReturn+="<td>"+rs2.getInt("go_qual")+"</td>";
				}else {
					tmpReturn+="<td>&nbsp;</td>";
				}
				rs2.close();
				tmpReturn += "</tr>";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tmpReturn += "</table></body></html>";
		return tmpReturn;
	}
}
