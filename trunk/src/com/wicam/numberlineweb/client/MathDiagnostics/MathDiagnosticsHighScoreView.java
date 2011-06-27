package com.wicam.numberlineweb.client.MathDiagnostics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import com.google.gwt.user.client.ui.HTML;
import com.wicam.numberlineweb.client.HighScoreView;
import com.wicam.numberlineweb.client.Player;

public class MathDiagnosticsHighScoreView extends HighScoreView {
	
	public MathDiagnosticsHighScoreView(ArrayList<? extends Player> playerList,
			String[] colorList, int width) {
		super(playerList, colorList);
		this.width = width + "px";
	}
	
	@Override
	protected void initilizeList(){
		Collections.sort(playerList);

		Iterator<? extends Player> i = playerList.iterator();

		int n=0;

		while (i.hasNext()) {

			MathDiagnosticsPlayer current=(MathDiagnosticsPlayer) i.next();
			n++;
			String pointsString = current.getPoints() == 1?"Punkt":"Punkte";
			highScoreList.add(new HTML("<div style='color:" + colorList[current.getColorId()]  +"'class='highscore-entry'><span class='highscore_pos'>"+n+". </span><span class='highscore_name'>"+current.getName()+" </span><span class='highscore_points'>"+current.getPoints()+ " " + pointsString + " (" + current.getMeanRT() + " ms)</span></div>"));

		}
	}
}
