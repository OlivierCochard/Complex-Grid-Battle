package combatJeu;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GameSettings {
    //COMBATTANTS
    // Combattants classique
    public static int ENERGIE_COMBATTANT_CLASSIQUE;
    public static int PORTEE_COMBATTANT_CLASSIQUE;
    public static int MUNITION_COMBATTANT_CLASSIQUE;
    public static int DEGAT_TIR_COMBATTANT_CLASSIQUE;
    // Combattants sniper
    public static int ENERGIE_COMBATTANT_SNIPER;
    public static int PORTEE_COMBATTANT_SNIPER;
    public static int MUNITION_COMBATTANT_SNIPER;
    public static int DEGAT_TIR_COMBATTANT_SNIPER;
    // Combattants tank
    public static int ENERGIE_COMBATTANT_TANK;
    public static int PORTEE_COMBATTANT_TANK;
    public static int MUNITION_COMBATTANT_TANK;
    public static int DEGAT_TIR_COMBATTANT_TANK;

    // Coûts énergétiques
    public static int ENERGIE_DEPLACEMENT;
    public static int ENERGIE_PROJECTILE;
    public static int ENERGIE_BOUCLIER;
    public static int ENERGIE_PIEGE;

    // Dégâts
    public static int DEGAT_MINE;
    public static int DEGAT_BOMBE;

    // Délais
    public static int DELAI_TOUR_EXPLOSION_BOMBE;
    public static int DELAI_TOUR_MAX_ENERGIE_PASTILLE;
    public static int DELAI_TOUR_ENERGIE_PASTILLE;
    public static int DELAI_ACTION_TIRER;
    public static int DELAI_ACTION_BOUCLIER;

    // Énergies
    public static int ENERGIE_PASTILLE;

    static {
        //va chercher les variables necessaires dans le fichier: /combatJeu/gameSettings.properties
        Properties properties = new Properties();
        try (InputStream input = GameSettings.class.getResourceAsStream("/combatJeu/gameSettings.properties")) {
            if (input == null) {
                System.err.println("Fichier de configuration gameSettings.properties non trouvé !");
            } else {
                properties.load(input);

                // Chargement des valeurs
                ENERGIE_COMBATTANT_CLASSIQUE = Integer.parseInt(properties.getProperty("ENERGIE_COMBATTANT_CLASSIQUE"));
                PORTEE_COMBATTANT_CLASSIQUE = Integer.parseInt(properties.getProperty("PORTEE_COMBATTANT_CLASSIQUE"));
                MUNITION_COMBATTANT_CLASSIQUE = Integer.parseInt(properties.getProperty("MUNITION_COMBATTANT_CLASSIQUE"));
                DEGAT_TIR_COMBATTANT_CLASSIQUE = Integer.parseInt(properties.getProperty("DEGAT_TIR_COMBATTANT_CLASSIQUE"));

                ENERGIE_COMBATTANT_SNIPER = Integer.parseInt(properties.getProperty("ENERGIE_COMBATTANT_SNIPER"));
                PORTEE_COMBATTANT_SNIPER = Integer.parseInt(properties.getProperty("PORTEE_COMBATTANT_SNIPER"));
                MUNITION_COMBATTANT_SNIPER = Integer.parseInt(properties.getProperty("MUNITION_COMBATTANT_SNIPER"));
                DEGAT_TIR_COMBATTANT_SNIPER = Integer.parseInt(properties.getProperty("DEGAT_TIR_COMBATTANT_SNIPER"));

                ENERGIE_COMBATTANT_TANK = Integer.parseInt(properties.getProperty("ENERGIE_COMBATTANT_TANK"));
                PORTEE_COMBATTANT_TANK = Integer.parseInt(properties.getProperty("PORTEE_COMBATTANT_TANK"));
                MUNITION_COMBATTANT_TANK = Integer.parseInt(properties.getProperty("MUNITION_COMBATTANT_TANK"));
                DEGAT_TIR_COMBATTANT_TANK = Integer.parseInt(properties.getProperty("DEGAT_TIR_COMBATTANT_TANK"));

                ENERGIE_DEPLACEMENT = Integer.parseInt(properties.getProperty("ENERGIE_DEPLACEMENT"));
                ENERGIE_PROJECTILE = Integer.parseInt(properties.getProperty("ENERGIE_PROJECTILE"));
                ENERGIE_BOUCLIER = Integer.parseInt(properties.getProperty("ENERGIE_BOUCLIER"));
                ENERGIE_PIEGE = Integer.parseInt(properties.getProperty("ENERGIE_PIEGE"));

                DEGAT_MINE = Integer.parseInt(properties.getProperty("DEGAT_MINE"));
                DEGAT_BOMBE = Integer.parseInt(properties.getProperty("DEGAT_BOMBE"));

                DELAI_TOUR_EXPLOSION_BOMBE = Integer.parseInt(properties.getProperty("DELAI_TOUR_EXPLOSION_BOMBE"));
                DELAI_TOUR_MAX_ENERGIE_PASTILLE = Integer.parseInt(properties.getProperty("DELAI_TOUR_MAX_ENERGIE_PASTILLE"));
                DELAI_TOUR_ENERGIE_PASTILLE = Integer.parseInt(properties.getProperty("DELAI_TOUR_ENERGIE_PASTILLE"));
                DELAI_ACTION_TIRER = Integer.parseInt(properties.getProperty("DELAI_ACTION_TIRER"));
                DELAI_ACTION_BOUCLIER = Integer.parseInt(properties.getProperty("DELAI_ACTION_BOUCLIER"));

                ENERGIE_PASTILLE = Integer.parseInt(properties.getProperty("ENERGIE_PASTILLE"));
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier de configuration.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Erreur de format dans le fichier de configuration.");
            e.printStackTrace();
        }
    }

    private GameSettings() {
    }
}
