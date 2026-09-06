package dev.haxalotl.gnomicon;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.Files;
import java.nio.file.Path;

public class GNOMicon implements ModInitializer {
	public static final String MOD_ID = "gnomicon";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static void createDesktopEntry() {
		try {
			Process process = new ProcessBuilder("gnome-shell", "--version").start();
			String getGnome = new String(process.getInputStream().readAllBytes()).trim();
			process.waitFor();
			Path desktopPath = Path.of(System.getProperty("user.home") + "/.local/share/applications/" + "gnomicon-" + GNOMiconConfig.windowName + ".desktop");

			if (getGnome.toUpperCase().contains("GNOME")) {
				System.out.println("GNOME version: " + getGnome);

				if (Files.notExists(desktopPath)) {

					Files.createFile(desktopPath);
					Files.writeString(desktopPath,
							"#!/usr/bin/env xdg-open\n\n" +
									"[Desktop Entry]\n" +
									"Type=Application\n" +
									"Icon=" + FabricLoader.getInstance().getConfigDir().toString() + "/gnomicon/" + GNOMiconConfig.iconName + "\n" +
									"StartupWMClass=" + GNOMiconConfig.windowName
					);
					LOGGER.info("Desktop file has been created");
				} else {
					LOGGER.warn("Your computer is probably using KDE");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onInitialize() {
        LOGGER.info("GNOME is gonna give me an aneurysm");
    }

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
