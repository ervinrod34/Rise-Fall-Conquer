package CustomWidgets;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.MyGdxGame;

import factions.PlayerFaction;
import map.Tile;

public class HomeTileOption {
	
	private Label resName, tileName, desc;
	private TextButton exit, upgrade, buildWorker;
	//private ArrayList<TextButton> buttons;
	private Table tOptions, holder, container, buttonsTable;
	private Tile tile;
	private boolean isOpen;
	private PlayerFaction pf;

	public HomeTileOption(Tile t, PlayerFaction pf) {
		//initialize tile, player faction, whether or not menu is open
		tile = t;
		this.pf = pf;
		isOpen = false;
		
		ArrayList<Cell> cells = new ArrayList<Cell>();
		ArrayList<TextButton> b = new ArrayList<TextButton>();
		//create all tables to be used
		setContainer(new Table(MyGdxGame.MENUSKIN));
		tOptions = new Table(MyGdxGame.MENUSKIN);
		tOptions.setFillParent(true);
		holder = new Table(MyGdxGame.MENUSKIN);
		holder.setFillParent(true);
		buttonsTable = new Table(MyGdxGame.MENUSKIN);
		
		//create generic background for tables
		Label background = new Label("", MyGdxGame.MENUSKINHUD);
		
		//Setting resource name and tile name, adding both to table
		try{
			resName = new Label(tile.getResourceID().name() + " ",MyGdxGame.MENUSKIN);
			getContainer().add(resName);
			getContainer().row();
		} catch(NullPointerException e){}
		
		try{
			tileName = new Label(tile.getTileId().name() + " ",MyGdxGame.MENUSKIN);
		} catch(NullPointerException e) {}
		
		//create text buttons
		//desc = new Label("A tile description",MyGdxGame.MENUSKIN);
		exit = new TextButton("EXIT",MyGdxGame.MENUSKIN);
		upgrade = new TextButton("UPGRADE",MyGdxGame.MENUSKIN);
		buildWorker = new TextButton("BUILD WORKER",MyGdxGame.MENUSKIN);
		b.add(exit);
		b.add(upgrade);
		b.add(buildWorker);
		
		float maxWidth = 0;
		//add items to table
		getContainer().add(tileName);
		getContainer().row();
		getContainer().add(desc);
		getContainer().row();
		cells.add(getContainer().add(upgrade));
		getContainer().row();
		cells.add(getContainer().add(buildWorker));
		getContainer().row();
		cells.add(getContainer().add(exit));
		getContainer().row();
		holder.add(getContainer());
		holder.add(buttonsTable);
		
		settOptions(new Table(MyGdxGame.MENUSKIN));
		tOptions.setFillParent(true);
		tOptions.stack(background, holder);
		
		
		float maxSize = b.get(0).getWidth();
		for(TextButton bt : b){
			if(bt.getWidth() > maxSize){
				maxSize = bt.getWidth();
			}
		}
		for(Cell c : cells){
			c.width(maxSize);
		}
			//set listeners for buttons
		this.setExitListener();
		this.setUpgradeListener();
		this.setBuildListeners();
		
	}

	private void settOptions(Table table) {
		// TODO Auto-generated method stub
		this.tOptions = table;
	}

	private void setBuildListeners() {
		buildWorker.addListener(new ClickListener() {
			public void clicked(InputEvent e,float x,float y){
				Gdx.app.log(this.getClass().getName(),"IM SO HAPPY RIGHT NOW, I BUILT A WORKER");
			}
		});
	}

	private void setUpgradeListener() {
		upgrade.addListener(new ClickListener() {
			public void clicked(InputEvent e,float x,float y){
				if(tile.getResource() != null){
					tile.getResource().upgradeTile();
				}
			}
		});
	}

	private void setExitListener() {
		// TODO Auto-generated method stub
		exit.addListener(new ClickListener() {
			public void clicked(InputEvent e,float x,float y){
				tOptions.remove();
				getContainer().remove();
				getContainer().invalidateHierarchy();
				setIsOpen(false);
			}
		});
	}

	private Table getContainer() {
		// TODO Auto-generated method stub
		return container;
	}

	private void setContainer(Table table) {
		// TODO Auto-generated method stub
		this.container = table;
	}

	public Actor gettOptions() {
		// TODO Auto-generated method stub
		return tOptions;
	}

	public void setIsOpen(boolean b) {
		// TODO Auto-generated method stub
		isOpen = b;
		
	}

}
