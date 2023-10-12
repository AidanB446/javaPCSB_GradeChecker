import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.WebElement;


// When i say main focus display box, or main data box, I mean the box that tells you grades, teacher names, classes, tardies, abscences etc
// If you still need to know what box I mean, you can just copy the xpath



public class extFunctions {

    public static void focusCallback(String username, String password) {

        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless"); // run the webdriver headless, (so that way user cant see browser open)
        WebDriver driver1 = new EdgeDriver(options); // declare webdriver object with our options added



        String rawGrades = null; // This will contain all the grades in the main focus display box

        try {
            driver1.get("https://focus.pcsb.org/focus/Modules.php?modname=misc/Portal.php"); // open signin page
            driver1.findElement(By.id("username-input")).sendKeys(username); //enter username in login form
            driver1.findElement(By.name("password")).sendKeys(password); // enter password in login form
            driver1.findElement(By.xpath("/html/body/div/div[3]/div/div[1]/form/div[2]/div[2]/button")).click(); // click sign in buttomn
            System.out.println("attempting to sign in...");

            Boolean gradeBoxElementExists = false;



            /*This while loop is here instead of implicit/explicit wait. It basically waits for page to load before trying to find needed
            elements
            
            Without this loop, then you would need almost megabit network speeds because it would be looking for xpaths before the page fully loads.
            */
            
            while (gradeBoxElementExists == false) {

                try {
                    if (driver1.findElements(By.xpath("/html/body/div/div[3]/div/div[1]/form/div[2]/div[1]/div[2]"))
                            .size() > 0
                            && driver1
                                    .findElement(By.xpath("/html/body/div/div[3]/div/div[1]/form/div[2]/div[1]/div[2]"))
                                    .getText().contains("Permission denied")) {
                        System.out.println("Login wrong");
                        return;
                    }

                } catch (Exception e) {}

                By gradeBoxElement = By.xpath("/html/body/div[1]/div[2]/div");
                List<WebElement> elements = driver1.findElements(gradeBoxElement); // if theres something in this list, we know that the xpath exists

                if (elements.size() > 0) { // checks if the xpath exists, if it does we know the page loaded.
                    System.out.println("found grades");
                    gradeBoxElementExists = true;
                }
            }






            rawGrades = driver1.findElement(By.xpath("/html/body/div[1]/div[2]/div")).getText(); //gets all data from main grade box
            driver1.close(); // close our webdriver
        } catch (Exception exception) {
            System.out.println("\n" + "Something may be wrong with network connection");
            System.out.print(exception);
            return;
        }

        String[] splitGrades = rawGrades.split("\n"); // split so we can traverse through data
        ArrayList<String> finalGrades1 = new ArrayList<String>(); // this is the grades that will be returned to user


        // get list of all possible grade letters
        ArrayList<String> letterGrades = new ArrayList<String>();
        
        letterGrades.addAll(Arrays.asList("A", "B", "C", "D", "F"));

        // traverse through posGrade (contains everything in focus display box) and isolate your grades and classes.
        for (String posGrade : splitGrades) {
            int indexLength = posGrade.length(); // indexlength is used twice so, ehh mine aswell make it a var

            if (posGrade.contains("ABC")) {
                finalGrades1.add(posGrade);
            }

            if (indexLength != 0 && indexLength < 7 && Arrays.asList(posGrade.split("")).contains(",") == false && letterGrades.contains(posGrade.substring(posGrade.length() - 1))) {
                finalGrades1.add(posGrade);
            }

        }



        // show finalgrades to the user
        JFrame jf = new JFrame("your grades");
        jf.setSize(600, 300);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JList<String> listOfGrades = new JList<String>(finalGrades1.toArray(new String[finalGrades1.size()])); //convert ArrayList to JList

        jf.add(listOfGrades);
        jf.setVisible(true);

    }

}
