package com.mygdx.game.util;

import com.mygdx.game.Players;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class HistoryScreenInformation implements Serializable {
    private Players player;
    private String messageCountLife;
    private String dateTimeString;

    public Players getPlayer() {
        return player;
    }

    public void setPlayer(Players players) {
        this.player = player;
    }

    public String getMessageCountLife() {
        return messageCountLife;
    }

    public void setMessageCountLife(String messageCountLife) {
        this.messageCountLife = messageCountLife;
    }

    public HistoryScreenInformation(Players players, String messageCountLife) {
        this.player = players;
        this.messageCountLife = messageCountLife;
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss",Locale.getDefault());
        sdf.setTimeZone(TimeZone.getDefault());

        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        this.dateTimeString = sdf.format(new Date());

    }
}
