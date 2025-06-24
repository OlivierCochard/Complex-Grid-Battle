package combatJeu.modele.ordinateur;

public class NoeudTest {

    public static void main(String[] args) {
        // Création de quelques instances de Noeud
        Noeud noeud1 = new Noeud(1, 2);
        Noeud noeud2 = new Noeud(1, 2);
        Noeud noeud3 = new Noeud(3, 4);
        
        // Test des getters
        assert noeud1.getX() == 1 : "Erreur: x de noeud1";
        assert noeud1.getY() == 2 : "Erreur: y de noeud1";
        
        // Test de la méthode equals
        assert noeud1.equals(noeud2) : "Erreur: noeud1 et noeud2 devraient être égaux";
        assert !noeud1.equals(noeud3) : "Erreur: noeud1 et noeud3 ne devraient pas être égaux";
        
        // Test de la méthode hashCode
        assert noeud1.hashCode() == noeud2.hashCode() : "Erreur: hashCode de noeud1 et noeud2 devraient être égaux";
        assert noeud1.hashCode() != noeud3.hashCode() : "Erreur: hashCode de noeud1 et noeud3 devraient être différents";
        
        // Test de la méthode toString
        assert noeud1.toString().equals("(1, 2)") : "Erreur: toString de noeud1";
        assert noeud3.toString().equals("(3, 4)") : "Erreur: toString de noeud3";

        System.out.println("Tous les tests sont passés !");
    }
}
