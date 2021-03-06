package client;

import java.io.IOException;
import java.util.HashMap;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyboardActionHandler implements EventHandler<KeyEvent> {
	private World world;
	private Communication comm;
	private Direction facing;
	private boolean moving;
	private HashMap<KeyCode,Boolean> activeKeyPress;
	
	public KeyboardActionHandler(World world) {
		this.world = world;
		this.comm = world.getCommunication();
		this.activeKeyPress = new HashMap<KeyCode,Boolean>();
	}
	@Override
	public void handle(KeyEvent event) {
		try {
			EventType type = event.getEventType();
			KeyCode code = event.getCode();
			if(type == KeyEvent.KEY_PRESSED) {
				switch(code) {
     		case C:
				  new Chatbox(world);
				break;
				case SPACE:
					comm.attack();
					break;
				case CONTROL:
					world.toggleBot();
					Log.println("Toggle bot");
					break;
				case LEFT:
				case RIGHT:
				case UP:
				case DOWN:
					Direction newDirection = Direction.fromKeyCode(code);
					if(facing == newDirection) {
						if(world.getSelf().canMove()) {
							comm.move(facing);
						}
						comm.refresh();
					}
					facing = newDirection;
					comm.face(facing);
					moving = true;
					break;
				default:
					break;
				}
				activeKeyPress.put(code, true);
			} else if(type == KeyEvent.KEY_RELEASED) {
				switch(code) {
				case CONTROL:
					world.toggleBot();
					Log.println("Toggle bot");
					break;
				case LEFT:
				case RIGHT:
				case UP:
				case DOWN:
					activeKeyPress.put(code, false);
					if(Direction.fromKeyCode(code) == facing) {
						Direction activeDirection = getActiveDirection();
						if(activeDirection == null) {
							moving = false;
						}
						else {
							facing = activeDirection;
						}
					}
					break;
				default:
					break;
				}
			} else if(type == KeyEvent.KEY_TYPED) {
				// event.getCharacter(); // On KeyCode.ENTER, handle these characters in a cache of 200 characters max.
				//							On KeyCode.ENTER again, send message and stop reading characters.
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	private Direction getActiveDirection() {
		if(this.activeKeyPress.get(KeyCode.LEFT)) return Direction.LEFT;
		else if(this.activeKeyPress.get(KeyCode.RIGHT)) return Direction.RIGHT;
		else if(this.activeKeyPress.get(KeyCode.UP)) return Direction.UP;
		else if(this.activeKeyPress.get(KeyCode.DOWN)) return Direction.DOWN;
		else return null;
	}

}
