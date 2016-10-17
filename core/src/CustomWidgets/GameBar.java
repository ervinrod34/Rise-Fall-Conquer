package CustomWidgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.MyGdxGame;

public class GameBar {

	private Table topBar;
	private WidgetResource itFood, itWood, itGold;
	private Label Turns, Time;
	private TextButton options,endTurn;
	private float padding;
	@SuppressWarnings("rawtypes")
	private Cell cCell, cCell2;
	
	private Table gameHUD;
	
	public GameBar() {
		gameHUD = new Table();
		gameHUD.setFillParent(true);
		
		// Create all widgets
		Label Background = new Label("", MyGdxGame.MENUSKINHUD);
		Turns = new Label("Turn: " + "0", MyGdxGame.MENUSKIN);
		Time = new Label("12:00", MyGdxGame.MENUSKIN);
		options = new TextButton("?",MyGdxGame.MENUSKIN);
		itFood = new WidgetResource("Food");
		itWood = new WidgetResource("Wood");
		itGold = new WidgetResource("Gold");
		endTurn = new TextButton("End Turn",MyGdxGame.MENUSKIN);
		
		float HUDheight = 32;
		padding = 10;
		
		// Add all the widgets
		gameHUD.add(itFood.gettContainer());
		gameHUD.add(itWood.gettContainer());
		gameHUD.add(itGold.gettContainer());
		cCell = gameHUD.add().height(HUDheight);
		gameHUD.add(endTurn);
		cCell2 = gameHUD.add();
		gameHUD.add().width(padding);
		gameHUD.add(Turns).height(HUDheight);
		gameHUD.add().width(padding);
		gameHUD.add(Time).height(HUDheight);
		gameHUD.add().width(padding);
		gameHUD.add(options).height(HUDheight);
		gameHUD.padLeft(padding).padRight(padding);
		
		// Put a background behind the top bar
		topBar = new Table();
		topBar.stack(Background, gameHUD);
		topBar.setFillParent(true);
		topBar.top().left();
		
		// Update the sizes of widgets
		this.update();
	}
	
	/**
	 * Updates the top bar when it needs resizing
	 */
	public void update(){ 
		
		// Calculate the size of the empty cells
		float filler = Gdx.graphics.getWidth() - itFood.getCellSize() - itWood.getCellSize() - itGold.getCellSize() - Turns.getWidth() - Time.getWidth() 
				- options.getWidth() - endTurn.getWidth() - 5*padding;
		cCell.width(filler/2);
		cCell2.width(filler/2);
		
		// Make bar update
		gameHUD.invalidateHierarchy();
	}
	
	/**
	 * Get the parent element
	 * @return
	 */
	public Table getTopBar(){
		return topBar;
	}
	
	/**
	 * Set's the turn value
	 * @param value
	 */
	public void setTurns(int value){
		Turns.setText("Turn: " + String.valueOf(value));
		this.update();
	}
	
	/**
	 * Set's the time on the bar in a 12 hour format
	 * @param value
	 */
	public void setTime12Format(int value){
		if(value < 1 || value > 24){
			Gdx.app.error(this.getClass().getName(), "Inavild time given to clock: " + value);
		}
		if(value > 12){
			value -= 12;
			Time.setText(String.format("%02d", value) + " PM");
		}else{
			Time.setText(String.format("%02d", value) + " AM");
		}
		this.update();
	}
	
	/**
	 * Set's the time on the bar in a 24 hour format
	 * @param value
	 */
	public void setTime24Format(int value){
		Time.setText(String.format("%02d", value));
		this.update();
	}
	
	/**
	 * Sets the value of the food resource
	 * @param value
	 * @param increment
	 */
	public void setFood(int value, int increment){
		itFood.setValue(value);
		itFood.setValueIncrement(increment);
		this.update();
	}
	
	/**
	 * Sets the value of the wood resource
	 * @param value
	 * @param increment
	 */
	public void setWood(int value, int increment){
		itWood.setValue(value);
		itWood.setValueIncrement(increment);
		this.update();
	}
	
	/**
	 * Sets the value of the gold resource
	 * @param value
	 * @param increment
	 */
	public void setGold(int value, int increment){
		itGold.setValue(value);
		itGold.setValueIncrement(increment);
		this.update();
	}
	
	/**
	 * Sets the click listener
	 * @param c
	 */
	public void setOptionsClickListener(ClickListener c){
		options.addListener(c);
	}
	
	/**
	 * Sets the click listener
	 * @param c
	 */
	public void setEndTurnClickListener(ClickListener c){
		endTurn.addListener(c);
	}
	
	/**
	 * Sets the click listener
	 * @param c
	 */
	public void setFoodClickListener(ClickListener c){
		itFood.setResourceImageClickListener(c);
	}
	
	/**
	 * Sets the click listener
	 * @param c
	 */
	public void setWoodClickListener(ClickListener c){
		itWood.setResourceImageClickListener(c);
	}
	
	/**
	 * Sets the click listener
	 * @param c
	 */
	public void setGoldClickListener(ClickListener c){
		itGold.setResourceImageClickListener(c);
	}
}
