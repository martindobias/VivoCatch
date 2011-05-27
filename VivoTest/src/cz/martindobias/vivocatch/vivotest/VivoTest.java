package cz.martindobias.vivocatch.vivotest;

import cz.martindobias.vivocatch.beans.Camera;
import cz.martindobias.vivocatch.network.ControlEventChannel;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class VivoTest {

    public static void main(String[] args) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"vivotest.xml"});
        context.registerShutdownHook();

        Camera camera = (Camera) context.getBean("cam");

        System.out.println(camera.serverParameters.system.getHostname());
        System.out.println(camera.serverParameters.system.getTimezoneIndex());

        boolean ledOff = camera.serverParameters.system.getLedOff();
        System.out.println(ledOff);
        camera.serverParameters.system.setLedOff(!ledOff);
        camera.serverParameters.system.setLedOff(ledOff);

        ControlEventChannel channel = new ControlEventChannel();
        channel.open("jagellonska.imatic.cz", 80);
        try {
            System.in.read();
        } catch(IOException ignored) {
        }
    }

}
