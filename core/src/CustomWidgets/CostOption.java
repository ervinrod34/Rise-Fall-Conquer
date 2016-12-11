package CustomWidgets;

/**
 * Defines a class that displays the cost of 
 * creating or upgrading a resource or unit.
 *  
 * @author Ervin Rodriguez
 * @author Seth Demasi
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

public class CostOption{
	
	/**
	 * Initialize variables
	 */
	private Label resourceName, description;
	private TextButton approve, cancel;
	private Table cOption, container, holder;

	private Stage s;
	private Tile tile;
	private PlayerFaction pf;
	private String type;
	private boolean upgradable;
	private ResourceID tempResID;

	private boolean isOpen;


	public CostOption(Tile t, PlayerFaction pf, boolean upg, String type, ResourceID r) {
		
		s = null;
		this.tile = t;
		tempResID = r;
		System.out.println(tempResID.getId());
		this.pf = pf;
		this.upgradable = upg;
		this.type = type;
		this.isOpen = false;
		setContainer(new Table(MyGdxGame.MENUSKIN));
		cOption = new Table(MyGdxGame.MENUSKIN);
		cOption.setFillParent(true);
		holder = new Table(MyGdxGame.MENUSKIN);
		holder.setFillParent(true);
		
		Label background = new Label("", MyGdxGame.MENUSKINHUD);
		
		/*
		try {
			this.resourceName = new Label("Cost to Upgrade " + tile.getResourceID().name() 
					+ ": ", MyGdxGame.MENUSKIN);
			getContainer().add(this.resourceName);
			getContainer().row();
		} catch(NullPointerException e) {
			this.resourceName = new Label(" ", MyGdxGame.MENUSKIN);
			getContainer().add(this.resourceName);
			getContainer().row();
		}*/
		
		/**
		 * Setup the table for the cost display
		 */
		CharSequence tempCostDisplay = "Cost to upgrade:\n" + 
									   " " + this.pf.getResFoodCost() + " Food\n" +
									   " " + this.pf.getResWoodCost() + " Wood\n" + 
									   " " + this.pf.getResGoldCost() + " Gold";
		this.description = new Label(tempCostDisplay, MyGdxGame.MENUSKIN);
		getContainer().add(this.description);
		
		getContainer().row();
		
		/**
		 * Create TextButtons
		 */
		this.approve = new TextButton("APPROVE", MyGdxGame.MENUSKIN);
		this.cancel = new TextButton("CANCEL", MyGdxGame.MENUSKIN);
		
		//Checks if tile is upgrade; if true, display approve button, else display message
		if(this.upgradable == true && type.equals("UPG")) {
			getContainer().add(this.approve);
			//getContainer().row();
		}
		else if(type.equals("NEW")){
			getContainer().add(this.approve);
			//getContainer().row();
		}
		else {
			Label cantUpg = new Label("Not enough resources.", MyGdxGame.MENUSKIN);
			getContainer().add(cantUpg);
			getContainer().row();
		}
		getContainer().add(this.cancel);
		holder.add(getContainer());

		cOption.setFillParent(true);
		cOption.stack(background, holder);
		
		this.setApproveListener();
		this.setCancelListener();
	}


	private void setCancelListener() {
		// TODO Auto-generated method stub
		this.cancel.addListener(new ClickListener() {
			public void clicked(InputEvent ie, float x, float y) {
				cOption.remove();
				getContainer().remove();
				getContainer().invalidateHierarchy();
				setIsOpen(false);
			}
		});
	}

	public void setStage(Stage s){
		this.s = s;
	}
	
	public void setIsOpen(boolean b) {
		// TODO Auto-generated method stub
		this.isOpen = b;
	}
	
	public Table getCOption(){
		return this.cOption;
	}
	
	private void setApproveListener() {
		// TODO Auto-generated method stub
		this.approve.addListener(new ClickListener() {
			public void clicked(InputEvent ie, float x, float y) {
				if(getType() == "UPG") {
					getTile().getResource().upgradeTile();
					getPF().updateResourcesPerTurn();
					getPF().applyUpgradeCost();
				} else if(getType() == "NEW") {
					System.out.println(tempResID.getId());
					getTile().setResourceID(getResID());
					getPF().claimTile(getTile());
					getPF().applyUpgradeCost();
				}
				
				//Close the window
				cOption.remove();
				getContainer().remove();
				getContainer().invalidateHierarchy();
				setIsOpen(false);
			}
		});
	}

	public Tile getTile() {
		// TODO Auto-generated method stub
		return this.tile;
	}
	
	public ResourceID getResID(){
		return this.tempResID;
	}
	
	public PlayerFaction getPF(){
		return this.pf;
	}
	
	public String getType() {
		return this.type;
	}

	public Label getResourceName() {
		return resourceName;
	}


	public void setResourceName(Label resourceName) {
		this.resourceName = resourceName;
	}


	public Label getDescription() {
		return description;
	}


	public void setDescription(Label description) {
		this.description = description;
	}


	public Table getContainer() {
		return container;
	}


	public void setContainer(Table container) {
		this.container = container;
	}

}
