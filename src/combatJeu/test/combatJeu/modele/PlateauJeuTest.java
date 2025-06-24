package combatJeu.modele;

import combatJeu.modele.carte.Carte;
import entitesJeu.obstacles.Combattant;
import entitesJeu.obstacles.Mur;
import entitesJeu.obstacles.Eau;
import entitesJeu.Entite;
import combatJeu.modele.carte.strategie.CarteGenerationStrategie;
import combatJeu.modele.carte.strategie.EquilibreCarteGenerationStrategie;
import entitesJeu.objets.PastilleEnergetique;

import java.util.*;

public class PlateauJeuTest {
    public static void main(String[] args) {
        PlateauJeu plateauJeu = new PlateauJeu(); // Vous devez implémenter cette classe ou utiliser un mock
        CarteGenerationStrategie carteGenerationStrategie = new EquilibreCarteGenerationStrategie();
        Carte carte = carteGenerationStrategie.genererCarte(10, 2, plateauJeu);
        List<String> listeTypeVraiJoueur = new ArrayList<String>();
        List<String> listeNomVraiJoueur = new ArrayList<String>();
        Combattant combattant1 = new Combattant("Guerrier", 100, 10, 2, 3);
        Combattant combattant2 = new Combattant("Mage", 80, 15, 3, 4);
        carte.addCombattant(combattant1);
        carte.addCombattant(combattant2);
        plateauJeu.setCarte(carte);
        PastilleEnergetique pastille = new PastilleEnergetique(50, 0, 0);

        carte.setObstacle(2, 3, combattant1);
        carte.setObstacle(7, 8, combattant2);
        carte.setObjet(5, 5, pastille);



        // Test: obtenir la carte
        assert plateauJeu.getCarte() == carte : "La carte devrait être celle initialisée";

        // Test: calculer une distance
        Coordonnee coord1 = new Coordonnee(2, 3);
        Coordonnee coord2 = new Coordonnee(7, 8);
        assert plateauJeu.calculeDistance(coord1, coord2) == 10 : "Distance incorrecte";

        // Test: trouver une entité proche
        Entite entiteProche = plateauJeu.getEntiteProche(new Coordonnee(6, 6));
        assert entiteProche == pastille : "La pastille devrait être l'entité la plus proche";

        // Test: obtenir les coordonnées d'une entité
        Coordonnee coordPastille = plateauJeu.getCoordonneeEntite(pastille);
        assert coordPastille.equals(new Coordonnee(5, 5)) : "Les coordonnées de la pastille sont incorrectes";

        // Test: déplacement
        assert plateauJeu.demandeDeplacement("droite") : "Le déplacement devrait être possible";
        plateauJeu.appliqueDeplacement("droite");
        assert plateauJeu.getCoordonneeCombattant(combattant1).equals(new Coordonnee(3, 3)) : "Le combattant aurait dû se déplacer";

        // Test: poser un objet
        assert plateauJeu.demandePoseObjet(0, 1) : "Le piège devrait pouvoir être posé ici";
        plateauJeu.appliquePoseObjet(0, 1, new PastilleEnergetique(20, 0, 0));

        // Test: obstacles autour d'une coordonnée
        carte.setObstacle(3, 3, new Mur());
        assert plateauJeu.getObstaclesAutourCoordonnee(new Coordonnee(3, 3)).size() == 1 : "Il devrait y avoir un obstacle (mur)";

        // Test: changement de combattant
        plateauJeu.changementCombattantCourant();
        assert plateauJeu.getCombattantCourant() == combattant2 : "Le combattant courant aurait dû changer";

        System.out.println("Tous les tests ont réussi !");
    }
}
