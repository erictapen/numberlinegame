package com.wicam.numberlineweb.client.VowelGame;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.google.gwt.core.client.GWT;
import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.Resources.DoppelungGameResourcesSoundsImpl;

/**
 * Class for retrieving the wave-files
 * 
 * @author alex
 *
 */

public class SoundRetriever {
	
	private static DoppelungGameResourcesSoundsImpl doppelungGameSoundImpl = GWT.create(DoppelungGameResourcesSoundsImpl.class);

	public static Sound getSound(SoundController sc, VowelGameWord word, boolean slow) {

		sc.setDefaultVolume(100);

		String path = getPath(word, slow);
		
		try {
			return sc.createSound(doppelungGameSoundImpl.getMimeType(), path, true, false);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private static String getPath(VowelGameWord word, boolean slow) {

		String path = null;
		String value = word.getWordString();

		if (word.isShortVowel()) {
			if (slow == false) {

				if (value.equals("Ball")) { 
					path = doppelungGameSoundImpl.getInstance().ballNormal().getSafeUri().asString();
				}
				if (value.equals("billig")) { 
					path = doppelungGameSoundImpl.getInstance().billigNormal().getSafeUri().asString();
				}
				if (value.equals("Biss")) { 
					path = doppelungGameSoundImpl.getInstance().bissNormal().getSafeUri().asString();
				}
				if (value.equals("bitter")) { 
					path = doppelungGameSoundImpl.getInstance().bitterNormal().getSafeUri().asString();
				}
				if (value.equals("Blatt")) { 
					path = doppelungGameSoundImpl.getInstance().blattNormal().getSafeUri().asString();
				}
				if (value.equals("blicken")) { 
					path = doppelungGameSoundImpl.getInstance().blickenNormal().getSafeUri().asString();
				}
				if (value.equals("Brett")) { 
					path = doppelungGameSoundImpl.getInstance().brettNormal().getSafeUri().asString();
				}
				if (value.equals("Brille")) { 
					path = doppelungGameSoundImpl.getInstance().brilleNormal().getSafeUri().asString();
				}
				if (value.equals("Brücke")) { 
					path = doppelungGameSoundImpl.getInstance().brueckeNormal().getSafeUri().asString();
				}
				if (value.equals("Brunnen")) { 
					path = doppelungGameSoundImpl.getInstance().brunnenNormal().getSafeUri().asString();
				}
				if (value.equals("Decke")) { 
					path = doppelungGameSoundImpl.getInstance().deckeNormal().getSafeUri().asString();
				}
				if (value.equals("dick")) { 
					path = doppelungGameSoundImpl.getInstance().dickNormal().getSafeUri().asString();
				}
				if (value.equals("Donner")) { 
					path = doppelungGameSoundImpl.getInstance().donnerNormal().getSafeUri().asString();
				}
				if (value.equals("Doppelbett")) { 
					path = doppelungGameSoundImpl.getInstance().doppelbettNormal().getSafeUri().asString();
				}
				if (value.equals("doppelt")) { 
					path = doppelungGameSoundImpl.getInstance().doppeltNormal().getSafeUri().asString();
				}
				if (value.equals("dreckig")) { 
					path = doppelungGameSoundImpl.getInstance().dreckigNormal().getSafeUri().asString();
				}
				if (value.equals("drucken")) { 
					path = doppelungGameSoundImpl.getInstance().druckenNormal().getSafeUri().asString();
				}
				if (value.equals("drücken")) { 
					path = doppelungGameSoundImpl.getInstance().drueckenNormal().getSafeUri().asString();
				}
				if (value.equals("Dummheit")) { 
					path = doppelungGameSoundImpl.getInstance().dummheitNormal().getSafeUri().asString();
				}
				if (value.equals("Ecke")) { 
					path = doppelungGameSoundImpl.getInstance().eckeNormal().getSafeUri().asString();
				}
				if (value.equals("fallen")) { 
					path = doppelungGameSoundImpl.getInstance().fallenNormal().getSafeUri().asString();
				}
				if (value.equals("fällen")) { 
					path = doppelungGameSoundImpl.getInstance().faellenNormal().getSafeUri().asString();
				}
				if (value.equals("Fell")) { 
					path = doppelungGameSoundImpl.getInstance().fellNormal().getSafeUri().asString();
				}
				if (value.equals("Flossen")) { 
					path = doppelungGameSoundImpl.getInstance().flossenNormal().getSafeUri().asString();
				}
				if (value.equals("füllen")) { 
					path = doppelungGameSoundImpl.getInstance().fuellenNormal().getSafeUri().asString();
				}
				if (value.equals("glatt")) { 
					path = doppelungGameSoundImpl.getInstance().glattNormal().getSafeUri().asString();
				}
				if (value.equals("Glück")) { 
					path = doppelungGameSoundImpl.getInstance().glueckNormal().getSafeUri().asString();
				}
				if (value.equals("Griff")) { 
					path = doppelungGameSoundImpl.getInstance().griffNormal().getSafeUri().asString();
				}
				if (value.equals("Halle")) { 
					path = doppelungGameSoundImpl.getInstance().halleNormal().getSafeUri().asString();
				}
				if (value.equals("Hammer")) { 
					path = doppelungGameSoundImpl.getInstance().hammerNormal().getSafeUri().asString();
				}
				if (value.equals("hassen")) { 
					path = doppelungGameSoundImpl.getInstance().hassenNormal().getSafeUri().asString();
				}
				if (value.equals("hässlich")) { 
					path = doppelungGameSoundImpl.getInstance().haesslichNormal().getSafeUri().asString();
				}
				if (value.equals("hell")) { 
					path = doppelungGameSoundImpl.getInstance().hellNormal().getSafeUri().asString();
				}
				if (value.equals("herrlich")) { 
					path = doppelungGameSoundImpl.getInstance().herrlichNormal().getSafeUri().asString();
				}
				if (value.equals("hoffen")) { 
					path = doppelungGameSoundImpl.getInstance().hoffenNormal().getSafeUri().asString();
				}
				if (value.equals("Hoffnung")) { 
					path = doppelungGameSoundImpl.getInstance().hoffnungNormal().getSafeUri().asString();
				}
				if (value.equals("Irrtum")) { 
					path = doppelungGameSoundImpl.getInstance().irrtumNormal().getSafeUri().asString();
				}
				if (value.equals("Kammer")) { 
					path = doppelungGameSoundImpl.getInstance().kammerNormal().getSafeUri().asString();
				}
				if (value.equals("Keller")) { 
					path = doppelungGameSoundImpl.getInstance().kellerNormal().getSafeUri().asString();
				}
				if (value.equals("kennen")) { 
					path = doppelungGameSoundImpl.getInstance().kennenNormal().getSafeUri().asString();
				}
				if (value.equals("Kette")) { 
					path = doppelungGameSoundImpl.getInstance().ketteNormal().getSafeUri().asString();
				}
				if (value.equals("kippen")) { 
					path = doppelungGameSoundImpl.getInstance().kippenNormal().getSafeUri().asString();
				}
				if (value.equals("klappen")) { 
					path = doppelungGameSoundImpl.getInstance().klappenNormal().getSafeUri().asString();
				}
				if (value.equals("klappern")) { 
					path = doppelungGameSoundImpl.getInstance().klappernNormal().getSafeUri().asString();
				}
				if (value.equals("klettern")) { 
					path = doppelungGameSoundImpl.getInstance().kletternNormal().getSafeUri().asString();
				}
				if (value.equals("Klippe")) { 
					path = doppelungGameSoundImpl.getInstance().klippeNormal().getSafeUri().asString();
				}
				if (value.equals("knacken")) { 
					path = doppelungGameSoundImpl.getInstance().knackenNormal().getSafeUri().asString();
				}
				if (value.equals("knapp")) { 
					path = doppelungGameSoundImpl.getInstance().knappNormal().getSafeUri().asString();
				}
				if (value.equals("knittern")) { 
					path = doppelungGameSoundImpl.getInstance().knitternNormal().getSafeUri().asString();
				}
				if (value.equals("knurren")) { 
					path = doppelungGameSoundImpl.getInstance().knurrenNormal().getSafeUri().asString();
				}
				if (value.equals("Komma")) { 
					path = doppelungGameSoundImpl.getInstance().kommaNormal().getSafeUri().asString();
				}
				if (value.equals("krumm")) { 
					path = doppelungGameSoundImpl.getInstance().krummNormal().getSafeUri().asString();
				}
				if (value.equals("lassen")) { 
					path = doppelungGameSoundImpl.getInstance().lassenNormal().getSafeUri().asString();
				}
				if (value.equals("lässig")) { 
					path = doppelungGameSoundImpl.getInstance().laessigNormal().getSafeUri().asString();
				}
				if (value.equals("Locke")) { 
					path = doppelungGameSoundImpl.getInstance().lockeNormal().getSafeUri().asString();
				}
				if (value.equals("lockig")) { 
					path = doppelungGameSoundImpl.getInstance().lockigNormal().getSafeUri().asString();
				}
				if (value.equals("Lücke")) { 
					path = doppelungGameSoundImpl.getInstance().lueckeNormal().getSafeUri().asString();
				}
				if (value.equals("messen")) { 
					path = doppelungGameSoundImpl.getInstance().messenNormal().getSafeUri().asString();
				}
				if (value.equals("Mitte")) { 
					path = doppelungGameSoundImpl.getInstance().mitteNormal().getSafeUri().asString();
				}
				if (value.equals("Mücke")) { 
					path = doppelungGameSoundImpl.getInstance().mueckeNormal().getSafeUri().asString();
				}
				if (value.equals("nennen")) { 
					path = doppelungGameSoundImpl.getInstance().nennenNormal().getSafeUri().asString();
				}
				if (value.equals("Nummer")) { 
					path = doppelungGameSoundImpl.getInstance().nummerNormal().getSafeUri().asString();
				}
				if (value.equals("öffnen")) { 
					path = doppelungGameSoundImpl.getInstance().oeffnenNormal().getSafeUri().asString();
				}
				if (value.equals("Päckchen")) { 
					path = doppelungGameSoundImpl.getInstance().paeckchenNormal().getSafeUri().asString();
				}
				if (value.equals("packen")) { 
					path = doppelungGameSoundImpl.getInstance().packenNormal().getSafeUri().asString();
				}
				if (value.equals("passen")) { 
					path = doppelungGameSoundImpl.getInstance().passenNormal().getSafeUri().asString();
				}
				if (value.equals("Pfiff")) { 
					path = doppelungGameSoundImpl.getInstance().pfiffNormal().getSafeUri().asString();
				}
				if (value.equals("pfiffig")) { 
					path = doppelungGameSoundImpl.getInstance().pfiffigNormal().getSafeUri().asString();
				}
				if (value.equals("Puppentheater")) { 
					path = doppelungGameSoundImpl.getInstance().puppentheaterNormal().getSafeUri().asString();
				}
				if (value.equals("Qualle")) { 
					path = doppelungGameSoundImpl.getInstance().qualleNormal().getSafeUri().asString();
				}
				if (value.equals("Quelle")) { 
					path = doppelungGameSoundImpl.getInstance().quelleNormal().getSafeUri().asString();
				}
				if (value.equals("Rennbahn")) { 
					path = doppelungGameSoundImpl.getInstance().rennbahnNormal().getSafeUri().asString();
				}
				if (value.equals("rennen")) { 
					path = doppelungGameSoundImpl.getInstance().rennenNormal().getSafeUri().asString();
				}
				if (value.equals("Riss")) { 
					path = doppelungGameSoundImpl.getInstance().rissNormal().getSafeUri().asString();
				}
				if (value.equals("Ritt")) { 
					path = doppelungGameSoundImpl.getInstance().rittNormal().getSafeUri().asString();
				}
				if (value.equals("rollen")) { 
					path = doppelungGameSoundImpl.getInstance().rollenNormal().getSafeUri().asString();
				}
				if (value.equals("Rücksicht")) { 
					path = doppelungGameSoundImpl.getInstance().ruecksichtNormal().getSafeUri().asString();
				}
				if (value.equals("sammeln")) { 
					path = doppelungGameSoundImpl.getInstance().sammelnNormal().getSafeUri().asString();
				}
				if (value.equals("schaffen")) { 
					path = doppelungGameSoundImpl.getInstance().schaffenNormal().getSafeUri().asString();
				}
				if (value.equals("Schall")) { 
					path = doppelungGameSoundImpl.getInstance().schallNormal().getSafeUri().asString();
				}
				if (value.equals("Schallgeschwindigkeit")) { 
					path = doppelungGameSoundImpl.getInstance().schallgeschwindigkeitNormal().getSafeUri().asString();
				}
				if (value.equals("scharren")) { 
					path = doppelungGameSoundImpl.getInstance().scharrenNormal().getSafeUri().asString();
				}
				if (value.equals("schicken")) { 
					path = doppelungGameSoundImpl.getInstance().schickenNormal().getSafeUri().asString();
				}
				if (value.equals("Schiff")) { 
					path = doppelungGameSoundImpl.getInstance().schiffNormal().getSafeUri().asString();
				}
				if (value.equals("Schimmel")) { 
					path = doppelungGameSoundImpl.getInstance().schimmelNormal().getSafeUri().asString();
				}
				if (value.equals("Schlamm")) { 
					path = doppelungGameSoundImpl.getInstance().schlammNormal().getSafeUri().asString();
				}
				if (value.equals("schleppen")) { 
					path = doppelungGameSoundImpl.getInstance().schleppenNormal().getSafeUri().asString();
				}
				if (value.equals("schlimm")) { 
					path = doppelungGameSoundImpl.getInstance().schlimmNormal().getSafeUri().asString();
				}
				if (value.equals("schlucken")) { 
					path = doppelungGameSoundImpl.getInstance().schluckenNormal().getSafeUri().asString();
				}
				if (value.equals("Schluss")) { 
					path = doppelungGameSoundImpl.getInstance().schlussNormal().getSafeUri().asString();
				}
				if (value.equals("Schlüssel")) { 
					path = doppelungGameSoundImpl.getInstance().schluesselNormal().getSafeUri().asString();
				}
				if (value.equals("schmettern")) { 
					path = doppelungGameSoundImpl.getInstance().schmetternNormal().getSafeUri().asString();
				}
				if (value.equals("schmollen")) { 
					path = doppelungGameSoundImpl.getInstance().schmollenNormal().getSafeUri().asString();
				}
				if (value.equals("schnappen")) { 
					path = doppelungGameSoundImpl.getInstance().schnappenNormal().getSafeUri().asString();
				}
				if (value.equals("schnell")) { 
					path = doppelungGameSoundImpl.getInstance().schnellNormal().getSafeUri().asString();
				}
				if (value.equals("Schramme")) { 
					path = doppelungGameSoundImpl.getInstance().schrammeNormal().getSafeUri().asString();
				}
				if (value.equals("Schreck")) { 
					path = doppelungGameSoundImpl.getInstance().schreckNormal().getSafeUri().asString();
				}
				if (value.equals("Schuppen")) { 
					path = doppelungGameSoundImpl.getInstance().schuppenNormal().getSafeUri().asString();
				}
				if (value.equals("schuppig")) { 
					path = doppelungGameSoundImpl.getInstance().schuppigNormal().getSafeUri().asString();
				}
				if (value.equals("Schuss")) { 
					path = doppelungGameSoundImpl.getInstance().schussNormal().getSafeUri().asString();
				}
				if (value.equals("Schusswaffe")) { 
					path = doppelungGameSoundImpl.getInstance().schusswaffeNormal().getSafeUri().asString();
				}
				if (value.equals("schütteln")) { 
					path = doppelungGameSoundImpl.getInstance().schuettelnNormal().getSafeUri().asString();
				}
				if (value.equals("Schwamm")) { 
					path = doppelungGameSoundImpl.getInstance().schwammNormal().getSafeUri().asString();
				}
				if (value.equals("schwimmen")) { 
					path = doppelungGameSoundImpl.getInstance().schwimmenNormal().getSafeUri().asString();
				}
				if (value.equals("spannen")) { 
					path = doppelungGameSoundImpl.getInstance().spannenNormal().getSafeUri().asString();
				}
				if (value.equals("Sperre")) { 
					path = doppelungGameSoundImpl.getInstance().sperreNormal().getSafeUri().asString();
				}
				if (value.equals("Splitter")) { 
					path = doppelungGameSoundImpl.getInstance().splitterNormal().getSafeUri().asString();
				}
				if (value.equals("Stamm")) { 
					path = doppelungGameSoundImpl.getInstance().stammNormal().getSafeUri().asString();
				}
				if (value.equals("starr")) { 
					path = doppelungGameSoundImpl.getInstance().starrNormal().getSafeUri().asString();
				}
				if (value.equals("Stecker")) { 
					path = doppelungGameSoundImpl.getInstance().steckerNormal().getSafeUri().asString();
				}
				if (value.equals("Stelle")) { 
					path = doppelungGameSoundImpl.getInstance().stelleNormal().getSafeUri().asString();
				}
				if (value.equals("stellen")) { 
					path = doppelungGameSoundImpl.getInstance().stellenNormal().getSafeUri().asString();
				}
				if (value.equals("still")) { 
					path = doppelungGameSoundImpl.getInstance().stillNormal().getSafeUri().asString();
				}
				if (value.equals("Stimme")) { 
					path = doppelungGameSoundImpl.getInstance().stimmeNormal().getSafeUri().asString();
				}
				if (value.equals("Stoff")) { 
					path = doppelungGameSoundImpl.getInstance().stoffNormal().getSafeUri().asString();
				}
				if (value.equals("stoppen")) { 
					path = doppelungGameSoundImpl.getInstance().stoppenNormal().getSafeUri().asString();
				}
				if (value.equals("stumm")) { 
					path = doppelungGameSoundImpl.getInstance().stummNormal().getSafeUri().asString();
				}
				if (value.equals("summen")) { 
					path = doppelungGameSoundImpl.getInstance().summenNormal().getSafeUri().asString();
				}
				if (value.equals("treffen")) { 
					path = doppelungGameSoundImpl.getInstance().treffenNormal().getSafeUri().asString();
				}
				if (value.equals("trennen")) { 
					path = doppelungGameSoundImpl.getInstance().trennenNormal().getSafeUri().asString();
				}
				if (value.equals("Trick")) { 
					path = doppelungGameSoundImpl.getInstance().trickNormal().getSafeUri().asString();
				}
				if (value.equals("Tritt")) { 
					path = doppelungGameSoundImpl.getInstance().trittNormal().getSafeUri().asString();
				}
				if (value.equals("trocken")) { 
					path = doppelungGameSoundImpl.getInstance().trockenNormal().getSafeUri().asString();
				}
				if (value.equals("Trockenheit")) { 
					path = doppelungGameSoundImpl.getInstance().trockenheitNormal().getSafeUri().asString();
				}
				if (value.equals("Tunnel")) { 
					path = doppelungGameSoundImpl.getInstance().tunnelNormal().getSafeUri().asString();
				}
				if (value.equals("voll")) { 
					path = doppelungGameSoundImpl.getInstance().vollNormal().getSafeUri().asString();
				}
				if (value.equals("Wasser")) { 
					path = doppelungGameSoundImpl.getInstance().wasserNormal().getSafeUri().asString();
				}
				if (value.equals("wecken")) { 
					path = doppelungGameSoundImpl.getInstance().weckenNormal().getSafeUri().asString();
				}
				if (value.equals("Wette")) { 
					path = doppelungGameSoundImpl.getInstance().wetteNormal().getSafeUri().asString();
				}
				if (value.equals("Wetter")) { 
					path = doppelungGameSoundImpl.getInstance().wetterNormal().getSafeUri().asString();
				}
				if (value.equals("Wille")) { 
					path = doppelungGameSoundImpl.getInstance().willeNormal().getSafeUri().asString();
				}
				if (value.equals("wissen")) { 
					path = doppelungGameSoundImpl.getInstance().wissenNormal().getSafeUri().asString();
				}
				if (value.equals("zerren")) { 
					path = doppelungGameSoundImpl.getInstance().zerrenNormal().getSafeUri().asString();
				}
				if (value.equals("Zucker")) { 
					path = doppelungGameSoundImpl.getInstance().zuckerNormal().getSafeUri().asString();
				}
				if (value.equals("zwicken")) { 
					path = doppelungGameSoundImpl.getInstance().zwickenNormal().getSafeUri().asString();
				}
				if (value.equals("Zwillinge")) { 
					path = doppelungGameSoundImpl.getInstance().zwillingeNormal().getSafeUri().asString();
				}

			}
			else {

				if (value.equals("Ball")) { 
					path = doppelungGameSoundImpl.getInstance().ballSlow().getSafeUri().asString();
				}
				if (value.equals("billig")) { 
					path = doppelungGameSoundImpl.getInstance().billigSlow().getSafeUri().asString();
				}
				if (value.equals("Biss")) { 
					path = doppelungGameSoundImpl.getInstance().bissSlow().getSafeUri().asString();
				}
				if (value.equals("bitter")) { 
					path = doppelungGameSoundImpl.getInstance().bitterSlow().getSafeUri().asString();
				}
				if (value.equals("Blatt")) { 
					path = doppelungGameSoundImpl.getInstance().blattSlow().getSafeUri().asString();
				}
				if (value.equals("blicken")) { 
					path = doppelungGameSoundImpl.getInstance().blickenSlow().getSafeUri().asString();
				}
				if (value.equals("Brett")) { 
					path = doppelungGameSoundImpl.getInstance().brettSlow().getSafeUri().asString();
				}
				if (value.equals("Brille")) { 
					path = doppelungGameSoundImpl.getInstance().brilleSlow().getSafeUri().asString();
				}
				if (value.equals("Brücke")) { 
					path = doppelungGameSoundImpl.getInstance().brueckeSlow().getSafeUri().asString();
				}
				if (value.equals("Brunnen")) { 
					path = doppelungGameSoundImpl.getInstance().brunnenSlow().getSafeUri().asString();
				}
				if (value.equals("Decke")) { 
					path = doppelungGameSoundImpl.getInstance().deckeSlow().getSafeUri().asString();
				}
				if (value.equals("dick")) { 
					path = doppelungGameSoundImpl.getInstance().dickSlow().getSafeUri().asString();
				}
				if (value.equals("Donner")) { 
					path = doppelungGameSoundImpl.getInstance().donnerSlow().getSafeUri().asString();
				}
				if (value.equals("Doppelbett")) { 
					path = doppelungGameSoundImpl.getInstance().doppelbettSlow().getSafeUri().asString();
				}
				if (value.equals("doppelt")) { 
					path = doppelungGameSoundImpl.getInstance().doppeltSlow().getSafeUri().asString();
				}
				if (value.equals("dreckig")) { 
					path = doppelungGameSoundImpl.getInstance().dreckigSlow().getSafeUri().asString();
				}
				if (value.equals("drucken")) { 
					path = doppelungGameSoundImpl.getInstance().druckenSlow().getSafeUri().asString();
				}
				if (value.equals("drücken")) { 
					path = doppelungGameSoundImpl.getInstance().drueckenSlow().getSafeUri().asString();
				}
				if (value.equals("Dummheit")) { 
					path = doppelungGameSoundImpl.getInstance().dummheitSlow().getSafeUri().asString();
				}
				if (value.equals("Ecke")) { 
					path = doppelungGameSoundImpl.getInstance().eckeSlow().getSafeUri().asString();
				}
				if (value.equals("fallen")) { 
					path = doppelungGameSoundImpl.getInstance().fallenSlow().getSafeUri().asString();
				}
				if (value.equals("fällen")) { 
					path = doppelungGameSoundImpl.getInstance().faellenSlow().getSafeUri().asString();
				}
				if (value.equals("Fell")) { 
					path = doppelungGameSoundImpl.getInstance().fellSlow().getSafeUri().asString();
				}
				if (value.equals("Flossen")) { 
					path = doppelungGameSoundImpl.getInstance().flossenSlow().getSafeUri().asString();
				}
				if (value.equals("füllen")) { 
					path = doppelungGameSoundImpl.getInstance().fuellenSlow().getSafeUri().asString();
				}
				if (value.equals("glatt")) { 
					path = doppelungGameSoundImpl.getInstance().glattSlow().getSafeUri().asString();
				}
				if (value.equals("Glück")) { 
					path = doppelungGameSoundImpl.getInstance().glueckSlow().getSafeUri().asString();
				}
				if (value.equals("Griff")) { 
					path = doppelungGameSoundImpl.getInstance().griffSlow().getSafeUri().asString();
				}
				if (value.equals("Halle")) { 
					path = doppelungGameSoundImpl.getInstance().halleSlow().getSafeUri().asString();
				}
				if (value.equals("Hammer")) { 
					path = doppelungGameSoundImpl.getInstance().hammerSlow().getSafeUri().asString();
				}
				if (value.equals("hassen")) { 
					path = doppelungGameSoundImpl.getInstance().hassenSlow().getSafeUri().asString();
				}
				if (value.equals("hässlich")) { 
					path = doppelungGameSoundImpl.getInstance().haesslichSlow().getSafeUri().asString();
				}
				if (value.equals("hell")) { 
					path = doppelungGameSoundImpl.getInstance().hellSlow().getSafeUri().asString();
				}
				if (value.equals("herrlich")) { 
					path = doppelungGameSoundImpl.getInstance().herrlichSlow().getSafeUri().asString();
				}
				if (value.equals("hoffen")) { 
					path = doppelungGameSoundImpl.getInstance().hoffenSlow().getSafeUri().asString();
				}
				if (value.equals("Hoffnung")) { 
					path = doppelungGameSoundImpl.getInstance().hoffnungSlow().getSafeUri().asString();
				}
				if (value.equals("Irrtum")) { 
					path = doppelungGameSoundImpl.getInstance().irrtumSlow().getSafeUri().asString();
				}
				if (value.equals("Kammer")) { 
					path = doppelungGameSoundImpl.getInstance().kammerSlow().getSafeUri().asString();
				}
				if (value.equals("Keller")) { 
					path = doppelungGameSoundImpl.getInstance().kellerSlow().getSafeUri().asString();
				}
				if (value.equals("kennen")) { 
					path = doppelungGameSoundImpl.getInstance().kennenSlow().getSafeUri().asString();
				}
				if (value.equals("Kette")) { 
					path = doppelungGameSoundImpl.getInstance().ketteSlow().getSafeUri().asString();
				}
				if (value.equals("kippen")) { 
					path = doppelungGameSoundImpl.getInstance().kippenSlow().getSafeUri().asString();
				}
				if (value.equals("klappen")) { 
					path = doppelungGameSoundImpl.getInstance().klappenSlow().getSafeUri().asString();
				}
				if (value.equals("klappern")) { 
					path = doppelungGameSoundImpl.getInstance().klappernSlow().getSafeUri().asString();
				}
				if (value.equals("klettern")) { 
					path = doppelungGameSoundImpl.getInstance().kletternSlow().getSafeUri().asString();
				}
				if (value.equals("Klippe")) { 
					path = doppelungGameSoundImpl.getInstance().klippeSlow().getSafeUri().asString();
				}
				if (value.equals("knacken")) { 
					path = doppelungGameSoundImpl.getInstance().knackenSlow().getSafeUri().asString();
				}
				if (value.equals("knapp")) { 
					path = doppelungGameSoundImpl.getInstance().knappSlow().getSafeUri().asString();
				}
				if (value.equals("knittern")) { 
					path = doppelungGameSoundImpl.getInstance().knitternSlow().getSafeUri().asString();
				}
				if (value.equals("knurren")) { 
					path = doppelungGameSoundImpl.getInstance().knurrenSlow().getSafeUri().asString();
				}
				if (value.equals("Komma")) { 
					path = doppelungGameSoundImpl.getInstance().kommaSlow().getSafeUri().asString();
				}
				if (value.equals("krumm")) { 
					path = doppelungGameSoundImpl.getInstance().krummSlow().getSafeUri().asString();
				}
				if (value.equals("lassen")) { 
					path = doppelungGameSoundImpl.getInstance().lassenSlow().getSafeUri().asString();
				}
				if (value.equals("lässig")) { 
					path = doppelungGameSoundImpl.getInstance().laessigSlow().getSafeUri().asString();
				}
				if (value.equals("Locke")) { 
					path = doppelungGameSoundImpl.getInstance().lockeSlow().getSafeUri().asString();
				}
				if (value.equals("lockig")) { 
					path = doppelungGameSoundImpl.getInstance().lockigSlow().getSafeUri().asString();
				}
				if (value.equals("Lücke")) { 
					path = doppelungGameSoundImpl.getInstance().lueckeSlow().getSafeUri().asString();
				}
				if (value.equals("messen")) { 
					path = doppelungGameSoundImpl.getInstance().messenSlow().getSafeUri().asString();
				}
				if (value.equals("Mitte")) { 
					path = doppelungGameSoundImpl.getInstance().mitteSlow().getSafeUri().asString();
				}
				if (value.equals("Mücke")) { 
					path = doppelungGameSoundImpl.getInstance().mueckeSlow().getSafeUri().asString();
				}
				if (value.equals("nennen")) { 
					path = doppelungGameSoundImpl.getInstance().nennenSlow().getSafeUri().asString();
				}
				if (value.equals("Nummer")) { 
					path = doppelungGameSoundImpl.getInstance().nummerSlow().getSafeUri().asString();
				}
				if (value.equals("öffnen")) { 
					path = doppelungGameSoundImpl.getInstance().oeffnenSlow().getSafeUri().asString();
				}
				if (value.equals("Päckchen")) { 
					path = doppelungGameSoundImpl.getInstance().paeckchenSlow().getSafeUri().asString();
				}
				if (value.equals("packen")) { 
					path = doppelungGameSoundImpl.getInstance().packenSlow().getSafeUri().asString();
				}
				if (value.equals("passen")) { 
					path = doppelungGameSoundImpl.getInstance().passenSlow().getSafeUri().asString();
				}
				if (value.equals("Pfiff")) { 
					path = doppelungGameSoundImpl.getInstance().pfiffSlow().getSafeUri().asString();
				}
				if (value.equals("pfiffig")) { 
					path = doppelungGameSoundImpl.getInstance().pfiffigSlow().getSafeUri().asString();
				}
				if (value.equals("Puppentheater")) { 
					path = doppelungGameSoundImpl.getInstance().puppentheaterSlow().getSafeUri().asString();
				}
				if (value.equals("Qualle")) { 
					path = doppelungGameSoundImpl.getInstance().qualleSlow().getSafeUri().asString();
				}
				if (value.equals("Quelle")) { 
					path = doppelungGameSoundImpl.getInstance().quelleSlow().getSafeUri().asString();
				}
				if (value.equals("Rennbahn")) { 
					path = doppelungGameSoundImpl.getInstance().rennbahnSlow().getSafeUri().asString();
				}
				if (value.equals("rennen")) { 
					path = doppelungGameSoundImpl.getInstance().rennenSlow().getSafeUri().asString();
				}
				if (value.equals("Riss")) { 
					path = doppelungGameSoundImpl.getInstance().rissSlow().getSafeUri().asString();
				}
				if (value.equals("Ritt")) { 
					path = doppelungGameSoundImpl.getInstance().rittSlow().getSafeUri().asString();
				}
				if (value.equals("rollen")) { 
					path = doppelungGameSoundImpl.getInstance().rollenSlow().getSafeUri().asString();
				}
				if (value.equals("Rücksicht")) { 
					path = doppelungGameSoundImpl.getInstance().ruecksichtSlow().getSafeUri().asString();
				}
				if (value.equals("sammeln")) { 
					path = doppelungGameSoundImpl.getInstance().sammelnSlow().getSafeUri().asString();
				}
				if (value.equals("schaffen")) { 
					path = doppelungGameSoundImpl.getInstance().schaffenSlow().getSafeUri().asString();
				}
				if (value.equals("Schall")) { 
					path = doppelungGameSoundImpl.getInstance().schallSlow().getSafeUri().asString();
				}
				if (value.equals("Schallgeschwindigkeit")) { 
					path = doppelungGameSoundImpl.getInstance().schallgeschwindigkeitSlow().getSafeUri().asString();
				}
				if (value.equals("scharren")) { 
					path = doppelungGameSoundImpl.getInstance().scharrenSlow().getSafeUri().asString();
				}
				if (value.equals("schicken")) { 
					path = doppelungGameSoundImpl.getInstance().schickenSlow().getSafeUri().asString();
				}
				if (value.equals("Schiff")) { 
					path = doppelungGameSoundImpl.getInstance().schiffSlow().getSafeUri().asString();
				}
				if (value.equals("Schimmel")) { 
					path = doppelungGameSoundImpl.getInstance().schimmelSlow().getSafeUri().asString();
				}
				if (value.equals("Schlamm")) { 
					path = doppelungGameSoundImpl.getInstance().schlammSlow().getSafeUri().asString();
				}
				if (value.equals("schleppen")) { 
					path = doppelungGameSoundImpl.getInstance().schleppenSlow().getSafeUri().asString();
				}
				if (value.equals("schlimm")) { 
					path = doppelungGameSoundImpl.getInstance().schlimmSlow().getSafeUri().asString();
				}
				if (value.equals("schlucken")) { 
					path = doppelungGameSoundImpl.getInstance().schluckenSlow().getSafeUri().asString();
				}
				if (value.equals("Schluss")) { 
					path = doppelungGameSoundImpl.getInstance().schlussSlow().getSafeUri().asString();
				}
				if (value.equals("Schlüssel")) { 
					path = doppelungGameSoundImpl.getInstance().schluesselSlow().getSafeUri().asString();
				}
				if (value.equals("schmettern")) { 
					path = doppelungGameSoundImpl.getInstance().schmetternSlow().getSafeUri().asString();
				}
				if (value.equals("schmollen")) { 
					path = doppelungGameSoundImpl.getInstance().schmollenSlow().getSafeUri().asString();
				}
				if (value.equals("schnappen")) { 
					path = doppelungGameSoundImpl.getInstance().schnappenSlow().getSafeUri().asString();
				}
				if (value.equals("schnell")) { 
					path = doppelungGameSoundImpl.getInstance().schnellSlow().getSafeUri().asString();
				}
				if (value.equals("Schramme")) { 
					path = doppelungGameSoundImpl.getInstance().schrammeSlow().getSafeUri().asString();
				}
				if (value.equals("Schreck")) { 
					path = doppelungGameSoundImpl.getInstance().schreckSlow().getSafeUri().asString();
				}
				if (value.equals("Schuppen")) { 
					path = doppelungGameSoundImpl.getInstance().schuppenSlow().getSafeUri().asString();
				}
				if (value.equals("schuppig")) { 
					path = doppelungGameSoundImpl.getInstance().schuppigSlow().getSafeUri().asString();
				}
				if (value.equals("Schuss")) { 
					path = doppelungGameSoundImpl.getInstance().schussSlow().getSafeUri().asString();
				}
				if (value.equals("Schusswaffe")) { 
					path = doppelungGameSoundImpl.getInstance().schusswaffeSlow().getSafeUri().asString();
				}
				if (value.equals("schütteln")) { 
					path = doppelungGameSoundImpl.getInstance().schuettelnSlow().getSafeUri().asString();
				}
				if (value.equals("Schwamm")) { 
					path = doppelungGameSoundImpl.getInstance().schwammSlow().getSafeUri().asString();
				}
				if (value.equals("schwimmen")) { 
					path = doppelungGameSoundImpl.getInstance().schwimmenSlow().getSafeUri().asString();
				}
				if (value.equals("spannen")) { 
					path = doppelungGameSoundImpl.getInstance().spannenSlow().getSafeUri().asString();
				}
				if (value.equals("Sperre")) { 
					path = doppelungGameSoundImpl.getInstance().sperreSlow().getSafeUri().asString();
				}
				if (value.equals("Splitter")) { 
					path = doppelungGameSoundImpl.getInstance().splitterSlow().getSafeUri().asString();
				}
				if (value.equals("Stamm")) { 
					path = doppelungGameSoundImpl.getInstance().stammSlow().getSafeUri().asString();
				}
				if (value.equals("starr")) { 
					path = doppelungGameSoundImpl.getInstance().starrSlow().getSafeUri().asString();
				}
				if (value.equals("Stecker")) { 
					path = doppelungGameSoundImpl.getInstance().steckerSlow().getSafeUri().asString();
				}
				if (value.equals("Stelle")) { 
					path = doppelungGameSoundImpl.getInstance().stelleSlow().getSafeUri().asString();
				}
				if (value.equals("stellen")) { 
					path = doppelungGameSoundImpl.getInstance().stellenSlow().getSafeUri().asString();
				}
				if (value.equals("still")) { 
					path = doppelungGameSoundImpl.getInstance().stillSlow().getSafeUri().asString();
				}
				if (value.equals("Stimme")) { 
					path = doppelungGameSoundImpl.getInstance().stimmeSlow().getSafeUri().asString();
				}
				if (value.equals("Stoff")) { 
					path = doppelungGameSoundImpl.getInstance().stoffSlow().getSafeUri().asString();
				}
				if (value.equals("stoppen")) { 
					path = doppelungGameSoundImpl.getInstance().stoppenSlow().getSafeUri().asString();
				}
				if (value.equals("stumm")) { 
					path = doppelungGameSoundImpl.getInstance().stummSlow().getSafeUri().asString();
				}
				if (value.equals("summen")) { 
					path = doppelungGameSoundImpl.getInstance().summenSlow().getSafeUri().asString();
				}
				if (value.equals("treffen")) { 
					path = doppelungGameSoundImpl.getInstance().treffenSlow().getSafeUri().asString();
				}
				if (value.equals("trennen")) { 
					path = doppelungGameSoundImpl.getInstance().trennenSlow().getSafeUri().asString();
				}
				if (value.equals("Trick")) { 
					path = doppelungGameSoundImpl.getInstance().trickSlow().getSafeUri().asString();
				}
				if (value.equals("Tritt")) { 
					path = doppelungGameSoundImpl.getInstance().trittSlow().getSafeUri().asString();
				}
				if (value.equals("trocken")) { 
					path = doppelungGameSoundImpl.getInstance().trockenSlow().getSafeUri().asString();
				}
				if (value.equals("Trockenheit")) { 
					path = doppelungGameSoundImpl.getInstance().trockenheitSlow().getSafeUri().asString();
				}
				if (value.equals("Tunnel")) { 
					path = doppelungGameSoundImpl.getInstance().tunnelSlow().getSafeUri().asString();
				}
				if (value.equals("voll")) { 
					path = doppelungGameSoundImpl.getInstance().vollSlow().getSafeUri().asString();
				}
				if (value.equals("Wasser")) { 
					path = doppelungGameSoundImpl.getInstance().wasserSlow().getSafeUri().asString();
				}
				if (value.equals("wecken")) { 
					path = doppelungGameSoundImpl.getInstance().weckenSlow().getSafeUri().asString();
				}
				if (value.equals("Wette")) { 
					path = doppelungGameSoundImpl.getInstance().wetteSlow().getSafeUri().asString();
				}
				if (value.equals("Wetter")) { 
					path = doppelungGameSoundImpl.getInstance().wetterSlow().getSafeUri().asString();
				}
				if (value.equals("Wille")) { 
					path = doppelungGameSoundImpl.getInstance().willeSlow().getSafeUri().asString();
				}
				if (value.equals("wissen")) { 
					path = doppelungGameSoundImpl.getInstance().wissenSlow().getSafeUri().asString();
				}
				if (value.equals("zerren")) { 
					path = doppelungGameSoundImpl.getInstance().zerrenSlow().getSafeUri().asString();
				}
				if (value.equals("Zucker")) { 
					path = doppelungGameSoundImpl.getInstance().zuckerSlow().getSafeUri().asString();
				}
				if (value.equals("zwicken")) { 
					path = doppelungGameSoundImpl.getInstance().zwickenSlow().getSafeUri().asString();
				}
				if (value.equals("Zwillinge")) { 
					path = doppelungGameSoundImpl.getInstance().zwillingeSlow().getSafeUri().asString();
				}


			}
		}
		else {
			if (slow == false) {

				if (value.equals("Ahnung")) { 
					path = doppelungGameSoundImpl.getInstance().ahnungNormal().getSafeUri().asString();
				}
				if (value.equals("Bahn")) { 
					path = doppelungGameSoundImpl.getInstance().bahnNormal().getSafeUri().asString();
				}
				if (value.equals("Blume")) { 
					path = doppelungGameSoundImpl.getInstance().blumeNormal().getSafeUri().asString();
				}
				if (value.equals("Bruder")) { 
					path = doppelungGameSoundImpl.getInstance().bruderNormal().getSafeUri().asString();
				}
				if (value.equals("Ebene")) { 
					path = doppelungGameSoundImpl.getInstance().ebeneNormal().getSafeUri().asString();
				}
				if (value.equals("Fehler")) { 
					path = doppelungGameSoundImpl.getInstance().fehlerNormal().getSafeUri().asString();
				}
				if (value.equals("Flug")) { 
					path = doppelungGameSoundImpl.getInstance().flugNormal().getSafeUri().asString();
				}
				if (value.equals("Frieden")) { 
					path = doppelungGameSoundImpl.getInstance().friedenNormal().getSafeUri().asString();
				}
				if (value.equals("Frühling")) { 
					path = doppelungGameSoundImpl.getInstance().fruehlingNormal().getSafeUri().asString();
				}
				if (value.equals("Gas")) { 
					path = doppelungGameSoundImpl.getInstance().gasNormal().getSafeUri().asString();
				}
				if (value.equals("Hose")) { 
					path = doppelungGameSoundImpl.getInstance().hoseNormal().getSafeUri().asString();
				}
				if (value.equals("Höhle")) { 
					path = doppelungGameSoundImpl.getInstance().hoehleNormal().getSafeUri().asString();
				}
				if (value.equals("Kino")) { 
					path = doppelungGameSoundImpl.getInstance().kinoNormal().getSafeUri().asString();
				}
				if (value.equals("Kohle")) { 
					path = doppelungGameSoundImpl.getInstance().kohleNormal().getSafeUri().asString();
				}
				if (value.equals("Lehrer")) { 
					path = doppelungGameSoundImpl.getInstance().lehrerNormal().getSafeUri().asString();
				}
				if (value.equals("Liege")) { 
					path = doppelungGameSoundImpl.getInstance().liegeNormal().getSafeUri().asString();
				}
				if (value.equals("Löwe")) { 
					path = doppelungGameSoundImpl.getInstance().loeweNormal().getSafeUri().asString();
				}
				if (value.equals("Lüge")) { 
					path = doppelungGameSoundImpl.getInstance().luegeNormal().getSafeUri().asString();
				}
				if (value.equals("Miete")) { 
					path = doppelungGameSoundImpl.getInstance().mieteNormal().getSafeUri().asString();
				}
				if (value.equals("Rede")) { 
					path = doppelungGameSoundImpl.getInstance().redeNormal().getSafeUri().asString();
				}
				if (value.equals("Rose")) { 
					path = doppelungGameSoundImpl.getInstance().roseNormal().getSafeUri().asString();
				}
				if (value.equals("Schal")) { 
					path = doppelungGameSoundImpl.getInstance().schalNormal().getSafeUri().asString();
				}
				if (value.equals("Schnee")) { 
					path = doppelungGameSoundImpl.getInstance().schneeNormal().getSafeUri().asString();
				}
				if (value.equals("Sieger")) { 
					path = doppelungGameSoundImpl.getInstance().siegerNormal().getSafeUri().asString();
				}
				if (value.equals("Sohn")) { 
					path = doppelungGameSoundImpl.getInstance().sohnNormal().getSafeUri().asString();
				}
				if (value.equals("Spiegel")) { 
					path = doppelungGameSoundImpl.getInstance().spiegelNormal().getSafeUri().asString();
				}
				if (value.equals("Spiel")) { 
					path = doppelungGameSoundImpl.getInstance().spielNormal().getSafeUri().asString();
				}
				if (value.equals("Straße")) { 
					path = doppelungGameSoundImpl.getInstance().strasseNormal().getSafeUri().asString();
				}
				if (value.equals("Stuhl")) { 
					path = doppelungGameSoundImpl.getInstance().stuhlNormal().getSafeUri().asString();
				}
				if (value.equals("Tafel")) { 
					path = doppelungGameSoundImpl.getInstance().tafelNormal().getSafeUri().asString();
				}
				if (value.equals("Telefon")) { 
					path = doppelungGameSoundImpl.getInstance().telefonNormal().getSafeUri().asString();
				}
				if (value.equals("Vater")) { 
					path = doppelungGameSoundImpl.getInstance().vaterNormal().getSafeUri().asString();
				}
				if (value.equals("Vogel")) { 
					path = doppelungGameSoundImpl.getInstance().vogelNormal().getSafeUri().asString();
				}
				if (value.equals("Wagen")) { 
					path = doppelungGameSoundImpl.getInstance().wagenNormal().getSafeUri().asString();
				}
				if (value.equals("Wahrheit")) { 
					path = doppelungGameSoundImpl.getInstance().wahrheitNormal().getSafeUri().asString();
				}
				if (value.equals("Weg")) { 
					path = doppelungGameSoundImpl.getInstance().wegNormal().getSafeUri().asString();
				}
				if (value.equals("Wiese")) { 
					path = doppelungGameSoundImpl.getInstance().wieseNormal().getSafeUri().asString();
				}
				if (value.equals("Wohnung")) { 
					path = doppelungGameSoundImpl.getInstance().wohnungNormal().getSafeUri().asString();
				}
				if (value.equals("Zahl")) { 
					path = doppelungGameSoundImpl.getInstance().zahlNormal().getSafeUri().asString();
				}
				if (value.equals("Zähler")) { 
					path = doppelungGameSoundImpl.getInstance().zaehlerNormal().getSafeUri().asString();
				}
				if (value.equals("Ziel")) { 
					path = doppelungGameSoundImpl.getInstance().zielNormal().getSafeUri().asString();
				}
				if (value.equals("Zug")) { 
					path = doppelungGameSoundImpl.getInstance().zugNormal().getSafeUri().asString();
				}
				if (value.equals("ähnlich")) { 
					path = doppelungGameSoundImpl.getInstance().aehnlichNormal().getSafeUri().asString();
				}
				if (value.equals("beten")) { 
					path = doppelungGameSoundImpl.getInstance().betenNormal().getSafeUri().asString();
				}
				if (value.equals("biegen")) { 
					path = doppelungGameSoundImpl.getInstance().biegenNormal().getSafeUri().asString();
				}
				if (value.equals("drehen")) { 
					path = doppelungGameSoundImpl.getInstance().drehenNormal().getSafeUri().asString();
				}
				if (value.equals("fahren")) { 
					path = doppelungGameSoundImpl.getInstance().fahrenNormal().getSafeUri().asString();
				}
				if (value.equals("fehlen")) { 
					path = doppelungGameSoundImpl.getInstance().fehlenNormal().getSafeUri().asString();
				}
				if (value.equals("fröhlich")) { 
					path = doppelungGameSoundImpl.getInstance().froehlichNormal().getSafeUri().asString();
				}
				if (value.equals("führen")) { 
					path = doppelungGameSoundImpl.getInstance().fuehrenNormal().getSafeUri().asString();
				}
				if (value.equals("geben")) { 
					path = doppelungGameSoundImpl.getInstance().gebenNormal().getSafeUri().asString();
				}
				if (value.equals("leben")) { 
					path = doppelungGameSoundImpl.getInstance().lebenNormal().getSafeUri().asString();
				}
				if (value.equals("legen")) { 
					path = doppelungGameSoundImpl.getInstance().legenNormal().getSafeUri().asString();
				}
				if (value.equals("lieben")) { 
					path = doppelungGameSoundImpl.getInstance().liebenNormal().getSafeUri().asString();
				}
				if (value.equals("nehmen")) { 
					path = doppelungGameSoundImpl.getInstance().nehmenNormal().getSafeUri().asString();
				}
				if (value.equals("niedlich")) { 
					path = doppelungGameSoundImpl.getInstance().niedlichNormal().getSafeUri().asString();
				}
				if (value.equals("ohne")) { 
					path = doppelungGameSoundImpl.getInstance().ohneNormal().getSafeUri().asString();
				}
				if (value.equals("sagen")) { 
					path = doppelungGameSoundImpl.getInstance().sagenNormal().getSafeUri().asString();
				}
				if (value.equals("schieben")) { 
					path = doppelungGameSoundImpl.getInstance().schiebenNormal().getSafeUri().asString();
				}
				if (value.equals("schief")) { 
					path = doppelungGameSoundImpl.getInstance().schiefNormal().getSafeUri().asString();
				}
				if (value.equals("schließlich")) { 
					path = doppelungGameSoundImpl.getInstance().schliesslichNormal().getSafeUri().asString();
				}
				if (value.equals("schwierig")) { 
					path = doppelungGameSoundImpl.getInstance().schwierigNormal().getSafeUri().asString();
				}
				if (value.equals("tragen")) { 
					path = doppelungGameSoundImpl.getInstance().tragenNormal().getSafeUri().asString();
				}
				if (value.equals("viel")) { 
					path = doppelungGameSoundImpl.getInstance().vielNormal().getSafeUri().asString();
				}
				if (value.equals("während")) { 
					path = doppelungGameSoundImpl.getInstance().waehrendNormal().getSafeUri().asString();
				}
				if (value.equals("wieder")) { 
					path = doppelungGameSoundImpl.getInstance().wiederNormal().getSafeUri().asString();
				}
				if (value.equals("wohnen")) { 
					path = doppelungGameSoundImpl.getInstance().wohnenNormal().getSafeUri().asString();
				}
				if (value.equals("zahlen")) { 
					path = doppelungGameSoundImpl.getInstance().zahlenNormal().getSafeUri().asString();
				}
				if (value.equals("zählen")) { 
					path = doppelungGameSoundImpl.getInstance().zaehlenNormal().getSafeUri().asString();
				}
				if (value.equals("zehn")) { 
					path = doppelungGameSoundImpl.getInstance().zehnNormal().getSafeUri().asString();
				}
				if (value.equals("ziehen")) { 
					path = doppelungGameSoundImpl.getInstance().ziehenNormal().getSafeUri().asString();
				}
				if (value.equals("ziemlich")) { 
					path = doppelungGameSoundImpl.getInstance().ziemlichNormal().getSafeUri().asString();
				}


			}
			else {

				if (value.equals("Ahnung")) { 
					path = doppelungGameSoundImpl.getInstance().ahnungSlow().getSafeUri().asString();
				}
				if (value.equals("Bahn")) { 
					path = doppelungGameSoundImpl.getInstance().bahnSlow().getSafeUri().asString();
				}
				if (value.equals("Blume")) { 
					path = doppelungGameSoundImpl.getInstance().blumeSlow().getSafeUri().asString();
				}
				if (value.equals("Bruder")) { 
					path = doppelungGameSoundImpl.getInstance().bruderSlow().getSafeUri().asString();
				}
				if (value.equals("Ebene")) { 
					path = doppelungGameSoundImpl.getInstance().ebeneSlow().getSafeUri().asString();
				}
				if (value.equals("Fehler")) { 
					path = doppelungGameSoundImpl.getInstance().fehlerSlow().getSafeUri().asString();
				}
				if (value.equals("Flug")) { 
					path = doppelungGameSoundImpl.getInstance().flugSlow().getSafeUri().asString();
				}
				if (value.equals("Frieden")) { 
					path = doppelungGameSoundImpl.getInstance().friedenSlow().getSafeUri().asString();
				}
				if (value.equals("Frühling")) { 
					path = doppelungGameSoundImpl.getInstance().fruehlingSlow().getSafeUri().asString();
				}
				if (value.equals("Gas")) { 
					path = doppelungGameSoundImpl.getInstance().gasSlow().getSafeUri().asString();
				}
				if (value.equals("Hose")) { 
					path = doppelungGameSoundImpl.getInstance().hoseSlow().getSafeUri().asString();
				}
				if (value.equals("Höhle")) { 
					path = doppelungGameSoundImpl.getInstance().hoehleSlow().getSafeUri().asString();
				}
				if (value.equals("Kino")) { 
					path = doppelungGameSoundImpl.getInstance().kinoSlow().getSafeUri().asString();
				}
				if (value.equals("Kohle")) { 
					path = doppelungGameSoundImpl.getInstance().kohleSlow().getSafeUri().asString();
				}
				if (value.equals("Lehrer")) { 
					path = doppelungGameSoundImpl.getInstance().lehrerSlow().getSafeUri().asString();
				}
				if (value.equals("Liege")) { 
					path = doppelungGameSoundImpl.getInstance().liegeSlow().getSafeUri().asString();
				}
				if (value.equals("Löwe")) { 
					path = doppelungGameSoundImpl.getInstance().loeweSlow().getSafeUri().asString();
				}
				if (value.equals("Lüge")) { 
					path = doppelungGameSoundImpl.getInstance().luegeSlow().getSafeUri().asString();
				}
				if (value.equals("Miete")) { 
					path = doppelungGameSoundImpl.getInstance().mieteSlow().getSafeUri().asString();
				}
				if (value.equals("Rede")) { 
					path = doppelungGameSoundImpl.getInstance().redeSlow().getSafeUri().asString();
				}
				if (value.equals("Rose")) { 
					path = doppelungGameSoundImpl.getInstance().roseSlow().getSafeUri().asString();
				}
				if (value.equals("Schal")) { 
					path = doppelungGameSoundImpl.getInstance().schalSlow().getSafeUri().asString();
				}
				if (value.equals("Schnee")) { 
					path = doppelungGameSoundImpl.getInstance().schneeSlow().getSafeUri().asString();
				}
				if (value.equals("Sieger")) { 
					path = doppelungGameSoundImpl.getInstance().siegerSlow().getSafeUri().asString();
				}
				if (value.equals("Sohn")) { 
					path = doppelungGameSoundImpl.getInstance().sohnSlow().getSafeUri().asString();
				}
				if (value.equals("Spiegel")) { 
					path = doppelungGameSoundImpl.getInstance().spiegelSlow().getSafeUri().asString();
				}
				if (value.equals("Spiel")) { 
					path = doppelungGameSoundImpl.getInstance().spielSlow().getSafeUri().asString();
				}
				if (value.equals("Straße")) { 
					path = doppelungGameSoundImpl.getInstance().strasseSlow().getSafeUri().asString();
				}
				if (value.equals("Stuhl")) { 
					path = doppelungGameSoundImpl.getInstance().stuhlSlow().getSafeUri().asString();
				}
				if (value.equals("Tafel")) { 
					path = doppelungGameSoundImpl.getInstance().tafelSlow().getSafeUri().asString();
				}
				if (value.equals("Telefon")) { 
					path = doppelungGameSoundImpl.getInstance().telefonSlow().getSafeUri().asString();
				}
				if (value.equals("Vater")) { 
					path = doppelungGameSoundImpl.getInstance().vaterSlow().getSafeUri().asString();
				}
				if (value.equals("Vogel")) { 
					path = doppelungGameSoundImpl.getInstance().vogelSlow().getSafeUri().asString();
				}
				if (value.equals("Wagen")) { 
					path = doppelungGameSoundImpl.getInstance().wagenSlow().getSafeUri().asString();
				}
				if (value.equals("Wahrheit")) { 
					path = doppelungGameSoundImpl.getInstance().wahrheitSlow().getSafeUri().asString();
				}
				if (value.equals("Weg")) { 
					path = doppelungGameSoundImpl.getInstance().wegSlow().getSafeUri().asString();
				}
				if (value.equals("Wiese")) { 
					path = doppelungGameSoundImpl.getInstance().wieseSlow().getSafeUri().asString();
				}
				if (value.equals("Wohnung")) { 
					path = doppelungGameSoundImpl.getInstance().wohnungSlow().getSafeUri().asString();
				}
				if (value.equals("Zahl")) { 
					path = doppelungGameSoundImpl.getInstance().zahlSlow().getSafeUri().asString();
				}
				if (value.equals("Zähler")) { 
					path = doppelungGameSoundImpl.getInstance().zaehlerSlow().getSafeUri().asString();
				}
				if (value.equals("Ziel")) { 
					path = doppelungGameSoundImpl.getInstance().zielSlow().getSafeUri().asString();
				}
				if (value.equals("Zug")) { 
					path = doppelungGameSoundImpl.getInstance().zugSlow().getSafeUri().asString();
				}
				if (value.equals("ähnlich")) { 
					path = doppelungGameSoundImpl.getInstance().aehnlichSlow().getSafeUri().asString();
				}
				if (value.equals("beten")) { 
					path = doppelungGameSoundImpl.getInstance().betenSlow().getSafeUri().asString();
				}
				if (value.equals("biegen")) { 
					path = doppelungGameSoundImpl.getInstance().biegenSlow().getSafeUri().asString();
				}
				if (value.equals("drehen")) { 
					path = doppelungGameSoundImpl.getInstance().drehenSlow().getSafeUri().asString();
				}
				if (value.equals("fahren")) { 
					path = doppelungGameSoundImpl.getInstance().fahrenSlow().getSafeUri().asString();
				}
				if (value.equals("fehlen")) { 
					path = doppelungGameSoundImpl.getInstance().fehlenSlow().getSafeUri().asString();
				}
				if (value.equals("fröhlich")) { 
					path = doppelungGameSoundImpl.getInstance().froehlichSlow().getSafeUri().asString();
				}
				if (value.equals("führen")) { 
					path = doppelungGameSoundImpl.getInstance().fuehrenSlow().getSafeUri().asString();
				}
				if (value.equals("geben")) { 
					path = doppelungGameSoundImpl.getInstance().gebenSlow().getSafeUri().asString();
				}
				if (value.equals("leben")) { 
					path = doppelungGameSoundImpl.getInstance().lebenSlow().getSafeUri().asString();
				}
				if (value.equals("legen")) { 
					path = doppelungGameSoundImpl.getInstance().legenSlow().getSafeUri().asString();
				}
				if (value.equals("lieben")) { 
					path = doppelungGameSoundImpl.getInstance().liebenSlow().getSafeUri().asString();
				}
				if (value.equals("nehmen")) { 
					path = doppelungGameSoundImpl.getInstance().nehmenSlow().getSafeUri().asString();
				}
				if (value.equals("niedlich")) { 
					path = doppelungGameSoundImpl.getInstance().niedlichSlow().getSafeUri().asString();
				}
				if (value.equals("ohne")) { 
					path = doppelungGameSoundImpl.getInstance().ohneSlow().getSafeUri().asString();
				}
				if (value.equals("sagen")) { 
					path = doppelungGameSoundImpl.getInstance().sagenSlow().getSafeUri().asString();
				}
				if (value.equals("schieben")) { 
					path = doppelungGameSoundImpl.getInstance().schiebenSlow().getSafeUri().asString();
				}
				if (value.equals("schief")) { 
					path = doppelungGameSoundImpl.getInstance().schiefSlow().getSafeUri().asString();
				}
				if (value.equals("schließlich")) { 
					path = doppelungGameSoundImpl.getInstance().schliesslichSlow().getSafeUri().asString();
				}
				if (value.equals("schwierig")) { 
					path = doppelungGameSoundImpl.getInstance().schwierigSlow().getSafeUri().asString();
				}
				if (value.equals("tragen")) { 
					path = doppelungGameSoundImpl.getInstance().tragenSlow().getSafeUri().asString();
				}
				if (value.equals("viel")) { 
					path = doppelungGameSoundImpl.getInstance().vielSlow().getSafeUri().asString();
				}
				if (value.equals("während")) { 
					path = doppelungGameSoundImpl.getInstance().waehrendSlow().getSafeUri().asString();
				}
				if (value.equals("wieder")) { 
					path = doppelungGameSoundImpl.getInstance().wiederSlow().getSafeUri().asString();
				}
				if (value.equals("wohnen")) { 
					path = doppelungGameSoundImpl.getInstance().wohnenSlow().getSafeUri().asString();
				}
				if (value.equals("zahlen")) { 
					path = doppelungGameSoundImpl.getInstance().zahlenSlow().getSafeUri().asString();
				}
				if (value.equals("zählen")) { 
					path = doppelungGameSoundImpl.getInstance().zaehlenSlow().getSafeUri().asString();
				}
				if (value.equals("zehn")) { 
					path = doppelungGameSoundImpl.getInstance().zehnSlow().getSafeUri().asString();
				}
				if (value.equals("ziehen")) { 
					path = doppelungGameSoundImpl.getInstance().ziehenSlow().getSafeUri().asString();
				}
				if (value.equals("ziemlich")) { 
					path = doppelungGameSoundImpl.getInstance().ziemlichSlow().getSafeUri().asString();
				}


			}
		}

		return path;

	}
}
