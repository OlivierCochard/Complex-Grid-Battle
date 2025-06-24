package combatJeu.modele;

import combatJeu.GameSettings;
import combatJeu.modele.Coordonnee;
import combatJeu.modele.carte.Carte;
import entitesJeu.obstacles.Combattant;
import entitesJeu.obstacles.Obstacle;
import entitesJeu.obstacles.Mur;
import entitesJeu.obstacles.Eau;
import entitesJeu.objets.Objet;
import entitesJeu.objets.Bombe;
import entitesJeu.objets.Mine;
import entitesJeu.objets.PastilleEnergetique;
import entitesJeu.Entite;
import outilsJeu.Observeur;
import outilsJeu.Observable;

import java.util.*;

//classe principale qui permet de faire le lien entre beaucoups de classes
//classe etant l'unique observeuse 
public class PlateauJeu implements Observeur {
    private int combattantCourantIndex;
    private Combattant combattantCourant;

    private boolean termine;
    private int numeroTour;
    private Carte carte;
   
    public PlateauJeu() {
        numeroTour = 1;
    }

    public Carte getCarte(){ return carte; }
    public boolean getTermine(){ return termine; }
    public Combattant getCombattantCourant() { return combattantCourant; }
    public int getNumeroTour(){ return numeroTour; }

    public void setCarte(Carte carte){ 
        this.carte = carte;
        combattantCourant = carte.getListeCombattant().get(0);
        System.out.println("NOUVEAU TOUR: " + numeroTour);
    }

    public int calculeDistance(Coordonnee coord1, Coordonnee coord2){
        return Math.abs(Math.abs(coord1.getX()) - Math.abs(coord2.getX())) + Math.abs(Math.abs(coord1.getY()) - Math.abs(coord2.getY()));
    }

    //verifie qui est le plus proche entre un combattant adverse ou une pastille d'energie
    //si meme distance choisi le combattant
    public Entite getEntiteProche(Coordonnee coord){
        int min = 99999999;
        Entite entite = null;
        for (int x = 0; x < carte.getTailleX(); x++){
            for (int y = 0; y < carte.getTailleY(); y++){
                if (carte.getObstacle(x, y) != null && carte.getObstacle(x, y) instanceof Combattant){
                    if (coord.getX() == x && coord.getY() == y){
                        continue;
                    }

                    int distance = calculeDistance(coord, new Coordonnee(x, y));
                    if (distance < min){
                        min = distance;
                        entite = (Entite) carte.getObstacle(x, y);
                    }
                }
                if (carte.getObjet(x, y) != null && carte.getObjet(x, y) instanceof PastilleEnergetique){
                    if (coord.getX() == x && coord.getY() == y){
                        continue;
                    }
                    PastilleEnergetique tmp = (PastilleEnergetique) carte.getObjet(x, y);
                    if (tmp.estPrenable() == false){
                        continue;
                    }

                    int distance = calculeDistance(coord, new Coordonnee(x, y));
                    if (distance < min){
                        min = distance;
                        entite = (Entite) carte.getObjet(x, y);
                    }
                }
            }
        }
        return entite;
    }

    public Coordonnee getCoordonneeEntite(Entite entite) {
        for (int x = 0; x < carte.getTailleX(); x++) {
            for (int y = 0; y < carte.getTailleY(); y++) {
                //VERIF OBJETS
                if (carte.getObjet(x, y) != null && carte.getObjet(x, y).equals(entite)) {
                    return new Coordonnee(x, y);
                }
                //VERIFS OBSTACLES
                if (carte.getObstacle(x, y) != null && carte.getObstacle(x, y).equals(entite)) {
                    return new Coordonnee(x, y);
                }
            }
        }
        return null;
    }


    public Coordonnee getCoordonneeCombattant(Combattant combattant){
        for (int x = 0; x < carte.getTailleX(); x++){
            for (int y = 0; y < carte.getTailleY(); y++){
                if (carte.getObstacle(x, y) != null && carte.getObstacle(x, y) == (Obstacle) combattant){
                    return new Coordonnee(x, y);
                }
            }
        }
        return null;
    }
    public Combattant getCombattantFromObstacle(Obstacle obstacle){
        for (int i = 0; i < carte.getListeCombattant().size(); i++){
            if (obstacle == (Obstacle) carte.getListeCombattant().get(i)){
                return  carte.getListeCombattant().get(i);
            }
        }
        return null;
    }

