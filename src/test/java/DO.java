import com.elin4it.util.convert.ConvertAlias;

import java.math.BigDecimal;
import java.util.Date;

public class DO extends ConvertAlias {
    private int        a;
    private String     b;
    private Date c;
    private boolean    d;
    private BigDecimal e;
    private String     f;
    private BigDecimal gg;
    private String     h;
    private int        j;
    private Character  k;

    {
        this.addAlias("a", "aa");
        this.addAlias("gg", "g");
    }

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

    public BigDecimal getE() {
        return e;
    }

    public void setE(BigDecimal e) {
        this.e = e;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public BigDecimal getGg() {
        return gg;
    }

    public void setGg(BigDecimal g) {
        this.gg = g;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public Character getK() {
        return k;
    }

    public void setK(Character k) {
        this.k = k;
    }

    @Override
    public String toString() {
        return "DO{" + "a=" + a + ", b='" + b + '\'' + ", c=" + c + ", d=" + d + ", e=" + e
               + ", f='" + f + '\'' + ", gg=" + gg + ", h='" + h + '\'' + ", j=" + j + ", k=" + k
               + '}';
    }
}