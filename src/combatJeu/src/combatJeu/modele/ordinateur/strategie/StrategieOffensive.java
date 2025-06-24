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

//strategie la plus inteligente
public class StrategieOffensive implements StrategieInterface {
	private Combattant combattant;
	private PlateauJeu plateauJeu;

	public StrategieOffensive(Combattant combattant, PlateauJeu plateauJeu){
		this.combattant = combattant;
		this.plateauJeu = plateauJeu;
	}

	public Combattant getCombattant(){ return combattant; }
    public PlateauJeu getPlateauJeu(){ return plateauJeu; }

    public String getActions(){
    	//TIR si possible
    	int portee = combattant.getPortee();
    	List<Obstacle> listeObstacleVerticale = plateauJeu.getObstacleTouche("verticale", portee);
    	List<Obstacle> listeObstacleHorizontale = plateauJeu.getObstacleTouche("horizontale", portee);
    	int compteurVerticale = 0;
    	int compteurHorizontale = 0;
    	//defini le meilleur tir (horizontale ou verticale)
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
    	//TIR INTERESSANT
    	if (compteurVerticale > 0 || compteurHorizontale > 0){
    		//SI PEUT PAS TIRER SE PROTEGE CAR ADVERSAIRE PROBABLE TIR
    		if ((combattant.getDelaiTir() > 0 && combattant.getDelaiBouclier() == 0) || (combattant.getMunition() <= 0 && combattant.getDelaiBouclier() == 0)){
    			return "bouclier";
    		}
    		//SI PEUT PAS TIRER NI SE PROTEGER PIEGE
    		if ((combattant.getDelaiTir() > 0 || combattant.getMunition() <= 0) && combattant.getDelaiBouclier() > 0){
    			Coordonnee coordCombattant = plateauJeu.getCoordonneeCombattant(combattant);
    			Entite entiteProche = plateauJeu.getEntiteProche(coordCombattant);
    			if (entiteProche instanceof Combattant){
    				Coordonnee coordEntite = plateauJeu.getCoordonneeCombattant((Combattant) entiteProche);
    				int dst = plateauJeu.calculeDistance(coordCombattant, coordEntite);
    				if (dst > 2){
    					//PAS INTERESSANT CAR ADVERSAIRE TROP LOIN
    					return "passer";
    				}

    				//PEUT PIEGER, DEFINI QUEL TYPE DE PIEGE ALEATOIREMENT
    				String res = "pieger";
	    			Random rand = new Random();
	    			int index = rand.nextInt(2);
	    			if (index == 0){
	    				res += " mine";
	    			}
	    			else {
	    				res += " bombe";
	    			}

	    			//DEFINI LA MEILLEURE CASE POUR LE PIEGE
	    			Coordonnee coord = getMeilleureCasePiege(coordCombattant, coordEntite);
	    			if (coord != null){
	    				return (res + " " + coord.getX() + " " + coord.getY());
	    			}
	    			System.out.println("pas de case dispo autour...");
    			}
    			//SI NI TIR, NI BOUCLIER, NI PIEGE mais a distance de tirer attends et passe le tour
    			return "passer";
    		}

    		//SI PEUT TIR, tir au meilleur endroit
    		if (compteurVerticale > compteurHorizontale){
    			return "tirer verticale";
    		}
    		if (compteurVerticale < compteurHorizontale){
    			return "tirer horizontale";
    		}
    		Random rand = new Random();
    		int index = rand.nextInt(2);
    		if (index == 0){
    			return "tirer verticale";
    		}
    		return "tirer horizontale";
    	}

    	//AVANCER SI PAS A DISTANCE DE TIRER
    	PathFinding pathFinding = new PathFinding();
    	Coordonnee combattantCoord = plateauJeu.getCoordonneeCombattant(combattant);
    	//defini un ciombattant ou une pastille proche pour savoir ou se rendre
    	Entite entiteProche = plateauJeu.getEntiteProche(combattantCoord);
    	if (entiteProche == null){
    		return "passer";
    	}

    	//Cherche le chemin pour s'y rendre
    	Coordonnee entiteProcheCoord = plateauJeu.getCoordonneeEntite(entiteProche);
		List<Noeud> chemin = pathFinding.trouverChemin(plateauJeu.getCarte().getMatriceObstacle(), plateauJeu.getCarte().getMatriceObjet(), combattantCoord.getX(), combattantCoord.getY(), entiteProcheCoord.getX(), entiteProcheCoord.getY(), combattant);
    	if (chemin == null){ //PAS DE CHEMIN POSSIBLE
    		if (entiteProche instanceof Obstacle && chemin.size() < 3)
    			return "passer";
			if (entiteProche instanceof Objet && chemin.size() < 2)
    			return "passer";
    	}

    	//regarde la premiere case pour atteindre la destination
    	Noeud prochainNoeud = chemin.get(1);
    	if (combattantCoord.getX() == prochainNoeud.getX()){
    		if (combattantCoord.getY() == prochainNoeud.getY() + 1){
    			return "marcher bas";
    		}
    		if (combattantCoord.getY() == prochainNoeud.getY() - 1){
    			return "marcher haut";
    		}
    		return "error marcher verticale";
    	}
    	if (combattantCoord.getY() == prochainNoeud.getY()){
    		if (combattantCoord.getX() == prochainNoeud.getX() + 1){
    			return "marcher gauche";
    		}
    		if (combattantCoord.getX() == prochainNoeud.getX() - 1){
    			return "marcher droite";
    		}
    		return "error marcher horizontale";
    	}
    	return "error marcher";
    }

