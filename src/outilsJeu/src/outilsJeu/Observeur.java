package outilsJeu;

import outilsJeu.Observable;

import java.util.ArrayList;
import java.util.List;

public interface Observeur {
    //methode qui permet de recevoir les evenement ou informtions d'un Observable
    void miseAJour(List<String> evenements, Observable source);
}