package smtech.com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
	public static void rmv(String path) {
		rmv(new File(path));
	}

	public static void rmv(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					rmv(files[i]);
				}
			}
			file.delete();
		}
	}

	public static boolean move(String srcFile, String destPath) {
		File file = new File(srcFile);
		File dir = new File(destPath);
		return file.renameTo(new File(dir, file.getName()));
	}

	public static boolean move(File srcFile, String destPath) {
		File dir = new File(destPath);
		return srcFile.renameTo(new File(dir, srcFile.getName()));
	}

	public static void copy(String oldPath, String newPath) throws IOException {
		if (oldPath.equals(newPath)) {
			return;
		}
			// 先检查目录是否存在
			File newFileParent = new File(new File(newPath).getParent());
			if (!newFileParent.exists() || !newFileParent.isDirectory()) {
				newFileParent.mkdirs();
			}

			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					fs.write(buffer, 0, byteread);
				}
				fs.close();
				inStream.close();
			}

	}

	public static void copy(File oldfile, String newPath) throws IOException {
			int bytesum = 0;
			int byteread = 0;
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldfile);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}

	}

	public static void copyDir(String src, String des) throws IOException {
		File file1 = new File(src);
		File[] fs = file1.listFiles();
		File file2 = new File(des);
		if (!file2.exists()) {
			file2.mkdirs();
		}
		for (File f : fs) {
			if (f.isFile()) {
				copy(f.getPath(), des + "\\" + f.getName()); // 调用文件拷贝的方法
			} else if (f.isDirectory()) {
				copyDir(f.getPath(), des + "\\" + f.getName());
			}
		}

	}

}