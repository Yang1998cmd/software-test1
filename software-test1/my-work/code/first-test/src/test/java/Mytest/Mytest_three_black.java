package Mytest;

import caculate.Packages;
import caculate.Ticket;
import caculate.caculate_tool;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import window.caculate_window;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mytest_three_black {
    private caculate_window mywindow;
    @DataProvider
    public Object[][] providerMethod() throws IOException {
        String excelPath = "黑盒测试.xlsx";
        File excel = new File(excelPath);
        String[][] mystr = null;
        if (excel.isFile() && excel.exists()) { // 判断文件是否存在

            String[] split = excel.getName().split("\\."); // .是特殊字符，需要转义！！！！！
            // System.out.println(split[1]);
            Workbook wb = null;
            // 根据文件后缀（xls/xlsx）进行判断
            FileInputStream fis = new FileInputStream(excel); // 文件流对象
            if ("xls".equals(split[1])) {
                wb = new HSSFWorkbook(fis);
            } else if ("xlsx".equals(split[1])) {
                wb = new XSSFWorkbook(fis);
            } else {
                System.out.println("文件类型错误!");

            }

            // 开始解析
            Sheet sheet = wb.getSheetAt(0); // 读取sheet 0

            int firstRowIndex = sheet.getFirstRowNum(); // 第一行是列名，所以不读
            int lastRowIndex = sheet.getLastRowNum();

            String [][] str=new String[lastRowIndex][2];

            for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) { // 遍历行
                Row row = sheet.getRow(rIndex);
                if (row != null) {
                    if(rIndex>0)
                    {
                        int firstCellIndex = row.getFirstCellNum();
                        int lastCellIndex = row.getLastCellNum();
                        String value = "";
                        for (int cIndex = firstCellIndex; cIndex <=lastCellIndex; cIndex++) { // 遍历列
                            Cell cell = row.getCell(cIndex);
                            if ((cell != null) && (!cell.toString().trim().equals(""))) {
                                value = value + cell.toString() + ",";
                            }
                        }
                        System.out.println(value);
                        str[rIndex-1][0]=Integer. toString(rIndex);
                        str[rIndex-1][1]=value;

                    }

                }
            }
            mystr=str;
        } else {
            System.out.println("找不到指定的文件");
        }
        return mystr;

    }


    @Test(dataProvider = "providerMethod")
    public void test_black(String rowid, String main_context) throws IOException {
        mywindow =new caculate_window();
        String[] context=main_context.split(",");
        int i=Integer.parseInt(rowid);

        String ans="输入有效";
        String s=mywindow.add_pack(context[4],context[5],context[6],context[7]);
        String b=mywindow.add_ticket("国内航线",context[3],context[2],context[9],context[1]);
        if(s.equals("非DOUBLE类型不允许")||s.equals("行李总长度不能小于60.0cm")||s.equals("行李重量不能小于2kg")||s.equals("行李总长度不能大于203.0cm")||s.equals("行李重量不能大于32kg"))
        {
            ans="输入无效";
        }
        else if(b.equals("票价负数不允许")||b.equals("非DOUBLE类型不允许"))
        {
            ans="输入无效";
        }

        Assert.assertEquals(ans,context[10],main_context+"  测试结果和预期不符");
        System.out.println(main_context);
        write_to_excel(i,ans);
    }


    public void write_to_excel(int i,String all_tran_price) throws IOException {
        String excelPath = "黑盒测试.xlsx";
        File excel = new File(excelPath);

        if (excel.isFile() && excel.exists()) { // 判断文件是否存在

            String[] split = excel.getName().split("\\."); // .是特殊字符，需要转义！！！！！
            // System.out.println(split[1]);
            Workbook wb = null;
            // 根据文件后缀（xls/xlsx）进行判断
            FileInputStream fis = new FileInputStream(excel); // 文件流对象
            if ("xls".equals(split[1])) {
                wb = new HSSFWorkbook(fis);
            } else if ("xlsx".equals(split[1])) {
                wb = new XSSFWorkbook(fis);
            } else {
                System.out.println("文件类型错误!");

            }

            // 开始解析
            Sheet sheet = wb.getSheetAt(0); // 读取sheet 0
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cell=row.getCell(11);
                cell.setCellValue(all_tran_price);
            }
            fis.close();

            FileOutputStream out=new FileOutputStream(excel);
            out.flush();
            wb.write(out);
            out.close();

        } else {
            System.out.println("找不到指定的文件");
        }

    }
}
