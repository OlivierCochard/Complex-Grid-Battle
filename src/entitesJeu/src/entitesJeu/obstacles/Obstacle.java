package entitesJeu.obstacles;

import entitesJeu.Entite;

/* classe non traversable par les entités mais traversable par les tirs */
public abstract class Obstacle extends Entite {
    public abstract void touche(int degats);
}
