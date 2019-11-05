package Ghostman;
/**
 * This class is a abstract class that implements fields that belong to all the other objects.listX and listY are the positions in the 2d matrix.
 * x and y is the pixel position.Since each cell in the matrix is 24 pixels,to get the x and y pixel position we just multiple by 24
 * the listX and listY coordinates.
 * 
 */
import java.awt.Graphics;

public abstract class GameObject {
	
enum ObjectId {
	Dot,Pac,Ghost,Block,RedPac
}

protected int x,y;//pixel position
protected ObjectId id;
protected int listX,listY;//2d matrix coordinates

public GameObject(ObjectId id,int listX,int listY)
{
	this.id=id;
	this.x=listX*Game.BLOCK_SIZE;
	this.y=listY*Game.BLOCK_SIZE;
	this.listX=listX;
	this.listY=listY;
}
public abstract void render(Graphics g);
public int getX()
{
	return x;
}
public int getY() {
	return y;
}
public void setX(int x)
{
	this.x=x;
}
public void setY(int y)
{
	this.y=y;
}

public int getListX()
{
	return this.listX;
}
public int getListY()
{
	return this.listY;
}
public  void setListX(int l)
{
	this.x=l*Game.BLOCK_SIZE;
	this.listX=l;
}
public  void setListY(int l)
{
	this.y=l*Game.BLOCK_SIZE;
	this.listY=l;
}

public ObjectId getId()
{
	return id;
}
public void setId(GameObject.ObjectId id)
{
	this.id=id;
}
}
