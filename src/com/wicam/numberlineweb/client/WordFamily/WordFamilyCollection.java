package com.wicam.numberlineweb.client.WordFamily;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;
import com.google.gwt.user.client.rpc.IsSerializable;


public class WordFamilyCollection implements IsSerializable {

	private ArrayList<WordFamily> wordCollection = new ArrayList<WordFamily>();
	
	public WordFamilyCollection() {

		wordCollection.add(new WordFamily("Ball", new ArrayList<String>(Arrays.asList("Abschlussball","Bälle"))));
		wordCollection.add(new WordFamily("beginnen", new ArrayList<String>(Arrays.asList("Beginn","begonnen"))));
		wordCollection.add(new WordFamily("besser", new ArrayList<String>(Arrays.asList("verbessern","gebessert","Besserung"))));
		wordCollection.add(new WordFamily("Brett", new ArrayList<String>(Arrays.asList("Bretter","Sprungbrett"))));
		wordCollection.add(new WordFamily("Decke", new ArrayList<String>(Arrays.asList("entdecken","zugedeckt","verdeckt"))));
		wordCollection.add(new WordFamily("doppelt", new ArrayList<String>(Arrays.asList("verdoppelt","Doppelbett","Doppelung"))));
		wordCollection.add(new WordFamily("Dreck", new ArrayList<String>(Arrays.asList("dreckig","verdreckt","Dreckspatz"))));
		wordCollection.add(new WordFamily("drucken", new ArrayList<String>(Arrays.asList("Druckfehler","gedruckt","ausdrucken"))));
		wordCollection.add(new WordFamily("drücken", new ArrayList<String>(Arrays.asList("Druck ","zerdrücken","gedrückt"))));
		wordCollection.add(new WordFamily("fallen", new ArrayList<String>(Arrays.asList("Abfall","Unfall","fällt"))));
		wordCollection.add(new WordFamily("füllen", new ArrayList<String>(Arrays.asList("ausfüllen","gefüllt"))));
		wordCollection.add(new WordFamily("Glück", new ArrayList<String>(Arrays.asList("glücklich","geglückt","verunglücken"))));
		wordCollection.add(new WordFamily("hell", new ArrayList<String>(Arrays.asList("heller","Helligkeit","erhellt"))));
		wordCollection.add(new WordFamily("Herr", new ArrayList<String>(Arrays.asList("herrlich","beherrschen"))));
		wordCollection.add(new WordFamily("hoffen", new ArrayList<String>(Arrays.asList("Hoffnung","hofft","hoffentlich"))));
		wordCollection.add(new WordFamily("irren", new ArrayList<String>(Arrays.asList("Irrtum","verirren"))));
		wordCollection.add(new WordFamily("kennen", new ArrayList<String>(Arrays.asList("kennen","bekannt","Kenntnis"))));
		wordCollection.add(new WordFamily("klappern", new ArrayList<String>(Arrays.asList("Klapperschlange","geklappert"))));
		wordCollection.add(new WordFamily("klettern", new ArrayList<String>(Arrays.asList("Klettergarten","geklettert"))));
		wordCollection.add(new WordFamily("kommen", new ArrayList<String>(Arrays.asList("ankommen","kommt","vorkommen"))));
		wordCollection.add(new WordFamily("lassen", new ArrayList<String>(Arrays.asList("Entlassung","lässt","verlassen"))));
		wordCollection.add(new WordFamily("Mann", new ArrayList<String>(Arrays.asList("Mannschaft","Männer"))));
		wordCollection.add(new WordFamily("messen", new ArrayList<String>(Arrays.asList("abmessen","gemessen","misst"))));
		wordCollection.add(new WordFamily("Mitte", new ArrayList<String>(Arrays.asList("mittlere","mittig"))));
		wordCollection.add(new WordFamily("Nummer", new ArrayList<String>(Arrays.asList("Nummernschild","nummeriert"))));
		wordCollection.add(new WordFamily("öffnen", new ArrayList<String>(Arrays.asList("Öffnungszeiten","geöffnet","Flaschenöffner"))));
		wordCollection.add(new WordFamily("packen", new ArrayList<String>(Arrays.asList("Päckchen","eingepackt"))));
		wordCollection.add(new WordFamily("passen", new ArrayList<String>(Arrays.asList("aufpassen","verpasst","passend"))));
		wordCollection.add(new WordFamily("rennen", new ArrayList<String>(Arrays.asList("Pferderennen","Rennfahrer","rennt"))));
		wordCollection.add(new WordFamily("Riss", new ArrayList<String>(Arrays.asList("gerissen","rissfest"))));
		wordCollection.add(new WordFamily("Ritt", new ArrayList<String>(Arrays.asList("Ausritt","geritten"))));
		wordCollection.add(new WordFamily("rollen", new ArrayList<String>(Arrays.asList("Rolltreppe","rollt","Rolle"))));
		wordCollection.add(new WordFamily("sammeln", new ArrayList<String>(Arrays.asList("Versammlung","gesammelt"))));
		wordCollection.add(new WordFamily("schaffen", new ArrayList<String>(Arrays.asList("schaffen","geschafft"))));
		wordCollection.add(new WordFamily("Schall", new ArrayList<String>(Arrays.asList("Beschallung","Schallgeschwindigkeit","schallend"))));
		wordCollection.add(new WordFamily("Schluss", new ArrayList<String>(Arrays.asList("Abschluss","Ladenschluss","schlüssig"))));
		wordCollection.add(new WordFamily("schmecken", new ArrayList<String>(Arrays.asList("Geschmack"))));
		wordCollection.add(new WordFamily("schmettern", new ArrayList<String>(Arrays.asList("Schmetterball","zerschmettern"))));
		wordCollection.add(new WordFamily("schmutzig", new ArrayList<String>(Arrays.asList("verschmutzen","Schmutz"))));
		wordCollection.add(new WordFamily("schnappen", new ArrayList<String>(Arrays.asList("schnappt","geschnappt","aufschnappen"))));
		wordCollection.add(new WordFamily("Schreck", new ArrayList<String>(Arrays.asList("erschreckend","erschrocken","erschrickt"))));
		wordCollection.add(new WordFamily("schwimmen", new ArrayList<String>(Arrays.asList("Schwimmflossen","geschwommen","schwimmend"))));
		wordCollection.add(new WordFamily("sitzen", new ArrayList<String>(Arrays.asList("nachsitzen","Hochsitz","sitzt"))));
		wordCollection.add(new WordFamily("spannen", new ArrayList<String>(Arrays.asList("gespannt","Spannung","ausspannen"))));
		wordCollection.add(new WordFamily("Sperre", new ArrayList<String>(Arrays.asList("versperren","sperrig","gesperrt"))));
		wordCollection.add(new WordFamily("Stelle", new ArrayList<String>(Arrays.asList("Ausstellung","gestellt"))));
		wordCollection.add(new WordFamily("stoppen", new ArrayList<String>(Arrays.asList("Stoppschild","gestoppt"))));
		wordCollection.add(new WordFamily("stumm", new ArrayList<String>(Arrays.asList("stummer","verstummen"))));
		wordCollection.add(new WordFamily("stützen", new ArrayList<String>(Arrays.asList("Liegestützen","Stütze","gestützt"))));
		wordCollection.add(new WordFamily("treffen", new ArrayList<String>(Arrays.asList("Treffpunkt","angetroffen","trifft"))));
		wordCollection.add(new WordFamily("trennen", new ArrayList<String>(Arrays.asList("Trennung","getrennt","Trennlinie"))));
		wordCollection.add(new WordFamily("Trick", new ArrayList<String>(Arrays.asList("austricksen","Zaubertrick"))));
		wordCollection.add(new WordFamily("Tritt", new ArrayList<String>(Arrays.asList("Trittbrett","Eintritt"))));
		wordCollection.add(new WordFamily("trocken", new ArrayList<String>(Arrays.asList("vertrocknet","Trockenheit"))));
		wordCollection.add(new WordFamily("verwirren", new ArrayList<String>(Arrays.asList("Verwirrung","verwirrend","verwirrt"))));
		wordCollection.add(new WordFamily("voll", new ArrayList<String>(Arrays.asList("völlig","verständnisvoll"))));
		wordCollection.add(new WordFamily("Wasser", new ArrayList<String>(Arrays.asList("Wasserquelle","bewässern"))));
		wordCollection.add(new WordFamily("wecken", new ArrayList<String>(Arrays.asList("aufgeweckt","Radiowecker"))));
		wordCollection.add(new WordFamily("Wette", new ArrayList<String>(Arrays.asList("Pferdewette","gewettet","Wetteinsatz"))));
		wordCollection.add(new WordFamily("Wetter", new ArrayList<String>(Arrays.asList("Wetterbericht","Wetterfrosch"))));
		wordCollection.add(new WordFamily("Wille", new ArrayList<String>(Arrays.asList("gewillt","wollen"))));
		wordCollection.add(new WordFamily("wissen", new ArrayList<String>(Arrays.asList("gewusst","Wissen","Wissenslücke"))));
		wordCollection.add(new WordFamily("zerren", new ArrayList<String>(Arrays.asList("Zerrung","verzerrt"))));

	}
	
