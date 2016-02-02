import java.math.BigDecimal;
import java.util.Date;

public class SuperModel {
    private int                one;
    private String             two;
    private Date               three;
    private boolean            four;
    private BigDecimal         five;
    private BlendingStatusEnum six;
    private boolean            eight;

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

    public BlendingStatusEnum getSix() {
        return six;
    }

    public void setSix(BlendingStatusEnum six) {
        this.six = six;
    }

    public boolean isEight() {
        return eight;
    }

    public void setEight(boolean eight) {
        this.eight = eight;
    }

    @Override
    public String toString() {
        return "SuperModel [one=" + one + ", two=" + two + ", three=" + three + ", four=" + four
               + ", five=" + five + ", six=" + six + ", eight=" + eight + "]";
    }

}