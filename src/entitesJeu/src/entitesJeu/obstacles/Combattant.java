package entitesJeu.obstacles;

import entitesJeu.obstacles.Obstacle;

import java.util.ArrayList;
import java.util.List;

public class Combattant extends Obstacle {
    private String nom;
    private String type;
    private boolean bouclier;
    private int energie;
    private int portee;
    private int munition;
    private int degatTir;
    private int delaiTir;
    private int delaiBouclier;

    public Combattant(String type, int energie, int portee, int munition, int degatTir){
        nom = "inconnu";
        this.type = type;
        this.energie = energie;
        this.portee = portee;
        this.munition = munition;
        this.degatTir = degatTir;
    }

    public String getNom(){ return nom; }
    public String getType(){ return type; }
    public boolean getBouclier(){ return bouclier; }
    public int getEnergie(){ return energie; }
    public int getPortee(){ return portee; }
    public int getMunition(){ return munition; }
    public int getDegatTir(){ return degatTir; }
    public int getDelaiTir(){ return delaiTir; }
    public int getDelaiBouclier(){ return delaiBouclier; }

    public void setNom(String nom){ this.nom = nom; }
    public void setBouclier(boolean bouclier){ this.bouclier = bouclier; }
    public void setDelaiTir(int delai){ delaiTir = delai; }
    public void setDelaiBouclier(int delai){ delaiBouclier = delai; }

    public boolean isMort(){ return energie <= 0; }

    public void perteMunition(){
        if (munition <= 0) return;

        int tmp = munition;
        munition --;
        System.out.println("[" + nom + "] munition (" + tmp + " => " + munition + ")");
    }
    public void perteEnergie(int quantite){
        if (quantite < 0) return;
        if (bouclier == true) return;

        int tmp = energie;
        energie -= quantite;
        System.out.println("[" + nom + "] energie (" + tmp + " => " + energie + ")");
        if (isMort()){
            destruction();
        }
    }
    public void gainEnergie(int quantite){
        if (quantite < 0) return;

        int tmp = energie;
        energie += quantite;
        System.out.println("[" + nom + "] energie (" + tmp + " => " + energie + ")");
    }

    //permet de demander aux Observeurs le placement d'un piege par le combattant
    public void pieger(String piege, int x, int y, int coutEnergetique){
        List<String> tmp = new ArrayList<String>();
        tmp.add("pieger");  
        tmp.add(piege);  
        tmp.add("" + x);
        tmp.add("" + y);
        tmp.add("" + coutEnergetique);
        notifierObserveurs(tmp);
    }
    public void bouclier(int coutEnergetique){
        List<String> tmp = new ArrayList<String>();
        tmp.add("bouclier"); 
        tmp.add("" + coutEnergetique);       
        notifierObserveurs(tmp);
    }
    public void deplacement(String direction, int coutEnergetique){
        List<String> tmp = new ArrayList<String>();
        tmp.add("deplacement");
        tmp.add(direction);
        tmp.add("" + coutEnergetique);
        notifierObserveurs(tmp);
    }
    public void projectile(String direction, int coutEnergetique){
        List<String> tmp = new ArrayList<String>();
        tmp.add("projectile");
        tmp.add(direction);
        tmp.add("" + coutEnergetique);
        notifierObserveurs(tmp);
    }

    @Override
    public void destruction(){
        List<String> tmp = new ArrayList<String>();
        tmp.add("destruction");
        tmp.add("combattant");
        notifierObserveurs(tmp);
        System.out.println("[" + nom + "] mort");
    }
    @Override
    public void touche(int degats){
        perteEnergie(degats);
    }
    public void initiative(){
        setBouclier(false);
        System.out.println("[" + nom + "] à toi de jouer");
        if (delaiTir > 0){
            delaiTir--;
        }
        if (delaiBouclier > 0){
            delaiBouclier--;
        }
    }
    
    @Override
    public String toString(){
        return "Combattant: " + nom + " | " + "type: " + type + " | " + "bouclier " + bouclier + " | " + "energie " + energie + " | " + "portee " + portee + " | " + "munition " + munition;
    }
}