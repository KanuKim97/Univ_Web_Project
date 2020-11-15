package util;

import java.io.*;

public class Util {
	public static void saveImage(String base, String id, String filename, byte[] data) throws Exception {
		File f = new File(base);
		if (!f.exists()) f.mkdir();

		String dir = base + "/" + id;
		f = new File(dir);
		if (!f.exists()) f.mkdir();

		FileOutputStream out = new FileOutputStream(new File(dir + "/" + filename));
		out.write(data);
		out.close();
	}
}
