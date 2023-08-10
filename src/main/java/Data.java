import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Data {
    private int addMore;
    private int deleteSome;
    private int contain;
    private int buy;
    private int sell;
    private int total;
    private Date date;

    private double score;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public Data() {
        this.addMore = 0;
        this.deleteSome = 0;
        this.contain = 0;
        this.buy = 0;
        this.sell = 0;
        this.total = 0;
        this.score = 0;
    }

    public void setScore(){
        score = ((double)(getAddMore() + getBuy() - 2 * getSell() - 2 * getDeleteSome()))/getTotal();
        score += 0.001111111;
    }

    public String getDate() {
        format.applyPattern("yyyy-MM-dd");
        return format.format(date);
    }

    public void setDate(String dateString) throws ParseException {
        this.date = format.parse(dateString);
    }

    public double getScore() {
        return score;
    }

    public void addField(String fieldName, String dateStr) throws ParseException {
        switch (fieldName){
            case "买入":
                setBuy(getBuy() + 1);
                break;
            case "增持":
                setAddMore(getAddMore() + 1);
                break;
            case "持有":
                setContain(getContain() + 1);
                break;
            case "减持":
                setDeleteSome(getDeleteSome() + 1);
                break;
            case "卖出":
                setSell(getSell() + 1);
                break;
        }
        setTotal(getTotal() + 1);
        setDate(dateStr);
    }

    @Override
    public String toString() {
        return "Data{" +
//                "score=" + score +
                "buy=" + buy +
                ", addMore=" + addMore +
                ", contain=" + contain +
                ", deleteSome=" + deleteSome +
                ", sell=" + sell +
                ", total=" + total +
//                ", date=" + getDate() +
                "}";
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getAddMore() {
        return addMore;
    }

    public void setAddMore(int addMore) {
        this.addMore = addMore;
    }

    public int getDeleteSome() {
        return deleteSome;
    }

    public void setDeleteSome(int deleteSome) {
        this.deleteSome = deleteSome;
    }

    public int getContain() {
        return contain;
    }

    public void setContain(int contain) {
        this.contain = contain;
    }

    public int getBuy() {
        return buy;
    }

    public void setBuy(int buy) {
        this.buy = buy;
    }

    public int getSell() {
        return sell;
    }

    public void setSell(int sell) {
        this.sell = sell;
    }
}
