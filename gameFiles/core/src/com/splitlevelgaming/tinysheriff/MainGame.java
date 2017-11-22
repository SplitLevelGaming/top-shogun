package com.splitlevelgaming.tinysheriff;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import java.util.Hashtable;
import java.util.ArrayList;

public class MainGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private BitmapFont font;
	private double pixelsPerBottomBlockside;
	private double pixelsPerSideBlockside;
	private Stage activeStage;
	private AssetVault <Texture> textureVault;
	private ControllerInputHandler[] controllers;
	private boolean controllersHookedUp = true;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();

		//Build Texture Vault
		ArrayList<String> textureExtensions = new ArrayList<String>();
		textureExtensions.add(".png");
		textureExtensions.add(".jpg");
		TextureFactory textureFactory = new TextureFactory();
		textureVault = new AssetVault<Texture>(textureExtensions, textureFactory);

		activeStage = new Stage_Test(this);
		controllers = new ControllerInputHandler[2];
		try{
			controllers[0] = new ControllerInputHandler(Controllers.getControllers().get(0));
			controllers[1] = new ControllerInputHandler(Controllers.getControllers().get(1));
		}
		catch (Exception e) {
			System.out.println("Please connect two controllers!");
			controllersHookedUp = false;
			//Gdx.app.exit();
		}
	}

	@Override
	public void render () {
		//Set up screen view
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		pixelsPerBottomBlockside = screenWidth / 32;
		pixelsPerSideBlockside = screenHeight / 18;
		OrthographicCamera orthoCam = new OrthographicCamera(screenWidth, screenHeight);
		orthoCam.position.set(screenWidth / 2, screenHeight / 2, 0);
		orthoCam.update();
		//Prepare the batch
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setProjectionMatrix(orthoCam.combined);
		Pen pen = new Pen(batch, pixelsPerBottomBlockside, pixelsPerSideBlockside, screenWidth, screenHeight);
		//Update the controller's inputs
		if(controllersHookedUp)
			for(int i = 0; i < controllers.length; i++){
				controllers[i].refresh();
			}
		//Tell stage to begin the step
		activeStage.activate(pen);
		//The following lines are here for testing purposes. They should not be uncommented in any PR.
		//font.draw(batch, screenWidth + ", " + screenHeight, 0, 15);
		//font.draw(batch, Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() + "", 0, 30);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		textureVault.dispose();
	}

	public void enterNewStage(Stage newStage){
		activeStage = newStage;
	}

	public Texture getTexture(String textureName){
		return textureVault.getAsset(textureName);
	}

	public ControllerInputHandler[] getControllers(){
		return controllers;
	}
}
