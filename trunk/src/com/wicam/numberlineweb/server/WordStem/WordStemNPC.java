package com.wicam.numberlineweb.server.WordStem;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.wicam.numberlineweb.client.WordStem.Word;
import com.wicam.numberlineweb.client.WordStem.WordStemGameState;

public class WordStemNPC {

	private WordStemGameCommunicationServiceServlet comm;
	private int gameid;
	private int playerid;
	boolean makeClick = false;
	private double skill = .7; // 70% correct clicks
	
	public WordStemNPC(WordStemGameCommunicationServiceServlet comm, int gameid, int playerid){
		this.comm = comm;
		this.gameid = gameid;
		this.playerid = playerid;
		new CPUBehavior().run();
	}
	
	
	
	/**
	 * Set skill
	 * @param skill Set skill to this value, range 0.0 (very unskilled) to 1.0 (winner-type)
	 */
	public void setSkill(double skill) {
		this.skill = skill;
	}



	/**
	 * @return Returns skill, range 0.0 (very unskilled) to 1.0 (winner-type)
	 */
	public double getSkill() {
		return skill;
	}



	/**
	 * @return Returns the WordStemGameState
	 */
	private WordStemGameState getGameState() {
		return ((WordStemGameState) comm.getGameById(gameid));
	}
	
	
	
	/**
	 * @param correct Specifies, if the answer has to be correct
	 * @return Gives you a specific answer, randomly picked, as wordValue
	 */
	private String getSpecificAnswer(boolean correct) {
		
		ArrayList<Word> words = comm.getSpecificAnswers(getGameState(), correct);
		
		if (words.size() > 0) {			
			int id = new Random().nextInt(words.size());
			for (Word word : words) {
				if (id == 0) {
					return word.getWord();
				}
				id--;
			}
		}
		return "fail";
		
	}
	
	
	private class CPUBehavior extends TimerTask {
		
		@Override
		synchronized public void run() {
			Timer t = new Timer();
			WordStemGameState game = (WordStemGameState) comm.getGameById(gameid);
			if (game != null){
				int state = game.getState();
				int time = 500;
				switch (state){
					case 1:
					case 2:
						time = 1000;
						if (!game.isPlayerReady(playerid))
							comm.updateReadyness(Integer.toString(gameid) + ":" + Integer.toString(playerid),playerid);
					break;
					case 3:
					case 4:
							if (!makeClick){
								time = 5000 + (int)(new Random().nextGaussian()*500);
								if (time < 5000)
									time = 5000;
								makeClick = true;
							}
							else {
								String answer;
								Boolean wasRight;								
								wasRight = (new Random().nextDouble() < skill);
								
								answer = getSpecificAnswer(true);
								
								if (!answer.equals("fail")) {
									
									makeClick = !comm.npcClicked(Integer.toString(game.getId()) + ":" + 
											Integer.toString(playerid) + ":" + answer, wasRight);
								
								}
							}
					break;
					case 5:
						makeClick = false;
					break;
					case 6:
						if (!game.isPlayerReady(playerid))
							comm.updateReadyness(Integer.toString(gameid) + ":" + Integer.toString(playerid),playerid);
					break;
				}
				
				if (state == 7 || state == 99){
					comm.leaveGame(game.getId() + ":" + Integer.toString(playerid),playerid);
				}
				else {
					t.schedule(new CPUBehavior(), time);
				}
			}
		}		
	}
}
