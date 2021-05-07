package UNIDAD_I;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class MENU extends JDialog implements ActionListener{

    private final JPanel contentPanel = new JPanel();
    int tipoJuego;                                      //1 --> 1P 2 --> 2P 3 --> HELP
    JButton unPlayer, dosPlayer, help, salir ;
    JLabel logame, author;
    URL r1;

    public MENU(JFrame parent, boolean modal, Font f) {

        super(parent, modal);
        setSize(768,640);
        setTitle("");
        setLocationRelativeTo(this);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        setUndecorated(true);

        contentPanel.setLayout(null);
        contentPanel.setBackground(Color.BLACK);

        r1 = getClass().getResource("/UNIDAD_I/res/logo.png");
        logame = new JLabel(new ImageIcon(r1));
        logame.setBounds(110, 100, 550, 256);

        unPlayer = new JButton("1 JUGADOR");
        unPlayer.setFont(f);
        unPlayer.setBackground(Color.DARK_GRAY);
        unPlayer.setForeground(Color.WHITE);
        unPlayer.setBounds(230, 350, 300, 25);
        dosPlayer = new JButton("2 JUGADORES");
        dosPlayer.setFont(f);
        dosPlayer.setBackground(Color.DARK_GRAY);
        dosPlayer.setForeground(Color.WHITE);
        dosPlayer.setBounds(230, 390, 300, 25);
        help = new JButton("AYUDA");
        help.setFont(f);
        help.setForeground(Color.WHITE);
        help.setBackground(Color.DARK_GRAY);
        help.setBounds(230, 430, 300, 25);
        salir = new JButton("SALIR");
        salir.setFont(f);
        salir.setForeground(Color.WHITE);
        salir.setBackground(Color.DARK_GRAY);
        salir.setBounds(230, 470, 300, 25);

        author = new JLabel("Creado por Adrian Ceja Renteria");
        author.setFont(f.deriveFont(12f));
        author.setForeground(Color.WHITE);
        author.setBackground(Color.DARK_GRAY);
        author.setBounds(200, 520, 400, 25);

        unPlayer.addActionListener(this);
        dosPlayer.addActionListener(this);
        help.addActionListener(this);
        salir.addActionListener(this);

        contentPanel.add(logame);
        contentPanel.add(unPlayer);
        contentPanel.add(dosPlayer);
        contentPanel.add(help);
        contentPanel.add(salir);
        contentPanel.add(author);

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

    }

    public int retorno (){
        setVisible(true);
        return tipoJuego;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand() == "1 JUGADOR") tipoJuego = 1;
        else if (e.getActionCommand() == "2 JUGADORES") tipoJuego = 2;
        else if (e.getActionCommand() == "AYUDA") tipoJuego = 3;
        else if (e.getActionCommand() == "SALIR") System.exit(0);
        setVisible(false);
        dispose();
    }

}