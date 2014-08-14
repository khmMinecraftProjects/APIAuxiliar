package me.khmdev.APIAuxiliar.lang;
import java.util.*;

public class txt_es_ES extends ListResourceBundle 
{ 
   public Object[][] getContents() 
   {
      return contenido;
   }
   private Object[][] contenido = { 
      { "init_disable","%plugin% se desactivo debido ha que no encontro la API"}, 
      { "CustomItem_NoPerms","No tienes permisos %perms%"},
   };
}