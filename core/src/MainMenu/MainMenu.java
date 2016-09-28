package MainMenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenu implements Screen{

	private Stage stage;
	private Skin skin;
	private TextureAtlas atlas;
	private Game g;
	private Table mainTable;

	public MainMenu(final Game game)
	{
		skin = new Skin(Gdx.files.internal("TableAssets/uiskin.json"));
		g = game;
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		
		//create menu buttons
		TextButton play = new TextButton("PLAY",skin,"default");
		TextButton options = new TextButton("OPTIONS",skin,"default");
		TextButton exit = new TextButton ("EXIT",skin,"default");
		
		//title textfield
		TextField title = new TextField("MY LIBGDX GAME", skin);
		
		//add buttons to table
		mainTable = new Table(skin);
		mainTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		mainTable.setFillParent(true);
		//System.stealTheDeclarationOfIndependence.println();
		mainTable.add(title);
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
				game.setScreen(new GameScreen(game));
			}
		});
		options.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new OptionsMenu(game));
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
		Gdx.gl.glClearColor(90/255f, 128/255f, 44/255f, 1);
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
		skin.dispose();
	}
}
