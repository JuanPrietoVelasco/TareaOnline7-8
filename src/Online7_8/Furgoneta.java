/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Online7_8;

import Online7_8.Enumerados.Tamanio;

/**
 *
 * @author juans
 */
public class Furgoneta extends Mercancias {
    private boolean refrigerado;
    private Tamanio tamanio;
    
    public Furgoneta(String matricula, String marca, String modelo, int cilindrada, int pma, int volumen, boolean refrigerado, Tamanio tamanio) {
        super(matricula, marca, modelo, cilindrada, pma, volumen);
        this.refrigerado = refrigerado;
        this.tamanio = tamanio;
    }

    public boolean getRefrigerado() {
        return refrigerado;
    }

    public Tamanio getTamanio() {
        return tamanio;
    }
    
    //Corregido

    @Override
    public String escribirFichero() {
        return super.escribirFichero() + "#" +this.refrigerado + "#" + this.tamanio + "\n";
    }
    

    @Override
    public String toString() {
        return super.toString() + "\t\tRefrigerado: " + refrigerado + "\t\tTamaño: " + tamanio;
    }
    
}
