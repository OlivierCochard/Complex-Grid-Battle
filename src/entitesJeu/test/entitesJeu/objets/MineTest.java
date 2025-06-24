package entitesJeu.objets;

import entitesJeu.objets.Mine;
import entitesJeu.obstacles.Combattant;
import outilsJeu.Observeur;
import outilsJeu.Observable;

import java.util.ArrayList;
import java.util.List;

// Classe de test pour observer les notifications
class TestObserveur_ implements Observeur {
    boolean aRecueNotification = false;
    List<String> evenementsRecus = null;

    @Override
    public void miseAJour(List<String> evenements, Observable observable) {
        this.aRecueNotification = true;
        this.evenementsRecus = evenements;
    }

    public boolean isNotificationRecue() {
        return aRecueNotification;
    }

    public List<String> getEvenementsRecus() {
        return evenementsRecus;
    }
}

public class MineTest {

    public static void main(String[] args) {
        // Création d'un combattant (poseur de la mine)
        Combattant poseur = new Combattant("Guerrier", 100, 50, 10, 20);

        // Création d'une instance de Mine
        Mine mine = new Mine(100, poseur);  // Utilisation du constructeur avec dégats et poseur

        // Test de l'ajout d'un observateur
        TestObserveur observeur1 = new TestObserveur();
        mine.ajouterObserveur(observeur1);

        // Simuler l'explosion de la mine
        mine.explosion();
        // Vérification si l'observateur a bien été notifié
        assert observeur1.isNotificationRecue() : "Erreur : observeur1 n'a pas reçu la notification.";
        assert observeur1.getEvenementsRecus().equals(List.of("explosion")) : "Erreur : Les événements notifiés sont incorrects.";

        // Simuler la destruction de la mine
        mine.destruction();
        // Vérification si l'observateur a bien été notifié
        assert observeur1.getEvenementsRecus().equals(List.of("destruction", "objet")) : "Erreur : Les événements notifiés pour la destruction sont incorrects.";

        System.out.println("Tous les tests sont passés avec succès !");
    }
}
