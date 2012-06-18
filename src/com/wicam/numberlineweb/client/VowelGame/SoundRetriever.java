package com.wicam.numberlineweb.client.VowelGame;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.Resources.DoppelungGameResources;

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
					path = DoppelungGameResources.INSTANCE.ballNormal().getUrl();
				}
				if (value.equals("billig")) { 
					path = DoppelungGameResources.INSTANCE.billigNormal().getUrl();
				}
				if (value.equals("Biss")) { 
					path = DoppelungGameResources.INSTANCE.bissNormal().getUrl();
				}
				if (value.equals("bitter")) { 
					path = DoppelungGameResources.INSTANCE.bitterNormal().getUrl();
				}
				if (value.equals("Blatt")) { 
					path = DoppelungGameResources.INSTANCE.blattNormal().getUrl();
				}
				if (value.equals("blicken")) { 
					path = DoppelungGameResources.INSTANCE.blickenNormal().getUrl();
				}
				if (value.equals("Brett")) { 
					path = DoppelungGameResources.INSTANCE.brettNormal().getUrl();
				}
				if (value.equals("Brille")) { 
					path = DoppelungGameResources.INSTANCE.brilleNormal().getUrl();
				}
				if (value.equals("Brücke")) { 
					path = DoppelungGameResources.INSTANCE.brueckeNormal().getUrl();
				}
				if (value.equals("Brunnen")) { 
					path = DoppelungGameResources.INSTANCE.brunnenNormal().getUrl();
				}
				if (value.equals("Decke")) { 
					path = DoppelungGameResources.INSTANCE.deckeNormal().getUrl();
				}
				if (value.equals("dick")) { 
					path = DoppelungGameResources.INSTANCE.dickNormal().getUrl();
				}
				if (value.equals("Donner")) { 
					path = DoppelungGameResources.INSTANCE.donnerNormal().getUrl();
				}
				if (value.equals("Doppelbett")) { 
					path = DoppelungGameResources.INSTANCE.doppelbettNormal().getUrl();
				}
				if (value.equals("doppelt")) { 
					path = DoppelungGameResources.INSTANCE.doppeltNormal().getUrl();
				}
				if (value.equals("dreckig")) { 
					path = DoppelungGameResources.INSTANCE.dreckigNormal().getUrl();
				}
				if (value.equals("drucken")) { 
					path = DoppelungGameResources.INSTANCE.druckenNormal().getUrl();
				}
				if (value.equals("drücken")) { 
					path = DoppelungGameResources.INSTANCE.drueckenNormal().getUrl();
				}
				if (value.equals("Dummheit")) { 
					path = DoppelungGameResources.INSTANCE.dummheitNormal().getUrl();
				}
				if (value.equals("Ecke")) { 
					path = DoppelungGameResources.INSTANCE.eckeNormal().getUrl();
				}
				if (value.equals("fallen")) { 
					path = DoppelungGameResources.INSTANCE.fallenNormal().getUrl();
				}
				if (value.equals("fällen")) { 
					path = DoppelungGameResources.INSTANCE.faellenNormal().getUrl();
				}
				if (value.equals("Fell")) { 
					path = DoppelungGameResources.INSTANCE.fellNormal().getUrl();
				}
				if (value.equals("Flossen")) { 
					path = DoppelungGameResources.INSTANCE.flossenNormal().getUrl();
				}
				if (value.equals("füllen")) { 
					path = DoppelungGameResources.INSTANCE.fuellenNormal().getUrl();
				}
				if (value.equals("glatt")) { 
					path = DoppelungGameResources.INSTANCE.glattNormal().getUrl();
				}
				if (value.equals("Glück")) { 
					path = DoppelungGameResources.INSTANCE.glueckNormal().getUrl();
				}
				if (value.equals("Griff")) { 
					path = DoppelungGameResources.INSTANCE.griffNormal().getUrl();
				}
				if (value.equals("Halle")) { 
					path = DoppelungGameResources.INSTANCE.halleNormal().getUrl();
				}
				if (value.equals("Hammer")) { 
					path = DoppelungGameResources.INSTANCE.hammerNormal().getUrl();
				}
				if (value.equals("hassen")) { 
					path = DoppelungGameResources.INSTANCE.hassenNormal().getUrl();
				}
				if (value.equals("hässlich")) { 
					path = DoppelungGameResources.INSTANCE.haesslichNormal().getUrl();
				}
				if (value.equals("hell")) { 
					path = DoppelungGameResources.INSTANCE.hellNormal().getUrl();
				}
				if (value.equals("herrlich")) { 
					path = DoppelungGameResources.INSTANCE.herrlichNormal().getUrl();
				}
				if (value.equals("hoffen")) { 
					path = DoppelungGameResources.INSTANCE.hoffenNormal().getUrl();
				}
				if (value.equals("Hoffnung")) { 
					path = DoppelungGameResources.INSTANCE.hoffnungNormal().getUrl();
				}
				if (value.equals("Irrtum")) { 
					path = DoppelungGameResources.INSTANCE.irrtumNormal().getUrl();
				}
				if (value.equals("Kammer")) { 
					path = DoppelungGameResources.INSTANCE.kammerNormal().getUrl();
				}
				if (value.equals("Keller")) { 
					path = DoppelungGameResources.INSTANCE.kellerNormal().getUrl();
				}
				if (value.equals("kennen")) { 
					path = DoppelungGameResources.INSTANCE.kennenNormal().getUrl();
				}
				if (value.equals("Kette")) { 
					path = DoppelungGameResources.INSTANCE.ketteNormal().getUrl();
				}
				if (value.equals("kippen")) { 
					path = DoppelungGameResources.INSTANCE.kippenNormal().getUrl();
				}
				if (value.equals("klappen")) { 
					path = DoppelungGameResources.INSTANCE.klappenNormal().getUrl();
				}
				if (value.equals("klappern")) { 
					path = DoppelungGameResources.INSTANCE.klappernNormal().getUrl();
				}
				if (value.equals("klettern")) { 
					path = DoppelungGameResources.INSTANCE.kletternNormal().getUrl();
				}
				if (value.equals("Klippe")) { 
					path = DoppelungGameResources.INSTANCE.klippeNormal().getUrl();
				}
				if (value.equals("knacken")) { 
					path = DoppelungGameResources.INSTANCE.knackenNormal().getUrl();
				}
				if (value.equals("knapp")) { 
					path = DoppelungGameResources.INSTANCE.knappNormal().getUrl();
				}
				if (value.equals("knittern")) { 
					path = DoppelungGameResources.INSTANCE.knitternNormal().getUrl();
				}
				if (value.equals("knurren")) { 
					path = DoppelungGameResources.INSTANCE.knurrenNormal().getUrl();
				}
				if (value.equals("Komma")) { 
					path = DoppelungGameResources.INSTANCE.kommaNormal().getUrl();
				}
				if (value.equals("krumm")) { 
					path = DoppelungGameResources.INSTANCE.krummNormal().getUrl();
				}
				if (value.equals("lassen")) { 
					path = DoppelungGameResources.INSTANCE.lassenNormal().getUrl();
				}
				if (value.equals("lässig")) { 
					path = DoppelungGameResources.INSTANCE.laessigNormal().getUrl();
				}
				if (value.equals("Locke")) { 
					path = DoppelungGameResources.INSTANCE.lockeNormal().getUrl();
				}
				if (value.equals("lockig")) { 
					path = DoppelungGameResources.INSTANCE.lockigNormal().getUrl();
				}
				if (value.equals("Lücke")) { 
					path = DoppelungGameResources.INSTANCE.lueckeNormal().getUrl();
				}
				if (value.equals("messen")) { 
					path = DoppelungGameResources.INSTANCE.messenNormal().getUrl();
				}
				if (value.equals("Mitte")) { 
					path = DoppelungGameResources.INSTANCE.mitteNormal().getUrl();
				}
				if (value.equals("Mücke")) { 
					path = DoppelungGameResources.INSTANCE.mueckeNormal().getUrl();
				}
				if (value.equals("nennen")) { 
					path = DoppelungGameResources.INSTANCE.nennenNormal().getUrl();
				}
				if (value.equals("Nummer")) { 
					path = DoppelungGameResources.INSTANCE.nummerNormal().getUrl();
				}
				if (value.equals("öffnen")) { 
					path = DoppelungGameResources.INSTANCE.oeffnenNormal().getUrl();
				}
				if (value.equals("Päckchen")) { 
					path = DoppelungGameResources.INSTANCE.paeckchenNormal().getUrl();
				}
				if (value.equals("packen")) { 
					path = DoppelungGameResources.INSTANCE.packenNormal().getUrl();
				}
				if (value.equals("passen")) { 
					path = DoppelungGameResources.INSTANCE.passenNormal().getUrl();
				}
				if (value.equals("Pfiff")) { 
					path = DoppelungGameResources.INSTANCE.pfiffNormal().getUrl();
				}
				if (value.equals("pfiffig")) { 
					path = DoppelungGameResources.INSTANCE.pfiffigNormal().getUrl();
				}
				if (value.equals("Puppentheater")) { 
					path = DoppelungGameResources.INSTANCE.puppentheaterNormal().getUrl();
				}
				if (value.equals("Qualle")) { 
					path = DoppelungGameResources.INSTANCE.qualleNormal().getUrl();
				}
				if (value.equals("Quelle")) { 
					path = DoppelungGameResources.INSTANCE.quelleNormal().getUrl();
				}
				if (value.equals("Rennbahn")) { 
					path = DoppelungGameResources.INSTANCE.rennbahnNormal().getUrl();
				}
				if (value.equals("rennen")) { 
					path = DoppelungGameResources.INSTANCE.rennenNormal().getUrl();
				}
				if (value.equals("Riss")) { 
					path = DoppelungGameResources.INSTANCE.rissNormal().getUrl();
				}
				if (value.equals("Ritt")) { 
					path = DoppelungGameResources.INSTANCE.rittNormal().getUrl();
				}
				if (value.equals("rollen")) { 
					path = DoppelungGameResources.INSTANCE.rollenNormal().getUrl();
				}
				if (value.equals("Rücksicht")) { 
					path = DoppelungGameResources.INSTANCE.ruecksichtNormal().getUrl();
				}
				if (value.equals("sammeln")) { 
					path = DoppelungGameResources.INSTANCE.sammelnNormal().getUrl();
				}
				if (value.equals("schaffen")) { 
					path = DoppelungGameResources.INSTANCE.schaffenNormal().getUrl();
				}
				if (value.equals("Schall")) { 
					path = DoppelungGameResources.INSTANCE.schallNormal().getUrl();
				}
				if (value.equals("Schallgeschwindigkeit")) { 
					path = DoppelungGameResources.INSTANCE.schallgeschwindigkeitNormal().getUrl();
				}
				if (value.equals("scharren")) { 
					path = DoppelungGameResources.INSTANCE.scharrenNormal().getUrl();
				}
				if (value.equals("schicken")) { 
					path = DoppelungGameResources.INSTANCE.schickenNormal().getUrl();
				}
				if (value.equals("Schiff")) { 
					path = DoppelungGameResources.INSTANCE.schiffNormal().getUrl();
				}
				if (value.equals("Schimmel")) { 
					path = DoppelungGameResources.INSTANCE.schimmelNormal().getUrl();
				}
				if (value.equals("Schlamm")) { 
					path = DoppelungGameResources.INSTANCE.schlammNormal().getUrl();
				}
				if (value.equals("schleppen")) { 
					path = DoppelungGameResources.INSTANCE.schleppenNormal().getUrl();
				}
				if (value.equals("schlimm")) { 
					path = DoppelungGameResources.INSTANCE.schlimmNormal().getUrl();
				}
				if (value.equals("schlucken")) { 
					path = DoppelungGameResources.INSTANCE.schluckenNormal().getUrl();
				}
				if (value.equals("Schluss")) { 
					path = DoppelungGameResources.INSTANCE.schlussNormal().getUrl();
				}
				if (value.equals("Schlüssel")) { 
					path = DoppelungGameResources.INSTANCE.schluesselNormal().getUrl();
				}
				if (value.equals("schmettern")) { 
					path = DoppelungGameResources.INSTANCE.schmetternNormal().getUrl();
				}
				if (value.equals("schmollen")) { 
					path = DoppelungGameResources.INSTANCE.schmollenNormal().getUrl();
				}
				if (value.equals("schnappen")) { 
					path = DoppelungGameResources.INSTANCE.schnappenNormal().getUrl();
				}
				if (value.equals("schnell")) { 
					path = DoppelungGameResources.INSTANCE.schnellNormal().getUrl();
				}
				if (value.equals("Schramme")) { 
					path = DoppelungGameResources.INSTANCE.schrammeNormal().getUrl();
				}
				if (value.equals("Schreck")) { 
					path = DoppelungGameResources.INSTANCE.schreckNormal().getUrl();
				}
				if (value.equals("Schuppen")) { 
					path = DoppelungGameResources.INSTANCE.schuppenNormal().getUrl();
				}
				if (value.equals("schuppig")) { 
					path = DoppelungGameResources.INSTANCE.schuppigNormal().getUrl();
				}
				if (value.equals("Schuss")) { 
					path = DoppelungGameResources.INSTANCE.schussNormal().getUrl();
				}
				if (value.equals("Schusswaffe")) { 
					path = DoppelungGameResources.INSTANCE.schusswaffeNormal().getUrl();
				}
				if (value.equals("schütteln")) { 
					path = DoppelungGameResources.INSTANCE.schuettelnNormal().getUrl();
				}
				if (value.equals("Schwamm")) { 
					path = DoppelungGameResources.INSTANCE.schwammNormal().getUrl();
				}
				if (value.equals("schwimmen")) { 
					path = DoppelungGameResources.INSTANCE.schwimmenNormal().getUrl();
				}
				if (value.equals("spannen")) { 
					path = DoppelungGameResources.INSTANCE.spannenNormal().getUrl();
				}
				if (value.equals("Sperre")) { 
					path = DoppelungGameResources.INSTANCE.sperreNormal().getUrl();
				}
				if (value.equals("Splitter")) { 
					path = DoppelungGameResources.INSTANCE.splitterNormal().getUrl();
				}
				if (value.equals("Stamm")) { 
					path = DoppelungGameResources.INSTANCE.stammNormal().getUrl();
				}
				if (value.equals("starr")) { 
					path = DoppelungGameResources.INSTANCE.starrNormal().getUrl();
				}
				if (value.equals("Stecker")) { 
					path = DoppelungGameResources.INSTANCE.steckerNormal().getUrl();
				}
				if (value.equals("Stelle")) { 
					path = DoppelungGameResources.INSTANCE.stelleNormal().getUrl();
				}
				if (value.equals("stellen")) { 
					path = DoppelungGameResources.INSTANCE.stellenNormal().getUrl();
				}
				if (value.equals("still")) { 
					path = DoppelungGameResources.INSTANCE.stillNormal().getUrl();
				}
				if (value.equals("Stimme")) { 
					path = DoppelungGameResources.INSTANCE.stimmeNormal().getUrl();
				}
				if (value.equals("Stoff")) { 
					path = DoppelungGameResources.INSTANCE.stoffNormal().getUrl();
				}
				if (value.equals("stoppen")) { 
					path = DoppelungGameResources.INSTANCE.stoppenNormal().getUrl();
				}
				if (value.equals("stumm")) { 
					path = DoppelungGameResources.INSTANCE.stummNormal().getUrl();
				}
				if (value.equals("summen")) { 
					path = DoppelungGameResources.INSTANCE.summenNormal().getUrl();
				}
				if (value.equals("treffen")) { 
					path = DoppelungGameResources.INSTANCE.treffenNormal().getUrl();
				}
				if (value.equals("trennen")) { 
					path = DoppelungGameResources.INSTANCE.trennenNormal().getUrl();
				}
				if (value.equals("Trick")) { 
					path = DoppelungGameResources.INSTANCE.trickNormal().getUrl();
				}
				if (value.equals("Tritt")) { 
					path = DoppelungGameResources.INSTANCE.trittNormal().getUrl();
				}
				if (value.equals("trocken")) { 
					path = DoppelungGameResources.INSTANCE.trockenNormal().getUrl();
				}
				if (value.equals("Trockenheit")) { 
					path = DoppelungGameResources.INSTANCE.trockenheitNormal().getUrl();
				}
				if (value.equals("Tunnel")) { 
					path = DoppelungGameResources.INSTANCE.tunnelNormal().getUrl();
				}
				if (value.equals("voll")) { 
					path = DoppelungGameResources.INSTANCE.vollNormal().getUrl();
				}
				if (value.equals("Wasser")) { 
					path = DoppelungGameResources.INSTANCE.wasserNormal().getUrl();
				}
				if (value.equals("wecken")) { 
					path = DoppelungGameResources.INSTANCE.weckenNormal().getUrl();
				}
				if (value.equals("Wette")) { 
					path = DoppelungGameResources.INSTANCE.wetteNormal().getUrl();
				}
				if (value.equals("Wetter")) { 
					path = DoppelungGameResources.INSTANCE.wetterNormal().getUrl();
				}
				if (value.equals("Wille")) { 
					path = DoppelungGameResources.INSTANCE.willeNormal().getUrl();
				}
				if (value.equals("wissen")) { 
					path = DoppelungGameResources.INSTANCE.wissenNormal().getUrl();
				}
				if (value.equals("zerren")) { 
					path = DoppelungGameResources.INSTANCE.zerrenNormal().getUrl();
				}
				if (value.equals("Zucker")) { 
					path = DoppelungGameResources.INSTANCE.zuckerNormal().getUrl();
				}
				if (value.equals("zwicken")) { 
					path = DoppelungGameResources.INSTANCE.zwickenNormal().getUrl();
				}
				if (value.equals("Zwillinge")) { 
					path = DoppelungGameResources.INSTANCE.zwillingeNormal().getUrl();
				}

			}
			else {

				if (value.equals("Ball")) { 
					path = DoppelungGameResources.INSTANCE.ballSlow().getUrl();
				}
				if (value.equals("billig")) { 
					path = DoppelungGameResources.INSTANCE.billigSlow().getUrl();
				}
				if (value.equals("Biss")) { 
					path = DoppelungGameResources.INSTANCE.bissSlow().getUrl();
				}
				if (value.equals("bitter")) { 
					path = DoppelungGameResources.INSTANCE.bitterSlow().getUrl();
				}
				if (value.equals("Blatt")) { 
					path = DoppelungGameResources.INSTANCE.blattSlow().getUrl();
				}
				if (value.equals("blicken")) { 
					path = DoppelungGameResources.INSTANCE.blickenSlow().getUrl();
				}
				if (value.equals("Brett")) { 
					path = DoppelungGameResources.INSTANCE.brettSlow().getUrl();
				}
				if (value.equals("Brille")) { 
					path = DoppelungGameResources.INSTANCE.brilleSlow().getUrl();
				}
				if (value.equals("Brücke")) { 
					path = DoppelungGameResources.INSTANCE.brueckeSlow().getUrl();
				}
				if (value.equals("Brunnen")) { 
					path = DoppelungGameResources.INSTANCE.brunnenSlow().getUrl();
				}
				if (value.equals("Decke")) { 
					path = DoppelungGameResources.INSTANCE.deckeSlow().getUrl();
				}
				if (value.equals("dick")) { 
					path = DoppelungGameResources.INSTANCE.dickSlow().getUrl();
				}
				if (value.equals("Donner")) { 
					path = DoppelungGameResources.INSTANCE.donnerSlow().getUrl();
				}
				if (value.equals("Doppelbett")) { 
					path = DoppelungGameResources.INSTANCE.doppelbettSlow().getUrl();
				}
				if (value.equals("doppelt")) { 
					path = DoppelungGameResources.INSTANCE.doppeltSlow().getUrl();
				}
				if (value.equals("dreckig")) { 
					path = DoppelungGameResources.INSTANCE.dreckigSlow().getUrl();
				}
				if (value.equals("drucken")) { 
					path = DoppelungGameResources.INSTANCE.druckenSlow().getUrl();
				}
				if (value.equals("drücken")) { 
					path = DoppelungGameResources.INSTANCE.drueckenSlow().getUrl();
				}
				if (value.equals("Dummheit")) { 
					path = DoppelungGameResources.INSTANCE.dummheitSlow().getUrl();
				}
				if (value.equals("Ecke")) { 
					path = DoppelungGameResources.INSTANCE.eckeSlow().getUrl();
				}
				if (value.equals("fallen")) { 
					path = DoppelungGameResources.INSTANCE.fallenSlow().getUrl();
				}
				if (value.equals("fällen")) { 
					path = DoppelungGameResources.INSTANCE.faellenSlow().getUrl();
				}
				if (value.equals("Fell")) { 
					path = DoppelungGameResources.INSTANCE.fellSlow().getUrl();
				}
				if (value.equals("Flossen")) { 
					path = DoppelungGameResources.INSTANCE.flossenSlow().getUrl();
				}
				if (value.equals("füllen")) { 
					path = DoppelungGameResources.INSTANCE.fuellenSlow().getUrl();
				}
				if (value.equals("glatt")) { 
					path = DoppelungGameResources.INSTANCE.glattSlow().getUrl();
				}
				if (value.equals("Glück")) { 
					path = DoppelungGameResources.INSTANCE.glueckSlow().getUrl();
				}
				if (value.equals("Griff")) { 
					path = DoppelungGameResources.INSTANCE.griffSlow().getUrl();
				}
				if (value.equals("Halle")) { 
					path = DoppelungGameResources.INSTANCE.halleSlow().getUrl();
				}
				if (value.equals("Hammer")) { 
					path = DoppelungGameResources.INSTANCE.hammerSlow().getUrl();
				}
				if (value.equals("hassen")) { 
					path = DoppelungGameResources.INSTANCE.hassenSlow().getUrl();
				}
				if (value.equals("hässlich")) { 
					path = DoppelungGameResources.INSTANCE.haesslichSlow().getUrl();
				}
				if (value.equals("hell")) { 
					path = DoppelungGameResources.INSTANCE.hellSlow().getUrl();
				}
				if (value.equals("herrlich")) { 
					path = DoppelungGameResources.INSTANCE.herrlichSlow().getUrl();
				}
				if (value.equals("hoffen")) { 
					path = DoppelungGameResources.INSTANCE.hoffenSlow().getUrl();
				}
				if (value.equals("Hoffnung")) { 
					path = DoppelungGameResources.INSTANCE.hoffnungSlow().getUrl();
				}
				if (value.equals("Irrtum")) { 
					path = DoppelungGameResources.INSTANCE.irrtumSlow().getUrl();
				}
				if (value.equals("Kammer")) { 
					path = DoppelungGameResources.INSTANCE.kammerSlow().getUrl();
				}
				if (value.equals("Keller")) { 
					path = DoppelungGameResources.INSTANCE.kellerSlow().getUrl();
				}
				if (value.equals("kennen")) { 
					path = DoppelungGameResources.INSTANCE.kennenSlow().getUrl();
				}
				if (value.equals("Kette")) { 
					path = DoppelungGameResources.INSTANCE.ketteSlow().getUrl();
				}
				if (value.equals("kippen")) { 
					path = DoppelungGameResources.INSTANCE.kippenSlow().getUrl();
				}
				if (value.equals("klappen")) { 
					path = DoppelungGameResources.INSTANCE.klappenSlow().getUrl();
				}
				if (value.equals("klappern")) { 
					path = DoppelungGameResources.INSTANCE.klappernSlow().getUrl();
				}
				if (value.equals("klettern")) { 
					path = DoppelungGameResources.INSTANCE.kletternSlow().getUrl();
				}
				if (value.equals("Klippe")) { 
					path = DoppelungGameResources.INSTANCE.klippeSlow().getUrl();
				}
				if (value.equals("knacken")) { 
					path = DoppelungGameResources.INSTANCE.knackenSlow().getUrl();
				}
				if (value.equals("knapp")) { 
					path = DoppelungGameResources.INSTANCE.knappSlow().getUrl();
				}
				if (value.equals("knittern")) { 
					path = DoppelungGameResources.INSTANCE.knitternSlow().getUrl();
				}
				if (value.equals("knurren")) { 
					path = DoppelungGameResources.INSTANCE.knurrenSlow().getUrl();
				}
				if (value.equals("Komma")) { 
					path = DoppelungGameResources.INSTANCE.kommaSlow().getUrl();
				}
				if (value.equals("krumm")) { 
					path = DoppelungGameResources.INSTANCE.krummSlow().getUrl();
				}
				if (value.equals("lassen")) { 
					path = DoppelungGameResources.INSTANCE.lassenSlow().getUrl();
				}
				if (value.equals("lässig")) { 
					path = DoppelungGameResources.INSTANCE.laessigSlow().getUrl();
				}
				if (value.equals("Locke")) { 
					path = DoppelungGameResources.INSTANCE.lockeSlow().getUrl();
				}
				if (value.equals("lockig")) { 
					path = DoppelungGameResources.INSTANCE.lockigSlow().getUrl();
				}
				if (value.equals("Lücke")) { 
					path = DoppelungGameResources.INSTANCE.lueckeSlow().getUrl();
				}
				if (value.equals("messen")) { 
					path = DoppelungGameResources.INSTANCE.messenSlow().getUrl();
				}
				if (value.equals("Mitte")) { 
					path = DoppelungGameResources.INSTANCE.mitteSlow().getUrl();
				}
				if (value.equals("Mücke")) { 
					path = DoppelungGameResources.INSTANCE.mueckeSlow().getUrl();
				}
				if (value.equals("nennen")) { 
					path = DoppelungGameResources.INSTANCE.nennenSlow().getUrl();
				}
				if (value.equals("Nummer")) { 
					path = DoppelungGameResources.INSTANCE.nummerSlow().getUrl();
				}
				if (value.equals("öffnen")) { 
					path = DoppelungGameResources.INSTANCE.oeffnenSlow().getUrl();
				}
				if (value.equals("Päckchen")) { 
					path = DoppelungGameResources.INSTANCE.paeckchenSlow().getUrl();
				}
				if (value.equals("packen")) { 
					path = DoppelungGameResources.INSTANCE.packenSlow().getUrl();
				}
				if (value.equals("passen")) { 
					path = DoppelungGameResources.INSTANCE.passenSlow().getUrl();
				}
				if (value.equals("Pfiff")) { 
					path = DoppelungGameResources.INSTANCE.pfiffSlow().getUrl();
				}
				if (value.equals("pfiffig")) { 
					path = DoppelungGameResources.INSTANCE.pfiffigSlow().getUrl();
				}
				if (value.equals("Puppentheater")) { 
					path = DoppelungGameResources.INSTANCE.puppentheaterSlow().getUrl();
				}
				if (value.equals("Qualle")) { 
					path = DoppelungGameResources.INSTANCE.qualleSlow().getUrl();
				}
				if (value.equals("Quelle")) { 
					path = DoppelungGameResources.INSTANCE.quelleSlow().getUrl();
				}
				if (value.equals("Rennbahn")) { 
					path = DoppelungGameResources.INSTANCE.rennbahnSlow().getUrl();
				}
				if (value.equals("rennen")) { 
					path = DoppelungGameResources.INSTANCE.rennenSlow().getUrl();
				}
				if (value.equals("Riss")) { 
					path = DoppelungGameResources.INSTANCE.rissSlow().getUrl();
				}
				if (value.equals("Ritt")) { 
					path = DoppelungGameResources.INSTANCE.rittSlow().getUrl();
				}
				if (value.equals("rollen")) { 
					path = DoppelungGameResources.INSTANCE.rollenSlow().getUrl();
				}
				if (value.equals("Rücksicht")) { 
					path = DoppelungGameResources.INSTANCE.ruecksichtSlow().getUrl();
				}
				if (value.equals("sammeln")) { 
					path = DoppelungGameResources.INSTANCE.sammelnSlow().getUrl();
				}
				if (value.equals("schaffen")) { 
					path = DoppelungGameResources.INSTANCE.schaffenSlow().getUrl();
				}
				if (value.equals("Schall")) { 
					path = DoppelungGameResources.INSTANCE.schallSlow().getUrl();
				}
				if (value.equals("Schallgeschwindigkeit")) { 
					path = DoppelungGameResources.INSTANCE.schallgeschwindigkeitSlow().getUrl();
				}
				if (value.equals("scharren")) { 
					path = DoppelungGameResources.INSTANCE.scharrenSlow().getUrl();
				}
				if (value.equals("schicken")) { 
					path = DoppelungGameResources.INSTANCE.schickenSlow().getUrl();
				}
				if (value.equals("Schiff")) { 
					path = DoppelungGameResources.INSTANCE.schiffSlow().getUrl();
				}
				if (value.equals("Schimmel")) { 
					path = DoppelungGameResources.INSTANCE.schimmelSlow().getUrl();
				}
				if (value.equals("Schlamm")) { 
					path = DoppelungGameResources.INSTANCE.schlammSlow().getUrl();
				}
				if (value.equals("schleppen")) { 
					path = DoppelungGameResources.INSTANCE.schleppenSlow().getUrl();
				}
				if (value.equals("schlimm")) { 
					path = DoppelungGameResources.INSTANCE.schlimmSlow().getUrl();
				}
				if (value.equals("schlucken")) { 
					path = DoppelungGameResources.INSTANCE.schluckenSlow().getUrl();
				}
				if (value.equals("Schluss")) { 
					path = DoppelungGameResources.INSTANCE.schlussSlow().getUrl();
				}
				if (value.equals("Schlüssel")) { 
					path = DoppelungGameResources.INSTANCE.schluesselSlow().getUrl();
				}
				if (value.equals("schmettern")) { 
					path = DoppelungGameResources.INSTANCE.schmetternSlow().getUrl();
				}
				if (value.equals("schmollen")) { 
					path = DoppelungGameResources.INSTANCE.schmollenSlow().getUrl();
				}
				if (value.equals("schnappen")) { 
					path = DoppelungGameResources.INSTANCE.schnappenSlow().getUrl();
				}
				if (value.equals("schnell")) { 
					path = DoppelungGameResources.INSTANCE.schnellSlow().getUrl();
				}
				if (value.equals("Schramme")) { 
					path = DoppelungGameResources.INSTANCE.schrammeSlow().getUrl();
				}
				if (value.equals("Schreck")) { 
					path = DoppelungGameResources.INSTANCE.schreckSlow().getUrl();
				}
				if (value.equals("Schuppen")) { 
					path = DoppelungGameResources.INSTANCE.schuppenSlow().getUrl();
				}
				if (value.equals("schuppig")) { 
					path = DoppelungGameResources.INSTANCE.schuppigSlow().getUrl();
				}
				if (value.equals("Schuss")) { 
					path = DoppelungGameResources.INSTANCE.schussSlow().getUrl();
				}
				if (value.equals("Schusswaffe")) { 
					path = DoppelungGameResources.INSTANCE.schusswaffeSlow().getUrl();
				}
				if (value.equals("schütteln")) { 
					path = DoppelungGameResources.INSTANCE.schuettelnSlow().getUrl();
				}
				if (value.equals("Schwamm")) { 
					path = DoppelungGameResources.INSTANCE.schwammSlow().getUrl();
				}
				if (value.equals("schwimmen")) { 
					path = DoppelungGameResources.INSTANCE.schwimmenSlow().getUrl();
				}
				if (value.equals("spannen")) { 
					path = DoppelungGameResources.INSTANCE.spannenSlow().getUrl();
				}
				if (value.equals("Sperre")) { 
					path = DoppelungGameResources.INSTANCE.sperreSlow().getUrl();
				}
				if (value.equals("Splitter")) { 
					path = DoppelungGameResources.INSTANCE.splitterSlow().getUrl();
				}
				if (value.equals("Stamm")) { 
					path = DoppelungGameResources.INSTANCE.stammSlow().getUrl();
				}
				if (value.equals("starr")) { 
					path = DoppelungGameResources.INSTANCE.starrSlow().getUrl();
				}
				if (value.equals("Stecker")) { 
					path = DoppelungGameResources.INSTANCE.steckerSlow().getUrl();
				}
				if (value.equals("Stelle")) { 
					path = DoppelungGameResources.INSTANCE.stelleSlow().getUrl();
				}
				if (value.equals("stellen")) { 
					path = DoppelungGameResources.INSTANCE.stellenSlow().getUrl();
				}
				if (value.equals("still")) { 
					path = DoppelungGameResources.INSTANCE.stillSlow().getUrl();
				}
				if (value.equals("Stimme")) { 
					path = DoppelungGameResources.INSTANCE.stimmeSlow().getUrl();
				}
				if (value.equals("Stoff")) { 
					path = DoppelungGameResources.INSTANCE.stoffSlow().getUrl();
				}
				if (value.equals("stoppen")) { 
					path = DoppelungGameResources.INSTANCE.stoppenSlow().getUrl();
				}
				if (value.equals("stumm")) { 
					path = DoppelungGameResources.INSTANCE.stummSlow().getUrl();
				}
				if (value.equals("summen")) { 
					path = DoppelungGameResources.INSTANCE.summenSlow().getUrl();
				}
				if (value.equals("treffen")) { 
					path = DoppelungGameResources.INSTANCE.treffenSlow().getUrl();
				}
				if (value.equals("trennen")) { 
					path = DoppelungGameResources.INSTANCE.trennenSlow().getUrl();
				}
				if (value.equals("Trick")) { 
					path = DoppelungGameResources.INSTANCE.trickSlow().getUrl();
				}
				if (value.equals("Tritt")) { 
					path = DoppelungGameResources.INSTANCE.trittSlow().getUrl();
				}
				if (value.equals("trocken")) { 
					path = DoppelungGameResources.INSTANCE.trockenSlow().getUrl();
				}
				if (value.equals("Trockenheit")) { 
					path = DoppelungGameResources.INSTANCE.trockenheitSlow().getUrl();
				}
				if (value.equals("Tunnel")) { 
					path = DoppelungGameResources.INSTANCE.tunnelSlow().getUrl();
				}
				if (value.equals("voll")) { 
					path = DoppelungGameResources.INSTANCE.vollSlow().getUrl();
				}
				if (value.equals("Wasser")) { 
					path = DoppelungGameResources.INSTANCE.wasserSlow().getUrl();
				}
				if (value.equals("wecken")) { 
					path = DoppelungGameResources.INSTANCE.weckenSlow().getUrl();
				}
				if (value.equals("Wette")) { 
					path = DoppelungGameResources.INSTANCE.wetteSlow().getUrl();
				}
				if (value.equals("Wetter")) { 
					path = DoppelungGameResources.INSTANCE.wetterSlow().getUrl();
				}
				if (value.equals("Wille")) { 
					path = DoppelungGameResources.INSTANCE.willeSlow().getUrl();
				}
				if (value.equals("wissen")) { 
					path = DoppelungGameResources.INSTANCE.wissenSlow().getUrl();
				}
				if (value.equals("zerren")) { 
					path = DoppelungGameResources.INSTANCE.zerrenSlow().getUrl();
				}
				if (value.equals("Zucker")) { 
					path = DoppelungGameResources.INSTANCE.zuckerSlow().getUrl();
				}
				if (value.equals("zwicken")) { 
					path = DoppelungGameResources.INSTANCE.zwickenSlow().getUrl();
				}
				if (value.equals("Zwillinge")) { 
					path = DoppelungGameResources.INSTANCE.zwillingeSlow().getUrl();
				}


			}
		}
		else {
			if (slow == false) {

				if (value.equals("Ahnung")) { 
					path = DoppelungGameResources.INSTANCE.ahnungNormal().getUrl();
				}
				if (value.equals("Bahn")) { 
					path = DoppelungGameResources.INSTANCE.bahnNormal().getUrl();
				}
				if (value.equals("Blume")) { 
					path = DoppelungGameResources.INSTANCE.blumeNormal().getUrl();
				}
				if (value.equals("Bruder")) { 
					path = DoppelungGameResources.INSTANCE.bruderNormal().getUrl();
				}
				if (value.equals("Ebene")) { 
					path = DoppelungGameResources.INSTANCE.ebeneNormal().getUrl();
				}
				if (value.equals("Fehler")) { 
					path = DoppelungGameResources.INSTANCE.fehlerNormal().getUrl();
				}
				if (value.equals("Flug")) { 
					path = DoppelungGameResources.INSTANCE.flugNormal().getUrl();
				}
				if (value.equals("Frieden")) { 
					path = DoppelungGameResources.INSTANCE.friedenNormal().getUrl();
				}
				if (value.equals("Frühling")) { 
					path = DoppelungGameResources.INSTANCE.fruehlingNormal().getUrl();
				}
				if (value.equals("Gas")) { 
					path = DoppelungGameResources.INSTANCE.gasNormal().getUrl();
				}
				if (value.equals("Hose")) { 
					path = DoppelungGameResources.INSTANCE.hoseNormal().getUrl();
				}
				if (value.equals("Höhle")) { 
					path = DoppelungGameResources.INSTANCE.hoehleNormal().getUrl();
				}
				if (value.equals("Kino")) { 
					path = DoppelungGameResources.INSTANCE.kinoNormal().getUrl();
				}
				if (value.equals("Kohle")) { 
					path = DoppelungGameResources.INSTANCE.kohleNormal().getUrl();
				}
				if (value.equals("Lehrer")) { 
					path = DoppelungGameResources.INSTANCE.lehrerNormal().getUrl();
				}
				if (value.equals("Liege")) { 
					path = DoppelungGameResources.INSTANCE.liegeNormal().getUrl();
				}
				if (value.equals("Löwe")) { 
					path = DoppelungGameResources.INSTANCE.loeweNormal().getUrl();
				}
				if (value.equals("Lüge")) { 
					path = DoppelungGameResources.INSTANCE.luegeNormal().getUrl();
				}
				if (value.equals("Miete")) { 
					path = DoppelungGameResources.INSTANCE.mieteNormal().getUrl();
				}
				if (value.equals("Rede")) { 
					path = DoppelungGameResources.INSTANCE.redeNormal().getUrl();
				}
				if (value.equals("Rose")) { 
					path = DoppelungGameResources.INSTANCE.roseNormal().getUrl();
				}
				if (value.equals("Schal")) { 
					path = DoppelungGameResources.INSTANCE.schalNormal().getUrl();
				}
				if (value.equals("Schnee")) { 
					path = DoppelungGameResources.INSTANCE.schneeNormal().getUrl();
				}
				if (value.equals("Sieger")) { 
					path = DoppelungGameResources.INSTANCE.siegerNormal().getUrl();
				}
				if (value.equals("Sohn")) { 
					path = DoppelungGameResources.INSTANCE.sohnNormal().getUrl();
				}
				if (value.equals("Spiegel")) { 
					path = DoppelungGameResources.INSTANCE.spiegelNormal().getUrl();
				}
				if (value.equals("Spiel")) { 
					path = DoppelungGameResources.INSTANCE.spielNormal().getUrl();
				}
				if (value.equals("Straße")) { 
					path = DoppelungGameResources.INSTANCE.strasseNormal().getUrl();
				}
				if (value.equals("Stuhl")) { 
					path = DoppelungGameResources.INSTANCE.stuhlNormal().getUrl();
				}
				if (value.equals("Tafel")) { 
					path = DoppelungGameResources.INSTANCE.tafelNormal().getUrl();
				}
				if (value.equals("Telefon")) { 
					path = DoppelungGameResources.INSTANCE.telefonNormal().getUrl();
				}
				if (value.equals("Vater")) { 
					path = DoppelungGameResources.INSTANCE.vaterNormal().getUrl();
				}
				if (value.equals("Vogel")) { 
					path = DoppelungGameResources.INSTANCE.vogelNormal().getUrl();
				}
				if (value.equals("Wagen")) { 
					path = DoppelungGameResources.INSTANCE.wagenNormal().getUrl();
				}
				if (value.equals("Wahrheit")) { 
					path = DoppelungGameResources.INSTANCE.wahrheitNormal().getUrl();
				}
				if (value.equals("Weg")) { 
					path = DoppelungGameResources.INSTANCE.wegNormal().getUrl();
				}
				if (value.equals("Wiese")) { 
					path = DoppelungGameResources.INSTANCE.wieseNormal().getUrl();
				}
				if (value.equals("Wohnung")) { 
					path = DoppelungGameResources.INSTANCE.wohnungNormal().getUrl();
				}
				if (value.equals("Zahl")) { 
					path = DoppelungGameResources.INSTANCE.zahlNormal().getUrl();
				}
				if (value.equals("Zähler")) { 
					path = DoppelungGameResources.INSTANCE.zaehlerNormal().getUrl();
				}
				if (value.equals("Ziel")) { 
					path = DoppelungGameResources.INSTANCE.zielNormal().getUrl();
				}
				if (value.equals("Zug")) { 
					path = DoppelungGameResources.INSTANCE.zugNormal().getUrl();
				}
				if (value.equals("ähnlich")) { 
					path = DoppelungGameResources.INSTANCE.aehnlichNormal().getUrl();
				}
				if (value.equals("beten")) { 
					path = DoppelungGameResources.INSTANCE.betenNormal().getUrl();
				}
				if (value.equals("biegen")) { 
					path = DoppelungGameResources.INSTANCE.biegenNormal().getUrl();
				}
				if (value.equals("drehen")) { 
					path = DoppelungGameResources.INSTANCE.drehenNormal().getUrl();
				}
				if (value.equals("fahren")) { 
					path = DoppelungGameResources.INSTANCE.fahrenNormal().getUrl();
				}
				if (value.equals("fehlen")) { 
					path = DoppelungGameResources.INSTANCE.fehlenNormal().getUrl();
				}
				if (value.equals("fröhlich")) { 
					path = DoppelungGameResources.INSTANCE.froehlichNormal().getUrl();
				}
				if (value.equals("führen")) { 
					path = DoppelungGameResources.INSTANCE.fuehrenNormal().getUrl();
				}
				if (value.equals("geben")) { 
					path = DoppelungGameResources.INSTANCE.gebenNormal().getUrl();
				}
				if (value.equals("leben")) { 
					path = DoppelungGameResources.INSTANCE.lebenNormal().getUrl();
				}
				if (value.equals("legen")) { 
					path = DoppelungGameResources.INSTANCE.legenNormal().getUrl();
				}
				if (value.equals("lieben")) { 
					path = DoppelungGameResources.INSTANCE.liebenNormal().getUrl();
				}
				if (value.equals("nehmen")) { 
					path = DoppelungGameResources.INSTANCE.nehmenNormal().getUrl();
				}
				if (value.equals("niedlich")) { 
					path = DoppelungGameResources.INSTANCE.niedlichNormal().getUrl();
				}
				if (value.equals("ohne")) { 
					path = DoppelungGameResources.INSTANCE.ohneNormal().getUrl();
				}
				if (value.equals("sagen")) { 
					path = DoppelungGameResources.INSTANCE.sagenNormal().getUrl();
				}
				if (value.equals("schieben")) { 
					path = DoppelungGameResources.INSTANCE.schiebenNormal().getUrl();
				}
				if (value.equals("schief")) { 
					path = DoppelungGameResources.INSTANCE.schiefNormal().getUrl();
				}
				if (value.equals("schließlich")) { 
					path = DoppelungGameResources.INSTANCE.schliesslichNormal().getUrl();
				}
				if (value.equals("schwierig")) { 
					path = DoppelungGameResources.INSTANCE.schwierigNormal().getUrl();
				}
				if (value.equals("tragen")) { 
					path = DoppelungGameResources.INSTANCE.tragenNormal().getUrl();
				}
				if (value.equals("viel")) { 
					path = DoppelungGameResources.INSTANCE.vielNormal().getUrl();
				}
				if (value.equals("während")) { 
					path = DoppelungGameResources.INSTANCE.waehrendNormal().getUrl();
				}
				if (value.equals("wieder")) { 
					path = DoppelungGameResources.INSTANCE.wiederNormal().getUrl();
				}
				if (value.equals("wohnen")) { 
					path = DoppelungGameResources.INSTANCE.wohnenNormal().getUrl();
				}
				if (value.equals("zahlen")) { 
					path = DoppelungGameResources.INSTANCE.zahlenNormal().getUrl();
				}
				if (value.equals("zählen")) { 
					path = DoppelungGameResources.INSTANCE.zaehlenNormal().getUrl();
				}
				if (value.equals("zehn")) { 
					path = DoppelungGameResources.INSTANCE.zehnNormal().getUrl();
				}
				if (value.equals("ziehen")) { 
					path = DoppelungGameResources.INSTANCE.ziehenNormal().getUrl();
				}
				if (value.equals("ziemlich")) { 
					path = DoppelungGameResources.INSTANCE.ziemlichNormal().getUrl();
				}


			}
			else {

				if (value.equals("Ahnung")) { 
					path = DoppelungGameResources.INSTANCE.ahnungSlow().getUrl();
				}
				if (value.equals("Bahn")) { 
					path = DoppelungGameResources.INSTANCE.bahnSlow().getUrl();
				}
				if (value.equals("Blume")) { 
					path = DoppelungGameResources.INSTANCE.blumeSlow().getUrl();
				}
				if (value.equals("Bruder")) { 
					path = DoppelungGameResources.INSTANCE.bruderSlow().getUrl();
				}
				if (value.equals("Ebene")) { 
					path = DoppelungGameResources.INSTANCE.ebeneSlow().getUrl();
				}
				if (value.equals("Fehler")) { 
					path = DoppelungGameResources.INSTANCE.fehlerSlow().getUrl();
				}
				if (value.equals("Flug")) { 
					path = DoppelungGameResources.INSTANCE.flugSlow().getUrl();
				}
				if (value.equals("Frieden")) { 
					path = DoppelungGameResources.INSTANCE.friedenSlow().getUrl();
				}
				if (value.equals("Frühling")) { 
					path = DoppelungGameResources.INSTANCE.fruehlingSlow().getUrl();
				}
				if (value.equals("Gas")) { 
					path = DoppelungGameResources.INSTANCE.gasSlow().getUrl();
				}
				if (value.equals("Hose")) { 
					path = DoppelungGameResources.INSTANCE.hoseSlow().getUrl();
				}
				if (value.equals("Höhle")) { 
					path = DoppelungGameResources.INSTANCE.hoehleSlow().getUrl();
				}
				if (value.equals("Kino")) { 
					path = DoppelungGameResources.INSTANCE.kinoSlow().getUrl();
				}
				if (value.equals("Kohle")) { 
					path = DoppelungGameResources.INSTANCE.kohleSlow().getUrl();
				}
				if (value.equals("Lehrer")) { 
					path = DoppelungGameResources.INSTANCE.lehrerSlow().getUrl();
				}
				if (value.equals("Liege")) { 
					path = DoppelungGameResources.INSTANCE.liegeSlow().getUrl();
				}
				if (value.equals("Löwe")) { 
					path = DoppelungGameResources.INSTANCE.loeweSlow().getUrl();
				}
				if (value.equals("Lüge")) { 
					path = DoppelungGameResources.INSTANCE.luegeSlow().getUrl();
				}
				if (value.equals("Miete")) { 
					path = DoppelungGameResources.INSTANCE.mieteSlow().getUrl();
				}
				if (value.equals("Rede")) { 
					path = DoppelungGameResources.INSTANCE.redeSlow().getUrl();
				}
				if (value.equals("Rose")) { 
					path = DoppelungGameResources.INSTANCE.roseSlow().getUrl();
				}
				if (value.equals("Schal")) { 
					path = DoppelungGameResources.INSTANCE.schalSlow().getUrl();
				}
				if (value.equals("Schnee")) { 
					path = DoppelungGameResources.INSTANCE.schneeSlow().getUrl();
				}
				if (value.equals("Sieger")) { 
					path = DoppelungGameResources.INSTANCE.siegerSlow().getUrl();
				}
				if (value.equals("Sohn")) { 
					path = DoppelungGameResources.INSTANCE.sohnSlow().getUrl();
				}
				if (value.equals("Spiegel")) { 
					path = DoppelungGameResources.INSTANCE.spiegelSlow().getUrl();
				}
				if (value.equals("Spiel")) { 
					path = DoppelungGameResources.INSTANCE.spielSlow().getUrl();
				}
				if (value.equals("Straße")) { 
					path = DoppelungGameResources.INSTANCE.strasseSlow().getUrl();
				}
				if (value.equals("Stuhl")) { 
					path = DoppelungGameResources.INSTANCE.stuhlSlow().getUrl();
				}
				if (value.equals("Tafel")) { 
					path = DoppelungGameResources.INSTANCE.tafelSlow().getUrl();
				}
				if (value.equals("Telefon")) { 
					path = DoppelungGameResources.INSTANCE.telefonSlow().getUrl();
				}
				if (value.equals("Vater")) { 
					path = DoppelungGameResources.INSTANCE.vaterSlow().getUrl();
				}
				if (value.equals("Vogel")) { 
					path = DoppelungGameResources.INSTANCE.vogelSlow().getUrl();
				}
				if (value.equals("Wagen")) { 
					path = DoppelungGameResources.INSTANCE.wagenSlow().getUrl();
				}
				if (value.equals("Wahrheit")) { 
					path = DoppelungGameResources.INSTANCE.wahrheitSlow().getUrl();
				}
				if (value.equals("Weg")) { 
					path = DoppelungGameResources.INSTANCE.wegSlow().getUrl();
				}
				if (value.equals("Wiese")) { 
					path = DoppelungGameResources.INSTANCE.wieseSlow().getUrl();
				}
				if (value.equals("Wohnung")) { 
					path = DoppelungGameResources.INSTANCE.wohnungSlow().getUrl();
				}
				if (value.equals("Zahl")) { 
					path = DoppelungGameResources.INSTANCE.zahlSlow().getUrl();
				}
				if (value.equals("Zähler")) { 
					path = DoppelungGameResources.INSTANCE.zaehlerSlow().getUrl();
				}
				if (value.equals("Ziel")) { 
					path = DoppelungGameResources.INSTANCE.zielSlow().getUrl();
				}
				if (value.equals("Zug")) { 
					path = DoppelungGameResources.INSTANCE.zugSlow().getUrl();
				}
				if (value.equals("ähnlich")) { 
					path = DoppelungGameResources.INSTANCE.aehnlichSlow().getUrl();
				}
				if (value.equals("beten")) { 
					path = DoppelungGameResources.INSTANCE.betenSlow().getUrl();
				}
				if (value.equals("biegen")) { 
					path = DoppelungGameResources.INSTANCE.biegenSlow().getUrl();
				}
				if (value.equals("drehen")) { 
					path = DoppelungGameResources.INSTANCE.drehenSlow().getUrl();
				}
				if (value.equals("fahren")) { 
					path = DoppelungGameResources.INSTANCE.fahrenSlow().getUrl();
				}
				if (value.equals("fehlen")) { 
					path = DoppelungGameResources.INSTANCE.fehlenSlow().getUrl();
				}
				if (value.equals("fröhlich")) { 
					path = DoppelungGameResources.INSTANCE.froehlichSlow().getUrl();
				}
				if (value.equals("führen")) { 
					path = DoppelungGameResources.INSTANCE.fuehrenSlow().getUrl();
				}
				if (value.equals("geben")) { 
					path = DoppelungGameResources.INSTANCE.gebenSlow().getUrl();
				}
				if (value.equals("leben")) { 
					path = DoppelungGameResources.INSTANCE.lebenSlow().getUrl();
				}
				if (value.equals("legen")) { 
					path = DoppelungGameResources.INSTANCE.legenSlow().getUrl();
				}
				if (value.equals("lieben")) { 
					path = DoppelungGameResources.INSTANCE.liebenSlow().getUrl();
				}
				if (value.equals("nehmen")) { 
					path = DoppelungGameResources.INSTANCE.nehmenSlow().getUrl();
				}
				if (value.equals("niedlich")) { 
					path = DoppelungGameResources.INSTANCE.niedlichSlow().getUrl();
				}
				if (value.equals("ohne")) { 
					path = DoppelungGameResources.INSTANCE.ohneSlow().getUrl();
				}
				if (value.equals("sagen")) { 
					path = DoppelungGameResources.INSTANCE.sagenSlow().getUrl();
				}
				if (value.equals("schieben")) { 
					path = DoppelungGameResources.INSTANCE.schiebenSlow().getUrl();
				}
				if (value.equals("schief")) { 
					path = DoppelungGameResources.INSTANCE.schiefSlow().getUrl();
				}
				if (value.equals("schließlich")) { 
					path = DoppelungGameResources.INSTANCE.schliesslichSlow().getUrl();
				}
				if (value.equals("schwierig")) { 
					path = DoppelungGameResources.INSTANCE.schwierigSlow().getUrl();
				}
				if (value.equals("tragen")) { 
					path = DoppelungGameResources.INSTANCE.tragenSlow().getUrl();
				}
				if (value.equals("viel")) { 
					path = DoppelungGameResources.INSTANCE.vielSlow().getUrl();
				}
				if (value.equals("während")) { 
					path = DoppelungGameResources.INSTANCE.waehrendSlow().getUrl();
				}
				if (value.equals("wieder")) { 
					path = DoppelungGameResources.INSTANCE.wiederSlow().getUrl();
				}
				if (value.equals("wohnen")) { 
					path = DoppelungGameResources.INSTANCE.wohnenSlow().getUrl();
				}
				if (value.equals("zahlen")) { 
					path = DoppelungGameResources.INSTANCE.zahlenSlow().getUrl();
				}
				if (value.equals("zählen")) { 
					path = DoppelungGameResources.INSTANCE.zaehlenSlow().getUrl();
				}
				if (value.equals("zehn")) { 
					path = DoppelungGameResources.INSTANCE.zehnSlow().getUrl();
				}
				if (value.equals("ziehen")) { 
					path = DoppelungGameResources.INSTANCE.ziehenSlow().getUrl();
				}
				if (value.equals("ziemlich")) { 
					path = DoppelungGameResources.INSTANCE.ziemlichSlow().getUrl();
				}


			}
		}

		return path;

	}
}
