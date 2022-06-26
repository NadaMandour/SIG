
package sig.model;

import java.util.ArrayList;


public class InvoiceLine {
     private InvoiceHeader invoice;
    private String name;
    private double price;
    private int count;
 private ArrayList<InvoiceLine> data;

    public InvoiceLine(InvoiceHeader invoice, String name, double price, int count) {
        this.invoice = invoice;
        this.name = name;
        this.price = price;
        this.count = count;
       // System.out.println(" from line********"+ "{ Item Name= " + name + "Item Price= " + price + "Count= " + count + "}\n");

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public InvoiceHeader getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceHeader invoice) {
        this.invoice = invoice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public double getLineTotal() {
        return count * price;
    }
     

    @Override
    public String toString() {
        return "             {" + "itemName=" + name + ", unitPrice=" + price + ", count=" + count + ", lineTotal=" + getLineTotal() + '}';
    }

    
    
}
