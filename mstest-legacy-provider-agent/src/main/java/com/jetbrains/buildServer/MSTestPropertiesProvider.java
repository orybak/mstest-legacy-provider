package com.jetbrains.buildServer;

import jetbrains.buildServer.agent.AgentLifeCycleAdapter;
import jetbrains.buildServer.agent.AgentLifeCycleListener;
import jetbrains.buildServer.agent.BuildAgent;
import jetbrains.buildServer.agent.BuildAgentConfiguration;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.positioning.PositionConstraint;
import jetbrains.buildServer.util.positioning.PositionConstraintAware;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Oleg Rybak (oleg.rybak@jetbrains.com)
 */
public class MSTestPropertiesProvider extends AgentLifeCycleAdapter implements PositionConstraintAware {

  private static final String CONFIG_PREFIX = "teamcity.dotnet.mstest.";
  private static final String SYSTEM_PREFIX = "MSTest.";

  public MSTestPropertiesProvider(@NotNull final EventDispatcher<AgentLifeCycleListener> events) {
    events.addListener(this);
  }

  @NotNull
  public PositionConstraint getConstraint() {
    // .Net properties extension has no orderId and is not position aware
    // We cannot reference it in PositionAware interface
    return PositionConstraint.last();
  }

  @Override
  public void agentInitialized(@NotNull BuildAgent agent) {
    final BuildAgentConfiguration config = agent.getConfiguration();
    final Map<String, String> configProperties = config.getConfigurationParameters();
    final Map<String, String> systemProperties = config.getBuildParameters().getSystemProperties();
    for (Map.Entry<String, String> entry: configProperties.entrySet()) {
      if (entry.getKey().startsWith(CONFIG_PREFIX)) {
        String systemPropertyKey = SYSTEM_PREFIX + entry.getKey().substring(CONFIG_PREFIX.length());
        if (!systemProperties.containsKey(systemPropertyKey)) {
          config.addSystemProperty(systemPropertyKey, entry.getValue());
        }
      }
    }
  }
}
