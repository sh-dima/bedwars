package io.gitlab.shdima.bedwars.event.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import io.gitlab.shdima.bedwars.Bedwars;
import io.gitlab.shdima.bedwars.managers.NameTagManager;
import io.gitlab.shdima.bedwars.types.Arena;
import io.gitlab.shdima.bedwars.utility.ConfigurationUtility;

public final class ConnectionListener implements Listener {
	private final Bedwars plugin;

	public ConnectionListener(@NotNull final Bedwars plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(@NotNull final PlayerJoinEvent event) {
		event.getPlayer().teleport(ConfigurationUtility.getLobbySpawn());
	}

	@EventHandler
	public void onPlayerQuit(@NotNull final PlayerQuitEvent event) {
		final Player player = event.getPlayer();

		final Arena arena = plugin.getArenaManager().getArena(player);

		if (arena != null) {
			arena.removePlayer(player);
		}

		NameTagManager.removeTag(player);
	}
}
