import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import org.json.simple.JSONObject;

/**
 *
 * @author Milo Hsieh
 */

public class WeatherAppAPI extends JFrame{
    
    private JSONObject weatherData;
    
    public WeatherAppAPI() {
        // set up our GUI and add a title
        super("Weather App");
        
        // configure GUI to end the program's process once it has been closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // set the size of our GUI (in pixels)
        setSize(450, 650);
        
        // load our GUI at the center of screen
        setLocationRelativeTo(null);
        
        // make our layout manager null to manually position our components within the GUI
        setLayout(null);
        
        // prevent any resize of our GUI
    setResizable(false);
        
        addGuiComponents();
    }
    
    private void addGuiComponents() {
        // search field
        JTextField searchTextField = new JTextField();
        
        // set the location and size of our component
        searchTextField.setBounds(15, 15, 350, 45);
        
        // change the font style and size
        searchTextField.setFont(new Font("MV Boli", Font.PLAIN, 24));
        
        add(searchTextField);
              
        // weather image
        JLabel weatherConditionImage = new JLabel(loadImage("src/pic/cloudy.png"));
        weatherConditionImage.setBounds(0, 125, 450, 217);
        add(weatherConditionImage);
        
        // temperature text
        JLabel temperatureText = new JLabel("10 C");
        temperatureText.setBounds(0, 350, 450, 55);
        temperatureText.setFont(new Font("MV boli", Font.BOLD, 48));
        
        // center the text
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);
        
        // weather condition description
        JLabel weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0, 405, 450, 36);
        weatherConditionDesc.setFont(new Font("MV Boli", Font.PLAIN, 32));
        
        // center the text
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);
        
        // humidity image
        JLabel humidityImage = new JLabel(loadImage("src/pic/humidity.png"));
        humidityImage.setBounds(15, 500, 74, 66);
        add(humidityImage);
        
        // humidity text
        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100% </html>");
        humidityText.setBounds(90, 500, 85, 55);
        humidityText.setFont(new Font("MV Boli", Font.PLAIN, 16));
        add(humidityText);
        
        // windspeed image
        JLabel windspeedImage = new JLabel(loadImage("src/pic/windspeed.png"));
        windspeedImage.setBounds(220, 500, 74, 66);
        add(windspeedImage);
        
        // windspeed text
        JLabel windspeedText = new JLabel("<html><b>Windspeed</b> 15km/h </html>");
        windspeedText.setBounds(310, 500, 85, 55);
        windspeedText.setFont(new Font("MV Boli", Font.PLAIN, 16));
        add(windspeedText);
        
        // search button
        JButton searchButton = new JButton(loadImage("src/pic/search.png"));
        
        // change the cursor to a hand cursor when hovering over this button
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 15, 45, 45);
        searchButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // get location from user
                String userInput = searchTextField.getText();
                
                // validate input - remove whitespace to ensure non-empty text
                if(userInput.replaceAll("\\s", "").length() <= 0){
                    return;
                }
                
                // retrieve weather data
                weatherData = WeatherApp.getWeatherData(userInput);
                             
                // update GUI           
                // update weatherr image
                String weatherCondition = (String) weatherData.get("weather_condition");
                
                // depending on the condition, we will update the weather image  that corresponds with the condition
                switch(weatherCondition) {
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("src/pic/clear.png"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("src/pic/cloudy.png"));
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("src/pic/rain.png"));
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("src/pic/snow.png"));
                        break;
                }
                
                // update temperature text
                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + " C");
                
                // update weather condition text
                weatherConditionDesc.setText(weatherCondition);
                
                // update humidity text
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b>" + humidity + "%</html>");
                
                // update windspeed text
                double windspeed = (double) weatherData.get("windspeed");
                windspeedText.setText("<html><b>Windspeed</b>" + windspeed + "km/h</html>");
            }
        });
        add(searchButton);
        
        
    }
    
    private ImageIcon loadImage(String resourcePath) {
        try{
            //read the image file from the path given
            BufferedImage image = ImageIO.read(new File(resourcePath));
            
            //returns on image icon so that our component can render it
            return new ImageIcon(image);
        }catch(IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Could not find the resource");
        return null;
    }
}