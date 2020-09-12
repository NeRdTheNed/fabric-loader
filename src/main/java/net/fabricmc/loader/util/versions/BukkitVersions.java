/*
 * Copyright 2016 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.loader.util.versions;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.LoggingException;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.launch.common.FabricLauncherBase;
import net.fabricmc.loader.util.FileSystemUtil;
import net.fabricmc.loader.util.versions.MinecraftVersions.MinecraftVersion;

public final class BukkitVersions {

	public static BukkitVersion getVersion(Path gameJar) {
		
		BukkitVersion ret;
		
		try (FileSystemUtil.FileSystemDelegate jarFs = FileSystemUtil.getJarFileSystem(gameJar, false)) {
			FileSystem fs = jarFs.get();
			Path file;
			
			if (Files.isRegularFile(file = fs.getPath("META-INF/MANIFEST.MF"))
					&& (ret = fromManifest(Files.newInputStream(gameJar))) != null) {
				return ret;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return fromFileName(gameJar.getFileName().toString());
	}

	private static BukkitVersion fromManifest(InputStream is) {
		
		try {
			JarInputStream jarStream = new JarInputStream(is);
			Manifest mf = jarStream.getManifest();
			Attributes attr = mf.getMainAttributes();
			
			if (attr.getValue("Specification-Title").equals("Bukkit")) /* Sanity check */ {
				
				String rawVersion = attr.getValue("Implementation-Version");
				//String normalizedVersion = attr.getValue("Implementation-Version"); //todo figure out some kind of versioning
				String normalizedVersion = "0.0.0"; //hack
				jarStream.close();
				return new BukkitVersion(rawVersion, normalizedVersion);
				
			} else {
				
				jarStream.close();
				LogManager.getLogger().error("Presumed Bukkit server file seemingly did not contain a Bukkit manifest! Presumed version will be based on file name instead.");
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return (BukkitVersion) null;
		
	}
	
	private static BukkitVersion fromFileName(String name) {
		// strip extension
		int pos = name.lastIndexOf('.');
		if (pos > 0) name = name.substring(0, pos);

		return new BukkitVersion(name, name); //todo find a better solution 
	}
	
	public static final class BukkitVersion extends ApplicationVersion {
		private BukkitVersion(String name, String release) {
			this.rawVersion = name;
			this.normalizedVersion = release;
		}
	}
	
}
