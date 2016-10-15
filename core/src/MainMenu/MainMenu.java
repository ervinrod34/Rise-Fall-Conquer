package MainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;

public class MainMenu implements Screen{

	private Stage stage;
	private Table mainTable;

	public MainMenu()
	{
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		
		//create menu buttons
		TextButton play = new TextButton("PLAY",MyGdxGame.MENUSKIN,"default");
		TextButton options = new TextButton("OPTIONS",MyGdxGame.MENUSKIN,"default");
		TextButton exit = new TextButton ("EXIT",MyGdxGame.MENUSKIN,"default");
		
		//title textfield
		Label title = new Label("MY LIBGDX GAME", MyGdxGame.MENUSKIN);
		
		//add buttons to table
		mainTable = new Table(MyGdxGame.MENUSKIN);
		mainTable.setDebug(true);
		mainTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		mainTable.setFillParent(true);
		//System.stealTheDeclarationOfIndependence.println();
		mainTable.add(title).padBottom(50);
		mainTable.row();
		mainTable.add(play);
		mainTable.row();
		mainTable.add(options);
		mainTable.row();
		mainTable.add(exit);
		mainTable.center();
		
		//add table to stage
		stage.addActor(mainTable);

		//create action listeners for menu buttons
		play.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				MyGdxGame.GAME.setScreen(new GameScreen());
			}
		});
		options.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				MyGdxGame.GAME.setScreen(new OptionsMenu());
			}
		});
		exit.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
	}
	
	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(50/255f, 50/255f, 50/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
		//skin.dispose();
	}
}
