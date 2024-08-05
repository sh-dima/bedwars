package io.gitlab.shdima.bedwars;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import io.gitlab.shdima.bedwars.commands.BedwarsCommand;
import io.gitlab.shdima.bedwars.event.listeners.ConnectionListener;
import io.gitlab.shdima.bedwars.event.listeners.GameListener;
import io.gitlab.shdima.bedwars.managers.ArenaManager;
import io.gitlab.shdima.bedwars.types.Arena;
import io.gitlab.shdima.bedwars.utility.ConfigurationUtility;

public final class Bedwars extends JavaPlugin {
	private ArenaManager arenaManager;

	@Override
	public void onEnable() {
		// Setting up the configuration must happen before setting up the arena manager,
		// as the arena manager relies heavily on the config.yml file.
		ConfigurationUtility.setUpConfig(this);

		arenaManager = new ArenaManager(this);

		final PluginManager pluginManager = Bukkit.getPluginManager();

		pluginManager.registerEvents(new ConnectionListener(this), this);
		pluginManager.registerEvents(new GameListener(this), this);

		new BedwarsCommand(this);
	}

	@Override
	public void onDisable() {
		if (arenaManager != null) {
			for (final Arena arena : arenaManager.getArenas()) {
				arena.getVillager().remove();
			}
		}
	}

	public ArenaManager getArenaManager() {
		return arenaManager;
	}
}
