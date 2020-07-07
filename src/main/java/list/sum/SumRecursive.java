package list.sum;

import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class SumRecursive extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private final List<Long> nums;

    public SumRecursive(List<Long> nums) {
        this.nums = nums;
    }

    @Override
    protected Long compute() {
        int size = nums.size();
        if (size < THRESHOLD) {
            return nums.stream()
                    .reduce(0L, Long::sum);
        }
        int middle = size / 2;
        SumRecursive firstHalf = new SumRecursive(nums.subList(0, middle));
        SumRecursive secondHalf = new SumRecursive(nums.subList(middle, size));
        return ForkJoinTask.invokeAll(List.of(firstHalf, secondHalf))
                .stream()
                .mapToLong(ForkJoinTask::join)
                .sum();
    }
}
