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
import java.util.ArrayList;
import java.util.Date;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
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
        frame.setNumberLable(numLable);
        //System.out.println("-----" + numLable.getText());
        //
        JLabel custLable = frame.getCustomerLable();
        frame.getInvoiceHeadersList().get(selectedRow).getCustomer();
        custLable.setText(String.valueOf(frame.getInvoiceHeadersList().get(selectedRow).getCustomer()));
        frame.setCustomerLable(custLable);
        //System.out.println("-----" + custLable.getText());
        //

        JLabel dateLabel = frame.getDateLable();
        frame.getInvoiceHeadersList().get(selectedRow).getDate();
        dateLabel.setText(String.valueOf(frame.getInvoiceHeadersList().get(selectedRow).getDate()));
        frame.setDateLable(dateLabel);
        // System.out.println("-----" + dateLabel.getText());
        //
        JLabel totalLable = frame.getTotalLabel();
        frame.getInvoiceHeadersList().get(selectedRow).getInvoiceTotal();
        totalLable.setText(String.valueOf(frame.getInvoiceHeadersList().get(selectedRow).getInvoiceTotal()));
        frame.setTotalLabel(totalLable);
        // System.out.println("-----" + totalLable.getText());

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

        JTextField invDate = new JTextField(20);
        JTextField customer = new JTextField(20);
        JButton okBtn = new JButton("OK");
        okBtn.setActionCommand("okInv");
        okBtn.addActionListener(this);
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setActionCommand("cancelInv");
        cancelBtn.addActionListener(this);

        frame1.setLayout(new FlowLayout());
        frame1.add(new JLabel("Invoce Date"));
        frame1.add(invDate); //textfield
        frame1.add(new JLabel("Customer"));
        frame1.add(customer);//textfield
        frame1.add(okBtn);
        frame1.add(cancelBtn);
//parameters
        int selectedRow = frame.getInvHeaderTable().getSelectedRow();
        System.out.println(selectedRow);
        okBtn.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                // ArrayList<InvoiceHeader> invoiceHeadersList = new ArrayList<>();

                System.out.println("action from Ok Btn");
                invoiceCustomer = customer.getText();
                try {
                    invoiceDate = new SimpleDateFormat("dd-MM-yyyy").parse(invDate.getText());
                } catch (ParseException ex) {
                }
                //invoiceNum = invoiceNum+(invHeder.getNum());
                //         System.out.println("inv num +++  "+ invoiceNum);
//display on table
                invHeder = new InvoiceHeader(invoiceNum, invoiceCustomer, invoiceDate);
                invoiceHeadersList.add(invHeder);
                frame.setInvoiceHeadersList(invoiceHeadersList);

                //System.out.println("customer=  " + invoiceCustomer + "   Date= " + invoiceDate);
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
            }
        });
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(350, 150);
        frame1.setLocation(100, 100);
        frame1.setVisible(true);
    }

    /**
     * ****************************************
     */
    private void deleteInvoice() {

        System.out.println("Action Delete Invoice");

        /* ListSelectionModel tableModel=  frame.getInvHeaderTable().getSelectionModel();
        System.out.println("/////"+tableModel);
tableModel.addListSelectionListener(this); */
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
                itmName = itemName.getText();
                price = Integer.valueOf(ItemPrice.getText());
                count = Integer.valueOf(itemCount.getText());

                System.out.println("invoiceHeder=" + invHeder + "{ item Name= " + itmName + " Price= " + price + "Count= " + count + "}");

                InvoiceLine invLine = new InvoiceLine(invHeder, itmName, price, count);
                int selectedRow = frame.getInvHeaderTable().getSelectedRow();
                System.out.println(selectedRow);
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

                // System.out.println("-----" + totalLable.getText());
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

            }
        });

        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

// header file reading
        JFileChooser fc = new JFileChooser();
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
                    InvoiceHeader invHeader = new InvoiceHeader(invNum, customerName, invDate);
                    invoiceHeadersList.add(invHeader);
                    //System.out.println(invoiceHeadersList);

                }
                System.out.println("check");

                // open lines
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
                        String itemCountS = dataInLineRow[2];

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
                        System.out.println( invLine );
//                        System.out.println(" invNum" + invNumber + "{ Item Name= " + itemName + "Item Price= " + itemPrice + "Count= " + itemCount + "}\n");
                        invHeder = header;

                    }
                    frame.setInvoiceHeadersList(invoiceHeadersList);
                    //System.out.println(invoiceHeadersList);

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

    /**
     * ****************************************
     */
    private void saveFile() {
        // JTable headerTable = new JTable(model);

        System.out.println("Action Save File");
        JFileChooser fc = new JFileChooser();
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
                    for (int j = 0; j < headerModel.getColumnCount(); j++) {
                        buffer.write(headerModel.getValueAt(i, j).toString() + ",");
                        //System.out.println("!!!!!!!!!!" + headerModel.getValueAt(i, j).toString());
                    }

                    buffer.newLine();
                }
                buffer.close();

                System.out.println("check save");
            } catch (IOException ex) {
            }
        }
        resultsHeader = fc.showOpenDialog(fc);
        if (resultsHeader == JFileChooser.APPROVE_OPTION) {
            String path2 = fc.getSelectedFile().getPath();
            File file2 = new File(path2);
            System.out.println(invoiceLinesList);
            invLineTableModel linesModel = new invLineTableModel(invoiceLinesList);
            //System.out.println("..>.>..>" + linesModel);
            try {
                FileWriter fileWrite2 = new FileWriter(file2);
                BufferedWriter buffer2 = new BufferedWriter(fileWrite2);
                for (int i = 0; i < linesModel.getRowCount(); i++) {
                    for (int j = 0; j < linesModel.getColumnCount(); j++) {
                        buffer2.write(linesModel.getValueAt(i, j).toString() + ",");
                    }

                    buffer2.newLine();
                }
                buffer2.close();

                fileWrite2.close();
            } catch (IOException ex) {
            }
        }
    }
}
