package com.example.nskStagirovka.Halper;

import com.example.nskStagirovka.model.Employee;
import org.apache.commons.csv.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = { "organization", "fio","position", "dateEmployment","dateLayoffs","education","educationEnd","status" };

    public static boolean hasCSVFormat(MultipartFile file) {
        if (TYPE.equals(file.getContentType())
                || file.getContentType().equals("application/vnd.ms-excel")) {
            return true;
        }

        return false;
    }

    public static List<Employee> csvToMake(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Employee> employeeList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Employee employee = new Employee(
                        csvRecord.get("organization"),
                        csvRecord.get("fio"),
                        csvRecord.get("position"),
                        csvRecord.get("dateEmployment"),
                        csvRecord.get("dateLayoffs"),
                        csvRecord.get("education"),
                        csvRecord.get("educationEnd"),
                        Boolean.parseBoolean("status")


                );

                employeeList.add(employee);
            }

            return employeeList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream tutorialsToCSV(List<Employee> employeeList) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream(); CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (Employee employee : employeeList) {
                List<String> data = Arrays.asList(
                        employee.getOrganization(),
                        employee.getFio(),
                        employee.getPosition(),
                        employee.getDateEmployment(),
                        employee.getDateLayoffs(),
                        employee.getEducation(),
                        employee.getEducation(),
                        String.valueOf(employee.isStatus())

                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
