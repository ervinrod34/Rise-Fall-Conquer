package CustomWidgets;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.MyGdxGame;

import factions.Unit;
import map.Map;

public class UnitOption {
	
	private Label menuTitle;
	private TextButton exitButton, upgradeButton, moveButton;
	private Table tOptions, holder, container, buttonsTable;
	private boolean isOpen, isMoving;
	private Unit uUnit;
	
	public UnitOption(Unit u) {
		//initialize tile, player faction, whether or not menu is open
		this.uUnit = u;
		isOpen = false;
		
		ArrayList<Cell<TextButton>> cells = new ArrayList<Cell<TextButton>>();
		ArrayList<TextButton> textButtons = new ArrayList<TextButton>();
		
		// Create all tables to be used
		setContainer(new Table(MyGdxGame.MENUSKIN));
		tOptions = new Table(MyGdxGame.MENUSKIN);
		tOptions.setFillParent(true);
		holder = new Table(MyGdxGame.MENUSKIN);
		holder.setFillParent(true);
		buttonsTable = new Table(MyGdxGame.MENUSKIN);
		
		// Create basic menu labels
		menuTitle = new Label(uUnit.getType().name(), MyGdxGame.MENUSKIN);
		
		// Create generic background for tables
		Label background = new Label("", MyGdxGame.MENUSKINHUD);
		
		// create text buttons
		exitButton = new TextButton("Exit",MyGdxGame.MENUSKIN);
		upgradeButton = new TextButton("Upgrade",MyGdxGame.MENUSKIN);
		moveButton = new TextButton("Move",MyGdxGame.MENUSKIN);
		textButtons.add(exitButton);
		textButtons.add(upgradeButton);
		textButtons.add(moveButton);
		
		//add items to table
		getContainer().add(menuTitle);
		getContainer().row();
		cells.add(getContainer().add(upgradeButton));
		getContainer().row();
		cells.add(getContainer().add(moveButton));
		getContainer().row();
		cells.add(getContainer().add(exitButton));
		getContainer().row();
		holder.add(getContainer());
		holder.add(buttonsTable);
		
		settOptions(new Table(MyGdxGame.MENUSKIN));
		tOptions.setFillParent(true);
		tOptions.stack(background, holder);
		
		//Make all text buttons the same size
		this.resizeTextButtons(textButtons, cells);
		
		//set listeners for buttons
		exitButton.addListener(new ClickListener() {
			public void clicked(InputEvent e,float x,float y){
				tOptions.remove();
				getContainer().remove();
				getContainer().invalidateHierarchy();
				setIsOpen(false);
			}
		});
		upgradeButton.addListener(new ClickListener(){
			public void clicked(InputEvent e,float x,float y){
				uUnit.upgrade();
			}
		});
		moveButton.addListener(new ClickListener(){
			public void clicked(InputEvent e,float x,float y){
				uUnit.displayMovementRange();
				tOptions.remove();
				getContainer().remove();
				getContainer().invalidateHierarchy();
				setIsOpen(false);
				isMoving = true;
			}
		});
		
	}

	/**
	 * Make all text buttons the same size
	 * 
	 * @param textButtons
	 * @param cells
	 */
	private void resizeTextButtons(ArrayList<TextButton> textButtons, ArrayList<Cell<TextButton>> cells){
		float maxSize = textButtons.get(0).getWidth();
		for(TextButton bt : textButtons){
			if(bt.getWidth() > maxSize){
				maxSize = bt.getWidth();
			}
		}
		for(Cell c : cells){
			c.width(maxSize);
		}
	}
	
	private void settOptions(Table table) {
		// TODO Auto-generated method stub
		this.tOptions = table;
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

	public boolean getIsOpen() {
		// TODO Auto-generated method stub
		return isOpen;
	}
	public boolean getIsMoving(){
		return this.isMoving;
	}
	public void setIsMoving(boolean moving){
		this.isMoving = moving;
	}
	public Unit getUnit(){
		return uUnit;
	}

}
