package client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Dialog extends JFrame{
    private final int DEFAULT_WIDTH = 400;
    private final int DEFAULT_HEIGHT = 300;

    private JTextField ipField;
    private JTextField nickField;
    private JTextField portField;
    private JLabel ipLabel;
    private JLabel nickLabel;
    private JLabel portLabel;
    private JLabel errorLabel;

    private JButton button;

    private ClientDialogInterface clientDialogInterface;

    public Dialog(){
        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((scrSize.width - DEFAULT_WIDTH) / 2,
                (scrSize.height - DEFAULT_HEIGHT) / 2,
                DEFAULT_WIDTH, DEFAULT_HEIGHT);

        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        setResizable(false);
        setMinimumSize(this.getSize());
        this.setTitle("Client");

        ipField = new JTextField("127.0.0.1");
        ipField.setHorizontalAlignment(JTextField.RIGHT);
        nickField = new JTextField("user");
        portField = new JTextField("8080");
        portField.setHorizontalAlignment(JTextField.RIGHT);

        ipLabel = new JLabel("IP:");
        nickLabel = new JLabel("Nick:");
        portLabel = new JLabel("Port:");
        errorLabel = new JLabel("");

        button = new JButton("Start");

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 100;
        constraints.weighty = 100;
        constraints.insets = new Insets(10, 5, 5, 5);

        add(nickLabel, constraints, 0, 0, 1, 1);
        add(ipLabel, constraints, 0, 1, 1, 1);
        add(portLabel, constraints, 0, 2, 1, 1);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 100;
        constraints.weighty = 0;
        add(ipField, constraints, 1, 1, 1, 1);
        add(nickField, constraints, 1, 0, 1, 1);
        add(portField, constraints, 1, 2, 1, 1);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        add(button, constraints, 1, 3, 1, 1);
        constraints.anchor = GridBagConstraints.WEST;
        add(errorLabel, constraints, 0, 4, 2, 1);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ipField.getText().equals("") || nickField.getText().equals("") || portField.getText().equals("")){
                    errorLabel.setText("Все поля должны быть заполнены");
                    return;
                }

                if(!validateIP(ipField.getText())){
                    errorLabel.setText("IP адресс некорректен");
                    return;
                }

                if(!isNumeric(portField.getText())){
                    errorLabel.setText("Port некорректен");
                    return;
                }
                errorLabel.setText("");

                clientDialogInterface.message(nickField.getText(), ipField.getText(), Integer.parseInt(portField.getText()));
                Dialog.this.dispose();
            }
        });
    }

    private void add(Component c, GridBagConstraints constraints, int x, int y, int w, int h){
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridheight = h;
        constraints.gridwidth = w;
        getContentPane().add(c, constraints);
    }

    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    public boolean validateIP(final String ip){
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(IPADDRESS_PATTERN);
        matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    public static boolean isNumeric(String str)
    {
        try{
            Integer.parseInt(str);
        }
        catch(NumberFormatException nfe){
            return false;
        }
        return true;
    }

    public void setStartListener(ClientDialogInterface clientDialogInterface){
        this.clientDialogInterface = clientDialogInterface;
    }
}
