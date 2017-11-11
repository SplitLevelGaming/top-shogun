package com.splitlevelgaming.tinysheriff;

import com.badlogic.gdx.graphics.Texture;

public class Character_Test extends Character{

  Character_Test(Stage stage, int playerNumber, double x, double y){
    super(stage, playerNumber, x, y, 9.66, 21.33);
  }

  protected void update(){
    ControllerInputHandler playerController = getPlayerController();
    if(playerController.getVerticalAxis() > .5){
      x += playerController.getVerticalAxis();
    }
    if(playerController.getHorizontalAxis() > .5){
      x += playerController.getHorizontalAxis();
    }
  }

  protected void render(Pen pen){
    pen.draw(getTexture("img_sheriff.png"), x, y, width, height);
  }

}
