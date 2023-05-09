package com.mygdx.game;


import static com.badlogic.gdx.graphics.g3d.particles.ParticleChannels.Interpolation;
import static com.mygdx.game.ManagerResources.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.sql.rowset.JdbcRowSet;

public class Dice extends Actor {
    public static final float WIDTH_DICE = 350;
    public static final float HEIGHT_DICE = 350;
    private static final float WIDTH_POINT = 35;
    private static final float HEIGHT_POINT = 35;
    private static final float partY = HEIGHT_DICE / 100;
    private static final float partX = WIDTH_DICE / 100;
    private final Image[] points = new Image[6];
    private final Image diceImage;
    private static final float DURATION = 0.13f;
    private static final float DELAY_DURATION = 0.2f;
    private static final float DELAY_DURATION_LASTED = 2.2f;
    private static final List<Integer> draw = new Vector<>();

    private final PointsCoordinates pc;

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        diceImage.setPosition(x, y);
        for (Image point : points) {
            point.setPosition(point.getX() + x, point.getY() + y);
        }

    }

    public Dice(Stage stage) {
        Drawable dice_drawable = new Image(ManagerResources.getInstance().getResource(DICE)).getDrawable();
        Drawable dice_point_drawable = new Image(ManagerResources.getInstance().getResource(DICE_POINT)).getDrawable();

        diceImage = new Image(dice_drawable);
        diceImage.setPosition(this.getX(), this.getY());
        diceImage.setWidth(WIDTH_DICE);
        diceImage.setHeight(HEIGHT_DICE);
        stage.addActor(diceImage);
        for (int i = 0; i < points.length; i++) {
            points[i] = new Image(dice_point_drawable);
            stage.addActor(points[i]);
        }
        pc = new PointsCoordinates();
        diceImage.setVisible(false);
        setVisiblePoints(false);
        initPoints();
    }
    // diceImage y coordinates
    private float dIY() {
        return diceImage.getY();
    }
    // diceImage x coordinates
    private float dIX() {
        return diceImage.getX();
    }

    private class PointsCoordinates {
        // half point shift for center point
        private float hd() {
           return WIDTH_POINT / 2;
        }
        // 1 roll

        public Vector2 getP1() {
            Vector2 p1 = new Vector2(dIX() + (WIDTH_DICE / 2) - hd() , dIY() + (HEIGHT_DICE / 2) - hd());
            return p1;
        }
        // 2 roll
        public Vector2 getP2_01() {
            Vector2 p2_01 = new Vector2(dIX() + (WIDTH_DICE * 1/4) - hd() , dIY() + (HEIGHT_DICE / 2) - hd());
            return p2_01;
        }
        public Vector2 getP2_02() {
            Vector2 p2_02 = new Vector2(dIX() + (WIDTH_DICE * 3/4) - hd() , dIY() + (HEIGHT_DICE / 2) - hd());
            return p2_02;
        }
        // 3 roll
        public Vector2 getP3_01() {
            Vector2 p3_01 = new Vector2(dIX() + (WIDTH_DICE * 1/4) - hd() , dIY() + (HEIGHT_DICE * 1/4) - hd());
            return p3_01;
        }
        public Vector2 getP3_02() {
            Vector2 p3_02 = new Vector2(dIX() + (WIDTH_DICE / 2) - hd() , dIY() + (HEIGHT_DICE / 2) - hd());
            return p3_02;
        }
        public Vector2 getP3_03() {
            Vector2 p3_03 = new Vector2(dIX() + (WIDTH_DICE * 3/4) - hd() , dIY() + (HEIGHT_DICE * 3/4) - hd());
            return p3_03;
        }
        // 4 roll
        public Vector2 getP4_01() {
            Vector2 p4_01 = new Vector2(dIX() + (WIDTH_DICE * 1/4) - hd() , dIY() + (HEIGHT_DICE * 1/4) - hd());
            return p4_01;
        }
        public Vector2 getP4_02() {
            Vector2 p4_02 = new Vector2(dIX() + (WIDTH_DICE * 3/4) - hd() , dIY() + (HEIGHT_DICE * 3/4) - hd());
            return p4_02;
        }
        public Vector2 getP4_03() {
            Vector2 p4_03 = new Vector2(dIX() + (WIDTH_DICE * 1/4) - hd() , dIY() + (HEIGHT_DICE * 3/4) - hd());
            return p4_03;
        }
        public Vector2 getP4_04() {
            Vector2 p4_04 = new Vector2(dIX() + (WIDTH_DICE * 3/4) - hd() , dIY() + (HEIGHT_DICE * 1/4) - hd());
            return p4_04;
        }
        // 5 roll
        public Vector2 getP5_01() {
            Vector2 p5_01 = new Vector2(dIX() + (WIDTH_DICE * 1/4) - hd() , dIY() + (HEIGHT_DICE * 1/4) - hd());
            return p5_01;
        }
        public Vector2 getP5_02() {
            Vector2 p5_02 = new Vector2(dIX() + (WIDTH_DICE * 3/4) - hd() , dIY() + (HEIGHT_DICE * 3/4) - hd());
            return p5_02;
        }
        public Vector2 getP5_03() {
            Vector2 p5_03 = new Vector2(dIX() + (WIDTH_DICE / 2) - hd() , dIY() + (HEIGHT_DICE / 2) - hd());
            return p5_03;
        }
        public Vector2 getP5_04() {
            Vector2 p5_04 = new Vector2(dIX() + (WIDTH_DICE * 1/4) - hd() , dIY() + (HEIGHT_DICE * 3/4) - hd());
            return p5_04;
        }
        public Vector2 getP5_05() {
            Vector2 p5_05 = new Vector2(dIX() + (WIDTH_DICE * 3/4) - hd() , dIY() + (HEIGHT_DICE * 1/4) - hd());
            return p5_05;
        }
        // 6 roll
        public Vector2 getP6_01() {
            Vector2 p6_01 = new Vector2(dIX() + (WIDTH_DICE * 1/4) - hd() , dIY() + (HEIGHT_DICE * 1/4) - hd());
            return p6_01;
        }
        public Vector2 getP6_02() {
            Vector2 p6_02 = new Vector2(dIX() + (WIDTH_DICE * 3/4) - hd() , dIY() + (HEIGHT_DICE * 3/4) - hd());
            return p6_02;
        }
        public Vector2 getP6_03() {
            Vector2 p6_03 = new Vector2(dIX() + (WIDTH_DICE * 1/4) - hd() , dIY() + (HEIGHT_DICE / 2) - hd());
            return p6_03;
        }
        public Vector2 getP6_04() {
            Vector2 p6_04 = new Vector2(dIX() + (WIDTH_DICE * 3/4) - hd() , dIY() + (HEIGHT_DICE / 2) - hd());
            return p6_04;
        }
        public Vector2 getP6_05() {
            Vector2 p6_05 = new Vector2(dIX() + (WIDTH_DICE * 1/4) - hd() , dIY() + (HEIGHT_DICE * 3/4) - hd());
            return p6_05;
        }
        public Vector2 getP6_06() {
            Vector2 p6_06 = new Vector2(dIX() + (WIDTH_DICE * 3/4) - hd() , dIY() + (HEIGHT_DICE * 1/4) - hd());
            return p6_06;
        }
    }
    MoveToAction[] animateTo_1() {
        MoveToAction[] mtc = new MoveToAction[6];
        mtc[0] = Actions.moveTo(pc.getP1().x, pc.getP1().y, DURATION);
        mtc[1] = Actions.moveTo(pc.getP1().x, pc.getP1().y, DURATION);
        mtc[2] = Actions.moveTo(pc.getP1().x, pc.getP1().y, DURATION);
        mtc[3] = Actions.moveTo(pc.getP1().x, pc.getP1().y, DURATION);
        mtc[4] = Actions.moveTo(pc.getP1().x, pc.getP1().y, DURATION);
        mtc[5] = Actions.moveTo(pc.getP1().x, pc.getP1().y, DURATION);
        return mtc;
    }

    MoveToAction[] animateTo_2() {
        MoveToAction[] mtc = new MoveToAction[6];
        mtc[0] = Actions.moveTo(pc.getP2_01().x, pc.getP2_01().y, DURATION);
        mtc[1] = Actions.moveTo(pc.getP2_02().x, pc.getP2_02().y, DURATION);
        mtc[2] = Actions.moveTo(pc.getP2_01().x, pc.getP2_01().y, DURATION);
        mtc[3] = Actions.moveTo(pc.getP2_02().x, pc.getP2_02().y, DURATION);
        mtc[4] = Actions.moveTo(pc.getP2_01().x, pc.getP2_01().y, DURATION);
        mtc[5] = Actions.moveTo(pc.getP2_02().x, pc.getP2_02().y, DURATION);
        return mtc;
    }
    MoveToAction[] animateTo_3() {
        MoveToAction[] mtc = new MoveToAction[6];
        mtc[0] = Actions.moveTo(pc.getP3_01().x, pc.getP3_01().y, DURATION);
        mtc[1] = Actions.moveTo(pc.getP3_02().x, pc.getP3_02().y, DURATION);
        mtc[2] = Actions.moveTo(pc.getP3_03().x, pc.getP3_03().y, DURATION);
        mtc[3] = Actions.moveTo(pc.getP3_01().x, pc.getP3_01().y, DURATION);
        mtc[4] = Actions.moveTo(pc.getP3_02().x, pc.getP3_02().y, DURATION);
        mtc[5] = Actions.moveTo(pc.getP3_03().x, pc.getP3_03().y, DURATION);
        return mtc;
    }
    MoveToAction[] animateTo_4() {
        MoveToAction[] mtc = new MoveToAction[6];
        mtc[0] = Actions.moveTo(pc.getP4_01().x, pc.getP4_01().y, DURATION);
        mtc[1] = Actions.moveTo(pc.getP4_01().x, pc.getP4_01().y, DURATION);
        mtc[2] = Actions.moveTo(pc.getP4_02().x, pc.getP4_02().y, DURATION);
        mtc[3] = Actions.moveTo(pc.getP4_03().x, pc.getP4_03().y, DURATION);
        mtc[4] = Actions.moveTo(pc.getP4_04().x, pc.getP4_04().y, DURATION);
        mtc[5] = Actions.moveTo(pc.getP4_04().x, pc.getP4_04().y, DURATION);
        return mtc;
    }
    MoveToAction[] animateTo_5() {
        MoveToAction[] mtc = new MoveToAction[6];
        mtc[0] = Actions.moveTo(pc.getP5_01().x, pc.getP5_01().y, DURATION);
        mtc[1] = Actions.moveTo(pc.getP5_01().x, pc.getP5_01().y, DURATION);
        mtc[2] = Actions.moveTo(pc.getP5_02().x, pc.getP5_02().y, DURATION);
        mtc[3] = Actions.moveTo(pc.getP5_03().x, pc.getP5_03().y, DURATION);
        mtc[4] = Actions.moveTo(pc.getP5_04().x, pc.getP5_04().y, DURATION);
        mtc[5] = Actions.moveTo(pc.getP5_05().x, pc.getP5_05().y, DURATION);
        return mtc;
    }
    MoveToAction[] animateTo_6() {
        MoveToAction[] mtc = new MoveToAction[6];
        mtc[0] = Actions.moveTo(pc.getP6_01().x, pc.getP6_01().y, DURATION);
        mtc[1] = Actions.moveTo(pc.getP6_02().x, pc.getP6_02().y, DURATION);
        mtc[2] = Actions.moveTo(pc.getP6_03().x, pc.getP6_03().y, DURATION);
        mtc[3] = Actions.moveTo(pc.getP6_04().x, pc.getP6_04().y, DURATION);
        mtc[4] = Actions.moveTo(pc.getP6_05().x, pc.getP6_05().y, DURATION);
        mtc[5] = Actions.moveTo(pc.getP6_06().x, pc.getP6_06().y, DURATION);
        return mtc;
    }
    List<Integer> getAnimRandomIndex1_6() {
        List<Integer> list = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5));
        Collections.shuffle(list);
        return list;
    }
    List<Integer> getAnimRandomIndex1_36() {
        List<Integer> list = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5));
        Collections.shuffle(list);
        if(draw.size() >= 2) draw.clear();
        if(!draw.contains(getLast(list))) draw.add(getLast(list));
        else {
            draw.clear();
            Collections.shuffle(list);
        }
        return list;
    }
    public static <T> T getLast(List<T> list) {
        return list != null && !list.isEmpty() ? list.get(list.size() - 1) : null;
    }

    void initPoints() {
        MoveToAction[] mta = getMoveToActionByIndex(getAnimRandomIndex1_6().get(0));

        points[0].setPosition( mta[0].getX() ,  mta[0].getY() );
        points[1].setPosition( mta[1].getX() ,  mta[1].getY() );
        points[2].setPosition( mta[2].getX() ,  mta[2].getY() );
        points[3].setPosition( mta[3].getX() ,  mta[3].getY() );
        points[4].setPosition( mta[4].getX() ,  mta[4].getY() );
        points[5].setPosition( mta[5].getX() ,  mta[5].getY() );
    }

    void rollTheDice() {
        diceImage.setVisible(true);
        setVisiblePoints(true);
        initPoints();

        List<MoveToAction[]> listMTA = listRandomActions(getAnimRandomIndex1_36());
        RunnableAction run = new RunnableAction();
        run.setRunnable(new Runnable() {
            @Override
            public void run() {
                //Only last action
                if(points[5].getActions().size == 1) {
                    diceImage.setVisible(false);
                    setVisiblePoints(false);
                }
            }
        });
        points[0].addAction(Actions.sequence( listMTA.get(0)[0] ,da(), listMTA.get(1)[0] ,da(), listMTA.get(2)[0] ,da(), listMTA.get(3)[0] ,da(), listMTA.get(4)[0] ,da(), listMTA.get(5)[0], da_lasted()) );
        points[1].addAction(Actions.sequence( listMTA.get(0)[1] ,da(), listMTA.get(1)[1] ,da(), listMTA.get(2)[1] ,da(), listMTA.get(3)[1] ,da(), listMTA.get(4)[1] ,da(), listMTA.get(5)[1], da_lasted()) );
        points[2].addAction(Actions.sequence( listMTA.get(0)[2] ,da(), listMTA.get(1)[2] ,da(), listMTA.get(2)[2] ,da(), listMTA.get(3)[2] ,da(), listMTA.get(4)[2] ,da(), listMTA.get(5)[2], da_lasted()) );
        points[3].addAction(Actions.sequence( listMTA.get(0)[3] ,da(), listMTA.get(1)[3] ,da(), listMTA.get(2)[3] ,da(), listMTA.get(3)[3] ,da(), listMTA.get(4)[3] ,da(), listMTA.get(5)[3], da_lasted()) );
        points[4].addAction(Actions.sequence( listMTA.get(0)[4] ,da(), listMTA.get(1)[4] ,da(), listMTA.get(2)[4] ,da(), listMTA.get(3)[4] ,da(), listMTA.get(4)[4] ,da(), listMTA.get(5)[4], da_lasted()) );
        points[5].addAction(Actions.sequence( listMTA.get(0)[5] ,da(), listMTA.get(1)[5] ,da(), listMTA.get(2)[5] ,da(), listMTA.get(3)[5] ,da(), listMTA.get(4)[5] ,da(), listMTA.get(5)[5], da_lasted(), run ));
    }

    private void setVisiblePoints(boolean b) {
        for (Image point : points) {
            point.setVisible(b);
        }
    }

    DelayAction da() {return Actions.delay(DELAY_DURATION); }
    DelayAction da_lasted() {return Actions.delay(DELAY_DURATION_LASTED); }

    List<MoveToAction[]> listRandomActions(List<Integer> listRandomInt) {
        List<MoveToAction[]> ret = new ArrayList<>();
        for (Integer i : listRandomInt) {
            MoveToAction[] mta = getMoveToActionByIndex(i);
            ret.add(mta);
        }
        return ret;
    }

    MoveToAction[] getMoveToActionByIndex(Integer integer) {
        switch (integer) {
            case 0: return animateTo_1();
            case 1: return animateTo_2();
            case 2: return animateTo_3();
            case 3: return animateTo_4();
            case 4: return animateTo_5();
            case 5: return animateTo_6();
            default:
                throw new IllegalStateException("Not exist anim for value: " + integer);
        }
    }
}
