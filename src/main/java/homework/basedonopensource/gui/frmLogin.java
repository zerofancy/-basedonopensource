package homework.basedonopensource.gui;
 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import homework.basedonopensource.App;
import java.awt.Toolkit;

public class frmLogin extends JFrame implements ActionListener {
	/**
	 *
	 */
	private static final long serialVersionUID = -5211780100084876232L;

	/**
	 * 生成md5
	 * 
	 * @param message
	 * @return
	 */
	public static String getMD5(String message) {
		String md5str = "";
		try {
			// 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
			MessageDigest md = MessageDigest.getInstance("MD5");

			// 2 将消息变成byte数组
			byte[] input = message.getBytes();

			// 3 计算后获得字节数组,这就是那128位了
			byte[] buff = md.digest(input);

			// 4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
			md5str = bytesToHex(buff);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5str;
	}

	/**
	 * 二进制转十六进制
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		StringBuffer md5str = new StringBuffer();
		// 把数组每一字节换成16进制连成md5字符串
		int digital;
		for (int i = 0; i < bytes.length; i++) {
			digital = bytes[i];

			if (digital < 0) {
				digital += 256;
			}
			if (digital < 16) {
				md5str.append("0");
			}
			md5str.append(Integer.toHexString(digital));
		}
		return md5str.toString().toUpperCase();
	}

	private JPanel jp = new JPanel();
	private JLabel[] jlArray = { new JLabel("用户名"), new JLabel("密　码"), new JLabel("") };
	private JButton[] jbArray = { new JButton("登陆"), new JButton("清空") };
	private JTextField jtxtName = new JTextField();
	private JPasswordField jtxtPassword = new JPasswordField();
	int noLoginTime=1;

	public frmLogin() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(frmLogin.class.getResource("/com/sun/javafx/scene/control/skin/modena/dialog-warning@2x.png")));
		setAlwaysOnTop(true);
		jp.setLayout(null);
		for (int i = 0; i < 2; i++) {
			jlArray[i].setBounds(30, 20 + i * 50, 80, 26);
			jbArray[i].setBounds(50 + i * 110, 130, 80, 26);
			jp.add(jlArray[i]);
			jp.add(jbArray[i]);
			jbArray[i].addActionListener(this);
		}
		jtxtName.setBounds(80, 20, 180, 30);
		jp.add(jtxtName);
		jtxtName.addActionListener(this);
		jtxtPassword.setBounds(80, 70, 180, 30);
		jp.add(jtxtPassword);
		jtxtPassword.setEchoChar('●');
		jtxtPassword.addActionListener(this);
		jlArray[2].setBounds(10, 180, 300, 30);
		jp.add(jlArray[2]);
		getContentPane().add(jp);
		this.setTitle("登陆");
		this.setResizable(false);
		this.setBounds(100, 100, 300, 250);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jtxtName) {
			jtxtPassword.requestFocus();
		} else if (e.getSource() == jbArray[1]) {
			jlArray[2].setText("");
			jtxtName.setText("");
			jtxtPassword.setText("");
			jtxtName.requestFocus();
		} else {
//			if (jtxtName.getText().equals("小明") && String.valueOf(jtxtPassword.getPassword()).equals("123")) {
//				jlArray[2].setText("登陆成功");
//			} else {
//				jlArray[2].setText("登陆错误");
//			}
			try {
				if (!App.con.isClosed()) {
					Statement statement = App.con.createStatement();
					Base64.Encoder encoder = Base64.getEncoder();
					Random ran = new Random();
					int randint = ran.nextInt(9999);
					String sql = "select * from admin where ad_name=from_base64('"
							+ encoder.encodeToString(jtxtName.getText().getBytes()) + "') and MD5(concat(ad_pwd,'"
							+ randint + "'))='" + getMD5(String.valueOf(jtxtPassword.getPassword()) + randint) + "'";
//					System.out.println(sql);
					ResultSet rs = statement.executeQuery(sql);
					if (rs.next()) {
						System.out.println("登录成功！");
						System.out.println("您的权限等级：" + rs.getInt("ad_level"));
						jlArray[2].setText("登录成功！");
						App.logined = true;
						App.level = rs.getInt("ad_level");
						this.dispose();
						new mainGUI();
					} else {
						System.out.println("登录失败！");
						jlArray[2].setText("登录失败！");
						setVisible(false);
						int midTime = noLoginTime;
						noLoginTime*=2;
						while (midTime > 0) {
							midTime--;
							long hh = midTime / 60 / 60 % 60;
							long mm = midTime / 60 % 60;
							long ss = midTime % 60;
							System.out.println("还剩" + hh + "小时" + mm + "分钟" + ss + "秒才能再次登录！");
							try {
								Thread.sleep(1000);

							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
						setVisible(true);
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

}
