import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Parser {

    public static ArrayList<Data> parse(File file) {

        ArrayList<Data> resList = new ArrayList<>();
        XSSFWorkbook wb = null;

        try (InputStream in = new FileInputStream(file)) {
            wb = new XSSFWorkbook(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (wb != null) {
            try {
                for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                    Sheet sheet = wb.getSheetAt(i);
                    for (Row row : sheet) {
                        for (Cell cell : row) {
                            Data data = new Data();
                            data.setGroup(getCellInString(row.getCell(0)));
                            data.setDayOfWeek(sheet.getSheetName().toLowerCase());

                            String s = getCellInString(cell);
                            String[] strings = s.split(" ");

                            if (row.getPhysicalNumberOfCells() > 1) {
                                String time = getTimeOfStartLessons(cell.getColumnIndex());
                                data.setTime(time);

                                if (strings.length == 1) {
                                    continue;
                                }

                                if (strings.length == 3) {
                                    data.setLecture(strings[0]);
                                    data.setAuditory(strings[1]);
                                    data.setLecturer(strings[2]);
                                } else if (strings.length == 2) {
                                    data.setLecture(strings[0]);
                                    data.setAuditory("н/д");
                                    data.setLecturer(strings[1]);
                                }
                            }

                            resList.add(data);

                        }
                    }
                }
            } catch (Exception e) {
                GUI.status.setText("Файл некорректно заполнен " + e.getMessage());
                e.printStackTrace();
            }
        }
        return resList;
    }

    private static String getCellInString(Cell cell) {
        return cell.getCellType() == Cell.CELL_TYPE_NUMERIC ?
                String.valueOf((int) cell.getNumericCellValue()) :
                cell.getStringCellValue();
    }

    private static String getTimeOfStartLessons(int k) {
        String time = "";
        switch (k) {
            case 1:
                time = "8.30";
                break;
            case 2:
                time = "10.20";
                break;
            case 3:
                time = "12.30";
                break;
            case 4:
                time = "14.20";
                break;
            case 5:
                time = "16.10";
                break;
            case 6:
                time = "18.00";
                break;
        }
        return time;
    }
}