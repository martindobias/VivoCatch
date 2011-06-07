package cz.martindobias.vivocatch;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import cz.martindobias.vivocatch.beans.Desktop;
import cz.martindobias.vivocatch.forms.MainWindow;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;

public class VivoCatch {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new PlasticLookAndFeel());
        } catch(UnsupportedLookAndFeelException ignored) {
        }
        //PlasticLookAndFeel.setCurrentTheme(new DesertBluer());

        AbstractApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"vivocatch.xml"});
        context.registerShutdownHook();

        Desktop desktop = (Desktop) context.getBean("desktop");

        new MainWindow(desktop).setVisible(true);
    }

}
