package com.bl.iplanalyser;

import com.opencsv.bean.CsvBindByName;

public class MostWicketsCSV {
    @CsvBindByName(column = "POS")
    public int pos;

    @CsvBindByName(column = "PLAYER")
    public String player;

    @CsvBindByName(column = "Mat")
    public int mat;

    @CsvBindByName(column = "Inns")
    public int inns;

    @CsvBindByName(column = "Ov")
    public double ov;

    @CsvBindByName(column = "Runs")
    public int runs;

    @CsvBindByName(column = "Wkts")
    public int wkts;

    @CsvBindByName(column = "BBI")
    public int bbi;

    @CsvBindByName(column = "Avg")
    public String avg;

    @CsvBindByName(column = "Econ")
    public double econ;

    @CsvBindByName(column = "SR")
    public String sr;

    @CsvBindByName(column = "4w")
    public int num4w;

    @CsvBindByName(column = "5w")
    public int num5w;

    public int getPos() {
        return pos;
    }

    public String getPlayer() {
        return player;
    }

    public int getMat() {
        return mat;
    }

    public int getInns() {
        return inns;
    }

    public double getOv() {
        return ov;
    }

    public int getRuns() {
        return runs;
    }

    public int getWkts() {
        return wkts;
    }

    public int getBbi() {
        return bbi;
    }

    public String getAvg() {
        return avg;
    }

    public double getEcon() {
        return econ;
    }

    public String getSr() {
        return sr;
    }

    public int getNum4w() {
        return num4w;
    }

    public int getNum5w() {
        return num5w;
    }

    @Override
    public String toString() {
        return "MostRunCSV{" + "POS=" + pos + ", Player=" + player + ", Mat=" + mat + ", Inns=" + inns + ", Ov=" + ov
                + ", Runs=" + runs + ", Wkts=" + wkts + ", BBI=" + bbi + ", Avg=" + avg + ", Econ=" + econ + ", SR="
                + sr + ", 4w=" + num4w + ", 5w=" + num5w + '}';
    }
}
