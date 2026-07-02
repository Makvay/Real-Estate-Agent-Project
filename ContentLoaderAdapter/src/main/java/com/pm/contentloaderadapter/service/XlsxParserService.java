package com.pm.contentloaderadapter.service;

import com.pm.contentloaderadapter.domain.dto.BuildingRawData;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class XlsxParserService {

    public List<BuildingRawData> parse(MultipartFile file, String source) {
        List<BuildingRawData> records = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                BuildingRawData data;
                if ("DOMCLICK".equalsIgnoreCase(source)) {
                    data = parseDomclick(row);
                } else if ("ROSREESTR".equalsIgnoreCase(source)) {
                    data = parseRosreestr(row);
                } else {
                    data = parseGeneric(row);
                }

                if (data != null) {
                    data.setSource(source);
                    records.add(data);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse XLSX file", e);
        }

        return records;
    }

    private BuildingRawData parseDomclick(Row row) {
        if (isEmpty(row.getCell(16))) return null;

        return BuildingRawData.builder()
                .cadastrNumber(getString(row.getCell(16)))
                .type(getString(row.getCell(1)))           // тип объекта
                .square(parseSquare(getString(row.getCell(5))))   // площадь
                .price(parsePrice(getString(row.getCell(8))))     // цена
                .build();
    }

    private BuildingRawData parseRosreestr(Row row) {
        if (isEmpty(row.getCell(1))) return null;

        return BuildingRawData.builder()
                .cadastrNumber(getString(row.getCell(1)))
                .type(getString(row.getCell(0)))
                .square(getNumeric(row.getCell(5)))
                .price((long) getNumeric(row.getCell(11)))
                .build();
    }

    private BuildingRawData parseGeneric(Row row) {
        if (isEmpty(row.getCell(0))) return null;

        return BuildingRawData.builder()
                .cadastrNumber(getString(row.getCell(0)))
                .type(getString(row.getCell(1)))
                .square(getNumeric(row.getCell(2)))
                .price((long) getNumeric(row.getCell(3)))
                .build();
    }

    private double parseSquare(String raw) {
        if (raw.isEmpty()) return 0;
        try {
            return Double.parseDouble(raw.split("/")[0].replace(",", ".").trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private long parsePrice(String raw) {
        if (raw.isEmpty()) return 0;
        try {
            return Long.parseLong(raw.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String getString(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            default -> "";
        };
    }

    private double getNumeric(Cell cell) {
        if (cell == null) return 0;
        return switch (cell.getCellType()) {
            case NUMERIC -> cell.getNumericCellValue();
            case STRING -> {
                try {yield Double.parseDouble(cell.getStringCellValue().replace(",", ".").replaceAll("[^0-9.]", ""));
                } catch (NumberFormatException e) {
                    yield 0;
                }
            }
            default -> 0;
        };
    }

    private boolean isEmpty(Cell cell) {
        return getString(cell).isEmpty();
    }
}
