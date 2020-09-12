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

package net.fabricmc.loader.game;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.util.Arguments;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import net.fabricmc.loader.util.versions.BukkitVersions;

public class BukkitMinecraftGameProvider extends MinecraftGameProvider {
	@Override
	public String getGameId() {
		return super.getGameId() + "-bukkit";
	}

	@Override
	public String getGameName() {
		return super.getGameName() + "/Bukkit";
	}

	@Override
	public void acceptArguments(String... argStrs) {
		super.acceptArguments(argStrs);
		arguments.addExtraArg("--nojline");
	}
	
	@Override
	public boolean locateGame(EnvType envType, ClassLoader loader) {
		this.envType = envType;
		List<String> entrypointClasses;

		if (envType == EnvType.CLIENT) {
			//...Bukkit servers should probably never be client side.
			return false;
		} else {
			entrypointClasses = Collections.singletonList("org.bukkit.craftbukkit.Main");
		}

		Optional<GameProviderHelper.EntrypointResult> entrypointResult = GameProviderHelper.findFirstClass(loader, entrypointClasses);
		if (!entrypointResult.isPresent()) {
			return false;
		}

		entrypoint = entrypointResult.get().entrypointName;
		gameJar = entrypointResult.get().entrypointPath;
		realmsJar = null;
		hasModLoader = GameProviderHelper.getSource(loader, "ModLoader.class").isPresent();
		versionData = BukkitVersions.getVersion(gameJar);

		return true;
	}
	
}