package Ghostman;

public class RedPac extends Pac {

	
	public RedPac(ObjectId id, int mapX, int mapY) {
		super(id, mapX, mapY);
	}
	@Override
	public void loadImages()
	{
		pacDown = "/images/redPacManDown.gif";
		pacDown2 = "/images/redPacManDown2.gif";
		pacLeft = "/images/redPacManLeft.gif";
		pacLeft2 = "/images/redPacManLeft2.gif";
		pacRight = "/images/redPacManRight.gif";
		pacUp = "/images/redPacManUp.gif";
		pacRight2 = "/images/redPacManRight2.gif";
		pacUp2 = "/images/redPacManUp2.gif";
	}
public void pacAI(Handler handler) {
		int[] xy=new int[2];
		if(Pac.enemyCount>1)
		{
		xy=getDifference(handler);
		chooseDirection(xy,handler);
		}
		else if(Pac.enemyCount==1)
		{
			pacCount=1;
			this.setId(GameObject.ObjectId.Pac);
			super.loadImages();
			super.pacAI(handler);
		}
}
public int[] getDifference(Handler handler)
{
	int[] xy=new int[2];
	for(GameObject obj:handler.object)
	{
		if(obj.getId()==GameObject.ObjectId.Ghost)
		{
			xy[0]=(obj.getListX()-this.getListX());
			xy[1]=(obj.getListY()-this.getListY());
		}
	}
	
	return xy;
}
public void chooseDirection(int[] xy,Handler handler)
{
	int x=Math.abs(xy[0]);
	int y=Math.abs(xy[1]);
	if(x>y&&xy[0]>0)
	{
		handler.moveObject(Direction.Right,this);
	}
	else if(x>y&&xy[0]<0)
	{
		handler.moveObject(Direction.Left,this);
	}
	else if(x<y&&xy[1]>0)
	{
		handler.moveObject(Direction.Down,this);
	}
	else if(x<y&&xy[1]<0)
	{
		handler.moveObject(Direction.Up,this);
	}
	else if(x==y&&xy[1]<0)
	{
		handler.moveObject(Direction.Up,this);
	}
	if(handler.object.removeIf(o -> o.getId() == GameObject.ObjectId.Ghost
			&& o.getListX() == this.getListX() && o.getListY() == this.getListY()))
	{
		Game.state=Game.GameState.GAMEOVER;
	}
}
}

