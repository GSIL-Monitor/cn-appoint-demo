package com.jd.appoint.store.util;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

public class ExcelUtils {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    public static boolean writeExcel(List<LinkedHashMap<String,String>> list, HttpServletResponse response){
        WritableWorkbook wwb = null;
        try {
            //首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象
            wwb = Workbook.createWorkbook(response.getOutputStream());
            if(wwb==null) {
                return false;
            }


            //创建一个可写入的工作表
            //Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置
            WritableSheet ws = wwb.createSheet("预约单列表", 0);
            int rows = list.size();
            //下面开始添加单元格
            for (int i = 0; i < rows; i++) {
                LinkedHashMap<String, String> map = list.get(i);
                Set<Map.Entry<String, String>> entries = map.entrySet();
                Iterator<Map.Entry<String, String>> iterator = entries.iterator();
                int column = 0;
                while (iterator.hasNext()) {
                    Map.Entry<String, String> entry = iterator.next();
                    int row = i;
                    if (i == 0) {
                        //第一次先打印表头
                        Label title = new Label(column, 0, entry.getKey());
                        ws.addCell(title);
                    }
                    //这里需要注意的是，在Excel中，第一个参数表示列，第二个表示行
                    Label cell = new Label(column++, i+1, entry.getValue());

                    //将生成的单元格添加到工作表中
                    ws.addCell(cell);

                }

            }
            //从内存中写入文件中
            String fileName = "预约单列表.xls";

            String headStr = "attachment; filename="+URLEncoder.encode(fileName,"utf-8");
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", headStr);
            wwb.write();

            return true;
        } catch (Exception e) {
            logger.error("写入excel出现异常，msg={}",e.getMessage());
        } finally {
            try {
                //关闭资源，释放内存
                wwb.close();
            } catch (Exception e) {
                logger.error("excel资源关闭失败");

            }
        }
        return false;
    }

}
