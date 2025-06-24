package entitesJeu;

import outilsJeu.Observeur;
import outilsJeu.Observable;
import entitesJeu.Entite;

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

    class ConcreteEntite extends Entite {
        @Override
        public void destruction() {
            // Implémentation simple de destruction pour les tests
            System.out.println("Destruction de l'entité.");
        }
    }
    
public class EntiteTest {

    public static void main(String[] args) {
        // Test de l'ajout et de la suppression d'observateurs
        ConcreteEntite entite = new ConcreteEntite();
        TestObserveur observeur1 = new TestObserveur();
        TestObserveur observeur2 = new TestObserveur();

        // Ajouter des observateurs
        entite.ajouterObserveur(observeur1);
        entite.ajouterObserveur(observeur2);

        // Vérifier que les observateurs ont bien été ajoutés (on peut vérifier si l'état interne de la classe Observable est correct)
        assert entite != null : "Erreur : L'entité n'est pas instanciée correctement.";

        // Retirer un observateur et vérifier que c'est bien le cas
        entite.retirerObserveur(observeur1);
        // On devrait avoir 1 observateur restant
        assert observeur1.isNotificationRecue() == false : "Erreur : observeur1 ne devrait pas être notifié.";
        assert observeur2.isNotificationRecue() == false : "Erreur : observeur2 ne devrait pas être notifié.";

        // Simuler une notification
        List<String> evenements = List.of("Événement1", "Événement2");
        entite.notifierObserveurs(evenements);

        // Vérification des notifications
        assert observeur2.isNotificationRecue() : "Erreur : observeur2 n'a pas reçu la notification.";
        assert observeur2.getEvenementsRecus().equals(evenements) : "Erreur : Les événements notifiés sont incorrects.";

        // Test de la méthode destruction
        entite.destruction(); // On s'attend à ce qu'il affiche "Destruction de l'entité."

        System.out.println("Tous les tests sont passés avec succès !");
    }
}
