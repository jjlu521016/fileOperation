package com.lujie.file;

import java.io.*;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class CreateFileTest {
	public CreateFileTest() {
	}

	public static void main(String[] args) throws IOException, Exception {
		int ipOne = 10; // 10~17
		int ipFour = 1; // 1~255
		String testHomeDir = "C:ftpdirtestfiles";
		String date = "";
		String ip = "";

		String testBeginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(Calendar.getInstance().getTime());
		System.out.println("测试开始时间： " + testBeginTime);

		File resourceFile = new File(testHomeDir + "basicfile.txt");
		for (int i = -365; i < 0; i++) {
			GregorianCalendar calendar;
			calendar = new GregorianCalendar();
			calendar.add(GregorianCalendar.DATE, i);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			date = year + "-" + month + "-" + day;

			for (ipOne = 10; ipOne <= 17; ipOne++) {
				ipFour = 1;
				for (ipFour = 1; ipFour <= 255; ipFour++) {
					ip = ipOne + ".10.10." + ipFour;
					StringBuffer filepath = new StringBuffer("");
					filepath.append(testHomeDir + date + "" + ip + "STA");
					File file = new File(filepath.toString());
					if (!file.exists()) {
						file.mkdirs();
					}

					// C:ftpdirtestfiles2008-3-3110.10.10.1STAa.txt
					File objFile = new File(filepath.toString() + "a.txt");
					objFile.createNewFile();
					CopyFile(resourceFile, objFile);

					// 操作STAvt010目录 STAvt010
					String vt010Path = filepath.toString() + "V0100";
					File vt010File = new File(vt010Path);
					if (!vt010File.exists()) {
						vt010File.mkdirs();
					}

					objFile = null;
					for (int j = 1; j < 11; j++) {
						objFile = new File(vt010File.getAbsolutePath() + "" + j
								+ "_a.txt");
						objFile.createNewFile();
						CopyFile(resourceFile, objFile);
					}
				}
			}
		}

		String testEndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(Calendar.getInstance().getTime());
		System.out.println("测试结束时间： " + testEndTime);
	}

	public static void CopyFile(File in, File out) throws Exception {
		FileInputStream fis = new FileInputStream(in);
		FileOutputStream fos = new FileOutputStream(out);
		byte[] buf = new byte[1024];
		int i = 0;
		while ((i = fis.read(buf)) != -1) {
			fos.write(buf, 0, i);
		}
		fis.close();
		fos.close();
	}

}