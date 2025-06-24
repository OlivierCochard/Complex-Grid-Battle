package entitesJeu.obstacles;

import entitesJeu.obstacles.Eau;

public class EauTest {

    public static void main(String[] args) {
        // Création de l'objet Eau
        Eau eau = new Eau();

        // Test de la méthode toString
        assert "Eau".equals(eau.toString()) : "Échec: la méthode toString ne retourne pas 'Eau'.";

        // Test de la méthode destruction
        eau.destruction();

        // Test de la méthode touche avec un nombre de dégâts arbitraire (par exemple 10)
        eau.touche(10);

        System.out.println("Tous les tests sont passés avec succès !");
    }
}
