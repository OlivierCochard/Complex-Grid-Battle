package combatJeu.modele.ordinateur.strategie;

import combatJeu.modele.ordinateur.Noeud;
import combatJeu.modele.ordinateur.PathFinding;
import combatJeu.modele.ordinateur.strategie.StrategieInterface;
import entitesJeu.obstacles.Combattant;
import entitesJeu.obstacles.Obstacle;
import entitesJeu.objets.Objet;
import combatJeu.modele.PlateauJeu;
import combatJeu.modele.Coordonnee;
import entitesJeu.Entite;

import java.util.*;
import java.util.Random;

//strategie assez inefficace donc j'ai prefere ne pas m'attarder dessus et la donner aux bots 
public class StrategieDefensive implements StrategieInterface {
    private Combattant combattant;
    private PlateauJeu plateauJeu;

    public StrategieDefensive(Combattant combattant, PlateauJeu plateauJeu){
        this.combattant = combattant;
        this.plateauJeu = plateauJeu;
    }

    public Combattant getCombattant(){ return combattant; }
    public PlateauJeu getPlateauJeu(){ return plateauJeu; }

    public String getActions(){
        PathFinding pathFinding = new PathFinding();
        Coordonnee combattantCoord = plateauJeu.getCoordonneeCombattant(combattant);
        Entite entiteProche = plateauJeu.getEntiteProche(combattantCoord);

        //SI ENTITE PROCHE
        if (entiteProche != null) {
            Coordonnee entiteProcheCoord = plateauJeu.getCoordonneeEntite(entiteProche);
            int distance = plateauJeu.calculeDistance(combattantCoord, entiteProcheCoord);

            //SI PEUT TIRER
            int portee = combattant.getPortee();
            if (distance <= portee && combattant.getDelaiTir() == 0){
                //DEFINI LA MEILLEURE DIRECTION DE TIR
                List<Obstacle> listeObstacleVerticale = plateauJeu.getObstacleTouche("verticale", portee);
                List<Obstacle> listeObstacleHorizontale = plateauJeu.getObstacleTouche("horizontale", portee);
                int compteurVerticale = 0;
                int compteurHorizontale = 0;
                for (Obstacle obstacleVerticale : listeObstacleVerticale){
                    if (obstacleVerticale instanceof Combattant){
                        compteurVerticale++;
                    }
                }
                for (Obstacle obstacleHorizontale : listeObstacleHorizontale){
                    if (obstacleHorizontale instanceof Combattant){
                        compteurHorizontale++;
                    }
                }
                if (compteurVerticale > compteurHorizontale){
                    return "tirer verticale";
                }
                if (compteurVerticale < compteurHorizontale){
                    return "tirer horizontale";
                }
                //SI LES DEUX SONT AUSSI INTERESSANTES TIRER ALEATOIREMENT LE RESULTAT
                Random rand = new Random();
                int index = rand.nextInt(2);
                if (index == 0){
                    return "tirer verticale";
                }
                return "tirer horizontale";
            }

            //SI ADVERSAIRE PROCHE FUIRE
            if (distance <= 5) {
                List<Noeud> cheminFuite = pathFinding.trouverChemin(plateauJeu.getCarte().getMatriceObstacle(), plateauJeu.getCarte().getMatriceObjet(),
                combattantCoord.getX(), combattantCoord.getY(), combattantCoord.getX(), combattantCoord.getY(), combattant);
                if (cheminFuite != null && !cheminFuite.isEmpty()) {
                    Noeud fuiteNoeud = fuiteVersOpposé(combattantCoord, entiteProcheCoord, cheminFuite);
                    return deplacerVers(fuiteNoeud);
                } else {
                    return "passer";
                }
            }

            //SI ADVERSAIRE LOIN SOIT PIEGER SOIT ATTENDRE
            Random rand = new Random();
            if (distance > 5 && rand.nextInt(2) == 0){
                String res = "pieger mine";
                Coordonnee coordPiege = getMeilleureCasePiege(combattantCoord, plateauJeu.getCoordonneeEntite(entiteProche));
                if (coordPiege != null) {
                    return res + " " + coordPiege.getX() + " " + coordPiege.getY();
                } else {
                    return "passer";
                }
            }
        }

        return "passer";
    }

    //CHERCHE LA CASE LA PLUS LOIN DE L'ADVERSAIRE LE PLUS PROCHE
    private Noeud fuiteVersOpposé(Coordonnee combattantCoord, Coordonnee entiteProcheCoord, List<Noeud> cheminFuite) {
        int deltaX = combattantCoord.getX() - entiteProcheCoord.getX();
        int deltaY = combattantCoord.getY() - entiteProcheCoord.getY();

        for (Noeud noeud : cheminFuite) {
            int dx = noeud.getX() - combattantCoord.getX();
            int dy = noeud.getY() - combattantCoord.getY();

            if (dx == deltaX && dy == deltaY) {
                return noeud;
            }
        }
        return cheminFuite.get(cheminFuite.size() - 1);
    }

    //VERIFI SI CASE DE FUITE LIBRE
    private String deplacerVers(Noeud noeud){
        Coordonnee coordCombattant = plateauJeu.getCoordonneeCombattant(combattant);
        if (coordCombattant.getX() == noeud.getX()){
            if (coordCombattant.getY() < noeud.getY()) {
                if (plateauJeu.getCarte().getObstacle(coordCombattant.getX(), coordCombattant.getY() + 1) == null && 
                    plateauJeu.getCarte().getObjet(coordCombattant.getX(), coordCombattant.getY() + 1) == null) {
                    return "marcher haut";
                }
            } else {
                if (plateauJeu.getCarte().getObstacle(coordCombattant.getX(), coordCombattant.getY() - 1) == null && 
                    plateauJeu.getCarte().getObjet(coordCombattant.getX(), coordCombattant.getY() - 1) == null) {
                    return "marcher bas";
                }
            }
        } else if (coordCombattant.getY() == noeud.getY()){
            if (coordCombattant.getX() < noeud.getX()) {
                if (plateauJeu.getCarte().getObstacle(coordCombattant.getX() + 1, coordCombattant.getY()) == null && 
                    plateauJeu.getCarte().getObjet(coordCombattant.getX() + 1, coordCombattant.getY()) == null) {
                    return "marcher droite";
                }
            } else {
                if (plateauJeu.getCarte().getObstacle(coordCombattant.getX() - 1, coordCombattant.getY()) == null && 
                    plateauJeu.getCarte().getObjet(coordCombattant.getX() - 1, coordCombattant.getY()) == null) {
                    return "marcher gauche";
                }
            }
        }
        return "passer";
    }

    private Coordonnee getMeilleureCasePiege(Coordonnee botCoord, Coordonnee cibleCoord){
        Coordonnee res = null;
        int distanceMin = 99999;

        if (plateauJeu.getCarte().getObstacle((botCoord.getX() + 1), botCoord.getY()) == null && plateauJeu.getCarte().getObjet((botCoord.getX() + 1), botCoord.getY()) == null){
            int distance = plateauJeu.calculeDistance(new Coordonnee((botCoord.getX() + 1), botCoord.getY()), cibleCoord);
            if (distance < distanceMin){
                distanceMin = distance;
                res = new Coordonnee(1, 0);
            }
        }
        return res; 
    }
}
