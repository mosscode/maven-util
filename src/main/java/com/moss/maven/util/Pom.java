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

import static com.moss.maven.util.impl.DomUtil.findNamedChildNode;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * An immutable class representing a pom file.
 */
public class Pom {
	
	private String groupId;
	private String artifactId;
	private String version;
	private Set<Dependency> dependencies = new HashSet<Dependency>();
	private Dependency parent;
	private List<String> modules = new LinkedList<String>();
	
	public Pom(File pomFile) throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(pomFile);
		Node projectTag = document.getDocumentElement();
		
		Node parentTag = findNamedChildNode("parent", projectTag);
		if(parentTag!=null){
			parent = new Dependency(parentTag);
		}
		
		Node groupIdTag = findNamedChildNode("groupId", projectTag);
		if(groupIdTag!=null){
			groupId = groupIdTag.getTextContent();
		}
		artifactId = findNamedChildNode("artifactId", projectTag).getTextContent();
		Node versionTag = findNamedChildNode("version", projectTag);
		
		// HANDLE 'CHILD' POMS WHO INHERIT THEIR VERSION FROM THEIR PARENT
		if(versionTag ==null && parentTag!=null){
			if(parentTag!=null){
				versionTag = findNamedChildNode("version", parentTag);
			}
		}
		
		version = versionTag.getTextContent();

		Node dependenciesTag = findNamedChildNode("dependencies", projectTag);
		if(dependenciesTag!=null){
			NodeList nodes = dependenciesTag.getChildNodes();
			for (int x=0;x<nodes.getLength();x++){
				Node nextChild = nodes.item(x);
				if(nextChild.getNodeName().equals("dependency")){
					dependencies.add(new Dependency(nextChild));
				}
			}
		}
		
		Node modulesTag = findNamedChildNode("modules", projectTag);
		if(modulesTag!=null){
			NodeList nodes = modulesTag.getChildNodes();
			for (int x=0;x<nodes.getLength();x++){
				Node nextChild = nodes.item(x);
				if(nextChild.getNodeName().equals("module")){
					modules.add(nextChild.getTextContent());
				}
			}
		}
		
	}
	
	public Dependency getParent() {
		return parent;
	}
	public String getArtifactId() {
		return artifactId;
	}
	public String getGroupId() {
		return groupId;
	}
	public String getVersion() {
		return version;
	}
	
	public class Dependency {

		private String groupId;
		private String artifactId;
		private String version;
		
		private Dependency(Node dependencyTag) {
			groupId = findNamedChildNode("groupId", dependencyTag).getTextContent().trim();
			artifactId = findNamedChildNode("artifactId", dependencyTag).getTextContent().trim();
				Node versionTag = findNamedChildNode("version", dependencyTag);
				if(versionTag!=null){
					version = versionTag.getTextContent().trim();
					if(version==null) throw new IllegalStateException("Cannot have a null version!");
				}
		}
		public String getArtifactId() {
			return artifactId;
		}
		public String getGroupId() {
			return groupId;
		}
		public String getVersion() {
			return version;
		}
		
	}
	public List<String> getModules() {
		return modules;
	}
	public Set<Dependency> getDependencies() {
		return dependencies;
	}
}
