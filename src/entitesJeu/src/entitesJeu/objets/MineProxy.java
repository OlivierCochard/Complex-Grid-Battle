package entitesJeu.objets;

import entitesJeu.obstacles.Combattant;

public class MineProxy{
    private final Mine mine;
    private final Combattant combattant;

    public MineProxy(Mine mine, Combattant combattant) {
        this.mine = mine;
        this.combattant = combattant;
    }

    public boolean peutAcceder(Combattant combattant) {
        return combattant == mine.getPoseur();
    }

    //permet d'acceder aux degats de la mine a condition que le combattant soit le poseur 
    public int getDegats() {
        if (peutAcceder(combattant)) {
            return mine.getDegats();
        }
        throw new UnsupportedOperationException("Accès non autorisé aux dégâts.");
    }

    //permet de déclancher l'explosion de la mine a condition que le combattant soit le poseur 
    public void explosion() {
        if (peutAcceder(combattant)) {
            mine.explosion();
        } else {
            throw new UnsupportedOperationException("Accès non autorisé pour l'explosion.");
        }
    }

    //permet de déclancher la destruction de la mine a condition que le combattant soit le poseur 
    public void destruction() {
        if (peutAcceder(combattant)) {
            mine.destruction();
        } else {
            throw new UnsupportedOperationException("Accès non autorisé pour la destruction.");
        }
    }

    @Override
    public String toString() {
        return mine.toString();
    }
}
