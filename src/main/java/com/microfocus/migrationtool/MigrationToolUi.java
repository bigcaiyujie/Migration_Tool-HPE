package com.microfocus.migrationtool;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Date;

public class MigrationToolUi extends JFrame {
    public int currentProgressStatus = 0;
    JPanel inputPanel;
    JLabel ipaddressLabel;
    JTextField ipaddressText;
    JLabel pswLabel;
    JPasswordField pswText;
    JLabel portLabel;
    JTextField portText;
    JLabel userLabel;
    JTextField userText;
    JLabel protocolLabel;
    ButtonGroup bg;
    JButton clearData;
    JButton submitData;
    JButton exportCsv;
    JRadioButton jr1;
    JRadioButton jr2;
    JProgressBar progressBar;
    JPanel currentPanel;
    JLabel udfLabel;
    JLabel udfValue;
    JLabel udiLabel;
    JLabel udiValue;
    JPanel summaryPanel;
    JLabel typeTitle;
    JLabel points;
    JLabel serverFullLabel;
    JLabel serverFullValue;
    JLabel serverFullPointValue;
    JLabel serverBasicLabel;
    JLabel serverBasicValue;
    JLabel serverBasicPointValue;
    JLabel allWorkstationLabel;
    JLabel allWorkstationValue;
    JLabel storageLabel;
    JLabel storageValue;
    JLabel storagePointValue;
    JLabel allWorkstationPointValue;
    JLabel networkLabel;
    JLabel networkValue;
    JLabel networkPointValue;
    JLabel dockerLabel;
    JLabel dockerValue;
    JLabel dockerPointValue;
    JPanel allPanel;
    JLabel migrationLabel;
    JLabel totalunitsLabel;
    JLabel numbers;
    JPanel outputPanel;
    JLabel mdrLabel;
    JLabel mdrValue;
    JLabel totalLabel;
    JLabel totalValue;
    JLabel totalPointValue;
    JLabel migrationValue;
    JLabel totalunitsValue;
    JLabel vmLabel;
    JLabel vmValue;
    JLabel vmPointValue;
    JTable jTable;
    JLabel compliancyLabel;
    JLabel compliancyValue;
    JLabel opsbWorkstationLabel;
    JLabel opsbWorkstationValue;
    JLabel opsbWorkstationPointValue;
    JLabel fbWorkstationLabel;
    JLabel fbWorkstationValue;
    JLabel fbWorkstationPointValue;
    JLabel unitCapacityLabel;
    JLabel unitCapacityValue;
    JLabel tipLabel;
    JLabel basicWorkstationLabel;
    JLabel basicWorkstationValue;
    JLabel basicWorkstationPointValue;
    JLabel serverOpsb;
    JLabel serverOpsbValue;
    JLabel serverOpsbPointValue;
    StringBuffer allData;
    String validationCode;
    int height = 30;
    int width = 180;
    int border = 80;
    int padding = 300;
    int initX = 40;
    int initY = 30;
    int borderX = 150;
    int borderY = 60;
    int padding1 = 260;
    int borderX1 = 260;
    int widthF = 250;
    int borderY1 = 30;
    boolean ucmdbVersion;
    static String[] HEADERS = {"UCMB 10.33", "Units", "UDF", "UDI", "MDR", "Server Full", "Server Basic", "VM", "Workstation", "Network", "Storage", "Container"};
    final static String[][] DATA = new String[][]{
            {"Capacity", "", "", "", "", "", "", "", "", "", "", ""},
            {"Discovered", "", "", "", "", "", "", "", "", "", "", ""},
            {"Migration Units", "", "", "", "", "", "", "", "", "", "", ""},
            {"Compliancy Units", "", "", "", "", "", "", "", "", "", "", ""},
            {"Total Units", "", "", "", "", "", "", "", "", "", "", ""},
            {"Validation Code", "", "", "", "", "", "", "", "", "", "", ""}
    };

    public MigrationToolUi() {
        this.setTitle("UCMDB 10.4x License Migration Calculator for Universal Discovery");
        this.setSize(685, 315);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);
        this.setResizable(false);

        /*
        inputPanel
         */
        inputPanel = createJPanel(0, 0, 680, 280, Color.white, null);
        inputPanel.setBorder(BorderFactory.createTitledBorder("UCMDB Server Connection"));
        ipaddressLabel = createJLabel("IP Address:", initX, initY, width, height);
        inputPanel.add(ipaddressLabel);

        ipaddressText = createJTextField("", initX + border, initY, width, height);
        inputPanel.add(ipaddressText);

        pswLabel = createJLabel("Password:", initX + padding, initY + border, width, height);
        inputPanel.add(pswLabel);

        pswText = new JPasswordField("");
        pswText.setBounds(initX + padding + border, initY + border, width, height);
        inputPanel.add(pswText);

        portLabel = createJLabel("Port:", initX + padding, initY, width, height);
        inputPanel.add(portLabel);

        portText = createJTextField("", initX + padding + border, initY, width, height);
        inputPanel.add(portText);

        userLabel = createJLabel("User:", initX, initY + border, width, height);
        inputPanel.add(userLabel);


        userText = createJTextField("", initX + border, initY + border, width, height);
        inputPanel.add(userText);