    //permet de definir la coordonne possible a partir d'une coordonne et d'une
    //direction afin de predire si la possible case est libre et existante
    public Coordonnee getNouvelleCoordonnee(Coordonnee coordonne, String direction){
        if (coordonne == null) return null;

        if (direction.equals("haut")){
            if (coordonne.getY() + 1 < 0 || coordonne.getY() + 1 >= carte.getTailleY()) return null;
            return new Coordonnee(coordonne.getX(), coordonne.getY() + 1);
        }
        if (direction.equals("droite")){
            if (coordonne.getX() + 1 < 0 || coordonne.getX() + 1 >= carte.getTailleX()) return null;
            return new Coordonnee(coordonne.getX() + 1, coordonne.getY());
        }
        if (direction.equals("bas")){
            if (coordonne.getY() - 1 < 0 || coordonne.getY() - 1 >= carte.getTailleY()) return null;
            return new Coordonnee(coordonne.getX(), coordonne.getY() - 1);
        }
        if (direction.equals("gauche")){
            if (coordonne.getX() - 1 < 0 || coordonne.getX() - 1 >= carte.getTailleX()) return null;
            return new Coordonnee(coordonne.getX() - 1, coordonne.getY());
        }

        return null;
    }

    //verifie si la case en (x, y) est libre pour la pose d'un objet
    public boolean demandePoseObjet(int x, int y){
        Coordonnee coordonneeCombattantCourant = getCoordonneeCombattant(combattantCourant);
        x += coordonneeCombattantCourant.getX();
        y += coordonneeCombattantCourant.getY();

        Obstacle obstacle = carte.getObstacle(x, y);
        if (obstacle != null){
            return false;
        }

        Objet objet = carte.getObjet(x, y);
        if (objet != null){
            return false;
        }

        return true;
    }
    public void appliquePoseObjet(int x, int y, Objet objet){
        if (objet == null){ return; }
        if (combattantCourant == null){ return; }

        Coordonnee coordonneeCombattantCourant = getCoordonneeCombattant(combattantCourant);
        if (coordonneeCombattantCourant == null) return ;
        x += coordonneeCombattantCourant.getX();
        y += coordonneeCombattantCourant.getY();

        carte.setObjet(x, y, objet);
        System.out.println("[Piège] posé en (" + x + ", " + y + ")");
    }

    public boolean demandeDeplacement(String direction){
        Coordonnee coordonnee = getCoordonneeCombattant(combattantCourant);
        Coordonnee nouvelleCoordonnee = getNouvelleCoordonnee(coordonnee, direction);

        if (nouvelleCoordonnee == null || carte.getObstacle(nouvelleCoordonnee.getX(), nouvelleCoordonnee.getY()) != null){
            return false;
        }
        return true;
    }
    public void appliqueDeplacement(String direction) {
        Coordonnee ancienneCoordonnees = getCoordonneeCombattant(combattantCourant);
        Coordonnee nouvellesCoordonnees = getNouvelleCoordonnee(ancienneCoordonnees, direction);

        //DEPLACEMENT POSSIBLE ?
        if (nouvellesCoordonnees == null) {
            System.err.println("Erreur : Déplacement invalide pour la direction " + direction);
            return;
        }

        //DEPLACEMENT
        carte.setObstacle(ancienneCoordonnees.getX(), ancienneCoordonnees.getY(), null);
        carte.setObstacle(nouvellesCoordonnees.getX(), nouvellesCoordonnees.getY(), combattantCourant);

        //VERIF OBJET, SI OUI ACTIVE EFFET
        Objet objet = carte.getObjet(nouvellesCoordonnees.getX(), nouvellesCoordonnees.getY());
        if (objet != null) {
            objet.explosion();
        }
    }


    private Coordonnee getCoordonneeObjet(Objet objet){
        for (int x = 0; x < carte.getTailleX(); x++){
            for (int y = 0; y < carte.getTailleY(); y++){
                if (carte.getObjet(x, y) != null && carte.getObjet(x, y) == objet){
                    return new Coordonnee(x, y);
                }
            }
        }
        return null;
    }

