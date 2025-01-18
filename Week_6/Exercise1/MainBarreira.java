package Week_6.Exercise1;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MainBarreira {
	static final int NUM_BLOCKS_TO_BE_SEARCHED=1000;
	static final int STRING_LENGTH=1024;
	static final String STRING_TO_BE_FOUND="huik";
	
	public static void main(String[] args) throws InterruptedException {
		final long initTime=System.currentTimeMillis();
		SearcherThread[] threads=new SearcherThread[NUM_BLOCKS_TO_BE_SEARCHED];
		CyclicBarrier barrier= new CyclicBarrier(NUM_BLOCKS_TO_BE_SEARCHED,new Runnable() {
			@Override
			public void run() {
				int count=0;
				for(SearcherThread t:threads)
					if(t.getResult()!=-1){
						//System.out.println("Found at "+t.getResult());//+" in "+t.getMyText());
						count++;
					}
				System.out.println("Barrier Search DONE. Found:"+count+" Time:"+
					(System.currentTimeMillis()-initTime));
				
			}
		});


		SearcherThreadForLatch[] threadsL = new SearcherThreadForLatch[NUM_BLOCKS_TO_BE_SEARCHED];
		CountDownLatch latch = new CountDownLatch(NUM_BLOCKS_TO_BE_SEARCHED);

		ExecutorService threadPool = Executors.newFixedThreadPool(4);

		RandomString rs=new RandomString(STRING_LENGTH);
		RandomString ls=new RandomString(STRING_LENGTH);
		RandomString ts=new RandomString(STRING_LENGTH);

		final int[] threadPoolCount = {0};
		final long[] finalThreadPoolTime = {0};

		for(int i=0; i!=NUM_BLOCKS_TO_BE_SEARCHED;i++){
			threadsL[i] = new SearcherThreadForLatch(ls.nextString(), STRING_TO_BE_FOUND, latch);
			threads[i]=new SearcherThread(rs.nextString(), STRING_TO_BE_FOUND, barrier);
			threads[i].start();
			threadsL[i].start();


			threadPool.submit(new Runnable() {
				@Override
				public void run() {
					if(ts.nextString().indexOf(STRING_TO_BE_FOUND) != -1)threadPoolCount[0]++;
					finalThreadPoolTime[0] = System.currentTimeMillis();
				}
			});
		}


        try {
            latch.await();
			AtomicInteger count = new AtomicInteger();
			long finalTime = System.currentTimeMillis();
			Arrays.stream(threadsL).toList().forEach(t->{
				if(t.getResult() != -1) count.getAndIncrement();
			});
			System.out.println("Latch Search DONE. Found:"+count+" Time:"+ (finalTime - initTime));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

		threadPool.awaitTermination(1000, TimeUnit.MILLISECONDS);
		System.out.println("ThreadPool Search DONE.  Found: " + threadPoolCount[0] + " Time: " + (finalThreadPoolTime[0] - initTime));

    }
}
