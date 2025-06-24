package combatJeu.modele.carte.strategie;

import combatJeu.modele.carte.strategie.CarteGenerationStrategie;
import combatJeu.modele.carte.Carte;
import combatJeu.modele.PlateauJeu;

//generation abuse, c'est a dire beaucoup d'obstacles, peu de pastilles et pas de distanves minimale entre les joueurs
public class ChaosCarteGenerationStrategie implements CarteGenerationStrategie {
    public Carte genererCarte(int taille, int nbJoueur, PlateauJeu plateauJeu) {
        Carte carte = new Carte(taille, taille, nbJoueur, plateauJeu);
        carte.remplirAvecProbabilite(0.4, 0.2, 0.01); // 40% murs, 20% eau, 1% pastilles
        carte.positionnerJoueurs(nbJoueur, 1); // Distance min entre joueurs
        return carte;
    }
}
