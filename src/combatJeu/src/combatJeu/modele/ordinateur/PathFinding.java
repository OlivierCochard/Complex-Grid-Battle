package combatJeu.modele.ordinateur;

import entitesJeu.obstacles.Combattant;
import entitesJeu.obstacles.Obstacle;
import entitesJeu.objets.Objet;
import entitesJeu.objets.Bombe;
import entitesJeu.objets.Mine;
import entitesJeu.objets.MineProxy;
import java.util.*;

public class PathFinding {

    //cherche chemin en fonction de la grille du jeu, et des coordonne de depart et d'arrivee,
    //cherche le chemin le plus court en esquivant les obstacles et pieges visibles
    public List<Noeud> trouverChemin(Obstacle[][] grilleObstacle, Objet[][] grilleObjet, int departX, int departY, int finX, int finY, Combattant combattant) {
        Noeud depart = new Noeud(departX, departY);
        Noeud fin = new Noeud(finX, finY);

        PriorityQueue<Noeud> listeOuverte = new PriorityQueue<>(Comparator.comparingInt(noeud -> noeud.coutG + noeud.coutH));
        Set<Noeud> listeFermee = new HashSet<>();

        listeOuverte.add(depart);
        while (!listeOuverte.isEmpty()) {
            //DEFINI MEILLEUR NOEUD
            Noeud courant = listeOuverte.poll();
            listeFermee.add(courant);

            //SI DESTINATION TROUVE
            if (courant.equals(fin)) {
                List<Noeud> chemin = reconstruireChemin(courant);
                return chemin;
            }

            //SINON VOISINS
            for (Noeud voisin : obtenirVoisins(courant, grilleObstacle, grilleObjet, listeFermee, fin, combattant)) {
                if (listeFermee.contains(voisin)) continue;

                //COUT
                int coutGCourant = courant.coutG + 1;
                int coutTotal = coutGCourant + voisin.coutH;

                //ANALYSE
                if (!listeOuverte.contains(voisin) || coutTotal < voisin.coutG + voisin.coutH) {
                    voisin.coutG = coutGCourant;
                    voisin.coutH = calculerHeuristique(voisin, fin);
                    voisin.parent = courant;

                    if (!listeOuverte.contains(voisin)) {
                        listeOuverte.add(voisin);
                    }
                }
            }
        }

        return null; //CHEMIN INEXISTANT
    }

    private List<Noeud> obtenirVoisins(Noeud noeud, Obstacle[][] grilleObstacle, Objet[][] grilleObjet, Set<Noeud> listeFermee, Noeud destination, Combattant combattant) {
	    List<Noeud> voisins = new ArrayList<>();
	    int[] dx = {0, 0, 1, -1};
	    int[] dy = {1, -1, 0, 0};

	    for (int i = 0; i < 4; i++) {
	        int nx = noeud.x + dx[i];
	        int ny = noeud.y + dy[i];

	        //VERIF LIMITES GRILLE
	        if (nx >= 0 && nx < grilleObstacle.length && ny >= 0 && ny < grilleObstacle[0].length) {
	            if (grilleObstacle[nx][ny] == null || (nx == destination.x && ny == destination.y)) {
	            	if (grilleObjet[nx][ny] != null){
	            		if (grilleObjet[nx][ny] instanceof Bombe){
	            			continue;
	            		}
	            		if (grilleObjet[nx][ny] instanceof Mine){
                            if (combattant != null){
                                //REAGARDE SI LE PIEGE EST VISIBLE PAR LE BOT OU NON
                                MineProxy mp = new MineProxy((Mine) grilleObjet[nx][ny], combattant);
                                if (mp.peutAcceder(combattant)){
                                    continue;
                                }
                            }
	            		}
	            	}
	                Noeud voisin = new Noeud(nx, ny);
	                if (!listeFermee.contains(voisin) && !voisins.contains(voisin)) {
	                    voisins.add(voisin);
	                }
	            }
	        }
	    }

	    return voisins;
	}

    private int calculerHeuristique(Noeud a, Noeud b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
    private List<Noeud> reconstruireChemin(Noeud noeud) {
        List<Noeud> chemin = new ArrayList<>();
        while (noeud != null) {
            chemin.add(noeud);
            noeud = noeud.parent;
        }
        Collections.reverse(chemin);
        return chemin;
    }

    public PathFinding() {}
}
