package utilities;

import java.util.Properties;

public interface SwingMailReceiver {
    //podemos hacer que devuelva el mensaje para luego ya tratarlo fuera del metodo
    //le pasamos las properties de la session a crear ya
   void receiveEmail(Properties properties);

}
