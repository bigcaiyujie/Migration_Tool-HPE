package com.microfocus.migrationtool;

import com.hp.ucmdb.api.ExecutionException;

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

public class NewUi extends JFrame {
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
    JLabel existServerLabel;
    StringBuffer allData;
    String validationCode;
    JPanel existOSIPanel;
    JPanel existDevicePanel;
    JPanel compliancyPanel;
    JPanel migrationPanel;
    JLabel existServerFullValue ;
    JLabel existServerInventoryValue;
    JLabel existFullLabel ;
    JLabel existInventoryLabel;
    JLabel existWorkstationLabel;
    JLabel existWorkstationInventoryValue;
    JLabel existVMLabel ;
    JLabel existVMInventoryValue ;
    JLabel existNetworkLabel;
    JLabel existNetworkValue;
    JLabel existStorageLabel;
    JLabel existStorageFullValue;
    JLabel  existContainerLabel ;
    JLabel  existContainerValue ;
    JLabel existOSILabel;
    JLabel existDevicekLabel;
    JLabel migrationTip;
    JLabel migrationTipLabel;
    JLabel totalunitsTip;
    int height = 30;
    int width = 180;
    int border = 55;
    int border2 = 70;
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
    static String[] HEADERS = {"UCMB 10.33", "Units", "UDF", "UDI", "MDR", "Server Advanced", "Server Basic", "VM Hosts", "Workstations", "Network Devices", "Storage Devices", "Containers"};
    final static String[][] DATA = new String[][]{
            {"Capacity", "", "", "", "", "", "", "", "", "", "", ""},
            {"Discovered", "", "", "", "", "", "", "", "", "", "", ""},
            {"Migration Units", "", "", "", "", "", "", "", "", "", "", ""},
            {"Compliancy Units", "", "", "", "", "", "", "", "", "", "", ""},
            {"Total Units", "", "", "", "", "", "", "", "", "", "", ""},
            {"Validation Code", "", "", "", "", "", "", "", "", "", "", ""}
    };

    public NewUi() {
        this.setTitle("UCMDB 10.4x License Migration Calculator for Universal Discovery");
        this.setSize(685, 240);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);
        this.setResizable(false);

        /*
        inputPanel
         */
        inputPanel = createJPanel(0, 0, 680, 205, Color.white, null);
        inputPanel.setBorder(BorderFactory.createTitledBorder("UCMDB Server Connection"));
        ipaddressLabel = createJLabel("IP Address:", initX, initY, width, height);
        inputPanel.add(ipaddressLabel);

        ipaddressText = createJTextField("", initX + border2, initY, width, height);
        inputPanel.add(ipaddressText);

        pswLabel = createJLabel("Password:", initX + padding, initY + border, width, height);
        inputPanel.add(pswLabel);

        pswText = new JPasswordField("");
        pswText.setBounds(initX + padding + border2, initY + border, width, height);
        inputPanel.add(pswText);

        portLabel = createJLabel("Port:", initX + padding, initY, width, height);
        inputPanel.add(portLabel);

        portText = createJTextField("", initX + padding + border2, initY, width, height);
        inputPanel.add(portText);

        userLabel = createJLabel("User:", initX, initY + border, width, height);
        inputPanel.add(userLabel);


        userText = createJTextField("", initX + border2, initY + border, width, height);
        inputPanel.add(userText);

        protocolLabel = createJLabel("Protocol:", initX, initY + border * 2, width, height);
        inputPanel.add(protocolLabel);

        bg = new ButtonGroup();
        jr1 = createJRadioButton("HTTP", initX + border2, initY + border * 2, 100, height);
        jr2 = createJRadioButton("HTTPS", initX + border2 + 100, initY + border * 2, 100, height);
        jr1.setBackground(Color.white);
        jr2.setBackground(Color.white);
        bg.add(jr1);
        bg.add(jr2);
        inputPanel.add(jr1);
        inputPanel.add(jr2);

        exportCsv = createJButton("Export.CSV",initX + padding + 120, initY + border * 2 , 90, height, false);
        inputPanel.add(exportCsv);
        exportCsv.setEnabled(false);

