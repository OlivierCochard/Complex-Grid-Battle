package combatJeu.modele.ordinateur.strategie;

import entitesJeu.obstacles.Combattant;
import combatJeu.modele.PlateauJeu;

//intrface permettant l'implementation de multiples strategie de bots
public interface StrategieInterface {
    Combattant getCombattant();
    PlateauJeu getPlateauJeu();
    
    //defini les actions choisis par le bot
    String getActions();
}
