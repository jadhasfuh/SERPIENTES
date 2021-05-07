package UNIDAD_I;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class SERPIENTES extends JPanel implements ActionListener, Runnable {

    private static final long serialVersionUID = 1L;
    JFrame v;
    Image tablero;
    ImageIcon playerMario;
    ImageIcon playerLuigi;
    ImageIcon pMarioFlip;
    ImageIcon pLuigiFlip;
    ImageIcon princess;
    ImageIcon dice;
    ImageIcon dk;
    JLabel veti[][];
    JPanel panelDados;
    JButton bDados;
    JButton bMenu;
    POPUP dialog;
    MENU menu;
    AYUDA ayuda;
    TABLERO t[];
    Thread hilo;
    Font f;

    int posfrM, posfcM, posfrL, posfcL, ngen, posMario, posLuigi, tipoJuego, turno = 0, paro = 0, avance = 0,
            restar = 0, ren = 0;

    public void restart() {
        posfrM = 4;
        posfcM = 0;
        posfrL = 4;
        posfcL = 0;
        posMario = 1;
        posLuigi = 1;
        turno = 0;
        veti[posfrL][posfcL].setIcon(null);
        if (tipoJuego == 2)
            veti[posfrL][posfcL].setIcon(playerLuigi);
        veti[posfrM][posfcM].setIcon(playerMario);
    }

    public void showMenu() {

        menu = new MENU(v, true, f);
        tipoJuego = menu.retorno();
        if (tipoJuego == 3) {
            ayuda = new AYUDA(v, true, f);
            showMenu();
        }

    }

    public SERPIENTES() {

        URL fontUrl;
        try {
            fontUrl = getClass().getResource("/UNIDAD_I/res/" + "PressStart2P.ttf");
            f = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream());
            f = f.deriveFont(Font.PLAIN, 18);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(f);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        playLoop("/UNIDAD_I/res/loop.wav");
        showMenu();

        v = new JFrame("Serpientes y escaleras");
        v.setSize(768, 640);
        v.setLocationRelativeTo(this);
        v.setResizable(false);
        v.setUndecorated(true);
        setBackground(Color.BLACK);
        setSize(768, 640);
        setLayout(new GridLayout(5, 6));

        makeTablero();
        veti = new JLabel[5][6];

        int n1 = 30;
        int n2 = 5;
        boolean b = true;

        for (int r = 0; r < veti.length; r++) {

            for (int c = 0; c < veti[0].length; c++) {

                veti[r][c] = new JLabel();
                veti[r][c].setFont(f);
                // AQUI NADAMAS PONEMOS NUMERACION A LAS CASILLAS
                if (b) {
                    veti[r][c].setText("    " + (n1 - n2));
                    if (n2 > 0)
                        n2--;
                } else {
                    veti[r][c].setText("    " + (n1 - n2));
                    if (n2 < 5)
                        n2++;
                }
                veti[r][c].setForeground(Color.WHITE);
                add(veti[r][c]);

            }

            b = !b;

            n1 -= 6;

        }

        URL r1 = getClass().getResource("/UNIDAD_I/res/level.png");
        tablero = new ImageIcon(r1).getImage();
        r1 = getClass().getResource("/UNIDAD_I/res/player.gif");
        playerMario = new ImageIcon(r1);
        r1 = getClass().getResource("/UNIDAD_I/res/playerflip.gif");
        pMarioFlip = new ImageIcon(r1);
        r1 = getClass().getResource("/UNIDAD_I/res/player2.gif");
        playerLuigi = new ImageIcon(r1);
        r1 = getClass().getResource("/UNIDAD_I/res/player2flip.gif");
        pLuigiFlip = new ImageIcon(r1);
        r1 = getClass().getResource("/UNIDAD_I/res/pauline.gif");
        princess = new ImageIcon(r1);
        r1 = getClass().getResource("/UNIDAD_I/res/dice.gif");
        dice = new ImageIcon(r1);
        r1 = getClass().getResource("/UNIDAD_I/res/dk.gif");
        dk = new ImageIcon(r1);

        restart();

        veti[0][5].setIcon(princess);
        veti[0][4].setIcon(dk);

        panelDados = new JPanel(new BorderLayout());
        bDados = new JButton(dice);
        bDados.setOpaque(false);
        bDados.setContentAreaFilled(false);
        bDados.setBorderPainted(false);
        bMenu = new JButton("Menu");
        bMenu.setFont(f);
        bMenu.setForeground(Color.WHITE);
        bMenu.setBackground(Color.DARK_GRAY);
        panelDados.add(bDados);
        panelDados.add(bMenu, BorderLayout.WEST);
        panelDados.setOpaque(false);
        panelDados.setBackground(Color.BLACK);

        // EVENTO DEL BOTON
        bDados.addActionListener(this);
        bMenu.addActionListener(this);

        v.add(panelDados, BorderLayout.SOUTH);
        v.add(this, BorderLayout.CENTER);
        v.setVisible(true);
        v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand() == "Menu") {
            borrado();
        } else {
            Random r = new Random();
            ngen = 1 + r.nextInt(6);
            URL r1 = getClass().getResource("/UNIDAD_I/res/dice" + ngen + ".png");
            bDados.setIcon(new ImageIcon(r1));
            if (turno == 0)
                dialog = new POPUP(v, true, "Mario anvanza " + ngen + " casillas!", "Mario", f);
            else
                dialog = new POPUP(v, true, "Luigi anvanza " + ngen + " casillas!", "Luigi", f);
            bDados.setIcon(dice);
            hilo = new Thread(this, "Hilo 1");
            hilo.start();
        }

    }

    public void hilin(boolean b, int avance) {

        if (b) {

            if (turno == 0) {

                if (t[avance].getFlip())
                    veti[posfrM][posfcM].setIcon(pMarioFlip);
                else
                    veti[posfrM][posfcM].setIcon(playerMario);
                if (tipoJuego == 2)
                    veti[posfrL][posfcL].setIcon(playerLuigi);

            } else {

                if (t[avance].getFlip())
                    veti[posfrL][posfcL].setIcon(pLuigiFlip);
                else
                    veti[posfrL][posfcL].setIcon(playerLuigi);
                if (tipoJuego == 2)
                    veti[posfrM][posfcM].setIcon(playerMario);

            }

        } else {

            if (turno == 0) {

                if (t[posMario].getFlip())
                    veti[posfrM][posfcM].setIcon(pMarioFlip);
                else
                    veti[posfrM][posfcM].setIcon(playerMario);
                if (tipoJuego == 2)
                    veti[posfrL][posfcL].setIcon(playerLuigi);

            } else {

                if (t[posLuigi].getFlip())
                    veti[posfrL][posfcL].setIcon(pLuigiFlip);
                else
                    veti[posfrL][posfcL].setIcon(playerLuigi);
                if (tipoJuego == 2)
                    veti[posfrM][posfcM].setIcon(playerMario);

            }

        }
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        bDados.setEnabled(false);
        bMenu.setEnabled(false);

        if (turno == 0)
            paro = posMario + ngen;
        else
            paro = posLuigi + ngen;

        int posac;

        if (paro > 30) {

            restar = (paro) - 30;
            if (turno == 0)
                posac = posMario;
            else
                posac = posLuigi;

            while (posac < 30) {

                posac++;
                if (turno == 0)
                    posMario++;
                else
                    posLuigi++;

                if (turno == 0) {
                    veti[posfrM][posfcM].setIcon(null);
                    posfrM = t[posMario].getRenglon();
                    posfcM = t[posMario].getColumna();
                } else {
                    veti[posfrL][posfcL].setIcon(null);
                    posfrL = t[posLuigi].getRenglon();
                    posfcL = t[posLuigi].getColumna();
                }
                hilin(false, avance);

            }

            for (int i = 0; i < restar; i++) {

                posac--;

                if (turno == 0) {
                    posMario--;
                    veti[posfrM][posfcM].setIcon(null);
                    posfrM = t[posMario].getRenglon();
                    posfcM = t[posMario].getColumna();
                } else {
                    posLuigi--;
                    veti[posfrL][posfcL].setIcon(null);
                    posfrL = t[posLuigi].getRenglon();
                    posfcL = t[posLuigi].getColumna();
                }

                hilin(false, avance);

            }

        } else {

            if (turno == 0)
                posac = posMario;
            else
                posac = posLuigi;

            while (posac < paro) {

                posac++;

                if (turno == 0) {
                    posMario++;
                    veti[posfrM][posfcM].setIcon(null);
                    posfrM = t[posMario].getRenglon();
                    posfcM = t[posMario].getColumna();
                } else {
                    posLuigi++;
                    veti[posfrL][posfcL].setIcon(null);
                    posfrL = t[posLuigi].getRenglon();
                    posfcL = t[posLuigi].getColumna();
                }

                hilin(false, avance);

            }

        }

        avance = t[posac].getAvance();
        ren = t[posac].getRenglon();

        if (avance != 0) {

            if (avance == 1) {

                if (turno == 0) {
                    dialog = new POPUP(v, true, "Te cargo el payaso", "Mario", f);
                    veti[posfrM][posfcM].setIcon(null);
                    posfrM = 4;
                    posfcM = 0;
                    posMario = 1;
                    veti[posfrM][posfcM].setIcon(playerMario);
                } else {
                    dialog = new POPUP(v, true, "Te cargo el payaso", "Luigi", f);
                    veti[posfrL][posfcL].setIcon(null);
                    posfrL = 4;
                    posfcL = 0;
                    posLuigi = 1;
                    veti[posfrL][posfcL].setIcon(playerLuigi);
                }
                playSFX("/UNIDAD_I/res/death.wav");

            } else {

                if (avance < posac) {

                    if (turno == 0)
                        dialog = new POPUP(v, true, "Retrocede a la posición " + avance, "Mario", f);
                    else
                        dialog = new POPUP(v, true, "Retrocede a la posición " + avance, "Luigi", f);
                    while (t[avance].getRenglon() > ren) {

                        if (turno == 0) {
                            veti[posfrM][posfcM].setIcon(null);
                            ren++;
                            posfrM = ren;
                        } else {
                            veti[posfrL][posfcL].setIcon(null);
                            ren++;
                            posfrL = ren;
                        }

                        hilin(true, avance);

                    }

                } else {

                    if (turno == 0)
                        dialog = new POPUP(v, true, "Avanza a la posición " + avance, "Mario", f);
                    else
                        dialog = new POPUP(v, true, "Avanza a la posición " + avance, "Luigi", f);

                    while (t[avance].getRenglon() < ren) {

                        if (turno == 0) {
                            veti[posfrM][posfcM].setIcon(null);
                            ren--;
                            posfrM = ren;
                        } else {
                            veti[posfrL][posfcL].setIcon(null);
                            ren--;
                            posfrL = ren;
                        }

                        hilin(true, avance);

                    }

                }

            }

            if (turno == 0)
                posMario = avance;
            else
                posLuigi = avance;

        }

        if (turno == 0) {
            posfrM = t[posMario].getRenglon();
            posfcM = t[posMario].getColumna();
            if (posMario == 30) {
                dialog = new POPUP(v, true, "Mario ha ganado", "Mario", f);
                playSFX("/UNIDAD_I/res/win.wav");
                borrado();
            }
        } else {
            posfrL = t[posLuigi].getRenglon();
            posfcL = t[posLuigi].getColumna();
            if (posLuigi == 30) {
                dialog = new POPUP(v, true, "Luigi ha ganado", "Luigi", f);
                playSFX("/UNIDAD_I/res/win.wav");
                borrado();
            }
        }

        if (tipoJuego == 2) {
            if (turno == 0)
                turno = 1;
            else
                turno = 0;
        }

        veti[0][5].setIcon(princess);
        veti[0][4].setIcon(dk);
        bDados.setEnabled(true);
        bMenu.setEnabled(true);

    }

    public void borrado() {
        veti[posfrM][posfcM].setIcon(null);
        veti[posfrL][posfcL].setIcon(null);
        restart();
        showMenu();
    }

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(tablero, 0, 10, getWidth(), getHeight(), this);

    }

    public static void main(String[] args) {
        new SERPIENTES();
    }

    public void makeTablero() {

        t = new TABLERO[31];

        // PRIMER RENGLON
        t[1] = new TABLERO(1, 4, 0, 0, false);
        t[2] = new TABLERO(2, 4, 1, 0, false);
        t[3] = new TABLERO(3, 4, 2, 22, false);
        t[4] = new TABLERO(4, 4, 3, 0, false);
        t[5] = new TABLERO(5, 4, 4, 8, false);
        t[6] = new TABLERO(6, 4, 5, 0, false);

        // SEGUNDO RENGLON
        t[7] = new TABLERO(7, 3, 5, 0, true);
        t[8] = new TABLERO(8, 3, 4, 0, true);
        t[9] = new TABLERO(9, 3, 3, 4, true);
        t[10] = new TABLERO(10, 3, 2, 0, true);
        t[11] = new TABLERO(11, 3, 1, 26, true);
        t[12] = new TABLERO(12, 3, 0, 0, true);

        // TERCER RENGLON
        t[13] = new TABLERO(13, 2, 0, 0, false);
        t[14] = new TABLERO(14, 2, 1, 0, false);
        t[15] = new TABLERO(15, 2, 2, 10, false);
        t[16] = new TABLERO(16, 2, 3, 0, false);
        t[17] = new TABLERO(17, 2, 4, 20, false);
        t[18] = new TABLERO(18, 2, 5, 7, false);

        // CUARTO RENGLON
        t[19] = new TABLERO(19, 1, 5, 0, true);
        t[20] = new TABLERO(20, 1, 4, 0, true);
        t[21] = new TABLERO(21, 1, 3, 16, true);
        t[22] = new TABLERO(22, 1, 2, 0, true);
        t[23] = new TABLERO(23, 1, 1, 0, true);
        t[24] = new TABLERO(24, 1, 0, 13, true);

        // QUINTO RENGLON
        t[25] = new TABLERO(25, 0, 0, 0, false);
        t[26] = new TABLERO(26, 0, 1, 0, false);
        t[27] = new TABLERO(27, 0, 2, 0, false);
        t[28] = new TABLERO(28, 0, 3, 16, false);
        t[29] = new TABLERO(29, 0, 4, 1, false);
        t[30] = new TABLERO(30, 0, 5, 0, false);

    }


    public void playLoop(String name) {

        try {
            Clip clip = AudioSystem.getClip();
            InputStream is = SERPIENTES.class.getResourceAsStream(name);
            InputStream bufferedIn = new BufferedInputStream(is);
            AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
            clip.open(ais);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch (UnsupportedAudioFileException e) {
            throw new IllegalArgumentException("unsupported audio format: '" + name + "'", e);
        }
        catch (LineUnavailableException e) {
            throw new IllegalArgumentException("could not play '" + name + "'", e);
        }
        catch (IOException e) {
            throw new IllegalArgumentException("could not play '" + name + "'", e);
        }

    }

    public void playSFX(String name) {

        try {
            Clip clip = AudioSystem.getClip();
            InputStream is = SERPIENTES.class.getResourceAsStream(name);
            InputStream bufferedIn = new BufferedInputStream(is);
            AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
            clip.open(ais);
            clip.start();
        }
        catch (UnsupportedAudioFileException e) {
            throw new IllegalArgumentException("unsupported audio format: '" + name + "'", e);
        }
        catch (LineUnavailableException e) {
            throw new IllegalArgumentException("could not play '" + name + "'", e);
        }
        catch (IOException e) {
            throw new IllegalArgumentException("could not play '" + name + "'", e);
        }

    }



}