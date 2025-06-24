package combatJeu.vue;

import combatJeu.modele.PlateauJeu;
import combatJeu.modele.carte.Carte;
import entitesJeu.obstacles.Obstacle;
import entitesJeu.obstacles.Mur;
import entitesJeu.obstacles.Eau;
import entitesJeu.obstacles.Combattant;
import entitesJeu.objets.Mine;
import entitesJeu.objets.MineProxy;
import entitesJeu.objets.Bombe;
import entitesJeu.objets.Objet;
import entitesJeu.objets.PastilleEnergetique;

public class TerminalVue {
	private PlateauJeu plateauJeu;
    private Carte carte;

    public TerminalVue(PlateauJeu plateauJeu, Carte carte){
    	this.plateauJeu = plateauJeu;
        this.carte = carte;
    }

    //affiche la carte avec objets et obstacles du combattant en prenant en compte les mines qu'il ne peut pas voir
    private String afficheVue(Combattant combattantVue) {
        StringBuilder res = new StringBuilder("VUE (" + combattantVue.getNom() + "):");

        for (int y = carte.getTailleY() - 1; y >= 0; y--) {
            res.append("\n");
            for (int x = 0; x < carte.getTailleX(); x++) {
                //OBSTACLE
                Obstacle obstacle = carte.getObstacle(x, y);
                if (obstacle != null){
                	res.append(getObstacleSymbol(obstacle, combattantVue));
                	continue;
                }

                //OBJET
                Objet objet = carte.getObjet(x, y);
                if (objet != null){
                	res.append(getObjetSymbol(objet, combattantVue));
                	continue;
                }

                res.append("  ");
            }
            res.append(" ").append(y);//COORD Y
        }

        res.append("\n");
        for (int x = 0; x < carte.getTailleX(); x++) {
            res.append(x).append(" ");//COORD X
        }
        res.append("\n");

        return res.toString();
    }

    //choix symbole d'obstacle
    private String getObstacleSymbol(Obstacle obstacle, Combattant combattantVue) {
        if (obstacle instanceof Mur) {
            return "⬜";
        } else if (obstacle instanceof Eau) {
            return "🟦";
        } else if (obstacle instanceof Combattant) {
            Combattant combattant = (Combattant) obstacle;
            if (combattant.equals(combattantVue)){
                return "🟩";//VERT SI JOUEUR COURANT SINON ROUGE
            }
            return "🟥";
        }
        return "ERROR";
    }
    //choix symbole d'objet
    private String getObjetSymbol(Objet objet, Combattant combattantVue) {
        if (objet instanceof Bombe) {
            Bombe bombe = (Bombe) objet;
            int tourRestant = bombe.getTourRestant();//DELAI DE LA BOMBE ET EMOJI EN LIEN
            if (tourRestant == 4){
                return "4️⃣ ";
            }
            if (tourRestant == 3){
                return "3️⃣ ";
            }
            if (tourRestant == 2){
                return "2️⃣ ";
            }
            if (tourRestant == 1){
                return "1️⃣ ";
            }
            return "  ";
        } else if (objet instanceof Mine) {
            //SI MINE VISIBLE
            Mine mine = (Mine) objet;
            MineProxy mineProxy = new MineProxy(mine, combattantVue);
            return mineProxy.peutAcceder(combattantVue) ? "⚫" : "  ";
        } else if (objet instanceof PastilleEnergetique) {
            //SI PASTILLE PRENABLE OU NON CHANGER APPARENCE
            PastilleEnergetique pastilleEnergetique = (PastilleEnergetique) objet;
            return pastilleEnergetique.estPrenable() ? "🟡" : "⚪";
        }
        return "ERROR";
    }

    @Override
    //permet l'affichage complet suivant le joueur courant
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("\n" + afficheVue(plateauJeu.getCombattantCourant()));
        return res.toString();
    }
}
