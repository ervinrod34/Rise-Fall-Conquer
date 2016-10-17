package MainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
	private TextureAtlas atlas;
	private Table mainTable;

	public OptionsMenu(){
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		
		//create menu button
		TextButton menu = new TextButton("MENU",MyGdxGame.MENUSKIN);
		
		//Drop down menu for game difficulty
		Label difficultyLabel = new Label("DIFFICULTY:", MyGdxGame.MENUSKIN);
		SelectBox<String> difficulty = new SelectBox<String>(MyGdxGame.MENUSKIN);
		difficulty.setItems("Conqueror", "Hard", "Normal", "Easy");
		
		//Create drop down menu to change resolution
		Label resolutionLabel = new Label("GRAPHICS:", MyGdxGame.MENUSKIN);
		SelectBox<String> resolution = new SelectBox<String>(MyGdxGame.MENUSKIN);
		resolution.setItems("High", "Medium", "Low");
		
		//Create a Bar that changes the music volume
		Label volumeLabel = new Label("VOLUME:", MyGdxGame.MENUSKIN);
		Slider volume = new Slider(0, 100, 50, false, MyGdxGame.MENUSKIN);
		
		//Checkbox to turn lighting on/off
		Label lightLabel = new Label("LIGHT(ON/OFF):", MyGdxGame.MENUSKIN);
		CheckBox light = new CheckBox(null, MyGdxGame.MENUSKIN);
		
		//Checkbox for animation
		Label animationLabel = new Label("ANIMATION(ON/OFF)", MyGdxGame.MENUSKIN);
		CheckBox animation = new CheckBox(null, MyGdxGame.MENUSKIN);
		
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
		
		centerTable.row();
		centerTable.add(difficultyLabel);
		centerTable.add(difficulty);
		
		centerTable.row();
		centerTable.add(resolutionLabel);
		centerTable.add(resolution);
		
		centerTable.row();
		centerTable.add(volumeLabel);
		centerTable.add(volume);
		
		centerTable.row();
		centerTable.add(lightLabel);
		centerTable.add(light);
		
		centerTable.row();
		centerTable.add(animationLabel);
		centerTable.add(animation);
		
		stage.addActor(mainTable);
		stage.addActor(centerTable);
		
		//create action listeners for menu buttons
		menu.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				MyGdxGame.GAME.setScreen(new MainMenu());
			}
		});
		
		
		//Listeners for VOLUME and RESOLUTION
		resolution.addListener(new ChangeListener(){
			public void changed(ChangeEvent event, Actor actor) {
				// TODO change the resolution
			}
		});
		
		volume.addListener(new ChangeListener(){
			public void changed(ChangeEvent event, Actor actor) {
				// TODO change the volumes
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