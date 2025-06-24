package combatJeu.modele.carte;

import entitesJeu.objets.Objet;
import entitesJeu.objets.PastilleEnergetique;
import entitesJeu.obstacles.Obstacle;
import entitesJeu.obstacles.Eau;
import entitesJeu.obstacles.Mur;
import entitesJeu.obstacles.Combattant;
import combatJeu.modele.Coordonnee;
import combatJeu.modele.PlateauJeu;
import combatJeu.modele.ordinateur.strategie.StrategieInterface;
import combatJeu.modele.ordinateur.strategie.StrategieOffensive;
import combatJeu.modele.ordinateur.strategie.StrategieDefensive;
import entitesJeu.obstacles.CombattantFactory;
import combatJeu.GameSettings;
import combatJeu.modele.ordinateur.Noeud;
import combatJeu.modele.ordinateur.PathFinding;

import java.util.*;
import java.util.Random;

//CLASSE QUI REPERTORIE LES GRILLES D'OBJETS ET D'OBSTACLES, LES COMBATTANTS VIVANTS, AINSI QUE DES INFOS IMPORTANTES
public class Carte {
	private int tailleX;
	private int tailleY;

	private List<Combattant> listeCombattant;
    private List<Objet> listeObjet;
    private Map<Combattant, StrategieInterface> strategieMap = new HashMap<>();
    private Obstacle[][] matriceObstacle;
    private Objet[][] matriceObjet;

    private List<Coordonnee> listeCoordonneeApparition;
    private List<String> listeNomVraiJoueur;

    private int compteurCombattant; 
    private int compteurOrdinateur; 

    private int nbVraiJoueur;
    private PlateauJeu plateauJeu;


	public Carte(int tailleX, int tailleY, int nbVraiJoueur, PlateauJeu plateauJeu){
		this.tailleX = tailleX;
		this.tailleY = tailleY;

		listeCombattant = new ArrayList<>();
		listeObjet = new ArrayList<>();

		matriceObstacle = new Obstacle[tailleX][tailleY];
		matriceObjet = new Objet[tailleX][tailleY];

        this.nbVraiJoueur = nbVraiJoueur;
        this.plateauJeu = plateauJeu;
	}

    public int getNbVraiJoueur(){ return nbVraiJoueur; }
	public int getTailleX(){ return tailleX; }
	public int getTailleY(){ return tailleY; }
    public List<Coordonnee> getListeCoordonneeApparition(){ return listeCoordonneeApparition; }
    public Map<Combattant, StrategieInterface> getStrategieMap(){ return strategieMap; }
	public List<Combattant> getListeCombattant(){ return listeCombattant; }
    public List<Objet> getListeObjet(){ return listeObjet; }
	public Obstacle getObstacle(int x, int y) { 
        if (x < 0 || y < 0 || x >= tailleX || y >= tailleY)
            return null;
        return matriceObstacle[x][y]; 
    }
    public Objet getObjet(int x, int y) { 
    	if (x < 0 || y < 0 || x >= tailleX || y >= tailleY)
            return null;
    	return matriceObjet[x][y]; 
    }
    public Obstacle[][] getMatriceObstacle(){ return matriceObstacle; }
    public Objet[][] getMatriceObjet(){ return matriceObjet; }

    public void setObstacle(int x, int y, Obstacle obstacle){
    	if (x < 0 || y < 0 || x >= tailleX || y >= tailleY)
            return;
    	matriceObstacle[x][y] = obstacle;
    }
    public void setObjet(int x, int y, Objet objet){
    	if (x < 0 || y < 0 || x >= tailleX || y >= tailleY)
            return;
    	matriceObjet[x][y] = objet;
    	listeObjet.add(objet);
    }

    public void setCoordonneeApparition(List<Coordonnee> listeCoordonneeApparition){
    	this.listeCoordonneeApparition = listeCoordonneeApparition;
    }

    public void addCombattant(Combattant combattant){
    	compteurCombattant++;
    	listeCombattant.add(combattant);
    }
    public void addOrdinateur(Combattant combattant, StrategieInterface stategie){
    	compteurOrdinateur++;
    	listeCombattant.add(combattant);
    	combattant.setNom("Ordinateur 0" + compteurOrdinateur);
        strategieMap.put(combattant, stategie);
    }

    //
    public void appliquerApparitionCombattants(){
        //COPIE LISTE DE COMBATTANTS
    	List<Combattant> listeCombattantCopy = new ArrayList<>();
    	for (Combattant combattant : listeCombattant){
    		listeCombattantCopy.add(combattant);
    	}

        //PREND UN COMBATTANT ALEATOIRE ET LE FAIT APPARAITRE
        //PERMET D'AVOIR UN POSITIONNEMENT ALEATOIRE
    	while (listeCombattantCopy.size() > 0){
    		Random rand = new Random();
    		int index = rand.nextInt(listeCombattantCopy.size());
        	Combattant combattant = listeCombattantCopy.get(index);
            matriceObstacle[listeCoordonneeApparition.get(index).getX()][listeCoordonneeApparition.get(index).getY()] = combattant;
    	    
            listeCombattantCopy.remove(combattant);
            listeCoordonneeApparition.remove(listeCoordonneeApparition.get(index));
        }
    }

