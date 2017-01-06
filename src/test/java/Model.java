import java.math.BigDecimal;
import java.util.Date;

public class Model {
    private int                a;
    private String             b;
    private Date c;
    private Boolean            d;
    private BigDecimal e;
    private BlendingStatusEnum f;
    private BigDecimal         g;
    private boolean            h;
    private int                i;
    private Integer            j;
    private char               k;

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

    public Boolean isD() {
        return d;
    }

    public void setD(Boolean d) {
        this.d = d;
    }

    public BigDecimal getE() {
        return e;
    }

    public void setE(BigDecimal e) {
        this.e = e;
    }

    public BlendingStatusEnum getF() {
        return f;
    }

    public void setF(BlendingStatusEnum f) {
        this.f = f;
    }

    public BigDecimal getG() {
        return g;
    }

    public void setG(BigDecimal g) {
        this.g = g;
    }

    public boolean isH() {
        return h;
    }

    public void setH(boolean h) {
        this.h = h;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public Boolean getD() {
        return d;
    }

    public Integer getJ() {
        return j;
    }

    public void setJ(Integer j) {
        this.j = j;
    }

    public char getK() {
        return k;
    }

    public void setK(char k) {
        this.k = k;
    }

    @Override
    public String toString() {
        return "Model{" + "a=" + a + ", b='" + b + '\'' + ", c=" + c + ", d=" + d + ", e=" + e
               + ", f=" + f + ", g=" + g + ", h=" + h + ", i=" + i + ", j=" + j + ", k=" + k + '}';
    }
}
