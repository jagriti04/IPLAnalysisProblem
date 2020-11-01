package com.bl.iplanalyser;

import com.bl.csvbuilder.CSVException;
import com.google.gson.Gson;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class IPLAnalyserServiceTest {
    private final static String MOST_RUNS_CSV_FILE_PATH = "./src/test/resources/IPL2019FactsheetMostRuns.csv";
    private final static String MOST_WKTS_CSV_FILE_PATH = "./src/test/resources/IPL2019FactsheetMostWkts.csv";

    private IPLAnalyserService iplAnalyserService = null;

    @Before
    public void initializeVariables() throws Exception {
        iplAnalyserService = new IPLAnalyserService();
    }
    @Test
    public void givenMostRunCsvFile_whenLoaded_returnCorrectRecords() {
        try {
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
            iplAnalyserService.loadMostRunsData(MOST_RUNS_CSV_FILE_PATH);
            List<MostRunsCSV> sortedMostRunByFourSixSr = iplAnalyserService.getFourSixSRWiseSortedRunsData();
            Assert.assertEquals("Andre Russell", sortedMostRunByFourSixSr.get(0).getPlayer());
        } catch (CSVException e) {
            e.printStackTrace();
        }
    }

    //UC5
    @Test
    public void givenMostRunCsvFile_whenSortedByAvgAndSR_shouldReturnTopBatsman() {
        try {
            iplAnalyserService.loadMostRunsData(MOST_RUNS_CSV_FILE_PATH);
            String sortedMostRunByAvgSr = iplAnalyserService.getAvgSrWiseSortedRunsData();
            MostRunsCSV[] mostRunsCSV = new Gson().fromJson(sortedMostRunByAvgSr, MostRunsCSV[].class);
            Assert.assertEquals("MS Dhoni", mostRunsCSV[0].getPlayer());
        } catch (CSVException e) {
            e.printStackTrace();
        }
    }

    //UC6
    @Test
    public void givenMostRunCsvFile_whenSortedByRunsAndAvg_shouldReturnTopBatsman() {
        try {
            iplAnalyserService.loadMostRunsData(MOST_RUNS_CSV_FILE_PATH);
            String sortedMostRunByRunsWithAvg = iplAnalyserService.getRunsAndAvgWiseSortedRunsData();
            MostRunsCSV[] mostRunsCSV = new Gson().fromJson(sortedMostRunByRunsWithAvg, MostRunsCSV[].class);
            Assert.assertEquals("David Warner", mostRunsCSV[0].getPlayer());
        } catch (CSVException e) {
            e.printStackTrace();
        }
    }

    //check load wkts file
    @Test
    public void givenMostWicketsCsvFile_whenLoaded_returnCorrectRecords() {
        try {
            int numOfRecords = iplAnalyserService.loadMostWicketsData(MOST_WKTS_CSV_FILE_PATH);
            Assert.assertEquals(99, numOfRecords);
        } catch (CSVException e) {
            e.printStackTrace();
        }
    }

    //UC7 top bowling averages of the Cricketers
    @Test
    public void givenMostWktsCsvFile_whenSortedByBowlingAvg_shouldReturnTopBowler() {
        try {
            iplAnalyserService.loadMostWicketsData(MOST_WKTS_CSV_FILE_PATH);
            String sortedMostWicketsByBowlingAvg = iplAnalyserService.getBowlingAvgWiseSortedBowlingData();
            MostWicketsCSV[] mostWicketsCSV = new Gson().fromJson(sortedMostWicketsByBowlingAvg, MostWicketsCSV[].class);
            Assert.assertEquals("Krishnappa Gowtham", mostWicketsCSV[0].getPlayer());
        } catch (CSVException e) {
            e.printStackTrace();
        }
    }

    //UC8 top Striking Rates of the Bowlers
    @Test
    public void givenMostWktsCsvFile_whenSortedByBowlingStrikingRate_shouldReturnTopBowler() {
        try {
            iplAnalyserService.loadMostWicketsData(MOST_WKTS_CSV_FILE_PATH);
            String sortedMostWicketsDataByBowlingSR = iplAnalyserService.getBowlingSRWiseSortedBowlingData();
            MostWicketsCSV[] mostWicketsCSV = new Gson().fromJson(sortedMostWicketsDataByBowlingSR, MostWicketsCSV[].class);
            Assert.assertEquals("Krishnappa Gowtham", mostWicketsCSV[0].getPlayer());
        } catch (CSVException e) {
            e.printStackTrace();
        }
    }

    //UC9 the Bowlers who had the best economy rate
    @Test
    public void givenMostWktsCsvFile_whenSortedByEconomyRate_shouldReturnTopBowler() {
        try {
            iplAnalyserService.loadMostWicketsData(MOST_WKTS_CSV_FILE_PATH);
            String sortedMostWicketsDataByBowlingER = iplAnalyserService.getBowlingERWiseSortedBowlingData();
            MostWicketsCSV[] mostWicketsCSV = new Gson().fromJson(sortedMostWicketsDataByBowlingER, MostWicketsCSV[].class);
            Assert.assertEquals("Ben Cutting", mostWicketsCSV[0].getPlayer());
        } catch (CSVException e) {
            e.printStackTrace();
        }
    }

    //UC10 Cricketers who had best striking rates with 5w and 4w
    @Test
    public void givenMostWktsCsvFile_whenSortedByBestSRWith5WAnd4W_shouldReturnTopBowler() {
        try {
            iplAnalyserService.loadMostWicketsData(MOST_WKTS_CSV_FILE_PATH);
            String sortedMostWicketsDataBySRWith5WAnd4W = iplAnalyserService.getSRWith5WAnd4WWiseSortedBowlingData();
            MostWicketsCSV[] mostWicketsCSV = new Gson().fromJson(sortedMostWicketsDataBySRWith5WAnd4W, MostWicketsCSV[].class);
            Assert.assertEquals("Krishnappa Gowtham", mostWicketsCSV[0].getPlayer());
        } catch (CSVException e) {
            e.printStackTrace();
        }
    }

    //UC11 Cricketers who had great bowling averages with the best striking rates
    @Test
    public void givenMostWicketsCsvFile_whenSortedByBestBowlingAvgWithSR_shouldReturnTopBowler() {
        try {
            iplAnalyserService.loadMostWicketsData(MOST_WKTS_CSV_FILE_PATH);
            String sortedMostWicketsDataByAvgWithSR = iplAnalyserService.getAvgWithSRWiseSortedBowlingData();
            MostWicketsCSV[] mostWicketsCSV = new Gson().fromJson(sortedMostWicketsDataByAvgWithSR, MostWicketsCSV[].class);
            Assert.assertEquals("Krishnappa Gowtham", mostWicketsCSV[0].getPlayer());
        } catch (CSVException e) {
            e.printStackTrace();
        }
    }

    //UC12 the Bowlers who took maximum wickets with best bowling averages
    @Test
    public void givenMostWicketsCsvFile_whenSortedByNumWicketsWithAvg_shouldReturnTopBowler() {
        try {
            iplAnalyserService.loadMostWicketsData(MOST_WKTS_CSV_FILE_PATH);
            String sortedMostWicketsDataByNumWicketsWithAvg = iplAnalyserService.getNumWicketsAndAvgWiseSortedBowlingData();
            MostWicketsCSV[] mostWicketsCSV = new Gson().fromJson(sortedMostWicketsDataByNumWicketsWithAvg, MostWicketsCSV[].class);
            Assert.assertEquals("Imran Tahir", mostWicketsCSV[0].getPlayer());
        } catch (CSVException e) {
            e.printStackTrace();
        }
    }

    //UC13 Cricketers who had the best Batting and Bowling averages
    @Test
    public void givenRunsAndWicketsData_whenSortedByBattingBowlingAvg_shouldReturnTopPlayer() {
        try {
            iplAnalyserService.loadMostRunsData(MOST_RUNS_CSV_FILE_PATH);
            iplAnalyserService.loadMostWicketsData(MOST_WKTS_CSV_FILE_PATH);
            List<String> sortedDataByBattingBowlingAvg = iplAnalyserService.getBattingBowlingAvgWiseSortedData();
            Assert.assertEquals("Andre Russell", sortedDataByBattingBowlingAvg.get(0));
        } catch (CSVException e) {
            e.printStackTrace();
        }
    }
}
