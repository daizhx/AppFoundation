package smtech.com.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipException;

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


    /**
     * 使用 org.apache.tools.zip.ZipFile 解压文件，它与 java 类库中的 java.util.zip.ZipFile
     * 使用方式是一新的，只不过多了设置编码方式的 接口。
     *
     * 注，apache 没有提供 ZipInputStream 类，所以只能使用它提供的ZipFile 来读取压缩文件。
     *
     * @param archive
     *            压缩包路径
     * @param decompressDir
     *            解压路径
     * @param newFiles 记录解压出来的文件，没什么实际作用
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ZipException
     */
    public static void upZipFile(String archive, String decompressDir,
                                 List<String> newFiles) throws IOException, FileNotFoundException,
            ZipException {
        if (newFiles == null)
            newFiles = new LinkedList<String>();

        BufferedInputStream bi;
        org.apache.tools.zip.ZipFile zf = new org.apache.tools.zip.ZipFile(
                archive, "GBK");// 支持中文

        Enumeration e = zf.getEntries();
        while (e.hasMoreElements()) {
            org.apache.tools.zip.ZipEntry ze2 = (org.apache.tools.zip.ZipEntry) e
                    .nextElement();
            String entryName = ze2.getName();
            String path = decompressDir + "/" + entryName;
            if (ze2.isDirectory()) {
                File decompressDirFile = new File(path);
                if (!decompressDirFile.exists()) {
                    decompressDirFile.mkdirs();
                }
                newFiles.add(decompressDirFile.getAbsolutePath());
            } else {
                String fileDir = path.substring(0, path.lastIndexOf("/"));
                File fileDirFile = new File(fileDir);
                if (!fileDirFile.exists()) {
                    fileDirFile.mkdirs();
                }
                if (decompressDir.lastIndexOf("/") == decompressDir.length() - 1){
                    newFiles.add(decompressDir + entryName);
                } else {
                    newFiles.add(decompressDir + "/" + entryName);
                }

                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(decompressDir + "/" + entryName));
                bi = new BufferedInputStream(zf.getInputStream(ze2));
                byte[] readContent = new byte[1024];
                int readCount = bi.read(readContent);
                while (readCount != -1) {
                    bos.write(readContent, 0, readCount);
                    readCount = bi.read(readContent);
                }
                bos.close();
            }
        }
        zf.close();
    }

}