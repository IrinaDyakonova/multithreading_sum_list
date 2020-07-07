package list.sum;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Main {

    private static final int NUM_OF_THREADS = Runtime.getRuntime().availableProcessors();
    private static final int NUM_OF_NUMBERS = 1_000_000;

    public static void main(String[] args) throws Exception {
        List<Long> list = LongStream.range(0, NUM_OF_NUMBERS)
                .boxed()
                .collect(Collectors.toList());

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        SumRecursive recursiveSum = new SumRecursive(list);
        long forkStart = System.currentTimeMillis();
        Long forkJoinSum = forkJoinPool.invoke(recursiveSum);
        long forkEnd = System.currentTimeMillis();
        System.out.println("ForkJoin: " + forkJoinSum + ", time: " + (forkEnd - forkStart));

        SumExecutorService sumExecutorService = new SumExecutorService(list, NUM_OF_THREADS);
        long executorStart = System.currentTimeMillis();
        long sum = sumExecutorService.calculateListSum();
        long executorEnd = System.currentTimeMillis();
        System.out.println("Executor: " + sum + ", time: " + (executorEnd - executorStart));
    }
}
