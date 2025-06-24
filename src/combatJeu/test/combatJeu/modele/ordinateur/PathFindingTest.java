package combatJeu.modele.ordinateur;

import combatJeu.modele.ordinateur.PathFinding;
import combatJeu.modele.ordinateur.Noeud;
import entitesJeu.obstacles.Combattant;
import entitesJeu.obstacles.Obstacle;
import entitesJeu.obstacles.Mur;
import entitesJeu.objets.Objet;
import entitesJeu.objets.Mine;

import java.util.List;

public class PathFindingTest {

    public static void main(String[] args) {
        // Simuler un grille de jeu avec des obstacles et des objets
        Obstacle[][] grilleObstacle = new Obstacle[5][5];
        Objet[][] grilleObjet = new Objet[5][5];

        // Exemple de grille avec un obstacle
        grilleObstacle[2][2] = new Mur();

        // Exemple d'objet (Bombe) placé sur la grille
        grilleObjet[1][1] = new Mine(10, null);

        // Combattant à tester
        Combattant combattant = new Combattant("Guerrier", 100, 10, 2, 3);; // Nous utilisons un combattant vide pour le test

        // Instancier PathFinding
        PathFinding pathFinding = new PathFinding();

        // Appeler la méthode pour trouver le chemin
        List<Noeud> chemin = pathFinding.trouverChemin(grilleObstacle, grilleObjet, 0, 0, 4, 4, combattant);

        // Vérification de la validité du chemin (assertions)
        if (chemin != null) {
            assert chemin.size() > 0 : "Le chemin trouvé ne contient pas de noeuds!";
            assert chemin.get(0).getX() == 0 && chemin.get(0).getY() == 0 : "Le premier noeud du chemin n'est pas la position de départ";
            assert chemin.get(chemin.size() - 1).getX() == 4 && chemin.get(chemin.size() - 1).getY() == 4 : "Le dernier noeud du chemin n'est pas la position d'arrivée";

            System.out.println("Chemin trouvé:");
            for (Noeud noeud : chemin) {
                System.out.println(noeud);
            }
        } else {
            System.out.println("Aucun chemin trouvé.");
        }
        System.out.println("Tous les tests sont passés !");
    }

}
