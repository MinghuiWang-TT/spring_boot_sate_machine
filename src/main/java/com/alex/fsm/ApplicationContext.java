package com.alex.fsm;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import com.alex.fsm.model.Config;
import com.alex.fsm.model.Data;
import com.alex.fsm.model.Events;
import com.alex.fsm.model.Stage;
import com.alex.fsm.model.States;


@SpringBootApplication(scanBasePackages = {"com.alex.fsm"})
public class ApplicationContext {
  private static final Logger logger = LoggerFactory.getLogger(ApplicationContext.class);
  
  public static void main(String[] args) {
    SpringApplication.run(ApplicationContext.class, args);
  }

  @Autowired
  StateMachineListener Listener;

  @SuppressWarnings("unchecked")
  @Bean
  @Profile({"FORK"})
  public StateMachine<String, String> stateMachineForForkSample() throws Exception {
    StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();
    builder.configureStates().withStates().initial("SI").end("SF").fork("SFork").join("SJoin")
        .state("S1").state("S2").and().withStates().parent("SFork").initial("F1-1").end("F1-2")
        .and().withStates().parent("SFork").initial("F2-1").end("F2-2");

    builder.configureTransitions().withExternal().source("SI").target("S1").event("E1").and()
        .withExternal().source("S1").target("S2").event("E2").and().withExternal().source("S2")
        .target("SFork").event("E3").and().withFork().source("SFork").target("F1-1").target("F2-1")
        .and().withExternal().source("F1-1").target("F1-2").event("EF1").and().withExternal()
        .source("F2-1").target("F2-2").event("EF2").and().withJoin()
        .sources(Arrays.asList("F1-2", "F2-2")).target("SJoin").and().withExternal().source("SJoin")
        .target("SF").event("End");


    StateMachine<String, String> machine = builder.build();
    machine.addStateListener(Listener);
    return machine;
  }

  @SuppressWarnings("unchecked")
  @Bean
  @Profile({"CHIOCE"})
  public StateMachine<States, Events> stateMachineForJunction() throws Exception {
    StateMachineBuilder.Builder<States, Events> builder = StateMachineBuilder.builder();
    builder.configureStates().withStates().initial(States.INTIAL).end(States.END)
        .choice(States.SELECT_STAGE)
        .choice(States.VERIFY_CREDENTIAL)
        .choice(States.VERIFY_EMAIL)
        .choice(States.VERIFY_IDENTITY);

    builder.configureTransitions()
    .withExternal().source(States.INTIAL).target(States.SELECT_STAGE).event(Events.START).and()
    .withChoice().source(States.SELECT_STAGE)
      .first(States.VERIFY_CREDENTIAL, context ->{
        Config config = context.getExtendedState().get("config",Config.class);
        logger.info("Alex Select stage VERIFY_CREDENTIAL current stage:" + config.getCurrentStage());
        return config.getCurrentStage().equals(Stage.INIT);
      })
      .then(States.VERIFY_EMAIL, context -> {
        Config config = context.getExtendedState().get("config",Config.class);
        logger.info("Alex Select stage VERIFY_EMAIL current stage:" + config.getCurrentStage());
        return config.getCurrentStage().equals(Stage.CREDENTIAL_VERIFYED);
      })
      .then(States.VERIFY_IDENTITY, context -> {
        Config config = context.getExtendedState().get("config",Config.class);
        logger.info("Alex Select stage VERIFY_IDENTITY current stage:" + config.getCurrentStage());
        return config.getCurrentStage().equals(Stage.EMAIL_VERIFIED);
      })
      .last(States.END)
      .and()
    .withChoice().source(States.VERIFY_CREDENTIAL)
      .first(States.SELECT_STAGE, context -> {

          Data data = context.getExtendedState().get("data",Data.class);
          Config config = context.getExtendedState().get("config",Config.class);
          boolean result =  Util.verifyCredential(data.getUserId(), data.getUserSecret());
          logger.info("Alex VerifyCredential VERIFY_CREDENTIAL result:" + result);
          if (result) {
            config.setCurrentStage(Stage.CREDENTIAL_VERIFYED);
          }
          return result;
        })
      .last(States.END)
      .and()
    .withChoice().source(States.VERIFY_EMAIL)
      .first(States.SELECT_STAGE, context -> {
          Data data = context.getExtendedState().get("data",Data.class);
          Config config = context.getExtendedState().get("config",Config.class);
          boolean result =  Util.verifyEmail(data.getEmailAddress());
          logger.info("Alex VerifyCredential VERIFY_EMAIL result:" + result + " email address:" + data.getEmailAddress());
          if (result) {
            config.setCurrentStage(Stage.EMAIL_VERIFIED);
          }
          return result;
        })
      .last(States.END)
      .and()
      .withChoice().source(States.VERIFY_IDENTITY)
        .first(States.SELECT_STAGE, context -> {
            Data data = context.getExtendedState().get("data",Data.class);
            Config config = context.getExtendedState().get("config",Config.class);
            boolean result =  Util.verifyIdCode(data.getIdCode());
            logger.info("Alex VerifyCredential VERIFY_IDENTITY result:" + result + " id code:" + data.getIdCode());
            if (result) {
              config.setCurrentStage(Stage.INDETITY_VEIRFYED);
            }
            return result;
          })
        .last(States.END);

    StateMachine<States, Events> machine = builder.build();
    machine.addStateListener(Listener);
    return builder.build();
  }

}
