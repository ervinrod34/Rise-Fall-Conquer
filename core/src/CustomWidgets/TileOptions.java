package CustomWidgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.MyGdxGame;

import map.Tile;

public class TileOptions {
	
	private Table tOptions;
	private Label resName, tileName, desc;
	private TextButton exit, upgrade;
	private Table container;
	private Tile tile;
	
	public TileOptions(Tile t){
		
		tile = t;
		container = new Table();
		container.setFillParent(true);
		
		Label background = new Label("", MyGdxGame.MENUSKINHUD);
		try{
			resName = new Label(tile.getResource().name() + " ",MyGdxGame.MENUSKIN);
			container.add(resName);
			container.row();
		} catch(NullPointerException e){}
		
		try{
			tileName = new Label(tile.getTileId().name() + " ",MyGdxGame.MENUSKIN);
		} catch(NullPointerException e) {}
		
		desc = new Label("A tile description",MyGdxGame.MENUSKIN);
		
		exit = new TextButton("EXIT",MyGdxGame.MENUSKIN);
		upgrade = new TextButton("UPGRADE",MyGdxGame.MENUSKIN);
	
		container.add(tileName);
		container.row();
		container.add(desc);
		container.row();
		container.add(upgrade);
		container.row();
		container.add(exit);
		container.row();
		
		tOptions = new Table();
		tOptions.stack(background,container);
		tOptions.setFillParent(true);
		tOptions.top().left();
		this.setExitListener();
		this.setUpgradeListener();
	}
	
	public Table getTable()
	{
		return this.container;
	}
	
	public void setExitListener(){
		exit.addListener(new ClickListener() {
			public void clicked(InputEvent e,float x,float y){
				tOptions.remove();
				container.remove();
				container.invalidateHierarchy();
			}
		});
	}
	
	public void setUpgradeListener(){
		upgrade.addListener(new ClickListener() {
			public void clicked(InputEvent e,float x,float y){
				tile.getResource().upgradeTile();
				//Gdx.app.log(this.getClass().getName(),"IM SO HAPPY RIGHT NOW, I'M BEING UPGRADED");
			}
		});
	}
}
