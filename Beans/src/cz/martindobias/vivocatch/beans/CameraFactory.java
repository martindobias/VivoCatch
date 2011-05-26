package cz.martindobias.vivocatch.beans;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class CameraFactory implements MethodInterceptor {

    private Object tmp;

    public Object createInstance(String ip) {
        CameraFactory interceptor = new CameraFactory();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Camera.class);
        enhancer.setCallback(interceptor);
        return enhancer.create(new Class[]{String.class}, new Object[]{ip});
    }

    /**
     * All generated proxied methods call this method instead of the original method.
     * The original method may either be invoked by normal reflection using the Method object,
     * or by using the MethodProxy (faster).
     *
     * @param obj    "this", the enhanced object
     * @param method intercepted Method
     * @param args   argument array; primitive types are wrapped
     * @param proxy  used to invoke super (non-intercepted method); may be called
     *               as many times as needed
     * @return any value compatible with the signature of the proxied method. Method returning void will ignore this value.
     * @throws Throwable any exception may be thrown; if so, super method will not be invoked
     * @see net.sf.cglib.proxy.MethodProxy
     */
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if(Modifier.isAbstract(method.getModifiers()) && method.getName().startsWith("set") && args.length == 1 && method.getReturnType() == Void.TYPE) {
            tmp = args[0];
            CameraProperty property = method.getAnnotation(CameraProperty.class);
            System.out.println(property.name());
            return null;
        }

        if(Modifier.isAbstract(method.getModifiers()) && method.getName().startsWith("get") && args.length == 0) {
            CameraProperty property = method.getAnnotation(CameraProperty.class);
            System.out.println(property.name());
            return tmp;
        }

        return null;
    }

}
