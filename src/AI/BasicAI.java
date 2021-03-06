package AI;

import java.util.ArrayList;

/**
 * This class is the basic AI class. it holds a collection of information about
 * the entity it is attached to, methods to update this data, and methods to
 * update its owner based on this data More specific AI's can extend this class
 * to override specific methods or to provide more information for a specific
 * entity
 * 
 * @author Luke M
 */
public class BasicAI {

	/**
	 * the entity that this AI is attached to
	 */
	public ConcreteObject.Entity owner = null;
	/**
	 * does the AI check for being in danger?
	 */
	public boolean checkDanger = true;
	/**
	 * not actually used right now, replaced by the hostility matrix in display
	 */
	public boolean hostile;
	/**
	 * whether or not the entity is in danger
	 */
	public boolean inDanger = false;
	/**
	 * the array of where the danger is
	 */
	public int[][] dangerSource = new int[800][800];
	/**
	 * whether or not the AI is being overridden by something else, such as the
	 * mapAI
	 */
	public boolean override = false;
	/**
	 * if this is being overridden, this provides a better way to interface
	 */
	public AI.MapAI overlord = null;
	/**
	 * whether or not the player is in range of this entity's sight
	 */
	public boolean targetInRange = true;
	/**
	 * the location of safe places
	 */
	public ArrayList<Float> xLocSafe = new ArrayList<Float>();
	public ArrayList<Float> yLocSafe = new ArrayList<Float>();
	// public float xLocSafe, yLocSafe;
	/**
	 * firing cooldown
	 */
	public int cooldown = 0;
	/**
	 * how far this AI looks for danger
	 */
	public int safetySearchRadius = 20;
	/**
	 * the destinations for the pathfinding
	 */
	float xDest, yDest;

	/**
	 * Null constructor
	 */
	public BasicAI() {
	}

	public void init() {
		System.out.println("Initializing BasicAI");
		xLocSafe.add(0f);
		yLocSafe.add(0f);
	}

	/**
	 * calls all AI methods
	 */
	public void update() {
		// this.owner.D.map.mapAI.update();
		if (this.owner.killed == false) {
			if (checkDanger) {
				isInDanger();
			}
			if (inDanger) {
				findSafePlaces();
			}
			// checkPlayerLoc();
			if (!this.owner.killed && !override) {
				updateOwner();
			}
			if (cooldown > 0) {
				cooldown--;
			}
			resetStuff();
		}

	}

	/**
	 * this resets values
	 */
	public void resetStuff() {
		inDanger = false;
		dangerSource = new int[800][800];
		// TODO reset safeplace if safeplace is no longer safe or if owner is
		// pretty close to safeplace
		if (Math.abs(xDest - this.owner.xLoc) < 1) {
			this.xLocSafe = new ArrayList<Float>();
			float add = 0;
			xLocSafe.add(add);
		}
		if (Math.abs(yDest - this.owner.yLoc) < 1) {
			this.yLocSafe = new ArrayList<Float>();
			float add = 0;
			yLocSafe.add(add);
		}
	}

	/**
	 * this method will check this.owner.map.mapAI.colorLevels to see if there
	 * is a concentration of similar colored plasma that will lead to
	 * dissolvement within 6 cells Whoo, it works!
	 */
	public void isInDanger() {

		for (int i = (int) (this.owner.xLoc - safetySearchRadius); i < (int) (this.owner.xLoc + safetySearchRadius); i++) {
			for (int j = (int) (this.owner.yLoc - safetySearchRadius); j < (int) (this.owner.yLoc + safetySearchRadius); j++) {
				// System.out.println(this.owner.xLoc+" "+this.owner.yLoc);
				if (i <= 800 && i >= 0 && j <= 800 && j >= 0) {
					// System.out.println(owner.colorDifference(this.owner.D.field.interpolatedDensity(i,j),
					// this.owner.red*256*256+this.owner.green*256+this.owner.blue));
					if (owner.colorDifference(
							this.owner.D.field.interpolatedDensity(i, j),
							this.owner.red * 256 * 256 + this.owner.green * 256
									+ this.owner.blue) < this.owner.resistance) {
						this.inDanger = true;
						// System.out.println(this.owner.name + "is in danger");
						// System.out.println(i+" "+j);
						dangerSource[i][j] = owner.colorDifference(
								this.owner.D.map.mapAI.colorLevels[i][j],
								this.owner.red * 256 * 256 + this.owner.green
										* 256 + this.owner.blue);
					}
				}
			}
		}
	}

	/**
	 * This will check against dangerSource and find a safe place to go
	 */
	public void findSafePlaces() {
		// System.out.println(this.owner.name + "Searching for safety");
		for (int i = (int) (this.owner.xLoc - safetySearchRadius); i < (int) (this.owner.xLoc + safetySearchRadius); i++) {
			for (int j = (int) (this.owner.yLoc - safetySearchRadius); j < (int) (this.owner.yLoc + safetySearchRadius); j++) {
				if (i >= 0 && j >= 0) {
					if ((owner.colorDifference(dangerSource[i][j],
							this.owner.resistance) > this.owner.resistance)) {
						this.xLocSafe.add((float) i);
					}
				}
				this.yLocSafe.add((float) j);
				// System.out.println(i+", "+j); Oh god, so much printing
			}
		}

	}

