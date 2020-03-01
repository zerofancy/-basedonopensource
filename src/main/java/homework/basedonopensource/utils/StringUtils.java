package homework.basedonopensource.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class StringUtils {

	/**
	 * 
	 * 读取TXT文件中所有内容
	 * 
	 * @param filePath
	 * @return
	 */
	public static String readTxtFile(String filePath) {
		try {
			String encoding = "GBK";
			String readText = "";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {// 读取每行
					readText += lineTxt;
				}
				read.close();
				// Log.i("readTxtFile()", "读取成功");
				return readText;
			} else {
				// Log.i("readTxtFile()", "找不到指定的文件");
				return null;
			}
		} catch (Exception e) {
			// Log.i("readTxtFile()", "读取文件内容出错");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 写入TXT文件
	 * 
	 * @param context  小说记录
	 * @param filePath 保存文件目录
	 * @return
	 */
	public static boolean saveTxtFile(String context, String filePath) {
		try {
			File f = new File(filePath);
			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			if (!f.exists()) {
				f.createNewFile();
			}
			output.write(context);
			output.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
