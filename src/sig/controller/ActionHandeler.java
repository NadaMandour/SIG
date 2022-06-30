package sig.controller;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import sig.model.InvoiceLine;
import sig.model.InvoiceHeader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import sig.model.invHeaderTableModel;
import sig.model.invLineTableModel;
import sig.view.NewJFrame;

public class ActionHandeler implements ActionListener, ListSelectionListener {

    private NewJFrame frame;

    public ActionHandeler(NewJFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "New Invoice": {
                newInvoice();
            }
            break;
            case "Delete Invoice": {
                deleteInvoice();
            }
            break;
            case "New Item": {
                newItem();
            }
            break;
            case "Delete Item": {
                deleteItem();
            }
            break;
            case " Load file": {
                loadeFile();
            }
            break;
            case "Save file": {
                saveFile();
            }
            break;

        }

    }
    ArrayList<InvoiceLine> invoiceLinesList = new ArrayList<>();

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (frame.getInvHeaderTable().getSelectedRow() != -1) {
            System.out.println("Row Selected");

            int selectedRow = frame.getInvHeaderTable().getSelectedRow();
            System.out.println(selectedRow);
            ArrayList<InvoiceLine> lines = frame.getInvoiceHeadersList().get(selectedRow).getLines();
            frame.getInvLineTable().setModel(new invLineTableModel(lines));
            //  ---------   
            invoiceLinesList = lines;
            //display on labels
            JLabel numLable = frame.getNumberLable();
            frame.getInvoiceHeadersList().get(selectedRow).getNum();
            numLable.setText(String.valueOf(frame.getInvoiceHeadersList().get(selectedRow).getNum()));
            frame.setNumberLabel(numLable);
            //System.out.println("-----" + numLable.getText());
            //
            JLabel custLable = frame.getCustomerLabel();
            frame.getInvoiceHeadersList().get(selectedRow).getCustomer();
            custLable.setText(String.valueOf(frame.getInvoiceHeadersList().get(selectedRow).getCustomer()));
            frame.setCustomerLabel(custLable);
            //System.out.println("-----" + custLable.getText());
            //

            JLabel dateLabel = frame.getDateLable();
            frame.getInvoiceHeadersList().get(selectedRow).getDate();
            dateLabel.setText(String.valueOf(frame.getInvoiceHeadersList().get(selectedRow).getDate()));
            frame.setDateLabel(dateLabel);
            // System.out.println("-----" + dateLabel.getText());
            //
            JLabel totalLable = frame.getTotalLabel();
            frame.getInvoiceHeadersList().get(selectedRow).getInvoiceTotal();
            totalLable.setText(String.valueOf(frame.getInvoiceHeadersList().get(selectedRow).getInvoiceTotal()));
            frame.setTotalLabel(totalLable);
            // System.out.println("-----" + totalLable.getText());
        }
        /*else if (frame.getInvHeaderTable().getSelectedRow() == -1) {
            frame.getInvHeaderTable().setRowSelectionInterval(0, 0);
        }
        else if (frame.getInvLineTable().getSelectedRow() == -1) {
            frame.getInvLineTable().setRowSelectionInterval(0, 0);
        }
         */
    }
    /**
     * ********************************************
     */
    int invoiceNum = 1;
    String invoiceCustomer = null;
    Date invoiceDate = null;
    InvoiceHeader invHeder;
    JTable headerTable = new JTable();

    /**
     * " New Invoice" **********************************
     */
    private void newInvoice() {

        JFrame frame1 = new JFrame();
        frame1.setTitle("New Invoice");
        System.out.println("Action New Invoie");

        JTextField invDateTextField = new JTextField(20);
        JTextField customer = new JTextField(20);
        JButton okBtn = new JButton("OK");
        okBtn.setActionCommand("okInv");
        okBtn.addActionListener(this);
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setActionCommand("cancelInv");
        cancelBtn.addActionListener(this);

        frame1.setLayout(new FlowLayout());
        frame1.add(new JLabel("Invoce Date"));
        frame1.add(invDateTextField); //textfield
        frame1.add(new JLabel("Customer"));
        frame1.add(customer);//textfield
        frame1.add(okBtn);
        frame1.add(cancelBtn);
//parameters
        // frame.getInvLineTable().getSelectionModel().clearSelection();
        // frame.getInvHeaderTable().getSelectionModel().clearSelection();

        // System.out.println(selectedRow);
        okBtn.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                // ArrayList<InvoiceHeader> invoiceHeadersList = new ArrayList<>();
                frame.getInvHeaderTable().setRowSelectionAllowed(false);
                frame.getInvLineTable().setRowSelectionAllowed(false);

                System.out.println("action from Ok Btn");
                invoiceCustomer = customer.getText();
                try {

                    Date invoiceDateD = new SimpleDateFormat("dd-MM-yyyy").parse(invDateTextField.getText());
                    DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
                    invoiceDate = (Date) formatter.parse(invoiceDateD.toString());
                    System.out.println(invoiceDate);

                } catch (ParseException ex) {
                }
                //display on table
                ArrayList<InvoiceHeader> invoiceHeaderList = frame.getInvoiceHeadersList();

                if (invoiceHeaderList.isEmpty()) {
                    invoiceNum = 1;
                } else {
                    invoiceNum = (getLastInvoiceNumber(invoiceHeaderList)) + 1;
                }
                double invHeaderTotal = 0;
                invHeder = new InvoiceHeader(invoiceNum, invoiceDate, invoiceCustomer, invHeaderTotal);
                invoiceHeadersList.add(invHeder);
                frame.setInvoiceHeadersList(invoiceHeadersList);
                // for handling row selection excepitions
                frame.getInvHeaderTable().setRowSelectionAllowed(true);
                frame.getInvLineTable().setRowSelectionAllowed(true);

                //System.out.println("custome0r=  " + invoiceCustomer + "   Date= " + invoiceDate);
                frame1.getContentPane().removeAll();
                frame1.repaint();
                frame1.setVisible(false);
            }
        });
        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                System.out.println("action from cancel Btn");
                frame1.getContentPane().removeAll();
                frame1.repaint();
                frame1.setVisible(false);
                frame.getInvHeaderTable().setRowSelectionAllowed(true);
                frame.getInvLineTable().setRowSelectionAllowed(true);
            }
        });
        //frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(350, 150);
        frame1.setLocation(100, 100);
        frame1.setVisible(true);
    }

    /**
     * ****************************************
     */
    private void deleteInvoice() {

        System.out.println("Action Delete Invoice");

        ListSelectionModel tableModel = frame.getInvHeaderTable().getSelectionModel();
        tableModel.addListSelectionListener(this);

        int selectedHeaderRow = frame.getInvHeaderTable().getSelectedRow();
        System.out.println("Row Selected");
        System.out.println(selectedHeaderRow);

        if (selectedHeaderRow != -1) {
            ArrayList<InvoiceHeader> invoiceHeaderList = frame.getInvoiceHeadersList();

            System.out.println(invoiceHeaderList);
            invoiceHeaderList.remove(selectedHeaderRow);
            // System.out.println("............" + invoiceHeaderList);

            frame.setInvoiceHeadersList(invoiceHeaderList);
            JOptionPane.showMessageDialog(null, "invoice Deleted successfully");
        } else if (selectedHeaderRow == -1) {
            JOptionPane.showMessageDialog(null, "please Select Invoice");

        }


        /*      JFrame frame = new JFrame();

        System.out.println("Action Delete invoice");
        frame.setTitle("Delete Invoice");
        frame.setLayout(new FlowLayout());
        frame.add(new JLabel("         Comfirm Deleting Invoice         "));
        JButton confirmBtn = new JButton("Confirm");
        JButton cancelBtn = new JButton("Cancel");
        frame.add(confirmBtn);
        frame.add(cancelBtn);

        confirmBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(275, 100);
        frame.setLocation(100, 100);
        frame.setVisible(true);
         */
    }

    /**
     * ****************************************************
     */
    String itmName = null;
    double price;
    int count;
    //ArrayList<InvoiceLine> invoiceLinesList = new ArrayList<>();

    private void newItem() {
        JFrame frame1 = new JFrame();

        System.out.println("Action New Item");
        frame1.setTitle(" New Item");

        JTextField itemName = new JTextField(20);
        JTextField itemCount = new JTextField(20);
        JTextField ItemPrice = new JTextField(20);

        JButton okBtn = new JButton("OK");
        okBtn.setActionCommand("okItm");
        okBtn.addActionListener(this);
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setActionCommand("cancelItm");
        cancelBtn.addActionListener(this);

        frame1.setLayout(new FlowLayout());
        frame1.add(new JLabel("Item Name"));
        frame1.add(itemName);
        frame1.add(new JLabel("Item Count"));
        frame1.add(itemCount);
        frame1.add(new JLabel("Price     "));
        frame1.add(ItemPrice);
        frame1.add(okBtn);
        frame1.add(cancelBtn);

        okBtn.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                System.out.println("action from Ok Btn");
                frame.getInvHeaderTable().setRowSelectionAllowed(false);
                frame.getInvLineTable().setRowSelectionAllowed(false);

                itmName = itemName.getText();
                price = Integer.valueOf(ItemPrice.getText());
                count = Integer.valueOf(itemCount.getText());

                System.out.println("invoiceHeder=" + invHeder + "{ item Name= " + itmName + " Price= " + price + "Count= " + count + "}");

                InvoiceLine invLine = new InvoiceLine(invHeder, itmName, price, count);
                int selectedRow = frame.getInvHeaderTable().getSelectedRow();
                System.out.println(selectedRow);
                if (selectedRow != -1) {

                    ArrayList<InvoiceLine> invoiceLinesList = frame.getInvoiceHeadersList().get(selectedRow).getLines();
                    // frame.getInvLineTable().setModel(new invLineTableModel(invoiceLinesList));

                    invoiceLinesList.add(invLine);
                    // System.out.println(">>>>>>>>>>" + frame.getInvoiceLinesList());
                    frame.setInvoiceLinesList(invoiceLinesList);
//update total label
                    JLabel totalLable = frame.getTotalLabel();
                    frame.getInvoiceHeadersList().get(selectedRow).getInvoiceTotal();
                    totalLable.setText(String.valueOf(frame.getInvoiceHeadersList().get(selectedRow).getInvoiceTotal()));
                    frame.setTotalLabel(totalLable);
                    frame.getInvHeaderTable().setRowSelectionAllowed(true);
                    frame.getInvLineTable().setRowSelectionAllowed(true);

                    // System.out.println("-----" + totalLable.getText());
                    frame1.getContentPane().removeAll();
                    frame1.repaint();
                    frame1.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "please select Invoice first");

                }
            }
        });
        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                System.out.println("action from cancel Btn");
                frame1.getContentPane().removeAll();
                frame1.repaint();
                frame1.setVisible(false);
                frame.getInvHeaderTable().setRowSelectionAllowed(true);
                frame.getInvLineTable().setRowSelectionAllowed(true);
            }
        });

        frame1.setSize(350, 200);
        frame1.setLocation(100, 100);
        frame1.setVisible(true);

    }

    /**
     * **************************************************
     */
    private void deleteItem() {
        System.out.println("Action Delete Item");
        int selectedRow = frame.getInvLineTable().getSelectedRow();
        System.out.println(selectedRow);
        //System.out.println("............" +  frame.getInvLineTable().getr);

        if (selectedRow != -1) {
            //ArrayList<InvoiceLine> invoiceLineList = frame.getInvoiceLinesList();
            System.out.println(invoiceLinesList);
            invoiceLinesList.remove(selectedRow);
            //System.out.println("............" + invoiceLinesList);
            frame.setInvoiceLinesList(invoiceLinesList);

            //update toral label
            JLabel totalLable = frame.getTotalLabel();
            frame.getInvoiceHeadersList().get(selectedRow).getInvoiceTotal();
            totalLable.setText(String.valueOf(frame.getInvoiceHeadersList().get(selectedRow).getInvoiceTotal()));
            frame.setTotalLabel(totalLable);

            // System.out.println("-----" + totalLable.getText());
            JOptionPane.showMessageDialog(null, "Item Deleted successfully");
        } else {
            JOptionPane.showMessageDialog(null, "please Select Item");

        }

        /*
        JFrame frame = new JFrame();

        System.out.println("Action Delete Item");
        frame.setTitle(" Delete Item");
        frame.setLayout(new FlowLayout());

        frame.add(new JLabel("         Comfirm Deleting Item         "));
        JButton confirmBtn = new JButton("Confirm");
        JButton cancelBtn = new JButton("Cancel");
        frame.add(confirmBtn);
        frame.add(cancelBtn);

        confirmBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        if (this.equals(confirmBtn)) {

        } else if (this.equals(cancelBtn)) {
        }

        frame.setSize(250, 100);
        frame.setLocation(100, 100);
        frame.setVisible(true);*/
    }

    /**
     * ************************************************
     */
    ArrayList<InvoiceHeader> invoiceHeadersList = new ArrayList<>();

    private void loadeFile() {
        System.out.println("Action Load File");
        frame.getInvHeaderTable().removeAll();
        frame.getInvLineTable().removeAll();
// header file reading
        JFileChooser fc = new JFileChooser();
        JOptionPane.showMessageDialog(null, "Please Select Invoice Header File");

        int results = fc.showOpenDialog(fc);

        if (results == JFileChooser.APPROVE_OPTION) {
            String path = fc.getSelectedFile().getPath();
            File file = new File(path);
            try {
                BufferedReader buffer = new BufferedReader(new FileReader(file));
                String fileHeadLines = null;
                while ((fileHeadLines = buffer.readLine()) != null) {
                    String[] dataInHeaderRow = fileHeadLines.split(",");

                    String invNumS = dataInHeaderRow[0];
                    String invDateS = dataInHeaderRow[1];
                    String customerName = dataInHeaderRow[2];

                    int invNum = Integer.parseInt(invNumS);

                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    Date invDate = df.parse(invDateS);

//              System.out.println("invoiceHeder=" + invHeder + "{ item Name= " + itmName + " Price= " + price + "Count= " + count+"}");
                    //                  System.out.println("inoice Header= { " + " Number" + invNumS + "InvoiceDate= " + invDateS + "Customer Name= " + customerName + "}\n");
                    double invHeaderTotal = 0;
                    InvoiceHeader invHeader = new InvoiceHeader(invNum, invDate, customerName, invHeaderTotal);
                    invHeaderTotal = invHeader.getInvoiceTotal();
                    //System.out.println("invHeaderTotal" + invHeaderTotal);

                    invoiceHeadersList.add(invHeader);
                    //System.out.println(invoiceHeadersList);

                }
                System.out.println("check");

                // open lines
                JOptionPane.showMessageDialog(null, "Please Select Item Lines File");

                results = fc.showOpenDialog(fc);
                if (results == JFileChooser.APPROVE_OPTION) {
                    path = fc.getSelectedFile().getPath();
                    File fileLine = new File(path);
                    BufferedReader bufferLine = new BufferedReader(new FileReader(fileLine));
                    String fileLines = null;
                    int prev = 0;

                    while ((fileLines = bufferLine.readLine()) != null) {
                        String[] dataInLineRow = fileLines.split(",");
                        String invNumS = dataInLineRow[0];
                        String itemName = dataInLineRow[1];
                        String itemPriceS = dataInLineRow[2];
                        String itemCountS = dataInLineRow[3];

                        int invNumber = Integer.valueOf(invNumS);
                        double itemPrice = Double.valueOf(itemPriceS);
                        int itemCount = Integer.valueOf(itemCountS);
                        InvoiceHeader header = getInvoiceHeaderById(invoiceHeadersList, invNumber);/*= findInvoice(invNumber);*/
                        if (prev != invNumber) {
                            prev = invNumber;
                            System.out.println(header);
                        }
                        InvoiceLine invLine = new InvoiceLine(header, itemName, itemPrice, itemCount);
                        header.getLines().add(invLine);
                        //        System.out.println(header);
                        System.out.println(invLine);
//                        System.out.println(" invNum" + invNumber + "{ Item Name= " + itemName + "Item Price= " + itemPrice + "Count= " + itemCount + "}\n");
                        invHeder = header;
                    }

                    frame.setInvoiceHeadersList(invoiceHeadersList);
                    for (InvoiceHeader invoice : invoiceHeadersList) {
                        //  System.out.println("--////--invHeaderTotal method check-///--");

                        double invHeaderTotal = invoice.getInvoiceTotal();
                        //  System.out.println("--/////--invHeaderTotal---" + invHeaderTotal);

                        int i = 0;
                        int columnCount = frame.getInvHeaderTable().getColumnCount();

                        frame.getInvHeaderTable().setValueAt(invHeaderTotal, i, columnCount - 1);
                        i++;
                        //  System.out.println("----invHeaderTotal---" + invHeaderTotal);

                    }
                }

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "File Not Found/n" + ex.getMessage() + JOptionPane.ERROR_MESSAGE);
            } catch (ParseException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Wrong Format /n" + ex.getMessage() + JOptionPane.ERROR_MESSAGE);

            } catch (IOException ex) {
                ex.printStackTrace();
                Logger.getLogger(ActionHandeler.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        //ArrayList<InvoiceLine> lines = frame.getInvoiceHeadersList().get(0).getLines();
        //System.out.println(lines);
    }

    private InvoiceHeader getInvoiceHeaderById(ArrayList<InvoiceHeader> invoices, int id) {
        for (InvoiceHeader invoice : invoices) {
            if (invoice.getNum() == id) {
                return invoice;

            }
        }

        return null;
    }

    private String dateFormater(String date) {

        DateTimeFormatter f = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z uuuu").withLocale(Locale.US);

        ZonedDateTime zdt = ZonedDateTime.parse(date, f);

        LocalDate ld = zdt.toLocalDate();
        DateTimeFormatter fLocalDate = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        String outputDate = ld.format(fLocalDate);

        return outputDate;

    }

    private int getLastInvoiceNumber(ArrayList<InvoiceHeader> invoices) {
        int lastInvNumber = 0;
        for (InvoiceHeader invoice : invoices) {
            if (invoice.getNum() > lastInvNumber) {
                lastInvNumber = invoice.getNum();
            }
        }

        return lastInvNumber;

    }

    /**
     * ****************************************
     */
    private void saveFile() {
        // JTable headerTable = new JTable(model);

        System.out.println("Action Save File");
        ArrayList<InvoiceHeader> invheadersTable = frame.getInvoiceHeadersList();
        //System.out.println(" header list before" + invheadersTable);
        JFileChooser fc = new JFileChooser();
        JOptionPane.showMessageDialog(null, "Please Select Invoices File or Create a new one");

        int resultsHeader = fc.showOpenDialog(fc);

        if (resultsHeader == JFileChooser.APPROVE_OPTION) {
            String path = fc.getSelectedFile().getPath();
            File file = new File(path);
            ArrayList<InvoiceHeader> headersTable = frame.getInvoiceHeadersList();
            System.out.println(headersTable);
            invHeaderTableModel headerModel = new invHeaderTableModel(headersTable);
            try {
                FileWriter fileWrite = new FileWriter(file);
                BufferedWriter buffer = new BufferedWriter(fileWrite);
                //System.out.println("..>.Column>..>" + headerModel.getColumnCount());
                //System.out.println("..>.>Row..>" + headerModel.getRowCount());

                for (int i = 0; i < headerModel.getRowCount(); i++) {

                    for (int j = 0; j < headerModel.getColumnCount() - 1; j++) {
                        if (j == 1) {
                            String dateLocal = headerModel.getValueAt(i, j).toString();
                            dateLocal = dateFormater(dateLocal);
                            buffer.write(dateLocal + ",");

                        } else {
                            buffer.write(String.valueOf(headerModel.getValueAt(i, j)) + ",");
                        }
                        //buffer.write(headerModel.getValueAt(i, j).toString() + ",");
                        //System.out.println("!!!!!!!!!!" + headerModel.getValueAt(i, j).toString());
                    }

                    buffer.newLine();
                }
                buffer.close();

                System.out.println("check save");
                //System.out.println(" header list after" + invheadersTable);

            } catch (IOException ex) {
            }
        }
        JOptionPane.showMessageDialog(null, "Please Select Items File or Create a new one");

        resultsHeader = fc.showOpenDialog(fc);
        if (resultsHeader == JFileChooser.APPROVE_OPTION) {

            String path2 = fc.getSelectedFile().getPath();
            File file2 = new File(path2);
            FileWriter fileWrite2 = null;
            BufferedWriter buffer2 = null;
            try {
                fileWrite2 = new FileWriter(file2);
                buffer2 = new BufferedWriter(fileWrite2);
                for (InvoiceHeader invoice : invheadersTable) {
                    int invNum = invoice.getNum();
                    ArrayList<InvoiceLine> linesTable = invoice.getLines();
                    System.out.println(" line table " + linesTable);

                    // linesTable = frame.getInvoiceLinesList();
                    invLineTableModel linesModel = new invLineTableModel(linesTable);
                    System.out.println("..>.>..>" + linesModel);

                    System.out.println("..>.Rows >..>" + linesModel.getRowCount());
                    System.out.println("..>.Columns>..>" + linesModel.getColumnCount());
                    for (int i = 0; i < linesModel.getRowCount(); i++) {
                        buffer2.write(invNum + ",");
                        for (int j = 0; j < linesModel.getColumnCount() - 1; j++) {

                            buffer2.write(linesModel.getValueAt(i, j).toString() + ",");
                            // System.out.println("in file >>>>>>." + invNum + "," + linesModel.getValueAt(i, j).toString() + ",");
                        }

                        buffer2.newLine();
                    }

                }
                buffer2.close();

                fileWrite2.close();

            } catch (IOException ex) {

            }

        }
    }

}
