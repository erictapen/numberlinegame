package com.wicam.numberlineweb.client.DoppelungGame;


/**
 * A Task for the AnimationTimer with possible starting delay
 * @author patrick
 *
 */
public class AnimationTimerTask {


	private boolean deletionMark = false;
	private int delay = 0;

	public void run() {
		// TODO Auto-generated method stub

	}	

	/**
	 * return the tasks starting delay in ms
	 * @return
	 */
	public int getDelay() {
		return delay;
	}

	/**
	 * sets the tasls starting delay in ms
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
