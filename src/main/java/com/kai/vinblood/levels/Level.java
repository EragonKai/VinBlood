package com.kai.vinblood.levels;

import com.kai.vinblood.core.Updatable;
import com.kai.vinblood.objs.ObjectController;
import com.kai.vinblood.objs.entities.enemies.load.EnemyLoader;
import com.kai.vinblood.util.ID;

import java.util.List;

/**
 * @author Kai on Mar 21, 2019
 */
public class Level implements Updatable {
    private boolean over = false;
    private int levelNum;
    private int existingEnemies;
    private List<ID> enemiesToGenerate;

    public Level(int levelNum) {
        this.levelNum = levelNum;
        enemiesToGenerate = LevelGen.generateEnemies(levelNum);
        existingEnemies = enemiesToGenerate.size();
        generate();
    }

    public void enemyDied() {
        existingEnemies--;
    }

    @Override
    public void update() {
        //Temporary:
        if (existingEnemies == 0) {
            setOver(true);
        }
    }

    private void generate() {
        for (ID enemyID: enemiesToGenerate) {
            EnemyLoader.createEnemy(enemyID, LevelGen.getBoundsAwayFromPlayer());
        }
    }



    public boolean isOver() { return over; }
    public void setOver(boolean over) { this.over = over; }
}
