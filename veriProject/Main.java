
package veriProject;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        MuzikKutuphane muzikKutuphane = new MuzikKutuphane();
        ResimKutuphane resimKutuphane = new ResimKutuphane();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Sanat Tavsiye Platformu");
        String secim;

        do {
            System.out.print("Hangi kütüphaneyi kullanmak istersiniz? (müzik, resim, çıkış): ");
            secim = scanner.nextLine().toLowerCase();

            switch (secim) {
                case "müzik":
                    muzikKutuphane.muzikKutuphaneMenu(scanner);
                    break;

                case "resim":
                    resimKutuphane.resimKutuphaneMenu(scanner);
                    break;

                case "çıkış":
                    System.out.println("Programdan çıkılıyor...");
                    break;

                default:
                    System.out.println("Geçersiz seçim, tekrar deneyin.");
            }
        } while (!secim.equals("çıkış"));

        scanner.close();
    }
}