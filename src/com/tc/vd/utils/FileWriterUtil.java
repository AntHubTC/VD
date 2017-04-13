package com.tc.vd.utils;

import java.io.*;

/**
 * Created by tangcheng on 2017/4/13.
 */
public class FileWriterUtil {
    private FileWriterUtil() {}

    public static void writeFileContent(File file, String content, boolean isAppend) throws IOException {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file, isAppend);
            bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(content);
        } catch (IOException e) {
            throw e;
        } finally {
            bufferedWriter.close();
            fileWriter.close();
        }
    }
}
