package cn.bosenkeji.utils;

import cn.bosenkeji.vo.cdKey.CdKeyOther;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ExcelUtil {

    public static InputStream genCdKeyExcel(List<CdKeyOther> list) {

        InputStream inputStream = null;
        ByteArrayOutputStream out = null;

        String[] headers = {"激活码","产品","套餐","时长","生成时间"};
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();

        sheet.setDefaultColumnWidth((short) 42); //设置列宽

        try {
        //1.设置字体
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);

        CellStyle baseCellStyle = createBaseCellStyle(workbook);
        CellStyle headerCellStyle = createBaseCellStyle(workbook);
        headerCellStyle.setFont(font);

        //设置标题行
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(headerCellStyle);
            cell.setCellValue(headers[i]); //给单元格设置内容
        }

        //遍历集合，填充到单元格中
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i+1);
            row.setHeight((short) 320);
            setCellStyleByCdKey(row,baseCellStyle,list.get(i));
        }
        out = new ByteArrayOutputStream();
        workbook.write(out);
        inputStream = new ByteArrayInputStream(out.toByteArray());


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inputStream;
    }

    /**
     * 将文件上传到阿里云OSS
     * @param oss 阿里云oss客户端
     * @param inputStream 文件InputStream
     * @param bucketName 存储空间 bucketName
     * @param url 存放位置
     * @return
     */
    public static boolean uploadExcelToOSS(OSS oss, InputStream inputStream, String bucketName, String url) {
        PutObjectResult putObjectResult = oss.putObject(bucketName, url, inputStream);
        if (putObjectResult == null) {
            return false;
        } else {
            return true;
        }
    }


    private static void setCellStyleByCdKey(HSSFRow row, CellStyle cellStyle, CdKeyOther cdKeyOther) {
        HSSFCell[] cells = new HSSFCell[5];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = row.createCell(i);
            cells[i].setCellStyle(cellStyle);
        }
        cells[0].setCellValue(cdKeyOther.getKey());
        cells[1].setCellValue(cdKeyOther.getProductName());
        cells[2].setCellValue(cdKeyOther.getComboName());
        cells[3].setCellValue(cdKeyOther.getTime());
        cells[4].setCellValue(cdKeyOther.getCreateAt());
    }

    private static CellStyle createBaseCellStyle(HSSFWorkbook workbook) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }
}
