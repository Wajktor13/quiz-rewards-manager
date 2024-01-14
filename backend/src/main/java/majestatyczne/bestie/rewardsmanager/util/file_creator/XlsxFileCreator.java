package majestatyczne.bestie.rewardsmanager.util.file_creator;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class XlsxFileCreator implements FileCreator {

    @Override
    public byte[] createFileWithTable(List<List<String>> inputRows, List<Integer> rowsToHighlight)
            throws IOException {
        if (inputRows.isEmpty() || inputRows.get(0).isEmpty()){
            throw new IllegalArgumentException("cannot create file with empty table");
        }

        int no_rows = inputRows.size();
        int no_cols = inputRows.get(0).size();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");

            for (int row_ind = 0; row_ind < no_rows; row_ind++) {
                Row row = sheet.createRow(row_ind);

                for (int col_ind = 0; col_ind < no_cols; col_ind++) {
                    Cell cell = row.createCell(col_ind);
                    cell.setCellValue(inputRows.get(row_ind).get(col_ind));
                }
            }

            for (int row_ind : rowsToHighlight) {
                setRowColor(sheet.getRow(row_ind), workbook);
            }

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                workbook.write(outputStream);
                return outputStream.toByteArray();
            }
        }
    }

    private void setRowColor(Row row, Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        for (Cell cell : row) {
            cell.setCellStyle(style);
        }
    }
}
