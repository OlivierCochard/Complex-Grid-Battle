package combatJeu.modele.carte.strategie;

import combatJeu.modele.carte.strategie.CarteGenerationStrategie;
import combatJeu.modele.carte.Carte;
import combatJeu.modele.PlateauJeu;

//generation soft en murs, eau avec bcp de pastilles, et une distance minimales entre les joueurs
public class EquilibreCarteGenerationStrategie implements CarteGenerationStrategie {
    public Carte genererCarte(int taille, int nbJoueur, PlateauJeu plateauJeu) {
        Carte carte = new Carte(taille, taille, nbJoueur, plateauJeu);
        carte.remplirAvecProbabilite(0.2, 0.1, 0.05); // 20% murs, 10% eau, 5% pastilles
        carte.positionnerJoueurs(nbJoueur, taille/2); // Distance min entre joueurs
        return carte;
    }
}
