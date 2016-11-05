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

public class UnitOption {
	
	private Label menuTitle, infoHealth, infoDefense, infoAttack;
	private TextButton exitButton, upgradeButton, moveButton, attackButton;
	private Table tOptions, container, infoTable, unitButtonTable;
	private boolean isOpen, isMoving, isAttacking;
	private Unit uUnit;
	
	public UnitOption(Unit u) {
		//initialize tile, player faction, whether or not menu is open
		this.uUnit = u;
		isOpen = false;
		
		ArrayList<Cell<TextButton>> cells = new ArrayList<Cell<TextButton>>();
		ArrayList<TextButton> textButtons = new ArrayList<TextButton>();
		
		// Create all tables to be used
		setContainer(new Table(MyGdxGame.MENUSKIN));
		container.setFillParent(true);
		tOptions = new Table(MyGdxGame.MENUSKIN);
		tOptions.setFillParent(true);
		infoTable = new Table(MyGdxGame.MENUSKIN);
		unitButtonTable = new Table(MyGdxGame.MENUSKIN);
		//infoTable.setFillParent(true);
		
		// Create basic menu labels
		menuTitle = new Label(uUnit.getType().name(), MyGdxGame.MENUSKIN);
		
		// Create unit info
		infoHealth = new Label("Health: " + Integer.toString((int)uUnit.getHealth()), MyGdxGame.MENUSKINHUD, "info");
		infoAttack = new Label("Attack: " + Integer.toString((int)uUnit.getAttack()), MyGdxGame.MENUSKINHUD, "info");
		infoDefense = new Label("Defense: " + Integer.toString((int)uUnit.getDefense()), MyGdxGame.MENUSKINHUD, "info");
		infoTable.add(infoHealth).pad(3);
		infoTable.add(infoAttack).pad(3);
		infoTable.add(infoDefense).pad(3);
		
		// Create generic background for tables
		Label background = new Label("", MyGdxGame.MENUSKINHUD);
		
		// Create text buttons
		exitButton = new TextButton("Exit",MyGdxGame.MENUSKIN);
		upgradeButton = new TextButton("Upgrade",MyGdxGame.MENUSKIN);
		moveButton = new TextButton("Move",MyGdxGame.MENUSKIN);
		attackButton = new TextButton("Attack",MyGdxGame.MENUSKIN);
		textButtons.add(exitButton);
		textButtons.add(upgradeButton);
		textButtons.add(moveButton);
		textButtons.add(attackButton);
		cells.add(unitButtonTable.add(upgradeButton));
		cells.add(unitButtonTable.add(moveButton));
		cells.add(unitButtonTable.add(attackButton));
		
		// Add items to table
		getContainer().add(menuTitle);
		getContainer().row();
		getContainer().add(infoTable);
		getContainer().row();
		getContainer().add(unitButtonTable);
		getContainer().row();
		cells.add(getContainer().add(exitButton));
		getContainer().row();
		
		settOptions(new Table(MyGdxGame.MENUSKIN));
		tOptions.setFillParent(true);
		tOptions.stack(background, getContainer());
		
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
		attackButton.addListener(new ClickListener(){
			public void clicked(InputEvent e,float x,float y){
				tOptions.remove();
				getContainer().remove();
				getContainer().invalidateHierarchy();
				if(uUnit.hasAttacked() == true){
					return;
				}
				setIsOpen(false);
				uUnit.displayAttackRange();
				isAttacking = true;
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
	
	/**
	 * @return the isAttacking
	 */
	public boolean isAttacking() {
		return isAttacking;
	}

	/**
	 * @param isAttacking the isAttacking to set
	 */
	public void setAttacking(boolean isAttacking) {
		this.isAttacking = isAttacking;
	}

	public Unit getUnit(){
		return uUnit;
	}

}
