package CustomWidgets;

/**
 * Defines a class that displays the cost of 
 * creating or upgrading a resource or unit.
 *  
 * @author Ervin Rodriguez
 * @version 1.0
 */

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.MyGdxGame;

import map.ResourceID;
import map.Tile;
import factions.PlayerFaction;


public class CostOption {

	private Stage stage;
	private Label resourceName, description;
	private TextButton approve, cancel;
	private Table mainTable, container;
	
	private Tile tile;
	private PlayerFaction pf;
	private String type;
	private boolean upgradable;
	private ResourceID tempResID;
	
	private boolean isOpen;
	
	public CostOption(Tile t, PlayerFaction pf, boolean upg, String type) {
		this.stage = null;
		this.tile = t;
		this.pf = pf;
		this.upgradable = upg;
		this.type = type;
		this.isOpen = false;
		
		setContainer(new Table(MyGdxGame.MENUSKIN));
		getContainer().setFillParent(true);
		
		Label backGround = new Label("", MyGdxGame.MENUSKIN);
		
		/**
		 * Setup the table for the tile name display.
		 */
		try {
			this.resourceName = new Label("Cost to Upgrade " + tile.getResourceID().name() 
					+ ": ", MyGdxGame.MENUSKIN);
			getContainer().add(this.resourceName);
			getContainer().row();
		} catch(NullPointerException e) {
			this.resourceName = new Label(" ", MyGdxGame.MENUSKIN);
			getContainer().add(this.resourceName);
			getContainer().row();
		}
		
		/**
		 * Setup the table for the cost display
		 */
		CharSequence tempCostDisplay = "Cost:" + 
									   "\nFood: " + this.pf.getResFoodCost() + 
									   "\nWood: " + this.pf.getResWoodCost() + 
									   "\nGold: " + this.pf.getResGoldCost();
		this.description = new Label(tempCostDisplay, MyGdxGame.MENUSKIN);
		getContainer().add(this.description);
		
		getContainer().row();
		
		/**
		 * Create TextButtons
		 */
		this.approve = new TextButton("APPROVE", MyGdxGame.MENUSKIN);
		this.cancel = new TextButton("CANCEL", MyGdxGame.MENUSKIN);
		
		//Checks if tile is upgrade; if true, display approve button, else display message
		if(this.upgradable == true) {
			getContainer().add(this.approve);
		}
		else {
			Label cantUpg = new Label("Not enough resources.", MyGdxGame.MENUSKIN);
			getContainer().add(cantUpg);
			getContainer().row();
		}
		getContainer().add(this.cancel);
		
		setMainTable(new Table(MyGdxGame.MENUSKIN));
		getMainTable().setFillParent(true);
		getMainTable().stack(backGround, container);

		
		/**
		 * Creates a listener for the APPROVE button.
		 */
		this.approve.addListener(new ClickListener() {
			public void clicked(InputEvent ie, float x, float y) {
				if(getType() == "UPG") {
					getTile().getResource().upgradeTile();
					getPF().updateResourcesPerTurn();
					getPF().applyUpgradeCost();
				} else if(getType() == "NEW") {
					getTile().setResourceID(getResID());
					getPF().claimTile(getTile());
					getPF().applyUpgradeCost();
				}
				
				//Close the window
				getContainer().remove();
				getContainer().invalidateHierarchy();
				setIsOpen(false);
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
	 * Returns the tile of this object.
	 * @return A Tile object
	 */
	public Tile getTile() {
		return this.tile;
	}
	
	/**
	 * Returns the pf of this object.
	 * @return A PlayerFaction object
	 */
	public PlayerFaction getPF() {
		return this.pf;
	}
	
	/**
	 * Returns a String representing the type of the uograde. 
	 * @return A String value
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Change the ResourceID for this object.
	 * @param r A reference to a ResourceID
	 */
	public void setResID(ResourceID r) {
		this.tempResID = r;
	}
	
	/**
	 * Returns the ResourceID in this class.
	 * @return A ResourceID
	 */
	public ResourceID getResID() {
		return this.tempResID;
	}
	
	/**
	 * Returns the stage of this object. 
	 * @return A Stage object
	 */
	public Stage getStage() {
		return this.stage;
	}
	
	/**
	 * Sets the stage for this class.
	 * @param stage A reference to a Stage object
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
	 * @param table A reference to a Table
	 */
	private void setMainTable(Table table) {
		this.mainTable = table;
	}
	/**
	 * Returns the container.
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