    //GENERATION ALEATOIRE
    public void remplirAvecProbabilite(double probaMur, double probaEau, double probaPastille){
        Random random = new Random();

        for (int x = 0; x < matriceObstacle.length; x++){
            for (int y = 0; y < matriceObstacle.length; y++){
                //MURS
                double testProbaMur = random.nextFloat();
                if (testProbaMur <= probaMur){
                    matriceObstacle[x][y] = new Mur();
                    continue;
                }
                //EAU
                double testProbaEau = random.nextFloat();
                if (testProbaEau <= probaEau){
                    matriceObstacle[x][y] = new Eau();
                    continue;
                }
            }   
        }

        //PASTILLES ENERGETIQUE
        for (int x = 0; x < matriceObjet.length; x++){
            for (int y = 0; y < matriceObjet.length; y++){
                if (matriceObstacle[x][y] != null) continue;

                double testProbaPastille = random.nextFloat();
                if (testProbaPastille <= probaPastille){
                    matriceObjet[x][y] = new PastilleEnergetique(GameSettings.ENERGIE_PASTILLE, GameSettings.DELAI_TOUR_MAX_ENERGIE_PASTILLE, GameSettings.DELAI_TOUR_ENERGIE_PASTILLE);
                }
            }   
        }
    }
    //CHOISIR LA POSITION DES JOUEURS, SUIVANT UNE DISTANCE MINIMALE POUR EVITER DES JOUEURS TROP PROCHES
    public void positionnerJoueurs(int nbJoueur, int distanceMini){
        listeCoordonneeApparition = new ArrayList<>();

        int nombreTentative = 0;
        while (nombreTentative < 1000){
            Coordonnee coord = trouveCoordonneeJoueur(distanceMini);
            listeCoordonneeApparition.add(coord);
            if (listeCoordonneeApparition.size() == nbJoueur){
                System.out.println("Generation reussie !!");
                return;
            }
        }
        System.out.println("Error: infinite loop");
    }
    //TROUVE UNE COORDONNEE LIBRE
    public Coordonnee trouveCoordonneeJoueur(int distanceMini){
        Random random = new Random();

        int x = random.nextInt(matriceObstacle.length);
        int y = random.nextInt(matriceObstacle.length);
        Coordonnee res = new Coordonnee(x, y);

        if (matriceObstacle[x][y] != null || estCoordonneeValide(res, distanceMini) == false) return trouveCoordonneeJoueur(distanceMini);

        return res;
    }
    //VERIFIE SI LA COORDONNEE EST BIEN A DISTANCE MINI + S'IL EXISTE UN CHEMIN DISPO ENTRE CE JOUEUR ET TOUS LES AUTRES
    public boolean estCoordonneeValide(Coordonnee coordTest, int distanceMini){
        for (Coordonnee coord : listeCoordonneeApparition){
            //DISTANCE MINI RESPECTEE
            int distance = plateauJeu.calculeDistance(coord, coordTest);
            if (distance < distanceMini)
                return false;

            //CHEMIN EXISTANT ENTRE JOUEURS
            PathFinding pathFinding = new PathFinding();
            List<Noeud> chemin = pathFinding.trouverChemin(matriceObstacle, matriceObjet, coordTest.getX(), coordTest.getY(), coord.getX(), coord.getY(), null);
            if (chemin == null)
                return false;
        }
        return true;
    }

    //PERMET DE DEFINIR DES PARAMETRES POUR LES JOUEURS ET FAIRE APPARAITRE TOUS LESW COMBATTANTS
    public void finition(int nombreEntites, List<String> listeTypeVraiJoueur, List<String> listeNomVraiJoueur){
        CombattantFactory cf = new CombattantFactory(
            GameSettings.ENERGIE_COMBATTANT_CLASSIQUE,
            GameSettings.PORTEE_COMBATTANT_CLASSIQUE,
            GameSettings.MUNITION_COMBATTANT_CLASSIQUE,
            GameSettings.DEGAT_TIR_COMBATTANT_CLASSIQUE,
            GameSettings.ENERGIE_COMBATTANT_SNIPER,
            GameSettings.PORTEE_COMBATTANT_SNIPER,
            GameSettings.MUNITION_COMBATTANT_SNIPER,
            GameSettings.DEGAT_TIR_COMBATTANT_SNIPER,
            GameSettings.ENERGIE_COMBATTANT_TANK,
            GameSettings.PORTEE_COMBATTANT_TANK,
            GameSettings.MUNITION_COMBATTANT_TANK,
            GameSettings.DEGAT_TIR_COMBATTANT_TANK
        );

        for (int i = 0; i < nombreEntites; i++){
            Combattant combattant = cf.createCombattantRandom();
            if (i < listeTypeVraiJoueur.size()){
                //CREER UN COMBATTANT CONTROLABLE SUIVANT UN TYPE ET UN NOM CHOISI
                combattant = cf.createCombattant(listeTypeVraiJoueur.get(i));
                combattant.setNom(listeNomVraiJoueur.get(i));
                addCombattant(combattant);
            }
            else {
                //CREER UN BOT (forcempent agressif car deffensif pas assez interessante quand il n'y a que des bots)
                StrategieInterface strategie = new StrategieOffensive(combattant, plateauJeu);
                addOrdinateur(combattant, strategie);
            }
            combattant.ajouterObserveur(plateauJeu);
        }
        appliquerApparitionCombattants();
    }
}
