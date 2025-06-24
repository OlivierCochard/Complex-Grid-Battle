package entitesJeu.obstacles;

public class MurTest {

    public static void main(String[] args) {
        // Création de l'objet Mur
        Mur mur = new Mur();

        // Test de la méthode toString
        assert "Mur".equals(mur.toString()) : "Échec: la méthode toString ne retourne pas 'Mur'.";

        // Test de la méthode destruction
        mur.destruction();

        // Test de la méthode touche avec un nombre de dégâts arbitraire (par exemple 10)
        mur.touche(10);

        System.out.println("Tous les tests sont passés avec succès !");
    }
}

