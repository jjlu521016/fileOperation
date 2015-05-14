package com.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 * Javaʵ���ļ����ơ����С�ɾ������
 * �ļ�ָ�ļ����ļ���
 * �ļ��ָ��ͳһ��"\\"
 */

public class FileOperateDemo {
	
	/**
	 * �����ļ����ļ���
	 * @param srcPath
	 * @param destDir Ŀ���ļ����ڵ�Ŀ¼
	 * @return
	 */
	public static boolean copyGeneralFile(String srcPath, String destDir) {
		boolean flag = false;
		File file = new File(srcPath);
		if(!file.exists()) {
			System.out.println("Դ�ļ���Դ�ļ��в�����!");
			return false;
		}
		if(file.isFile()) {	//Դ�ļ�
			System.out.println("��������ļ�����!");
			flag = copyFile(srcPath, destDir);
		}
		else if(file.isDirectory()) {
			System.out.println("��������ļ��и���!");
			flag = copyDirectory(srcPath, destDir);
		}
		
		return flag;
	}
	
	/**
	 * �����ļ�
	 * 
	 * @param srcPath
	 *            Դ�ļ�����·��
	 * @param destDir
	 * 			    Ŀ���ļ�����Ŀ¼
	 * @return boolean
	 */
	private static boolean copyFile(String srcPath, String destDir) {
		boolean flag = false;

		File srcFile = new File(srcPath);
		if (!srcFile.exists()) { // Դ�ļ�������
			System.out.println("Դ�ļ�������");
			return false;
		}
		//��ȡ�������ļ����ļ���
		String fileName = srcPath.substring(srcPath.lastIndexOf(File.separator));
		String destPath = destDir + fileName;
		if (destPath.equals(srcPath)) { // Դ�ļ�·����Ŀ���ļ�·���ظ�
			System.out.println("Դ�ļ�·����Ŀ���ļ�·���ظ�!");
			return false;
		}
		File destFile = new File(destPath);
		if(destFile.exists() && destFile.isFile()) {	//��·�����Ѿ���һ��ͬ���ļ�
			System.out.println("Ŀ��Ŀ¼������ͬ���ļ�!");
			return false;
		}
		
		File destFileDir = new File(destDir);
		destFileDir.mkdirs();
		try {
			FileInputStream fis = new FileInputStream(srcPath);
			FileOutputStream fos = new FileOutputStream(destFile);
			byte[] buf = new byte[1024];
			int c;
			while ((c = fis.read(buf)) != -1) {
				fos.write(buf, 0, c);
			}
			fis.close();
			fos.close();

			flag = true;
		} catch (IOException e) {
			//
		}
		
		if(flag) {
			System.out.println("�����ļ��ɹ�!");
		}

		return flag;
	}
	
	/**
	 * 
	 * @param srcPath	Դ�ļ���·��
	 * @param destPath	Ŀ���ļ�������Ŀ¼
	 * @return
	 */
	private static boolean copyDirectory(String srcPath, String destDir) {
		System.out.println("�����ļ��п�ʼ!");
		boolean flag = false;

		File srcFile = new File(srcPath);
		if (!srcFile.exists()) { // Դ�ļ��в�����
			System.out.println("Դ�ļ��в�����");
			return false;
		}
		//��ô����Ƶ��ļ��е����֣���������Ƶ��ļ���Ϊ"E:\\dir"���ȡ������Ϊ"dir"
		String dirName = getDirName(srcPath);
		//Ŀ���ļ��е�����·��
		String destPath = destDir + File.separator + dirName;
//		System.out.println("Ŀ���ļ��е�����·��Ϊ��" + destPath);
		
		if(destPath.equals(srcPath)) {
			System.out.println("Ŀ���ļ�����Դ�ļ����ظ�");
			return false;
		}
		File destDirFile = new File(destPath);
		if(destDirFile.exists()) {	//Ŀ��λ����һ��ͬ���ļ���
			System.out.println("Ŀ��λ������ͬ���ļ���!");
			return false;
		}
		destDirFile.mkdirs();	//����Ŀ¼
		
		File[] fileList = srcFile.listFiles();	//��ȡԴ�ļ����µ����ļ������ļ���
		if(fileList.length==0) {	//���Դ�ļ���Ϊ��Ŀ¼��ֱ������flagΪtrue����һ���ǳ����Σ�debug�˺ܾ�
			flag = true;
		}
		else {
			for(File temp: fileList) {
				if(temp.isFile()) {	//�ļ�
					flag = copyFile(temp.getAbsolutePath(), destPath);
				}
				else if(temp.isDirectory()) {	//�ļ���
					flag = copyDirectory(temp.getAbsolutePath(), destPath);
				}
				if(!flag) {
					break;
				}
			}
		}
		
		if(flag) {
			System.out.println("�����ļ��гɹ�!");
		}
		
		return flag;
	}
	
