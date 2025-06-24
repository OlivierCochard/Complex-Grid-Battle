package outilsJeu;

import outilsJeu.Observable;
import outilsJeu.Observeur;
import java.util.ArrayList;
import java.util.List;

// Implémentation d'un observateur simple
class ObserveurTest implements Observeur {
    private String nom;

    public ObserveurTest(String nom) {
        this.nom = nom;
    }

    @Override
    public void miseAJour(List<String> evenements, Observable source) {
        System.out.println(nom + " a reçu les événements: " + evenements + " venant du fichier source: " + source);
    }
}

// Implémentation d'une classe Observable pour les tests
class ObservableTest implements Observable {
    private List<Observeur> observeurs = new ArrayList<>();

    @Override
    public void ajouterObserveur(Observeur observeur) {
        observeurs.add(observeur);
        System.out.println(observeur + " ajouté.");
    }

    @Override
    public void retirerObserveur(Observeur observeur) {
        observeurs.remove(observeur);
        System.out.println(observeur + " retiré.");
    }

    @Override
    public void notifierObserveurs(List<String> evenements) {
        for (Observeur observeur : observeurs) {
            observeur.miseAJour(evenements, this);
        }
    }
}

public class ObservationTest {
    public static void main(String[] args) {
        // Création de l'objet Observable
        ObservableTest observable = new ObservableTest();

        // Création de quelques observateurs
        ObserveurTest observeur1 = new ObserveurTest("Observeur 1");
        ObserveurTest observeur2 = new ObserveurTest("Observeur 2");

        // Ajout des observateurs
        observable.ajouterObserveur(observeur1);
        observable.ajouterObserveur(observeur2);

        // Création d'un événement à notifier
        List<String> evenements = new ArrayList<>();
        evenements.add("Explosion");
        evenements.add("Nouveau tour");

        // Notification des observateurs
        observable.notifierObserveurs(evenements);

        // Retrait d'un observateur
        observable.retirerObserveur(observeur1);

        // Notification des observateurs après le retrait
        System.out.println("Notification après retrait d'un observateur :");
        observable.notifierObserveurs(evenements);
    }
}