	/*
	 * @param alreadyPlayed List containing IDs of wordCollections that have already been shown
	 * @param r Random
	 * @return Gives you a not yet played WordFamily
	 */
	public WordFamily getRandomWordFamily(ArrayList<Integer> alreadyPlayed, Random r) {
		int getID;
		
		if (alreadyPlayed.size() == this.wordCollection.size()) {
			alreadyPlayed.clear();
		}
		
		do {
			getID = r.nextInt(this.wordCollection.size());
		}
		while (alreadyPlayed.contains(getID));
		
		alreadyPlayed.add(getID);
		return new WordFamily(this.wordCollection.get(getID));		
	}

	/**
	 * @param i Number of Families to get
	 * @param alreadyPlayed List containing IDs of wordCollections that have already been shown
	 * @param r Random-object
	 * @return i random WordFamilies
	 */
	public ArrayList<WordFamily> getRandomFamilies(int i,
			ArrayList<Integer> alreadyPlayed, Random r) {
		
		ArrayList<WordFamily> res = new ArrayList<WordFamily>();
		
		while (i > 0) {
			res.add(this.getRandomWordFamily(alreadyPlayed, r));
			i--;
		}
		
		return res;
		
	}
	
	

	/**
	 * @param stem The stem to search for
	 * @return Returns the WordSet matching the stem
	 */
	public WordFamily getWordSet(String stem) throws NoSuchElementException {
		
		for (WordFamily set : this.wordCollection) {
			if (set.getStem().getWord().equals(stem)) {
				return new WordFamily(set);
			}
		}
		throw new NoSuchElementException("No WordFamily found for stem \"" + stem + "\"");
		
	}
	
	
	
	public ArrayList<Word> getRandomWords(int count, ArrayList<Word> notThese, Random r) throws NullPointerException {
		ArrayList<Word> res = new ArrayList<Word>();
		ArrayList<Word> tmpLs;
		Word tmp;
		
		while (count > this.wordCollection.size()-notThese.size()) {
			count--;
		}
		
		while (count > 0) {
			
			tmpLs = this.wordCollection.get(r.nextInt(this.wordCollection.size())).getWords();
			tmp = tmpLs.get(r.nextInt(tmpLs.size()));
			
			if (!notThese.contains(tmp) && !res.contains(tmp)) {
				res.add(tmp);
				count--;
			}
			
		}
		
		return res;
	}
	
	
}
