package entitesJeu.obstacles;

import entitesJeu.Entite;
import outilsJeu.Observeur;
import outilsJeu.Observable;

import java.util.List;

// Classe de test pour Observeur (implémentation simple pour les tests)
class TestObserveur implements Observeur {
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

// Sous-classe concrète pour tester Obstacle
class MurObstacle extends Obstacle {

    @Override
    public void touche(int degats) {
        System.out.println("Mur a bloqué " + degats + " dégats.");
    }

    @Override
    public void destruction() {
        System.out.println("Le mur ne peut pas être détruit.");
    }
}

public class ObstacleTest {

    public static void main(String[] args) {
        // Création d'une instance de MurObstacle
        MurObstacle mur = new MurObstacle();

        // Test de l'ajout et retrait d'observateurs
        TestObserveur observeur1 = new TestObserveur();
        mur.ajouterObserveur(observeur1);

        // Simuler une notification
        List<String> evenements = List.of("Impact détecté");
        mur.notifierObserveurs(evenements);

        // Vérification si l'observateur a bien été notifié
        assert observeur1.isNotificationRecue() : "Erreur : observeur1 n'a pas reçu la notification.";
        assert observeur1.getEvenementsRecus().equals(evenements) : "Erreur : Les événements notifiés sont incorrects.";

        // Test de la méthode touche (Mur bloque les dégats)
        mur.touche(10);  
        mur.destruction();  

        System.out.println("Tous les tests sont passés avec succès !");
    }
}
