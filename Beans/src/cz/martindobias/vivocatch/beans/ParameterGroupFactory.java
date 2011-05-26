package cz.martindobias.vivocatch.beans;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ParameterGroupFactory implements MethodInterceptor {

    private static final ParameterGroupFactory interceptor = new ParameterGroupFactory();

    public static ParameterGroup createInstance(Class<? extends ParameterGroup> cl, String url) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cl);
        enhancer.setCallback(interceptor);
        return (ParameterGroup) enhancer.create(new Class[]{String.class}, new Object[]{url});
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
            ParameterGroupProperty group = obj.getClass().getSuperclass().getAnnotation(ParameterGroupProperty.class);
            ParameterProperty property = method.getAnnotation(ParameterProperty.class);

            System.out.println(((ParameterGroup) obj).url);
            System.out.println(group.group());
            System.out.println(property.name());
            return null;
        }

        if(Modifier.isAbstract(method.getModifiers()) && method.getName().startsWith("get") && args.length == 0) {
            ParameterGroupProperty group = obj.getClass().getSuperclass().getAnnotation(ParameterGroupProperty.class);
            ParameterProperty property = method.getAnnotation(ParameterProperty.class);

            System.out.println(((ParameterGroup) obj).url);
            System.out.println(group.group());
            System.out.println(property.name());
        }

        return null;
    }
}
