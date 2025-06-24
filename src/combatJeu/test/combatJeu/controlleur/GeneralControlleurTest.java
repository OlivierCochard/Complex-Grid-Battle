package combatJeu.controlleur;

import combatJeu.controlleur.GeneralControlleur;
import combatJeu.modele.PlateauJeu;
import combatJeu.modele.Coordonnee;
import combatJeu.modele.carte.Carte;
import combatJeu.modele.carte.strategie.CarteGenerationStrategie;
import combatJeu.modele.carte.strategie.EquilibreCarteGenerationStrategie;
import entitesJeu.obstacles.Combattant;

import java.util.*;

public class GeneralControlleurTest {

    public static void main(String[] args) {
        // Création de mock objects (PlateauJeu et Combattant)
        PlateauJeu plateauJeu = new PlateauJeu(); // Vous devez implémenter cette classe ou utiliser un mock
        CarteGenerationStrategie carteGenerationStrategie = new EquilibreCarteGenerationStrategie();
        Carte carte = carteGenerationStrategie.genererCarte(10, 1, plateauJeu);
        List<String> listeTypeVraiJoueur = new ArrayList<String>();
        List<String> listeNomVraiJoueur = new ArrayList<String>();
        List<Coordonnee> listeCoordonnee = new ArrayList<Coordonnee>();
        listeCoordonnee.add(new Coordonnee(0, 0));
        listeCoordonnee.add(new Coordonnee(1, 0));
        carte.setCoordonneeApparition(listeCoordonnee);
        carte.finition(1, listeTypeVraiJoueur, listeNomVraiJoueur);
        plateauJeu.setCarte(carte);
        Combattant combattant = new Combattant("Guerrier", 100, 50, 10, 20); // Crée un combattant exemple

        // Création du contrôleur
        GeneralControlleur controller = new GeneralControlleur(plateauJeu);

        // Test de l'action "passer"
        List<String> actionPasser = Arrays.asList("passer");
        controller.appliquerAction(actionPasser, combattant);

        // Test de l'action "bouclier"
        List<String> actionBouclier = Arrays.asList("bouclier");
        controller.appliquerAction(actionBouclier, combattant);

        // Test de l'action "marcher" (direction correcte)
        List<String> actionMarcher = Arrays.asList("marcher", "haut");
        controller.appliquerAction(actionMarcher, combattant);

        // Test de l'action "marcher" (direction incorrecte)
        List<String> actionMarcherInvalide = Arrays.asList("marcher", "nord");
        controller.appliquerAction(actionMarcherInvalide, combattant);

        // Test de l'action "tirer" (direction correcte)
        List<String> actionTirer = Arrays.asList("tirer", "verticale");
        controller.appliquerAction(actionTirer, combattant);

        // Test de l'action "tirer" (direction incorrecte)
        List<String> actionTirerInvalide = Arrays.asList("tirer", "diagonale");
        controller.appliquerAction(actionTirerInvalide, combattant);

        // Test de l'action "pieger" (type correct de piège)
        List<String> actionPieger = Arrays.asList("pieger", "mine", "1", "1");
        controller.appliquerAction(actionPieger, combattant);

        // Test de l'action "pieger" (type incorrect de piège)
        List<String> actionPiegerInvalide = Arrays.asList("pieger", "laser", "1", "1");
        controller.appliquerAction(actionPiegerInvalide, combattant);

        // Test de l'action "pieger" avec coordonnées invalides
        List<String> actionPiegerInvalidCoords = Arrays.asList("pieger", "mine", "0", "0");
        controller.appliquerAction(actionPiegerInvalidCoords, combattant);

        System.out.println("\nTous les tests ont réussi !");
    }
}

