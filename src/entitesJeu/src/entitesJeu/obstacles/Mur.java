package entitesJeu.obstacles;

import entitesJeu.obstacles.Obstacle;

public class Mur extends Obstacle {
    public Mur(){}

    @Override
    public void destruction(){
        System.out.println("Mur ne peut pas etre détruit.");
    }
    @Override
    public void touche(int degats){
        System.out.println("Mur a bloqué le projectile.");
    }

    @Override
    public String toString(){
        return "Mur";
    }
}
