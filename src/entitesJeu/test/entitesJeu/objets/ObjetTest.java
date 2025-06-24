package entitesJeu.objets;

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

// Sous-classe concrète pour tester Objet
class Bombe extends Objet {

    @Override
    public void explosion() {
        System.out.println("La bombe a explosé !");
    }

    @Override
    public void destruction() {
        System.out.println("La bombe a été détruite.");
    }
}

public class ObjetTest {

    public static void main(String[] args) {
        // Création d'une instance de Bombe (sous-classe concrète d'Objet)
        Mine mine = new Mine(10, null);

        // Test de l'ajout et retrait d'observateurs
        TestObserveur observeur1 = new TestObserveur();
        mine.ajouterObserveur(observeur1);

        // Simuler une notification
        List<String> evenements = List.of("Explosion imminente");
        mine.notifierObserveurs(evenements);

        // Vérification si l'observateur a bien été notifié
        assert observeur1.isNotificationRecue() : "Erreur : observeur1 n'a pas reçu la notification.";
        assert observeur1.getEvenementsRecus().equals(evenements) : "Erreur : Les événements notifiés sont incorrects.";

        // Test de la méthode explosion (affiche un message lorsque la bombe explose)
        mine.explosion(); 
        mine.destruction(); 

        System.out.println("Tous les tests sont passés avec succès !");
    }
}
