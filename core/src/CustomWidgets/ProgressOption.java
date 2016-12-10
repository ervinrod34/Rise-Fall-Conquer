package CustomWidgets;

/**
 * A class representing a screen that displays the 
 * player's current winning progress, the player's city count and 
 * the other players' city counts. 
 * 
 * @author Ervin Rodriguez
 * @version 1.0
 */

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.MyGdxGame;

import MainMenu.MainMenu;
import factions.Faction;

public class ProgressOption {
	
	private Stage stage;
	private Label screenDisplay;
	private Label playerLabel, playerCount; 
	private Label otherLabel, otherCount;
	private TextButton close, endGame;
	//mainTable is holder
	private Table mainTable, container, holder;
	
	private boolean isOpen;
	private ArrayList<Faction> factions;
	
	/**
	 * Creates a ProgressOption object with the factions as a parameter.
	 * @param f An ArrayList of Factions
	 */
	public ProgressOption(ArrayList<Faction> f) {
		this.stage = null;
		this.factions = f;
		this.isOpen = false;
		
		setContainer(new Table(MyGdxGame.MENUSKIN));
		getContainer().setFillParent(true);
		
		/**
		 * Display a simple Label for the title of this screen
		 */
		this.screenDisplay = new Label("Progress", MyGdxGame.MENUSKIN);
		getContainer().add(this.screenDisplay).center();
		getContainer().row();
		
		/**
		 * Creates a display for the player's city count
		 */
		getContainer().row();
		this.playerLabel = new Label("Player's City Count: ", MyGdxGame.MENUSKIN);
		getContainer().add(this.playerLabel);
		this.playerCount = new Label(Integer.toString(factions.get(0).getCityCount()), MyGdxGame.MENUSKIN);
		getContainer().add(this.playerCount);
		
		/**
		 * Creates a display for the other city count
		 */
		getContainer().row();
		this.otherLabel = new Label("Other's City Count: ", MyGdxGame.MENUSKIN);
		getContainer().add(this.otherLabel);
		int tempCityCount = 0;
		//iterate through the other factions to get total city count
		for(int i = 1; i < factions.size(); i++) {
			tempCityCount += factions.get(i).getCityCount();
		}
		this.otherCount = new Label(Integer.toString(tempCityCount), MyGdxGame.MENUSKIN);
		getContainer().add(this.otherCount);
		
		/**
		 * Create and add the buttons into the mainTable
		 */
		getContainer().row();
		getContainer().row();
		this.endGame = new TextButton("END GAME", MyGdxGame.MENUSKIN);
		this.close = new TextButton("CLOSE", MyGdxGame.MENUSKIN);
		getContainer().add(this.endGame).width(100).padBottom(10);
		getContainer().add(this.close).width(100).padBottom(10);
		
		Label backGround = new Label("", MyGdxGame.MENUSKINHUD);		
		
		setMainTable(new Table(MyGdxGame.MENUSKIN));
		getMainTable().setFillParent(true);
		getMainTable().stack(backGround, getContainer());
		
		/**
		 * Creates a listener for the end game button.
		 */
		this.endGame.addListener(new ClickListener() {
			public void clicked(InputEvent ie, float x, float y) {
				MyGdxGame.GAME.setScreen(new MainMenu());
			}
		});
		
		/**
		 * Creates a listener for the close button.
		 */
		this.close.addListener(new ClickListener() {
			public void clicked(InputEvent ie, float x, float y) {
				getContainer().remove();
				getContainer().invalidateHierarchy();
				setIsOpen(false);
			}
		});
		
	}
	
	/**
	 * Return the stage of this object.
	 * @return A Stage object
	 */
	public Stage getStage() {
		return this.stage;
	}
	
	/**
	 * Change the stage of this object.
	 * @param stage A reference to a stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	/**
	 * Returns the mainTable of this object.
	 * @return A Table object
	 */
	public Table getMainTable() {
		return this.mainTable;
	}
	
	/**
	 * Change the mainTable of this object.
	 * @param pO A reference to a Table
	 */
	public void setMainTable(Table pO) {
		this.mainTable = pO;
	}
	
	/**
	 * Returns the container of this object.
	 * @return A Table object
	 */
	public Table getContainer() {
		return this.container;
	}
	
	/**
	 * Change the container of this object.
	 * @param c A reference to a Table
	 */
	public void setContainer(Table c) {
		this.container = c;
	}
	
	/**
	 * Returns a boolean value whether this object
	 * is open or not.
	 * @return A boolean value
	 */
	public boolean getIsOpen() {
		return this.isOpen;
	}
	
	/**
	 * Change whether this object is open or not.
	 * @param open A reference to a boolean value
	 */
	public void setIsOpen(boolean open) {
		this.isOpen = open;
	}
	
}
