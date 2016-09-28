package MainMenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Navigator;

public class GameScreen implements Screen{
	private Game game;
	SpriteBatch batch;
	private map.Map mBoard;
	private OrthographicCamera oGameCam;
	private Navigator nav;
	private Stage stage;
	private Skin skin;
	private TextureAtlas atlas;
	private Table gameTable;
	
	public GameScreen(Game g)
	{
		game=g;
		createSimpleSkin();
		
		TextButton menu = new TextButton("MENU", skin);
		menu.setWidth(100);
		menu.setHeight(100);
		stage = new Stage(new ScreenViewport());
	    gameTable = new Table(skin);
		gameTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		gameTable.setFillParent(true);
		gameTable.top();
		menu.setPosition(0,0);
		gameTable.add(menu);
		gameTable.row();
		stage.addActor(gameTable);
		
		menu.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MainMenu(game));
			}
		});
		
		batch = new SpriteBatch();
		mBoard = new map.Map();
		oGameCam = new OrthographicCamera(1280, 720);
		nav = new Navigator(oGameCam, batch);
		InputMultiplexer ipm = new InputMultiplexer();
		ipm.addProcessor(nav);
		ipm.addProcessor(stage);
		Gdx.input.setInputProcessor(ipm);
		oGameCam.position.set(oGameCam.viewportWidth /2,oGameCam.viewportHeight /2,0);
		oGameCam.update();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(90/255f, 128/255f, 44/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		mBoard.drawMap(batch);
		batch.end();
		nav.inputHandle();
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
