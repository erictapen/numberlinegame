package com.wicam.numberlineweb.client.LetrisPush;

import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * A Task for the AnimationTimer with possible starting delay
 * and a delay option for continuous running.  
 * @author timfissler
 *
 */
public class LetrisPushGameAnimationTimerTask {


	private boolean deletionMark = false;
	private int delay = 0;
	private int delayForContinuousRunning = 0;
	private boolean firstRun = true;
	protected int taskX;
	protected int taskY;

	public LetrisPushGameAnimationTimerTask(){};
	
	public void run() {
		// TODO Auto-generated method stub	
	}	
	
	/**
	 * Set the first run indication.
	 * @param firstRun
	 */
	public void setFirstRun(boolean firstRun) {
		this.firstRun = firstRun;
	}
	
	/**
	 * Returns true, if the taskText is in its first run iteration.
	 * @return
	 */
	public boolean isFirstRun() {
		return this.firstRun;
	}
	
	/**
	 * Set the delay time for continuous running this taskText in ms.
	 * @param delay time
	 */
	public void setDelayForContinuousRunning(int delay) {
		this.delayForContinuousRunning = delay;
	}
	
	/**
	 * Get the delay time for continuous running this taskText in ms.
	 * @return delay time
	 */
	public int getDelayForContinuousRunning() {
		return this.delayForContinuousRunning;
	}

	/**
	 * return the tasks starting delay in ms
	 * @return
	 */
	public int getDelay() {
		return delay;
	}

	/**
	 * sets the tasks starting delay in ms
	 * @param delay
	 */
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	/**
	 * mark this taskText for deletion.
	 */

	public void markForDelete() {

		this.deletionMark = true;
		
	}

	/**
	 * unmark this taskText for deletion
	 */
	public void unmarkForDelete() {

		this.deletionMark = false;

	}

	public boolean isMarkedForDeletion() {
		return deletionMark;
	}

}
