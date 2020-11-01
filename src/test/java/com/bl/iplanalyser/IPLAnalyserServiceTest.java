package com.bl.iplanalyser;

import com.bl.csvbuilder.CSVException;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

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

    // UC3 to top striking rate batting
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
}
