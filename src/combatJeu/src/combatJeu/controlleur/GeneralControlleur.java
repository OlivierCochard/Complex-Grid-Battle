package combatJeu.controlleur;

import combatJeu.GameSettings;
import combatJeu.modele.PlateauJeu;
import entitesJeu.obstacles.Combattant;

import java.util.*;

//permet au joueur courant d'effectuer des actions à partir du terminal et de l'interface graphique
public class GeneralControlleur {
	private PlateauJeu plateauJeu;

	public GeneralControlleur(PlateauJeu plateauJeu){
		this.plateauJeu = plateauJeu;
	}

	public void appliquerAction(List<String> listeAction, Combattant combattantCourant){
        //PASSER
        if (listeAction.get(0).equals("passer")){
            System.out.println(combattantCourant.getNom() + " a passé son tour");
            plateauJeu.changementCombattantCourant();
            return;
        }
        //BOUCLIER
        if (listeAction.get(0).equals("bouclier")){
            combattantCourant.bouclier(GameSettings.ENERGIE_BOUCLIER);
            return;
        }        
        //MARCHER
        if (listeAction.get(0).equals("marcher")){
            if (listeAction.get(1).equals("haut") || listeAction.get(1).equals("droite") || listeAction.get(1).equals("bas") || listeAction.get(1).equals("gauche")){
                combattantCourant.deplacement(listeAction.get(1), GameSettings.ENERGIE_DEPLACEMENT);
                return;
            }
            System.out.println("Direction non reconnue : " + listeAction.get(1) + "\n");
            return;
        }
        //TIRER
        if (listeAction.get(0).equals("tirer")){
            if (listeAction.get(1).equals("verticale") || listeAction.get(1).equals("horizontale")){
                combattantCourant.projectile(listeAction.get(1), GameSettings.ENERGIE_PROJECTILE);
                return;
            }
            System.out.println("Direction non reconnue : " + listeAction.get(1) + "\n");
            return;
        }
        //PIEGER
        if (listeAction.get(0).equals("pieger")){
            if (listeAction.get(1).equals("mine") || listeAction.get(1).equals("bombe")){
                int x = Integer.parseInt(listeAction.get(2));
                int y = Integer.parseInt(listeAction.get(3));

                if (x == 0 && y == 0){
                    System.out.println("Impossible de poser la bombe sous vous !");
                    return;
                }

                combattantCourant.pieger(listeAction.get(1), x, y, GameSettings.ENERGIE_PIEGE);
                return;
            }
            System.out.println("Type de piège non reconnu : " + listeAction.get(1) + "\n");
            return;
        }
    }
}