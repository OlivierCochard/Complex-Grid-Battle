package entitesJeu.obstacles;

import entitesJeu.obstacles.Obstacle;

public class Eau extends Obstacle {
    public Eau(){}

    @Override
    public void destruction(){
        System.out.println("Eau ne peut pas etre détruit.");
    }
    @Override
    public void touche(int degats){
        System.out.println("Eau a laissé passer le projectile.");
    }

    @Override
    public String toString(){
        return "Eau";
    }
}
