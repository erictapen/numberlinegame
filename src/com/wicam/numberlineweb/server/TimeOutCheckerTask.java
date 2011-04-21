package com.wicam.numberlineweb.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;

public class TimeOutCheckerTask extends TimerTask {


	private ArrayList<TimeOutState> timeOutStates;
	private ArrayList<EmptyGameTimeOutState> emptyGameTimeOutStates;
	private GameCommunicationServiceServlet gameComm;


	public TimeOutCheckerTask(ArrayList<TimeOutState> timeOutStates, ArrayList<EmptyGameTimeOutState> emptyGameTimeOutStates, GameCommunicationServiceServlet gameComm) {


		this.timeOutStates = timeOutStates;
		this.emptyGameTimeOutStates = emptyGameTimeOutStates;
		this.gameComm = gameComm;


	}


	@Override
	public void run() {

		checkPlayerTimeOut();
		checkEmptyGameTimeout();


	}


	private void checkPlayerTimeOut() {
		Iterator<TimeOutState> i = timeOutStates.iterator();



		while (gameComm.timeOutListLocked()) {


		}

		gameComm.timeOutListLock();

		while (i.hasNext()) {

			TimeOutState current = i.next();

			if (!current.countDown()) {

				timeOutStates.remove(current);
				gameComm.timeOutListUnLock();
				System.out.println("player " + current.getPlayerId() + " timed out.");
				gameComm.leavePlayer(current.getPlayerId(), current.getGameId());

				break;
			}

		}


		gameComm.timeOutListUnLock();
	}


	private void checkEmptyGameTimeout() {

		Iterator<EmptyGameTimeOutState> i = emptyGameTimeOutStates.iterator();



		while (i.hasNext()) {

			EmptyGameTimeOutState current = i.next();

			if (gameComm.getGameById(current.getGameId()).getPlayerCount() == 0 && !current.countDown()) {

				emptyGameTimeOutStates.remove(current);
				System.out.println("Empty game #" + current.getGameId() + " timed out.");
				
				gameComm.removeGame(current.getGameId());
				break;
			}

		}


		gameComm.timeOutListUnLock();


	}



}
