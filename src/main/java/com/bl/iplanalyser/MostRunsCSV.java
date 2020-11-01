package com.bl.iplanalyser;

import com.opencsv.bean.CsvBindByName;

public class MostRunsCSV {
    @CsvBindByName(column = "POS")
    public int pos;

    @CsvBindByName(column = "PLAYER")
    public String player;

    @CsvBindByName(column = "Mat")
    public int mat;

    @CsvBindByName(column = "Inns")
    public int inns;

    @CsvBindByName(column = "No")
    public int no;

    @CsvBindByName(column = "Runs")
    public int runs;

    @CsvBindByName(column = "HS")
    public String hs;

    @CsvBindByName(column = "Avg")
    public String avg;

    @CsvBindByName(column = "BF")
    public int bf;

    @CsvBindByName(column = "SR")
    public double sr;

    @CsvBindByName(column = "100")
    public int num100;

    @CsvBindByName(column = "50")
    public int num50;

    @CsvBindByName(column = "4s")
    public int num4s;

    @CsvBindByName(column = "6s")
    public int num6s;

    public String getPlayer() {
        return player;
    }

    public int getMat() {
        return mat;
    }

    public int getInns() {
        return inns;
    }

    public int getNo() {
        return no;
    }

    public int getRuns() {
        return runs;
    }

    public String getHs() {
        return hs;
    }

    public int getBf() {
        return bf;
    }

    public double getSr() {
        return sr;
    }

    public int getNum100() {
        return num100;
    }

    public int getNum50() {
        return num50;
    }

    public int getNum4s() {
        return num4s;
    }

    public int getNum6s() {
        return num6s;
    }

    public double getAverage() {
        if (avg.equals("-"))
            return 0.0;
        return Double.parseDouble(avg);
    }

    @Override
    public String toString() {
        return "MostRunCSV{" + "POS=" + pos + ", Player=" + player + ", Mat=" + mat + ", Inns=" + inns + ", No=" + no
                + ", Runs=" + runs + ", HS=" + hs + ", Avg=" + getAverage() + ", BF=" + bf + ", SR=" + sr + ", 100="
                + num100 + ", 50=" + num50 + ", 4s=" + num4s + ", 6s=" + num6s + '}';
    }
}
