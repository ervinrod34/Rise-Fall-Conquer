package CustomWidgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.MyGdxGame;

public class WidgetResource {

	private ImageButton iImage;
	private Label lValue;
	private Label lValueIncrement;
	private Table tContainer;
	float totalPad = 20;
	
	public WidgetResource(String value) {
		tContainer = new Table();
		iImage = new ImageButton(MyGdxGame.MENUSKINHUD, value);
		lValue = new Label(String.valueOf(Integer.MAX_VALUE), MyGdxGame.MENUSKIN);
		lValueIncrement = new Label("(" + String.valueOf(Integer.MAX_VALUE) + ")", MyGdxGame.MENUSKIN);
		tContainer.add().width(5);
		tContainer.add(iImage);
		tContainer.add().width(5);
		tContainer.add(lValue);
		tContainer.add().width(5);
		tContainer.add(lValueIncrement);
		tContainer.add().width(5);
	}
	
	/**
	 * The main container for the widget
	 * @return
	 */
	public Table gettContainer(){
		return tContainer;	
	}
	
	/**
	 * Gets the size of the whole widget
	 * @return
	 */
	public float getCellSize(){
		return iImage.getWidth() + lValue.getWidth() + lValueIncrement.getWidth() + totalPad;
	}
	
	/**
	 * Set resource value
	 * @param value
	 */
	public void setValue(int value){
		lValue.setText(String.valueOf(value));
	}
	
	/**
	 * Sets the increment near the resource value
	 * @param value
	 */
	public void setValueIncrement(int value){
		if(value > 0){
			lValueIncrement.setColor(Color.GREEN);
			lValueIncrement.setText("(+" + String.valueOf(value) + ")");
		}else if(value < 0){
			value = Math.abs(value);
			lValueIncrement.setColor(Color.RED);
			lValueIncrement.setText("(-" + String.valueOf(value) + ")");
		}else{
			lValueIncrement.setColor(Color.YELLOW);
			lValueIncrement.setText("(" + String.valueOf(value) + ")");
		}
	}
	
	public void setResourceImageClickListener(ClickListener c){
		iImage.addListener(c);
	}
}
