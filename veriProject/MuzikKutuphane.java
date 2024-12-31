package veriProject;

import java.util.Scanner;
import java.util.LinkedList; //Oynatma listesi için
import java.util.Stack; //yapılan işlemleri kaydeder



public class MuzikKutuphane {
    private SanatciNode sanatciRoot;

    public class Sarki {
        public String ad; 
        public String sanatciAd; 
        public String albumAd; 

        public Sarki(String ad, String sanatciAd, String albumAd) {
            this.ad = ad;
            this.sanatciAd = sanatciAd;
            this.albumAd = albumAd;
        }

        @Override
        public String toString() {
            return ad + " - " + sanatciAd + " (" + albumAd + ")";
        }
    }
 
        private Stack<String> islemGecmisi = new Stack<>();
        private LinkedList<Sarki> oynatmaListesi = new LinkedList<>();


        public class SarkiNode {
            public Sarki sarki;
            public SarkiNode left;
            public SarkiNode right;

            public SarkiNode(Sarki sarki) {
                this.sarki = sarki;
                this.left = null;
                this.right = null;
            }
        }

        public class SanatciNode {
            public String ad;
            public SanatciNode left;
            public SanatciNode right;
            public SarkiNode sarkiRoot;

            public SanatciNode(String ad) {
                this.ad = ad;
                this.left = null;
                this.right = null;
                this.sarkiRoot = null;
            }
        }

        public void muzikKutuphaneMenu(Scanner scanner) {
            String komut;

            do {
                System.out.print("Müzik kütüphanesi komutları (ekle, sanatçılar, listele, oynatma, geri, undo): ");
                komut = scanner.nextLine().toLowerCase();

                switch (komut) {
                    case "ekle":
                        System.out.print("Sanatçı adı: ");
                        String sanatciAd = scanner.nextLine();
                        System.out.print("Şarkı adı: ");
                        String sarkiAd = scanner.nextLine();
                        System.out.print("Albüm adı: ");
                        String albumAd = scanner.nextLine();
                        Sarki yeniSarki = new Sarki(sarkiAd, sanatciAd, albumAd);
                        sarkiEkle(yeniSarki);
                        islemGecmisi.push("Şarkı eklendi: " + yeniSarki);
                        System.out.println("Şarkı eklendi!");
                        break;

                    case "sanatçılar":
                        sanatcilariListele();
                        break;

                    case "listele":
                        sarkilariListele();
                        break;

                    case "oynatma":
                        oynatmaListesiniYonet(scanner);
                        break;

                    case "geri":
                        System.out.println("Ana menüye dönülüyor...");
                        break;

                    case "undo":
                        if (!islemGecmisi.isEmpty()) {
                            System.out.println("Son işlem geri alındı: " + islemGecmisi.pop());
                        } else {
                            System.out.println("Geri alınacak işlem yok.");
                        }
                        break;

                    default:
                        System.out.println("Geçersiz komut, tekrar deneyin.");
                }
            } while (!komut.equals("geri"));
        }

        public void sarkiEkle(Sarki yeniSarki) {
            sanatciRoot = sanatciEkleRecursive(sanatciRoot, yeniSarki.sanatciAd);
            SanatciNode sanatciNode = sanatciBul(sanatciRoot, yeniSarki.sanatciAd);
            if (sanatciNode != null) {
                sanatciNode.sarkiRoot = sarkiEkleRecursive(sanatciNode.sarkiRoot, yeniSarki);
                oynatmaListesi.add(yeniSarki);
            }
        }

        private SanatciNode sanatciEkleRecursive(SanatciNode node, String sanatciAd) {
            if (node == null) {
                return new SanatciNode(sanatciAd);
            }
            if (sanatciAd.compareToIgnoreCase(node.ad) < 0) {
                node.left = sanatciEkleRecursive(node.left, sanatciAd);
            } else if (sanatciAd.compareToIgnoreCase(node.ad) > 0) {
                node.right = sanatciEkleRecursive(node.right, sanatciAd);
            }
            return node;
        }

        private SarkiNode sarkiEkleRecursive(SarkiNode node, Sarki yeniSarki) {
            if (node == null) {
                return new SarkiNode(yeniSarki);
            }
            if (yeniSarki.ad.compareToIgnoreCase(node.sarki.ad) < 0) {
                node.left = sarkiEkleRecursive(node.left, yeniSarki);
            } else if (yeniSarki.ad.compareToIgnoreCase(node.sarki.ad) > 0) {
                node.right = sarkiEkleRecursive(node.right, yeniSarki);
            }
            return node;
        }

        public SanatciNode sanatciBul(SanatciNode node, String sanatciAd) {
            if (node == null || node.ad.equalsIgnoreCase(sanatciAd)) {
                return node;
            }
            if (sanatciAd.compareToIgnoreCase(node.ad) < 0) {
                return sanatciBul(node.left, sanatciAd);
            }
            return sanatciBul(node.right, sanatciAd);
        }

        public void sanatcilariListele() {
            System.out.println("Sanatçılar:");
            sanatciInOrder(sanatciRoot);
        }

        private void sanatciInOrder(SanatciNode node) {
            if (node != null) {
                sanatciInOrder(node.left);
                System.out.println("- " + node.ad);
                sanatciInOrder(node.right);
            }
        }

        public void sarkilariListele() {
            System.out.println("Tüm şarkılar:");
            sarkilariInOrder(sanatciRoot);
        }

        private void sarkilariInOrder(SanatciNode sanatciNode) {
            if (sanatciNode != null) {
                sarkilariInOrder(sanatciNode.left);
                System.out.println("Sanatçı: " + sanatciNode.ad);
                sarkiInOrder(sanatciNode.sarkiRoot);
                sarkilariInOrder(sanatciNode.right);
            }
        }

        private void sarkiInOrder(SarkiNode node) {
            if (node != null) {
                sarkiInOrder(node.left);
                System.out.println("  - " + node.sarki);
                sarkiInOrder(node.right);
            }
        }

        private void oynatmaListesiniYonet(Scanner scanner) {
            System.out.println("Oynatma Listesi:");
            for (Sarki sarki : oynatmaListesi) {
                System.out.println("- " + sarki);
            }
        }
    }
