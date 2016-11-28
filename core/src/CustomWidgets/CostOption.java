package CustomWidgets;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Defines a class that displays the cost of 
 * creating or upgrading a resource or unit.
 *  
 * @author Ervin Rodriguez
 * @version 1.0
 */

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.MyGdxGame;

import map.Tile;
import factions.PlayerFaction;


public class CostOption {

	private Stage stage;
	private Label resourceName, description;
	private TextButton approve, cancel;
	private Table container, holder;
	private Tile tile;
	private PlayerFaction pf;
	private boolean isOpen;
	
	public CostOption(Tile tile, PlayerFaction pf) {
		this.stage = null;
		this.tile = tile;
		this.pf = pf;
		
		this.isOpen = false;
		
		this.container = new Table(MyGdxGame.MENUSKIN);
		//this.displayTable = new Table(MyGdxGame.MENUSKIN);
		//this.displayTable.setFillParent(true);
		//this.buttonTable = new Table(MyGdxGame.MENUSKIN);
		//this.buttonTable.setFillParent(true);
		this.holder = new Table(MyGdxGame.MENUSKIN);
		this.holder.setFillParent(true);
		
		//Label backGround = new Label("", MyGdxGame.MENUSKIN);
		
		/**
		 * Setup the table for the tile name display.
		 */
		try {
			resourceName = new Label(tile.getResourceID().name() + " ", MyGdxGame.MENUSKIN);
			this.container.add(resourceName);
			this.container.row();
		} catch(NullPointerException e) {
			resourceName = new Label(" ", MyGdxGame.MENUSKIN);
			this.container.add(resourceName);
			this.container.row();
		}
		
		/**
		 * Setup the table for the cost display
		 */
		CharSequence tempCostDisplay = "Cost:" + 
									   "\nFood: " + this.pf.getResFoodCost() + 
									   "\nWood: " + this.pf.getResWoodCost() + 
									   "\nGold: " + this.pf.getResGoldCost();
		this.description = new Label(tempCostDisplay, MyGdxGame.MENUSKIN);
		
		/**
		 * Place the displays into a table for displays
		 */
		//this.displayTable.row();
		//this.displayTable.add(this.description);
		
		/**
		 * Create TextButtons
		 */
		this.approve = new TextButton("APPROVE", MyGdxGame.MENUSKIN);
		this.cancel = new TextButton("CANCEL", MyGdxGame.MENUSKIN);
		
		/**
		 * Place the APPROVE and CANCEL textbutton in a table
		 */
		//this.buttonTable.add(this.approve);
		//this.buttonTable.add(this.cancel);
		//this.buttonTable.row();
		
		/**
		 * Adds the sub-tables into the table
		 */
		this.container.row();
		this.container.add(this.description);
		this.container.row();
		this.container.add(this.description);
		this.container.row();
		this.container.add(this.approve);
		this.container.add(this.cancel);
		
		this.holder.add(this.container);
		
		/**
		 * Creates a listener for the APPROVE button.
		 */
		this.approve.addListener(new ClickListener() {
			public void clicked(InputEvent ie, float x, float y) {
				
			}
		});
		
		/**
		 * Creates a listener for the CANCEL button.
		 */
		this.cancel.addListener(new ClickListener() {
			public void clicked(InputEvent ie, float x, float y) {
				getContainer().remove();
				getContainer().invalidateHierarchy();
				setIsOpen(false);
			}
		});
	}

	/**
	 * Return the main table.
	 * @return A Table object
	 */
	public Table getContainer() {
		return this.container;
	}
	
	/**
	 * Sets the stage for this class.
	 * @param stage A reference to a Stage object
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	/**
	 * Returns a boolean value whether this window is open.
	 * @return A boolean value
	 */
	public boolean getIsOpen() {
		return isOpen;
	}

	/**
	 * Change the boolean 
	 * @param change The new boolean value
	 */
	public void setIsOpen(boolean change) {
		this.isOpen = change;
	}
}
