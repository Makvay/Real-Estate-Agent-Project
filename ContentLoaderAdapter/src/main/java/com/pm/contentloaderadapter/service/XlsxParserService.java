package com.pm.contentloaderadapter.service;

import com.pm.contentloaderadapter.domain.dto.BuildingRawData;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class XlsxParserService {

    private static final int DOMCLICK_CADASTR = 16;
    private static final int DOMCLICK_TYPE = 1;
    private static final int DOMCLICK_SQUARE = 5;
    private static final int DOMCLICK_PRICE = 8;

    private static final int ROSREESTR_CADASTR = 1;
    private static final int ROSREESTR_TYPE = 0;
    private static final int ROSREESTR_SQUARE = 5;
    private static final int ROSREESTR_PRICE = 11;

    private static final int GENERIC_CADASTR = 0;
    private static final int GENERIC_TYPE = 1;
    private static final int GENERIC_SQUARE = 2;
    private static final int GENERIC_PRICE = 3;

    public List<BuildingRawData> parse(MultipartFile file, String source) {
        if (source != null && source.contains(",")) {
            source = source.split(",")[0].trim();
        }

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
        if (isEmpty(row.getCell(DOMCLICK_CADASTR))) return null;

        return BuildingRawData.builder()
                .cadastrNumber(getString(row.getCell(DOMCLICK_CADASTR)))
                .type(getString(row.getCell(DOMCLICK_TYPE)))
                .square(parseSquare(getString(row.getCell(DOMCLICK_SQUARE))))
                .price(parsePrice(getString(row.getCell(DOMCLICK_PRICE))))
                .build();
    }

    private BuildingRawData parseRosreestr(Row row) {
        if (isEmpty(row.getCell(ROSREESTR_CADASTR))) return null;

        return BuildingRawData.builder()
                .cadastrNumber(getString(row.getCell(ROSREESTR_CADASTR)))
                .type(getString(row.getCell(ROSREESTR_TYPE)))
                .square(getNumeric(row.getCell(ROSREESTR_SQUARE)))
                .price((long) getNumeric(row.getCell(ROSREESTR_PRICE)))
                .build();
    }

    private BuildingRawData parseGeneric(Row row) {
        if (isEmpty(row.getCell(GENERIC_CADASTR))) return null;

        return BuildingRawData.builder()
                .cadastrNumber(getString(row.getCell(GENERIC_CADASTR)))
                .type(getString(row.getCell(GENERIC_TYPE)))
                .square(getNumeric(row.getCell(GENERIC_SQUARE)))
                .price((long) getNumeric(row.getCell(GENERIC_PRICE)))
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
