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

/**
 * TODO Work on JavaDoc
 * 
 * A class that represents a version of an application.
 * This is represented by two parts: 
 * a "raw version", which is not guaranteed to be logically comparable with other "raw versions" of the same application (e.g. "18w12a"),
 * and a normalized version, which should be logically comparable with other normalized versions of the same application (e.g. "1.3.1").
 * A normalized version is compliant with semantic versioning.
 */
public abstract class ApplicationVersion {

	/**
	 * TODO Work on JavaDoc
	 * 
	 * A "raw version" of an application, which is not guaranteed to be logically comparable with other "raw versions" of the same application (e.g. "18w12a").
	 */
	protected String rawVersion;
	/**
	 * TODO Work on JavaDoc
	 * 
	 * A normalized version of an application, which should be logically comparable with other normalized versions of the same application (e.g. "1.3.1").
	 * A normalized version is compliant with semantic versioning.
	 */
	protected String normalizedVersion;
	
	public String getRawVersion() {
		
		return rawVersion;
		
	}
	
	public String getNormalizedVersion() {
		
		return normalizedVersion;
		
	}
	
}
