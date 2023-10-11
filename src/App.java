import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

// needed imports 

public class App extends extFunctions { // utilizes functions from extFunctions class just so it looks better

    public static void main(String[] args) {

        JFrame jf1 = new JFrame("grade encouragement??");
        jf1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // will close frame when X is clicked
        jf1.setSize(600, 300);
        // setup JFrame windows




        JTextField voiddd = new JTextField();
        voiddd.setBounds(0, 0, 0, 0);
        // This is here hidden so that way it autofocuses on this instead the usernameTextBox below.
        // user doesn't interact with this



        JTextField usernameTextBox = new JTextField();
        usernameTextBox.setBounds(5, 10, 150, 20);
        usernameTextBox.setText("Enter Username");
        // Textbox for user to interact with

        usernameTextBox.addFocusListener(new FocusListener() {
            // These functions are here so we can treat the text in the textbox as a placeholder.
            // when user clicks on them, the text is set to nothing
            
            @Override
            public void focusGained(FocusEvent arg0) {
                usernameTextBox.setText("");
            }

            @Override
            public void focusLost(FocusEvent arg0) {
                //pass
            }
        });





        
        JPasswordField passwordTextBox = new JPasswordField();
        passwordTextBox.setText("Enter Password"); 
        passwordTextBox.setEchoChar((char) 0); // here we leave the text currently readable by the user and not "*" out.
        passwordTextBox.setBounds(5, 40, 150, 20);
        
        passwordTextBox.addFocusListener(new FocusListener() {
            // listener method to act as a placeholder
            
            @Override
            public void focusGained(FocusEvent arg0) {
                passwordTextBox.setText("");
                passwordTextBox.setEchoChar('*'); // now when the user types in their password, it is "*" out, and not readable
            }

            @Override
            public void focusLost(FocusEvent arg0) {
                //pass
            }
        });






        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(5, 70, 125, 20);
        

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                jf1.dispose(); // removes the currently open JFrame

                String username = usernameTextBox.getText();
                char[] passwordchar = passwordTextBox.getPassword(); 
                //must be stored as a char in order to get the password from the passwordTextBox

                String password = "";


                // this forloop is the current best way to get each character of the char[]. TRUST ME
                for (char char1 : passwordchar) { // this is here to convert the char[] to String,
                    password = password + char1;
                }

                
                focusCallback(username, password); // calls upon main method with the users input.
            }
        });

        jf1.add(voiddd);
        jf1.add(usernameTextBox);
        jf1.add(passwordTextBox);
        jf1.add(submitButton);
        jf1.setLayout(null);
        jf1.setVisible(true);
    }
}
