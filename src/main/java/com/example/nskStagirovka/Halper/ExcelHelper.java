package com.example.nskStagirovka.Halper;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.nskStagirovka.model.Employee;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;


public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"organization", "fio", "position", "dateEmployment", "dateLayoffs", "education", "educationEnd", "status"};
    static String SHEET = "Employees";

    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<Employee> excelToEmployees(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Employee> employees = new ArrayList<Employee>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Employee employee = new Employee();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            employee.setOrganization(currentCell.getStringCellValue());
                            break;
                        case 1:
                            employee.setFio(currentCell.getStringCellValue());
                            break;

                        case 2:
                            employee.setPosition(currentCell.getStringCellValue());
                            break;

                        case 3:
                            employee.setDateEmployment(currentCell.getStringCellValue());
                            break;
                        case 4:
                            employee.setDateLayoffs(currentCell.getStringCellValue());
                            break;
                        case 5:
                            employee.setEducation(currentCell.getStringCellValue());
                            break;
                        case 6:
                            employee.setEducationEnd(currentCell.getStringCellValue());
                            break;
                        case 7:
                            employee.setStatus(currentCell.getBooleanCellValue());

                        default:
                            break;
                    }

                    cellIdx++;
                }

                employees.add(employee);
            }

            workbook.close();

            return employees;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}

