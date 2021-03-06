package com.bl.iplanalyser;

import com.bl.csvbuilder.CSVBuilderFactory;
import com.bl.csvbuilder.CSVException;
import com.bl.csvbuilder.ICSVBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
        checkIfRunsListEmpty();
        Comparator<MostRunsCSV> runDataComparator = Comparator.comparing(MostRunsCSV::getAverage).reversed();
        mostRunsCSVList = this.descendingOrderSort(runDataComparator, mostRunsCSVList);
        String sortedAvgRunsDataJson = new Gson().toJson(mostRunsCSVList);
        return sortedAvgRunsDataJson;
    }

    public String getStrikingRateWiseSortedRunsData() throws CSVException {
        checkIfRunsListEmpty();
        Comparator<MostRunsCSV> runsCSVComparator = Comparator.comparing(MostRunsCSV::getSr).reversed();
        mostRunsCSVList = this.descendingOrderSort(runsCSVComparator, mostRunsCSVList);
        String sortedSRRunsDataJson = new Gson().toJson(mostRunsCSVList);
        return sortedSRRunsDataJson;
    }

    public String getSixWiseSortedRunsData() throws CSVException {
        checkIfRunsListEmpty();
        Comparator<MostRunsCSV> runsCSVComparator = Comparator.comparing(MostRunsCSV::getNum6s).reversed();
        mostRunsCSVList = this.descendingOrderSort(runsCSVComparator, mostRunsCSVList);
        String sortedNumSixRunsDataJson = new Gson().toJson(mostRunsCSVList);
        return sortedNumSixRunsDataJson;
    }

    public String getFourWiseSortedRunsData() throws CSVException {
        checkIfRunsListEmpty();
        Comparator<MostRunsCSV> runsCSVComparator = Comparator.comparing(MostRunsCSV::getNum4s).reversed();
        mostRunsCSVList = this.descendingOrderSort(runsCSVComparator, mostRunsCSVList);
        String sortedNumFourRunsDataJson = new Gson().toJson(mostRunsCSVList);
        return sortedNumFourRunsDataJson;
    }

    public List<MostRunsCSV> getFourSixSRWiseSortedRunsData() throws CSVException {
        checkIfRunsListEmpty();
        int maxNumSixFour = mostRunsCSVList.stream()
                .map(runsData -> runsData.num4s + runsData.num6s)
                .max(Integer::compare).get();
        List<MostRunsCSV> maxSixFoursList = mostRunsCSVList.stream()
                .filter(runsData -> runsData.num4s + runsData.num6s == maxNumSixFour)
                .collect(Collectors.toList());

        double MaxStrikeRate = maxSixFoursList.stream()
                .map(runsData -> runsData.sr)
                .max(Double::compare).get();

        List<MostRunsCSV> maxStrikeRateList = maxSixFoursList.stream().filter(runsData -> runsData.sr == MaxStrikeRate)
                .collect(Collectors.toList());

        return maxStrikeRateList;
    }

    public String getAvgSrWiseSortedRunsData() throws CSVException {
        checkIfRunsListEmpty();
        Comparator<MostRunsCSV> runsCSVComparator = Comparator.comparing(MostRunsCSV::getAverage)
                .thenComparing(MostRunsCSV::getSr)
                .reversed();
        mostRunsCSVList = this.descendingOrderSort(runsCSVComparator, mostRunsCSVList);
        String sortedByAvgSRRunsDataJson = new Gson().toJson(mostRunsCSVList);
        return sortedByAvgSRRunsDataJson;
    }

    //UC6
    public String getRunsAndAvgWiseSortedRunsData() throws CSVException {
        checkIfRunsListEmpty();
        Comparator<MostRunsCSV> runsCSVComparator = Comparator.comparing(MostRunsCSV::getRuns)
                .thenComparing(MostRunsCSV::getAverage)
                .reversed();
        mostRunsCSVList = this.descendingOrderSort(runsCSVComparator, mostRunsCSVList);
        String sortedByRunsWithAvgRunsDataJson = new Gson().toJson(mostRunsCSVList);
        return sortedByRunsWithAvgRunsDataJson;
    }

    // bowlers avg sorting
    public String getBowlingAvgWiseSortedBowlingData() throws CSVException {
        checkIfWicketsListEmpty();
        Comparator<MostWicketsCSV> bowlingCSVComparator = Comparator.comparing(MostWicketsCSV::getAvg)
                .reversed();
        mostWicketsCSVList = this.descendingOrderSort(bowlingCSVComparator, mostWicketsCSVList);
        String sortedByAvgBowlingDataJson = new Gson().toJson(mostWicketsCSVList);
        return sortedByAvgBowlingDataJson;
    }

    // bowlers striking rate sorting
    public String getBowlingSRWiseSortedBowlingData() throws CSVException {
        checkIfWicketsListEmpty();
        Comparator<MostWicketsCSV> bowlingCSVComparator = Comparator.comparing(MostWicketsCSV::getSr)
                .reversed();
        mostWicketsCSVList = this.descendingOrderSort(bowlingCSVComparator, mostWicketsCSVList);
        String sortedBySRBowlingDataJson = new Gson().toJson(mostWicketsCSVList);
        return sortedBySRBowlingDataJson;
    }

    // bowlers economy rate sorting
    public String getBowlingERWiseSortedBowlingData() throws CSVException {
        checkIfWicketsListEmpty();
        Comparator<MostWicketsCSV> bowlingCSVComparator = Comparator.comparing(MostWicketsCSV::getEcon)
                .reversed();
        mostWicketsCSVList = this.descendingOrderSort(bowlingCSVComparator, mostWicketsCSVList);
        String sortedBySRBowlingDataJson = new Gson().toJson(mostWicketsCSVList);
        return sortedBySRBowlingDataJson;
    }

    // bowlers Striking rate with 5W and 4W sorting
    public String getSRWith5WAnd4WWiseSortedBowlingData() throws CSVException {
        checkIfWicketsListEmpty();
        Comparator<MostWicketsCSV> bowlingCSVComparator = Comparator.comparing(MostWicketsCSV::getSr)
                .thenComparing(MostWicketsCSV::getNum5w)
                .thenComparing(MostWicketsCSV::getNum4w)
                .reversed();
        mostWicketsCSVList = this.descendingOrderSort(bowlingCSVComparator, mostWicketsCSVList);
        String sortedBySRBowlingDataJson = new Gson().toJson(mostWicketsCSVList);
        return sortedBySRBowlingDataJson;
    }

    // bowlers avg and striking rate sorting
    public String getAvgWithSRWiseSortedBowlingData() throws CSVException {
        checkIfWicketsListEmpty();
        Comparator<MostWicketsCSV> bowlingCSVComparator = Comparator.comparing(MostWicketsCSV::getAvg)
                .thenComparing(MostWicketsCSV::getSr)
                .reversed();
        mostWicketsCSVList = this.descendingOrderSort(bowlingCSVComparator, mostWicketsCSVList);
        String sortedBySRBowlingDataJson = new Gson().toJson(mostWicketsCSVList);
        return sortedBySRBowlingDataJson;
    }

    //Bowling number of wickets and bowling average sorting
    public String getNumWicketsAndAvgWiseSortedBowlingData() throws CSVException {
        checkIfWicketsListEmpty();
        Comparator<MostWicketsCSV> bowlingCSVComparator = Comparator.comparing(MostWicketsCSV::getWkts)
                .thenComparing(MostWicketsCSV::getAvg)
                .reversed();
        mostWicketsCSVList = this.descendingOrderSort(bowlingCSVComparator, mostWicketsCSVList);
        String sortedBySRBowlingDataJson = new Gson().toJson(mostWicketsCSVList);
        return sortedBySRBowlingDataJson;
    }

    //Batting and bowling average sorting
    public List<String> getBattingBowlingAvgWiseSortedData() throws CSVException {
        checkIfRunsListEmpty();
        checkIfWicketsListEmpty();

        List<String> battingBowlingSortedList = new ArrayList<>();
        Comparator<MostRunsCSV> battingCSVComparator = Comparator.comparing(MostRunsCSV::getAverage).reversed();
        mostRunsCSVList = this.descendingOrderSort(battingCSVComparator, mostRunsCSVList);

        Comparator<MostWicketsCSV> bowlingCSVComparator = Comparator.comparing(MostWicketsCSV::getAvg).reversed();
        mostWicketsCSVList = this.descendingOrderSort(bowlingCSVComparator, mostWicketsCSVList);

        for (MostRunsCSV battingData : mostRunsCSVList) {
            for (MostWicketsCSV bowlingData : mostWicketsCSVList) {
                if (battingData.getPlayer().equals(bowlingData.getPlayer())) {
                    battingBowlingSortedList.add(battingData.getPlayer());
                }
            }
        }
        return battingBowlingSortedList;
    }

    //number runs and wickets sorting
    public List<String> getRunsAndWicketsWiseSortedBowlingData() throws CSVException {
        checkIfRunsListEmpty();
        checkIfWicketsListEmpty();

        List<String> runsWicketsWiseSortedList = new ArrayList<>();
        Comparator<MostRunsCSV> battingCSVComparator = Comparator.comparing(MostRunsCSV::getRuns).reversed();
        mostRunsCSVList = this.descendingOrderSort(battingCSVComparator, mostRunsCSVList);

        Comparator<MostWicketsCSV> bowlingCSVComparator = Comparator.comparing(MostWicketsCSV::getWkts).reversed();
        mostWicketsCSVList = this.descendingOrderSort(bowlingCSVComparator, mostWicketsCSVList);

        for (MostRunsCSV battingData : mostRunsCSVList) {
            for (MostWicketsCSV bowlingData : mostWicketsCSVList) {
                if (battingData.getPlayer().equals(bowlingData.getPlayer())) {
                    runsWicketsWiseSortedList.add(battingData.getPlayer());
                }
            }
        }
        return runsWicketsWiseSortedList;
    }

    // number of hundreds and batting average sorting
    public String getNumHundredsAndAvgWiseSortedRunsData() throws CSVException {
        checkIfRunsListEmpty();
        Comparator<MostRunsCSV> runsCSVComparator = Comparator.comparing(MostRunsCSV::getNum100)
                .thenComparing(MostRunsCSV::getAverage)
                .reversed();
        mostRunsCSVList = this.descendingOrderSort(runsCSVComparator, mostRunsCSVList);
        String sortedByAvgSRRunsDataJson = new Gson().toJson(mostRunsCSVList);
        return sortedByAvgSRRunsDataJson;
    }

    // players with zero hundreds and fifty but best average
    public String getBatsmanWithZeroHundredFiftyAndMaxAvg() throws CSVException {
        checkIfRunsListEmpty();
        List<MostRunsCSV> zeroHundredFiftyList = mostRunsCSVList.stream()
                .filter(runsData -> runsData.getNum100() == 0 && runsData.getNum50() == 0)
                .collect(Collectors.toList());
        Comparator<MostRunsCSV> runsCSVComparator = Comparator.comparing(MostRunsCSV::getAverage)
                .reversed();
        mostRunsCSVList = this.descendingOrderSort(runsCSVComparator, zeroHundredFiftyList);
        String sortedByAvgSRRunsDataJson = new Gson().toJson(mostRunsCSVList);
        return sortedByAvgSRRunsDataJson;
    }

    private <E> List<E> descendingOrderSort(Comparator<E> comparator, List<E> list) {
        List<E> reverseSorted = list.stream().sorted(comparator).collect(Collectors.toList());
        return reverseSorted;
    }

    private void checkIfRunsListEmpty() throws CSVException {
        if (mostRunsCSVList == null || mostRunsCSVList.size() == 0) {
            throw new CSVException("No Runs Data", CSVException.ExceptionType.NO_CSV_DATA);
        }
    }

    private void checkIfWicketsListEmpty() throws CSVException {
        if (mostWicketsCSVList == null || mostWicketsCSVList.size() == 0) {
            throw new CSVException("No Runs Data", CSVException.ExceptionType.NO_CSV_DATA);
        }
    }

}
