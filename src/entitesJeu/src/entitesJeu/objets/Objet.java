package entitesJeu.objets;

import entitesJeu.Entite;

//traversable par les combattants et par les tirs
public abstract class Objet extends Entite {
    public abstract void explosion();
}