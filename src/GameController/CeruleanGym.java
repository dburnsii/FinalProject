package GameController;

import java.util.ArrayList;

import GUI.GameView.towerType;
import GameController.Enemy;
import GameController.Tower;

/**
 * This is a concrete gym type that extends Gym and Implements serializable
 * @author Max Justice
 * 
 * instance variables
 * final int Cost = 300; 		- these establishes the Gym as a cost of 300
 * 
 * methods
 * Constructor declares a the name of the player and a few other
 * ArrayList<Pokemon> AttackEnemy(ArrayList<Pokemon> enemies)
 * setModifier
 * getModifer
 * boolean increaseFireRate(int amountToIncrease)
 *  boolean checkBuy(int PlayerCurrency)
 *  boolean levelUp()
 */
public class CeruleanGym extends Tower{

	/**
	 * 
	 */
	private static final long serialVersionUID = -264213046314837558L;
	
	// the Cerulean Gym cost 300 but can be changed if desired
	public static final int Cost = 200;
	//String Name, int Attack, int Radius, int FireRateSec, String PlayersName
	/**
	 * The default settings for the cerulean gym a attack power of 25, a radius range of 3 tiles,
	 * 3 shots per a second and the Image URL to the gym
	 * @param PlayersName is the only value the gym 
	 */
	public CeruleanGym(String PlayersName){
<<<<<<< HEAD
		/* String Name, int Attack, int Radius, double FireRateSec, String PlayersName,
					String Image, int cost */
		super("Cerulean Gym", 25, 4, 3.0, PlayersName,"src/images/cuboneStatic.png", Cost);
=======
		super("Cerulean Gym", 25, 4, 2.0, PlayersName,"src/images/cuboneStatic.png", Cost);
>>>>>>> 91a7accd72bab003457a311b80949cfb38513533
		 setTowerType(towerType.NORMAL);
		// TODO Auto-generated constructor stub
	}

	/**
	 * (non-Javadoc)
	 * @see GameController.Tower#AttackEnemy(java.util.ArrayList)
	 * Attack Enemy algorithm is simply.  It is passed the arraylist
	 * of current enemies on the board that are alive.  It then runs a 
	 * for loop for each enemy getting their current point on the board
	 * checks if that enemy is within range with canAttackEnemy() method which
	 * returns true if it can be attacked.  It then uses the incomingAttack()
	 * method from the pokemon object and subtracts from the current pokemons
	 * instance of its health.  
	 * 
	 * TODO add the images and sprites for the attack.  Update the list of
	 * when a pokemon dies to the listeners or observers.  If a pokemon faints
	 * add currency to the player.
	 */
	 
	 //This method will be called by map every tick() as long as tower is not on cooldown from last attack and passed
	 //a list of all enemies currently on the map
	@Override
	public boolean AttackEnemy(ArrayList<Enemy> enemies) {
		
		Enemy myClosestEnemy;
		
		myClosestEnemy = super.findClosestEnemy(enemies);
		
		if (myClosestEnemy == null)
			return false;
		
		if ( canAttackEnemy(myClosestEnemy.getLocation())){
			myClosestEnemy.incomingAttack(super.getAttackPower());
			/*
			 * GUI here
			 */
			getMap().notifyOfAttack(this.getType(), this.getPosition(), myClosestEnemy.getLocation());
				
			
		}
		/*
		//TODO is set up the graphics call for attack
		if(!enemies.isEmpty()){ //just check if enemies has size >=1
		for (Enemy enemy : enemies){
			if (canAttackEnemy(enemy.getLocation())){
				//TODO Attack enemy with graphics commands
				// Figure out how to implement the fireRate in here??
				enemy.incomingAttack(super.getAttackPower());
				//--------------code under here should be moved to enemy/deleted like we said ---------------
				System.out.println("Health" + enemy.getHealth());
				if (enemy.isDead())  		     // if it is dead remove it
					enemies.remove(enemy);	 // remove to pokemon from list (no need to do this)
				System.out.println(enemies.size());
				
				*
				 * After we found an enemy and attacked we then pause the thread 
				 * for for a shot per a second.  The thread counts time in
				 * milliseconds so 1 sec = 1000 milliseconds with 3 shots per a sec
				 * we get a pause of 333 milliseconds for each shot
				 *               1000 milliseconds
				 * Pause time =   ________________
				 * 			(	   units of fire    )
				 * 			(         ---------     )
				 * 					  second
				 *
				try {
				    Thread.sleep(1000/(int)this.getFireRate());                 //1000 milliseconds is one second.
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			}// end if
		} // end for loop
		}// end while loop*/
		return true;
	}

	// This sets modifers when we figure it out
	@Override
	public boolean setModifer() {
		// TODO Auto-generated method stub
		return false;
	}

	// This method is for later time when we figure out modifiers
	@Override
	public boolean getModifer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean increaseFireRate(double amountToIncrease) {
		double newFire = super.getFireRate()+ amountToIncrease;
		if (newFire <= 0)
			newFire = 1;
		super.setFireRate(newFire);
		return true;
	}

	@Override
	public boolean levelUp() {
		this.levelIncrease(); 		// increases the leve by one
		this.setAttackPower(5); 	// increase attack power by 5 poins
		this.modifyAttackRadius(25);// increase attack radius to 25 pixels
		this.increaseFireRate(1); 	// increase the fire rate by one
		return true;
	}

	@Override
	public String printTowerStats() {
		
		String stats = new String ("Name: "+ getGymName() + "\n" +
									"Owner: "+ getGymOwner() +
									"Attack: " + getAttackPower() + "\n"+
									"Rate of Fire: "+ getFireRate() + "\n" +
									"Cost: " + getCost() + "\n"+
									"Modifier: " + getModifer() + "\n"
									);
		return stats;
	}
	
}