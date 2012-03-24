package com.wicam.numberlineweb.client.WordStem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;
import com.google.gwt.user.client.rpc.IsSerializable;


public class WordSetCollection implements IsSerializable {

	private ArrayList<WordSet> wordSetCollection = new ArrayList<WordSet>();
	
	public WordSetCollection() {
		
		wordSetCollection.add(new WordSet("ball", new ArrayList<String>(Arrays.asList("Abschlussball","geballt")), "bald"));
		wordSetCollection.add(new WordSet("bess", new ArrayList<String>(Arrays.asList("verbessern","gebessert","Besserung")), "beste"));
		wordSetCollection.add(new WordSet("brett", new ArrayList<String>(Arrays.asList("Bretter","Sprungbrett")), "Bett"));
		wordSetCollection.add(new WordSet("deck", new ArrayList<String>(Arrays.asList("entdecken","zugedeckt","verdeckt","Decke")), ""));
		wordSetCollection.add(new WordSet("dreck", new ArrayList<String>(Arrays.asList("dreckig","verdreckt")), ""));
		wordSetCollection.add(new WordSet("druck", new ArrayList<String>(Arrays.asList("Druckfehler","gedruckt","ausdrucken")), ""));
		wordSetCollection.add(new WordSet("eck", new ArrayList<String>(Arrays.asList("Eckpunkt","Tischecke","anecken","eckig")), ""));
		wordSetCollection.add(new WordSet("fall", new ArrayList<String>(Arrays.asList("Ausfall","Einfall","gefallen","umfallen")), "Falte"));
		wordSetCollection.add(new WordSet("füll", new ArrayList<String>(Arrays.asList("ausfüllen","auffüllen","gefüllt","Fülle")), "fühlen"));
		wordSetCollection.add(new WordSet("glück", new ArrayList<String>(Arrays.asList("glücklich","geglückt","Glücksspiel","verunglücken")), ""));
		wordSetCollection.add(new WordSet("hell", new ArrayList<String>(Arrays.asList("heller","Helligkeit","erhellt")), ""));
		wordSetCollection.add(new WordSet("hoff", new ArrayList<String>(Arrays.asList("Hoffnung","unverhofft","erhoffen","hoffentlich")), "oft"));
		wordSetCollection.add(new WordSet("irr", new ArrayList<String>(Arrays.asList("irren","verirrt","Irrweg")), "irgendetwas"));
		wordSetCollection.add(new WordSet("kenn", new ArrayList<String>(Arrays.asList("Kenntnis")), "kentern"));
		wordSetCollection.add(new WordSet("kett", new ArrayList<String>(Arrays.asList("Verkettung","angekettet")), ""));
		wordSetCollection.add(new WordSet("klett", new ArrayList<String>(Arrays.asList("Klettergarten","geklettert")), ""));
		wordSetCollection.add(new WordSet("komm", new ArrayList<String>(Arrays.asList("ankommen","kommt","gekommen","vorkommen")), ""));
		wordSetCollection.add(new WordSet("lass", new ArrayList<String>(Arrays.asList("Nachlass","auslassen","gelassen","verlässt")), "Last"));
		wordSetCollection.add(new WordSet("mes", new ArrayList<String>(Arrays.asList("abmessen","gemessen","Messbecher")), ""));
		wordSetCollection.add(new WordSet("mitt", new ArrayList<String>(Arrays.asList("mittlere","gemittelt","mittig","Mittwoch")), "mitmachen"));
		wordSetCollection.add(new WordSet("pack", new ArrayList<String>(Arrays.asList("Packesel","gepackt","verpackt")), ""));
		wordSetCollection.add(new WordSet("pass", new ArrayList<String>(Arrays.asList("aufpassen","gepasst","verpasst")), "Pasta"));
		wordSetCollection.add(new WordSet("ren", new ArrayList<String>(Arrays.asList("Pferderennen","Rennfahrer","rennt","verrennen")), ""));
		wordSetCollection.add(new WordSet("riss", new ArrayList<String>(Arrays.asList("ausgerissen","rissfest","zerrissen","Riss")), ""));
		wordSetCollection.add(new WordSet("rol", new ArrayList<String>(Arrays.asList("Rolltreppe","rollt","gerollt","Rolle")), ""));
		wordSetCollection.add(new WordSet("rück", new ArrayList<String>(Arrays.asList("rückwärts","Rücken","verrücken")), ""));
		wordSetCollection.add(new WordSet("schaf", new ArrayList<String>(Arrays.asList("anschaffen","geschafft")), ""));
		wordSetCollection.add(new WordSet("schall", new ArrayList<String>(Arrays.asList("Schallgeschwindigkeit","schallend","Schalldämpfer")), "Schalter"));
		wordSetCollection.add(new WordSet("schick", new ArrayList<String>(Arrays.asList("abgeschickt","verschickt")), "Schikane"));
		wordSetCollection.add(new WordSet("schlamm", new ArrayList<String>(Arrays.asList("schlammig","Schlammbad")), ""));
		wordSetCollection.add(new WordSet("schlepp", new ArrayList<String>(Arrays.asList("verschleppt","Schlepper","schleppend")), ""));
		wordSetCollection.add(new WordSet("schluss", new ArrayList<String>(Arrays.asList("Ausschluss","Abschlussessen","Schulabschluss")), "Ausschuss"));
		wordSetCollection.add(new WordSet("schmutz", new ArrayList<String>(Arrays.asList("verschmutzen","Schmutz")), ""));
		wordSetCollection.add(new WordSet("schnapp", new ArrayList<String>(Arrays.asList("schnappt","geschnappt","aufschnappen")), ""));
		wordSetCollection.add(new WordSet("schreck", new ArrayList<String>(Arrays.asList("erschrecken","schrecklich","erschreckend")), ""));
		wordSetCollection.add(new WordSet("schwimm", new ArrayList<String>(Arrays.asList("Schwimmflossen","Brustschwimmen")), ""));
		wordSetCollection.add(new WordSet("sitz", new ArrayList<String>(Arrays.asList("Nachsitzen","Hochsitz","Kindersitz","sitzt")), ""));
		wordSetCollection.add(new WordSet("spann", new ArrayList<String>(Arrays.asList("gespannt","Spannung","verspannt","ausspannen")), ""));
		wordSetCollection.add(new WordSet("Sperr", new ArrayList<String>(Arrays.asList("versperren","sperrig","gesperrt","Sperre")), "Sperber"));
		wordSetCollection.add(new WordSet("stamm", new ArrayList<String>(Arrays.asList("Baumstamm","abstammen","Volksstamm")), ""));
		wordSetCollection.add(new WordSet("starr", new ArrayList<String>(Arrays.asList("Totenstarre","erstarrt","starren","Starrheit")), ""));
		wordSetCollection.add(new WordSet("stell", new ArrayList<String>(Arrays.asList("verstellen","gestellt","Ausstellung","aufstellen")), "Stelzen"));
		wordSetCollection.add(new WordSet("still", new ArrayList<String>(Arrays.asList("Stillschweigen","stillen","gestillt")), ""));
		wordSetCollection.add(new WordSet("stimm", new ArrayList<String>(Arrays.asList("stimmt","verstimmen","Lautsprecherstimme","Stimmung")), ""));
		wordSetCollection.add(new WordSet("stopp", new ArrayList<String>(Arrays.asList("Stoppschild","gestoppt")), ""));
		wordSetCollection.add(new WordSet("stumm", new ArrayList<String>(Arrays.asList("stummer","verstummen","verstummt","Stummfilm")), ""));
		wordSetCollection.add(new WordSet("stütz", new ArrayList<String>(Arrays.asList("Liegestützen","Stütze","gestützt")), ""));
		wordSetCollection.add(new WordSet("treff", new ArrayList<String>(Arrays.asList("Treffpunkt","antreffen")), ""));
		wordSetCollection.add(new WordSet("trenn", new ArrayList<String>(Arrays.asList("Trennung","getrennt","Trennlinie")), ""));
		wordSetCollection.add(new WordSet("trock", new ArrayList<String>(Arrays.asList("Trockenheit","getrocknet","Wäschetrockner","trocknen")), ""));
		wordSetCollection.add(new WordSet("wirr", new ArrayList<String>(Arrays.asList("Verwirrung","verwirrend","verwirrt")), "Wirt"));
		wordSetCollection.add(new WordSet("voll", new ArrayList<String>(Arrays.asList("mühevoll","verständnisvoll","liebevoll")), ""));
		wordSetCollection.add(new WordSet("wett", new ArrayList<String>(Arrays.asList("Pferdewette","gewettet","Wetteinsatz")), ""));
		wordSetCollection.add(new WordSet("wiss", new ArrayList<String>(Arrays.asList("wissbegierig","Wissenslücke")), "Wiesel"));
		
	}
	
