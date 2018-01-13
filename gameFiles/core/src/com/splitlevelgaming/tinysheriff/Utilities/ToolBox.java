package com.splitlevelgaming.tinysheriff;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;

public class ToolBox implements PhysicalReferencer{

  private TextureHandler textureHandler;
  private MusicHandler musicHander;
  private SoundHandler soundHandler;
  private TimerController timerController;
  private InputHandler[] controllers;

  public ToolBox(){
    musicHander = new MusicHandler();
    textureHandler = new TextureHandler();
    soundHandler = new SoundHandler();
    timerController = new TimerController();
    controllers = new InputHandler[2];
		try{
			controllers[0] = new ControllerInputHandler(Controllers.getControllers().get(0));
			controllers[1] = new ControllerInputHandler(Controllers.getControllers().get(1));
		}
		catch (Exception e) {
			System.out.println("Please connect two controllers!");
      controllers[0] = new KeyboardInputHandler();
			controllers[1] = new KeyboardInputHandler();
    }
  }

  public void startStep(){
    textureHandler.startStep();
    timerController.startStep();
  }

  public void endStep(){
    textureHandler.endStep();
    timerController.startStep();
  }

  public void dispose(){
    textureHandler.dispose();
    musicHander.dispose();
    soundHandler.dispose();
  }

  public void removePhysicalReferences(Physical physical){
    timerController.removePhysicalReferences(physical);
  }

  public TextureHandler getTextureHandler(){
    return textureHandler;
  }

  public MusicHandler getMusicHandler(){
    return musicHander;
  }

  public SoundHandler getSoundHandler(){
    return soundHandler;
  }

  public TimerController getTimerController(){
    return timerController;
  }

  public InputHandler[] getControllers(){
      return controllers;
	}

}
