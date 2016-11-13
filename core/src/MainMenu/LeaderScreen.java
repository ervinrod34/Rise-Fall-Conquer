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

import factions.Score;
import factions.ScoreBoard;

public class LeaderScreen implements Screen {

	private Stage stage;
	private Table mainTable;
	private TextButton menu;
	
	public LeaderScreen() {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		//button to go back to menu
		menu = new TextButton("MENU",MyGdxGame.MENUSKIN);
		
		//set up table
		mainTable = new Table(MyGdxGame.MENUSKIN);
		mainTable.setBounds(0, 0, Gdx.graphics.getWidth()
					, Gdx.graphics.getHeight());
		mainTable.setFillParent(true);
		
		//get the high scores from the database
		ScoreBoard s = new ScoreBoard();
		s.fillFromDatabase();
		
		//add menu button
		mainTable.row();
		mainTable.add(menu);
		mainTable.center();
		
		//generate labels w/scores from database
		for(Score score : s.getScoreList()) {
			mainTable.row();
			mainTable.add(new Label(score.toString()
						, MyGdxGame.MENUSKIN)).width(100);
			mainTable.center();
		}
		stage.addActor(mainTable);
		
		//add a listener for menu button
		//so that it goes back to main menu
		menu.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				MyGdxGame.GAME.setScreen(new MainMenu());
			}
		});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(50/255f, 50/255f, 50/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
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
}
