package combatJeu;

import combatJeu.vue.GraphiqueVuePrincipal;
import combatJeu.vue.TerminalVue;
import combatJeu.modele.carte.Carte;
import combatJeu.modele.PlateauJeu;
import combatJeu.modele.carte.CarteFactory;
import combatJeu.controlleur.TerminalControlleur;
import combatJeu.controlleur.GeneralControlleur;
import combatJeu.modele.carte.strategie.CarteGenerationStrategie;
import combatJeu.modele.carte.strategie.EquilibreCarteGenerationStrategie;
import combatJeu.modele.carte.strategie.ChaosCarteGenerationStrategie;

import java.util.*;

//classe qui sert d'executable à mon projet
public class ClassePrincipale{
	public static void main(String[] args){
        //enchainement de questions pour effectuer un setup customisé
        Scanner scanner = new Scanner(System.in);

        //MODE GENERATION
        System.out.println("Quel mode de generation voulez vous ? Parmi les choix suivant: [aleatoire, niveaux] ?");
        String modeGeneration = scanner.nextLine();
        if (modeGeneration.equals("aleatoire") == false && modeGeneration.equals("niveaux") == false){
            System.out.println("Le mode (" + modeGeneration + ") n'existe pas !!!");
            return;
        }

        //MODE ALEATOIRE
        String modeAleatoire = "";
        if (modeGeneration.equals("aleatoire")){
            System.out.println("Quel mode de generation aleatoire voulez vous ? Parmi les choix suivant: [equilibre, chaos] ?");
            modeAleatoire = scanner.nextLine();
            if (modeAleatoire.equals("equilibre") == false && modeAleatoire.equals("chaos") == false){
                System.out.println("Le mode (" + modeAleatoire + ") n'existe pas !!!");
                return;
            }
        }

        //TAILLE CARTE
        int tailleCarte = 0;
        if (modeGeneration.equals("aleatoire")){
            System.out.println("Quelle taille de carte voulez-vous: [1...n] ?");
            tailleCarte = Integer.parseInt(scanner.nextLine());
            if (tailleCarte < 1){
                System.out.println("La taille (" + tailleCarte + ") ne doit pas etre inferieur à 1 !!!");
                return;
            }
        }

        //NB JOUEURS
        int nbJoueur = 0;
        if (modeGeneration.equals("niveaux")){
            System.out.println("Combien de joueurs voulez-vous ? Parmi les choix suivant: [2, 4, 6, 8] ?");
            nbJoueur = Integer.parseInt(scanner.nextLine());
            if (nbJoueur != 2 && nbJoueur != 4 && nbJoueur != 6 && nbJoueur != 8){
                System.out.println("Le nombre (" + nbJoueur + ") n'a pas encore été implémenté !!!");
                return;
            }
        }
        else if (modeGeneration.equals("aleatoire")){
            System.out.println("Combien de joueurs voulez-vous ? Parmi la fourchette suivante: [2...n] ?");
            nbJoueur = Integer.parseInt(scanner.nextLine());
            if (nbJoueur < 2){
                System.out.println("Le nombre (" + nbJoueur + ") doit etre 2 minimum !!!");
                return;
            }
        }
        System.out.println("Lancement du mode " + modeGeneration + " pour " + nbJoueur + " joueurs...");
        
        //NB VRAI JOUEUR
        System.out.println("Pour combien de joueurs voulez-vous avoir le controle ? [0..." + nbJoueur + "]");
        int nbVraiJoueur = Integer.parseInt(scanner.nextLine());
        if (nbVraiJoueur < 0 || nbVraiJoueur > nbJoueur){
            System.out.println("Le nombre (" + nbVraiJoueur + ") n'est pas compris entre 0 et " + nbJoueur + " !!!");
            return;
        }

        //NOMS VRAIS JOUEURS
        List<String> listeNomVraiJoueur = new ArrayList<String>();
        for (int i = 0; i < nbVraiJoueur; i++){
            System.out.println("Entrez le nom du joueur 0" + (i+1) + ":");
            String nom = scanner.nextLine();
            listeNomVraiJoueur.add(nom);
        }

        //TYPES VRAIS JOUEURS
        List<String> listeTypeVraiJoueur = new ArrayList<String>();
        for (int i = 0; i < nbVraiJoueur; i++){
            System.out.println("Entrez le type du joueur 0" + (i+1) + ": [classique, sniper, tank, aleatoire]");
            String type = scanner.nextLine();
            if (type.equals("classique") || type.equals("sniper") || type.equals("tank") || type.equals("aleatoire"))
                listeTypeVraiJoueur.add(type);
            else {
                System.out.println("Type non reconnu: " + type);
                i--;
            }
        }

        //GENERATION
        PlateauJeu plateauJeu = new PlateauJeu();
        Carte carte = null;
        //CARTE EXISTANTES
        if (modeGeneration.equals("niveaux")){
            carte = CarteFactory.creerCarte(plateauJeu, nbJoueur, nbVraiJoueur, listeNomVraiJoueur, listeTypeVraiJoueur);
        }
        //CARTE AVEC GENERATION ALEATOIRE
        else if (modeGeneration.equals("aleatoire")){
            CarteGenerationStrategie carteGenerationStrategie = null;
            //MODE SOFT
            if (modeAleatoire.equals("equilibre")){
                carteGenerationStrategie = new EquilibreCarteGenerationStrategie();
            }
            //MODE HARDCORE
            else if (modeAleatoire.equals("chaos")){
                carteGenerationStrategie = new ChaosCarteGenerationStrategie();
            }
            carte = carteGenerationStrategie.genererCarte(tailleCarte, nbJoueur, plateauJeu);
            carte.finition(nbJoueur, listeTypeVraiJoueur, listeNomVraiJoueur);
        }

        //MODE VISUEL
        System.out.println("Quel mode voulez-vous ? Parmi les choix suivant: [terminal, graphique] ?");
        String mode = scanner.nextLine();
        if (mode.toLowerCase().equals("terminal")){
            System.out.println("Mode du jeu en mode terminal...");
            plateauJeu.setCarte(carte);
            TerminalVue terminalVue = new TerminalVue(plateauJeu, carte);
            GeneralControlleur generalControlleur = new GeneralControlleur(plateauJeu);
            TerminalControlleur terminalControlleur = new TerminalControlleur(plateauJeu, terminalVue, generalControlleur);
        }
        else if (mode.toLowerCase().equals("graphique")){
            System.out.println("Lancement du jeu en mode graphique...");
            plateauJeu.setCarte(carte);
            GeneralControlleur generalControlleur = new GeneralControlleur(plateauJeu);
            GraphiqueVuePrincipal mainFrame = new GraphiqueVuePrincipal(carte, plateauJeu, generalControlleur);
            mainFrame.setVisible(true);
            return;
        }
        else {
            System.out.println("Erreur: Mode non reconnu");
            return;
        }
    }
}
