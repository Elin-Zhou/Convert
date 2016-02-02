import java.util.Date;

public class SubDo extends SuperDo {
    private int     a;
    private String  b;
    private Date c;
    private boolean d;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public Date getC() {
        return c;
    }

    public void setC(Date c) {
        this.c = c;
    }

    public boolean isD() {
        return d;
    }

    public void setD(boolean d) {
        this.d = d;
    }

    @Override
    public String toString() {
        return super.toString() + "SubDo1 [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + "]";
    }

}
