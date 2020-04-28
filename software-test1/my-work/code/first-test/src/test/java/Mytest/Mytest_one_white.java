package Mytest;

import caculate.Packages;
import caculate.Ticket;
import caculate.caculate_tool;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Mytest_one_white {

    @DataProvider
    public Object[][] providerMethod() throws IOException {
        String excelPath = "国内航线白盒测试.xlsx";
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
    public void test_in_china(String rowid, String main_context) throws IOException {
        String[] context=main_context.split(",");
        int i=Integer.parseInt(rowid);
        String class_kind=context[2];//舱类型
        double ticket_price=2000.0;//票价
        String passenge_kind=context[1];//乘客类型
        double package_length=Double.parseDouble(context[3]);//长
        double package_width=Double.parseDouble(context[4]);//宽
        double package_height=Double.parseDouble(context[5]);//高
        double package_weight=Double.parseDouble(context[6]);//重量

        Ticket newticket=new Ticket("国内航线","",class_kind,ticket_price,passenge_kind);
        Packages newpackage=new Packages(package_length,package_width,package_height,package_weight);
        List<Packages> passenger_Package=new ArrayList<Packages>();
        passenger_Package.add(newpackage);
        caculate_tool cacul=new caculate_tool(passenger_Package,newticket);
        cacul.in_china (passenger_Package.get(0));

        Assert.assertEquals(cacul.to_all_tran_price(),Double.parseDouble(context[8]),main_context+"  计算结果和预期不符");
        System.out.println("计算值: "+i+" "+cacul.to_all_tran_price());
        write_to_excel(i,cacul.to_all_tran_price());
    }


    public void write_to_excel(int i,double all_tran_price) throws IOException {
        String excelPath = "国内航线白盒测试.xlsx";
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
                Cell cell=row.getCell(10);
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
