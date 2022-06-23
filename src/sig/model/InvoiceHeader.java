
package sig.model;

import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;


public class InvoiceHeader {
    
    private int num;
    private String customer;
    private Date date;
    private ArrayList<InvoiceLine> lines;

    public InvoiceHeader(){}
    public InvoiceHeader(int num, String customer, Date date) {
        this.num = num;
        this.customer = customer;
        this.date = date;
        //System.out.println(" from Header"+"num ="+ num +"customer = "+customer +"Date = " +date);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        
        this.date = date;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public ArrayList<InvoiceLine> getLines() {
        return lines;
    }

    public void setLines(ArrayList<InvoiceLine> lines) {
        this.lines = lines;
    }
    
 public double getInvoiceTotal() {
        double total = 0;
        for (InvoiceLine line : getLines()) {
            total += line.getLineTotal();
        }
        return total;
    }
   @Override
    public String toString() {
        return "InvoiceHeader{" + "Invoice Num.=" + num + ", CustomerName=" + customer + ", Date=" + date + '}';
    }

    public ArrayList<InvoiceLine> getLines() {
        if (lines == null) {
            lines = new ArrayList<>();
        }
        return lines;
    }
    
}
