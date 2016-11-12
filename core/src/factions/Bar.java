package factions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import javafx.util.Pair;

public class Bar {

	protected static final float BAR_HEIGHT = 3;
	protected static final float BAR_WIDTH = 40;
	
	private float maxValue, value;
	private Pair<Rectangle, Rectangle> rectanglePair;
	private Pair<Color,Color> colorPair;
	
	public Bar(float value, float maxValue) {
		this.setMaxValue(maxValue);
		this.setValue(value);
		colorPair = new Pair<Color,Color>(Color.GREEN, Color.RED);
		rectanglePair = new Pair<Rectangle, Rectangle>(new Rectangle(0,0,BAR_WIDTH, BAR_HEIGHT), new Rectangle(0,0,BAR_WIDTH, BAR_HEIGHT));
	}

	public void draw(ShapeRenderer rend){
		rend.setColor(colorPair.getValue());
		rend.rect(rectanglePair.getValue().x, rectanglePair.getValue().y, rectanglePair.getValue().width, rectanglePair.getValue().height);
		float sub = ((maxValue - value)/maxValue)*rectanglePair.getValue().width;
		rend.setColor(colorPair.getKey());
		rend.rect(rectanglePair.getKey().x, rectanglePair.getKey().y, rectanglePair.getKey().width - sub, rectanglePair.getKey().height);
	}

	public float getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
	
	/**
	 * Sets the bars location from center
	 * @param x
	 * @param y
	 */
	public void setLocation(float x, float y){
		rectanglePair.getKey().setCenter(x, y);
		rectanglePair.getValue().setCenter(x, y);
	}
}
