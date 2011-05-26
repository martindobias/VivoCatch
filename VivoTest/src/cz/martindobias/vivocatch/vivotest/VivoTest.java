package cz.martindobias.vivocatch.vivotest;

import cz.martindobias.vivocatch.beans.Camera;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class VivoTest {

    public static void main(String[] args) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"vivotest.xml"});
        context.registerShutdownHook();

        Camera camera = (Camera) context.getBean("cam");
        camera.serverParameters.system.setHostname("test");
        System.out.println(camera.serverParameters.system.getHostname());
    }

}
