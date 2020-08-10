public class Stats {
    private float averageIncome;
    private float averagePayments;

    Stats(float averageIncome, float averagePayments){
        this.averageIncome = averageIncome;
        this.averagePayments = averagePayments;
    }

    public float getAveragePayments() {
        return averagePayments;
    }

    public float getAverageIncome() {
        return averageIncome;
    }
}
