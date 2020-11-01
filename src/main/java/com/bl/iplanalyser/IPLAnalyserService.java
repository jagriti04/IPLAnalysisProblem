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
    List<MostWicketsCSV> mostWicketsCSVList = null;

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
    public int loadMostWicketsData(String csvFilePath) throws CSVException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            mostWicketsCSVList = csvBuilder.getCSVFileList(reader, MostWicketsCSV.class);
            return mostWicketsCSVList.size();
        } catch (IOException e) {
            throw new CSVException(e.getMessage(),
                    CSVException.ExceptionType.CSV_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CSVException(e.getMessage(), CSVException.ExceptionType.WRONG_CSV_FILE);
        }
    }
    public String getAvgWiseSortedRunsData() throws CSVException {
        checkIfListEmpty();
        Comparator<MostRunsCSV> runDataComparator = Comparator.comparing(MostRunsCSV::getAverage).reversed();
        mostRunsCSVList = this.descendingOrderSort(runDataComparator, mostRunsCSVList);
        String sortedAvgRunsDataJson = new Gson().toJson(mostRunsCSVList);
        return sortedAvgRunsDataJson;
    }

    public String getStrikingRateWiseSortedRunsData() throws CSVException {
        checkIfListEmpty();
        Comparator<MostRunsCSV> runsCSVComparator = Comparator.comparing(MostRunsCSV::getSr).reversed();
        mostRunsCSVList = this.descendingOrderSort(runsCSVComparator, mostRunsCSVList);
        String sortedSRRunsDataJson = new Gson().toJson(mostRunsCSVList);
        return sortedSRRunsDataJson;
    }

    public String getSixWiseSortedRunsData() throws CSVException {
        checkIfListEmpty();
        Comparator<MostRunsCSV> runsCSVComparator = Comparator.comparing(MostRunsCSV::getNum6s).reversed();
        mostRunsCSVList = this.descendingOrderSort(runsCSVComparator, mostRunsCSVList);
        String sortedNumSixRunsDataJson = new Gson().toJson(mostRunsCSVList);
        return sortedNumSixRunsDataJson;
    }

    public String getFourWiseSortedRunsData() throws CSVException {
        checkIfListEmpty();
        Comparator<MostRunsCSV> runsCSVComparator = Comparator.comparing(MostRunsCSV::getNum4s).reversed();
        mostRunsCSVList = this.descendingOrderSort(runsCSVComparator, mostRunsCSVList);
        String sortedNumFourRunsDataJson = new Gson().toJson(mostRunsCSVList);
        return sortedNumFourRunsDataJson;
    }

    public List<MostRunsCSV> getFourSixSRWiseSortedRunsData() throws CSVException {
        checkIfListEmpty();
        int maxNumSixFour = mostRunsCSVList.stream()
                .map(runsData -> runsData.num4s + runsData.num6s)
                .max(Integer::compare).get();
        List<MostRunsCSV> maxSixFoursList = mostRunsCSVList.stream()
                .filter(runsData -> runsData.num4s + runsData.num6s == maxNumSixFour)
                .collect(Collectors.toList());

        double MaxStrikeRate = maxSixFoursList.stream()
                .map(runsData -> runsData.sr)
                .max(Double::compare).get();

        List<MostRunsCSV> maxStrikeRateList = maxSixFoursList.stream().filter(i -> i.sr == MaxStrikeRate)
                .collect(Collectors.toList());

        return maxStrikeRateList;
    }

    public String getAvgSrWiseSortedRunsData() throws CSVException {
        checkIfListEmpty();
        Comparator<MostRunsCSV> runsCSVComparator = Comparator.comparing(MostRunsCSV::getAverage)
                .thenComparing(MostRunsCSV::getSr)
                .reversed();
        mostRunsCSVList = this.descendingOrderSort(runsCSVComparator, mostRunsCSVList);
        String sortedByAvgSRRunsDataJson = new Gson().toJson(mostRunsCSVList);
        return sortedByAvgSRRunsDataJson;
    }

    //UC6
    public String getRunsAndAvgWiseSortedRunsData() throws CSVException {
        checkIfListEmpty();
        Comparator<MostRunsCSV> runsCSVComparator = Comparator.comparing(MostRunsCSV::getRuns)
                .thenComparing(MostRunsCSV::getAverage)
                .reversed();
        mostRunsCSVList = this.descendingOrderSort(runsCSVComparator, mostRunsCSVList);
        String sortedByRunsWithAvgRunsDataJson = new Gson().toJson(mostRunsCSVList);
        return sortedByRunsWithAvgRunsDataJson;
    }

    private <E> List<E> descendingOrderSort(Comparator<E> comparator, List<E> list) {
        List<E> reverseSorted = list.stream().sorted(comparator).collect(Collectors.toList());
        return reverseSorted;
    }

    private void checkIfListEmpty() throws CSVException {
        if (mostRunsCSVList == null || mostRunsCSVList.size() == 0) {
            throw new CSVException("No Runs Data", CSVException.ExceptionType.NO_CSV_DATA);
        }
    }

}
