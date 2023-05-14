package com.mygdx.game;

import static com.mygdx.game.screens.util.ManagerScreens.Screens.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.screens.util.ManagerScreens;
import com.mygdx.game.util.HistoryScreenInformation;
import com.mygdx.game.util.SavedInformationSerializable;
import java.util.ArrayList;
import java.util.List;

public class MTGLifeCounter extends Game {

	public static List<HistoryScreenInformation> historyScreenInformationList;
	public static String savedCountLifePlayer1;
	public static String savedCountLifePlayer2;
	private static String initialLife="20";
	public Stage stage;

	public enum InitLife {
		TWENTY("20"), FORTY("40"), SIXTY("60");
		private final String initLife;
		InitLife(String initLife) {
			this.initLife = initLife;
		}

		@Override
		public String toString() {
			return initLife;
		}

		public static InitLife getInitLife(String init) {
			for (InitLife il : InitLife.values()) {
				if(il.toString().equals(init)) return il;
			}
			throw new RuntimeException("Don`t exists InitLife enum! (\""+init+"\")");
		}
	}

	public static InitLife getInitialLife() {
		return InitLife.getInitLife(initialLife);
	}

	public static void setInitialLife(InitLife init) {
		initialLife = init.toString();
		savedCountLifePlayer1 = init.toString();
		savedCountLifePlayer2 = init.toString();
	}

	@Override
	public void create () {

		// Loading resource
		ManagerResources.loadNewAssets();

		SavedInformationSerializable serializable = SavedInformationSerializable.read();
		if(serializable != null) {
			historyScreenInformationList = serializable.historyScreenInformationList;
			savedCountLifePlayer1 = serializable.getSavedCountLifePlayer1();
			savedCountLifePlayer2 = serializable.getSavedCountLifePlayer2();
			initialLife = serializable.getInitialLife();
		} else {
			historyScreenInformationList = new ArrayList<>();
			setInitialLife();
		}
		stage = new Stage(new ScreenViewport());

		this.setScreen(ManagerScreens.stepOnScreen(LOADING_SCREEN,this));
		//Gdx.input.setInputProcessor(stage);
	}

	public static void saveAppInfo() {
		SavedInformationSerializable serializable = new SavedInformationSerializable();
		serializable.setHistoryScreenInformationList(historyScreenInformationList);
		serializable.setSavedCountLifePlayer1(savedCountLifePlayer1);
		serializable.setSavedCountLifePlayer2(savedCountLifePlayer2);
		serializable.setInitialLife(initialLife);
		SavedInformationSerializable.save(serializable);
	}

	public static void clearHistory() {
		historyScreenInformationList.clear();
	}

	public static void setInitialLife() {
		savedCountLifePlayer1 = initialLife;
		savedCountLifePlayer2 = initialLife;
	}

	float time = 0f;
	public void render() {
		super.render();
	}

	public void clearStage() {
		for (Actor actor : stage.getActors()) actor.remove();
	}
}
