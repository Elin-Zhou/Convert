import java.math.BigDecimal;
import java.util.Date;

public class SuperDo {
    private int        one;
    private String     two;
    private Date three;
    private boolean    four;
    private BigDecimal five;
    private String     six;
    private BigDecimal seven;
    private String     eight;

    public int getOne() {
        return one;
    }

    public void setOne(int one) {
        this.one = one;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public Date getThree() {
        return three;
    }

    public void setThree(Date three) {
        this.three = three;
    }

    public boolean isFour() {
        return four;
    }

    public void setFour(boolean four) {
        this.four = four;
    }

    public BigDecimal getFive() {
        return five;
    }

    public void setFive(BigDecimal five) {
        this.five = five;
    }

    public String getSix() {
        return six;
    }

    public void setSix(String six) {
        this.six = six;
    }

    public BigDecimal getSeven() {
        return seven;
    }

    public void setSeven(BigDecimal seven) {
        this.seven = seven;
    }

    public String getEight() {
        return eight;
    }

    public void setEight(String eight) {
        this.eight = eight;
    }

    @Override
    public String toString() {
        return "SuperDo [one=" + one + ", thw=" + two + ", three=" + three + ", four=" + four
               + ", five=" + five + ", six=" + six + ", seven=" + seven + ", eight=" + eight + "]";
    }

}