	/**
	 * this method will move(pathfind?) towards the nearest safe spot To find
	 * this, it will probably need recursion to check every step of the path. I
	 * hate recursion. maybe I can do something iteratively. Lets try this
	 */
	public void moveToSafety() {
		// System.out.println(this.owner.name+" is moving to safety");
		int i = (int) (Math.random() * xLocSafe.size());
		int j = (int) (Math.random() * yLocSafe.size());
		if (xDest == 0) {
			xDest = xLocSafe.get(i);
		}
		if (yDest == 0) {
			yDest = yLocSafe.get(j);
		}
		float[] ownerPosition = { this.owner.xLoc, this.owner.yLoc };
		float[] destPostion = { xDest, yDest };
		float[] movementVectors = calculateVectors(destPostion, ownerPosition,
				10);
		// this.owner.xAccel = movementVectors[0];
		// this.owner.yAccel = movementVectors[1];
		this.owner.Velocity(movementVectors[0], movementVectors[1]);
		// this.owner.yVel = movementVectors[1]*10;

	}

	/**
	 * this method will just use the distance formula to check the distance
	 * between the player and the entity against the entity's sight value
	 */
	public void checkTargetLoc(ConcreteObject.Entity target) {
		float distance = (float) Math.sqrt(Math.pow(this.owner.xLoc
				- target.xLoc, 2)
				+ Math.pow(this.owner.yLoc - target.yLoc, 2));
		targetInRange = (distance <= this.owner.sight);
	}

	/**
	 * updates the owner based on AI stuff, this will be a macro method
	 */
	public void updateOwner() {
		doShootyStuff();
		if (inDanger && checkDanger) {
			moveToSafety();
		}
		if (!inDanger) {
			normalMovement();// possibly rewrite this. actually, no, if I want
								// to override this, I can set override to be
								// true
		}

	}

	/**
	 * does shooty stuff
	 */
	public void doShootyStuff() {
		for (int i = 0; i < this.owner.D.map.entities.size(); i++) {
			checkTargetLoc(this.owner.D.map.entities.get(i));
			if (targetInRange
					&& cooldown == 0
					&& checkHostile(this.owner,
							this.owner.D.map.entities.get(i))) {
				shootAtTarget(this.owner.D.map.entities.get(i));
			}
		}
		checkTargetLoc(this.owner.D.you);
		if (targetInRange && cooldown == 0 && hostile) {
			shootAtTarget(this.owner.D.you);
		}
	}

	/**
	 * this method checks whether two entities are hostile to each other
	 */
	public boolean checkHostile(ConcreteObject.Entity entity1,
			ConcreteObject.Entity entity2) {
		return this.owner.D.hostility[entity1.teamTag][entity2.teamTag];

	}

	/**
	 * this method will predict where the player will be if they continue on
	 * their current vector and then calculate the xVelocity and yVelocity that
	 * will hit that location in the time it calculates, it then will fire the
	 * bullet. This may change to be a constant speed bullet and it calculates
	 * what direction to fire it
	 */
	public void shootAtTarget(ConcreteObject.Entity target) {
		float[] position = predictPosition(target);
		float[] ownerPosition = { this.owner.xLoc, this.owner.yLoc };
		float[] vectors = calculateVectors(position, ownerPosition, 50);
		this.owner.fireBullet(vectors[0], vectors[1]);

		cooldown = 2000;
	}

	/**
	 * this method calculates where the target will be in... 50
	 * cycles(arbitrary) then returns an array of floats that is the player's
	 * predicted location. BETTER TARGET METHOD USED NOW.(or... will be, not
	 * used yet...I am a bit lazy)
	 * 
	 * @return {xLoc,yLoc}
	 */
	public float[] predictPosition(ConcreteObject.Entity target) {
		float[] position = { 0, 0 };
		if (target.xVel != 0 || target.yVel != 0) {
			position[0] = target.xLoc + (target.xVel * 50);
			position[1] = target.yLoc + (target.yVel * 50);
		} else {
			position[0] = target.xLoc;
			position[1] = target.yLoc;
		}
		return position;
	}

	/**
	 * this method calculates the vectors needed to hit the location found in
	 * predictPosition() after 50 cycles(or exactly that, actually) return
	 * {xVel,yVel} BETTER TARGET METHOD USED NOW (not actually true) (This was a
	 * year ago and I didn't write the idea down)
	 */
	public float[] calculateVectors(float[] position1, float[] position2,
			int cycles) {
		float[] vectors = { 0, 0 };
		vectors[0] = ((position1[0] - position2[0]) / cycles);
		vectors[1] = ((position1[1] - position2[1]) / cycles);

		return vectors;
	}

	/**
	 * this is what the entity does normally, ie. when it is not in danger
	 */
	public void normalMovement() {
		if (this.owner.yLoc < 20) {
			this.owner.yAccel += .05;
		}
		if (this.owner.yLoc > 500) {
			this.owner.yAccel -= .05;
		}
	}
}
