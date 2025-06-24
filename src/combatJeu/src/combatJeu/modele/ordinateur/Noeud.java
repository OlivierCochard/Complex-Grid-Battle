package combatJeu.modele.ordinateur;

import java.util.Objects;

//permet au pathfinding de savoir quels noeuds ont ete exploré et quel noeuds et le meilleur
public class Noeud {
    int x, y;
    int coutG, coutH;
    Noeud parent;

    public Noeud(int x, int y) {
        this.x = x;
        this.y = y;
        this.coutG = 0;
        this.coutH = 0;
        this.parent = null;
    }

    public int getX(){ return x; }
    public int getY(){ return y; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Noeud noeud = (Noeud) obj;
        return x == noeud.x && y == noeud.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
