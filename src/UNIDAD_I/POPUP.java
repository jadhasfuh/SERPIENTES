package UNIDAD_I;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class POPUP extends JDialog {

    private final JPanel contentPanel = new JPanel();
    JLabel mensaje, player;
    JButton aceptar;
    URL r1;

    public POPUP(JFrame parent, boolean modal, String notificacion, String jugador, Font f) {

        super(parent, modal);
        setSize(600, 300);
        setLocationRelativeTo(this);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        setUndecorated(true);

        contentPanel.setLayout(null);
        contentPanel.setBackground(Color.BLACK);

        mensaje = new JLabel(notificacion);
        mensaje.setFont(f);
        mensaje.setForeground(Color.WHITE);
        mensaje.setBounds(70, 50, 600, 25);
        contentPanel.add(mensaje);

        if (jugador.equals("Mario"))
            r1 = getClass().getResource("/UNIDAD_I/res/mario.png");
        else
            r1 = getClass().getResource("/UNIDAD_I/res/luigi.png");

        player = new JLabel();
        player.setIcon(new ImageIcon(r1));
        player.setBounds(265, 85, 64, 64);
        contentPanel.add(player);

        aceptar = new JButton("OK");
        aceptar.setBounds(245, 180, 100, 40);
        aceptar.setBackground(Color.DARK_GRAY);
        aceptar.setForeground(Color.WHITE);
        aceptar.setFont(f);
        contentPanel.add(aceptar);

        aceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setVisible(true);

    }

}