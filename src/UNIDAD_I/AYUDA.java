package UNIDAD_I;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

class AYUDA extends JDialog {

    private final JPanel contentPanel = new JPanel();
    JTextPane ayuda;
    JButton aceptar;
    JLabel fondo;
    URL r1;
    String mensaje;

    public AYUDA(JFrame parent, boolean modal, Font f) {

        super(parent, modal);
        setSize(768,640);
        setLocationRelativeTo(this);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        setUndecorated(true);

        contentPanel.setLayout(null);
        contentPanel.setBackground(Color.BLACK);

        mensaje = "Acompaña a Mauro y Luis en esta aventura para salvar a la princesa Paola," +
                " da clic en el dado para detenerlo y avanzar, si encuentras a tu paso una escalera podrás " +
                "usarla, pero en caso de encontrar un hueco podrás caer en el, ¡cuidado!" +
                "Llega a la casilla 30 para salvar a la princesa y ganar. Cuidado con Prince Kong !";

        SimpleAttributeSet sa = new SimpleAttributeSet();
        StyleConstants.setLineSpacing(sa, .2f);
        StyleConstants.setAlignment(sa, StyleConstants.ALIGN_JUSTIFIED);

        ayuda = new JTextPane();
        ayuda.getStyledDocument().setParagraphAttributes(0,mensaje.length(),sa,false);
        ayuda.setText(mensaje);
        ayuda.setOpaque(false);
        ayuda.setEnabled(false);
        ayuda.setFont(f);
        ayuda.setForeground(Color.WHITE);
        ayuda.setBounds(80, 50, 610, 250);
        contentPanel.add(ayuda);

        r1 = getClass().getResource("/UNIDAD_I/res/help.png");
        fondo = new JLabel(new ImageIcon(r1));
        fondo.setBounds(50, 260, 668, 400);
        contentPanel.add(fondo);

        aceptar = new JButton("OK");
        aceptar.setBackground(Color.DARK_GRAY);
        aceptar.setForeground(Color.WHITE);
        aceptar.setFont(f);
        aceptar.setBounds(480, 510, 100, 40);
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