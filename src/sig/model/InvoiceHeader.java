
package sig.model;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class InvoiceHeader {
    
    private int num;
    private String customer;
    private Date date;
    private ArrayList<InvoiceLine> lines;
    public InvoiceHeader(){}
    public InvoiceHeader(int num,  Date date,String customer, double total) {
        this.num = num;
        this.customer = customer;
        this.date = date;
        //System.out.println("************from header"+"num= "+ num+" Customer= " +customer +" Date= "+ date);

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
         if (lines == null) {
            lines = new ArrayList<>();
        }
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
   
   private  String dateFormater(Date format)
    {
     
        String date = format.toString();
        DateTimeFormatter f = DateTimeFormatter.ofPattern( "E MMM dd HH:mm:ss z uuuu" ).withLocale( Locale.US );
 
ZonedDateTime zdt = ZonedDateTime.parse( date , f );
 
LocalDate ld = zdt.toLocalDate();
DateTimeFormatter fLocalDate = DateTimeFormatter.ofPattern( "dd-MM-uuuu" );
String outputDate = ld.format( fLocalDate) ;
        
        return outputDate;
        
    }
   @Override
   
    public String toString() {
        return "Invoice Num.=" + num + ", CustomerName=" + customer + ", Date=" + dateFormater(date)+ ", Total=" + getInvoiceTotal() + '}';
    }
 
    

   
    
}
