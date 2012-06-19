package com.wicam.numberlineweb.client.VowelGame;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.Resources.DoppelungGameResourcesSounds;

/**
 * Class for retrieving the wave-files
 * 
 * @author alex
 *
 */

public class SoundRetriever {


	public static Sound getSound(SoundController sc, VowelGameWord word, boolean slow) {

		sc.setDefaultVolume(100);

		String path = getPath(word, slow);
		
		try {
			return sc.createSound(Sound.MIME_TYPE_AUDIO_OGG_VORBIS, path);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}



	//	public static Sound getSound(SoundController sc, VowelGameWord word, boolean slow) {
	//	
	//		sc.setDefaultVolume(100);
	//		
	//		String value = word.getWordString().replace("ä", "ae").replace("ö", "oe").replace("ü", "ue");
	//		value = value.replace("Ä", "Ae").replace("Ö", "Oe").replace("Ü", "Ue").replace("ß", "ss");
	//		String length = (word.isShortVowel()) ? "short" : "long";
	//		String speed = (slow) ? "slow" : "normal";
	//		String path = "doppelungGame/new_sounds/" + length + "/" + speed + "/" + value + ".wav";
	//		
	//		try {
	//			return sc.createSound(Sound.MIME_TYPE_AUDIO_WAV_PCM, path);
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//			return null;
	//		}
	//		
	//	}

	//Preload sound by playing it silently
	public static void preloadSound(VowelGameWord word, boolean slow) {

		SoundController dummySoundController = new SoundController();

		dummySoundController.setDefaultVolume(0);

		String value = word.getWordString().replace("ä", "ae").replace("ö", "oe").replace("ü", "ue");
		value = value.replace("Ä", "Ae").replace("Ö", "Oe").replace("Ü", "Ue").replace("ß", "ss");
		String length = (word.isShortVowel()) ? "short" : "long";
		String speed = (slow) ? "slow" : "normal";
		String path = "doppelungGame/new_sounds/" + length + "/" + speed + "/" + value + ".wav";

		try {
			Sound sound = dummySoundController.createSound(Sound.MIME_TYPE_AUDIO_WAV_PCM, path);
			sound.play();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String getPath(VowelGameWord word, boolean slow) {

		String path = null;
		String value = word.getWordString();

		if (word.isShortVowel()) {
			if (slow == false) {

				if (value.equals("Ball")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.ballNormal().getUrl();
				}
				if (value.equals("billig")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.billigNormal().getUrl();
				}
				if (value.equals("Biss")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.bissNormal().getUrl();
				}
				if (value.equals("bitter")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.bitterNormal().getUrl();
				}
				if (value.equals("Blatt")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.blattNormal().getUrl();
				}
				if (value.equals("blicken")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.blickenNormal().getUrl();
				}
				if (value.equals("Brett")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.brettNormal().getUrl();
				}
				if (value.equals("Brille")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.brilleNormal().getUrl();
				}
				if (value.equals("Brücke")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.brueckeNormal().getUrl();
				}
				if (value.equals("Brunnen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.brunnenNormal().getUrl();
				}
				if (value.equals("Decke")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.deckeNormal().getUrl();
				}
				if (value.equals("dick")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.dickNormal().getUrl();
				}
				if (value.equals("Donner")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.donnerNormal().getUrl();
				}
				if (value.equals("Doppelbett")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.doppelbettNormal().getUrl();
				}
				if (value.equals("doppelt")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.doppeltNormal().getUrl();
				}
				if (value.equals("dreckig")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.dreckigNormal().getUrl();
				}
				if (value.equals("drucken")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.druckenNormal().getUrl();
				}
				if (value.equals("drücken")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.drueckenNormal().getUrl();
				}
				if (value.equals("Dummheit")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.dummheitNormal().getUrl();
				}
				if (value.equals("Ecke")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.eckeNormal().getUrl();
				}
				if (value.equals("fallen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.fallenNormal().getUrl();
				}
				if (value.equals("fällen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.faellenNormal().getUrl();
				}
				if (value.equals("Fell")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.fellNormal().getUrl();
				}
				if (value.equals("Flossen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.flossenNormal().getUrl();
				}
				if (value.equals("füllen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.fuellenNormal().getUrl();
				}
				if (value.equals("glatt")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.glattNormal().getUrl();
				}
				if (value.equals("Glück")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.glueckNormal().getUrl();
				}
				if (value.equals("Griff")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.griffNormal().getUrl();
				}
				if (value.equals("Halle")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.halleNormal().getUrl();
				}
				if (value.equals("Hammer")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.hammerNormal().getUrl();
				}
				if (value.equals("hassen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.hassenNormal().getUrl();
				}
				if (value.equals("hässlich")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.haesslichNormal().getUrl();
				}
				if (value.equals("hell")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.hellNormal().getUrl();
				}
				if (value.equals("herrlich")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.herrlichNormal().getUrl();
				}
				if (value.equals("hoffen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.hoffenNormal().getUrl();
				}
				if (value.equals("Hoffnung")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.hoffnungNormal().getUrl();
				}
				if (value.equals("Irrtum")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.irrtumNormal().getUrl();
				}
				if (value.equals("Kammer")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.kammerNormal().getUrl();
				}
				if (value.equals("Keller")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.kellerNormal().getUrl();
				}
				if (value.equals("kennen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.kennenNormal().getUrl();
				}
				if (value.equals("Kette")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.ketteNormal().getUrl();
				}
				if (value.equals("kippen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.kippenNormal().getUrl();
				}
				if (value.equals("klappen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.klappenNormal().getUrl();
				}
				if (value.equals("klappern")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.klappernNormal().getUrl();
				}
				if (value.equals("klettern")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.kletternNormal().getUrl();
				}
				if (value.equals("Klippe")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.klippeNormal().getUrl();
				}
				if (value.equals("knacken")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.knackenNormal().getUrl();
				}
				if (value.equals("knapp")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.knappNormal().getUrl();
				}
				if (value.equals("knittern")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.knitternNormal().getUrl();
				}
				if (value.equals("knurren")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.knurrenNormal().getUrl();
				}
				if (value.equals("Komma")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.kommaNormal().getUrl();
				}
				if (value.equals("krumm")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.krummNormal().getUrl();
				}
				if (value.equals("lassen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.lassenNormal().getUrl();
				}
				if (value.equals("lässig")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.laessigNormal().getUrl();
				}
				if (value.equals("Locke")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.lockeNormal().getUrl();
				}
				if (value.equals("lockig")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.lockigNormal().getUrl();
				}
				if (value.equals("Lücke")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.lueckeNormal().getUrl();
				}
				if (value.equals("messen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.messenNormal().getUrl();
				}
				if (value.equals("Mitte")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.mitteNormal().getUrl();
				}
				if (value.equals("Mücke")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.mueckeNormal().getUrl();
				}
				if (value.equals("nennen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.nennenNormal().getUrl();
				}
				if (value.equals("Nummer")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.nummerNormal().getUrl();
				}
				if (value.equals("öffnen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.oeffnenNormal().getUrl();
				}
				if (value.equals("Päckchen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.paeckchenNormal().getUrl();
				}
				if (value.equals("packen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.packenNormal().getUrl();
				}
				if (value.equals("passen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.passenNormal().getUrl();
				}
				if (value.equals("Pfiff")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.pfiffNormal().getUrl();
				}
				if (value.equals("pfiffig")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.pfiffigNormal().getUrl();
				}
				if (value.equals("Puppentheater")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.puppentheaterNormal().getUrl();
				}
				if (value.equals("Qualle")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.qualleNormal().getUrl();
				}
				if (value.equals("Quelle")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.quelleNormal().getUrl();
				}
				if (value.equals("Rennbahn")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.rennbahnNormal().getUrl();
				}
				if (value.equals("rennen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.rennenNormal().getUrl();
				}
				if (value.equals("Riss")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.rissNormal().getUrl();
				}
				if (value.equals("Ritt")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.rittNormal().getUrl();
				}
				if (value.equals("rollen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.rollenNormal().getUrl();
				}
				if (value.equals("Rücksicht")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.ruecksichtNormal().getUrl();
				}
				if (value.equals("sammeln")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.sammelnNormal().getUrl();
				}
				if (value.equals("schaffen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schaffenNormal().getUrl();
				}
				if (value.equals("Schall")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schallNormal().getUrl();
				}
				if (value.equals("Schallgeschwindigkeit")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schallgeschwindigkeitNormal().getUrl();
				}
				if (value.equals("scharren")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.scharrenNormal().getUrl();
				}
				if (value.equals("schicken")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schickenNormal().getUrl();
				}
				if (value.equals("Schiff")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schiffNormal().getUrl();
				}
				if (value.equals("Schimmel")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schimmelNormal().getUrl();
				}
				if (value.equals("Schlamm")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schlammNormal().getUrl();
				}
				if (value.equals("schleppen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schleppenNormal().getUrl();
				}
				if (value.equals("schlimm")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schlimmNormal().getUrl();
				}
				if (value.equals("schlucken")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schluckenNormal().getUrl();
				}
				if (value.equals("Schluss")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schlussNormal().getUrl();
				}
				if (value.equals("Schlüssel")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schluesselNormal().getUrl();
				}
				if (value.equals("schmettern")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schmetternNormal().getUrl();
				}
				if (value.equals("schmollen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schmollenNormal().getUrl();
				}
				if (value.equals("schnappen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schnappenNormal().getUrl();
				}
				if (value.equals("schnell")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schnellNormal().getUrl();
				}
				if (value.equals("Schramme")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schrammeNormal().getUrl();
				}
				if (value.equals("Schreck")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schreckNormal().getUrl();
				}
				if (value.equals("Schuppen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schuppenNormal().getUrl();
				}
				if (value.equals("schuppig")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schuppigNormal().getUrl();
				}
				if (value.equals("Schuss")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schussNormal().getUrl();
				}
				if (value.equals("Schusswaffe")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schusswaffeNormal().getUrl();
				}
				if (value.equals("schütteln")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schuettelnNormal().getUrl();
				}
				if (value.equals("Schwamm")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schwammNormal().getUrl();
				}
				if (value.equals("schwimmen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schwimmenNormal().getUrl();
				}
				if (value.equals("spannen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.spannenNormal().getUrl();
				}
				if (value.equals("Sperre")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.sperreNormal().getUrl();
				}
				if (value.equals("Splitter")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.splitterNormal().getUrl();
				}
				if (value.equals("Stamm")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.stammNormal().getUrl();
				}
				if (value.equals("starr")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.starrNormal().getUrl();
				}
				if (value.equals("Stecker")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.steckerNormal().getUrl();
				}
				if (value.equals("Stelle")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.stelleNormal().getUrl();
				}
				if (value.equals("stellen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.stellenNormal().getUrl();
				}
				if (value.equals("still")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.stillNormal().getUrl();
				}
				if (value.equals("Stimme")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.stimmeNormal().getUrl();
				}
				if (value.equals("Stoff")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.stoffNormal().getUrl();
				}
				if (value.equals("stoppen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.stoppenNormal().getUrl();
				}
				if (value.equals("stumm")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.stummNormal().getUrl();
				}
				if (value.equals("summen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.summenNormal().getUrl();
				}
				if (value.equals("treffen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.treffenNormal().getUrl();
				}
				if (value.equals("trennen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.trennenNormal().getUrl();
				}
				if (value.equals("Trick")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.trickNormal().getUrl();
				}
				if (value.equals("Tritt")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.trittNormal().getUrl();
				}
				if (value.equals("trocken")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.trockenNormal().getUrl();
				}
				if (value.equals("Trockenheit")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.trockenheitNormal().getUrl();
				}
				if (value.equals("Tunnel")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.tunnelNormal().getUrl();
				}
				if (value.equals("voll")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.vollNormal().getUrl();
				}
				if (value.equals("Wasser")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.wasserNormal().getUrl();
				}
				if (value.equals("wecken")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.weckenNormal().getUrl();
				}
				if (value.equals("Wette")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.wetteNormal().getUrl();
				}
				if (value.equals("Wetter")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.wetterNormal().getUrl();
				}
				if (value.equals("Wille")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.willeNormal().getUrl();
				}
				if (value.equals("wissen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.wissenNormal().getUrl();
				}
				if (value.equals("zerren")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.zerrenNormal().getUrl();
				}
				if (value.equals("Zucker")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.zuckerNormal().getUrl();
				}
				if (value.equals("zwicken")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.zwickenNormal().getUrl();
				}
				if (value.equals("Zwillinge")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.zwillingeNormal().getUrl();
				}

			}
			else {

				if (value.equals("Ball")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.ballSlow().getUrl();
				}
				if (value.equals("billig")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.billigSlow().getUrl();
				}
				if (value.equals("Biss")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.bissSlow().getUrl();
				}
				if (value.equals("bitter")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.bitterSlow().getUrl();
				}
				if (value.equals("Blatt")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.blattSlow().getUrl();
				}
				if (value.equals("blicken")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.blickenSlow().getUrl();
				}
				if (value.equals("Brett")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.brettSlow().getUrl();
				}
				if (value.equals("Brille")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.brilleSlow().getUrl();
				}
				if (value.equals("Brücke")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.brueckeSlow().getUrl();
				}
				if (value.equals("Brunnen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.brunnenSlow().getUrl();
				}
				if (value.equals("Decke")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.deckeSlow().getUrl();
				}
				if (value.equals("dick")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.dickSlow().getUrl();
				}
				if (value.equals("Donner")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.donnerSlow().getUrl();
				}
				if (value.equals("Doppelbett")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.doppelbettSlow().getUrl();
				}
				if (value.equals("doppelt")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.doppeltSlow().getUrl();
				}
				if (value.equals("dreckig")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.dreckigSlow().getUrl();
				}
				if (value.equals("drucken")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.druckenSlow().getUrl();
				}
				if (value.equals("drücken")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.drueckenSlow().getUrl();
				}
				if (value.equals("Dummheit")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.dummheitSlow().getUrl();
				}
				if (value.equals("Ecke")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.eckeSlow().getUrl();
				}
				if (value.equals("fallen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.fallenSlow().getUrl();
				}
				if (value.equals("fällen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.faellenSlow().getUrl();
				}
				if (value.equals("Fell")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.fellSlow().getUrl();
				}
				if (value.equals("Flossen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.flossenSlow().getUrl();
				}
				if (value.equals("füllen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.fuellenSlow().getUrl();
				}
				if (value.equals("glatt")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.glattSlow().getUrl();
				}
				if (value.equals("Glück")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.glueckSlow().getUrl();
				}
				if (value.equals("Griff")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.griffSlow().getUrl();
				}
				if (value.equals("Halle")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.halleSlow().getUrl();
				}
				if (value.equals("Hammer")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.hammerSlow().getUrl();
				}
				if (value.equals("hassen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.hassenSlow().getUrl();
				}
				if (value.equals("hässlich")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.haesslichSlow().getUrl();
				}
				if (value.equals("hell")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.hellSlow().getUrl();
				}
				if (value.equals("herrlich")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.herrlichSlow().getUrl();
				}
				if (value.equals("hoffen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.hoffenSlow().getUrl();
				}
				if (value.equals("Hoffnung")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.hoffnungSlow().getUrl();
				}
				if (value.equals("Irrtum")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.irrtumSlow().getUrl();
				}
				if (value.equals("Kammer")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.kammerSlow().getUrl();
				}
				if (value.equals("Keller")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.kellerSlow().getUrl();
				}
				if (value.equals("kennen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.kennenSlow().getUrl();
				}
				if (value.equals("Kette")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.ketteSlow().getUrl();
				}
				if (value.equals("kippen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.kippenSlow().getUrl();
				}
				if (value.equals("klappen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.klappenSlow().getUrl();
				}
				if (value.equals("klappern")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.klappernSlow().getUrl();
				}
				if (value.equals("klettern")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.kletternSlow().getUrl();
				}
				if (value.equals("Klippe")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.klippeSlow().getUrl();
				}
				if (value.equals("knacken")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.knackenSlow().getUrl();
				}
				if (value.equals("knapp")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.knappSlow().getUrl();
				}
				if (value.equals("knittern")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.knitternSlow().getUrl();
				}
				if (value.equals("knurren")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.knurrenSlow().getUrl();
				}
				if (value.equals("Komma")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.kommaSlow().getUrl();
				}
				if (value.equals("krumm")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.krummSlow().getUrl();
				}
				if (value.equals("lassen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.lassenSlow().getUrl();
				}
				if (value.equals("lässig")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.laessigSlow().getUrl();
				}
				if (value.equals("Locke")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.lockeSlow().getUrl();
				}
				if (value.equals("lockig")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.lockigSlow().getUrl();
				}
				if (value.equals("Lücke")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.lueckeSlow().getUrl();
				}
				if (value.equals("messen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.messenSlow().getUrl();
				}
				if (value.equals("Mitte")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.mitteSlow().getUrl();
				}
				if (value.equals("Mücke")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.mueckeSlow().getUrl();
				}
				if (value.equals("nennen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.nennenSlow().getUrl();
				}
				if (value.equals("Nummer")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.nummerSlow().getUrl();
				}
				if (value.equals("öffnen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.oeffnenSlow().getUrl();
				}
				if (value.equals("Päckchen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.paeckchenSlow().getUrl();
				}
				if (value.equals("packen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.packenSlow().getUrl();
				}
				if (value.equals("passen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.passenSlow().getUrl();
				}
				if (value.equals("Pfiff")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.pfiffSlow().getUrl();
				}
				if (value.equals("pfiffig")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.pfiffigSlow().getUrl();
				}
				if (value.equals("Puppentheater")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.puppentheaterSlow().getUrl();
				}
				if (value.equals("Qualle")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.qualleSlow().getUrl();
				}
				if (value.equals("Quelle")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.quelleSlow().getUrl();
				}
				if (value.equals("Rennbahn")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.rennbahnSlow().getUrl();
				}
				if (value.equals("rennen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.rennenSlow().getUrl();
				}
				if (value.equals("Riss")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.rissSlow().getUrl();
				}
				if (value.equals("Ritt")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.rittSlow().getUrl();
				}
				if (value.equals("rollen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.rollenSlow().getUrl();
				}
				if (value.equals("Rücksicht")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.ruecksichtSlow().getUrl();
				}
				if (value.equals("sammeln")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.sammelnSlow().getUrl();
				}
				if (value.equals("schaffen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schaffenSlow().getUrl();
				}
				if (value.equals("Schall")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schallSlow().getUrl();
				}
				if (value.equals("Schallgeschwindigkeit")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schallgeschwindigkeitSlow().getUrl();
				}
				if (value.equals("scharren")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.scharrenSlow().getUrl();
				}
				if (value.equals("schicken")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schickenSlow().getUrl();
				}
				if (value.equals("Schiff")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schiffSlow().getUrl();
				}
				if (value.equals("Schimmel")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schimmelSlow().getUrl();
				}
				if (value.equals("Schlamm")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schlammSlow().getUrl();
				}
				if (value.equals("schleppen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schleppenSlow().getUrl();
				}
				if (value.equals("schlimm")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schlimmSlow().getUrl();
				}
				if (value.equals("schlucken")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schluckenSlow().getUrl();
				}
				if (value.equals("Schluss")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schlussSlow().getUrl();
				}
				if (value.equals("Schlüssel")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schluesselSlow().getUrl();
				}
				if (value.equals("schmettern")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schmetternSlow().getUrl();
				}
				if (value.equals("schmollen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schmollenSlow().getUrl();
				}
				if (value.equals("schnappen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schnappenSlow().getUrl();
				}
				if (value.equals("schnell")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schnellSlow().getUrl();
				}
				if (value.equals("Schramme")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schrammeSlow().getUrl();
				}
				if (value.equals("Schreck")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schreckSlow().getUrl();
				}
				if (value.equals("Schuppen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schuppenSlow().getUrl();
				}
				if (value.equals("schuppig")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schuppigSlow().getUrl();
				}
				if (value.equals("Schuss")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schussSlow().getUrl();
				}
				if (value.equals("Schusswaffe")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schusswaffeSlow().getUrl();
				}
				if (value.equals("schütteln")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schuettelnSlow().getUrl();
				}
				if (value.equals("Schwamm")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schwammSlow().getUrl();
				}
				if (value.equals("schwimmen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schwimmenSlow().getUrl();
				}
				if (value.equals("spannen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.spannenSlow().getUrl();
				}
				if (value.equals("Sperre")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.sperreSlow().getUrl();
				}
				if (value.equals("Splitter")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.splitterSlow().getUrl();
				}
				if (value.equals("Stamm")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.stammSlow().getUrl();
				}
				if (value.equals("starr")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.starrSlow().getUrl();
				}
				if (value.equals("Stecker")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.steckerSlow().getUrl();
				}
				if (value.equals("Stelle")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.stelleSlow().getUrl();
				}
				if (value.equals("stellen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.stellenSlow().getUrl();
				}
				if (value.equals("still")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.stillSlow().getUrl();
				}
				if (value.equals("Stimme")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.stimmeSlow().getUrl();
				}
				if (value.equals("Stoff")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.stoffSlow().getUrl();
				}
				if (value.equals("stoppen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.stoppenSlow().getUrl();
				}
				if (value.equals("stumm")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.stummSlow().getUrl();
				}
				if (value.equals("summen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.summenSlow().getUrl();
				}
				if (value.equals("treffen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.treffenSlow().getUrl();
				}
				if (value.equals("trennen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.trennenSlow().getUrl();
				}
				if (value.equals("Trick")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.trickSlow().getUrl();
				}
				if (value.equals("Tritt")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.trittSlow().getUrl();
				}
				if (value.equals("trocken")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.trockenSlow().getUrl();
				}
				if (value.equals("Trockenheit")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.trockenheitSlow().getUrl();
				}
				if (value.equals("Tunnel")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.tunnelSlow().getUrl();
				}
				if (value.equals("voll")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.vollSlow().getUrl();
				}
				if (value.equals("Wasser")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.wasserSlow().getUrl();
				}
				if (value.equals("wecken")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.weckenSlow().getUrl();
				}
				if (value.equals("Wette")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.wetteSlow().getUrl();
				}
				if (value.equals("Wetter")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.wetterSlow().getUrl();
				}
				if (value.equals("Wille")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.willeSlow().getUrl();
				}
				if (value.equals("wissen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.wissenSlow().getUrl();
				}
				if (value.equals("zerren")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.zerrenSlow().getUrl();
				}
				if (value.equals("Zucker")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.zuckerSlow().getUrl();
				}
				if (value.equals("zwicken")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.zwickenSlow().getUrl();
				}
				if (value.equals("Zwillinge")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.zwillingeSlow().getUrl();
				}


			}
		}
		else {
			if (slow == false) {

				if (value.equals("Ahnung")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.ahnungNormal().getUrl();
				}
				if (value.equals("Bahn")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.bahnNormal().getUrl();
				}
				if (value.equals("Blume")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.blumeNormal().getUrl();
				}
				if (value.equals("Bruder")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.bruderNormal().getUrl();
				}
				if (value.equals("Ebene")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.ebeneNormal().getUrl();
				}
				if (value.equals("Fehler")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.fehlerNormal().getUrl();
				}
				if (value.equals("Flug")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.flugNormal().getUrl();
				}
				if (value.equals("Frieden")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.friedenNormal().getUrl();
				}
				if (value.equals("Frühling")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.fruehlingNormal().getUrl();
				}
				if (value.equals("Gas")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.gasNormal().getUrl();
				}
				if (value.equals("Hose")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.hoseNormal().getUrl();
				}
				if (value.equals("Höhle")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.hoehleNormal().getUrl();
				}
				if (value.equals("Kino")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.kinoNormal().getUrl();
				}
				if (value.equals("Kohle")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.kohleNormal().getUrl();
				}
				if (value.equals("Lehrer")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.lehrerNormal().getUrl();
				}
				if (value.equals("Liege")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.liegeNormal().getUrl();
				}
				if (value.equals("Löwe")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.loeweNormal().getUrl();
				}
				if (value.equals("Lüge")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.luegeNormal().getUrl();
				}
				if (value.equals("Miete")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.mieteNormal().getUrl();
				}
				if (value.equals("Rede")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.redeNormal().getUrl();
				}
				if (value.equals("Rose")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.roseNormal().getUrl();
				}
				if (value.equals("Schal")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schalNormal().getUrl();
				}
				if (value.equals("Schnee")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schneeNormal().getUrl();
				}
				if (value.equals("Sieger")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.siegerNormal().getUrl();
				}
				if (value.equals("Sohn")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.sohnNormal().getUrl();
				}
				if (value.equals("Spiegel")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.spiegelNormal().getUrl();
				}
				if (value.equals("Spiel")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.spielNormal().getUrl();
				}
				if (value.equals("Straße")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.strasseNormal().getUrl();
				}
				if (value.equals("Stuhl")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.stuhlNormal().getUrl();
				}
				if (value.equals("Tafel")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.tafelNormal().getUrl();
				}
				if (value.equals("Telefon")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.telefonNormal().getUrl();
				}
				if (value.equals("Vater")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.vaterNormal().getUrl();
				}
				if (value.equals("Vogel")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.vogelNormal().getUrl();
				}
				if (value.equals("Wagen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.wagenNormal().getUrl();
				}
				if (value.equals("Wahrheit")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.wahrheitNormal().getUrl();
				}
				if (value.equals("Weg")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.wegNormal().getUrl();
				}
				if (value.equals("Wiese")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.wieseNormal().getUrl();
				}
				if (value.equals("Wohnung")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.wohnungNormal().getUrl();
				}
				if (value.equals("Zahl")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.zahlNormal().getUrl();
				}
				if (value.equals("Zähler")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.zaehlerNormal().getUrl();
				}
				if (value.equals("Ziel")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.zielNormal().getUrl();
				}
				if (value.equals("Zug")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.zugNormal().getUrl();
				}
				if (value.equals("ähnlich")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.aehnlichNormal().getUrl();
				}
				if (value.equals("beten")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.betenNormal().getUrl();
				}
				if (value.equals("biegen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.biegenNormal().getUrl();
				}
				if (value.equals("drehen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.drehenNormal().getUrl();
				}
				if (value.equals("fahren")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.fahrenNormal().getUrl();
				}
				if (value.equals("fehlen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.fehlenNormal().getUrl();
				}
				if (value.equals("fröhlich")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.froehlichNormal().getUrl();
				}
				if (value.equals("führen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.fuehrenNormal().getUrl();
				}
				if (value.equals("geben")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.gebenNormal().getUrl();
				}
				if (value.equals("leben")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.lebenNormal().getUrl();
				}
				if (value.equals("legen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.legenNormal().getUrl();
				}
				if (value.equals("lieben")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.liebenNormal().getUrl();
				}
				if (value.equals("nehmen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.nehmenNormal().getUrl();
				}
				if (value.equals("niedlich")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.niedlichNormal().getUrl();
				}
				if (value.equals("ohne")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.ohneNormal().getUrl();
				}
				if (value.equals("sagen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.sagenNormal().getUrl();
				}
				if (value.equals("schieben")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schiebenNormal().getUrl();
				}
				if (value.equals("schief")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schiefNormal().getUrl();
				}
				if (value.equals("schließlich")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schliesslichNormal().getUrl();
				}
				if (value.equals("schwierig")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schwierigNormal().getUrl();
				}
				if (value.equals("tragen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.tragenNormal().getUrl();
				}
				if (value.equals("viel")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.vielNormal().getUrl();
				}
				if (value.equals("während")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.waehrendNormal().getUrl();
				}
				if (value.equals("wieder")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.wiederNormal().getUrl();
				}
				if (value.equals("wohnen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.wohnenNormal().getUrl();
				}
				if (value.equals("zahlen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.zahlenNormal().getUrl();
				}
				if (value.equals("zählen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.zaehlenNormal().getUrl();
				}
				if (value.equals("zehn")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.zehnNormal().getUrl();
				}
				if (value.equals("ziehen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.ziehenNormal().getUrl();
				}
				if (value.equals("ziemlich")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.ziemlichNormal().getUrl();
				}


			}
			else {

				if (value.equals("Ahnung")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.ahnungSlow().getUrl();
				}
				if (value.equals("Bahn")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.bahnSlow().getUrl();
				}
				if (value.equals("Blume")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.blumeSlow().getUrl();
				}
				if (value.equals("Bruder")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.bruderSlow().getUrl();
				}
				if (value.equals("Ebene")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.ebeneSlow().getUrl();
				}
				if (value.equals("Fehler")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.fehlerSlow().getUrl();
				}
				if (value.equals("Flug")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.flugSlow().getUrl();
				}
				if (value.equals("Frieden")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.friedenSlow().getUrl();
				}
				if (value.equals("Frühling")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.fruehlingSlow().getUrl();
				}
				if (value.equals("Gas")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.gasSlow().getUrl();
				}
				if (value.equals("Hose")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.hoseSlow().getUrl();
				}
				if (value.equals("Höhle")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.hoehleSlow().getUrl();
				}
				if (value.equals("Kino")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.kinoSlow().getUrl();
				}
				if (value.equals("Kohle")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.kohleSlow().getUrl();
				}
				if (value.equals("Lehrer")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.lehrerSlow().getUrl();
				}
				if (value.equals("Liege")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.liegeSlow().getUrl();
				}
				if (value.equals("Löwe")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.loeweSlow().getUrl();
				}
				if (value.equals("Lüge")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.luegeSlow().getUrl();
				}
				if (value.equals("Miete")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.mieteSlow().getUrl();
				}
				if (value.equals("Rede")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.redeSlow().getUrl();
				}
				if (value.equals("Rose")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.roseSlow().getUrl();
				}
				if (value.equals("Schal")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schalSlow().getUrl();
				}
				if (value.equals("Schnee")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schneeSlow().getUrl();
				}
				if (value.equals("Sieger")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.siegerSlow().getUrl();
				}
				if (value.equals("Sohn")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.sohnSlow().getUrl();
				}
				if (value.equals("Spiegel")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.spiegelSlow().getUrl();
				}
				if (value.equals("Spiel")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.spielSlow().getUrl();
				}
				if (value.equals("Straße")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.strasseSlow().getUrl();
				}
				if (value.equals("Stuhl")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.stuhlSlow().getUrl();
				}
				if (value.equals("Tafel")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.tafelSlow().getUrl();
				}
				if (value.equals("Telefon")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.telefonSlow().getUrl();
				}
				if (value.equals("Vater")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.vaterSlow().getUrl();
				}
				if (value.equals("Vogel")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.vogelSlow().getUrl();
				}
				if (value.equals("Wagen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.wagenSlow().getUrl();
				}
				if (value.equals("Wahrheit")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.wahrheitSlow().getUrl();
				}
				if (value.equals("Weg")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.wegSlow().getUrl();
				}
				if (value.equals("Wiese")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.wieseSlow().getUrl();
				}
				if (value.equals("Wohnung")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.wohnungSlow().getUrl();
				}
				if (value.equals("Zahl")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.zahlSlow().getUrl();
				}
				if (value.equals("Zähler")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.zaehlerSlow().getUrl();
				}
				if (value.equals("Ziel")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.zielSlow().getUrl();
				}
				if (value.equals("Zug")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.zugSlow().getUrl();
				}
				if (value.equals("ähnlich")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.aehnlichSlow().getUrl();
				}
				if (value.equals("beten")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.betenSlow().getUrl();
				}
				if (value.equals("biegen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.biegenSlow().getUrl();
				}
				if (value.equals("drehen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.drehenSlow().getUrl();
				}
				if (value.equals("fahren")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.fahrenSlow().getUrl();
				}
				if (value.equals("fehlen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.fehlenSlow().getUrl();
				}
				if (value.equals("fröhlich")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.froehlichSlow().getUrl();
				}
				if (value.equals("führen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.fuehrenSlow().getUrl();
				}
				if (value.equals("geben")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.gebenSlow().getUrl();
				}
				if (value.equals("leben")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.lebenSlow().getUrl();
				}
				if (value.equals("legen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.legenSlow().getUrl();
				}
				if (value.equals("lieben")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.liebenSlow().getUrl();
				}
				if (value.equals("nehmen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.nehmenSlow().getUrl();
				}
				if (value.equals("niedlich")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.niedlichSlow().getUrl();
				}
				if (value.equals("ohne")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.ohneSlow().getUrl();
				}
				if (value.equals("sagen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.sagenSlow().getUrl();
				}
				if (value.equals("schieben")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schiebenSlow().getUrl();
				}
				if (value.equals("schief")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schiefSlow().getUrl();
				}
				if (value.equals("schließlich")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schliesslichSlow().getUrl();
				}
				if (value.equals("schwierig")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.schwierigSlow().getUrl();
				}
				if (value.equals("tragen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.tragenSlow().getUrl();
				}
				if (value.equals("viel")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.vielSlow().getUrl();
				}
				if (value.equals("während")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.waehrendSlow().getUrl();
				}
				if (value.equals("wieder")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.wiederSlow().getUrl();
				}
				if (value.equals("wohnen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.wohnenSlow().getUrl();
				}
				if (value.equals("zahlen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.zahlenSlow().getUrl();
				}
				if (value.equals("zählen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.zaehlenSlow().getUrl();
				}
				if (value.equals("zehn")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.zehnSlow().getUrl();
				}
				if (value.equals("ziehen")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.ziehenSlow().getUrl();
				}
				if (value.equals("ziemlich")) { 
					path = DoppelungGameResourcesSounds.INSTANCE.ziemlichSlow().getUrl();
				}


			}
		}

		return path;

	}
}
