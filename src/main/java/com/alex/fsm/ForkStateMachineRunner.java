package com.alex.fsm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

@Component
@Profile({"FORK"})
public class ForkStateMachineRunner implements CommandLineRunner {
  private static final Logger logger = LoggerFactory.getLogger(ForkStateMachineRunner.class);

  @Autowired
  private StateMachine<String, String> stateMachine;

  @Override
  public void run(String... args) throws Exception {
    stateMachine.start();
    stateMachine.sendEvent("E1");
    stateMachine.sendEvent("E2");
    stateMachine.sendEvent("E3");
    stateMachine.sendEvent("EF1");
    stateMachine.sendEvent("EF2");
    stateMachine.sendEvent("End");
    logger.info("Alex Final State:" + stateMachine.getState().getId());
    stateMachine.stop();
  }
}
