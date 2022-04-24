package lostark.calc.model;

public class EffResponse {
    private Double amountDiff;

    public EffResponse() {
    }

    public EffResponse(Double amountDiff) {
        this.amountDiff=amountDiff;
    }

    public Double getAmountDiff() {
        return amountDiff;
    }

    public void setAmountDiff(Double amountDiff) {
        this.amountDiff = amountDiff;
    }

    @Override
    public String toString() {
        return "EffResponse{" +
            "amountDiff=" + amountDiff +
            '}';
    }
}
