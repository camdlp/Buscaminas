/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import javax.swing.JButton;

/**
 *
 * @author carlosabia
 */
public class Boton extends JButton {

    private int mina = 0;
    private int i = 0;
    private int j = 0;
    private int numeroMinasAlrededor = 0;
    private boolean flag = false;

    public Boton(int _i, int _j, boolean _flag) {
        i = _i;
        j = _j;
        flag = _flag;
        this.setBorder(null);

    }

    public int getMina() {
        return mina;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setMina(int mina) {
        this.mina = mina;
    }

    public int getNumeroMinasAlrededor() {
        return numeroMinasAlrededor;
    }

    public void setNumeroMinasAlrededor(int numeroMinasAlrededor) {
        this.numeroMinasAlrededor = numeroMinasAlrededor;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

}
