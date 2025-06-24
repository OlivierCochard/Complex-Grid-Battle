package combatJeu.modele.carte.strategie;

import combatJeu.modele.carte.Carte;
import combatJeu.modele.PlateauJeu;

//interface qui permet de definir les regles de generation de la carte
public interface CarteGenerationStrategie {
    Carte genererCarte(int taille, int nbJoueur, PlateauJeu plateauJeu);
}
