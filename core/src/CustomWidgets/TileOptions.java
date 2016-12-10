package CustomWidgets;

import java.util.ArrayList;

import javax.annotation.Resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.MyGdxGame;

import factions.PlayerFaction;
import map.ResourceID;
import map.Tile;


public class TileOptions {
	
	private Stage stage;
	private Label resName, tileName, desc;
	private TextButton exit, upgrade;
	private ArrayList<TextButton> buttons;
	private Table tOptions, holder, container, buttonsTable;
	private Tile tile;
	private boolean isOpen;
	private PlayerFaction pf;
	private CostOption costDisplay;
	
	public TileOptions(Tile t, PlayerFaction pf){
		this.stage = null;
		this.pf = pf;
		isOpen = false;	
		tile = t;
		setContainer(new Table(MyGdxGame.MENUSKIN));
		tOptions = new Table(MyGdxGame.MENUSKIN);
		tOptions.setFillParent(true);
		holder = new Table(MyGdxGame.MENUSKIN);
		holder.setFillParent(true);
		buttonsTable = new Table(MyGdxGame.MENUSKIN);
		
		Label background = new Label("", MyGdxGame.MENUSKINHUD);
		
		//Setting resource name and tile name, adding both to table
		try{
			resName = new Label(tile.getResourceID().name() + " ",MyGdxGame.MENUSKIN);
			getContainer().add(resName);
			getContainer().row();
		} catch(NullPointerException e){
			resName = new Label(" ",MyGdxGame.MENUSKIN);
			getContainer().add(resName);
			getContainer().row();
		}
		
		try{
			tileName = new Label(tile.getTileId().name() + " ",MyGdxGame.MENUSKIN);
		} catch(NullPointerException e) {}
		
		desc = new Label("A tile description",MyGdxGame.MENUSKIN);
		exit = new TextButton("EXIT",MyGdxGame.MENUSKIN);
		upgrade = new TextButton("UPGRADE",MyGdxGame.MENUSKIN);
		
		buttons = new ArrayList<TextButton>();
		ArrayList<Cell> cells = new ArrayList<Cell>();
		int i = 0;
		for(ResourceID r : ResourceID.values()){
			buttons.add(new TextButton(r.name(),MyGdxGame.MENUSKIN));
			buttons.get(i).setName(r.name());
			cells.add(buttonsTable.add(buttons.get(i)));
			if((i+1)%2 ==0)
				buttonsTable.row();
			i++;
		}
		
		// Resize buttons
		float maxSize = buttons.get(0).getWidth();
		for(TextButton b : buttons){
			if(b.getWidth() > maxSize){
				maxSize = b.getWidth();
			}
		}
		for(Cell c : cells){
			c.width(maxSize);
		}
		
		//add items to table
		getContainer().add(tileName);
		getContainer().row();
		getContainer().add(desc);
		getContainer().row();
		getContainer().add(upgrade);
		getContainer().row();
		getContainer().add(exit);
		getContainer().row();
		
		holder.add(getContainer());
		holder.add(buttonsTable);
		
		settOptions(new Table(MyGdxGame.MENUSKIN));
		gettOptions().setFillParent(true);
		gettOptions().stack(background, holder);
		
			//set listeners for buttons
		this.setExitListener();
		this.setUpgradeListener();
		this.setButtonListeners();
	}
	
	//returns the table containing everything else
	public Table getTable()
	{
		return this.getContainer();
	}
	
	//exit listener for exit button
	public void setExitListener(){
		exit.addListener(new ClickListener() {
			public void clicked(InputEvent e,float x,float y){
				tOptions.remove();
				getContainer().remove();
				getContainer().invalidateHierarchy();
				setIsOpen(false);
			}
		});
	}
	
	//upgrade listener for upgrade button
	public void setUpgradeListener(){
		upgrade.addListener(new ClickListener() {
			public void clicked(InputEvent e,float x,float y){
				try {
					if(tile.getResource() != null) {
						boolean upgradable = pf.checkCanUpgrade(tile.getResourceID());
						costDisplay = new CostOption(tile, pf, upgradable, "UPG");
						costDisplay.getMainTable().setFillParent(true);
						costDisplay.getMainTable().center();
						//costDisplay.getMainTable().setColor(Color.BLACK);
						getStage().addActor(costDisplay.getMainTable());
						costDisplay.setStage(getStage());
						costDisplay.setIsOpen(true);
					
					}
					
					/*
					if(tile.getResource() != null){
						if(pf.checkCanUpgrade(tile.getResourceID()) == true) {
							tile.getResource().upgradeTile(); //this is where tile is getting upgraded, use pf to access res
							pf.updateResourcesPerTurn();
							pf.applyUpgradeCost();
						}
					}*/
				} catch (NullPointerException ne) {
					//upgrade.setVisible(false);
					Gdx.app.error(null, "Upgrade error.");
				}
				//Gdx.app.log(this.getClass().getName(),"IM SO HAPPY RIGHT NOW, I'M BEING UPGRADED");
			}
		});
	}
	
	public void setButtonListeners(){
		for(final TextButton b : buttons){
			b.addListener(new ClickListener(){
				public void clicked(InputEvent e, float x, float y){
					try {
						for(ResourceID r : ResourceID.values()){
							if(b.getName().equals(r.name()) && tile.getClaim() == 0){								
								/*boolean upgradable = pf.checkCanUpgrade(r);
								System.out.println("line 174 ok");
								costDisplay = new CostOption(tile, pf, upgradable, "NEW");
								System.out.println("line 176 ok");
								costDisplay.setResID(r);
								System.out.println("line 178 ok");
								//resName.setText(tile.getResourceID().name());
								//System.out.println("line 180 ok");
								
								costDisplay.getMainTable().setFillParent(true);
								System.out.println("line 182 ok");
								costDisplay.getMainTable().center();
								System.out.println("line 184 ok");
								getStage().addActor(costDisplay.getMainTable());
								System.out.println("line 186 ok");
								costDisplay.setStage(getStage());
								System.out.println("line 188 ok");
								costDisplay.setIsOpen(true);*/
								
								
								if(pf.checkCanUpgrade(r) == true) {
									tile.setResourceID(r);
								    resName.setText(tile.getResourceID().name());
								
									pf.claimTile(tile); //already calls updateResourcesPerTurn
									pf.applyUpgradeCost();
									
								} else {
									System.out.println("Not enough Resources.");
								}
							}
						}
					} catch (NullPointerException ne) {
						Gdx.app.error(null, "Upgrade error.");
					}
				}
			});
		}
	}
	
	public Stage getStage() {
		return this.stage;
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Table gettOptions() {
		return tOptions;
	}

	public void settOptions(Table tOptions) {
		this.tOptions = tOptions;
	}

	public Table getContainer() {
		return container;
	}

	public void setContainer(Table container) {
		this.container = container;
	}

	public boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
}
