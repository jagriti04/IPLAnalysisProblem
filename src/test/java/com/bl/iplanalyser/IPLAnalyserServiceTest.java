package com.bl.iplanalyser;

import com.bl.csvbuilder.CSVException;
import org.junit.Assert;
import org.junit.Test;

public class IPLAnalyserServiceTest {
    private final static String MOST_RUNS_CSV_FILE_PATH = "./src/test/resources/IPL2019FactsheetMostRuns.csv";
    private final static String MOST_WKTS_CSV_FILE_PATH = ".src/test/resources/IPL2019FactsheetMostWkts.csv";

    @Test
    public void givenMostRunCsvFile_whenLoaded_returnCorrectRecords() {
        try {
            IPLAnalyserService censusAnalyser = new IPLAnalyserService();
            int numOfRecords = censusAnalyser.loadMostRunsData(MOST_RUNS_CSV_FILE_PATH);
            Assert.assertEquals(101, numOfRecords);
        } catch (CSVException e) {
            e.printStackTrace();
        }
    }

}
