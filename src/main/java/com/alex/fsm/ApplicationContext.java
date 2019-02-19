package com.alex.fsm;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;

@SpringBootApplication(scanBasePackages = {"com.alex.fsm"})
public class ApplicationContext {
  public static void main(String[] args) {
    SpringApplication.run(ApplicationContext.class, args);
  }

  @Autowired
  StateMachineListener Listener;

  @Bean
  @Profile({"FORK"})
  public StateMachine<String, String> buildStateMachine() throws Exception {
    StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();
    builder.configureStates().withStates().initial("SI").end("SF").fork("SFork").join("SJoin")
        .state("S1")
        .state("S2")
          .and().withStates().parent("SFork").initial("F1-1").end("F1-2")
          .and().withStates().parent("SFork").initial("F2-1").end("F2-2");

    builder.configureTransitions()
        .withExternal().source("SI").target("S1").event("E1").and()
        .withExternal().source("S1").target("S2").event("E2").and()
        .withExternal().source("S2").target("SFork").event("E3").and()
        .withFork().source("SFork").target("F1-1").target("F2-1").and()
        .withExternal().source("F1-1").target("F1-2").event("EF1").and()
        .withExternal().source("F2-1").target("F2-2").event("EF2").and()
        .withJoin().sources(Arrays.asList("F1-2", "F2-2")).target("SJoin").and()
        .withExternal().source("SJoin").target("SF").event("End");


    StateMachine<String, String> machine = builder.build();
    machine.addStateListener(Listener);
    return machine;
  }
  
  
}