        protocolLabel = createJLabel("Protocol:", initX, initY + border * 2, width, height);
        inputPanel.add(protocolLabel);

        bg = new ButtonGroup();
        jr1 = createJRadioButton("HTTP", initX + border, initY + border * 2, 100, height);
        jr2 = createJRadioButton("HTTPS", initX + border + 100, initY + border * 2, 100, height);
        jr1.setBackground(Color.white);
        jr2.setBackground(Color.white);
        bg.add(jr1);
        bg.add(jr2);
        inputPanel.add(jr1);
        inputPanel.add(jr2);

        exportCsv = createJButton("Export.CSV",initX + padding + 120, initY + border * 2 , 90, height, false);
        inputPanel.add(exportCsv);
        exportCsv.setEnabled(false);

        clearData = createJButton("Reset",initX + padding + border + 150, initY + border * 2 , 90, height, true);
        inputPanel.add(clearData);

        submitData = createJButton("Calculate",initX + padding + 10, initY + border * 2 , 90, height, true);
        inputPanel.add(submitData);

        progressBar = new JProgressBar();
        progressBar.setBounds(0, initY + border * 3 - 10, 700, 20);
        progressBar.setForeground(SystemColor.inactiveCaption);
        progressBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        progressBar.setOrientation(JProgressBar.HORIZONTAL);
        progressBar.setBorderPainted(true);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        inputPanel.add(progressBar);
        this.add(inputPanel);
         /*
        onClickListener
         */
        ActionListener exprotListener = new ExportListener();
        exportCsv.addActionListener(exprotListener);

        ActionListener clearDataListener = new ClearDatatListener();
        clearData.addActionListener(clearDataListener);

        ActionListener submitDataListener = new SubmitDataListener(this);
        submitData.addActionListener(submitDataListener);

