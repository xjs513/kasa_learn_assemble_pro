##  POI （Apache POI）

Apache POI 是Apache软件基金会的开源函数库，

POI提供API给Java程序对 Microsoft Office格式文档的读写能力。

###  基本功能

 HSSF - 提供读写 Microsoft Excel 格式档案的功能。03版 最多 65535 行

 XSSF - 提供读写 Microsoft Excel OOXML 格式档案的功能。07 版

HWPF- 提供读写 Microsoft Word 格式档案的功能。

 HSLF - 提供读写 Microsoft PowerPoint 格式档案的功能。

 HDGF-提供读写 Microsoft Visio 格式档案的功能。

>  easyExcel

easyExcel 官网地址：https://github.com/alibaba/easyexcel

Java 解析 Excel 工具

Java解析、生成Excel比较有名的框架有Apache poi、jxl。但他们都存在一个严重的问题就是非常的耗内存，poi有一套SAX模式的API可以一定程度的解决一些内存溢出的问题，但POI还是有一些缺陷，比如07版Excel解压缩以及解压后存储都是在内存中完成的，内存消耗依然很大。easyexcel重写了poi对07版Excel的解析，能够原本一个3M的excel用POI sax依然需要100M左右内存降低到几M，并且再大的excel不会出现内存溢出，03版依赖POI的sax模式。在上层做了模型转换的封装，让使用者更加简单方便

####  对应的几个对象

工作簿 工作表 行 单元格

```xml
<!-- POI 依赖 03 -->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>4.0.0</version>
</dependency>
<!-- POI 依赖 07 -->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>4.0.0</version>
</dependency>
<!-- 日期格式化工具 -->
<dependency>
    <groupId>joda-time</groupId>
    <artifactId>joda-time</artifactId>
    <version>2.10.5</version>
</dependency>
```

```java
package com.kuang;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriteTest {

    static String PATH =  "C:\\kasa_work_pros\\kasa_learn_pro\\juc-kasa\\poi_out\\";
    public static void main(String[] args) throws IOException {
        // 1. create a workbook
       //  Workbook workbook = new HSSFWorkbook();  // 03
        Workbook workbook = new XSSFWorkbook();    // 07
        // 2. create a worksheet
        Sheet sheet = workbook.createSheet("狂神观众统计表");
        // 3. create a row
        Row row1 = sheet.createRow(0);
        // 4. create a cell
        Cell cell11 = row1.createCell(0);
        // 5. write content to the cell
        cell11.setCellValue("今日新增观众");
        Cell cell12   = row1.createCell(1);
        cell12.setCellValue(666);
        // the second row (2, 1)
        Row row2 = sheet.createRow(1);
        Cell cell21 = row2.createCell(0);
        cell21.setCellValue("统计时间");
        // (2， 2)
        Cell cell22 = row2.createCell(1);
        String time = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        cell22.setCellValue(time);
        FileOutputStream fileOutputStream =
                new FileOutputStream(PATH + "狂神观众统计表07.xlsX");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        System.out.println("文件输出完毕！~");

    }
}
```

###   大文件写入

>  大文件写  HSSF

缺点：最多处理  65536 行，再多就异常

```java
java.lang.IllegalRrgumentException:Invalid row number (65536) outside allowable range (0...65535)
```

优点：过程中写入缓存，不操作磁盘，最后一次性写入，速度快

```java
@Test
public void testWrite03BigData() throws IOException{
    long begin = System.currentTimeMillis();
    Workbook workbook = new HSSFWorkbook();  // 03
    Sheet sheet = workbook.createSheet();
    for (int rowNum = 0; rowNum < 65536; rowNum++) {
        Row row = sheet.createRow(rowNum);
        for (int cellNum = 0; cellNum < 10; cellNum++) {
            Cell cell = row.createCell(cellNum);
            cell.setCellValue(rowNum + "-" + cellNum);
        }
    }
    System.out.println("over");
    FileOutputStream fileOutputStream =
        new FileOutputStream(PATH + "testWrite03BigData.xls");
    workbook.write(fileOutputStream);
    workbook.write(fileOutputStream);
    fileOutputStream.close();
    long end = System.currentTimeMillis();
    System.out.println((end-begin)/1000);
}
```

