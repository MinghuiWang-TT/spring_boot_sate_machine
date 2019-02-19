package com.alex.fsm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

@Component
public class StateMachineListener extends StateMachineListenerAdapter<String, String> {
  private static final Logger logger = LoggerFactory.getLogger(StateMachineListener.class);

  @Override
  public void transitionEnded(Transition<String, String> transition) {
    StringBuilder sb = new StringBuilder();
    sb.append("TransitionEnded source:");
    if (transition.getSource() != null) {
      sb.append(transition.getSource().getId());
    } else {
      sb.append("NULL");
    }
    sb.append(" target:");
    if (transition.getTarget() != null) {
      sb.append(transition.getTarget().getId());
    } else {
      sb.append("NULL");
    }
    sb.append(" trigger:");
    if (transition.getTrigger() != null) {
      sb.append(transition.getTrigger().getEvent());
    } else {
      sb.append("NULL");
    }

    logger.info("Alex:" + sb.toString());
  }

  @Override
  public void stateChanged(State<String, String> from, State<String, String> to) {
    StringBuilder sb = new StringBuilder();
    sb.append("StateChanged from:");
    if (from != null) {
      sb.append(from.getId());
    } else {
      sb.append("NULL");
    }
    sb.append(" to:");
    if (to != null) {
      sb.append(to.getId());
    } else {
      sb.append("NULL");
    }
    logger.info("Alex:" + sb.toString());
  }

}
