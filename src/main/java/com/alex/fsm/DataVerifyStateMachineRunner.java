package com.alex.fsm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import com.alex.fsm.model.Config;
import com.alex.fsm.model.Data;
import com.alex.fsm.model.Events;
import com.alex.fsm.model.Stage;
import com.alex.fsm.model.States;


@Component
@Profile({"CHIOCE"})
public class DataVerifyStateMachineRunner implements CommandLineRunner {
  private static final Logger logger = LoggerFactory.getLogger(DataVerifyStateMachineRunner.class);

  @Autowired
  StateMachine<States, Events> stateMachine;

  @Override
  public void run(String... args) throws Exception {
    Data data = new Data();
    Config config = new Config();
    config.setCurrentStage(Stage.INIT);


    data.setUserId("");
    data.setUserSecret("");
    stateMachine.start();
    stateMachine.getExtendedState().getVariables().put("data", data);
    stateMachine.getExtendedState().getVariables().put("config", config);
    stateMachine.sendEvent(Events.START);
    stateMachine.stop();
    logger.info("Alex Current stage 0 " + config.getCurrentStage());

    stateMachine.start();
    data.setUserId("alexwang");
    data.setUserSecret("test_secret");
    
    stateMachine.getExtendedState().getVariables().put("data", data);
    stateMachine.getExtendedState().getVariables().put("config", config);
    stateMachine.sendEvent(Events.START);
    stateMachine.stop();
    logger.info("Alex Current stage 1 " + config.getCurrentStage());


    stateMachine.start();
    data.setEmailAddress("invalid email address");
    stateMachine.getExtendedState().getVariables().put("data", data);
    stateMachine.getExtendedState().getVariables().put("config", config);
    stateMachine.sendEvent(Events.START);
    stateMachine.stop();
    logger.info("Alex Current stage 2 " + config.getCurrentStage());


    stateMachine.start();
    data.setEmailAddress("test@hotmail.com");
    stateMachine.getExtendedState().getVariables().put("data", data);
    stateMachine.getExtendedState().getVariables().put("config", config);
    stateMachine.sendEvent(Events.START);
    stateMachine.stop();
    logger.info("Alex Current stage 3 " + config.getCurrentStage());


    stateMachine.start();
    data.setIdCode("123456789012345");
    stateMachine.getExtendedState().getVariables().put("data", data);
    stateMachine.getExtendedState().getVariables().put("config", config);
    stateMachine.sendEvent(Events.START);
    stateMachine.stop();
    logger.info("Alex Current stage 4 " + config.getCurrentStage());
    
    
    stateMachine.start();
    Data data2 = new Data();
    Config config2 = new Config();
    config2.setCurrentStage(Stage.INIT);
    data2.setUserId("test");
    data2.setUserSecret("secret11111");
    data2.setIdCode("123456789654321");
    data2.setEmailAddress("test@gmail.com");
    stateMachine.getExtendedState().getVariables().put("data", data2);
    stateMachine.getExtendedState().getVariables().put("config", config2);
    stateMachine.sendEvent(Events.START);
    stateMachine.stop();
    logger.info("Alex Current stage 5 " + config2.getCurrentStage());
    logger.info("Alex stateMachine final state " + stateMachine.getState().getId().name());
  }
}
