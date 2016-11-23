package CustomWidgets;

import com.badlogic.gdx.scenes.scene2d.InputEvent;

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

	private Label resourceName, tileName, description;
	private TextButton approve, cancel;
	private Table displayTable, buttonTable, mainTable;
	private Tile tile;
	private PlayerFaction pf;
	private boolean isOpen;
	
	public CostOption(Tile tile, PlayerFaction pf) {
		this.tile = tile;
		this.pf = pf;
		
		this.isOpen = false;
		
		this.displayTable = new Table(MyGdxGame.MENUSKIN);
		this.displayTable.setFillParent(true);
		this.buttonTable = new Table(MyGdxGame.MENUSKIN);
		this.buttonTable.setFillParent(true);
		this.mainTable = new Table(MyGdxGame.MENUSKIN);
		this.displayTable.setFillParent(true);
		
		
		/**
		 * Setup the table for the tile name display.
		 */
		try {
			resourceName = new Label(tile.getResourceID().name() + " ", MyGdxGame.MENUSKIN);
			this.displayTable.add(resourceName);
			this.displayTable.row();
		} catch(NullPointerException e) {
			resourceName = new Label(" ", MyGdxGame.MENUSKIN);
			this.displayTable.add(resourceName);
			this.displayTable.row();
		}
		
		try {
			tileName = new Label(tile.getTileId().name() + " ", MyGdxGame.MENUSKIN);
		} catch(NullPointerException e) {}
		
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
		this.displayTable.row();
		this.displayTable.add(this.description);
		
		/**
		 * Create TextButtons
		 */
		this.approve = new TextButton("APPROVE", MyGdxGame.MENUSKIN);
		this.cancel = new TextButton("CANCEL", MyGdxGame.MENUSKIN);
		
		/**
		 * Place the APPROVE and CANCEL textbutton in a table
		 */
		this.buttonTable.add(this.approve);
		this.buttonTable.row();
		this.buttonTable.add(this.cancel);
		this.buttonTable.row();
		
		this.mainTable.add(this.displayTable);
		this.mainTable.add(this.buttonTable);
		
		
		this.approve.addListener(new ClickListener() {
			public void clicked(InputEvent ie, float x, float y) {
				
			}
		});
		
		this.cancel.addListener(new ClickListener() {
			public void clicked(InputEvent ie, float x, float y) {
				setIsOpen(false);
			}
		});
	}

	public boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
}
