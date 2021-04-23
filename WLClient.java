/*

java -cp .:lib/wlthint3client.jar WLClient $URL "$PRINCIPAL" $JNDI

where JNDI can be anything from a JDBC name to any other object.

*/

import java.util.Hashtable;
import javax.naming.*;

public class WLClient {
   private InitialContext ctx = null;

   /*NOTE: The next two lines set the name of the Queue Connection Factory
           and the Queue that we want to use. */
   private String JNDI_NAME = "";
   private String url = "";
   private String principal = "";

   private final String WL_INIT_CONTEXT = "weblogic.jndi.WLInitialContextFactory";

   public WLClient(String url, String principal) {
       super();
       this.url = url;
       this.principal = principal;
   }

   public void getObject(String JNDI_NAME) {
       /* create InitialContext */
       Hashtable properties = new Hashtable();
       properties.put(Context.INITIAL_CONTEXT_FACTORY, this.WL_INIT_CONTEXT);
       /*NOTE:The port number of the server is provided in the next line,
              followed by the userid and password on the next two lines. */
       properties.put(Context.PROVIDER_URL, this.url);
       properties.put(Context.SECURITY_PRINCIPAL, this.principal);
       properties.put(Context.SECURITY_CREDENTIALS, "");

       try {
           ctx = new InitialContext(properties);
           System.out.println("Got InitialContext " + ctx.toString());

           Object obj = (Object)ctx.lookup(JNDI_NAME);
           System.out.println("Got Object " + obj.toString());
       } catch (Exception e) {
           e.printStackTrace(System.err);
           System.exit(0);
       }
   }

   public static void main(String args[]) {
      WLClient client = new WLClient(args[0], args[1]);
      client.getObject(args[2]);
   }
}
