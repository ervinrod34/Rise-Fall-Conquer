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
		g = game;
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		
		//create a default skin
		createSimpleSkin();
		
		//create menu buttons
		TextButton play = new TextButton("PLAY",skin);
		TextButton options = new TextButton("OPTIONS",skin);
		TextButton exit = new TextButton ("EXIT",skin);
		
		//add buttons to table
		mainTable = new Table(skin);
		mainTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		mainTable.setFillParent(true);
		mainTable.top();
		mainTable.add(play);
		mainTable.row();
		mainTable.add(options);
		mainTable.row();
		mainTable.add(exit);

		
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
		Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
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
	
	private void createSimpleSkin()
	{
	  //Create a font
	  atlas = new TextureAtlas("button.pack");
	  BitmapFont font = new BitmapFont();
	  skin = new Skin(atlas);
	  skin.add("default", font);
	 
	  //Create a texture
	  Pixmap pixmap = new Pixmap((int)Gdx.graphics.getWidth()/4,(int)Gdx.graphics.getHeight()/10,Pixmap.Format.RGB888);
	  pixmap.setColor(Color.WHITE);
	  pixmap.fill();
	  skin.add("background",new Texture(pixmap));
	  //Create a button style
	  TextButtonStyle textButtonStyle = new TextButtonStyle();
	  textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
	  textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
	  textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
	  textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
	  textButtonStyle.font = skin.getFont("default");
	  skin.add("default", textButtonStyle);
	}
	
}
