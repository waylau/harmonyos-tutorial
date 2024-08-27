package com.waylau.hmos.javadataabilityhelperaccessfile;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {

    /**
     * 输出文件内容
     * @param fd
     * @return 文件内容
     * @throws IOException
     */
    public static String getFileContent(FileDescriptor fd) throws IOException {
        // 根据FileDescriptor创建FileInputStream对象
        FileInputStream fis = new FileInputStream(fd);

        int b = 0;
        StringBuilder sb = new StringBuilder();
        while((b = fis.read()) != -1){
            sb.append((char)b);
        }
        fis.close();

        return sb.toString();
    }

}
