package Week_6.Exercise1;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class SearcherThread extends Thread{
		private String myText;
		private String textToFind;
		private CyclicBarrier barrier;
		private int result=-1;

		public SearcherThread(String myText, String textToFind,
				CyclicBarrier barrier) {
			this.myText = myText;
			this.textToFind = textToFind;
			this.barrier = barrier;
		}

		public String getMyText() {
			return myText;
		}

		public int getResult() {
			return result;
		}

		@Override
		public void run() {
			result=myText.indexOf(textToFind);
			try {
				barrier.await();
				//System.err.println("Barrier Thread finishing at: "+System.currentTimeMillis());
			} catch (InterruptedException | 
					BrokenBarrierException e) {
			}
		}
		
		
	}

