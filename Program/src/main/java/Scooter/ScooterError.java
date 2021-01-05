package Scooter;

import java.time.LocalDate;

public class ScooterError {
    private String id;
    private String errorMessage;
    private LocalDate timeOfError;
    private double xLoc;
    private double yLoc;

    public ScooterError(String id, String errorMessage, LocalDate timeOfError, double xLoc, double yLoc){
        this.id = id;
        this.errorMessage = errorMessage;
        this.timeOfError = timeOfError;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

    public String getId() {
        return id;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDate getTimeOfError() {
        return timeOfError;
    }

    public double getxLoc() {
        return xLoc;
    }

    public double getyLoc() {
        return yLoc;
    }
}
