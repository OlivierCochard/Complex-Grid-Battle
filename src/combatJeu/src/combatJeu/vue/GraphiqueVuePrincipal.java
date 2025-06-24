package combatJeu.vue;

import combatJeu.controlleur.GeneralControlleur;
import combatJeu.modele.carte.Carte;
import combatJeu.modele.PlateauJeu;
import combatJeu.modele.Coordonnee;
import entitesJeu.obstacles.Combattant;
import entitesJeu.obstacles.Obstacle;
import entitesJeu.obstacles.Mur;
import entitesJeu.obstacles.Eau;
import entitesJeu.objets.Objet;
import entitesJeu.objets.Mine;
import entitesJeu.objets.MineProxy;
import entitesJeu.objets.Bombe;
import entitesJeu.objets.PastilleEnergetique;
import combatJeu.modele.ordinateur.strategie.StrategieInterface;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GraphiqueVuePrincipal extends JFrame {
    private JTable combattantTable;
    private JPanel jeuPanel;
    private JPanel boutonPanel;
    private JLabel tourLabel;
    private int caseSize = 32;
    private int cercleSize = 20;

    private List<Combattant> combattants;
    private Carte carte;
    private PlateauJeu plateauJeu;
    private GeneralControlleur generalControlleur;

    private String actionCourante;

    //setup de tous les elements visuels
    public GraphiqueVuePrincipal(Carte carte, PlateauJeu plateauJeu, GeneralControlleur generalControlleur) {
        this.combattants = carte.getListeCombattant();
        this.carte = carte;
        this.plateauJeu = plateauJeu;
        this.generalControlleur = generalControlleur;

        setTitle("Jeu de Combat");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initTable();
        initJeuPanel();
        initBoutonPanel();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    //setup de la tables avec des colonnes d'uinformations pour tous les joueurs vivants
    private void initTable() {
	    String[] columnNames = {"Nom", "Type", "Bouclier", "Energie", "Portee", "DegatTir", "Munition", "DelaiTir", "DelaiBouclier"};
	    Object[][] data = new Object[combattants.size()][9];

	    for (int i = 0; i < combattants.size(); i++) {
	        Combattant combattant = combattants.get(i);
	        data[i][0] = combattant.getNom();
	        data[i][1] = combattant.getType();
	        data[i][2] = combattant.getBouclier();
	        data[i][3] = combattant.getEnergie();
	        data[i][4] = combattant.getPortee();
	        data[i][5] = combattant.getDegatTir();
	        data[i][6] = combattant.getMunition();
	        data[i][7] = combattant.getDelaiTir();
	        data[i][8] = combattant.getDelaiBouclier();
	    }

	    //texte pour voir le numero de tour ainsi que le joueurCourant
	    combattantTable = new JTable(data, columnNames);
	    tourLabel = new JLabel("Tour " + plateauJeu.getNumeroTour() + " | C'est au tour de " + plateauJeu.getCombattantCourant().getNom(), JLabel.CENTER);
    	tourLabel.setFont(new Font("Arial", Font.BOLD, 16));
	    JScrollPane scrollPane = new JScrollPane(combattantTable);
	    scrollPane.setPreferredSize(new Dimension(1920, 150));

	    //legende des elements visuels sur la carte
	    JLabel legendLabel = new JLabel("<html>"
        + "<b><span style='color:blue;'>■ BLEU</span>: eau, "
        + "<span style='color:gray;'>■ BLANC</span>: mur, "
        + "<span style='color:green;'>● VERT</span>: joueur courant, "
        + "<span style='color:red;'>● ROUGE</span>: adversaire, "
        + "<span style='color:gray;'>● BLANC</span><span style='color:orange;'>/JAUNE</span>: pastille, "
        + "<span style='color:black;'>● NOIR</span><span style='color:gray;'>/CHIFFRE</span></span>: bombe, "
        + "<span style='color:black;'>● NOIR</span><span style='color:red;'>/ROUGE</span></span>: mine"
        + "</b></html>", JLabel.CENTER);
    	legendLabel.setFont(new Font("Arial", Font.ITALIC, 12));

	    JPanel panel = new JPanel(new BorderLayout());
	    panel.add(tourLabel, BorderLayout.NORTH);
	    panel.add(scrollPane, BorderLayout.CENTER);
	    panel.add(legendLabel, BorderLayout.SOUTH);
	    add(panel, BorderLayout.NORTH);
	}
	//rafraichissement de la table d'information et du tour / joueur courant
    private void refreshTable() {
	    String[] columnNames = {"Nom", "Type", "Bouclier", "Energie", "Munition", "DelaiTir", "DelaiBouclier"};
	    Object[][] data = new Object[combattants.size()][7];

	    for (int i = 0; i < combattants.size(); i++) {
	        Combattant combattant = combattants.get(i);
	        data[i][0] = combattant.getNom();
	        data[i][1] = combattant.getType();
	        data[i][2] = combattant.getBouclier();
	        data[i][3] = combattant.getEnergie();
	        data[i][4] = combattant.getMunition();
	        data[i][5] = combattant.getDelaiTir();
	        data[i][6] = combattant.getDelaiBouclier();
	    }

	    combattantTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
	    tourLabel.setText("Tour " + plateauJeu.getNumeroTour() + " | C'est au tour de " + plateauJeu.getCombattantCourant().getNom());
	}

	//prepare la section de jeu avec la grille qui sert de carte
    private void initJeuPanel() {
        jeuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGrid(g);
            }
        };

        jeuPanel.setPreferredSize(new Dimension(1920, 800));
        jeuPanel.setBackground(Color.BLACK);
        add(jeuPanel, BorderLayout.CENTER);
    }
    //dessine la grille de jeu
    private void drawGrid(Graphics g) {
	    int panelWidth = jeuPanel.getWidth();
	    int panelHeight = jeuPanel.getHeight();

	    int cols = carte.getTailleX();
	    int rows = carte.getTailleY();

	    int gridWidth = cols * caseSize;
	    int gridHeight = rows * caseSize;

	    int xMargin = (panelWidth - gridWidth) / 2;
	    int yMargin = (panelHeight - gridHeight) / 2;

	    for (int row = 0; row < rows; row++) {
	        int y = yMargin + (rows - 1 - row) * caseSize;
	        for (int col = 0; col < cols; col++) {
	            int x = xMargin + col * caseSize;
	            drawCase(g, x, y, col, row);
	        }
	    }
	}
	//dessine la case suivant l'element dessus
	private void drawCase(Graphics g, int x, int y, int col, int row) {
	    //OBSTACLES
	    Obstacle obstacle = carte.getObstacle(col, row);
	    if (obstacle instanceof Eau) {
	        g.setColor(Color.BLUE);
	    } else if (obstacle instanceof Mur) {
	        g.setColor(Color.WHITE);
	    } else {
	        g.setColor(Color.DARK_GRAY);
	    }
	    g.fillRect(x, y, caseSize, caseSize);
	    g.setColor(Color.BLACK);
	    g.drawRect(x, y, caseSize, caseSize);

	    //OBJETS
	    Objet objet = carte.getObjet(col, row);
	    if (objet != null) drawObjet(g, objet, x, y);

	    //COMBATTANTS
	    if (obstacle instanceof Combattant) {
	        drawCombattant(g, (Combattant) obstacle, x, y);
	    }
	}
	//dessine la case si c'est un objet dessus
	private void drawObjet(Graphics g, Objet objet, int x, int y) {
	    int cercleX = x + (caseSize - cercleSize) / 2;
	    int cercleY = y + (caseSize - cercleSize) / 2;

	    if (objet instanceof Bombe) {
		    g.setColor(Color.BLACK);
		    g.fillOval(cercleX, cercleY, cercleSize, cercleSize);
		    g.setColor(Color.WHITE);
		    Bombe bombe = (Bombe) objet;

		    g.setFont(new Font("Arial", Font.PLAIN, 20));
		    String text = bombe.getTourRestant() + "";
		    FontMetrics metrics = g.getFontMetrics();
		    int textWidth = metrics.stringWidth(text);
		    int textHeight = metrics.getHeight();
		    int xText = cercleX + (cercleSize - textWidth) / 2;
		    int yText = cercleY + (cercleSize + textHeight) / 2 - metrics.getDescent();
		    g.drawString(text, xText, yText);
		}
	    else if (objet instanceof Mine) {
	    	Mine mine = (Mine) objet;
	    	MineProxy mp = new MineProxy(mine, plateauJeu.getCombattantCourant());
	    	if (mp.peutAcceder(plateauJeu.getCombattantCourant()) == false){
	    		return;
	    	}

		    g.setColor(Color.BLACK);
		    g.fillOval(cercleX, cercleY, cercleSize, cercleSize);
		    
		    int redCircleSize = cercleSize / 3; 
		    int redCircleX = cercleX + (cercleSize - redCircleSize) / 2;
		    int redCircleY = cercleY + (cercleSize - redCircleSize) / 2;
		    g.setColor(Color.RED);
		    g.fillOval(redCircleX, redCircleY, redCircleSize, redCircleSize);
		}
	    else if (objet instanceof PastilleEnergetique) {
	    	PastilleEnergetique pe = (PastilleEnergetique) objet;
	    	g.setColor(Color.WHITE);
	    	if (pe.estPrenable())
	        	g.setColor(Color.ORANGE);
	        g.fillOval(cercleX, cercleY, cercleSize, cercleSize);
	    }
	}
	//dessine combattant
	private void drawCombattant(Graphics g, Combattant combattant, int x, int y) {
	    g.setColor(Color.RED);
	    if (combattant.getNom().equals(plateauJeu.getCombattantCourant().getNom())) {
	        g.setColor(Color.GREEN);
	    }

	    int cercleX = x + (caseSize - cercleSize) / 2;
	    int cercleY = y + (caseSize - cercleSize) / 2;
	    g.fillOval(cercleX, cercleY, cercleSize, cercleSize);
	}

	//setup les boutons pour l'utilisateur
    private void initBoutonPanel() {
    	actionCourante = "";
        boutonPanel = new JPanel();
        boutonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        refreshBoutonPanel();
        add(boutonPanel, BorderLayout.SOUTH);
    }

    private void refreshBoutonPanel() {
        boutonPanel.removeAll();

        Map<Combattant, StrategieInterface> strategieMap = carte.getStrategieMap();
        boolean termine = plateauJeu.getTermine();

        if (!termine) {
            if (strategieMap.containsKey(plateauJeu.getCombattantCourant())) {
            	//affichage du bouton suivant pour que le bot puisse jouer
                JButton suivantButton = new JButton("Suivant");
                suivantButton.setPreferredSize(new Dimension(150, 50));
                suivantButton.setBackground(Color.GRAY);
				suivantButton.setForeground(Color.WHITE); 
                suivantButton.addActionListener(e -> {
                    StrategieInterface strategie = strategieMap.get(plateauJeu.getCombattantCourant());
                    List<String> actions = Arrays.asList(strategie.getActions().split(" "));
                    generalControlleur.appliquerAction(actions, plateauJeu.getCombattantCourant());
                    
                    jeuPanel.repaint();
                    refreshBoutonPanel();
                    refreshTable();
                });
                boutonPanel.add(suivantButton);
            } else {
            	//affichage des boutons d'action si le joueurCOurant n'est pas un bot
                addActionButtons();
            }
        } else {
        	//affichage gagnant car partie terminé
            boutonPanel.add(new JLabel("GAGNANT : " + plateauJeu.getCombattantCourant().getNom()));
        }

        boutonPanel.revalidate();
        boutonPanel.repaint();
    }

    private void addActionButtons() {
    	List<String> listeAction = new ArrayList<>();
    	Combattant combattantCourant = plateauJeu.getCombattantCourant();

    	//liste de boutons d'actions principales
    	if (actionCourante.equals("")){
    		String[] actions = {"Déplacement", "Bouclier", "Passer", "Tirer", "Piéger"};
	        for (String action : actions) {
	            JButton button = new JButton(action);
	            button.setPreferredSize(new Dimension(150, 50));
	            button.setBackground(Color.GRAY);
				button.setForeground(Color.WHITE);

	            if (action.equals("Déplacement")){
	            	button.addActionListener(e -> {
	            		actionCourante = "marcher";
	            		refreshBoutonPanel();
		        	});
		        	boutonPanel.add(button);
	            }
	            else if (action.equals("Bouclier")){
	            	button.addActionListener(e -> {
			        	listeAction.add("bouclier");
			        	generalControlleur.appliquerAction(listeAction, combattantCourant);

			            jeuPanel.repaint();
			            refreshBoutonPanel();
			            refreshTable();
		        	});
		        	if (combattantCourant.getDelaiBouclier() == 0)
		        		boutonPanel.add(button);
	            }
	            else if (action.equals("Passer")){
	            	button.addActionListener(e -> {
			        	listeAction.add("passer");
			        	generalControlleur.appliquerAction(listeAction, combattantCourant);

			            jeuPanel.repaint();
			            refreshBoutonPanel();
			            refreshTable();
		        	});
		        	boutonPanel.add(button);
	            }
	            else if (action.equals("Tirer")){
	            	button.addActionListener(e -> {
			        	actionCourante = "tirer";
			        	refreshBoutonPanel();
		        	});
		        	if (combattantCourant.getDelaiTir() == 0)
		        		boutonPanel.add(button);
	            }
	            else if (action.equals("Piéger")){
	            	button.addActionListener(e -> {
			        	actionCourante = "pieger";
			        	refreshBoutonPanel();
		        	});
		        	boutonPanel.add(button);
	            }
	        }
    	}
        else if (actionCourante.equals("marcher")){
        	//liste de boutons pour preciser la direction de l'action marcher
        	listeAction.add(actionCourante);
        	actionCourante = "";

        	String[] actions = {"Gauche", "Haut", "Bas", "Droite", "Retour"};
	        for (String action : actions) {
	            JButton button = new JButton(action);
	            button.setPreferredSize(new Dimension(150, 50));
	            button.setBackground(Color.GRAY);
				button.setForeground(Color.WHITE);

	            if (action.equals("Gauche")){
	            	button.addActionListener(e -> {
	            		listeAction.add("gauche");
	            		generalControlleur.appliquerAction(listeAction, combattantCourant);
	            		
	            		jeuPanel.repaint();
			            refreshBoutonPanel();
			            refreshTable();
		        	});

		        	if (plateauJeu.demandeDeplacement("gauche"))
		        		boutonPanel.add(button);
	            }
	            else if (action.equals("Haut")){
	            	button.addActionListener(e -> {
	            		listeAction.add("haut");
	            		generalControlleur.appliquerAction(listeAction, combattantCourant);
	            		
	            		jeuPanel.repaint();
			            refreshBoutonPanel();
			            refreshTable();
		        	});
		        	if (plateauJeu.demandeDeplacement("haut"))
		        		boutonPanel.add(button);
	            }
	            else if (action.equals("Bas")){
	            	button.addActionListener(e -> {
	            		listeAction.add("bas");
	            		generalControlleur.appliquerAction(listeAction, combattantCourant);
	            		
	            		jeuPanel.repaint();
			            refreshBoutonPanel();
			            refreshTable();
		        	});
		        	if (plateauJeu.demandeDeplacement("bas"))
		        		boutonPanel.add(button);
	            }
	            else if (action.equals("Droite")){
	            	button.addActionListener(e -> {
	            		listeAction.add("droite");
	            		generalControlleur.appliquerAction(listeAction, combattantCourant);
	            		
	            		jeuPanel.repaint();
			            refreshBoutonPanel();
			            refreshTable();
		        	});
		        	if (plateauJeu.demandeDeplacement("droite"))
		        		boutonPanel.add(button);
	            }
	            else if (action.equals("Retour")){
	            	button.addActionListener(e -> {
			            refreshBoutonPanel();
		        	});
		        	boutonPanel.add(button);
	            }
	        }
        }
        else if (actionCourante.equals("tirer")){
        	//liste de boutons pour preciser la direction de l'action tirer
            listeAction.add(actionCourante);
            actionCourante = "";

            String[] actions = {"Verticale", "Horizontale", "Retour"};
            for (String action : actions) {
                JButton button = new JButton(action);
                button.setPreferredSize(new Dimension(150, 50));
                button.setBackground(Color.GRAY);
				button.setForeground(Color.WHITE);

                if (action.equals("Verticale")){
                    button.addActionListener(e -> {
                        listeAction.add("verticale");
                        generalControlleur.appliquerAction(listeAction, combattantCourant);
                        
                        jeuPanel.repaint();
                        refreshBoutonPanel();
                        refreshTable();
                    });
                }
                else if (action.equals("Horizontale")){
                    button.addActionListener(e -> {
                        listeAction.add("horizontale");
                        generalControlleur.appliquerAction(listeAction, combattantCourant);
                        
                        jeuPanel.repaint();
                        refreshBoutonPanel();
                        refreshTable();
                    });
                }
                else if (action.equals("Retour")){
                    button.addActionListener(e -> {
                        refreshBoutonPanel();
                    });
                }
                boutonPanel.add(button);
            }
        }
        else if (actionCourante.equals("pieger")){
        	//liste de boutons pour preciser le type de piege l'action pieger
            String[] actions = {"Mine", "Bombe", "Retour"};
            for (String action : actions) {
                JButton button = new JButton(action);
                button.setPreferredSize(new Dimension(150, 50));
                button.setBackground(Color.GRAY);
				button.setForeground(Color.WHITE);

                if (action.equals("Mine")){
                    button.addActionListener(e -> {
                        actionCourante = "pieger mine";
                        refreshBoutonPanel();
                    });
                }
                else if (action.equals("Bombe")){
                    button.addActionListener(e -> {
                        actionCourante = "pieger bombe";
                        refreshBoutonPanel();
                    });
                }
                else if (action.equals("Retour")){
                    button.addActionListener(e -> {
                    	actionCourante = "";
                        refreshBoutonPanel();
                    });
                }
                boutonPanel.add(button);
            }
        }
        else if (actionCourante.equals("pieger mine") || actionCourante.equals("pieger bombe")){
            //liste de boutons pour preciser les coordonnees de l'action pieger
            listeAction.add("pieger");
            if (actionCourante.equals("pieger mine")){
            	listeAction.add("mine");
            }
            else if (actionCourante.equals("pieger bombe")){
            	listeAction.add("bombe");
            }

            String[] actions = {"-1 1", "0 1", "1 1", "-1 0", "Retour", "1 0", "-1 -1", "0 -1", "1 -1"};
            for (String action : actions) {
                JButton button = new JButton(action);
                button.setPreferredSize(new Dimension(150, 50));
                button.setBackground(Color.GRAY);
				button.setForeground(Color.WHITE);

                if (action.equals("Retour")){
                    button.addActionListener(e -> {
                    	actionCourante = "pieger";
                        refreshBoutonPanel();
                    });
                    boutonPanel.add(button);
                }
                else {
                	String[] actionSplit = action.split(" ");
            		String x = actionSplit[0];
            		String y = actionSplit[1];
                	button.addActionListener(e -> {
                		actionCourante = "";
                		listeAction.add(x);
                		listeAction.add(y);
                		generalControlleur.appliquerAction(listeAction, combattantCourant);
                        
                        jeuPanel.repaint();
                        refreshBoutonPanel();
                        refreshTable();
                    });
                	if (plateauJeu.demandePoseObjet(Integer.parseInt(x), Integer.parseInt(y)))
                    	boutonPanel.add(button);
                }
            }
        }
    }
}
