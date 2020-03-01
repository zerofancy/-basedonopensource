package homework.basedonopensource;

import java.sql.Connection;
import java.sql.DriverManager;

import homework.basedonopensource.gui.frmLogin;
import homework.basedonopensource.gui.frmSettings;
import homework.basedonopensource.utils.JsonSettings;

public class App {
	public static Connection con;
	public static boolean logined=false;
	public static int level=0;
	public static JsonSettings set1;
	
	public static void main(String[] args) {
		System.out.println("欢迎使用商品管理系统！");
		
		set1=new JsonSettings("set.json");
		try {
			String driver = set1.getConfig("driver").toString();
			String url = set1.getConfig("dburl").toString();
			String user = set1.getConfig("user").toString();
			String password = set1.getConfig("password").toString();
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("数据库连接失败，请检查您的配置。");
			new frmSettings();
			return;
		} finally {
			System.out.println("数据库数据成功获取。");
		}
		new frmLogin();
	}
}
