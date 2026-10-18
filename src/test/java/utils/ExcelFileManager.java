package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelFileManager {
    private static final Logger log = LogManager.getLogger(ExcelFileManager.class);

    private XSSFWorkbook workbook;
    private Sheet sheet;

    public ExcelFileManager(String filePath, String sheetName) {
        log.info("Opening Excel test data file: {} (sheet: {})", filePath, sheetName);
        try (FileInputStream file = new FileInputStream(filePath)) {
            workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheet(sheetName);
            log.info("Excel workbook loaded successfully: {}", filePath);

            if (sheet == null) {
                workbook.close();
                log.info("Excel workbook closed successfully");
                workbook = null;
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

        } catch (IOException e) {
            log.error("Failed to load Excel file: {}", filePath, e);
            throw new RuntimeException("Failed to load Excel file: " + filePath, e);
        }
    }

    public int getCellValue(int rowIndex, int colIndex) {
        if (workbook == null || sheet == null) {
            throw new IllegalStateException("Excel workbook is not loaded or has been closed.");
        }

        if (rowIndex < 0 || colIndex < 0) {
            throw new IllegalArgumentException("Row and column indexes must not be negative.");
        }

        if (sheet.getRow(rowIndex) == null ||
                sheet.getRow(rowIndex).getCell(colIndex) == null) {
            throw new IllegalArgumentException(
                    "Cell not found at row " + rowIndex + ", column " + colIndex);
        }

        log.debug("Reading Excel cell at row {}, column {}", rowIndex, colIndex);
        String value = new DataFormatter().formatCellValue(
                sheet.getRow(rowIndex).getCell(colIndex));

        int parsedValue = Integer.parseInt(value.replace(",", "").trim());
        log.debug("Excel cell [{}, {}] parsed as integer", rowIndex, colIndex);
        return parsedValue;
    }

    public void closeWorkbook() {
        log.debug("Closing Excel workbook");
        if (workbook != null) {
            try {
                workbook.close();
                log.info("Excel workbook closed successfully");
            } catch (IOException e) {
                throw new RuntimeException("Failed to close Excel workbook", e);
            } finally {
                workbook = null;
                sheet = null;
            }
        }
    }
}