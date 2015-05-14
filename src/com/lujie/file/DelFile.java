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
		System.out.println("���Կ�ʼʱ�䣺 " + testBeginTime);

		ArrayList subPathBeforDelDatelist = getSubPathBeforDelDate(
				absConfigPath, delDate);

		for (int i = 0; i < subPathBeforDelDatelist.size(); i++) {
			String tempPath = (String) subPathBeforDelDatelist.get(i);
			System.out.println("ɾ�������ļ�·����" + tempPath);
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

					// ɾ�������������Ŀ��ļ���
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
		System.out.println("���Խ���ʱ�䣺 " + testEndTime);
		long hour = (b - a) / 3600000;
		long minute = (b - a) / (60 * 1000) - hour * 60;
		long second = (b - a) / 1000 - hour * 60 * 60 - minute * 60;
		System.out.println("�ܹ���ʱ�� " + hour + " Сʱ" + minute + " ��" + second
				+ " ��");
	}

	// ��ɾ�������ļ����õĺ��� ------begin
	// �ҳ�С��ָ�����ڵ���·��������List Ԫ��ֵΪ C:ftpdirconfigdata2009-3-9
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

	// �ж��ļ�·�����Ƿ����������С�ڵ���ָ������
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

	// �������ַ���ת��Ϊ������
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

	// ɾ���ļ�
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

	// ɾ��ĳһ·���������ļ���Ȼ��ɾ�������ļ���
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

	// �ж�ĳһ�ļ����Ƿ��ǿ��ļ���
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

	// ��ɾ�������ļ����õĺ��� ------end

}