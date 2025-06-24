package combatJeu.controlleur;

import combatJeu.GameSettings;
import combatJeu.controlleur.GeneralControlleur;
import combatJeu.modele.PlateauJeu;
import combatJeu.vue.TerminalVue;
import entitesJeu.obstacles.Combattant;
import entitesJeu.obstacles.Obstacle;
import entitesJeu.objets.Objet;
import entitesJeu.objets.Mine;
import entitesJeu.objets.Bombe;
import combatJeu.modele.ordinateur.strategie.StrategieInterface;

import java.util.*;

//permet au joueur courant jouer son tour a partir du terminal
public class TerminalControlleur {
	private PlateauJeu plateauJeu;
	private TerminalVue terminalVue;
    private GeneralControlleur generalControlleur;

	public TerminalControlleur(PlateauJeu plateauJeu, TerminalVue terminalVue, GeneralControlleur generalControlleur){
		this.plateauJeu = plateauJeu;
		this.terminalVue = terminalVue;
        this.generalControlleur = generalControlleur;
		jouer();
	}

	private void jouer(){
		Scanner scanner = new Scanner(System.in);

        //si la partie est toujours en cours, peut jouer
		while (plateauJeu.getTermine() == false){
            Combattant combattantCourant = plateauJeu.getCombattantCourant();

            //SI BOT
            Map<Combattant, StrategieInterface> tmp = plateauJeu.getCarte().getStrategieMap();
            if (tmp.containsKey(combattantCourant)){
                if (plateauJeu.getCarte().getNbVraiJoueur() > 0){
                    System.out.println(terminalVue);
                }

                String actions = tmp.get(plateauJeu.getCombattantCourant()).getActions();
                List<String> listeAction = new ArrayList<>(Arrays.asList(actions.split(" ")));
                generalControlleur.appliquerAction(listeAction, combattantCourant);

                if (plateauJeu.getCarte().getNbVraiJoueur() == 0){
                    System.out.println(terminalVue);
                    System.out.println("Appuyez sur ENTRER pour continuer...");
                    scanner.nextLine();
                }
                continue;
            }

            //SI VRAI JOUEUR
            System.out.println(terminalVue);
            String actionTexte = "[" + combattantCourant.getNom() + "] => Entrez une action:";
            actionTexte += "\n(0) passer";
            actionTexte += "\n(" + GameSettings.ENERGIE_DEPLACEMENT + ") marcher [haut, droite, bas, gauche]";
            if (combattantCourant.getDelaiTir() == 0)
                actionTexte += "\n(" + GameSettings.ENERGIE_PROJECTILE + ") tirer [verticale, horizontale]";
            if (combattantCourant.getDelaiBouclier() == 0)
                actionTexte += "\n(" + GameSettings.ENERGIE_BOUCLIER + ") bouclier";
            actionTexte += "\n(" + GameSettings.ENERGIE_PIEGE + ") pieger [bombe, mine] [-1...1] [-1...1]";
            System.out.println(actionTexte);

            String actions = scanner.nextLine();
            List<String> listeAction = new ArrayList<>(Arrays.asList(actions.split(" ")));

            //ACTIONS POSSIBLES
            if (listeAction.get(0).equals("marcher")){
                if (listeAction.size() == 1){
                    System.out.println("Entrez une direction (haut, droite, bas, gauche): ");
                    listeAction.add(scanner.nextLine());
                }
                generalControlleur.appliquerAction(listeAction, combattantCourant);
                continue;
            }
            if (listeAction.get(0).equals("tirer")){
                if (combattantCourant.getDelaiTir() != 0){
                    System.out.println("Attendez encore " + combattantCourant.getDelaiTir() + " tour(s) avant de tirer");
                    continue;
                }

                if (listeAction.size() == 1){
                    System.out.println("Entrez une direction (verticale, horizontale): ");
                    listeAction.add(scanner.nextLine());
                }
                generalControlleur.appliquerAction(listeAction, combattantCourant);
                continue;
            }
            if (listeAction.get(0).equals("pieger")){
                if (listeAction.size() == 1){
                    System.out.println("Entrez un piege (mine, bombe): ");
                    listeAction.add(scanner.nextLine());
                }
                if (listeAction.size() == 2){
                    System.out.println("Entrez une valeur x -> [-1...1]: ");
                    listeAction.add(scanner.nextLine());
                }
                if (listeAction.size() == 3){
                    System.out.println("Entrez une valeur y -> [-1...1]: ");
                    listeAction.add(scanner.nextLine());
                }
                generalControlleur.appliquerAction(listeAction, combattantCourant);
                continue;
            }
            if (listeAction.get(0).equals("bouclier")){
                if (combattantCourant.getDelaiBouclier() != 0){
                    System.out.println("Attendez encore " + combattantCourant.getDelaiBouclier() + " tour(s) d'utiliser votre bouclier");
                    continue;
                }
                generalControlleur.appliquerAction(listeAction, combattantCourant);
                continue;
            }
            if (listeAction.get(0).equals("passer")){
                generalControlleur.appliquerAction(listeAction, combattantCourant);
                continue;
            }
            System.out.println("Action non reconnue : " + listeAction.get(0) + "\n");
		}
    }
}
