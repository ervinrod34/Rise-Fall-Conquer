package map;
/**
 * Used to create a day night cycle for a map
 * @author Porter
 *
 */
public class DayNightCycle {

	private int time = 10;
	private float value = 0;
	private map.Map mBoard;
	
	public DayNightCycle(map.Map mBoard) {
		this.mBoard = mBoard;
	}
	public int getTime(){
		return time;
	}
	/**
	 * Updates the day night cycle to the next tick
	 */
	public void update(){
		int timeTotal = 240;
		int maxRGB = 255;
		if(time == timeTotal){
			time = 10;
			value = 0;
		}
		// If past halfway start making it darker
		if(time >= timeTotal/2){
			value -= maxRGB/(timeTotal / 2);
		}else{
			value += maxRGB/(timeTotal / 2);
		}
		// Update overall color of map
		mBoard.getrayHandler().setAmbientLight((1/(float)maxRGB)*value, (1/(float)maxRGB)*value, (1/(float)maxRGB)*value, 1f);
		time++;
	}
}
