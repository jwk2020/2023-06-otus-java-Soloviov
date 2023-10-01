package tester.result;

public class TestResult {
    private final int successCount;
    private final int errorCount;

    public TestResult(int successCount, int errorCount) {
        this.successCount = successCount;
        this.errorCount = errorCount;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public int getTotalCount() {
        return successCount + errorCount;
    }
}
