package MainMenu;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;

import map.Map;
import map.ResourceID;
import map.Tile;

public class MapSelectScreen implements Screen{
	
	private Stage stage;
	private Table mainTable;
	private SpriteBatch batch;
	private OrthographicCamera cam;
	private Map map;
	
	public MapSelectScreen(){
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		
		//title textfield
		Label title = new Label("Select Game Map", MyGdxGame.MENUSKIN);
		
		//add buttons to table
		mainTable = new Table(MyGdxGame.MENUSKIN);
		mainTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		mainTable.setFillParent(true);
		TextButton Map1 = new TextButton("Map1",MyGdxGame.MENUSKIN,"default");
		stage.addActor(mainTable);
		
		mainTable.add(title);
		mainTable.row();

		String mapName = "";
		final ArrayList<TextButton> buttons = new ArrayList<TextButton>();
		for(int i = 1; i< 11; i++){
			final int q = i;
			mapName = "Map_"+i+".json";
			buttons.add(new TextButton(mapName,MyGdxGame.MENUSKIN,"default"));
			if(i == 1){
				buttons.get(i - 1).setText("Multi Biome");
			} else if(i == 2){
				buttons.get(i - 1).setText("Dessert");
			}
			buttons.get(i-1).setName(mapName);
			mainTable.add(buttons.get(i-1));
			mainTable.row();
			if(i <= 2){
				buttons.get(i-1).addListener(new ClickListener(){
					public void clicked(InputEvent event, float x, float y) {
						System.out.println(buttons.get(q-1).getName());
						MyGdxGame.GAME.setScreen(new GameScreen(buttons.get(q-1).getName()));
					}
				});
			}
		}
		
		//mainTable.add(Map1);

		
		batch = new SpriteBatch();
		cam = new OrthographicCamera(1280, 720);
		map = new Map(cam);
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
		batch.begin();
		map.drawAnimated(batch);
		batch.end();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.getViewport().update(width, height,true);
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
	
	public void addButton(){
		
	}

}
