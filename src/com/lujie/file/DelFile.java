package com.lujie.file;

import java.io.File;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.*;

public class DelFile {
	public DelFile() {
	}

	public static void main(String[] args) {
		long a = System.currentTimeMillis();

		int ipOne = 10; // 10~17
		int ipFour = 1; // 1~255

		String ip = "";
		int count = 0;
		String absConfigPath = "F:ftpdirtestfiles7Dayconfigdata";

		// String
		// absConfigPath="/export/home0/wenwei/ftpdir/testfiles/7Day/configdata/2009-3-31";
		String delDate = "2009-3-25";

		String testBeginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(Calendar.getInstance().getTime());
		System.out.println("测试开始时间： " + testBeginTime);

		ArrayList subPathBeforDelDatelist = getSubPathBeforDelDate(
				absConfigPath, delDate);

		for (int i = 0; i < subPathBeforDelDatelist.size(); i++) {
			String tempPath = (String) subPathBeforDelDatelist.get(i);
			System.out.println("删除日期文件路径：" + tempPath);
			for (ipOne = 10; ipOne < 18; ipOne++) {
				ipFour = 1;
				for (ipFour = 1; ipFour <= 255; ipFour++) {
					ip = ipOne + ".10.10." + ipFour;
					count++;
					System.out.println("ip=" + ip);
					File file = new File(tempPath + File.separator + ip);
					if (file.exists()) {
						removeFile(file);
					}

					// 删除以日期命名的空文件夹
					if (isNullFolder(tempPath)) {
						removeFile(tempPath);
					}
				}
			}
		}

		System.out.println("count=" + count);
		String testEndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(Calendar.getInstance().getTime());
		long b = System.currentTimeMillis();
		System.out.println("测试结束时间： " + testEndTime);
		long hour = (b - a) / 3600000;
		long minute = (b - a) / (60 * 1000) - hour * 60;
		long second = (b - a) / 1000 - hour * 60 * 60 - minute * 60;
		System.out.println("总共用时： " + hour + " 小时" + minute + " 分" + second
				+ " 秒");
	}

	// 供删除备份文件调用的函数 ------begin
	// 找出小于指定日期的子路径，返回List 元素值为 C:ftpdirconfigdata2009-3-9
	public static ArrayList getSubPathBeforDelDate(String strPath,
			String strDate) {
		ArrayList tempList = new ArrayList();
		File dir = new File(strPath);
		File[] files = dir.listFiles();
		if (files == null) {
			return tempList;
		}

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				String strFileName = files[i].getAbsolutePath();
				if (pathContains(strFileName, strDate)) {
					tempList.add(files[i].getAbsolutePath());
					// System.out.println("files[i].getAbsolutePath()=" +
					// files[i].getAbsolutePath());
				}

			}
		}
		return tempList;
	}

	// 判断文件路径中是否包含的日期小于等于指定日期
	public static boolean pathContains(String path, String strDelDate) {
		if (path == null || strDelDate == null) {
			return false;
		}
		if (path == "" || strDelDate == "") {
			return false;
		}

		boolean flag = false;
		int datePos = path.lastIndexOf(File.separator);
		String strDate = path.substring(datePos + 1);
		if (convertDatetoLong(strDelDate) - convertDatetoLong(strDate) >= 0) {
			flag = true;
		}
		return flag;
	}

	// 将日期字符串转化为长整形
	public static long convertDatetoLong(String strDate) {
		long result = 0L;
		SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = dateFormate.parse(strDate);
			result = date.getTime();
		} catch (ParseException ex) {
		}
		return result;
	}

	// 删除文件
	public static void removeFile(ArrayList filelist) {
		String path = "";
		if (filelist != null && filelist.size() > 0) {
			for (int i = 0; i < filelist.size(); i++) {
				path = (String) filelist.get(i);
				removeFile(path);
			}
		}
	}

	public static void removeFile(String path) {
		removeFile(new File(path));
	}

	// 删除某一路径下所有文件，然后删除父空文件夹
	public static void removeFile(File path) {
		if (path.isDirectory()) {
			File[] child = path.listFiles();
			if (child != null && child.length != 0) {
				for (int i = 0; i < child.length; i++) {
					removeFile(child[i]);
					child[i].delete();
				}
			}
		}
		path.delete();
	}

	// 判断某一文件夹是否是空文件夹
	public static boolean isNullFolder(String path) {
		boolean flag = false;
		if (path == null || path.equals("")) {
			return false;
		}
		File filepath = new File(path);
		if (filepath.isDirectory()) {
			File[] child = filepath.listFiles();
			if (child == null || child.length == 0) {
				flag = true;
			} else {
				flag = false;
			}
		} else {
			flag = false;
		}
		return flag;
	}

	// 供删除备份文件调用的函数 ------end

}