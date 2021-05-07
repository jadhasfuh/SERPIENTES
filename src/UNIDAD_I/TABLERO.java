package UNIDAD_I;

public class TABLERO {

    int posicion;
    int r,c;
    int avance;
    boolean flip;

    public TABLERO(int p, int r, int c, int avance, boolean f){
        posicion = p;
        this.r = r;
        this.c = c;
        this.avance = avance;
        flip = f;
    }

    public int getRenglon(){

        return r;

    }

    public int getColumna(){

        return c;

    }

    public int getAvance(){

        return avance;

    }

    public boolean getFlip(){

        return flip;

    }

}