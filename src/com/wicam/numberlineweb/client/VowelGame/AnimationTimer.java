package com.wicam.numberlineweb.client.VowelGame;

import java.util.ArrayList;
import java.util.Iterator;


import com.google.gwt.user.client.Timer;

/**
 * A _single_ timer user for animation purposes. The timer is stopped when no animations
 * are left to animate.
 * The timer is started when an animation is added and the timer isnt running yet.
 * @author patrick
 *
 */

public class AnimationTimer extends Timer {

	private ArrayList<AnimationTimerTask> tasks = new ArrayList<AnimationTimerTask>();
	private boolean isRunning = false;

	
	//the global animation time is 30ms. Every animation has to set its
	//animation according to this number. Performance is improved massivly this way,
	//since concurring timers arent possible anymore.
	public static final int TIMER_SPEED = 40;


	/**
	 * run every task in our task lists, remove tasks marked for deletion
	 */
	public void run() {

		Iterator<AnimationTimerTask> it = tasks.iterator();

		while (it.hasNext()) {

			AnimationTimerTask t = it.next();
			if (t.isMarkedForDeletion()) {
				it.remove();
			}else{
				
				//consider the optional delay set in the task
				if (t.getDelay()<=0) {
					t.run();
				}else{
					t.setDelay(t.getDelay() - TIMER_SPEED);
				}
			
			}
		}
		
		if (tasks.size() == 0) this.cancel();

	}
	
	/**
	 * Returns the number of tasks
	 * 
	 * @return number of tasks
	 */
	public int getNumberOfTasks(){
		return this.tasks.size();
	}

	/**
	 * Registers a new AnimationTask. The timer is started if it is not currently
	 * running.
	 * @param t
	 */
	public void registerTask(AnimationTimerTask t) {

		if (tasks.contains(t)) return;
		
		this.tasks.add(t);
		t.unmarkForDelete();
		if (!this.isRunning) this.scheduleRepeating(TIMER_SPEED);

	}



}