	/**
	 * ��ȡ�������ļ��е��ļ�����
	 * @param dir
	 * @return String
	 */
	private static String getDirName(String dir) {
		if(dir.endsWith(File.separator)) {	//����ļ���·����"\\"��β������ȥ��ĩβ��"\\"
			dir = dir.substring(0, dir.lastIndexOf(File.separator));
		}
		return dir.substring(dir.lastIndexOf(File.separator)+1);
	}

	/**
	 * ɾ���ļ����ļ���
	 * 
	 * @param path
	 *            ��ɾ�����ļ��ľ���·��
	 * @return boolean
	 */
	public static boolean deleteGeneralFile(String path) {
		boolean flag = false;

		File file = new File(path);
		if (!file.exists()) { // �ļ�������
			System.out.println("Ҫɾ�����ļ������ڣ�");
		}

		if (file.isDirectory()) { // �����Ŀ¼���򵥶�����
			flag = deleteDirectory(file.getAbsolutePath());
		} else if (file.isFile()) {
			flag = deleteFile(file);
		}

		if (flag) {
			System.out.println("ɾ���ļ����ļ��гɹ�!");
		}

		return flag;
	}

	/**
	 * ɾ���ļ�
	 * @param file
	 * @return boolean
	 */
	private static boolean deleteFile(File file) {
		return file.delete();
	}

	/**
	 * ɾ��Ŀ¼����������������ļ������ļ��У�ע��һ��Ŀ¼��������������ļ����ļ���
	 * ��ֱ�ӵ���delete�����ǲ��еģ�����������ļ������ļ�����ȫɾ���˲��ܹ�����delete
	 * @param path
	 *            pathΪ��Ŀ¼��·��
	 */
	private static boolean deleteDirectory(String path) {
		boolean flag = true;
		File dirFile = new File(path);
		if (!dirFile.isDirectory()) {
			return flag;
		}
		File[] files = dirFile.listFiles();
		for (File file : files) { // ɾ�����ļ����µ��ļ����ļ���
			// Delete file.
			if (file.isFile()) {
				flag = deleteFile(file);
			} else if (file.isDirectory()) {// Delete folder
				flag = deleteDirectory(file.getAbsolutePath());
			}
			if (!flag) { // ֻҪ��һ��ʧ�ܾ����̲��ټ���
				break;
			}
		}
		flag = dirFile.delete(); // ɾ����Ŀ¼
		return flag;
	}
	
	/**
	 * �����淽����������з���������+ɾ��
	 * @param  destDir ͬ��
	 */
	public static boolean cutGeneralFile(String srcPath, String destDir) {
		if(!copyGeneralFile(srcPath, destDir)) {
			System.out.println("����ʧ�ܵ��¼���ʧ��!");
			return false;
		}
		if(!deleteGeneralFile(srcPath)) {
			System.out.println("ɾ��Դ�ļ�(�ļ���)ʧ�ܵ��¼���ʧ��!");
			return false;
		}
		
		System.out.println("���гɹ�!");
		return true;
	}

	public static void main(String[] args) {
		copyGeneralFile("E:\\Assemble.txt", "E:\\New.txt");	//�����ļ�
		copyGeneralFile("E:\\hello", "E:\\world");			//�����ļ���
		deleteGeneralFile("E:\\onlinestockdb.sql");			//ɾ���ļ�
		deleteGeneralFile("E:\\woman");						//ɾ���ļ���
		cutGeneralFile("E:\\hello", "E:\\world");			//�����ļ���
		cutGeneralFile("E:\\Difficult.java", "E:\\Cow\\");	//�����ļ�
	}

}
