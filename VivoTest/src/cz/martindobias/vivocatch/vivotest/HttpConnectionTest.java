package cz.martindobias.vivocatch.vivotest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpConnectionTest {

  /**
   * @param args
   */
  public static void main(String[] args) {
      HttpURLConnection connection = null;
      OutputStreamWriter wr = null;
      BufferedReader rd  = null;
      StringBuilder sb = null;
      String line = null;

      URL serverAddress = null;

      try {
          serverAddress = new URL("http://jagellonska.imatic.cz/cgi-bin/admin/ctrlevent.cgi");
          //set up out communications stuff
          connection = null;

          //Set up the initial connection
          connection = (HttpURLConnection)serverAddress.openConnection();
          connection.setRequestMethod("GET");
          connection.setRequestProperty("User-Agent", "TunnelClient");
          connection.setRequestProperty("x-sessioncookie", "5AasdGTHfgjwsqDdSF33");
          connection.setRequestProperty("Accept", "application/x-vvtk-tunnelled");
          connection.setRequestProperty("Pragma", "no-cache");
          connection.setRequestProperty("Cache-Control", "no-cache");
          connection.setRequestProperty("Connection", "Keep-Alive");

          //connection.setDoOutput(true);

          connection.connect();

          for (int i=0; ; i++) {
              String headerName = connection.getHeaderFieldKey(i);
              String headerValue = connection.getHeaderField(i);

              if (headerName == null && headerValue == null) {
                  // No more headers
                  break;
              }
              if (headerName == null) {
                  System.out.println(headerValue);
              }
              else {
                  System.out.println(headerName + " : " + headerValue);
              }
          }

          //read the result from the server
          rd  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
          sb = new StringBuilder();

          while ((line = rd.readLine()) != null)
          {
              sb.append(line + '\n');
          }

          System.out.println(sb.toString());

      } catch (MalformedURLException e) {
          e.printStackTrace();
      } catch (ProtocolException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }
      finally
      {
          //close the connection, set all objects to null
          connection.disconnect();
          rd = null;
          sb = null;
          wr = null;
          connection = null;
      }
  }
}
