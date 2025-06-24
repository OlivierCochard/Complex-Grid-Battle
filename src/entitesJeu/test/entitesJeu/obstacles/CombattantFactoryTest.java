package entitesJeu.obstacles;

import entitesJeu.obstacles.Combattant;
import entitesJeu.obstacles.CombattantFactory;

public class CombattantFactoryTest {

    public static void main(String[] args) {
        // Initialisation des valeurs pour les combattants
        CombattantFactory factory = new CombattantFactory(100, 50, 10, 20, 80, 70, 5, 30, 200, 30, 3, 50);

        // Test création combattant classique
        Combattant classique = factory.createCombattant("classique");
        assert classique.getType().equals("Classique") : "Échec: type incorrect pour classique";
        assert classique.getEnergie() == 100 : "Échec: énergie incorrecte pour classique";
        assert classique.getPortee() == 50 : "Échec: portée incorrecte pour classique";
        assert classique.getMunition() == 10 : "Échec: munitions incorrectes pour classique";
        assert classique.getDegatTir() == 20 : "Échec: dégât tir incorrect pour classique";

        // Test création combattant sniper
        Combattant sniper = factory.createCombattant("sniper");
        assert sniper.getType().equals("Sniper") : "Échec: type incorrect pour sniper";
        assert sniper.getEnergie() == 80 : "Échec: énergie incorrecte pour sniper";
        assert sniper.getPortee() == 70 : "Échec: portée incorrecte pour sniper";
        assert sniper.getMunition() == 5 : "Échec: munitions incorrectes pour sniper";
        assert sniper.getDegatTir() == 30 : "Échec: dégât tir incorrect pour sniper";

        // Test création combattant tank
        Combattant tank = factory.createCombattant("tank");
        assert tank.getType().equals("Tank") : "Échec: type incorrect pour tank";
        assert tank.getEnergie() == 200 : "Échec: énergie incorrecte pour tank";
        assert tank.getPortee() == 30 : "Échec: portée incorrecte pour tank";
        assert tank.getMunition() == 3 : "Échec: munitions incorrectes pour tank";
        assert tank.getDegatTir() == 50 : "Échec: dégât tir incorrect pour tank";

        // Test création combattant avec type inconnu (devrait lever une exception)
        try {
            factory.createCombattant("inconnu");
            assert false : "Échec: l'exception n'a pas été levée pour un type inconnu";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("Type de combattant inconnu: inconnu") : "Échec: message d'exception incorrect";
        }

        // Test création combattant aléatoire
        Combattant randomCombattant = factory.createCombattantRandom();
        assert randomCombattant != null : "Échec: le combattant aléatoire est nul";
        assert randomCombattant.getType().equals("Classique") || randomCombattant.getType().equals("Sniper") || randomCombattant.getType().equals("Tank") : 
            "Échec: type de combattant aléatoire incorrect";

        System.out.println("Tous les tests sont passés avec succès !");
    }
}
