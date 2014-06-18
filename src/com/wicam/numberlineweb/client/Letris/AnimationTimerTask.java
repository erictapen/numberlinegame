package com.wicam.numberlineweb.client.Letris;

import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * A Task for the AnimationTimer with possible starting delay
 * and a delay option for continuous running.  
 * @author timfissler
 *
 */
public class AnimationTimerTask {


	private boolean deletionMark = false;
	private int delay = 0;
	private int delayForContinuousRunning = 0;
	private boolean firstRun = true;
	protected int taskX;
	protected int taskY;

	public AnimationTimerTask(){};
	
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
	 * Returns true, if the task is in its first run iteration.
	 * @return
	 */
	public boolean isFirstRun() {
		return this.firstRun;
	}
	
	/**
	 * Set the delay time for continuous running this task in ms.
	 * @param delay time
	 */
	public void setDelayForContinuousRunning(int delay) {
		this.delayForContinuousRunning = delay;
	}
	
	/**
	 * Get the delay time for continuous running this task in ms.
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
	 * mark this task for deletion.
	 */

	public void markForDelete() {

		this.deletionMark = true;
		
	}

	/**
	 * unmark this task for deletion
	 */
	public void unmarkForDelete() {

		this.deletionMark = false;

	}

	public boolean isMarkedForDeletion() {
		return deletionMark;
	}

}