        this.setVisible(true);
    }

    private JButton createJButton(String title, int x, int y, int width, int height, boolean enable) {
        JButton button = new JButton(title);
        button.setBounds(x, y, width, height);
        button.setEnabled(enable);
        return button;
    }

    private JRadioButton createJRadioButton(String title, int x, int y, int width, int height) {
        JRadioButton button = new JRadioButton(title);
        button.setBounds(x, y, width, height);
        return button;
    }

    private JTextField createJTextField(String title, int x, int y, int width, int height) {
        JTextField textField = new JTextField(title);
        textField.setBounds(x, y, width, height);
        return textField;
    }

    private JLabel createJLabel(String title, int x, int y, int width, int height) {
        JLabel label = new JLabel(title);
        label.setBounds(x, y, width, height);
        return label;
    }

    private JPanel createJPanel(int x, int y, int width, int height, Color color, LayoutManager layout) {
        JPanel jPanel = new JPanel();
        jPanel.setBounds(x, y, width, height);
        jPanel.setBackground(color);
        jPanel.setLayout(layout);
        return jPanel;
    }


    class ExportListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            chooser.setDialogTitle("select filePath");
            chooser.setFileSelectionMode(0);
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int state = chooser.showOpenDialog(null);
            String filePath;
            if (state == 1) {
                return;
            } else {
                filePath = chooser.getSelectedFile().getAbsolutePath();
            }
            Date date = new Date();
            String fileName = date.getTime() + "_license_report";
            File file = new File(filePath + "/" + fileName + ".csv");
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            try {
                file.createNewFile();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));

                for (String title : HEADERS) {
                    outputStreamWriter.write(title);
                    outputStreamWriter.write(",");
                }
                outputStreamWriter.write("\r\n");
                outputStreamWriter.write(allData.toString());
                outputStreamWriter.write("Hash");
                outputStreamWriter.write(",");
                outputStreamWriter.write(validationCode);
                for (int z = 0; z < 10; z++) {
                    outputStreamWriter.write(",");
                }
                outputStreamWriter.flush();
                outputStreamWriter.close();
                JOptionPane.showMessageDialog(null, "Export Compeleted !");
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    class ClearDatatListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setSize(685, 315);
            ipaddressText.setText("");
            userText.setText("");
            pswText.setText("");
            portText.setText("");
            bg.clearSelection();
            progressBar.setValue(0);
            if (outputPanel == null) {
                return;
            }
            exportCsv.setEnabled(false);
            clearData.setEnabled(true);
            submitData.setEnabled(true);
            outputPanel.setVisible(false);

        }
    }

    class SubmitDataListener implements ActionListener {
        private MigrationToolUi ui;

        public SubmitDataListener(MigrationToolUi ui) {
            this.ui = ui;
        }

        public void actionPerformed(ActionEvent e) {
            setSize(685, 315);
            if (outputPanel != null) {
                outputPanel.setVisible(false);
            }
            exportCsv.setEnabled(false);
            clearData.setEnabled(false);
            submitData.setEnabled(false);
            progressBar.setValue(0);
            CalulateLicense instance = new CalulateLicense(ui);
            instance.start();
            UpdateProgressBarValue instance2 = new UpdateProgressBarValue();
            instance2.start();
        }
    }

    public void setProgressBarValue(int value) {
        int currentValue = progressBar.getValue();
        if (value == 100 || value == 0) {
            progressBar.setValue(value);
            return;
        }
        if (currentValue < value) {
            for (int i = currentValue; i <= value; i++) {
                if (checkDirectStatus()) return;
                progressBar.setValue(i);
                threadSleep(200);
                if (value < currentProgressStatus) {
                    progressBar.setValue(value);
                    return;
                }
            }
        } else if (currentValue > value) {
            progressBar.setValue(value);
        }

    }

    private boolean checkDirectStatus() {
        if (currentProgressStatus == 100 || currentProgressStatus == 0) {
            progressBar.setValue(currentProgressStatus);
            return true;
        }
        return false;
    }

    private void threadSleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class UpdateProgressBarValue extends Thread {
        int perviousStatus = 0;

        public void run() {
            while (true) {
                if (perviousStatus != currentProgressStatus) {
                    perviousStatus = currentProgressStatus;
                    setProgressBarValue(currentProgressStatus);
                } else {
                    threadSleep(200);
                }
                if (progressBar.getValue() == 100) {
                    break;
                }
            }
        }
    }

    class CalulateLicense extends Thread {
        JFrame jFrame;

        CalulateLicense(JFrame jFrame) {
            this.jFrame = jFrame;
        }

        public void run() {
            String ip = ipaddressText.getText().trim();
            String user = userText.getText().trim();
            String psw = pswText.getText().trim();
            String portS = portText.getText().trim();
            String protocal = "";
            allData = new StringBuffer();
            if (jr1.isSelected()) {
                protocal = "HTTP";
            } else if (jr2.isSelected()) {
                protocal = "HTTPS";
            }
            if (ip.isEmpty() || user.isEmpty() || psw.isEmpty() || portS.isEmpty() || protocal.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Input data cannot be empty!");
                clearData.setEnabled(true);
                submitData.setEnabled(true);
                currentProgressStatus = 100;
            } else {
                currentProgressStatus = 0;
                threadSleep(800);
                currentProgressStatus = 20;
                try {
                    GetAllLicense getAllLicense = new GetAllLicense(ip, protocal, user, psw, Integer.valueOf(portS));
                    currentProgressStatus = 60;
                    getAllLicense.init();
                    currentProgressStatus = 90;
                    ucmdbVersion = getAllLicense.getVersion();
                    jTable = new JTable(DATA, HEADERS);
                    if (getAllLicense.getVersion()) {
                        ResultLarge resultLarge = getAllLicense.getLicense();
                        moreThanPanel(resultLarge);
                        this.jFrame.add(outputPanel);
                        if (resultLarge.getServerOpsb() != 0 || resultLarge.getWorkstationOfOpbs() != 0){
                            this.jFrame.setSize(685, 850);
                        }else{
                            this.jFrame.setSize(685, 790);
                        }

                        outputPanel.setVisible(true);
                        outputPanel.setBorder(BorderFactory.createTitledBorder("Current Version UCMDB " + resultLarge.getVersion()));
                        JOptionPane.showMessageDialog(null, "Completetd !");
                    } else {
                        ResultLess resultLess = getAllLicense.getLicense();
                        if (resultLess.getUdi() < resultLess.getUsedUdi() || resultLess.getUdf() < resultLess.getUsedUdf() || resultLess.getMdr() < resultLess.getUsedMdr()) {
                            JOptionPane.showMessageDialog(null, "<html><body>Migration to UCMDB 10.40 and unit-based licensing is not possible when your current licensing <br></br>is not compliant. </body></html>", "Warning", JOptionPane.WARNING_MESSAGE);
                            notEnoughPanel(resultLess);
                            this.jFrame.add(outputPanel, null);
                            this.jFrame.setSize(685, 480);
                            outputPanel.setVisible(true);
                            outputPanel.setBorder(BorderFactory.createTitledBorder("Current Version UCMDB " + resultLess.getVersion()));
                        } else {
                            lessThanPanel(resultLess);
                            this.jFrame.add(outputPanel, null);
                            currentProgressStatus = 100;
                            JOptionPane.showMessageDialog(null, "Completetd !");
                            exportCsv.setEnabled(true);
                            this.jFrame.setSize(685, 790);
                            outputPanel.setVisible(true);
                            outputPanel.setBorder(BorderFactory.createTitledBorder("Current Version UCMDB " + resultLess.getVersion()));
                        }
                    }
                    currentProgressStatus = 100;
                    clearData.setEnabled(true);
                    submitData.setEnabled(true);

                } catch (Exception x) {
                    currentProgressStatus = 100;
                    x.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Connect faild !");
                    clearData.setEnabled(true);
                    submitData.setEnabled(true);
                }
            }

        }
    }

    public int getPoint(int number, double rate) {
        BigDecimal tempDecimal = new BigDecimal(number);
        double basicServerPoint = tempDecimal.multiply(new BigDecimal(rate)).setScale(1, RoundingMode.HALF_UP).doubleValue();
        return (int) Math.ceil(basicServerPoint);
    }

    public double getPointMore(int number, double rate) {
        BigDecimal tempDecimal = new BigDecimal(number);
        double basicServerPoint = tempDecimal.multiply(new BigDecimal(rate)).setScale(1, RoundingMode.HALF_UP).doubleValue();
        return basicServerPoint;
    }

    public void notEnoughPanel(ResultLess resultLess) {

        outputPanel = createJPanel(0, 280, 680, 165, Color.white, null);
        initX = 10;
        initY = 20;
        padding = 370;
        width = 330;
        borderY = 40;
        currentPanel = createJPanel(initX, initY, 660, 120, Color.white, null);
        currentPanel.setBorder(BorderFactory.createTitledBorder("Current License Usage Status :"));
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        String usage;
        if (resultLess.getUdi() == 0) {
            if (resultLess.getUsedUdi() == 0) {
                udfLabel = createJLabel("Universal Discovery - Inventory only: " + resultLess.getUsedUdi() + "/" + resultLess.getUdi() + " - Usage: 0 %", initX, initY, 350, height);
                udfLabel.setToolTipText("Universal Discovery - Inventory only: " + resultLess.getUsedUdi() + "/" + resultLess.getUdi() + " - Usage: 0 %");
            } else {
                udfLabel = createJLabel("Universal Discovery - Inventory only: " + resultLess.getUsedUdi() + "/" + resultLess.getUdi() + " - Usage: Exceeded", initX, initY, 350, height);
                udfLabel.setToolTipText("Universal Discovery - Inventory only: " + resultLess.getUsedUdi() + "/" + resultLess.getUdi() + " - Usage: Exceeded");
                udfLabel.setForeground(Color.red);
            }

        } else {
            usage = numberFormat.format((float) resultLess.getUsedUdi() / (float) resultLess.getUdi() * 100);
            udfLabel = createJLabel("Universal Discovery - Inventory only: " + resultLess.getUsedUdi() + "/" + resultLess.getUdi() + " - Usage: " + usage + " %", initX, initY, width, height);
            udfLabel.setToolTipText("Universal Discovery - Inventory only: " + resultLess.getUsedUdi() + "/" + resultLess.getUdi() + " - Usage: " + usage + " %");
            if (resultLess.getUsedUdi() >  resultLess.getUdi()) {
                udfLabel.setForeground(Color.red);
            }
        }


        if (resultLess.getUdf() == 0) {
            if (resultLess.getUsedUdf() == 0) {
                udiLabel = createJLabel("Universal Discovery - Full: " + resultLess.getUsedUdf() + "/" + resultLess.getUdf() + " - Usage: 0 %", initX + padding, initY, width, height);

            } else {
                udiLabel = createJLabel("Universal Discovery - Full: " + resultLess.getUsedUdf() + "/" + resultLess.getUdf() + " - Usage: Exceeded", initX + padding, initY, width, height);
                udiLabel.setForeground(Color.red);
            }
        } else {
            usage = numberFormat.format((float) resultLess.getUsedUdf() / (float) resultLess.getUdf() * 100);
            udiLabel = createJLabel("Universal Discovery - Full: " + resultLess.getUsedUdf() + "/" + resultLess.getUdf() + " - Usage: " + usage + " %", initX + padding, initY, width, height);
            udiLabel.setToolTipText("Universal Discovery - Full: " + resultLess.getUsedUdf() + "/" + resultLess.getUdf() + " - Usage: " + usage + " %");
            if (resultLess.getUsedUdf() > resultLess.getUdf()) {
                udiLabel.setForeground(Color.red);
            }
        }

        if (resultLess.getMdr() == 0) {
            if (resultLess.getUsedMdr() == 0) {
                mdrLabel = createJLabel("Available 3rd patry integrations: " + resultLess.getUsedMdr() + "/" + resultLess.getMdr() + " - Usage: 0 %", initX, initY + borderY, width, height);
            } else {
                mdrLabel = createJLabel("Available 3rd patry integrations: " + resultLess.getUsedMdr() + "/" + resultLess.getMdr() + " - Usage: Exceeded", initX, initY + borderY, width, height);
                mdrLabel.setForeground(Color.red);
            }
        } else {
            usage = numberFormat.format((float) resultLess.getUsedMdr() / (float) resultLess.getMdr() * 100);
            mdrLabel = createJLabel("Available 3rd patry integrations: " + resultLess.getUsedMdr() + "/" + resultLess.getMdr() + " - Usage: " + usage + " %", initX, initY + borderY, width, height);
            mdrLabel.setToolTipText("Available 3rd patry integrations: " + resultLess.getUsedMdr() + "/" + resultLess.getMdr() + " - Usage: " + usage + " %");
            if (resultLess.getUsedMdr() > resultLess.getMdr()) {
                mdrLabel.setForeground(Color.red);
            }
        }


        currentPanel.add(udfLabel);
        currentPanel.add(udiLabel);
        currentPanel.add(mdrLabel);
        outputPanel.add(currentPanel);
    }

    public void moreThanPanel(ResultLarge resultLarge) {

        outputPanel = createJPanel(0, 280, 680, 475, Color.white, null);
        initX = 20;
        initY = 20;
        padding = 320;
        height = 30;
        width = 200;

         /*
        Current license status
         */

        currentPanel = createJPanel(20, initY, 640, 60, Color.white, null);
        currentPanel.setBorder(BorderFactory.createTitledBorder("Current License Capacity :"));

        unitCapacityLabel = createJLabel("Unit  Capacity :", initX, initY, width, height);
        currentPanel.add(unitCapacityLabel);

        unitCapacityValue = createJLabel(resultLarge.getUnitCapacity() + "", initX + 100, initY, width, height);
        currentPanel.add(unitCapacityValue);


        mdrLabel = createJLabel("MDR  Capacity :", initX + borderX + padding, initY, width, height);
        currentPanel.add(mdrLabel);

        mdrValue = createJLabel(resultLarge.getMdr() + "", initX + borderX + padding + 100, initY, width, height);
        currentPanel.add(mdrValue);

        outputPanel.add(currentPanel);

        /*
        After upgrade
         */

        summaryPanel = createJPanel(20, initY + 60, 640, 380, Color.white, null);
        summaryPanel.setBorder(BorderFactory.createTitledBorder("License Unit Detail :"));

        int widthF = 250;
        int borderY1 = 30;
        initY = 50;

        typeTitle = createJLabel("License Types", initX, 20, widthF, height);
        summaryPanel.add(typeTitle);

        numbers = createJLabel("CI Counts", initX + borderX1, 20, widthF, height);
        summaryPanel.add(numbers);

        points = createJLabel("Units ", initX + borderX + padding, 20, widthF, height);
        summaryPanel.add(points);

        /*
        serverFull
         */

        serverFullLabel = createJLabel("Servers with Advanced License :", initX, initY, widthF, height);
        summaryPanel.add(serverFullLabel);

        serverFullValue = createJLabel(resultLarge.getServerOfFull() + "", initX + borderX1, initY, width, height);
        summaryPanel.add(serverFullValue);

        serverFullPointValue = createJLabel(resultLarge.getServerOfFull() + "", initX + padding + borderX, initY, width, height);
        serverFullPointValue.setToolTipText("10/10  unit for every advanced Server discovered");
        summaryPanel.add(serverFullPointValue);

        /*
        server Basic
         */

        serverBasicLabel = createJLabel("Servers with Basic License :", initX, initY + borderY1, widthF, height);
        summaryPanel.add(serverBasicLabel);

        serverBasicValue = createJLabel(resultLarge.getServerBasic() + "", initX + borderX1, initY + borderY1, width, height);
        summaryPanel.add(serverBasicValue);

        serverBasicPointValue = createJLabel(getPointMore(resultLarge.getServerBasic(), 0.2) + "", initX + padding + borderX, initY + borderY1, width, height);
        serverBasicPointValue.setToolTipText("2/10  unit for every Basic Server discovered");
        summaryPanel.add(serverBasicPointValue);

        if (resultLarge.getServerOpsb() != 0 || resultLarge.getWorkstationOfOpbs() != 0) {
         /*
        Opsb
         */
            serverOpsb = createJLabel("Servers with Operational License :", initX, initY + borderY1 * 2, widthF, height);
            summaryPanel.add(serverOpsb);

            serverOpsbValue = createJLabel(resultLarge.getServerOpsb() + "", initX + borderX1, initY + borderY1 * 2, width, height);
            summaryPanel.add(serverOpsbValue);

            serverOpsbPointValue = createJLabel(getPointMore(resultLarge.getServerOpsb(), 0.2) + "", initX + padding + borderX, initY + borderY1 * 2, width, height);
            serverOpsbPointValue.setToolTipText("2/10  unit for every operational Server discovered");
            summaryPanel.add(serverOpsbPointValue);

            initY += borderY1 ;
            outputPanel.setSize(680, 535);
            summaryPanel.setSize(640, 440);
        }
         /*
        workstationFull
         */

        fbWorkstationLabel = createJLabel("Workstations with Advanced License :", initX, initY + borderY1 * 2, widthF, height);
        summaryPanel.add(fbWorkstationLabel);

        fbWorkstationValue = createJLabel(resultLarge.getWorkstationFull() + "", initX + borderX1, initY + borderY1 * 2, width, height);
        summaryPanel.add(fbWorkstationValue);

        fbWorkstationPointValue = createJLabel(getPointMore(resultLarge.getWorkstationFull(), 0.1) + "", initX + padding + borderX, initY + borderY1 * 2, width, height);
        fbWorkstationPointValue.setToolTipText("<html><body>1/10  unit  for every advanced<br></br> Workstation discovered</html></body>");
        summaryPanel.add(fbWorkstationPointValue);

          /*
        workstationBasic
         */

        basicWorkstationLabel = createJLabel("Workstations with Basic License :", initX, initY + borderY1 * 3, widthF, height);
        summaryPanel.add(basicWorkstationLabel);

        basicWorkstationValue = createJLabel(resultLarge.getWorkstationBasic() + "", initX + borderX1, initY + borderY1 * 3, width, height);
        summaryPanel.add(basicWorkstationValue);

        basicWorkstationPointValue = createJLabel(getPointMore(resultLarge.getWorkstationBasic(), 0.1) + "", initX + padding + borderX, initY + borderY1 * 3, width, height);
        basicWorkstationPointValue.setToolTipText("<html><body>1/10  unit  for every basic<br></br> Workstation discovered</html></body>");
        summaryPanel.add(basicWorkstationPointValue);


        if (resultLarge.getServerOpsb() != 0 || resultLarge.getWorkstationOfOpbs() != 0) {

            opsbWorkstationLabel = createJLabel("Workstations with Operational License :", initX, initY + borderY1 * 4, widthF, height);
            summaryPanel.add(opsbWorkstationLabel);

            opsbWorkstationValue = createJLabel(resultLarge.getWorkstationOfOpbs() + "", initX + borderX1, initY + borderY1 * 4, width, height);
            summaryPanel.add(opsbWorkstationValue);


            opsbWorkstationPointValue = createJLabel(getPointMore(resultLarge.getWorkstationOfOpbs(), 0.2) + "", initX + padding + borderX, initY + borderY1 * 4, width, height);
            opsbWorkstationPointValue.setToolTipText("2/10  unit for every operational Workstation discovered");
            summaryPanel.add(opsbWorkstationPointValue);
            initY += borderY1 ;
        }
              /*
        Network
         */
        networkLabel = createJLabel("Network Devices :", initX, initY + borderY1 * 4, widthF, height);
        summaryPanel.add(networkLabel);

        networkValue = createJLabel(resultLarge.getNetworkCis() + "", initX + borderX1, initY + borderY1 * 4, width, height);
        summaryPanel.add(networkValue);


        networkPointValue = createJLabel(getPointMore(resultLarge.getNetworkCis(), 0.1) + "", initX + padding + borderX, initY + borderY1 * 4, width, height);
        networkPointValue.setToolTipText("<html><body>1/10 compliancy unit for every <br></br>network device discovered</body></html>");
        summaryPanel.add(networkPointValue);

        /*
        Storage
         */

        storageLabel = createJLabel("Storage Devices :", initX, initY + borderY1 * 5, widthF, height);
        summaryPanel.add(storageLabel);

        storageValue = createJLabel(resultLarge.getStorageCis() + "", initX + borderX1, initY + borderY1 * 5, width, height);
        summaryPanel.add(storageValue);

        storagePointValue = createJLabel(getPointMore(resultLarge.getStorageCis(), 0.1) + "", initX + padding + borderX, initY + borderY1 * 5, width, height);
        storagePointValue.setToolTipText("<html><body>1/10 compliancy unit for every<br></br> storage device discovered</body></html>");
        summaryPanel.add(storagePointValue);

        /*
        Docker
         */

        dockerLabel = createJLabel("Containers :", initX, initY + borderY1 * 6, widthF, height);
        summaryPanel.add(dockerLabel);

        dockerValue = createJLabel(resultLarge.getDockerCis() + "", initX + borderX1, initY + borderY1 * 6, width, height);
        summaryPanel.add(dockerValue);

        dockerPointValue = createJLabel(getPointMore(resultLarge.getDockerCis(), 0.2) + "", initX + padding + borderX, initY + borderY1 * 6, width, height);
        dockerPointValue.setToolTipText("<html><body>1/10 compliancy unit for <br></br>every container device discovered</body></html>");
        summaryPanel.add(dockerPointValue);

         /*
        Docker
         */

        totalLabel = createJLabel("Total CIs :", initX, initY + borderY1 * 7, widthF, height);
        summaryPanel.add(totalLabel);

        totalValue = createJLabel(resultLarge.getServerOfFull() + resultLarge.getServerBasic() + resultLarge.getServerOpsb() + resultLarge.getWorkstationBasic() + resultLarge.getWorkstationFull() + resultLarge.getWorkstationOfOpbs() + resultLarge.getNetworkCis() + resultLarge.getStorageCis() + resultLarge.getDockerCis() + "", initX + borderX1, initY + borderY1 * 7, width, height);
        summaryPanel.add(totalValue);

        totalPointValue = createJLabel(resultLarge.getUsdUnits() + "", initX + padding + borderX, initY + borderY1 * 7, width, height);
        summaryPanel.add(totalPointValue);
         /*
        ALL
         */

        allPanel = new JPanel();
        allPanel.setBorder(BorderFactory.createTitledBorder("Summary :"));
        allPanel.setBackground(Color.white);
        allPanel.setBounds(5, initY + borderY1 * 8 + 10, 630, 70);
        allPanel.setLayout(null);

        migrationLabel = createJLabel("Unit Capacity :", initX, 20, width, height);
        allPanel.add(migrationLabel);

        migrationValue = createJLabel(resultLarge.getUnitCapacity() + "", initX + 120, 20, width, height);
        allPanel.add(migrationValue);

        compliancyLabel = createJLabel("Used Units :", initX + borderX1 - 10, 20, width, height);
        allPanel.add(compliancyLabel);

        compliancyValue = createJLabel(resultLarge.getUsdUnits() + "", initX + borderX1 + 90, 20, width, height);
        allPanel.add(compliancyValue);

        totalunitsLabel = createJLabel("Remaining Units :", initX + borderX + padding - 40, 20, width, height);
        allPanel.add(totalunitsLabel);
        BigDecimal bigDecimal = new BigDecimal(resultLarge.getUnitCapacity() - resultLarge.getUsdUnits());
        double remain = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        totalunitsValue = createJLabel(remain + "", initX + padding + borderX + 80, 20, width, height);
        if (resultLarge.getUnitCapacity() - resultLarge.getUsdUnits() < 0) {
            totalunitsValue.setForeground(Color.red);
        }

        allPanel.add(totalunitsValue);

        summaryPanel.add(allPanel);
        outputPanel.add(summaryPanel);
    }

    public void lessThanPanel(ResultLess resultLess) {

        outputPanel = createJPanel(0, 280, 680, 520, Color.white, null);

        initX = 20;
        initY = 20;
        int borderX = 150;
        int borderY = 60;
        padding = 320;
        int padding1 = 260;
        height = 30;
        width = 200;
        int borderX1 = 260;

        /*
        Current license status
         */

        currentPanel = createJPanel(20, initY, 640, 60, Color.white, null);
        currentPanel.setBorder(BorderFactory.createTitledBorder("Current License Capacity :"));

        udfLabel = createJLabel("UDF  Capacity :", initX, initY, width, height);
        currentPanel.add(udfLabel);

        udfValue = createJLabel(resultLess.getUdf() + "", initX + 100, initY, width, height);
        currentPanel.add(udfValue);

        udiLabel = createJLabel("UDI Capacity :", initX + borderX1, initY, width, height);
        currentPanel.add(udiLabel);

        udiValue = createJLabel(resultLess.getUdi() + "", initX + padding1 + 100, initY, width, height);
        currentPanel.add(udiValue);

        mdrLabel = createJLabel("MDR  Capacity :", initX + borderX + padding, initY, width, height);
        currentPanel.add(mdrLabel);

        mdrValue = createJLabel(resultLess.getMdr() + "", initX + borderX + padding + 100, initY, width, height);
        currentPanel.add(mdrValue);

        outputPanel.add(currentPanel);

        /*
        After upgrade
         */

        summaryPanel = createJPanel(20, initY + borderY, 640, 380, Color.white, null);
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Migration Unit Conversion :"));

        initY = 50;

        typeTitle = createJLabel("License Types", initX, 20, widthF, height);
        summaryPanel.add(typeTitle);

        numbers = createJLabel("CI Counts", initX + borderX1, 20, widthF, height);
        summaryPanel.add(numbers);

        points = createJLabel("Compliancy Units ", initX + borderX + padding, 20, widthF, height);
        summaryPanel.add(points);

        /*
        serverFull
         */

        serverFullLabel = createJLabel("Servers with Advanced License :", initX, initY, widthF, height);
        summaryPanel.add(serverFullLabel);

        serverFullValue = createJLabel(resultLess.getServerFull() + "", initX + borderX1, initY, width, height);
        summaryPanel.add(serverFullValue);

        serverFullPointValue = createJLabel("0", initX + padding + borderX, initY, width, height);
        summaryPanel.add(serverFullPointValue);

        /*
        server Basic
         */

        serverBasicLabel = createJLabel("Servers with Basic License :", initX, initY + borderY1, widthF, height);
        summaryPanel.add(serverBasicLabel);

        serverBasicValue = createJLabel(resultLess.getServerBasic() + "", initX + borderX1, initY + borderY1, width, height);
        summaryPanel.add(serverBasicValue);

        serverBasicPointValue = createJLabel(getPoint(resultLess.getServerBasic(), 0.1) + "", initX + padding + borderX, initY + borderY1, width, height);
        serverBasicPointValue.setToolTipText("1/10 compliancy unit added to 1/10 migration unit for every Basic Server discovered");
        summaryPanel.add(serverBasicPointValue);

        /*
        Allworkstation
         */

        allWorkstationLabel = createJLabel("Workstations :", initX, initY + borderY1 * 2, widthF, height);
        summaryPanel.add(allWorkstationLabel);

        allWorkstationValue = createJLabel(resultLess.getAllWorkstation() + "", initX + borderX1, initY + borderY1 * 2, width, height);
        summaryPanel.add(allWorkstationValue);


        allWorkstationPointValue = createJLabel("0", initX + padding + borderX, initY + borderY1 * 2, width, height);
        summaryPanel.add(allWorkstationPointValue);

         /*
        VM
         */

        vmLabel = createJLabel("VM Hosts :", initX, initY + borderY1 * 3, widthF, height);
        summaryPanel.add(vmLabel);

        vmValue = createJLabel(resultLess.getVm() + "", initX + borderX1, initY + borderY1 * 3, width, height);
        summaryPanel.add(vmValue);


        vmPointValue = createJLabel(getPoint(resultLess.getVm(), 0.9) + "", initX + padding + borderX, initY + borderY1 * 3, width, height);
        vmPointValue.setToolTipText("9/10 compliancy unit added to 1/10 migration unit for every VM Host discovered");
        summaryPanel.add(vmPointValue);
               /*
        Network
         */
        networkLabel = createJLabel("Network Devices :", initX, initY + borderY1 * 4, widthF, height);
        summaryPanel.add(networkLabel);

        networkValue = createJLabel(resultLess.getNetworkCis() + "", initX + borderX1, initY + borderY1 * 4, width, height);
        summaryPanel.add(networkValue);

        networkPointValue = createJLabel(getPoint(resultLess.getNetworkCis(), 0.1) + "", initX + padding + borderX, initY + borderY1 * 4, width, height);
        networkPointValue.setToolTipText("<html><body>1/10 compliancy unit for every <br></br>network device discovered</body></html>");
        summaryPanel.add(networkPointValue);

        /*
        Storage
         */

        storageLabel = createJLabel("Storage Devices :", initX, initY + borderY1 * 5, widthF, height);
        summaryPanel.add(storageLabel);

        storageValue = createJLabel(resultLess.getStorageCis() + "", initX + borderX1, initY + borderY1 * 5, width, height);
        summaryPanel.add(storageValue);

        storagePointValue = createJLabel(getPoint(resultLess.getStorageCis(), 0.1) + "", initX + padding + borderX, initY + borderY1 * 5, width, height);
        storagePointValue.setToolTipText("<html><body>1/10 compliancy unit for every<br></br> storage device discovered</body></html>");
        summaryPanel.add(storagePointValue);

        /*
        Docker
         */

        dockerLabel = createJLabel("Containers :", initX, initY + borderY1 * 6, widthF, height);
        summaryPanel.add(dockerLabel);

        dockerValue = createJLabel(resultLess.getDockerCis() + "", initX + borderX1, initY + borderY1 * 6, width, height);
        summaryPanel.add(dockerValue);

        dockerPointValue = createJLabel(getPoint(resultLess.getDockerCis(), 0.1) + "", initX + padding + borderX, initY + borderY1 * 6, width, height);
        dockerPointValue.setToolTipText("<html><body>1/10 compliancy unit for <br></br>every container device discovered</body></html>");
        summaryPanel.add(dockerPointValue);

         /*
        Docker
         */

        totalLabel = createJLabel("Total CIs :", initX, initY + borderY1 * 7, widthF, height);
        summaryPanel.add(totalLabel);

        totalValue = createJLabel(resultLess.getServerFull() + resultLess.getServerBasic() + resultLess.getAllWorkstation() + resultLess.getVm() + resultLess.getNetworkCis() + resultLess.getStorageCis() + resultLess.getDockerCis() + "", initX + borderX1, initY + borderY1 * 7, width, height);
        summaryPanel.add(totalValue);

        totalPointValue = createJLabel(resultLess.getCompliancyUnits() + "", initX + padding + borderX, initY + borderY1 * 7, width, height);
        summaryPanel.add(totalPointValue);
         /*
        ALL
         */

        allPanel = new JPanel();
        allPanel.setBorder(BorderFactory.createTitledBorder("ALL New License Units :"));
        allPanel.setBackground(Color.white);
        allPanel.setBounds(5, initY + borderY1 * 8 + 10, 630, 70);
        allPanel.setLayout(null);

        migrationLabel = createJLabel("Migration Units :", initX, 20, width, height);
        allPanel.add(migrationLabel);

        migrationValue = createJLabel(resultLess.getMigrationUnits() + "", initX + 120, 20, width, height);
        migrationValue.setToolTipText("2/10 migration unit for every udi capacity discovered and 10/10 2/10 migration unit for every udf capacity discovered");
        allPanel.add(migrationValue);

        compliancyLabel = createJLabel("Compliancy Units :", initX + borderX1 - 10, 20, width, height);
        allPanel.add(compliancyLabel);

        compliancyValue = createJLabel(resultLess.getCompliancyUnits() + "", initX + borderX1 + 120, 20, width, height);
        allPanel.add(compliancyValue);

        totalunitsLabel = createJLabel("Total Units :", initX + borderX + padding - 10, 20, width, height);
        allPanel.add(totalunitsLabel);

        totalunitsValue = createJLabel(resultLess.getTotalUntis() + "", initX + padding + borderX + 80, 20, width, height);
        allPanel.add(totalunitsValue);

        summaryPanel.add(allPanel);
        outputPanel.add(summaryPanel);

        /*
         Vaildation Code
        */
        HEADERS[0] = "UCMDB " + resultLess.getVersion();
        jTable.getModel().setValueAt(resultLess.getUdf() + "", 0, 2);
        jTable.getModel().setValueAt(resultLess.getUdi() + "", 0, 3);
        jTable.getModel().setValueAt(resultLess.getMdr() + "", 0, 4);
        jTable.getModel().setValueAt(resultLess.getServerFull() + "", 1, 5);
        jTable.getModel().setValueAt(resultLess.getServerBasic() + "", 1, 6);
        jTable.getModel().setValueAt(resultLess.getVm() + "", 1, 7);
        jTable.getModel().setValueAt(resultLess.getAllWorkstation() + "", 1, 8);
        jTable.getModel().setValueAt(resultLess.getNetworkCis() + "", 1, 9);
        jTable.getModel().setValueAt(resultLess.getStorageCis() + "", 1, 10);
        jTable.getModel().setValueAt(resultLess.getDockerCis() + "", 1, 11);
        jTable.getModel().setValueAt(resultLess.getMigrationUnits() + "", 2, 1);
        jTable.getModel().setValueAt(resultLess.getCompliancyUnits() + "", 3, 1);
        jTable.getModel().setValueAt(resultLess.getTotalUntis() + "", 4, 1);

        for (int i = 0; i < 5; i++) {
            for (int z = 0; z < 12; z++) {
                String data = jTable.getModel().getValueAt(i, z).toString();
                allData.append(data);
                allData.append(",");
            }
            allData.append("\r\n");
        }
        validationCode = new GetMD5().getMD5(allData.toString());
    }
}