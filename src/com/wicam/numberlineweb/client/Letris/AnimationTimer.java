package com.wicam.numberlineweb.client.Letris;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.wicam.numberlineweb.client.Letris.LetrisGameMoveLetterBlockTask;

/**
 * A _single_ timer user for animation purposes. The timer is stopped when no animations
 * are left to animate.
 * The timer is started when an animation is added and the timer isn't running yet.
 * Adding new tasks while iterating over the list now is safe.
 * The timer now can handle tasks with delayed continuous running.
 * @author timfissler
 *
 */

public class AnimationTimer extends Timer {

	/**
	 * The list containing all the tasks that should be performed regularly.
	 */
	private ArrayList<AnimationTimerTask> tasks = new ArrayList<AnimationTimerTask>();
	/**
	 * A temporary list of new tasks that are added to the main list each iteration.
	 */
	private ArrayList<AnimationTimerTask> tasksToAdd = new ArrayList<AnimationTimerTask>();
	/**
	 * Indicates whether the timer is running.
	 */
	private boolean isRunning = false;

	
	//the global animation time is 30ms. Every animation has to set its
	//animation according to this number. Performance is improved massivly this way,
	//since concurring timers aren't possible anymore.
	public static final int TIMER_SPEED = 40;


	/**
	 * run every task in our task lists, remove tasks marked for deletion
	 */
	@Override
	public void run() {

		Iterator<AnimationTimerTask> it = tasks.iterator();
		
		while (it.hasNext()) {

			AnimationTimerTask t = it.next();
			if (t.isMarkedForDeletion()) {
				t.setDelay(0);
				it.remove();
			}else{
				
				//consider the optional delay set in the task
				if (t.getDelay()<=0) {
					t.run();
				}else{
					t.setDelay(t.getDelay() - TIMER_SPEED);
				}
				
				// Handle the continuous running delay.
				if (t.getDelayForContinuousRunning() > 0) {
					if (t.isFirstRun()) {
						t.setFirstRun(false);
						t.setDelay(t.getDelayForContinuousRunning() - TIMER_SPEED);
					}
				}
			
			}
		}
		
		// Add new tasks.
		tasks.addAll(tasksToAdd);
		tasksToAdd.clear();
		
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
		
		if (tasks.contains(t)) {
			return;
		}
		
		// Set the timer speed in a move object.
		// TODO Check if this is being reached.
		if (t.getClass().getName().equals("LetrisGameMoveLetterBlockTask")) {
			LetrisGameMoveLetterBlockTask m = (LetrisGameMoveLetterBlockTask) t;
			m.setTimerSpeed(TIMER_SPEED);
		}
//		this.tasks.add(t);
		this.tasksToAdd.add(t);
		t.setFirstRun(true);
		t.unmarkForDelete();
		if (!this.isRunning) {
			this.scheduleRepeating(TIMER_SPEED);
		}

	}



}
