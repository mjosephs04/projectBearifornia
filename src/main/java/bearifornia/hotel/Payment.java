package bearifornia.hotel;

public class Payment {
    private final Integer reservationId;
    private final Integer guestId;
    private final double amount;
    private boolean paymentStatus;

    public Payment(Integer reservationId, Integer guestId, double amount) {
        this.reservationId = reservationId;
        this.guestId = guestId;
        this.amount = amount;
        this.paymentStatus = false; // Payment status is false until processed
    }

    // Process payment through Bank
    public boolean processPayment() {
        Bank bank = new Bank();
        this.paymentStatus = bank.processTransaction(amount);
        return paymentStatus;
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public Integer getGuestId() {
        return guestId;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }
}