>   大文件写 XSSF

缺点 ：写数据的时候非常慢，非常耗内存，也会发生内存溢出，比如 100万条数据

优点：可以写较大的数据量，比如：20W条数据。 

```java
@Test
public void testWrite07BigData() throws IOException{
    long begin = System.currentTimeMillis();
    Workbook workbook = new XSSFWorkbook();  // 03
    Sheet sheet = workbook.createSheet();
    for (int rowNum = 0; rowNum < 100000; rowNum++) {
        Row row = sheet.createRow(rowNum);
        for (int cellNum = 0; cellNum < 10; cellNum++) {
            Cell cell = row.createCell(cellNum);
            cell.setCellValue(rowNum + "-" + cellNum);
        }
    }
    System.out.println("over");
    FileOutputStream fileOutputStream =
        new FileOutputStream(PATH + "testWrite07BigData.xlsx");
    workbook.write(fileOutputStream);
    workbook.write(fileOutputStream);
    fileOutputStream.close();
    long end = System.currentTimeMillis();
    System.out.println((end-begin)/1000);
}
```

>    大文件写 SXSSF

优点：可以写非常大的数据量，如100W甚至更多，写入速度快，占用更少的内存

缺点：过程会产生临时文件，需要清理临时文件

```java
@Test
public void testWrite07BigDataS() throws IOException{
    long begin = System.currentTimeMillis();
    SXSSFWorkbook workbook = new SXSSFWorkbook();  // 07
    Sheet sheet = workbook.createSheet();
    for (int rowNum = 0; rowNum < 200000; rowNum++) {
        Row row = sheet.createRow(rowNum);
        for (int cellNum = 0; cellNum < 10; cellNum++) {
            Cell cell = row.createCell(cellNum);
            cell.setCellValue(rowNum + "-" + cellNum);
        }
    }
    System.out.println("over");
    FileOutputStream fileOutputStream =
        new FileOutputStream(PATH + "testWrite07BigDataS.xlsx");
    workbook.write(fileOutputStream);
    fileOutputStream.close();
    // 清除临时文件
    workbook.dispose();
    long end = System.currentTimeMillis();
    System.out.println((end-begin)/1000);
}
```

SXSSFWorkbook-来自官方的解释：

实现 “BigGridDemo”  策略 的流式 XSSFWorkbook  版本。

允许写入非常大的文件而不会耗尽内存，因为任何部分只有可配置的行部分在内存中。

请注意：仍然可能会消耗 大量内存，比如使用某些功能 ，合并区域 等等

注释：仍然只存储在内存中，因此如果 广泛使用，可能 需要 大量内存 。

再使用 POI 的时候，排查 内存 问题用 Jprofile~!

###   PIO-Excel  读

>  POI 读取 03、07格式的数据

```java
package com.kuang;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReadTest {

    String PATH = "C:\\kasa_work_pros\\kasa_learn_pro\\juc-kasa\\poi_out\\";

    @Test
    public void testRead03() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(PATH + "狂神观众统计表03.xls");
        Workbook workbook = new HSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(1);
        // 读取单元格的时候，一定要注意类型,否则会读取失败！！~~
        // String value = cell.getStringCellValue();
        double value = cell.getNumericCellValue();
        System.out.println("value = " + value);
    }

    @Test
    public void testRead07() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(String.format("%s狂神观众统计表07.xlsx", PATH));
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(1);
        // 读取单元格的时候，一定要注意类型,否则会读取失败！！~~
        // String value = cell.getStringCellValue();
        double value = cell.getNumericCellValue();
        System.out.println("value = " + value);
    }
}

```