    //Defini la meilleur case pour un pige
    //Defini la case la plus proche de l'adversaire
    private Coordonnee getMeilleureCasePiege(Coordonnee botCoord, Coordonnee cibleCoord){
    	Coordonnee res = null;
    	int distanceMin = 99999;

    	//DROITE
    	if (plateauJeu.getCarte().getObstacle((botCoord.getX() + 1), botCoord.getY()) == null && plateauJeu.getCarte().getObjet((botCoord.getX() + 1), botCoord.getY()) == null){
    		int distance = plateauJeu.calculeDistance(new Coordonnee((botCoord.getX() + 1), botCoord.getY()), cibleCoord);
    		if (distance < distanceMin){
    			distanceMin = distance;
    			res = new Coordonnee(1, 0);
    		}
		}
		//GAUCHE
		if (plateauJeu.getCarte().getObstacle((botCoord.getX() - 1), botCoord.getY()) == null && plateauJeu.getCarte().getObjet((botCoord.getX() - 1), botCoord.getY()) == null){
    		int distance = plateauJeu.calculeDistance(new Coordonnee((botCoord.getX() - 1), botCoord.getY()), cibleCoord);
    		if (distance < distanceMin){
    			distanceMin = distance;
    			res = new Coordonnee(-1, 0);
    		}
		}
		//HAUT
    	if (plateauJeu.getCarte().getObstacle(botCoord.getX(), (botCoord.getY() + 1)) == null && plateauJeu.getCarte().getObjet(botCoord.getX(), (botCoord.getY() + 1)) == null){
    		int distance = plateauJeu.calculeDistance(new Coordonnee(botCoord.getX(), (botCoord.getY() + 1)), cibleCoord);
    		if (distance < distanceMin){
    			distanceMin = distance;
    			res = new Coordonnee(0, 1);
    		}
		}
		//BAS
    	if (plateauJeu.getCarte().getObstacle(botCoord.getX(), (botCoord.getY() - 1)) == null && plateauJeu.getCarte().getObjet(botCoord.getX(), (botCoord.getY() - 1)) == null){
    		int distance = plateauJeu.calculeDistance(new Coordonnee(botCoord.getX(), (botCoord.getY() - 1)), cibleCoord);
    		if (distance < distanceMin){
    			distanceMin = distance;
    			res = new Coordonnee(0, -1);
    		}
		}

		return res;
    }
}	
