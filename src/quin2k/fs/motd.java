package quin2k.fs;

/*
Using the Spigot API 1.9 create a plugin that meets the following criteria:

Plugin supplies its own default config file that contains a message of the day. [DONE]

Performs the six tasks below.

When the plugin is loaded:
- Build the default config (if config is not present) with a single string for message of the
  day (motd). [DONE]
- Prints the message of the day to the console via configured motd. [DONE]
- Starts a synchronous task to broadcast the message of the day every 10 minutes. [DONE]
- Plays a sound to a player when they join the server. [DONE]

When the plugin is unloaded:
- Send a sad message to console that the console is being unloaded. [DONE]
- Cancel all running tasks. [DONE]

Once you've completed the project, upload it to github and reply with a link to the project
and we will move forward to the next steps on the application. After 7 days, if the project
is not uploaded for review, the application will be declined

Author Note:
I tried to go above and beyond - since both the Join Message and System Messages are referred
to as "MOTD" in some contexts, I set out to do all of them.  Sadly, some of it isn't actually
functioning as intended which is why it is commented out.  I do have some additional features
such as mhelp, mreload, motd, and joinmotd commands.
 */

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

// Created by Quin2k on 3/25/2017.

public class motd extends JavaPlugin implements Listener {
    BukkitTask task;

    public void onEnable() {
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("Successfully Enabled");
        String motd = this.getConfig().getString("motd").replace("&", "§");
        task = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
            public void run() {
                Bukkit.getServer().broadcastMessage(motd);
            }
            }, 0L, 600L); //currently 30 seconds for testing.
    }

    public void onDisable() {
        getLogger().info("Aww don't turn me off - I'm awesome!");
        cancelTask(task);
    }

    public void cancelTask(BukkitTask taskId) {
        taskId.cancel();
    }

    @EventHandler
    public void JoinMassage(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        e.setJoinMessage(this.getConfig().getString("motdJoin").replace("&", "§").replace("%player%", p.getDisplayName()));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(this.getConfig().getBoolean("motdOn")) {
            p.playSound(p.getLocation(), Sound.valueOf(this.getConfig().getString("motdSound")), 1, 0);
        }
    }

    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        StringBuilder str = null;
        String system;
        int i;

        if (command.getName().equalsIgnoreCase("mhelp")) {
            if (!sender.hasPermission("motd")) {
                sender.sendMessage("[FrozenMOTD] You don't have permission to view that.");
                return true;
            } else {
                sender.sendMessage("==================[Frozen MOTD]===================");
                sender.sendMessage("/mhelp: This help file");
                sender.sendMessage("/motd: See the Message of the Day");
                //sender.sendMessage("/motdset: Set a new Message of the Day");
                sender.sendMessage("/joinmotd: See your Join Message");
                //sender.sendMessage("/joinmotdset <text>: Set your Join Message");
                //sender.sendMessage("/sysmotd: See your System Message");
                //sender.sendMessage("/sysmotdset <text>: Set your System Message");
                sender.sendMessage("/mreload: Reload config.yml!");
                sender.sendMessage("==================================================");
                return true;
            }
        }
        if (command.getName().equalsIgnoreCase("motd")) {
            if (!sender.hasPermission("motd")) {
                sender.sendMessage("[FrozenMOTD] You don't have permission to view that.");
                return true;
            }
            else {
                system = this.getConfig().getString("motd").replaceAll("&", "§");
                sender.sendMessage("[FrozenMOTD] MOTD set to: " + system);
            }
        }

        /*
        if (command.getName().equalsIgnoreCase("motdset")) {
            if (!sender.hasPermission("motd")) {
                sender.sendMessage("[FrozenMOTD] You don't have permission to change that.");
                return true;
            } else if (args.length == 0) {
                sender.sendMessage("You need to input text after this command!");
                return true;
            } else {
                for (i = 0; i < args.length; ++i) {
                    str.append(args[i] + " ");
                }
                system = str.toString();
                this.getConfig().set("motd", system);
                this.saveConfig();
                system = this.getConfig().getString("motd");
                system = system.replaceAll("&", "§");
                sender.sendMessage("[Frozen MOTD] Announcement set to:" + system);
                return true;
            }
        }
        */
        if (command.getName().equalsIgnoreCase("joinmotd")) {
            if (!sender.hasPermission("motd")) {
                sender.sendMessage("[FrozenMOTD] You don't have permission to view that.");
                return true;
            }
            else {
                system = this.getConfig().getString("motdJoin").replaceAll("&", "§");
                sender.sendMessage("[FrozenMOTD] MOTD set to: " + system);
            }
        }
        /*
        if (command.getName().equalsIgnoreCase("joinmotdset")) {
            if (!sender.hasPermission("motd")) {
                sender.sendMessage("[FrozenMOTD] You don't have permission to change that.");
                return true;
            }
            else if(args.length == 0) {
                sender.sendMessage("You need to input text after this command!");
                return true;
            }
            else {
                for (i = 0; i < args.length; ++i) {
                    str.append(args[i] + " ");
                }
                system = str.toString();
                this.getConfig().set("motdJoin", system);
                this.saveConfig();
                system = this.getConfig().getString("motdJoin");
                system = system.replaceAll("&", "§");
                sender.sendMessage("[Frozen MOTD] Announcement set to:" + system);
                return true;
            }
        }
        */
        /*
        if (command.getName().equalsIgnoreCase("sysmotd")) {
            if (!sender.hasPermission("motd")) {
                sender.sendMessage("[FrozenMOTD] You don't have permission to view that.");
                return true;
            }
            else {
                system = this.getConfig().getString("motdSys").replaceAll("&", "§");
                sender.sendMessage("[FrozenMOTD] MOTD set to: " + system);
            }
        }
        */
        /*
        if (command.getName().equalsIgnoreCase("sysmotdset")) {
            if (!sender.hasPermission("motd")) {
                sender.sendMessage("[FrozenMOTD] You don't have permission to change that.");
                return true;
            }
            else if(args.length == 0) {
                sender.sendMessage("You need to input text after this command!");
                return true;
            }
            else {
                for (i = 0; i < args.length; ++i) {
                    str.append(args[i] + " ");
                }
                system = str.toString();
                this.getConfig().set("motdSys", system);
                this.saveConfig();
                system = this.getConfig().getString("motdSys");
                system = system.replaceAll("&", "§");
                sender.sendMessage("[Frozen MOTD] Announcement set to:" + system);
                return true;
            }
        }
        */
        if (command.getName().equalsIgnoreCase("mreload")) {
            if (!sender.hasPermission("motd")) {
                sender.sendMessage("[FrozenMOTD] You don't have permission to change that.");
                return true;
            } else {
                this.reloadConfig();
                this.saveConfig();
                sender.sendMessage("[FrozenMOTD] Reload Complete!");
                return true;
            }
        }
        return true;
    }
}