    public List<Obstacle> getObstacleTouche(String direction, int portee){
        List<Obstacle> res = new ArrayList<Obstacle>();
        Coordonnee coordonneeCombattantCourant = getCoordonneeCombattant(combattantCourant);

        if (combattantCourant == null) return res;
        if (coordonneeCombattantCourant == null) return res;

        //regarde les cibles touches en verticale
        if (direction.equals("verticale")){
            int fin = (coordonneeCombattantCourant.getY() + portee + 1);
            int depart = (coordonneeCombattantCourant.getY() + 1);
            for (int y = depart; y < fin ; y++){
                Obstacle obstacle = carte.getObstacle(coordonneeCombattantCourant.getX(), y);
                if (obstacle != null){
                    res.add(obstacle);
                    if (obstacle instanceof Mur || obstacle instanceof Combattant)
                        break;
                }
            }
            //BAS
            fin = (coordonneeCombattantCourant.getY() - portee - 1);
            depart = (coordonneeCombattantCourant.getY() - 1);
            for (int y = depart; y > fin; y--){
                Obstacle obstacle = carte.getObstacle(coordonneeCombattantCourant.getX(), y);
                if (obstacle != null){
                    res.add(obstacle);
                    if (obstacle instanceof Mur || obstacle instanceof Combattant)
                        break;
                }
            }
        }
        //regarde les cibles touches en horizontale
        else if (direction.equals("horizontale")){
            //DROITE
            int fin = (coordonneeCombattantCourant.getX() + portee + 1);
            int depart = (coordonneeCombattantCourant.getX() + 1);
            for (int x = depart; x < fin; x++){
                Obstacle obstacle = carte.getObstacle(x, coordonneeCombattantCourant.getY());
                res.add(obstacle);
                    if (obstacle instanceof Mur || obstacle instanceof Combattant)
                        break;
            }
            //GAUCHE
            fin = (coordonneeCombattantCourant.getX() - portee - 1);
            depart = (coordonneeCombattantCourant.getX() - 1);
            for (int x = depart; x > fin; x--){
                Obstacle obstacle = carte.getObstacle(x, coordonneeCombattantCourant.getY());
                res.add(obstacle);
                    if (obstacle instanceof Mur || obstacle instanceof Combattant){
                        break;
                    }
            }
        }

        return res;
    }

    public void changementCombattantCourant() {
        //VERIF SI PARTIE FINI CAR PLUS ASSEZ DE JOUEURS POUR CONTINUER LA PARTIE
        if (carte.getListeCombattant().size() <= 1) { 
            System.out.println("\nFINITO, GAGNANT: " + carte.getListeCombattant().get(0).getNom());
            termine = true;
            return;
        }
        combattantCourantIndex++;

        //NOUVEAU TOUR => RESET
        if (combattantCourantIndex >= carte.getListeCombattant().size()) {
            numeroTour++;
            System.out.println("\nNOUVEAU TOUR: " + numeroTour);
            combattantCourantIndex = 0;

            //OBJETS UPDATE
            for (int i = 0; i < carte.getListeObjet().size(); i++) {
                if (carte.getListeObjet().get(i) instanceof Bombe) {
                    Bombe bombe = (Bombe) carte.getListeObjet().get(i);
                    bombe.reductionTourRestant();
                } else if (carte.getListeObjet().get(i) instanceof PastilleEnergetique) {
                    PastilleEnergetique pastilleEnergetique = (PastilleEnergetique) carte.getListeObjet().get(i);
                    pastilleEnergetique.reductionTourRestant();
                }
            }
        }

        //COMBATTANT VALIDE ?
        if (combattantCourantIndex < carte.getListeCombattant().size()) {
            combattantCourant = carte.getListeCombattant().get(combattantCourantIndex);
            combattantCourant.initiative();
        } else {
            System.err.println("Erreur : index du combattant courant invalide.");
        }
    }

    //permet de voirs quels obstacles ont ete touché par les explosifs
    public List<Obstacle> getObstaclesAutourCoordonnee(Coordonnee coord){
        List<Obstacle> res = new ArrayList<>();
        if (coord == null){
            return res;
        }

        for (int i = -1; i <= 1; i++){
            for (int y = -1; y <= 1; y++){
                Obstacle obstacle = carte.getObstacle(coord.getX() + i, coord.getY() + y);
                if (obstacle != null && obstacle instanceof Combattant){
                    res.add((Combattant) obstacle);
                }
            }
        }
        return res;
    }

