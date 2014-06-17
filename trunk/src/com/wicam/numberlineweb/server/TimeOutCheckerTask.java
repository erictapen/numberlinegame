package com.wicam.numberlineweb.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

public class TimeOutCheckerTask extends TimerTask {

	private List<TimeOutState> timeOutStates;
	private List<EmptyGameTimeOutState> emptyGameTimeOutStates;
	private GameCommunicationServiceServlet gameComm;

	public TimeOutCheckerTask(List<TimeOutState> timeOutStates,
			List<EmptyGameTimeOutState> emptyGameTimeOutStates,
			GameCommunicationServiceServlet gameComm) {

		this.timeOutStates = timeOutStates;
		this.emptyGameTimeOutStates = emptyGameTimeOutStates;
		this.gameComm = gameComm;

	}

	@Override
	public synchronized void run() {

		checkPlayerTimeOut();
		checkEmptyGameTimeout();

	}

	private synchronized void checkPlayerTimeOut() {
		Iterator<TimeOutState> i = timeOutStates.iterator();

		while (gameComm.timeOutListLocked()) {

		}

		gameComm.timeOutListLock();

		while (i.hasNext()) {

			TimeOutState current = i.next();

			if (!current.countDown()
					&& !gameComm.getGameById(current.getGameId())
							.hasPlayerLeft(current.getPlayerId())) {

				timeOutStates.remove(current);
				gameComm.timeOutListUnLock();
				System.out.println("player " + current.getPlayerId()
						+ " timed out.");
				gameComm.leavePlayer(current.getUid(), current.getPlayerId(),
						current.getGameId());

				break;
			}

		}

		gameComm.timeOutListUnLock();
	}

	private synchronized void checkEmptyGameTimeout() {

		Iterator<EmptyGameTimeOutState> i = emptyGameTimeOutStates.iterator();

		while (i.hasNext()) {

			EmptyGameTimeOutState current = i.next();

			if (gameComm.getGameById(current.getGameId()) != null
					&& gameComm.getGameById(current.getGameId())
							.getPlayerCount() == 0 && !current.countDown()) {

				emptyGameTimeOutStates.remove(current);
				System.out.println("Empty game #" + current.getGameId()
						+ " timed out.");

				gameComm.removeGame(current.getGameId());
				break;
			}

		}

		gameComm.timeOutListUnLock();

	}

}
