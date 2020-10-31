package com.bl.iplanalyser;

import com.bl.csvbuilder.CSVBuilderFactory;
import com.bl.csvbuilder.CSVException;
import com.bl.csvbuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class IPLAnalyserService {
    List<MostRunsCSV> mostRunsCSVList = null;

    public void printWelcomeMsg() {
        System.out.println("Welcome to IPL Analyser system.");
    }

    public int loadMostRunsData(String csvFilePath) throws CSVException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            mostRunsCSVList = csvBuilder.getCSVFileList(reader, MostRunsCSV.class);
            return mostRunsCSVList.size();
        } catch (IOException e) {
            throw new CSVException(e.getMessage(),
                    CSVException.ExceptionType.CSV_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CSVException(e.getMessage(), CSVException.ExceptionType.WRONG_CSV_FILE);
        }
    }
}