>   ==读取不同的数据类型(工作中这里最麻烦)==

```java
String PATH2 = "C:\\kasa_work_pros\\kasa_learn_pro\\juc-kasa\\poi_in\\";
@Test
public void testCellType() throws IOException {
    FileInputStream fileInputStream = new FileInputStream(PATH2 + "明细表.xls");
    Workbook workbook = new HSSFWorkbook(fileInputStream);
    Sheet sheet = workbook.getSheetAt(0);
    // get title content
    Row rowTitle = sheet.getRow(0);
    if (null != rowTitle) {
        // must master this method
        int cellCount = rowTitle.getPhysicalNumberOfCells();
        System.out.println("cellCount = " + cellCount);
        for (int cellNum = 0; cellNum < cellCount; cellNum++) {
            Cell cell = rowTitle.getCell(cellNum);
            if (null != cell){
                CellType cellType = cell.getCellType();
                String cellValue = cell.getStringCellValue();
                System.out.print(cellValue + " | ");
            }
        }
        System.out.println();
    }
    // get data content in this sheet
    int rowCount = sheet.getPhysicalNumberOfRows();
    for (int rowNum = 1; rowNum < rowCount; rowNum++) {
        Row rowData = sheet.getRow(rowNum);
        if (null != rowData) {
            // get columns in this row
            int cellCount = rowTitle.getPhysicalNumberOfCells();
            for (int cellNum = 0; cellNum < cellCount; cellNum++) {
                System.out.print("[" + (rowNum+1) + "-" + (cellNum+1) + "]");
                Cell cell = rowData.getCell(cellNum);
                if (null != cell) {
                    CellType cellType = cell.getCellType();
                    String cellValue = "";
                    switch (cellType) {
                        case STRING:// 字符串
                            System.out.print("[String]");
                            cellValue = cell.getStringCellValue();
                            break;
                        case BOOLEAN:// 布尔
                            System.out.print("[BOOLEAN]");
                            cellValue = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case BLANK:// 空
                            System.out.print("[BLANK]");
                            break;
                        case NUMERIC:// 数字(日期和普通数字)
                            if (HSSFDateUtil.isCellDateFormatted(cell)){// 日期
                                System.out.print("[日期]");
                                Date date = cell.getDateCellValue();
                                cellValue = new DateTime(date).toString("yyyy-MM-dd");
                            } else {
                                // 如是日期格式，防止数字过长 ~~！！
                                System.out.print("[普通数字]");
                                cell.setCellType(CellType.STRING);
                                cellValue = cell.toString();
                            }
                            break;
                        case ERROR:
                            System.out.print("[ERROR]");
                            break;
                    }

                    System.out.println(cellValue);
                }

            }
            System.out.println();
        }
    }
    fileInputStream.close();
}
```

>   计算公式 ==了解即可==

```java
@Test
public void testFormula() throws Exception {
    FileInputStream fileInputStream = new FileInputStream(PATH2 + "公式.xls");
    Workbook workbook = new HSSFWorkbook(fileInputStream);

    Sheet sheet = workbook.getSheetAt(0);
    Row row = sheet.getRow(4);
    Cell cell = row.getCell(0);
    // get formula eval
    FormulaEvaluator formulaEvaluator = new HSSFFormulaEvaluator(
        (HSSFWorkbook) workbook
    );

    // output value of the cell
    CellType cellType = cell.getCellType();
    switch (cellType){
        case FORMULA:
            String formula = cell.getCellFormula();
            System.out.println("formula = " + formula);
            CellValue evaluate = formulaEvaluator.evaluate(cell);
            String cellValue = evaluate.formatAsString();
            System.out.println("cellValue = " + cellValue);
            break;
    }

}
```

###  Easy-Excel 

==看官方文档即可==

https://github.com/alibaba/easyexcel



