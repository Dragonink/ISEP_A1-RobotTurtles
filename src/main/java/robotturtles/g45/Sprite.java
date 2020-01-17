package robotturtles.g45;

public interface Sprite extends Drawable{
    public static String SPRITE_PATH = "/images/";

    public default String getSprite(){
     return "";
    }

    public default void draw(String image){

    }
}
