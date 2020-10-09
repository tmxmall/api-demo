package com.tmxmall.docTrans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

    public static void inputStreamToFile(InputStream inputStream, String filePath) throws IOException {
        inputStreamToFile(inputStream, new File(filePath));
    }

    public static void inputStreamToFile(InputStream inputStream, File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fs = new FileOutputStream(file);
        int byteIndex = 0;
        byte[] buffer = new byte[4096];
        while ((byteIndex = inputStream.read(buffer)) != -1) {
            fs.write(buffer, 0, byteIndex);
        }
        fs.close();
    }

}
