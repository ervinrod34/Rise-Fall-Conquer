package MainMenu;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;

public class OptionsMenu implements Screen{

	private Stage stage;
	//private TextureAtlas atlas;
	//private Table mainTable;
	
	//Label objects for the different options
	private Label difficultyLabel;
	private Label resolutionLabel;
	private Label volumeLabel; 
	private Label lightLabel;
	private Label animationLabel;
	
	
	public OptionsMenu(){
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		
		//create menu button
		TextButton menu = new TextButton("MENU",MyGdxGame.MENUSKIN);
		
		//Create a Slider to choose the game difficulty
		this.difficultyLabel = new Label("DIFFICULTY:", MyGdxGame.MENUSKIN);
		final Slider difficulty = new Slider(0, 3, 1, false, MyGdxGame.MENUSKIN);
		difficulty.setValue(MyGdxGame.DIFFICULTY);
		int savedDifficulty = Math.round(MyGdxGame.DIFFICULTY);
		String displayDif = "";
		if(savedDifficulty == 0) {
			displayDif = "Very Easy";
		} else if (savedDifficulty == 1) {
			displayDif = "Easy";
		} else if (savedDifficulty == 2){
			displayDif = "Normal";
		} else {
			displayDif = "Hard";
		}
		final Label selectedDifficulty = new Label(displayDif, MyGdxGame.MENUSKIN);
		
		//Create a Slider to choose the resolution settings
		this.resolutionLabel = new Label("GRAPHICS:", MyGdxGame.MENUSKIN);
		final Slider resolution = new Slider(0, 2, 1, false, MyGdxGame.MENUSKIN);
		resolution.setValue(MyGdxGame.RESOLUTION);
		int savedResolution = Math.round(MyGdxGame.RESOLUTION);
		String displayRes = "";
		
		if(savedResolution == 0) {
			displayRes = "Low";
		} else if (savedResolution == 1) {
			displayRes = "Med";
		} else {
			displayRes = "High";
		}
		final Label selectedResolution = new Label(displayRes, MyGdxGame.MENUSKIN);
		
		
		//Create a Bar that changes the music volume
		this.volumeLabel = new Label("VOLUME:", MyGdxGame.MENUSKIN);
		final Slider volume = new Slider(0, 100, 10, false, MyGdxGame.MENUSKIN);
		volume.setValue(MyGdxGame.VOLUME);
		String savedVolume = String.valueOf((int)MyGdxGame.VOLUME);
		final Label selectedVolume = new Label(savedVolume, MyGdxGame.MENUSKIN);
		
		//Checkbox to turn lighting on/off
		this.lightLabel = new Label("LIGHT:", MyGdxGame.MENUSKIN);
		final CheckBox light = new CheckBox(null, MyGdxGame.MENUSKIN);
		light.setChecked(MyGdxGame.LIGHTING);
		
		//Checkbox for animation
		this.animationLabel = new Label("ANIMATION:", MyGdxGame.MENUSKIN);
		final CheckBox animation = new CheckBox(null, MyGdxGame.MENUSKIN);
		animation.setChecked(MyGdxGame.ANIMATION);
		
		/**
		 * Creates tables for the widgets.
		 */
		/*Table mainTable = new Table(MyGdxGame.MENUSKIN);
		mainTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		mainTable.setFillParent(true);
		mainTable.top();
		menu.setPosition(200, 200);
		mainTable.add(menu);
		mainTable.row();*/
		Table centerTable = new Table(MyGdxGame.MENUSKIN);
		centerTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		centerTable.setFillParent(true);
		centerTable.center();
		
		/**
		 * Adds the widgets to the tables.
		 */
		centerTable.row();
		centerTable.add(new Label("", MyGdxGame.MENUSKIN));
		centerTable.add(menu).center();
		
		//difficulty
		centerTable.row();
		centerTable.add(this.difficultyLabel);
		centerTable.add(difficulty).width(200).padBottom(10);
		centerTable.add(selectedDifficulty).width(100).padBottom(10);
		
		//resolution
		centerTable.row();
		centerTable.add(this.resolutionLabel);
		centerTable.add(resolution).width(200).padBottom(10);
		centerTable.add(selectedResolution).width(100).padBottom(10);
		
		//volume
		centerTable.row();
		centerTable.add(this.volumeLabel);
		centerTable.add(volume).width(200).padBottom(10);
		centerTable.add(selectedVolume).width(100).padBottom(10);
		
		//light
		centerTable.row();
		centerTable.add(this.lightLabel);
		centerTable.add(light);
		
		//animation
		centerTable.row();
		centerTable.add(this.animationLabel);
		centerTable.add(animation);
		
		//stage.addActor(mainTable);
		stage.addActor(centerTable);
		
		/**
		 * Create action listeners for menu button.
		 */
		menu.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				MyGdxGame.GAME.setScreen(new MainMenu());
			}
		});
		
		/**
		 * Creates an action listener for the difficulty Slider.
		 * Updates the display for current value of Slider. Saves the
		 * value to the MyGdxGame.DIFFICULTY.
		 */
		difficulty.addListener(new ChangeListener(){
			public void changed(ChangeEvent event, Actor actor) {
				int intVal = Math.round(difficulty.getValue());
				String displayValue = "";
				
				if(intVal == 0) {
					displayValue = "Very Easy";
				} else if (intVal == 1) {
					displayValue = "Easy";
				} else if (intVal == 2){
					displayValue = "Normal";
				} else {
					displayValue = "Hard";
				}
				selectedDifficulty.setText(displayValue);
				MyGdxGame.DIFFICULTY = difficulty.getValue();
			}	
		});
		
		/**
		 * Creates an action listener for the resolution Slider.
		 * Updates the display for current value of Slider. Saves the
		 * value to the MyGdxGame.RESOLUTION.
		 */
		resolution.addListener(new ChangeListener(){
			public void changed(ChangeEvent event, Actor actor) {
					int intVal = Math.round(resolution.getValue());
					String displayValue = "";
					
					if(intVal == 0) {
						displayValue = "Low";
					} else if (intVal == 1) {
						displayValue = "Med";
					} else {
						displayValue = "High";
					}
					selectedResolution.setText(displayValue);
					MyGdxGame.RESOLUTION = resolution.getValue();
			}
		});
		
		/**
		 * Creates an action listener for the volume Slider.
		 * Updates the display for current value of Slider. Saves the
		 * value to the MyGdxGame.VOLUME.
		 */
		volume.addListener(new ChangeListener(){
			public void changed(ChangeEvent event, Actor actor) {
				selectedVolume.setText(String.valueOf((int)volume.getValue()));
				MyGdxGame.VOLUME = volume.getValue();
			}
		});
		
		/**
		 * Creates an action listener for the lighting CheckBox.
		 * Saves the value to the MyGdxGame.LIGHTING.
		 */
		light.addListener(new ChangeListener(){
			public void changed(ChangeEvent event, Actor actor) {
				MyGdxGame.LIGHTING = light.isChecked();
			}
		});
		
		/**
		 * Creates an action listener for the animation CheckBox.
		 * Saves the value to the MyGdxGame.ANIMATION.
		 */
		animation.addListener(new ChangeListener(){
			public void changed(ChangeEvent event, Actor actor) {
				MyGdxGame.ANIMATION = animation.isChecked();
			}
		});
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(50/255f, 50/255f, 50/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}