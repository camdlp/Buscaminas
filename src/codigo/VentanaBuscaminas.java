/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author carlosabia
 */

/*
Ejercicio de ejemplo de Recurisividad
Algoritmo de Ordenación(Flooding)
 */
public class VentanaBuscaminas extends javax.swing.JFrame {

    int filas = 15;
    int columnas = 20;

    Boton[][] arrayBotones = new Boton[filas][columnas];

    boolean reescalado = false;
    int numeroMinas = 15;

    /*
    Imagenes de numeros y minas
     */
    ImageIcon icono1 = new ImageIcon(getClass().getResource("/img/uno.png"));
    ImageIcon icono2 = new ImageIcon(getClass().getResource("/img/dos.png"));
    ImageIcon icono3 = new ImageIcon(getClass().getResource("/img/tres.png"));
    ImageIcon icono4 = new ImageIcon(getClass().getResource("/img/cuatro.png"));
    ImageIcon iconoMina = new ImageIcon(getClass().getResource("/img/mina.png"));
    ImageIcon iconoMinaRota = new ImageIcon(getClass().getResource("/img/minaRota.png"));
    ImageIcon iconoFlag = new ImageIcon(getClass().getResource("/img/flag.png"));
    ImageIcon iconoNoMina = new ImageIcon(getClass().getResource("/img/noMina.png"));
    ImageIcon iconoVacio = new ImageIcon(getClass().getResource("/img/vacio.png"));

