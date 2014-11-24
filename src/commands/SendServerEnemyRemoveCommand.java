package commands;

import server.GameServer;
import GameController.Enemy;

public class SendServerEnemyRemoveCommand extends Command<GameServer>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5421561357860369854L;
	private Enemy enemy;

	public SendServerEnemyRemoveCommand(Enemy e){
		this.enemy = e;
	}
	
	@Override
	public void execute(GameServer executeOn) {
		//executeOn.removeEnemy(this.enemy);
		//This method should not be used unless we want to let the player click and
		//kill enemies, currently not implemented -PH
		
	}

}
