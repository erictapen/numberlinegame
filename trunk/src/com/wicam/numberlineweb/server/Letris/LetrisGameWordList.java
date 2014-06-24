package com.wicam.numberlineweb.server.Letris;

import java.util.ArrayList;
import java.util.Collections;

import com.wicam.numberlineweb.client.VowelGame.VowelGameWord;

/**
 * Create the list of target words for the Letris game.
 * @author timfissler
 *
 */
public class LetrisGameWordList {
	
	public static ArrayList<VowelGameWord> createWordList(){
		ArrayList<VowelGameWord> wordList = new ArrayList<VowelGameWord>();
		
		wordList.add(new VowelGameWord("Ball", "ll"));
		wordList.add(new VowelGameWord("billig", "ll"));
		wordList.add(new VowelGameWord("Biss", "ss"));
		wordList.add(new VowelGameWord("bitter", "tt"));
		wordList.add(new VowelGameWord("Blatt", "tt"));
		wordList.add(new VowelGameWord("blicken", "ck"));
		wordList.add(new VowelGameWord("Brett", "tt"));
		wordList.add(new VowelGameWord("Brille", "ll"));
		wordList.add(new VowelGameWord("Brücke", "ck"));
		wordList.add(new VowelGameWord("Brunnen", "nn"));
		wordList.add(new VowelGameWord("Decke", "ck"));
		wordList.add(new VowelGameWord("dick", "ck"));
		wordList.add(new VowelGameWord("Donner", "nn"));
		wordList.add(new VowelGameWord("Doppelbett", "pp"));
		wordList.add(new VowelGameWord("doppelt", "pp"));
		wordList.add(new VowelGameWord("dreckig", "ck"));
		wordList.add(new VowelGameWord("drucken", "ck"));
		wordList.add(new VowelGameWord("drücken", "ck"));
		wordList.add(new VowelGameWord("Dummheit", "mm"));
		wordList.add(new VowelGameWord("Ecke", "ck"));
		wordList.add(new VowelGameWord("fallen", "ll"));
		wordList.add(new VowelGameWord("fällen", "ll"));
		wordList.add(new VowelGameWord("Fell", "ll"));
		wordList.add(new VowelGameWord("Flossen", "ss"));
		wordList.add(new VowelGameWord("füllen", "ll"));
		wordList.add(new VowelGameWord("glatt", "tt"));
		wordList.add(new VowelGameWord("Glück", "ck"));
		wordList.add(new VowelGameWord("Griff", "ff"));
		wordList.add(new VowelGameWord("Halle", "ll"));
		wordList.add(new VowelGameWord("Hammer", "mm"));
		wordList.add(new VowelGameWord("hassen", "ss"));
		wordList.add(new VowelGameWord("hässlich", "ss"));
		wordList.add(new VowelGameWord("hell", "ll"));
		wordList.add(new VowelGameWord("herrlich", "rr"));
		wordList.add(new VowelGameWord("hoffen", "ff"));
		wordList.add(new VowelGameWord("Hoffnung", "ff"));
		wordList.add(new VowelGameWord("Irrtum", "rr"));
		wordList.add(new VowelGameWord("Kammer", "mm"));
		wordList.add(new VowelGameWord("Keller", "ll"));
		wordList.add(new VowelGameWord("kennen", "nn"));
		wordList.add(new VowelGameWord("Kette", "tt"));
		wordList.add(new VowelGameWord("kippen", "pp"));
		wordList.add(new VowelGameWord("klappen", "pp"));
		wordList.add(new VowelGameWord("klappern", "pp"));
		wordList.add(new VowelGameWord("klettern", "tt"));
		wordList.add(new VowelGameWord("Klippe", "pp"));
		wordList.add(new VowelGameWord("knacken", "ck"));
		wordList.add(new VowelGameWord("knapp", "pp"));
		wordList.add(new VowelGameWord("knittern", "tt"));
		wordList.add(new VowelGameWord("knurren", "rr"));
		wordList.add(new VowelGameWord("Komma", "mm"));
		wordList.add(new VowelGameWord("krumm", "mm"));
		wordList.add(new VowelGameWord("lassen", "ss"));
		wordList.add(new VowelGameWord("lässig", "ss"));
		wordList.add(new VowelGameWord("Locke", "ck"));
		wordList.add(new VowelGameWord("lockig", "ck"));
		wordList.add(new VowelGameWord("Lücke", "ck"));
		wordList.add(new VowelGameWord("messen", "ss"));
		wordList.add(new VowelGameWord("Mitte", "tt"));
		wordList.add(new VowelGameWord("Mücke", "ck"));
		wordList.add(new VowelGameWord("nennen", "nn"));
		wordList.add(new VowelGameWord("Nummer", "mm"));
		wordList.add(new VowelGameWord("öffnen", "ff"));
		wordList.add(new VowelGameWord("Päckchen", "ck"));
		wordList.add(new VowelGameWord("packen", "ck"));
		wordList.add(new VowelGameWord("passen", "ss"));
		wordList.add(new VowelGameWord("Pfiff", "ff"));
		wordList.add(new VowelGameWord("pfiffig", "ff"));
		wordList.add(new VowelGameWord("Puppentheater", "pp"));
		wordList.add(new VowelGameWord("Qualle", "ll"));
		wordList.add(new VowelGameWord("Quelle", "ll"));
		wordList.add(new VowelGameWord("Rennbahn", "nn"));
		wordList.add(new VowelGameWord("rennen", "nn"));
		wordList.add(new VowelGameWord("Riss", "ss"));
		wordList.add(new VowelGameWord("Ritt", "tt"));
		wordList.add(new VowelGameWord("rollen", "ll"));
		wordList.add(new VowelGameWord("Rücksicht", "ck"));
		wordList.add(new VowelGameWord("sammeln", "mm"));
		wordList.add(new VowelGameWord("schaffen", "ff"));
		wordList.add(new VowelGameWord("Schall", "ll"));
//		wordList.add(new VowelGameWord("Schallgeschwindigkeit", "ll"));
		wordList.add(new VowelGameWord("scharren", "rr"));
		wordList.add(new VowelGameWord("schicken", "ck"));
		wordList.add(new VowelGameWord("Schiff", "ff"));
		wordList.add(new VowelGameWord("Schimmel", "mm"));
		wordList.add(new VowelGameWord("Schlamm", "mm"));
		wordList.add(new VowelGameWord("schleppen", "pp"));
		wordList.add(new VowelGameWord("schlimm", "mm"));
		wordList.add(new VowelGameWord("schlucken", "ck"));
		wordList.add(new VowelGameWord("Schluss", "ss"));
		wordList.add(new VowelGameWord("Schlüssel", "ss"));
		wordList.add(new VowelGameWord("schmettern", "tt"));
		wordList.add(new VowelGameWord("schmollen", "ll"));
		wordList.add(new VowelGameWord("schnappen", "pp"));
		wordList.add(new VowelGameWord("schnell", "ll"));
		wordList.add(new VowelGameWord("Schramme", "mm"));
		wordList.add(new VowelGameWord("Schreck", "ck"));
		wordList.add(new VowelGameWord("Schuppen", "pp"));
		wordList.add(new VowelGameWord("schuppig", "pp"));
		wordList.add(new VowelGameWord("Schuss", "ss"));
		wordList.add(new VowelGameWord("Schusswaffe", "ss"));
		wordList.add(new VowelGameWord("schütteln", "tt"));
		wordList.add(new VowelGameWord("Schwamm", "mm"));
		wordList.add(new VowelGameWord("schwimmen", "mm"));
		wordList.add(new VowelGameWord("spannen", "nn"));
		wordList.add(new VowelGameWord("Sperre", "rr"));
		wordList.add(new VowelGameWord("Splitter", "tt"));
		wordList.add(new VowelGameWord("Stamm", "mm"));
		wordList.add(new VowelGameWord("starr", "rr"));
		wordList.add(new VowelGameWord("Stecker", "ck"));
		wordList.add(new VowelGameWord("Stelle", "ll"));
		wordList.add(new VowelGameWord("stellen", "ll"));
		wordList.add(new VowelGameWord("still", "ll"));
		wordList.add(new VowelGameWord("Stimme", "mm"));
		wordList.add(new VowelGameWord("Stoff", "ff"));
		wordList.add(new VowelGameWord("stoppen", "pp"));
		wordList.add(new VowelGameWord("stumm", "mm"));
		wordList.add(new VowelGameWord("summen", "mm"));
		wordList.add(new VowelGameWord("treffen", "ff"));
		wordList.add(new VowelGameWord("trennen", "nn"));
		wordList.add(new VowelGameWord("Trick", "ck"));
		wordList.add(new VowelGameWord("Tritt", "tt"));
		wordList.add(new VowelGameWord("trocken", "ck"));
		wordList.add(new VowelGameWord("Trockenheit", "ck"));
		wordList.add(new VowelGameWord("Tunnel", "nn"));
		wordList.add(new VowelGameWord("voll", "ll"));
		wordList.add(new VowelGameWord("Wasser", "ss"));
		wordList.add(new VowelGameWord("wecken", "ck"));
		wordList.add(new VowelGameWord("Wette", "tt"));
		wordList.add(new VowelGameWord("Wetter", "tt"));
		wordList.add(new VowelGameWord("Wille", "ll"));
		wordList.add(new VowelGameWord("wissen", "ss"));
		wordList.add(new VowelGameWord("zerren", "rr"));
		wordList.add(new VowelGameWord("Zucker", "ck"));
		wordList.add(new VowelGameWord("zwicken", "ck"));
		wordList.add(new VowelGameWord("Zwillinge", "ll"));

		
		wordList.add(new VowelGameWord("Ahnung"));
		wordList.add(new VowelGameWord("Bahn"));
		wordList.add(new VowelGameWord("Blume"));
		wordList.add(new VowelGameWord("Bruder"));
		wordList.add(new VowelGameWord("Ebene"));
		wordList.add(new VowelGameWord("Fehler"));
		wordList.add(new VowelGameWord("Flug"));
		wordList.add(new VowelGameWord("Frieden"));
		wordList.add(new VowelGameWord("Frühling"));
		wordList.add(new VowelGameWord("Gas"));
		wordList.add(new VowelGameWord("Hose"));
		wordList.add(new VowelGameWord("Höhle"));
		wordList.add(new VowelGameWord("Kino"));
		wordList.add(new VowelGameWord("Kohle"));
		wordList.add(new VowelGameWord("Lehrer"));
		wordList.add(new VowelGameWord("Liege"));
		wordList.add(new VowelGameWord("Löwe"));
		wordList.add(new VowelGameWord("Lüge"));
		wordList.add(new VowelGameWord("Miete"));
		wordList.add(new VowelGameWord("Rede"));
		wordList.add(new VowelGameWord("Rose"));
		wordList.add(new VowelGameWord("Schal"));
		wordList.add(new VowelGameWord("Schnee"));
		wordList.add(new VowelGameWord("Sieger"));
		wordList.add(new VowelGameWord("Sohn"));
		wordList.add(new VowelGameWord("Spiegel"));
		wordList.add(new VowelGameWord("Spiel"));
		wordList.add(new VowelGameWord("Straße"));
		wordList.add(new VowelGameWord("Stuhl"));
		wordList.add(new VowelGameWord("Tafel"));
		wordList.add(new VowelGameWord("Telefon"));
		wordList.add(new VowelGameWord("Vater"));
		wordList.add(new VowelGameWord("Vogel"));
		wordList.add(new VowelGameWord("Wagen"));
		wordList.add(new VowelGameWord("Wahrheit"));
		wordList.add(new VowelGameWord("Weg"));
		wordList.add(new VowelGameWord("Wiese"));
		wordList.add(new VowelGameWord("Wohnung"));
		wordList.add(new VowelGameWord("Zahl"));
		wordList.add(new VowelGameWord("Zähler"));
		wordList.add(new VowelGameWord("Ziel"));
		wordList.add(new VowelGameWord("Zug"));
		wordList.add(new VowelGameWord("ähnlich"));
		wordList.add(new VowelGameWord("beten"));
		wordList.add(new VowelGameWord("biegen"));
		wordList.add(new VowelGameWord("drehen"));
		wordList.add(new VowelGameWord("fahren"));
		wordList.add(new VowelGameWord("fehlen"));
		wordList.add(new VowelGameWord("fröhlich"));
		wordList.add(new VowelGameWord("führen"));
		wordList.add(new VowelGameWord("geben"));
		wordList.add(new VowelGameWord("leben"));
		wordList.add(new VowelGameWord("legen"));
		wordList.add(new VowelGameWord("lieben"));
		wordList.add(new VowelGameWord("nehmen"));
		wordList.add(new VowelGameWord("niedlich"));
		wordList.add(new VowelGameWord("ohne"));
		wordList.add(new VowelGameWord("sagen"));
		wordList.add(new VowelGameWord("schieben"));
		wordList.add(new VowelGameWord("schief"));
		wordList.add(new VowelGameWord("schließlich"));
		wordList.add(new VowelGameWord("schwierig"));
		wordList.add(new VowelGameWord("tragen"));
		wordList.add(new VowelGameWord("viel"));
		wordList.add(new VowelGameWord("wählen"));
		wordList.add(new VowelGameWord("während"));
		wordList.add(new VowelGameWord("wieder"));
		wordList.add(new VowelGameWord("wohnen"));
		wordList.add(new VowelGameWord("zahlen"));
		wordList.add(new VowelGameWord("zählen"));
		wordList.add(new VowelGameWord("zehn"));
		wordList.add(new VowelGameWord("ziehen"));
		wordList.add(new VowelGameWord("ziemlich"));
		
		// Check whether there are words in the list that are longer than 15 letters.
//		for (VowelGameWord word : wordList) {
//			if (word.getWordString().length() > 15) {
//				System.out.println(word);
//			}
//		}
		
		Collections.shuffle(wordList);
		
		return wordList;
	}
}
