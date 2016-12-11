package CustomWidgets;



/**
 * A class representing a window that appears
 * when the player decides to end the game. 
 * Prompt user to enter a name and either submit
 * or not submit the player's score.
 * 
 * @author Ervin Rodriguez
 * @version 1.0
 */

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.MyGdxGame;

import MainMenu.MainMenu;
import factions.Score;

public class EndGameWindow {
	
	/**
	 * Initialize variables
	 */
	private Stage stage;
	private Label windowTitle;
	private Label message;
	private TextButton exitToMain;
	private TextButton submitScore;
	private boolean highScorer;
	private boolean playerWon;
	private boolean isOpen;
	private Score playerScore;
	
	private Table mainTable, container;
	
	private TextField input;
	private String text;
	
	/**
	 * Creates an EndGameWindow.
	 * @param playerGotHighScore A boolean whether player is a 
	 * 							 high scorer or not
	 */
	public EndGameWindow(boolean playerGotHighScore, boolean pW, Score score) {
		this.highScorer = playerGotHighScore;
		this.playerWon = pW;
		this.playerScore = score;
		
		this.stage = null;
		
		setContainer(new Table(MyGdxGame.MENUSKIN));
		getContainer().setFillParent(true);
		
		//The title of the window
		this.windowTitle = new Label("END OF GAME\n", MyGdxGame.MENUSKIN);
		getContainer().add(this.windowTitle);
		getContainer().row();
		
		//Check if the player won
		//If true, print winning message. Else, print losing message
		if(this.playerWon == true) {
			
			this.message = new Label("\"I rise, you fall.\" -Optimus Prime\nYou are a true Conqueror.\n", 
										 MyGdxGame.MENUSKIN);
			getContainer().row();
		} else {
			this.message = new Label("You have become one of the Fallen.", MyGdxGame.MENUSKIN);
		}
		
		//Adds the title to the container
		getContainer().add(this.message);
		getContainer().row();
		
		//If playerWon is true, show a TextField to input name and show submit button
		if(this.highScorer == true) {
			Label prompt = new Label("Enter your name: ", MyGdxGame.MENUSKIN);
			this.input = new TextField("", MyGdxGame.MENUSKIN);
			getContainer().add(prompt);
			getContainer().add(this.input);
			getContainer().row();
			
			this.submitScore = new TextButton("Submit Score", MyGdxGame.MENUSKIN);
			getContainer().add(this.submitScore);
			getContainer().row();
			/**
			 * Creates a listener for the submitScore button.
			 * Grabs the text from the box and push the text and the 
			 * player's score to the database.
			 */
			this.submitScore.addListener(new ClickListener() {
				public void clicked(InputEvent ie, float x, float y) {
					
					setText(getInput().getText());
					System.out.println("TextField should have: " + getText());
					playerScore.setName(getText());
					playerScore.pushToLeaderBoard();
				}
			});
		}
		
		this.exitToMain = new TextButton("Exit to Main Menu", MyGdxGame.MENUSKIN);
		getContainer().add(this.exitToMain);
		
		Label background = new Label("", MyGdxGame.MENUSKINHUD);
		
		setMainTable(new Table(MyGdxGame.MENUSKIN));
		getMainTable().setFillParent(true);
		getMainTable().stack(background, getContainer());
		

		
		/**
		 * Creates a listener for the exitToMain button.
		 * Changes the screen into the MainMenu screen.
		 */
		this.exitToMain.addListener(new ClickListener() {
			public void clicked(InputEvent ie, float x, float y) {
				MyGdxGame.GAME.setScreen(new MainMenu());
			}
		});
	}
	
	/**
	 * Change the text.
	 * @param t A String object
	 */
	public void setText(String t) {
		this.text = t;
	}
	
	/**
	 * Returns the text.
	 * @return A String value
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Returns the input from the TextField.
	 * @return A TextField object
	 */
	public TextField getInput() {
		return this.input;
	}
	
	/**
	 * Change the Stage for this class.
	 * @param s A reference to a Stage
	 */
	public void setStage(Stage s) {
		this.stage = s;
	}
	
	/**
	 * Returns the Stage of this class.
	 * @return A Stage object
	 */
	public Stage getStage() {
		return this.stage;
	}
	
	/**
	 * Change the container of this class.
	 * @param c A reference to a Table object
	 */
	public void setContainer(Table c) {
		this.container = c;
	}
	
	/**
	 * Returns the container of this class.
	 * @return A Table object
	 */
	public Table getContainer() {
		return this.container;
	}
	
	/**
	 * Change the mainTable of this class.
	 * @param mt A reference to a Table
	 */
	public void setMainTable(Table mt) {
		this.mainTable = mt;
	}
	
	/**
	 * Return the mainTable of this class.
	 * @return A Table object
	 */
	public Table getMainTable() {
		return this.mainTable;
	}
	
	/**
	 * Change the isOpen of this class. 
	 * @param o A reference to a boolean value
	 */
	public void setIsOpen(boolean o) {
		this.isOpen = o;
	}
	
	/**
	 * Return the isOpen boolean value.
	 * @return A boolean value
	 */
	public boolean getIsOpen() {
		return this.isOpen;
	}
}
