package com.alex.fsm.model;

public class Config {
  private Stage currentStage;

  public Stage getCurrentStage() {
    return currentStage;
  }

  public void setCurrentStage(Stage currentStage) {
    this.currentStage = currentStage;
  }

}
