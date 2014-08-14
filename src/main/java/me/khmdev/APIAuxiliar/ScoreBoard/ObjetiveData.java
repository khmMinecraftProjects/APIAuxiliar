package me.khmdev.APIAuxiliar.ScoreBoard;

import org.bukkit.entity.Player;

public class ObjetiveData {
	private FunctorString title;
	private Functor f;
	public ObjetiveData(FunctorString str,Functor func){
		title=str;
		f=func;
	}
	public String getTitle(Player p) {
		if (title==null){return null;}
		String s=title.getString(p);
		if(s.length()>15){s=s.substring(0,15);}
		return s;
	}

	public int getF(Player p) {
		return f.getInt(p);
	}
	public void setTitle(FunctorString funct) {
		title=funct;
	}

}
