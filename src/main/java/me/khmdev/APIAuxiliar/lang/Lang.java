package me.khmdev.APIAuxiliar.lang;

import java.util.Locale;
import java.util.ResourceBundle;

public class Lang {
	public static ResourceBundle lang=
			ResourceBundle.getBundle("me.khmdev.APIAuxiliar.lang.txt",
			new Locale("es", "ES"));

	public static String get(String s){
		return lang.getString(s);
	}

}
