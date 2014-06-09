package com.Lpoo.game;

/**
 * Class used to save the scores
 *
 */
public class Score {
	public int points;
	public int time;

	/**
	 * Empty constructor used to read and load scores
	 */
	public Score() {
		points = 0;
		time = 0;
	}

	/**
	 * Creates a score
	 * @param score
	 * @param time
	 */
	public Score(int score, int time) {
		this.points = score;
		this.time = time;
	}

}
