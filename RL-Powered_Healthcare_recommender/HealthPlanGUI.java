import javax.swing.*;
import java.awt.*;

public class HealthPlanGUI {
    private JFrame frame;
    private JTextField ageField, chronicField, incomeField, literacyField, distanceField;
    private JLabel resultLabel;
    private HealthPlanRecommender recommender;

    public HealthPlanGUI() {
        recommender = new HealthPlanRecommender();
        recommender.train();

        frame = new JFrame("RL Health Plan Recommender");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 350);
        frame.setLayout(new GridLayout(7, 2));

        frame.add(new JLabel("Age (0-1):"));
        ageField = new JTextField("0.5");
        frame.add(ageField);

        frame.add(new JLabel("Chronic Score (0-1):"));
        chronicField = new JTextField("0.3");
        frame.add(chronicField);

        frame.add(new JLabel("Income Level (0-1):"));
        incomeField = new JTextField("0.6");
        frame.add(incomeField);

        frame.add(new JLabel("Literacy Level (0-1):"));
        literacyField = new JTextField("0.7");
        frame.add(literacyField);

        frame.add(new JLabel("Distance to Clinic (0-1):"));
        distanceField = new JTextField("0.4");
        frame.add(distanceField);

        JButton recommendButton = new JButton("Recommend Plan");
        frame.add(recommendButton);

        resultLabel = new JLabel("Recommended plan will appear here.");
        frame.add(resultLabel);

        recommendButton.addActionListener(e -> recommend());

        frame.setVisible(true);
    }

    private void recommend() {
        double[] patient = new double[5];
        try {
            patient[0] = Double.parseDouble(ageField.getText());
            patient[1] = Double.parseDouble(chronicField.getText());
            patient[2] = Double.parseDouble(incomeField.getText());
            patient[3] = Double.parseDouble(literacyField.getText());
            patient[4] = Double.parseDouble(distanceField.getText());
        } catch (NumberFormatException e) {
            resultLabel.setText("Invalid input. Enter numbers between 0 and 1.");
            return;
        }

        String plan = recommender.recommendPlan(patient);
        resultLabel.setText("Recommended Plan: " + plan);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HealthPlanGUI::new);
    }
}