        clearData = createJButton("Reset",initX + padding + border2 + 160, initY + border * 2 , 90, height, true);
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
        label.setFont(new Font (Font.DIALOG, 0, 12));
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
                JOptionPane.showMessageDialog(null, "Exported Successfully !");
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    class ClearDatatListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setSize(685, 240);
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
        private NewUi ui;

        public SubmitDataListener(NewUi ui) {
            this.ui = ui;
        }

        public void actionPerformed(ActionEvent e) {
            setSize(685, 240);
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
                    getAllLicense.judgepermission();
                    currentProgressStatus = 90;
                    ucmdbVersion = getAllLicense.getVersion();
                    jTable = new JTable(DATA, HEADERS);
                    if (getAllLicense.getVersion())
                    {
                        ResultLarge resultLarge = getAllLicense.getLicense();
                        moreThanPanel(resultLarge);
                        this.jFrame.add(outputPanel);
                        if (resultLarge.getServerOpsb() != 0 || resultLarge.getWorkstationOfOpbs() != 0){
                            this.jFrame.setSize(685, 775);
                        }else{
                            this.jFrame.setSize(685, 715);
                        }

                        outputPanel.setVisible(true);
                        outputPanel.setBorder(BorderFactory.createTitledBorder("Current Version UCMDB " + resultLarge.getVersion()));
                        JOptionPane.showMessageDialog(null, "Completed !");
                    } else {
                        ResultLess resultLess = getAllLicense.getLicense();
                        if (resultLess.getUdi() < resultLess.getUsedUdi() || resultLess.getUdf() < resultLess.getUsedUdf() || resultLess.getMdr() < resultLess.getUsedMdr()) {
                            JOptionPane.showMessageDialog(null, "<html><body>Migration to UCMDB 10.40 and unit-based licensing is not possible when your current licensing <br></br>is not compliant. </body></html>", "Warning", JOptionPane.WARNING_MESSAGE);
                            notEnoughPanel(resultLess);
                            this.jFrame.add(outputPanel, null);
                            this.jFrame.setSize(685, 405);
                            outputPanel.setVisible(true);
                            outputPanel.setBorder(BorderFactory.createTitledBorder("Current Version UCMDB " + resultLess.getVersion()));
                        } else {
                            lessThanPanel(resultLess);
                            this.jFrame.add(outputPanel, null);
                            currentProgressStatus = 100;
                            JOptionPane.showMessageDialog(null, "Completed !");
                            exportCsv.setEnabled(true);
                            this.jFrame.setSize(685, 825);
                            outputPanel.setVisible(true);
                            outputPanel.setBorder(BorderFactory.createTitledBorder("Current Version UCMDB " + resultLess.getVersion()));
                        }
                    }
                    currentProgressStatus = 100;
                    clearData.setEnabled(true);
                    submitData.setEnabled(true);

                } catch (ExecutionException e){
                    currentProgressStatus = 100;
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Permission Denied", "Warning", JOptionPane.WARNING_MESSAGE);
                    clearData.setEnabled(true);
                    submitData.setEnabled(true);
                }catch (Exception x) {
                    currentProgressStatus = 100;
                    x.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Connection faild !");
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

        outputPanel = createJPanel(0, 205, 680, 165, Color.white, null);
        initX = 10;
        initY = 20;
        padding = 355;
        width = 350;
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
                udiLabel.setToolTipText("Universal Discovery - Full: " + resultLess.getUsedUdf() + "/" + resultLess.getUdf() + " - Usage: 0 %");
            } else {
                udiLabel = createJLabel("Universal Discovery - Full: " + resultLess.getUsedUdf() + "/" + resultLess.getUdf() + " - Usage: Exceeded", initX + padding, initY, width, height);
                udiLabel.setForeground(Color.red);
                udiLabel.setToolTipText("Universal Discovery - Full: " + resultLess.getUsedUdf() + "/" + resultLess.getUdf() + " - Usage: Exceeded");
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
                mdrLabel.setToolTipText("Available 3rd patry integrations: " + resultLess.getUsedMdr() + "/" + resultLess.getMdr() + " - Usage: 0 %");
            } else {
                mdrLabel = createJLabel("Available 3rd patry integrations: " + resultLess.getUsedMdr() + "/" + resultLess.getMdr() + " - Usage: Exceeded", initX, initY + borderY, width, height);
                mdrLabel.setForeground(Color.red);
                mdrLabel.setToolTipText("Available 3rd patry integrations: " + resultLess.getUsedMdr() + "/" + resultLess.getMdr() + " - Usage: Exceeded");
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

        outputPanel = createJPanel(0, 205, 680, 475, Color.white, null);
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
        serverFullPointValue.setToolTipText("10/10 unit for every advanced Server discovered");
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

        outputPanel = createJPanel(0, 205, 680, 585, Color.white, null);

        int borderX11 = 120;
        int borderY11 = 25;
        initX = 20;
        initY = 20;
        int borderX = 120;
        int borderY = 60;
        padding = 350;
        int padding1 = 260;
        height = 30;
        width = 200;
        int borderX1 = 260;

        /*
        Current license status
         */

        currentPanel = createJPanel(20, initY, 640, 200, Color.white, null);
        currentPanel.setBorder(BorderFactory.createTitledBorder("Existing Capacity in [UCMDB "+resultLess.getVersion()+"]"));

        udfLabel = createJLabel("UD Full Capacity (OSI):", initX, initY, width, height);
        currentPanel.add(udfLabel);

        udfValue = createJLabel(resultLess.getUdf() + "", initX + 150, initY, width, height);
        currentPanel.add(udfValue);

        udiLabel = createJLabel("UD Inventory Capacity (OSI):", initX  + padding, initY, width, height);
        currentPanel.add(udiLabel);

        udiValue = createJLabel(resultLess.getUdi() + "", initX  + padding + 180, initY, width, height);
        currentPanel.add(udiValue);

        mdrLabel = createJLabel("MDR  Capacity :", initX + borderX + padding, initY, width, height);
//        currentPanel.add(mdrLabel);

        mdrValue = createJLabel(resultLess.getMdr() + "", initX + borderX + padding + 100, initY, width, height);
//        currentPanel.add(mdrValue);

        existOSIPanel = createJPanel(initX, initY+30, 300, 135, Color.white, null);
        existOSIPanel.setBorder(BorderFactory.createTitledBorder(""));

        existOSILabel = createJLabel("Discovered OSI", 10 , 10, 100, 20);
        existOSILabel.setFont(new Font (Font.DIALOG, 1, 12));
        existFullLabel = createJLabel("UD Full", 10+borderX11 , 10, 100, 20);
        existInventoryLabel = createJLabel("UD Inventory", 10+borderX11*2-35 , 10, 100, 20);
        existOSIPanel.add(existOSILabel);
        existOSIPanel.add(existFullLabel);
        existOSIPanel.add(existInventoryLabel);

        existServerLabel = createJLabel("Servers", 10 , initY+borderY11, 100, 20);
        existServerFullValue = createJLabel(resultLess.getServerFull()+"", 10+borderX11 , initY+borderY11, 100, 20);
        existServerInventoryValue = createJLabel(resultLess.getServerBasic()+"", 10+borderX11*2-35 , initY+borderY11, 100, 20);
        existOSIPanel.add(existServerLabel);
        existOSIPanel.add(existServerFullValue);
        existOSIPanel.add(existServerInventoryValue);

        existWorkstationLabel = createJLabel("Workstations", 10 , initY+borderY11*2, 100, 20);
        existWorkstationInventoryValue = createJLabel(resultLess.getAllWorkstation()+"", 10+borderX11*2-35 , initY+borderY11*2, 100, 20);
        existOSIPanel.add(existWorkstationLabel);
        existOSIPanel.add(existWorkstationInventoryValue);

        existVMLabel = createJLabel("VM Hosts", 10 , initY+borderY11*3, 100, 20);
        existVMInventoryValue = createJLabel(resultLess.getVm()+"", 10+borderX11*2-35 , initY+borderY11*3, 100, 20);
        existOSIPanel.add(existVMLabel);
        existOSIPanel.add(existVMInventoryValue);

        existDevicePanel = createJPanel(initX+350, initY+30, 250, 135, Color.white, null);
        existDevicePanel.setBorder(BorderFactory.createTitledBorder(""));

        existDevicekLabel = createJLabel("Discovered Devices & Containers ", 10 , 10, 200, 20);
        existDevicekLabel.setFont(new Font (Font.DIALOG, 1, 12));
        existNetworkLabel = createJLabel("Network Devices", 10 , initY+borderY11, 150, 20);
        existNetworkValue = createJLabel(resultLess.getNetworkCis()+"", 10+borderX11+50 , initY+borderY11, 100, 20);
        existDevicePanel.add(existDevicekLabel);
        existDevicePanel.add(existNetworkLabel);
        existDevicePanel.add(existNetworkValue);

        existStorageLabel = createJLabel("Storage Devices", 10 , initY+borderY11*2, 150, 20);
        existStorageFullValue = createJLabel(resultLess.getStorageCis()+"", 10+borderX11+50 , initY+borderY11*2, 100, 20);
        existDevicePanel.add(existStorageLabel);
        existDevicePanel.add(existStorageFullValue);

        existContainerLabel = createJLabel("Containers", 10 , initY+borderY11*3, 150, 20);
        existContainerValue = createJLabel(resultLess.getDockerCis()+"", 10+borderX11+50 , initY+borderY11*3, 100, 20);
        existDevicePanel.add(existContainerLabel);
        existDevicePanel.add(existContainerValue);

        currentPanel.add(existOSIPanel);
        currentPanel.add(existDevicePanel);
        outputPanel.add(currentPanel);

        /*
        After upgrade
         */
        migrationPanel = createJPanel(20, initY + 200, 640, 70, Color.white, null);
        migrationPanel.setBorder(BorderFactory.createTitledBorder("10.4x Migration Entitlement"));


        migrationLabel = createJLabel("Total Migration Units :", initX , initY, widthF, height);
        migrationTipLabel = createJLabel("Tip :", initX+250 , initY, width, height);
        migrationTipLabel.setForeground(Color.gray);
        migrationValue = createJLabel(resultLess.getMigrationUnits()+"", initX+150 , initY, widthF, height);
        migrationValue.setFont(new Font (Font.DIALOG, 1, 12));
        migrationLabel.setFont(new Font (Font.DIALOG, 1, 12));
        migrationTip = createJLabel("<html>1 unit for every UD Full OSI License,<br>1 unit for every 10 UD Inventory OSI License.</html>)", initX+285 , initY, 500, height);
        migrationTip.setForeground(Color.gray);
        migrationPanel.add(migrationTipLabel);
        migrationPanel.add(migrationLabel);
        migrationPanel.add(migrationValue);
        migrationPanel.add(migrationTip);

        outputPanel.add(migrationPanel);

        compliancyPanel = createJPanel(20, initY + 270, 640, 240, Color.white, null);
        compliancyPanel.setBorder(BorderFactory.createTitledBorder("10.4x Compliancy Entitlement"));

        initY = 50;

        typeTitle = createJLabel("License Types", initX, 20, widthF, height);
        compliancyPanel.add(typeTitle);

        numbers = createJLabel("Counts", initX + borderX1, 20, widthF, height);
        compliancyPanel.add(numbers);

        points = createJLabel("Compliancy Units ", initX + borderX + padding, 20, widthF, height);
        compliancyPanel.add(points);
        /*
        server Basic
         */
        serverBasicLabel = createJLabel("Servers with Basic License ", initX, initY , widthF, height);
        compliancyPanel.add(serverBasicLabel);

        serverBasicValue = createJLabel(resultLess.getServerBasic() + "", initX + borderX1, initY , width, height);
        compliancyPanel.add(serverBasicValue);

        serverBasicPointValue = createJLabel(getPoint(resultLess.getServerBasic(), 0.1) + "", initX + padding + borderX, initY , width, height);
        serverBasicPointValue.setToolTipText("<html><body>1 compliancy unit for every<br></br>10 servers discovered with<br></br>UD Inventory</body></html>");
        compliancyPanel.add(serverBasicPointValue);
         /*
        VM
         */
        vmLabel = createJLabel("VM Hosts ", initX, initY + borderY1 , widthF, height);
        compliancyPanel.add(vmLabel);

        vmValue = createJLabel(resultLess.getVm() + "", initX + borderX1, initY + borderY1 , width, height);
        compliancyPanel.add(vmValue);

        vmPointValue = createJLabel(getPoint(resultLess.getVm(), 0.9) + "", initX + padding + borderX, initY + borderY1, width, height);
        vmPointValue.setToolTipText("<html><body>9 compliancy units for every<br></br>10 VMs discovered with UD<br></br>Inventory</body></html>");
        compliancyPanel.add(vmPointValue);
               /*
        Network
         */
        networkLabel = createJLabel("Network Devices ", initX, initY + borderY1 * 2, widthF, height);
        compliancyPanel.add(networkLabel);

        networkValue = createJLabel(resultLess.getNetworkCis() + "", initX + borderX1, initY + borderY1 * 2, width, height);
        compliancyPanel.add(networkValue);

        networkPointValue = createJLabel(getPoint(resultLess.getNetworkCis(), 0.1) + "", initX + padding + borderX, initY + borderY1 * 2, width, height);
        networkPointValue.setToolTipText("<html><body>1 compliancy unit for every<br></br>10 discoved network devices</body></html>");
        compliancyPanel.add(networkPointValue);

        /*
        Storage
         */

        storageLabel = createJLabel("Storage Devices ", initX, initY + borderY1 * 3, widthF, height);
        compliancyPanel.add(storageLabel);

        storageValue = createJLabel(resultLess.getStorageCis() + "", initX + borderX1, initY + borderY1 * 3, width, height);
        compliancyPanel.add(storageValue);

        storagePointValue = createJLabel(getPoint(resultLess.getStorageCis(), 0.1) + "", initX + padding + borderX, initY + borderY1 * 3, width, height);
        storagePointValue.setToolTipText("<html><body>1 compliancy unit for every <br></br>10 discovered storage devices</body></html>");
        compliancyPanel.add(storagePointValue);

        /*
        Docker
         */

        dockerLabel = createJLabel("Containers ", initX, initY + borderY1 * 4, widthF, height);
        compliancyPanel.add(dockerLabel);

        dockerValue = createJLabel(resultLess.getDockerCis() + "", initX + borderX1, initY + borderY1 * 4, width, height);
        compliancyPanel.add(dockerValue);

        dockerPointValue = createJLabel(getPoint(resultLess.getDockerCis(), 0.1) + "", initX + padding + borderX, initY + borderY1 * 4, width, height);
        dockerPointValue.setToolTipText("<html><body>1 compliancy unit for every<br></br>10 discovered containers</body></html>");
        compliancyPanel.add(dockerPointValue);


        compliancyLabel = createJLabel("Total Compliancy Units :", initX+borderX1+40, initY + borderY1 * 5, widthF, height);
        compliancyLabel.setFont(new Font (Font.DIALOG, 1, 12));
        compliancyPanel.add(compliancyLabel);

        compliancyValue = createJLabel( resultLess.getCompliancyUnits()+"", initX + padding + borderX, initY + borderY1 * 5, width, height);
        compliancyValue.setFont(new Font (Font.DIALOG, 1, 12));
        compliancyPanel.add(compliancyValue);
        outputPanel.add(compliancyPanel);

         /*
        ALL
         */


        totalunitsLabel = createJLabel("Total Unit Entitlement :", initX+20 , initY+490, width, height);
        totalunitsLabel.setFont(new Font (Font.DIALOG, 1, 12));
        outputPanel.add(totalunitsLabel);

        totalunitsValue = createJLabel(resultLess.getTotalUntis() + "", initX +170, initY+490, width, height);
        totalunitsValue.setFont(new Font (Font.DIALOG, 1, 12));
        outputPanel.add(totalunitsValue);

        totalunitsTip = createJLabel( "Tip : Migration Units plus Compliancy Units.", initX +270, initY+490, widthF, height);
        totalunitsTip.setForeground(Color.gray);
        outputPanel.add(totalunitsTip);

        if(resultLess.getUdf()==0&resultLess.getUdi()==0){
            serverBasicPointValue.setText("0");
            vmPointValue.setText("0");
            networkPointValue.setText("0");
            storagePointValue.setText("0");
            dockerPointValue.setText("0");
            compliancyValue.setText("0");
            resultLess.setCompliancyUnits(0);
        }
        /*
         Vaildation Codex
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