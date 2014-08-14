package me.khmdev.APIAuxiliar.Auxiliar;

public class Timer {
	long timeout,init;
	public Timer(long time){
		timeout=time;
		start();
	}
	public void changeTimeOut(long t){
		timeout=t;
	}

	public void start(){
		init=System.currentTimeMillis();

	}
	
	public boolean isEnd(){
		return (System.currentTimeMillis() - init) >= timeout;
	}
	
	public long getLeftSeconds(){
		return (timeout-(System.currentTimeMillis() - init ))/1000;
	}
}
