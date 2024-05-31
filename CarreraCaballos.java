import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CarreraCaballos {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaCarrera();
            }
        });
    }
}

class Hilo implements Runnable { //CREACION Y GESTION DE HIILOS
    Thread t;
    String nombre;
    JLabel caballo;
    JLabel labeFinal;
    private boolean paused= false;
    int retardo;
    public static int lugar;

    public Hilo(String nombre, JLabel caballo, JLabel labeFinal) {
        this.nombre = nombre;
        this.labeFinal = labeFinal;
        this.caballo = caballo;
        t = new Thread(this, nombre);
        t.start();
    }

    @Override
    public void run() {
        int retardo; //TIEMPO RETRASO DEL MOVIMIENTO DEL CABALLO
        try {
            lugar = 1; //SEGUIR EL PROGRESO DEL CABALLO EN LA CARRERA
            retardo = (int) (Math.random() * 15) + 1; //VELOCIDAD DE LOS CABALLOS DE FORMA ALEATOREA
            labeFinal.setVisible(false);
            caballo.setVisible(true);

            for (int i = 50; i <= 500; i++) { // CONTROLA EL MOVIMIETO HORIZONTAL
                synchronized (this) { //SINCRONIZACION ENTRE THREADS
                    while (paused) {
                        wait();
                    }
                }
                caballo.setLocation(i, caballo.getY());
                Thread.sleep(retardo);
            }

            caballo.setVisible(false);
            labeFinal.setVisible(true);
            labeFinal.setForeground(Color.white);
            labeFinal.setText(nombre + " POSICION: " + lugar);
            lugar++;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public synchronized void stopThread() {
        paused = true;
    }

    public synchronized void resumeThread() {
        paused = false;
        notify();
    }
}

class VentanaCarrera extends JFrame {
    private Hilo tcaballo, thorse, tcheval;

    public VentanaCarrera() {
        super(".: HORSE RACING :.");
        JLabel caballo, horse, cheval, caballoPos, horsePos, chevalPos;
        JButton inicioCarrera, btnSleep, btnResume, btnPaused;

        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        String backgroundPath = "/home/marco/Documentos/carreraCaballos/pista.jpg";
        JLabel backgroundLabel = new JLabel(new ImageIcon(new ImageIcon(backgroundPath).getImage().getScaledInstance(800, 400, Image.SCALE_DEFAULT)));
        backgroundLabel.setBounds(0, 0, 600, 300);

        Image imagenCaballo = new ImageIcon("/home/marco/Documentos/carreraCaballos/uno.gif").getImage();
        ImageIcon iconCaballo = new ImageIcon(imagenCaballo.getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        caballo = new JLabel();
        caballo.setIcon(iconCaballo);
        caballo.setBounds(50, 50, 50, 50);

        Image imagenHorse = new ImageIcon("/home/marco/Documentos/carreraCaballos/dos.gif").getImage();
        ImageIcon iconHorse = new ImageIcon(imagenHorse.getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        horse = new JLabel();
        horse.setIcon(iconHorse);
        horse.setBounds(50, 100, 50, 50);

        Image imagenCheval = new ImageIcon("/home/marco/Documentos/carreraCaballos/tres.gif").getImage();
        ImageIcon iconCheval = new ImageIcon(imagenCheval.getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        cheval = new JLabel();
        cheval.setIcon(iconCheval);
        cheval.setBounds(50, 150, 50, 50);

        caballoPos = new JLabel();
        caballoPos.setBounds(50, 50, 350, 50);

        horsePos = new JLabel();
        horsePos.setBounds(50, 100, 350, 50);

        chevalPos = new JLabel();
        chevalPos.setBounds(50, 150, 350, 50);

        inicioCarrera = new JButton("INICIAR");
        inicioCarrera.setBounds(10, 220, 100, 30);

        btnSleep = new JButton("SLEEP");
        btnSleep.setBounds(100, 220, 100, 30);

        btnPaused = new JButton("SUSPEND");
        btnPaused.setBounds(200, 220, 100, 30);

        btnResume = new JButton("RESUME");
        btnResume.setBounds(300, 220, 120, 30);

        inicioCarrera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tcaballo = new Hilo("Caballo #Uno", caballo, caballoPos);
                thorse = new Hilo("Caballo #Dos", horse, horsePos);
                tcheval = new Hilo("Caballo #Tres", cheval, chevalPos);
            }
        });

        btnSleep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tcaballo != null && thorse != null && tcheval != null) {
                    try {
                        Thread.sleep(3000); 
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        btnPaused.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tcaballo != null) tcaballo.stopThread();
                if (thorse != null) thorse.stopThread();
                if (tcheval != null) tcheval.stopThread();
            }
        });

        btnResume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tcaballo != null) tcaballo.resumeThread();
                if (thorse != null) thorse.resumeThread();
                if (tcheval != null) tcheval.resumeThread();
            }
        });

        panel.add(caballo);
        panel.add(caballoPos);
        panel.add(horse);
        panel.add(horsePos);
        panel.add(cheval);
        panel.add(chevalPos);
        panel.add(inicioCarrera);
        panel.add(btnSleep);
        panel.add(btnPaused);
        panel.add(btnResume);
        panel.add(backgroundLabel); panel.setComponentZOrder(backgroundLabel, panel.getComponentCount() - 1);

        add(panel);
        setVisible(true);
    
    }
}
