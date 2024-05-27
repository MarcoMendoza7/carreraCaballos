import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class horseRacing{
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaCarrera();
            }
        });
    }
}

class Hilo implements Runnable{
    Thread t;
    String nombre;
    JLabel caballo;
    JLabel labeFinal;

    public static int lugar;

    public Hilo(String nombre, JLabel caballo, JLabel labeFinal){
        this.nombre = nombre;   
        this.labeFinal = labeFinal;
        this.caballo = caballo;
        t = new Thread(this, nombre);
        t.start();
    }

    @Override
    public void run(){
        int retardo;
        try{
            lugar = 1;
            retardo = (int) (Math.random() * 15) +1;
            labeFinal.setVisible(false);
            caballo.setVisible(true);

            for(int i=50; i<500; i++){
                caballo.setLocation(i, caballo.getY());
                Thread.sleep(retardo);
            }
            caballo.setVisible(false);
            labeFinal.setVisible(true);
            labeFinal.setText(nombre + " POSICION: " + lugar);
            lugar++;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

class VentanaCarrera extends JFrame{
    public VentanaCarrera(){
        super(".: HORSE RACING :.");
        JLabel caballo, horse, cheval, caballoPos, horsePos, chevalPos; 
        JButton inicioCarrera;

        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null); 

        Image imagenCaballo = new ImageIcon("/home/marco/Documentos/carreraCaballos/uno.gif").getImage();
        ImageIcon iconCaballo = new ImageIcon(imagenCaballo.getScaledInstance(50,50,Image.SCALE_DEFAULT));
        caballo = new JLabel();
        caballo.setIcon(iconCaballo);
        caballo.setBounds(50,50,50,50);

        Image imagenHorse = new ImageIcon("/home/marco/Documentos/carreraCaballos/uno.gif").getImage();
        ImageIcon iconHorse = new ImageIcon(imagenHorse.getScaledInstance(50,50,Image.SCALE_DEFAULT));
        horse = new JLabel();
        horse.setIcon(iconHorse);
        horse.setBounds(50,100,50,50);

        Image imagenCheval = new ImageIcon("/home/marco/Documentos/carreraCaballos/dos.gif").getImage();
        ImageIcon iconCheval = new ImageIcon(imagenCheval.getScaledInstance(50,50,Image.SCALE_DEFAULT));
        cheval = new JLabel();
        cheval.setIcon(iconCheval);
        cheval.setBounds(50,150,50,50);

        caballoPos = new JLabel();
        caballoPos.setBounds(50,50,350,50); 

        horsePos = new JLabel();
        horsePos.setBounds(50,100,350,50); 

        chevalPos = new JLabel();
        chevalPos.setBounds(50,150,350,50); 

        inicioCarrera = new JButton("INICIAR CARRERA"); 
        inicioCarrera.setBounds(150,200,150,50);

        inicioCarrera.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Hilo tcaballo = new Hilo("Caballo #Uno", caballo, caballoPos);
                Hilo thorse = new Hilo("Caballo #Dos", horse, horsePos);
                Hilo tcheval = new Hilo("Caballo #Tres", cheval, chevalPos);
            }
        });

        panel.add(caballo);
        panel.add(caballoPos);
        panel.add(horse);
        panel.add(horsePos);    
        panel.add(cheval);
        panel.add(chevalPos);   
        panel.add(inicioCarrera);   

        add(panel);
        setVisible(true);

    }   
}