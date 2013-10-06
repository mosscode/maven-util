/**
 * Copyright (C) 2013, Moss Computing Inc.
 *
 * This file is part of maven-util.
 *
 * maven-util is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * maven-util is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with maven-util; see the file COPYING.  If not, write to the
 * Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 *
 * Linking this library statically or dynamically with other modules is
 * making a combined work based on this library.  Thus, the terms and
 * conditions of the GNU General Public License cover the whole
 * combination.
 *
 * As a special exception, the copyright holders of this library give you
 * permission to link this library with independent modules to produce an
 * executable, regardless of the license terms of these independent
 * modules, and to copy and distribute the resulting executable under
 * terms of your choice, provided that you also meet, for each linked
 * independent module, the terms and conditions of the license of that
 * module.  An independent module is a module which is not derived from
 * or based on this library.  If you modify this library, you may extend
 * this exception to your version of the library, but you are not
 * obligated to do so.  If you do not wish to do so, delete this
 * exception statement from your version.
 */
package com.moss.maven.util;

import java.io.File;
import java.util.Properties;

/**
 * A tool to locate maven artifacts in local-filesystem repositories.
 */
public class SimpleArtifactFinder {
	
	private static final String DEFAULT_ARTIFACT_TYPE = "jar";
	
	/**
	 * Find an artifact file in the local repository. Assumes the local repository is located
	 * at ${user.home}/.m2/repository.
	 * @param artifact - note that the classifier property of this class may be null, in
	 * which case it is ignored, but the rest of the properties in this class must be non-null.
	 * @returns The file of an artifact in the local repository.
	 */
	public File findLocal(SimpleArtifact artifact) {
		return findLocal(artifact.getGroupId(), artifact.getArtifactId(), artifact.getVersion(), artifact.getClassifier(), artifact.getType());
	}
	
	/**
	 * Find an artifact file in the local repository. Assumes the local repository is located
	 * at ${user.home}/.m2/repository.
	 */
	public File findLocal(String groupId, String artifactId, String version) {
		return findLocal(groupId, artifactId, version, null, null);
	}
	
	/**
	 * Find an artifact file in the local repository. Assumes the local repository is located
	 * at ${user.home}/.m2/repository.
	 * @param groupId - required
	 * @param artifactId - required
	 * @param version - required
	 * @param classifier - can be null, in which case this is ignored
	 * @param type - if null, defaults to 'jar'
	 * @returns The file of an artifact in the local repository.
	 */
	public File findLocal(String groupId, String artifactId, String version, String classifier, String type) {
		StringBuffer path = new StringBuffer();
		path.append(System.getProperty("user.home")).append("/.m2/repository/");
		
		String[] groupPieces = groupId.split("\\.");
		for (int i=0; i<groupPieces.length; i++) {
			String groupPath = groupPieces[i];
			path.append(groupPath);
			path.append("/");
		}
		
		path.append(artifactId).append("/").append(version).append("/");
		path.append(artifactId).append("-").append(version);
		
		if (classifier != null) {
			path.append("-").append(classifier);
		}
		
		if (type == null) {
			type = DEFAULT_ARTIFACT_TYPE;
		}
		path.append(".").append(type);
		
		return new File(path.toString());
	}
}
