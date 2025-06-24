package entitesJeu.objets;

import entitesJeu.objets.Mine;
import entitesJeu.objets.MineProxy;
import entitesJeu.obstacles.Combattant;

public class MineProxyTest {

    public static void main(String[] args) {
        // Création de deux combattants
        Combattant poseur = new Combattant("Guerrier", 100, 50, 10, 20);
        Combattant autreCombattant = new Combattant("Mage", 80, 60, 12, 15);

        // Création d'une mine avec le poseur
        Mine mine = new Mine(100, poseur);
        
        // Création du proxy pour le poseur
        MineProxy mineProxyPoseur = new MineProxy(mine, poseur);
        
        // Création du proxy pour l'autre combattant
        MineProxy mineProxyAutre = new MineProxy(mine, autreCombattant);

        // Test d'accès par le poseur
        System.out.println("Test avec le poseur :");

        // Vérification des dégâts
        assert mineProxyPoseur.getDegats() == 100 : "Erreur : Le dégât de la mine n'est pas correct pour le poseur.";
        
        // Test de l'explosion
        mineProxyPoseur.explosion();
        
        // Test de la destruction
        mineProxyPoseur.destruction();

        // Test d'accès par un autre combattant
        System.out.println("\nTest avec un autre combattant :");
        try {
            mineProxyAutre.getDegats();
            assert false : "Erreur : L'autre combattant n'aurait pas dû pouvoir accéder aux dégâts.";
        } catch (UnsupportedOperationException e) {
            System.out.println("Erreur attendue : " + e.getMessage());
        }

        try {
            mineProxyAutre.explosion();
            assert false : "Erreur : L'autre combattant n'aurait pas dû pouvoir provoquer l'explosion.";
        } catch (UnsupportedOperationException e) {
            System.out.println("Erreur attendue : " + e.getMessage());
        }

        try {
            mineProxyAutre.destruction();
            assert false : "Erreur : L'autre combattant n'aurait pas dû pouvoir détruire la mine.";
        } catch (UnsupportedOperationException e) {
            System.out.println("Erreur attendue : " + e.getMessage());
        }

        System.out.println("\nTous les tests sont passés avec succès !");
    }
}
