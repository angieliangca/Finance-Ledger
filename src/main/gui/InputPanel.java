package gui;

import exceptions.DuplicateItemException;
import model.Item;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// Represents the input panel in which user can input data
public class InputPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private static final int WIDTH = 390;
    private static final int HEIGHT = 540;
    private static final int BORDER = 5;
    private static final String TYPE_TXT = "Type: ";
    private static final String DATE_TXT = "Date: ";
    private static final String PAYER_TXT = "Payer Name: ";
    private static final String PAYEE_TXT = "Payee Name: ";
    private static final String DESCRIPTION_TXT = "Description: ";
    private static final String CATEGORY_TXT = "Category: ";
    private static final String AMOUNT_TXT = "Amount: ";

    private JLabel typeLbl;
    private JLabel dateLbl;
    private JLabel payerLbl;
    private JLabel payeeLbl;
    private JLabel descriptionLbl;
    private JLabel categoryLbl;
    private JLabel amountLbl;

    private JRadioButton incomeBtn;
    private JRadioButton expenseBtn;
    private JSpinner dateSpinner;
    private JTextField entityTxt;
    private JTextField descriptionTxt;
    private JComboBox incomeCatList;
    private JComboBox expenseCatList;
    private JFormattedTextField amountField;
    private JButton addButton;
    private JButton loadData;
    private JButton saveButton;

    private String type;
    private String date;
    private String entity;
    private String description;
    private String category;
    private Double amount;

    private OutputPanel output;

    // EFFECTS: constructs input panel
    public InputPanel(OutputPanel output) {
        this.output = output;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBorder(new EmptyBorder(BORDER, BORDER, BORDER, BORDER));
        setLayout(new BorderLayout());

        initDate();
        initInputPane();
        initControlPane();
        addListeners();
    }

    // EFFECTS: initializes input pane in which user can add an item
    private void initInputPane() {
        JPanel inputPane = new JPanel(new BorderLayout());
        inputPane.setBorder(BorderFactory.createTitledBorder("Input"));
        add(inputPane, BorderLayout.CENTER);

        initLabels(inputPane);
        initFields(inputPane);
    }

    // EFFECTS: initializes labels in input pane
    private void initLabels(JPanel inputPane) {
        JPanel labelPane = new JPanel(new GridBagLayout());
        labelPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;
        c.weighty = 1;

        createLabels();

        c.gridy = 1;
        labelPane.add(typeLbl, c);
        c.gridy = 2;
        labelPane.add(dateLbl, c);
        c.gridy = 3;
        labelPane.add(payerLbl, c);
        labelPane.add(payeeLbl, c);
        payeeLbl.setVisible(false);
        c.gridy = 4;
        labelPane.add(descriptionLbl, c);
        c.gridy = 5;
        labelPane.add(categoryLbl, c);
        c.gridy = 6;
        labelPane.add(amountLbl, c);

        inputPane.add(labelPane, BorderLayout.WEST);
    }

    // EFFECTS: create labels
    private void createLabels() {
        typeLbl = new JLabel(TYPE_TXT);
        dateLbl = new JLabel(DATE_TXT);
        payerLbl = new JLabel(PAYER_TXT);
        payeeLbl = new JLabel(PAYEE_TXT);
        descriptionLbl = new JLabel(DESCRIPTION_TXT);
        categoryLbl = new JLabel(CATEGORY_TXT);
        amountLbl = new JLabel(AMOUNT_TXT);
    }

    // EFFECTS: initializes fields in input pane
    private void initFields(JPanel inputPane) {
        JPanel fieldPane = new JPanel(new GridBagLayout());
        fieldPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagConstraints c = initGridBag();

        JPanel typePane = setupType();
        entityTxt = new JTextField(12);
        descriptionTxt = new JTextField(12);
        setupCategory();
        setupAmount();

        c.gridy = 1;
        fieldPane.add(typePane, c);
        c.gridy = 2;
        fieldPane.add(dateSpinner, c);
        c.gridy = 3;
        fieldPane.add(entityTxt, c);
        c.gridy = 4;
        fieldPane.add(descriptionTxt, c);
        c.gridy = 5;
        fieldPane.add(incomeCatList, c);
        fieldPane.add(expenseCatList, c);
        expenseCatList.setVisible(false);
        c.gridy = 6;
        fieldPane.add(amountField, c);
        inputPane.add(fieldPane, BorderLayout.EAST);
    }

    // EFFECTS: initializes grid bag constraints
    private GridBagConstraints initGridBag() {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 1;
        return c;
    }

    // EFFECTS: setups type radio buttons
    private JPanel setupType() {
        JPanel typePane = new JPanel();
        incomeBtn = new JRadioButton("Income");
        expenseBtn = new JRadioButton("Expense");
        typePane.add(incomeBtn);
        typePane.add(expenseBtn);
        return typePane;
    }

    // EFFECTS: setups category combo box based on the type selected
    private void setupCategory() {
        String[] incomeList = {"Salary", "Investment"};
        incomeCatList = new JComboBox(incomeList);

        String[] expenseList = {"Housing", "Transportation", "Food", "Clothing",
                "Utilities", "Entertainment", "Medical", "Miscellaneous"};
        expenseCatList = new JComboBox(expenseList);
    }

    // EFFECTS: setups amount fields
    private void setupAmount() {
        NumberFormat amountFormat = NumberFormat.getNumberInstance();
        amountField = new JFormattedTextField(amountFormat);
        amountField.setColumns(12);
        amountField.setValue(0.00);
        amountField.addPropertyChangeListener("value", (PropertyChangeListener) this);
    }

    // EFFECTS: initializes control pane in which user can add an item
    private void initControlPane() {
        JPanel buttonPane = new JPanel(new FlowLayout());
        buttonPane.setBorder(new EmptyBorder(BORDER, BORDER, BORDER, BORDER));
        loadData = new JButton("Load Ledger");
        addButton = new JButton("Add Item");
        saveButton = new JButton("Save Ledger");
        buttonPane.add(loadData);
        buttonPane.add(addButton);
        buttonPane.add(saveButton);
        loadData.setActionCommand("load");
        loadData.addActionListener(this);
        addButton.setActionCommand("add");
        addButton.addActionListener(this);
        saveButton.setActionCommand("save");
        saveButton.addActionListener(this);
        add(buttonPane, BorderLayout.SOUTH);
    }

    // EFFECTS: adds listeners for input panel
    private void addListeners() {
        ButtonGroup group = new ButtonGroup();
        group.add(incomeBtn);
        group.add(expenseBtn);
        incomeBtn.addActionListener(this);
        incomeBtn.setSelected(true);
        expenseBtn.addActionListener(this);
        incomeCatList.addActionListener(this);
        entityTxt.addActionListener(this);
        descriptionTxt.addActionListener(this);
    }

    // EFFECTS: initializes dates in input pane
    private void initDate() {
        Calendar calendar = Calendar.getInstance();
        Date initDate = calendar.getTime();
        calendar.add(Calendar.YEAR, -100);
        Date earliestDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 200);
        Date latestDate = calendar.getTime();
        SpinnerDateModel dateModel = new SpinnerDateModel(initDate,
                earliestDate,
                latestDate,
                Calendar.YEAR);
        dateSpinner = new JSpinner(dateModel);
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "MM/dd/yyyy"));
    }

    // EFFECTS: formats dates in input pane
    private String formatDate() {
        Date inputDate = (Date) dateSpinner.getValue();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        return dateFormat.format(inputDate);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (incomeBtn.isSelected()) {
            type = "income";
            category = (String) incomeCatList.getSelectedItem();
            enableIncome();
        } else if (expenseBtn.isSelected()) {
            type = "expense";
            category = (String) expenseCatList.getSelectedItem();
            enableExpense();
        }
        date = formatDate();
        entity = entityTxt.getText();
        description = descriptionTxt.getText();

        if (e.getActionCommand().equals("load")) {
            new FileChooser();
        }

        if (e.getActionCommand().equals("add")) {
            addAnItem(type, date, entity, description, category, amount);
        }

        if (e.getActionCommand().equals("save")) {
            new FileSaver();
        }
    }

    // EFFECTS: adds an item
    private void addAnItem(String type, String date, String entity,
                           String description, String category, double amount) {
        try {
            LedgerApp.myLedger.addItem(type, date, entity, description, category, amount);
            if (!output.getReviewButton().isEnabled()) {
                updateDataList();
            }
        } catch (DuplicateItemException exp) {
            JOptionPane.showMessageDialog(new JFrame("Message"), "This item already exist.",
                    "Duplicate Item", JOptionPane.PLAIN_MESSAGE);
        }
    }

    // EFFECTS: updates item lists in real time
    private void updateDataList() {
        Item[] data = LedgerApp.myLedger.getMyLedger().toArray(new Item[0]);
        for (int i = 0; i < LedgerApp.myLedger.getMyLedger().size(); i++) {
            data[i] = (LedgerApp.myLedger.getMyLedger()).get(i);
        }
        output.getDataList().setListData(data);
    }

    // EFFECTS: enables income category combo box and payer label
    private void enableIncome() {
        incomeCatList.setVisible(true);
        expenseCatList.setVisible(false);
        payeeLbl.setVisible(false);
        payerLbl.setVisible(true);
    }

    // EFFECTS: enables expense category combo box and payee label
    private void enableExpense() {
        incomeCatList.setVisible(false);
        expenseCatList.setVisible(true);
        payeeLbl.setVisible(true);
        payerLbl.setVisible(false);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object source = evt.getSource();
        if (source == amountField) {
            amount = ((Number)amountField.getValue()).doubleValue();
            long temp = Math.round(amount * (long) Math.pow(10, 2));
            amount = (double) temp / (long) Math.pow(10, 2);
            if (amount < 0) {
                amountField.setValue(-amount);
            }
        }
    }

}
