package list.sum;

import java.util.List;
import java.util.concurrent.Callable;

public class SumCallable implements Callable<Long> {
    private final List<Long> nums;

    public SumCallable(List<Long> nums) {
        this.nums = nums;
    }

    @Override
    public Long call() {
        return nums.stream()
                .reduce(0L, Long::sum);
    }
}
