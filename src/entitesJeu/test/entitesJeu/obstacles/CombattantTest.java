package entitesJeu.obstacles;

import entitesJeu.obstacles.Combattant;

public class CombattantTest {

    public static void main(String[] args) {
        // Test initialisation
        Combattant combattant = new Combattant("Guerrier", 100, 50, 10, 20);
        combattant.setNom("Combattant");
        assert "Combattant".equals(combattant.getNom()) : "Échec: nom incorrect";
        assert "Guerrier".equals(combattant.getType()) : "Échec: type incorrect";
        assert combattant.getEnergie() == 100 : "Échec: énergie incorrecte";

        // Test perte de munitions
        combattant.perteMunition();
        assert combattant.getMunition() == 9 : "Échec: perte munition incorrecte";

        // Test perte d'énergie sans bouclier
        combattant.setBouclier(false);
        combattant.perteEnergie(50);
        assert combattant.getEnergie() == 50 : "Échec: perte énergie incorrecte";

        // Test bouclier actif
        combattant.setBouclier(true);
        combattant.perteEnergie(50);
        assert combattant.getEnergie() == 50 : "Échec: bouclier ne protège pas";

        // Test destruction
        combattant.setBouclier(false);
        combattant.perteEnergie(50);
        assert combattant.isMort() : "Échec: combattant non marqué comme mort";

        System.out.println("Tous les tests sont passés avec succès !");
    }
}
