package com.wicam.numberlineweb.client.WordStem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Word implements IsSerializable{
	
	private static final long serialVersionUID = 842910888960886182L;
	
	private String word;
	private int initialLeft;
	private int initialTop;
	private int actualLeft;
	private int actualTop;
	private boolean isTaken;
	private boolean isSelected;
	
	
	public Word(){}
	
	public Word(String word) {
		this.word = word;
		this.setTaken(false);
		this.setSelected(false);
	}
	
	
	@Override
	public boolean equals(Object o) {
		if (o.getClass().getName().equals(this.getClass().getName())) {
			return ((Word) o).getWord().equals(this.getWord());
		}
		return false;
	}
	

	/**
	 * Set the initial position of the word
	 * @param absoluteLeft Pixels from the left
	 * @param absoluteTop Pixels from the top
	 */
	public void setBasicPosition(int absoluteLeft, int absoluteTop) {
		this.initialLeft = absoluteLeft;
		this.initialTop = absoluteTop;
	}

	/**
	 * Set the actual position of the word
	 * @param absoluteLeft Pixels from the left
	 * @param absoluteTop Pixels from the top
	 */
	public void setPosition(int absoluteLeft, int absoluteTop) {
		this.actualLeft = absoluteLeft;
		this.actualTop = absoluteTop;
	}

	/**
	 * @return Returns word
	 */
	public String getWord() {
		return word;
	}

	/**
	 * Set word
	 * @param word Set word to this value
	 */
	public void setWord(String word) {
		this.word = word;
	}

	/**
	 * @return Returns isTaken
	 */
	public boolean isTaken() {
		return isTaken;
	}

	/**
	 * Set isDragged
	 * @param isSelected Set isDragged to this value
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * @return Returns isDragged
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * Set isTaken
	 * @param isTaken Set isTaken to this value
	 */
	public void setTaken(boolean isTaken) {
		this.isTaken = isTaken;
	}

	/**
	 * @return Returns initialLeft
	 */
	public int getInitialLeft() {
		return initialLeft;
	}

	/**
	 * @return Returns initialTop
	 */
	public int getInitialTop() {
		return initialTop;
	}

	/**
	 * @return Returns actualLeft
	 */
	public int getActualLeft() {
		return actualLeft;
	}

	/**
	 * @return Returns actualTop
	 */
	public int getActualTop() {
		return actualTop;
	}
	
	
	
}