    @Override
    //permet de recevoir la notification des observables afin de gerer la partie en fonction
    public void miseAJour(List<String> evenements, Observable source){
        //EXPLOSION
        if (evenements.get(0).equals("explosion")){
            //COORD
            Coordonnee coord = getCoordonneeObjet((Objet) source);
            if (coord == null) return;

            //BOMBE
            if (source instanceof Bombe){
                System.out.println("Une bombe vient d'exploser !");
                Bombe bombe = (Bombe) source;
                if (bombe == null) return;
                List<Obstacle> listeObstacleTmp = getObstaclesAutourCoordonnee(coord);
                for (int i = 0; i < listeObstacleTmp.size(); i++){
                    listeObstacleTmp.get(i).touche(bombe.getDegats());
                }
                bombe.destruction();
                return;
            }
            //MINE
            if (source instanceof Mine){
                System.out.println("Une mine vient d'exploser !");
                Mine mine = (Mine) source;
                Combattant combattant = (Combattant) carte.getObstacle(coord.getX(), coord.getY());
                combattant.touche(mine.getDegats());
                mine.destruction();
                return;
            }
            //PASTILLE ENERGETIQUE
            if (source instanceof PastilleEnergetique){
                PastilleEnergetique pastilleEnergetique = (PastilleEnergetique) source;
                Combattant combattant = (Combattant) carte.getObstacle(coord.getX(), coord.getY());
                combattant.gainEnergie(pastilleEnergetique.getEnergie());
                pastilleEnergetique.reinitialiser();
                return;
            }
            return;
        }
        //DESTRUCTION
        if (evenements.get(0).equals("destruction")){
            //OBJET
            if (evenements.get(1).equals("objet")){
                Objet objet = (Objet) source;
                Coordonnee coord = getCoordonneeObjet(objet);
                carte.setObjet(coord.getX(), coord.getY(), null);
                carte.getListeObjet().remove(objet);
                return;
            }
            //COMBATTANT
            if (evenements.get(1).equals("combattant")){
                Combattant combattant = (Combattant) source;
                Coordonnee coord = getCoordonneeCombattant(combattant);
                carte.setObstacle(coord.getX(), coord.getY(), null);
                carte.getListeCombattant().remove(combattant);
                return;
            }
        }

        //PIEGER
        if (evenements.get(0).equals("pieger")){
            //VERIFS
            int x = Integer.valueOf(evenements.get(2));
            int y = Integer.valueOf(evenements.get(3));
            boolean peutPoser = demandePoseObjet(x, y);
            if (peutPoser == false){
                System.out.println("Position impossible");
                return;
            }

            //ENERGIE
            Integer coutEnergetique = Integer.valueOf(evenements.get(4));
            combattantCourant.perteEnergie(coutEnergetique);

            //EFFETS
            Objet objet = null;
            if (evenements.get(1).equals("mine")){
                objet = new Mine(GameSettings.DEGAT_MINE, combattantCourant);
            }
            if (evenements.get(1).equals("bombe")){
                objet = new Bombe(GameSettings.DEGAT_BOMBE, combattantCourant, GameSettings.DELAI_TOUR_EXPLOSION_BOMBE);
            }
            objet.ajouterObserveur(this);
            appliquePoseObjet(x, y, objet);
            changementCombattantCourant();
        }
        //DEPLACEMENT
        if (evenements.get(0).equals("deplacement")){
            System.out.println(combattantCourant.getNom() + " s'est deplacé");

            //VERIFS
            String direction = evenements.get(1);
            if (demandeDeplacement(direction) == false){
                System.out.println("Deplacement impossible");
                return;
            } 

            //ENERGIE
            Integer coutEnergetique = Integer.valueOf(evenements.get(2));
            combattantCourant.perteEnergie(coutEnergetique);

            //EFFETS
            appliqueDeplacement(direction);
            changementCombattantCourant();
            return;
        }
        //PROJECTILE
        if (evenements.get(0).equals("projectile")){
            //VERIFS
            if (combattantCourant.getMunition() <= 0) {
                System.out.println("Plus de munitions");
                return;
            }

            System.out.println(combattantCourant.getNom() + " tir un projectile");

            //DELAI
            combattantCourant.setDelaiTir(GameSettings.DELAI_ACTION_TIRER);

            //ENERGIE
            Integer coutEnergetique = Integer.valueOf(evenements.get(2));
            combattantCourant.perteEnergie(coutEnergetique);

            //EFFETS
            String direction = evenements.get(1);
            List<Obstacle> listeObstacleTouchés = getObstacleTouche(direction, combattantCourant.getPortee());
            if (listeObstacleTouchés.size() > 0){
                for (Obstacle obstacle : listeObstacleTouchés){
                    if (obstacle == null || combattantCourant == null) continue;
                    obstacle.touche(combattantCourant.getDegatTir());
                }
            }
            combattantCourant.perteMunition();
            changementCombattantCourant();
            return;
        }
        //PROJECTILE
        if (evenements.get(0).equals("bouclier")){
            System.out.println(combattantCourant.getNom() + " se met un bouclier");
            //DELAI
            combattantCourant.setDelaiBouclier(GameSettings.DELAI_ACTION_BOUCLIER);

            //ENERGIE
            Integer coutEnergetique = Integer.valueOf(evenements.get(1));
            combattantCourant.perteEnergie(coutEnergetique);
            
            //AUTRE
            combattantCourant.setBouclier(true);
            changementCombattantCourant();
            return;
        }
    }
}