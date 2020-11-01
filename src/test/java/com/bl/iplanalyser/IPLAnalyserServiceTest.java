package com.bl.iplanalyser;

import com.bl.csvbuilder.CSVException;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class IPLAnalyserServiceTest {
    private final static String MOST_RUNS_CSV_FILE_PATH = "./src/test/resources/IPL2019FactsheetMostRuns.csv";
    private final static String MOST_WKTS_CSV_FILE_PATH = ".src/test/resources/IPL2019FactsheetMostWkts.csv";

    @Test
    public void givenMostRunCsvFile_whenLoaded_returnCorrectRecords() {
        try {
            IPLAnalyserService iplAnalyserService = new IPLAnalyserService();
            int numOfRecords = iplAnalyserService.loadMostRunsData(MOST_RUNS_CSV_FILE_PATH);
            Assert.assertEquals(101, numOfRecords);
        } catch (CSVException e) {
            e.printStackTrace();
        }
    }

    //UC1
    @Test
    public void givenMostRunCsvFile_whenSortedByAvg_shouldReturnTopAvg() {
        try {
            IPLAnalyserService iplAnalyserService = new IPLAnalyserService();
            iplAnalyserService.loadMostRunsData(MOST_RUNS_CSV_FILE_PATH);
            String sortedMostRunByAvg = iplAnalyserService.getAvgWiseSortedRunsData();
            MostRunsCSV[] mostRunsCSV = new Gson().fromJson(sortedMostRunByAvg, MostRunsCSV[].class);
            Assert.assertEquals(83.2, mostRunsCSV[0].getAverage(), 0.0);
        } catch (CSVException e) {
            e.printStackTrace();
        }
    }

    // UC2 to top striking rate batting
    @Test
    public void givenMostRunCsvFile_whenSortedByStrikingRate_shouldReturnTopStrikingRate() {
        try {
            IPLAnalyserService iplAnalyserService = new IPLAnalyserService();
            iplAnalyserService.loadMostRunsData(MOST_RUNS_CSV_FILE_PATH);
            String sortedMostRunByStrikingRate = iplAnalyserService.getStrikingRateWiseSortedRunsData();
            MostRunsCSV[] mostRunsCSV = new Gson().fromJson(sortedMostRunByStrikingRate, MostRunsCSV[].class);
            Assert.assertEquals(333.33, mostRunsCSV[0].getSr(), 0.0);
        } catch (CSVException e) {
            e.printStackTrace();
        }
    }

    // UC3
    @Test
    public void givenMostRunCsvFile_whenSortedBySix_shouldReturnTopBatsman() {
        try {
            IPLAnalyserService iplAnalyserService = new IPLAnalyserService();
            iplAnalyserService.loadMostRunsData(MOST_RUNS_CSV_FILE_PATH);
            String sortedMostRunByNumOfSix = iplAnalyserService.getSixWiseSortedRunsData();
            MostRunsCSV[] mostRunsCSV = new Gson().fromJson(sortedMostRunByNumOfSix, MostRunsCSV[].class);
            Assert.assertEquals("Andre Russell", mostRunsCSV[0].getPlayer());
        } catch (CSVException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenMostRunCsvFile_whenSortedByFour_shouldReturnTopBatsman() {
        try {
            IPLAnalyserService iplAnalyserService = new IPLAnalyserService();
            iplAnalyserService.loadMostRunsData(MOST_RUNS_CSV_FILE_PATH);
            String sortedMostRunByNumOfFour = iplAnalyserService.getFourWiseSortedRunsData();
            MostRunsCSV[] mostRunsCSV = new Gson().fromJson(sortedMostRunByNumOfFour, MostRunsCSV[].class);
            Assert.assertEquals("Shikhar Dhawan", mostRunsCSV[0].getPlayer());
        } catch (CSVException e) {
            e.printStackTrace();
        }
    }

    //UC4
    @Test
    public void givenMostRunCsvFile_whenSortedByFourSixSR_shouldReturnTopBatsman() {
        try {
            IPLAnalyserService iplAnalyserService = new IPLAnalyserService();
            iplAnalyserService.loadMostRunsData(MOST_RUNS_CSV_FILE_PATH);
            List<MostRunsCSV> sortedMostRunByNumOfFour = iplAnalyserService.getFourSixSRWiseSortedRunsData();
            Assert.assertEquals("Andre Russell", sortedMostRunByNumOfFour.get(0).getPlayer());
        } catch (CSVException e) {
            e.printStackTrace();
        }
    }
}
