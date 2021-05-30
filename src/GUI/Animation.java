package GUI;

public class Animation implements Runnable {
	private MainWindow mw;
	public Animation(MainWindow mw) {
		this.mw=mw;
	}
	
	@Override
	public void run() {
		double longest=mw.longestPathByTime(mw.getPathList());           //finds the longest path.
		while(mw.time<longest) {                               			 //while the time is shorter than the longest path's time.
			mw.repaint();
			mw.pacmanMove(mw.getPacmanList(),mw.getPathList(),mw.time);  //makes all pacman move by the given time.
			mw.time+=5;
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
