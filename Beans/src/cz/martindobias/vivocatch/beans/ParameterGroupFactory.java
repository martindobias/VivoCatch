package cz.martindobias.vivocatch.beans;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ParameterGroupFactory implements MethodInterceptor {

    private static final ParameterGroupFactory interceptor = new ParameterGroupFactory();
    private static final HttpClient httpClient;
    static {
        httpClient = new DefaultHttpClient();
        HttpParams parameters = httpClient.getParams();
        parameters.setParameter(HttpProtocolParams.PROTOCOL_VERSION, new ProtocolVersion("HTTP", 1, 0));
    }

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
            ParameterGroup settings = (ParameterGroup) obj;
            ParameterGroupProperty group = obj.getClass().getSuperclass().getAnnotation(ParameterGroupProperty.class);
            ParameterProperty property = method.getAnnotation(ParameterProperty.class);

            String propertyName = String.format("%s_%s", group.group(), property.name());
            String value = property.type().convert(args[0]);
            HttpGet httpGet = new HttpGet(String.format("%s/cgi-bin/%s/setparam.cgi?%s=%s", settings.url, property.subdir(), propertyName, value));
            HttpResponse response = httpClient.execute(httpGet);
            String result = EntityUtils.toString(response.getEntity());
            if(result == null || result.equals("")) {
                throw new RuntimeException(String.format("Connection error, no result returned: %s", propertyName));
            }

            int index = result.indexOf('=');
            if(index == -1) {
                throw new RuntimeException(String.format("Response malformed: %s", result));
            }

            String newProperty = result.substring(0, index);
            String newValue = result.substring(index + 1);
            if(!propertyName.equals(newProperty) || !args[0].equals(property.type().parse(newValue))) {
                throw new RuntimeException(String.format("Property not set: %s = %s | %s", propertyName, value, result));
            }
            return null;
        }

        if(Modifier.isAbstract(method.getModifiers()) && method.getName().startsWith("get") && args.length == 0) {
            ParameterGroup settings = (ParameterGroup) obj;
            ParameterGroupProperty group = obj.getClass().getSuperclass().getAnnotation(ParameterGroupProperty.class);
            ParameterProperty property = method.getAnnotation(ParameterProperty.class);

            String propertyName = String.format("%s_%s", group.group(), property.name());
            HttpGet httpGet = new HttpGet(String.format("%s/cgi-bin/%s/getparam.cgi?%s", settings.url, property.subdir(), propertyName));
            HttpResponse response = httpClient.execute(httpGet);
            String result = EntityUtils.toString(response.getEntity());
            if(result == null || result.equals("")) {
                throw new RuntimeException(String.format("Connection error, no result returned: %s", propertyName));
            }

            int index = result.indexOf('=');
            if(index == -1) {
                throw new RuntimeException(String.format("Response malformed: %s", result));
            }

            String newProperty = result.substring(0, index);
            String newValue = result.substring(index + 1);
            if(!propertyName.equals(newProperty)) {
                throw new RuntimeException(String.format("Invalid property returned: %s | %s", propertyName, newProperty));
            }
            return property.type().parse(newValue);
        }

        return null;
    }
}
