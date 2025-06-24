package combatJeu.modele;

import combatJeu.modele.Coordonnee;

public class CoordonneeTest {
    public static void main(String[] args) {
        // Test 1: Création d'une coordonnée
        Coordonnee coordonnee = new Coordonnee(3, 5);
        
        // Vérification des valeurs x et y
        assert coordonnee.getX() == 3 : "Erreur : La valeur de x n'est pas correcte.";
        assert coordonnee.getY() == 5 : "Erreur : La valeur de y n'est pas correcte.";
        
        // Test de la méthode toString()
        String expectedToString = "(3, 5)";
        assert coordonnee.toString().equals(expectedToString) : "Erreur : La méthode toString() ne retourne pas la bonne valeur.";
        
        // Test 2: Création d'une autre coordonnée
        Coordonnee coordonnee2 = new Coordonnee(-1, 0);
        
        // Vérification des valeurs x et y
        assert coordonnee2.getX() == -1 : "Erreur : La valeur de x n'est pas correcte pour coordonnee2.";
        assert coordonnee2.getY() == 0 : "Erreur : La valeur de y n'est pas correcte pour coordonnee2.";
        
        // Test de la méthode toString()
        String expectedToString2 = "(-1, 0)";
        assert coordonnee2.toString().equals(expectedToString2) : "Erreur : La méthode toString() ne retourne pas la bonne valeur pour coordonnee2.";
        
        System.out.println("Tous les tests sont passés avec succès !");
    }
}

