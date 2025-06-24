package entitesJeu.obstacles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CombattantFactory {

    private int energie_classique;
    private int portee_classique;
    private int munition_classique;
    private int degatTir_classique;
    private int energie_sniper;
    private int portee_sniper;
    private int munition_sniper;
    private int degatTir_sniper;
    private int energie_tank;
    private int portee_tank;
    private int munition_tank;
    private int degatTir_tank;

    public CombattantFactory(
        int energie_classique, int portee_classique, int munition_classique, int degatTir_classique,
        int energie_sniper, int portee_sniper, int munition_sniper, int degatTir_sniper,
        int energie_tank, int portee_tank, int munition_tank, int degatTir_tank
    ){
        this.energie_classique = energie_classique;
        this.portee_classique = portee_classique;
        this.munition_classique = munition_classique;
        this.degatTir_classique = degatTir_classique;

        this.energie_sniper = energie_sniper;
        this.portee_sniper = portee_sniper;
        this.munition_sniper = munition_sniper;
        this.degatTir_sniper = degatTir_sniper;

        this.energie_tank = energie_tank;
        this.portee_tank = portee_tank;
        this.munition_tank = munition_tank;
        this.degatTir_tank = degatTir_tank;
    }

    public Combattant createCombattant(String type) {
        switch (type.toLowerCase()) {
            case "aleatoire":
                return createCombattantRandom();
            case "classique":
                return new Combattant(
                    "Classique",
                    energie_classique, 
                    portee_classique, 
                    munition_classique, 
                    degatTir_classique
                );
            case "sniper":
                return new Combattant(
                    "Sniper",
                    energie_sniper, 
                    portee_sniper, 
                    munition_sniper, 
                    degatTir_sniper
                );
            case "tank":
                return new Combattant(
                    "Tank",
                    energie_tank, 
                    portee_tank, 
                    munition_tank, 
                    degatTir_tank
                );
            default:
                throw new IllegalArgumentException("Type de combattant inconnu: " + type);
        }
    }

    public Combattant createCombattantRandom() {
        List<String> types = new ArrayList<>();
        types.add("classique");
        types.add("sniper");
        types.add("tank");

        Random rand = new Random();
        String type = types.get(rand.nextInt(types.size()));

        return createCombattant(type);
    }
}
