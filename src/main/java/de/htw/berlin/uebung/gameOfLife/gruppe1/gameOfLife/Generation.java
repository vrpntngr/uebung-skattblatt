package de.htw.berlin.uebung.gameOfLife.gruppe1.gameOfLife;

import java.util.HashSet;
import java.util.Set;

public class Generation {

    Zelle[][] generation;

    public Generation(int groesse) {
        this.generation = new Zelle[groesse][groesse];
        initialisieren();
        setLebendigeZellen();
    }

    public Generation(int groesse, boolean standardInitialisieren) {
        if (standardInitialisieren) {
            new Generation(groesse);
        } else {
            this.generation = new Zelle[groesse][groesse];
            initialisieren();
        }
    }

    public void setCostumLebendeZellen(int zeile, int spalte) {
        generation[zeile][spalte] = Zelle.LEBENDIG;
    }

    private void initialisieren() {
        for (int zeile = 0; zeile < generation.length; zeile++) {
            for (int spalte = 0; spalte < generation.length; spalte++) {
                generation[zeile][spalte] = Zelle.TOT;
            }
        }
    }

    private void setLebendigeZellen() {
        generation[1][1] = Zelle.LEBENDIG;
        generation[2][1] = Zelle.LEBENDIG;
        generation[3][1] = Zelle.LEBENDIG;
        generation[1][4] = Zelle.LEBENDIG;
        generation[2][4] = Zelle.LEBENDIG;
        generation[3][4] = Zelle.LEBENDIG;
    }

    public void simuliereNaechsteGeneration() {
        Zelle[][] naechsteGeneration = new Zelle[generation.length][generation.length];
        for (int zeile = 0; zeile < generation.length; zeile++) {
            for (int spalte = 0; spalte < generation.length; spalte++) {
                // anzahl der Nachbarn zählen
                int anzahlLebenderNachbar = anzahlLebenderNachbar(zeile, spalte);
                // Zelle nach nächsten STatus fragen
                Zelle zelleInDerAktuellenGeneration = generation[zeile][spalte];
                Zelle zelleInDerNaechstenGeneration = zelleInDerAktuellenGeneration.simuliereNaechsteGeneration(anzahlLebenderNachbar);
                naechsteGeneration[zeile][spalte] = zelleInDerNaechstenGeneration;
            }
        }
        generation = naechsteGeneration;
    }

    int anzahlLebenderNachbar(int zeile, int spalte) {
        int anzahlLebenderNachbar = 0;
        for (int pruefZeile = -1; pruefZeile <= 1; pruefZeile++) {
            for (int pruefSpalte = -1; pruefSpalte <= 1; pruefSpalte++) {
                int nachbarZeile = zeile + pruefZeile;
                int nachbarSpalte = spalte + pruefSpalte;
                if (istImArray(nachbarZeile, nachbarSpalte)) {
                    if (istNachbar(zeile, spalte, nachbarZeile, nachbarSpalte)) {
                        if (generation[nachbarZeile][nachbarSpalte] == Zelle.LEBENDIG) {
                            anzahlLebenderNachbar++;
                        }
                    }
                }
            }
        }
        return anzahlLebenderNachbar;
    }

    private boolean istImArray(int nachbarZeile, int nachbarSpalte) {
        return nachbarZeile >= 0 && nachbarSpalte >= 0
                && nachbarZeile < generation.length && nachbarSpalte < generation.length;
    }

    private static boolean istNachbar(int zeile, int spalte, int nachbarZeile, int nachbarSpalte) {
        return !(nachbarZeile == zeile && nachbarSpalte == spalte);
    }

    public void zeichnen() {
        for (int zeile = 0; zeile < generation.length; zeile++) {
            for (int spalte = 0; spalte < generation.length; spalte++) {
                System.out.print(generation[zeile][spalte].symbol);
            }
            System.out.println("");
        }
        System.out.println();
    }

    public Set<Coordinate> getCoordinateOfAllLivingCells() {
        Set<Coordinate> lebendigeZellen = new HashSet();
        for (int zeile = 0; zeile < generation.length; zeile++) {
            for (int spalte = 0; spalte < generation.length; spalte++) {
                if (generation[zeile][spalte] == Zelle.LEBENDIG) {
                    lebendigeZellen.add(new Coordinate(zeile, spalte));
                }
            }
        }
        return lebendigeZellen;
    }
}
