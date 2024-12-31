package veriProject;

import java.util.Scanner;

public class Resim {
    public String ad;
    public String ressamAd;
    public String tur;
    public String donem;

    public Resim(String ad, String ressamAd, String tur, String donem) {
        this.ad = ad;
        this.ressamAd = ressamAd;
        this.tur = tur;
        this.donem = donem;
    }

    @Override
    public String toString() {
        return ad + " - " + ressamAd + " (" + tur + ", " + donem + ")";
    }
}
