package com.bl.iplanalyser;

import com.bl.csvbuilder.CSVBuilderFactory;
import com.bl.csvbuilder.CSVException;
import com.bl.csvbuilder.ICSVBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public String getAvgWiseSortedRunsData() throws CSVException {
        if (mostRunsCSVList == null || mostRunsCSVList.size() == 0) {
            throw new CSVException("No Runs Data", CSVException.ExceptionType.NO_CSV_DATA);
        }
        Comparator<MostRunsCSV> runDataComparator = Comparator.comparing((MostRunsCSV::getAverage)).reversed();
        this.descendingOrderSort(runDataComparator, mostRunsCSVList);
        String sortedAvgRunsDataJson = new Gson().toJson(mostRunsCSVList);
        return sortedAvgRunsDataJson;
    }

    private <E> void descendingOrderSort(Comparator<E> comparator, List<E> list) {
        List<E> reverseSorted = list.stream().sorted(comparator).collect(Collectors.toList());
        list = reverseSorted;
    }
}
