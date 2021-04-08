/**
 * @Description:TODO
 * @date: 2018年9月4日 下午8:29:22
 * @ClassName: 周伯通
 */
package com.xsebe.yumao.utility;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

/**
 * @Description:TODO
 * @date: 2018年9月4日 下午8:29:22
 * @ClassName: 周伯通
 */
public class ZipUtil {

    public static void main(final String[] args) throws Exception {
        FileOutputStream out = new FileOutputStream("D:/aaa.zip");
        // ByteArrayOutputStream out = new ByteArrayOutputStream();

        List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("fileName", "._jpg.jpg");
        map.put("fileBytes", "This is file".getBytes());
        fileList.add(map);

        map = new HashMap<String, Object>();
        map.put("fileName", "._jpg.jpg");
        map.put("fileBytes", "This is file".getBytes());
        fileList.add(map);

        compress(fileList, out);
        out.close();
    }

    public static void compress(final List<Map<String, Object>> fileList, final OutputStream out) throws Exception {
        ZipOutputStream zout = new ZipOutputStream(out);
        for (Map<String, Object> file : fileList) {
            String fileName = file.get("fileName").toString();
            try {
                zout.putNextEntry(new ZipEntry(fileName));
            } catch (ZipException ex) {
                if (ex.getMessage().startsWith("duplicate entry:")) {
                    fileName = UUID.randomUUID().toString().replace("-", "") + "-" + fileName;
                    zout.putNextEntry(new ZipEntry(fileName));
                } else {
                    throw ex;
                }
            }

            byte[] b = (byte[]) file.get("fileBytes");
            zout.write(b);
            zout.closeEntry();
        }

        zout.finish();
    }
}
