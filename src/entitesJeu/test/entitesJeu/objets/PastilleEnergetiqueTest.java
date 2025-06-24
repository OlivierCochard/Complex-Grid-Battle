package entitesJeu.objets;

import entitesJeu.objets.PastilleEnergetique;

public class PastilleEnergetiqueTest {

    public static void main(String[] args) {
        // Test 1: Création d'une PastilleEnergetique
        PastilleEnergetique pastille = new PastilleEnergetique(50, 3, 3);

        // Vérification des valeurs initiales
        assert pastille.getEnergie() == 50 : "Erreur : L'énergie initiale n'est pas correcte.";
        assert pastille.getTourRestant() == 3 : "Erreur : Le nombre de tours restants initial n'est pas correct.";
        assert !pastille.estPrenable() : "Erreur : La pastille devrait être prise après la réduction des tours.";

        // Affichage pour vérification
        System.out.println("Initial PastilleEnergetique: " + pastille);

        // Test 2: Réduction du nombre de tours restants
        pastille.reductionTourRestant();
        assert pastille.getTourRestant() == 2 : "Erreur : La réduction du nombre de tours restants a échoué.";
        System.out.println("Après réduction: " + pastille);

        // Test 3: Vérification de la méthode estPrenable
        assert !pastille.estPrenable() : "Erreur : La pastille ne doit pas être prenable avant 0 tour.";

        // Réduire encore les tours
        pastille.reductionTourRestant();
        pastille.reductionTourRestant();

        // Vérification si la pastille devient prenable
        assert pastille.estPrenable() : "Erreur : La pastille devrait être prenable lorsque les tours restants sont à 0.";
        System.out.println("Après réduction finale: " + pastille);

        // Test 4: Réinitialisation
        pastille.reinitialiser();
        assert pastille.getTourRestant() == 4 : "Erreur : La réinitialisation des tours restants a échoué.";
        System.out.println("Après réinitialisation: " + pastille);

        // Test 5: Explosion et destruction
        pastille.explosion();
        pastille.destruction();
    }
}

