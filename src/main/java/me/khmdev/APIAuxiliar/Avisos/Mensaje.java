package me.khmdev.APIAuxiliar.Avisos;

public class Mensaje {
	private String pl,msg;
	public Mensaje(String p,String m){
		pl=p;msg=m;
	}
	public String getPl() {
		return pl;
	}
	public void setPl(String pl) {
		this.pl = pl;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
