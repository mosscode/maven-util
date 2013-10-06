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

import java.net.URL;
import java.util.Properties;

import org.joda.time.Instant;

/**
 * Reads the property file that maven includes in jars when it builds them.
 * The file contains the time the jar was built, along with the groupId, 
 * artifactId, and version of the artifact to which the jar belongs.
 */
public class PropertyReader {
	
	private String groupId;
	private String artifactId;
	private String version;
	private Instant whenBuilt;
	
	private ClassLoader cl;
	
	public PropertyReader(String groupId, String artifactId) throws Exception {
		this(groupId, artifactId, null);
	}
	
	/**
	 * Use this constructor if you want to load the property file from a specific class loader
	 */
	public PropertyReader(String groupId, String artifactId, ClassLoader cl) throws Exception {
		this.cl = cl;
		
		String resourceClasspath = "META-INF/maven/" + groupId + "/" + artifactId + "/pom.properties";
		URL url;
		
		if (cl != null) {
			url = cl.getResource(resourceClasspath);
		}
		else {
			url = this.getClass().getResource("/" + resourceClasspath);
		}
		
		if(url==null){
			throw new RuntimeException("CAN'T FIND " + resourceClasspath);
		}
		Properties p = new Properties();
		p.load(url.openStream());
		this.groupId = p.getProperty("groupId");
		this.artifactId = p.getProperty("artifactId");
		this.version = p.getProperty("version");
		
		this.whenBuilt = new MavenPomPropertiesDateFormatter().parseWhenBuilt(url.openStream());
	}
	
	public String getGroupId() {
		return groupId;
	}
	
	public String getArtifactId() {
		return artifactId;
	}
	
	public String getVersion() {
		return version;
	}
	
	public Instant getWhenBuilt() {
		return whenBuilt;
	}
	
	/**
	 * Builds a {@link SimpleArtifact} initialized with only the applicable information
	 * available from the properties file this class reads. I.e., groupId, artifactId,
	 * and version.
	 * @return
	 */
	public SimpleArtifact buildSimpleArtifact() {
		SimpleArtifact a = new SimpleArtifact();
		a.setGroupId(groupId);
		a.setArtifactId(artifactId);
		a.setVersion(version);
		return a;
	}
}
