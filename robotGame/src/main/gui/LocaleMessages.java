package gui;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleMessages {

    public static ResourceBundle messages = ResourceBundle.getBundle("locale.messages", Locale.getDefault());

    public void changeLocale(Locale locale){
        messages = ResourceBundle.getBundle("locale.messages", locale);
    }

    public ResourceBundle getMessages(){
        return messages;
    }
}
