import javax.swing.*;

/**
 *
 * @author hsiin
 */

public class AppLauncher {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                // display our weather app GUI
                new WeatherAppAPI().setVisible(true);
            }
        });
    }
}