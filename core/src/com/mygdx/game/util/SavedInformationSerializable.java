package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import jdk.nashorn.internal.runtime.Context;

public class SavedInformationSerializable implements Serializable {

    public List<HistoryScreenInformation> historyScreenInformationList;
    public String savedCountLifePlayer1;
    public String savedCountLifePlayer2;
    public String initialLife="20";

    public SavedInformationSerializable() {}

    public String getSavedCountLifePlayer1() {
        return savedCountLifePlayer1;
    }

    public void setSavedCountLifePlayer1(String savedCountLifePlayer1) {
        this.savedCountLifePlayer1 = savedCountLifePlayer1;
    }

    public String getSavedCountLifePlayer2() {
        return savedCountLifePlayer2;
    }

    public void setSavedCountLifePlayer2(String savedCountLifePlayer2) {
        this.savedCountLifePlayer2 = savedCountLifePlayer2;
    }

    public String getInitialLife() {
        return initialLife;
    }

    public void setInitialLife(String initialLife) {
        this.initialLife = initialLife;
    }

    public List<HistoryScreenInformation> getHistoryScreenInformationList() {
        return historyScreenInformationList;
    }

    public void setHistoryScreenInformationList(List<HistoryScreenInformation> historyScreenInformationList) {
        this.historyScreenInformationList = historyScreenInformationList;
    }
    private transient static final String FILE_NAME = "SavedInformationSerializable.serialized";
    public static void save(SavedInformationSerializable informationSerializable) {

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/data/data/com.mygdx.game",FILE_NAME)))) {
            oos.writeObject(informationSerializable);
        } catch(Exception ex){ Gdx.app.error("<<SAVE_>>", ex.getMessage(), ex); }
    }

    public static SavedInformationSerializable read() {
        SavedInformationSerializable informationSerializable=null;
        File fileForRead = new File("/data/data/com.mygdx.game",FILE_NAME);

        if(fileForRead.exists() && !fileForRead.isDirectory()) {
            try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream( fileForRead ))) {
                informationSerializable = ((SavedInformationSerializable)ois.readObject());
            } catch(Exception ex){ Gdx.app.error("<<READ_>>", ex.getMessage(), ex); }
        }
        return informationSerializable;
    }


}
