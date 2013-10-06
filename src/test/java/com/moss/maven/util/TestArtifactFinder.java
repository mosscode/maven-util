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
import java.net.URL;
import java.net.URLClassLoader;

import com.moss.maven.util.PropertyReader;
import com.moss.maven.util.SimpleArtifact;
import com.moss.maven.util.SimpleArtifactFinder;

import junit.framework.TestCase;

public class TestArtifactFinder extends TestCase {

	public void testArtifactFinder() throws Exception {
		URL projectJarUrl = TestPropertyReader.class.getResource("/com/moss/maven/util/test-project-0.0.1a1-SNAPSHOT.jar");
		URLClassLoader cl = new URLClassLoader(new URL[] { projectJarUrl }, this.getClass().getClassLoader());
		
		PropertyReader reader = new PropertyReader("moss", "test-project", cl);
		SimpleArtifact artifact = reader.buildSimpleArtifact();
		
		File file = new SimpleArtifactFinder().findLocal(artifact);
		System.out.println(file);
		
		assertEquals(System.getProperty("user.home") + "/.m2/repository/moss/test-project/0.0.1a1-SNAPSHOT/test-project-0.0.1a1-SNAPSHOT.jar", file.toString());
		
		/*
		 * TODO: test this on m$windoze
		 */
	}
	
}