	/**
	 * @param n Number of WordSets to get
	 * @param taken All word stems that are already taken
	 * @param r Random-object
	 */
	public ArrayList<WordSet> getRandomSets(int n, ArrayList<Word> taken, 
			Random r) throws NullPointerException {
		
		ArrayList<WordSet> res = new ArrayList<WordSet>();
		
		if (taken.size()+n > this.wordSetCollection.size()) {
			throw new NullPointerException("Not enough untaken WordSets left");
		} else {
			
			while (res.size() < n) {
				int index = r.nextInt(this.wordSetCollection.size());
				if (!taken.contains(this.wordSetCollection.get(index).getStem())) {
					res.add(new WordSet(this.wordSetCollection.get(index)));
					taken.add(new Word(this.wordSetCollection.get(index).getStem()));
				}
			}			
			
		}
		
		return res;
		
	}
	
	
	/**
	 * @param stem The stem to search for
	 * @return Returns the WordSet matching the stem
	 */
	public WordSet getWordSet(String stem) throws NoSuchElementException {
		
		for (WordSet set : this.wordSetCollection) {
			if (set.getStem().getWord().equals(stem)) {
				return new WordSet(set);
			}
		}
		throw new NoSuchElementException("No WordSet found for stem \"" + stem + "\"");
		
	}
	
	
}
