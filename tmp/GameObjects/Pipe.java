package com.kilobolt.GameObjects;

import java.util.Random;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Pipe extends Scrollable {

	private Random r;

	private Rectangle pipeTopUp, pipeTopDown, pipeUp, pipeDown;

	public static final int VERTICAL_GAP = 45;
	public static final int PIPE_TOP_WIDTH = 24;
	public static final int PIPE_TOP_HEIGHT = 11;
	private float groundY;

	private boolean isScored = false;

	// When Pipe's constructor is invoked, invoke the super (Scrollable)
	// constructor
	public Pipe(float x, float y, int width, int height, float scrollSpeed,
			float groundY) {
		super(x, y, width, height, scrollSpeed);
		// Initialize a Random object for Random number generation
		r = new Random();
		pipeTopUp = new Rectangle();
		pipeTopDown = new Rectangle();
		pipeUp = new Rectangle();
		pipeDown = new Rectangle();

		this.groundY = groundY;
	}

	@Override
	public void update(float delta) {
		// Call the update method in the superclass (Scrollable)
		super.update(delta);

		// The set() method allows you to set the top left corner's x, y
		// coordinates,
		// along with the width and height of the rectangle

		pipeUp.set(position.x, position.y, width, height);
		pipeDown.set(position.x, position.y + height + VERTICAL_GAP, width,
				groundY - (position.y + height + VERTICAL_GAP));

		// Our pipeTop width is 24. The pipe is only 22 pixels wide. So the pipeTop
		// must be shifted by 1 pixel to the left (so that the pipeTop is centered
		// with respect to its pipe).

		// This shift is equivalent to: (PIPE_TOP_WIDTH - width) / 2
		pipeTopUp.set(position.x - (PIPE_TOP_WIDTH - width) / 2, position.y + height
				- PIPE_TOP_HEIGHT, PIPE_TOP_WIDTH, PIPE_TOP_HEIGHT);
		pipeTopDown.set(position.x - (PIPE_TOP_WIDTH - width) / 2, pipeDown.y,
				PIPE_TOP_WIDTH, PIPE_TOP_HEIGHT);

	}

	@Override
	public void reset(float newX) {
		// Call the reset method in the superclass (Scrollable)
		super.reset(newX);
		// Change the height to a random number
		height = r.nextInt(90) + 15;
		isScored = false;
	}

	public void onRestart(float x, float scrollSpeed) {
		velocity.x = scrollSpeed;
		reset(x);
	}

	public Rectangle getSkullUp() {
		return pipeTopUp;
	}

	public Rectangle getSkullDown() {
		return pipeTopDown;
	}

	public Rectangle getBarUp() {
		return pipeUp;
	}

	public Rectangle getBarDown() {
		return pipeDown;
	}

	public boolean collides(Pacman pacman) {
		if (position.x < pacman.getX() + pacman.getWidth()) {
			return (Intersector.overlaps(pacman.getBoundingCircle(), pipeUp)
					|| Intersector.overlaps(pacman.getBoundingCircle(), pipeDown)
					|| Intersector.overlaps(pacman.getBoundingCircle(), pipeTopUp) || Intersector
						.overlaps(pacman.getBoundingCircle(), pipeTopDown));
		}
		return false;
	}

	public boolean isScored() {
		return isScored;
	}

	public void setScored(boolean b) {
		isScored = b;
	}
}
