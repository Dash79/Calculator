import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

/*********************************************************************************************
 * Header
 * This is a simple calculator that performs functions based on the given assignment prompt
 * @author Dawit Ashenafi Getachew
 * @version Version1
 * @date 12-02-2024
**********************************************************************************************/
public class Calculator extends Application 
{
    private TextField inputDisplay;
    private TextField outputDisplay;
    private Label messageLabel;
    private ToggleGroup mathGradeGroup;
    private RadioButton grade4RadioButton;
    private RadioButton grade5RadioButton;
    private RadioButton grade6RadioButton;

    // Sound effect file
    private static final String CLICK_SOUND_FILE = "click.mp3";

    @Override
    public void start(Stage primaryStage) 
    {
        primaryStage.setTitle("Calculator");
        primaryStage.setWidth(300);
        primaryStage.setHeight(300);
        primaryStage.setResizable(false);

        // Creating the title label
        Label titleLabel = new Label("CalcMaster");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        // Creating the display
        inputDisplay = new TextField();
        inputDisplay.setEditable(false);
        inputDisplay.setAlignment(Pos.CENTER_RIGHT);

        outputDisplay = new TextField();
        outputDisplay.setEditable(false);
        outputDisplay.setAlignment(Pos.CENTER_RIGHT);

        messageLabel = new Label();
        messageLabel.setAlignment(Pos.CENTER);

        // Creating radio buttons
        mathGradeGroup = new ToggleGroup();
        grade4RadioButton = new RadioButton("Grade 4");
        grade4RadioButton.setToggleGroup(mathGradeGroup);
        grade4RadioButton.setSelected(true);
        grade5RadioButton = new RadioButton("Grade 5");
        grade5RadioButton.setToggleGroup(mathGradeGroup);
        grade6RadioButton = new RadioButton("Grade 6");
        grade6RadioButton.setToggleGroup(mathGradeGroup);

        // Creating number buttons
        Button[] numberButtons = new Button[10];
        for (int i = 0; i < 10; i++) 
        {
            final int num = i;
            numberButtons[i] = new Button(String.valueOf(i));
            numberButtons[i].setOnAction(e -> 
            {
                appendToInputDisplay(String.valueOf(num));
                playButtonClickSound();
            });
        }

        // Creating buttons for arithmetic operations
        Button plus = new Button("+");
        plus.setOnAction(e -> 
        {
            appendToInputDisplay("+");
            playButtonClickSound();
        });

        Button minus = new Button("-");
        minus.setOnAction(e -> 
        {
            appendToInputDisplay("-");
            playButtonClickSound();
        });

        Button multiply = new Button("*");
        multiply.setOnAction(e -> 
        {
            appendToInputDisplay("*");
            playButtonClickSound();
        });

        Button divide = new Button("/");
        divide.setOnAction(e -> 
        {
            appendToInputDisplay("/");
            playButtonClickSound();
        });

        Button equals = new Button("=");
        equals.setOnAction(e -> 
        {
            calculateResult();
            playButtonClickSound();
        });

        Button clear = new Button("Clear");
        clear.setOnAction(e -> 
        {
            clearDisplays();
            playButtonClickSound();
        });

        // Layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(3);
        gridPane.setPadding(new Insets(10));
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(titleLabel, 0, 0, 4, 1);
        gridPane.add(inputDisplay, 0, 1, 4, 1);
        gridPane.add(outputDisplay, 0, 2, 4, 1);
        gridPane.add(messageLabel, 0, 3, 4, 1);

        gridPane.add(grade4RadioButton, 0, 4);
        gridPane.add(grade5RadioButton, 1, 4);
        gridPane.add(grade6RadioButton, 2, 4);

        // Adding number buttons
        for (int i = 1; i <= 9; i++) 
        {
            int row = 6 + (9 - i) / 3;
            int col = (i - 1) % 3;
            gridPane.add(numberButtons[i], col, row);
        }
        gridPane.add(numberButtons[0], 1, 9);

        // Adding arithmetic operation buttons
        gridPane.add(plus, 3, 6);
        gridPane.add(minus, 3, 7);
        gridPane.add(multiply, 3, 8);
        gridPane.add(divide, 3, 9);

        // Adding equals and clear buttons
        gridPane.add(equals, 2, 9);
        gridPane.add(clear, 0, 9);

        // Set background color
        gridPane.setStyle("-fx-background-color: #ADD8E6;");

        Scene scene = new Scene(gridPane, 250, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void appendToInputDisplay(String text) 
    {
        inputDisplay.appendText(text);
    }

    private void clearDisplays() 
    {
        inputDisplay.clear();
        outputDisplay.clear();
        messageLabel.setText("");
    }

    private void calculateResult() 
    {
        String expression = inputDisplay.getText();
        try {
            String[] parts = expression.split("[-+*/]");
            double num1 = Double.parseDouble(parts[0]);
            double num2 = Double.parseDouble(parts[1]);
            char operator = expression.charAt(parts[0].length());
            double result = 0;

            if (grade4RadioButton.isSelected() && operator == '-') 
            {
                if (num1 < num2) 
                {
                    messageLabel.setText("Error");
                    return;
                }
            }

            if (grade4RadioButton.isSelected() && operator == '/') 
            {
                if (num1 % num2 == 0) {
                    result = num1 / num2;
                } else {
                    messageLabel.setText("Error");
                    return;
                }
            } 
            else if (grade5RadioButton.isSelected() && operator == '/') 
            {
                int quotient = (int) (num1 / num2);
                int remainder = (int) (num1 % num2);
                String resultString = quotient + (remainder > 0 ? (" R " + remainder) : "");
                outputDisplay.setText(resultString);
                messageLabel.setText("");
                return;
            } 
            else if (grade6RadioButton.isSelected() && operator == '/') 
            {
                result = Math.round((num1 / num2) * 10000.0) / 10000.0; 
            } 
            else 
            {
                switch (operator) 
                {
                    case '+':
                        result = num1 + num2;
                        break;
                    case '-':
                        result = num1 - num2;
                        break;
                    case '*':
                        result = num1 * num2;
                        break;
                    case '/':
                        if (num2 != 0) {
                            result = num1 / num2;
                        } else {
                            messageLabel.setText("Error: Division by zero");
                            return;
                        }
                        break;
                }
            }

            outputDisplay.setText(Double.toString(result));
            messageLabel.setText("");
        } 
        catch (NumberFormatException | ArrayIndexOutOfBoundsException e) 
        {
            messageLabel.setText("Error: Invalid expression");
        }
    }

    private void playButtonClickSound() 
    {
        try 
        {
            Media sound = new Media(new File(CLICK_SOUND_FILE).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } 
        catch (Exception e) 
        {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }
    
}
