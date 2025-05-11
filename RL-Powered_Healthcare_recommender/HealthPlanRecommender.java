import java.util.Random;

public class HealthPlanRecommender {
    static final int NUM_PATIENTS = 1000;
    static final int NUM_FEATURES = 5;
    static final int NUM_ACTIONS = 3;
    static final int NUM_STATES = 10;

    static double[][] Q = new double[NUM_STATES][NUM_ACTIONS];
    static double alpha = 0.1;
    static double gamma = 0.9;
    static double epsilon = 0.2;
    static Random rand = new Random();

    public void train() {
        for (int episode = 0; episode < 10000; episode++) {
            double[] patient = generatePatient();
            int state = getState(patient);

            int action = chooseAction(state);
            double reward = getReward(patient, action);
            int nextState = getState(generatePatient());

            Q[state][action] += alpha * (reward + gamma * max(Q[nextState]) - Q[state][action]);
        }
    }

    public String recommendPlan(double[] patient) {
        int state = getState(patient);
        int action = argmax(Q[state]);
        return actionName(action);
    }

    static double[] generatePatient() {
        double[] p = new double[NUM_FEATURES];
        for (int i = 0; i < NUM_FEATURES; i++) {
            p[i] = rand.nextDouble();
        }
        return p;
    }

    static int getState(double[] features) {
        double sum = 0;
        for (double f : features) sum += f;
        return Math.min((int) (sum / NUM_FEATURES * NUM_STATES), NUM_STATES - 1);
    }

    static int chooseAction(int state) {
        if (rand.nextDouble() < epsilon) {
            return rand.nextInt(NUM_ACTIONS);
        } else {
            return argmax(Q[state]);
        }
    }

    static double getReward(double[] p, int action) {
        double chronic = p[1];
        double income = p[2];
        double literacy = p[3];
        double distance = p[4];

        return switch (action) {
            case 0 -> 1.5 - chronic - distance;
            case 1 -> 2.0 - 0.5 * chronic - 0.5 * distance + 0.2 * literacy;
            case 2 -> 2.5 - chronic + 0.3 * income - distance;
            default -> -1.0;
        };
    }

    static int argmax(double[] arr) {
        int best = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[best]) best = i;
        }
        return best;
    }

    static double max(double[] arr) {
        double best = arr[0];
        for (double v : arr) if (v > best) best = v;
        return best;
    }

    static String actionName(int action) {
        return switch (action) {
            case 0 -> "Basic";
            case 1 -> "Enhanced";
            case 2 -> "Premium";
            default -> "Unknown";
        };
    }
}