    /**
     * Creates new form VentanaBuscaminas
     */
    public VentanaBuscaminas() {
        initComponents();
        setSize(800, 600);
        setLocationRelativeTo(null);

        GridLayout layout = new GridLayout(filas, columnas, -4, -4);
        getContentPane().setLayout(layout);
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Boton boton = new Boton(i, j, false);
                getContentPane().add(boton);

                arrayBotones[i][j] = boton;
                boton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent evt) {
                        botonPulsado(evt);
                    }
                });
            }
        }

        ponMinas(numeroMinas);
        cuentaMinas();

    }

    private void botonPulsado(MouseEvent e) {
        Boton miBoton = (Boton) e.getComponent();
        miBoton.setFocusable(false);
        if (e.getButton() == MouseEvent.BUTTON3) {
            Image imgFlag = iconoFlag.getImage();
            Image imgFlagReescalada = imgFlag.getScaledInstance(arrayBotones[0][0].getWidth() - 3, arrayBotones[0][0].getWidth() - 5, java.awt.Image.SCALE_SMOOTH);
            iconoFlag = new ImageIcon(imgFlagReescalada);
            
            if (miBoton.getIcon() == null) {
                miBoton.setIcon(iconoFlag);
                miBoton.setFlag(true);
                if(miBoton.getName().equals("m")){
                    numeroMinas--;
                    System.out.println(numeroMinas);
                }
            } else {
                miBoton.setIcon(null);
                miBoton.setFlag(false);
                if(miBoton.getName().equals("m")){
                    numeroMinas++;
                }
            }

        } else if (e.getButton() == MouseEvent.BUTTON1) {
            String oculto = miBoton.getName();
            switch (oculto) {
                case "0":
                    //Llamo al algoritmo de inundación
                    inunda(miBoton.getI(), miBoton.getJ());
                    break;
                case "1":
                case "2":
                case "3":
                case "4":
                    ponImagen(miBoton.getI(), miBoton.getJ());
                    break;
                case "m":
                    ImageIcon iconoMinaRota = new ImageIcon(getClass().getResource("/img/minaRota.png"));
                    Image imgMinaRota = iconoMinaRota.getImage();
                    Image imgMinaRotaReescalada = imgMinaRota.getScaledInstance(arrayBotones[0][0].getWidth() - 3, arrayBotones[0][0].getWidth() - 5, java.awt.Image.SCALE_SMOOTH);
                    iconoMinaRota = new ImageIcon(imgMinaRotaReescalada);
                    miBoton.setIcon(iconoMinaRota);
                    miBoton.setBorderPainted(true);
                    miBoton.setBackground(Color.red);
                    //this.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "Has perdido", "Paquete",
                            JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/img/minaRotaGlande.png")));
                    
                    
                    
                    muestraMinas(filas, columnas);
                    //Reinicia el juego cuando pierdes
                    //this.dispose();
                    //new VentanaBuscaminas().setVisible(true);
                    break;
                default:
                    break;

            }
        }
        /*
        BUCLE DEL JUEGO
        */
        if(numeroMinas == 0){
            JOptionPane.showMessageDialog(null, "¡Has ganado! ¿Quieres volver a jugar?", "Enhorabuena",
                            JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/img/normal.png")));
            this.dispose();
            new VentanaBuscaminas().setVisible(true);
        }

    }

    private void muestraMinas(int lineas, int columnas) {
        for (int i = 0; i < lineas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (arrayBotones[i][j].getName().equals("m")) {
                    if (arrayBotones[i][j].getIcon() == null) {
                        arrayBotones[i][j].setIcon(iconoMina);
                    }
                }else if(arrayBotones[i][j].getFlag() == true){
                        arrayBotones[i][j].setIcon(iconoNoMina);
                        System.out.println(i + "   " + j);
                    }

            }
        }
        this.setEnabled(false);
    }

    private void ponMinas(int numeroMinas) {
        Random r = new Random();
        for (int i = 0; i < numeroMinas; i++) {
            int f = r.nextInt(filas);
            int c = r.nextInt(columnas);
            /*
            TODO 
            Hay que hacer una versión que chequee si en la casilla seleccionada 
            ya hay una mina porque en ese caso tiene que buscar otra.
             */
            arrayBotones[f][c].setMina(1);
            arrayBotones[f][c].setName("m");
            arrayBotones[f][c].setBackground(Color.red);
            
            //arrayBotones[f][c].setIcon(new ImageIcon(getClass().getResource("/img/mina2.png")));
        }

    }

    public void inunda(int i, int j) {
        /*
        Algoritmo de inundación en 8 direcciones.
         */
        if (arrayBotones[i][j].getIcon() == null) {
            arrayBotones[i][j].setEnabled(false);
            for (int k = -1; k < 2; k++) {//filas +
                for (int m = -1; m < 2; m++) {//columnas +                             
                    if ((i + k >= 0) && (j + m >= 0) && (i + k < filas) && (j + m < columnas)) {
                        if (arrayBotones[i + k][j + m].getName().equals("0") && arrayBotones[i + k][j + m].isEnabled()) {
                            //Línea para extender el algoritmo en 4 direcciones
                            if (k == 0 || m == 0) {
                                inunda(i + k, j + m);
                            }
                        } else {
                            if (arrayBotones[i + k][j + m].getIcon() == null) {
                                ponImagen(i + k, j + m);
                            }

                        }

                    }
                }
            }
        }

    }

    public void reescalaImagenes() {
        /*
        Reescalado de las imágenes
         */
        //Icono 1
        Image img1 = icono1.getImage();
        Image imgReescalada1 = img1.getScaledInstance(arrayBotones[0][0].getWidth() - 3, arrayBotones[0][0].getWidth() - 5, java.awt.Image.SCALE_SMOOTH);
        icono1 = new ImageIcon(imgReescalada1);

        //Icono 2
        Image img2 = icono2.getImage();
        Image imgReescalada2 = img2.getScaledInstance(arrayBotones[0][0].getWidth() - 3, arrayBotones[0][0].getWidth() - 5, java.awt.Image.SCALE_SMOOTH);
        icono2 = new ImageIcon(imgReescalada2);

        //Icono 3
        Image img3 = icono3.getImage();
        Image imgReescalada3 = img3.getScaledInstance(arrayBotones[0][0].getWidth() - 3, arrayBotones[0][0].getWidth() - 5, java.awt.Image.SCALE_SMOOTH);
        icono3 = new ImageIcon(imgReescalada3);

        //Icono 4
        Image img4 = icono4.getImage();
        Image imgReescalada4 = img4.getScaledInstance(arrayBotones[0][0].getWidth() - 3, arrayBotones[0][0].getWidth() - 5, java.awt.Image.SCALE_SMOOTH);
        icono4 = new ImageIcon(imgReescalada4);

        //Icono Mina
        Image imgMina = iconoMina.getImage();
        Image imgMinaReescalada = imgMina.getScaledInstance(arrayBotones[0][0].getWidth() - 3, arrayBotones[0][0].getWidth() - 5, java.awt.Image.SCALE_SMOOTH);
        iconoMina = new ImageIcon(imgMinaReescalada);

        //Icono no hay Mina
        Image imgNoMina = iconoNoMina.getImage();
        Image imgNoMinaReescalada = imgNoMina.getScaledInstance(arrayBotones[0][0].getWidth() - 3, arrayBotones[0][0].getWidth() - 5, java.awt.Image.SCALE_SMOOTH);
        iconoNoMina = new ImageIcon(imgNoMinaReescalada);

        //Icono vacio
        Image imgVacio = iconoVacio.getImage();
        Image imgVacioReescalada = imgVacio.getScaledInstance(arrayBotones[0][0].getWidth() - 3, arrayBotones[0][0].getWidth() - 5, java.awt.Image.SCALE_SMOOTH);
        iconoVacio = new ImageIcon(imgVacioReescalada);

    }

    public void ponImagen(int i, int j) {
        if (!reescalado) {
            reescalaImagenes();
            reescalado = true;
        }

        arrayBotones[i][j].setBorderPainted(false);
        switch (arrayBotones[i][j].getName()) {
            case "1":
                arrayBotones[i][j].setIcon(icono1);
                break;
            case "2":
                arrayBotones[i][j].setIcon(icono2);
                break;
            case "3":
                arrayBotones[i][j].setIcon(icono3);
                break;
            case "4":
                arrayBotones[i][j].setIcon(icono4);
                break;
            case "m":
                arrayBotones[i][j].setIcon(iconoMina);
                break;
            default:
                arrayBotones[i][j].setIcon(iconoVacio);
                break;
        }
//        arrayBotones[i][j].setBackground(Color.decode("#5b5b5b"));
    }

    /*
    Este método hace que para cada boton calcula el número de minas que tiene 
    alrededor.
     */
    private void cuentaMinas() {

        int minas = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                for (int k = -1; k < 2; k++) {//filas +
                    for (int m = -1; m < 2; m++) {//columnas +
                        //Aquí si interesa que compruebe las 8 direcciones
                        if ((i + k >= 0) && (j + m >= 0) && (i + k < filas) && (j + m < columnas)) {
                            minas = minas + arrayBotones[i + k][j + m].getMina();
                        }
                    }
                }
                arrayBotones[i][j].setNumeroMinasAlrededor(minas);

                /*
                Pongo names en vez de texts para que no sean visibles los números
                 */
                if (arrayBotones[i][j].getMina() == 0) {
                    arrayBotones[i][j].setName(String.valueOf(minas));
                    //arrayBotones[i][j].setForeground(Color.white);
                    //arrayBotones[i][j].setBackground(Color.white);                    

                }
                minas = 0;
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaBuscaminas().setVisible(true);
                  
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
