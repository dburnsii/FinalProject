package client;

import java.awt.Point;
<<<<<<< HEAD
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.EOFException;
=======
>>>>>>> 439ea0fb3ac227faea8edda5cdaf59334b6960c6
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import model.Level0Map;
import server.GameServer;
import GUI.MainMenu;
import GameController.Enemy;
import GameController.Tower;

import commands.Command;
import commands.DisconnectCommand;
import commands.SendServerEnemyRemoveCommand;
import commands.SendServerTowerCommand;
import commands.SendServerTowerRemoveCommand;

public class GameClient{
	private String clientName = "tester"; // user name of the client
	
	// Hardcoded values for testing
	private String host = "localhost";
	private String port = "9001";
	
	private Socket server; // connection to server
	private ObjectOutputStream out; // output stream
	private ObjectInputStream in; // input stream
	
	private Level0Map level;
	private MainMenu mainMenu;
	private Player player;
	
	public void newMessage(String message) {
		// TODO Display a new message to our text/chat panel. May need to add a local list of messages	
		System.out.println(message);
	}

	/**
	 * This class reads and executes commands sent from the server
	 * 
	 * @author Gabriel Kishi
	 *
	 */
	private class ServerHandler implements Runnable{
		public void run() {
			try{
				while(true){
					// read a command from server and execute it
					Command<GameClient> c = (Command<GameClient>)in.readObject();
					c.execute(GameClient.this);
				}
			}
//			catch(SocketException e){
//				return; // "gracefully" terminate after disconnect
//			}
//			catch (EOFException e) {
//				return; // "gracefully" terminate
//			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public GameClient(){
		// ask the user for a host, port, and user name
		// String host = JOptionPane.showInputDialog("Host address:");
		// String port = JOptionPane.showInputDialog("Host port:");
		// clientName = JOptionPane.showInputDialog("User name:");
		
		
		if (host == null || port == null || clientName == null)
			return;
		
		try{
			// Open a connection to the server
			server = new Socket(host, Integer.parseInt(port));
			out = new ObjectOutputStream(server.getOutputStream());
			in = new ObjectInputStream(server.getInputStream());
			
			// write out the name of this client
			out.writeObject(clientName);
			
			// add a listener that sends a disconnect command to when closing
//			this.addWindowListener(new WindowAdapter(){
//				public void windowClosing(WindowEvent arg0) {
//					try {
//						out.writeObject(null);// TODO send a disconnect command to the server to safely disconnect
//						out.close();
//						in.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			});
			
			level = (Level0Map) in.readObject();
			System.out.println("Level Received");
			player = (Player) in.readObject();
			System.out.println("Player Received");
			
			// start a thread for handling server events
			new Thread(new ServerHandler()).start();
		
			
		}catch(Exception e){
			e.printStackTrace();
		}

		mainMenu = new MainMenu(this);
		mainMenu.setPlayer(player);
		
	}
	
	public GameClient(String string) {
		// ask the user for a host, port, and user name
		// String host = JOptionPane.showInputDialog("Host address:");
		// String port = JOptionPane.showInputDialog("Host port:");
		// clientName = JOptionPane.showInputDialog("User name:");
		this.clientName = string;
		
		if (host == null || port == null || clientName == null)
			return;
		
		try{
			// Open a connection to the server
			server = new Socket(host, Integer.parseInt(port));
			out = new ObjectOutputStream(server.getOutputStream());
			in = new ObjectInputStream(server.getInputStream());
			
			// write out the name of this client
			out.writeObject(clientName);
			
			// add a listener that sends a disconnect command to when closing
//			this.addWindowListener(new WindowAdapter(){
//				public void windowClosing(WindowEvent arg0) {
//					try {
//						out.writeObject(null);// TODO send a disconnect command to the server to safely disconnect
//						out.close();
//						in.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			});
			
			// TODO set up the GUI
			
			// start a thread for handling server events
			new Thread(new ServerHandler()).start();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void sendCommand(Command<GameServer> command){
		try {
			out.writeObject(command);
		} catch (IOException e) {
			System.out.println("sendCommand FAILED");
		}
	}

	public static void main(String[] args){
		new GameClient();
	}

	/**
	 * Updates the ChatPanel with the updated message log
	 * 
	 * @param messages	the log of messages to display
	 */
	public void update(List<String> messages) {
		// TODO update the gui when called
		// GUI.update(messages);
	}

	/**
	 * Receives a tower and enemy list and sends it on to the model.
	 * @param enemyList
	 * @param towerList
	 */
	public void update(List<Enemy> enemyList, List<Tower> towerList){
		mainMenu.getView().update(towerList, enemyList);
	}
	
	public void addTower(Tower t, Point loc){
		SendServerTowerCommand c = new SendServerTowerCommand(t, loc);
		this.sendCommand(c);
	}
	
	public void removeTower(Tower t){
		SendServerTowerRemoveCommand c = new SendServerTowerRemoveCommand(t);
		this.sendCommand(c);
	}
	
	public void removeEnemy(Enemy e){
		SendServerEnemyRemoveCommand c = new SendServerEnemyRemoveCommand(e);
		this.sendCommand(c);
	}
	
	public int disconnect(){
		try{
			out.writeObject(new DisconnectCommand(player.getName()));
			return 0;
		}catch(Exception e){
			e.printStackTrace();
			return 1;
		}
	}
	
	/**
	 * To be called by the TimeCommand objects every time they are executed (every ~20 ms)
	 */
	public void tick(){
		System.out.println("Tick Received!");
	}
}
