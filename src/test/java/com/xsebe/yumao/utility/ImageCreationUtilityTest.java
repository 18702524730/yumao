package com.xsebe.yumao.utility;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

public class ImageCreationUtilityTest {
    
    @Test
    public void testStatementImageJpgOutput() throws IOException {
        String no = "20190403112306034567";
        String stopName = "贝贝";
        String content = "所展示的原创内容已完成原创公证保管保护，一经发现盗用本店设计、图片、款式等原创内容，必将追究其法律责任。";
        OutputStream output = new FileOutputStream("D:\\声明函-生成.jpg");
        ImageCreationUtility.statementImageJpgOutput(no, stopName, content, output);
        output.close();
    }
}
