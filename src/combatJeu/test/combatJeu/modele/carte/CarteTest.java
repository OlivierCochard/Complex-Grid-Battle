package combatJeu.modele.carte;

import combatJeu.modele.PlateauJeu;
import combatJeu.GameSettings;
import entitesJeu.obstacles.Combattant;
import entitesJeu.objets.PastilleEnergetique;
import entitesJeu.objets.Objet;
import entitesJeu.obstacles.Mur;
import entitesJeu.obstacles.Obstacle;
import combatJeu.modele.ordinateur.PathFinding;
import combatJeu.modele.Coordonnee;
import combatJeu.modele.ordinateur.Noeud;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class CarteTest {

    public static void main(String[] args) {
        // Simuler un plateau de jeu
        PlateauJeu plateauJeu = new PlateauJeu();
        int tailleX = 10;
        int tailleY = 10;
        int nbVraiJoueur = 2;

        // Créer une carte
        Carte carte = new Carte(tailleX, tailleY, nbVraiJoueur, plateauJeu);

        // Vérification des dimensions de la carte
        assert carte.getTailleX() == 10 : "La taille X de la carte est incorrecte";
        assert carte.getTailleY() == 10 : "La taille Y de la carte est incorrecte";

        // Générer des obstacles et des objets aléatoires
        carte.remplirAvecProbabilite(0.2, 0.1, 0.3); // Proba de 20% pour des murs, 10% pour de l'eau, 30% pour des pastilles

        // Vérification de la génération des obstacles
        Obstacle obstacle = carte.getObstacle(5, 5);
        assert obstacle == null || obstacle instanceof Mur : "L'obstacle à (5, 5) n'est pas un Mur";

        // Vérification de la présence de pastilles énergétiques
        Objet objet = carte.getObjet(2, 3);
        assert objet == null || objet instanceof PastilleEnergetique : "L'objet à (2, 3) n'est pas une pastille énergétique";

        // Positionner les joueurs sur la carte
        carte.positionnerJoueurs(nbVraiJoueur, 3); // Placer les joueurs avec une distance minimum de 3 cases

        // Vérification que les joueurs sont positionnés correctement
        List<Coordonnee> coordonneeApparition = carte.getListeCoordonneeApparition();
        assert coordonneeApparition.size() == nbVraiJoueur : "Le nombre de joueurs positionnés est incorrect";
        System.out.println("Coordonnées des joueurs : ");
        for (Coordonnee coordonnee : coordonneeApparition) {
            System.out.println(coordonnee);
        }

        // Tester la génération d'un chemin
        PathFinding pathFinding = new PathFinding();
        Coordonnee depart = coordonneeApparition.get(0);
        Coordonnee destination = coordonneeApparition.get(1);
        List<Noeud> chemin = pathFinding.trouverChemin(carte.getMatriceObstacle(), carte.getMatriceObjet(), depart.getX(), depart.getY(), destination.getX(), destination.getY(), null);

        // Vérification du chemin trouvé
        assert chemin != null : "Aucun chemin trouvé entre les joueurs";
        assert chemin.size() > 0 : "Le chemin trouvé est vide";

        // Afficher le chemin
        System.out.println("Chemin trouvé entre les joueurs :");
        for (Noeud noeud : chemin) {
            System.out.println(noeud);
        }

        System.out.println("Test réussi !");
    }
}
