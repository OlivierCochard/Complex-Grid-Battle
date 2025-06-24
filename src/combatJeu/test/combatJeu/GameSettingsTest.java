package combatJeu;

import combatJeu.GameSettings;

public class GameSettingsTest {
    public static void main(String[] args) {
        // Test de la configuration des combattants classiques
        System.out.println("Test des combattants classiques :");
        assert GameSettings.ENERGIE_COMBATTANT_CLASSIQUE == 125 : "Erreur : mauvaise valeur pour ENERGIE_COMBATTANT_CLASSIQUE";
        assert GameSettings.PORTEE_COMBATTANT_CLASSIQUE == 3 : "Erreur : mauvaise valeur pour PORTEE_COMBATTANT_CLASSIQUE";
        assert GameSettings.MUNITION_COMBATTANT_CLASSIQUE == 10 : "Erreur : mauvaise valeur pour MUNITION_COMBATTANT_CLASSIQUE";
        assert GameSettings.DEGAT_TIR_COMBATTANT_CLASSIQUE == 25 : "Erreur : mauvaise valeur pour DEGAT_TIR_COMBATTANT_CLASSIQUE";

        // Test de la configuration des combattants snipers
        System.out.println("\nTest des combattants snipers :");
        assert GameSettings.ENERGIE_COMBATTANT_SNIPER == 100 : "Erreur : mauvaise valeur pour ENERGIE_COMBATTANT_SNIPER";
        assert GameSettings.PORTEE_COMBATTANT_SNIPER == 5 : "Erreur : mauvaise valeur pour PORTEE_COMBATTANT_SNIPER";
        assert GameSettings.MUNITION_COMBATTANT_SNIPER == 10 : "Erreur : mauvaise valeur pour MUNITION_COMBATTANT_SNIPER";
        assert GameSettings.DEGAT_TIR_COMBATTANT_SNIPER == 15 : "Erreur : mauvaise valeur pour DEGAT_TIR_COMBATTANT_SNIPER";

        // Test de la configuration des combattants tanks
        System.out.println("\nTest des combattants tanks :");
        assert GameSettings.ENERGIE_COMBATTANT_TANK == 150 : "Erreur : mauvaise valeur pour ENERGIE_COMBATTANT_TANK";
        assert GameSettings.PORTEE_COMBATTANT_TANK == 1 : "Erreur : mauvaise valeur pour PORTEE_COMBATTANT_TANK";
        assert GameSettings.MUNITION_COMBATTANT_TANK == 10 : "Erreur : mauvaise valeur pour MUNITION_COMBATTANT_TANK";
        assert GameSettings.DEGAT_TIR_COMBATTANT_TANK == 35 : "Erreur : mauvaise valeur pour DEGAT_TIR_COMBATTANT_TANK";

        // Test des coûts énergétiques
        System.out.println("\nTest des coûts énergétiques :");
        assert GameSettings.ENERGIE_DEPLACEMENT == 5 : "Erreur : mauvaise valeur pour ENERGIE_DEPLACEMENT";
        assert GameSettings.ENERGIE_PROJECTILE == 5 : "Erreur : mauvaise valeur pour ENERGIE_PROJECTILE";
        assert GameSettings.ENERGIE_BOUCLIER == 5 : "Erreur : mauvaise valeur pour ENERGIE_BOUCLIER";
        assert GameSettings.ENERGIE_PIEGE == 10 : "Erreur : mauvaise valeur pour ENERGIE_PIEGE";

        // Test des dégâts
        System.out.println("\nTest des dégâts :");
        assert GameSettings.DEGAT_MINE == 25 : "Erreur : mauvaise valeur pour DEGAT_MINE";
        assert GameSettings.DEGAT_BOMBE == 40 : "Erreur : mauvaise valeur pour DEGAT_BOMBE";

        // Test des délais
        System.out.println("\nTest des délais :");
        assert GameSettings.DELAI_TOUR_EXPLOSION_BOMBE == 4 : "Erreur : mauvaise valeur pour DELAI_TOUR_EXPLOSION_BOMBE";
        assert GameSettings.DELAI_TOUR_MAX_ENERGIE_PASTILLE == 4 : "Erreur : mauvaise valeur pour DELAI_TOUR_MAX_ENERGIE_PASTILLE";
        assert GameSettings.DELAI_TOUR_ENERGIE_PASTILLE == 1 : "Erreur : mauvaise valeur pour DELAI_TOUR_ENERGIE_PASTILLE";
        assert GameSettings.DELAI_ACTION_TIRER == 3 : "Erreur : mauvaise valeur pour DELAI_ACTION_TIRER";
        assert GameSettings.DELAI_ACTION_BOUCLIER == 3 : "Erreur : mauvaise valeur pour DELAI_ACTION_BOUCLIER";

        // Test de l'énergie de la pastille
        System.out.println("\nTest de l'énergie de la pastille :");
        assert GameSettings.ENERGIE_PASTILLE == 20 : "Erreur : mauvaise valeur pour ENERGIE_PASTILLE";

        System.out.println("\nTous les tests ont réussi !");
    }
}

