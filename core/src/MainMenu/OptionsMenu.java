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
	
	
	private Label difficultyLabel;
	//private SelectBox<String> difficulty;
	private String selectedDifficulty;
	//private ArrayList<TextButton> difficultiesList;
	
	private Label resolutionLabel;
	//private SelectBox<String> resolution;
	//private TextButton[] resolutionsList;
	
	private Label volumeLabel;
	//private Slider volume; 
	
	private Label lightLabel;
	//private CheckBox light;
	private boolean lightClicked;
	
	private Label animationLabel;
	//private CheckBox animation;
	private boolean animationClicked;
	
	
	public OptionsMenu(){
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		
		//create menu button
		TextButton menu = new TextButton("MENU",MyGdxGame.MENUSKIN);
		
		//TODO: Create clickable selection objects.
		//Drop down menu for game difficulty
		this.difficultyLabel = new Label("DIFFICULTY:", MyGdxGame.MENUSKIN);
		final SelectBox<String> difficulty = new SelectBox<String>(MyGdxGame.MENUSKIN);
		difficulty.setItems("Conqueror", "Hard", "Normal", "Easy");
		//this.selectedDifficulty = difficulty.getSelected();
		/*this.difficultiesList = new ArrayList<TextButton>();
		this.difficultiesList.add(new TextButton("Conqueror", MyGdxGame.MENUSKIN));
		this.difficultiesList.add(new TextButton("Hard", MyGdxGame.MENUSKIN));
		this.difficultiesList.add(new TextButton("Normal", MyGdxGame.MENUSKIN));
		this.difficultiesList.add(new TextButton("Easy", MyGdxGame.MENUSKIN));
		//for(int i = 0; i < this.difficultiesList.size(); i++) {
		this.difficulty.setItems(this.difficultiesList.get(0), this.difficultiesList.get(1),
								 this.difficultiesList.get(2), this.difficultiesList.get(3));
		//}*/
		
		//Create drop down menu to change resolution
		this.resolutionLabel = new Label("GRAPHICS:", MyGdxGame.MENUSKIN);
		final SelectBox<String> resolution = new SelectBox<String>(MyGdxGame.MENUSKIN);
		resolution.setItems("High", "Medium", "Low");
		
		
		//Create a Bar that changes the music volume
		this.volumeLabel = new Label("VOLUME:", MyGdxGame.MENUSKIN);
		final Slider volume = new Slider(0, 100, 10, false, MyGdxGame.MENUSKIN);
		
		//Checkbox to turn lighting on/off
		this.lightLabel = new Label("LIGHT:", MyGdxGame.MENUSKIN);
		final CheckBox light = new CheckBox(null, MyGdxGame.MENUSKIN);
		
		//Checkbox for animation
		this.animationLabel = new Label("ANIMATION:", MyGdxGame.MENUSKIN);
		final CheckBox animation = new CheckBox(null, MyGdxGame.MENUSKIN);
		
		
		/**
		 * Creates tables for the widgets.
		 */
		Table mainTable = new Table(MyGdxGame.MENUSKIN);
		mainTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		mainTable.setFillParent(true);
		mainTable.top();
		menu.setPosition(200, 200);
		mainTable.add(menu);
		mainTable.row();
		Table centerTable = new Table(MyGdxGame.MENUSKIN);
		centerTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		centerTable.setFillParent(true);
		centerTable.center();
		
		/**
		 * Adds the widgets to the tables.
		 */
		//difficulty
		centerTable.row();
		centerTable.add(this.difficultyLabel);
		centerTable.add(difficulty);
		
		//resolution
		centerTable.row();
		centerTable.add(this.resolutionLabel);
		centerTable.add(resolution);
		
		//volume
		centerTable.row();
		centerTable.add(this.volumeLabel);
		centerTable.add(volume);
		
		//light
		centerTable.row();
		centerTable.add(this.lightLabel);
		centerTable.add(light);
		
		//animation
		centerTable.row();
		centerTable.add(this.animationLabel);
		centerTable.add(animation);
		
		stage.addActor(mainTable);
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
		 * Creates an action listener for the difficulty SelectBox.
		 * TODO: When clicking an item in the selectBox, must be clickable.
		 *       Checks the value of the selected item, calls the method that will
		 *       update game settings. For now, just print!!
		 * 		 
		 */
		difficulty.addListener(new ChangeListener(){
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println(difficulty.getSelected());
				/*if(difficulty.getSelected().equalsIgnoreCase("Conqueror")) {
					System.out.println("You chose Conqueror level! You'll die.");
				} else if (difficulty.getSelected().equalsIgnoreCase("Hard")) {
					System.out.println("Hard Level!");
				} else if (difficulty.getSelected().equalsIgnoreCase("Normal")) {
					System.out.println("Normal Level!");
				} else if (difficulty.getSelected().equalsIgnoreCase("Easy")) {
					System.out.println("Easy Level!");
				}*/
			}	
		});
		
		/**
		 * Create an action listener for the resolution SelectBox.
		 */
		resolution.addListener(new ChangeListener(){
			public void changed(ChangeEvent event, Actor actor) {
				// TODO change the resolution
				System.out.println(resolution.getSelected());
			}
		});
		
		volume.addListener(new ChangeListener(){
			public void changed(ChangeEvent event, Actor actor) {
				// TODO change the volumes
			}
		});
		
		light.addListener(new ChangeListener(){
			public void changed(ChangeEvent event, Actor actor) {
				if(lightClicked == false) {
					lightClicked = true;
					System.out.println("Lights ON");
				} else {
					lightClicked = false;
					System.out.println("Lights OFF");
				}
				
			}
		});
		
		animation.addListener(new InputListener(){
			public void clicked(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("Clicked");
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