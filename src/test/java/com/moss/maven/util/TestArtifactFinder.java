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
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;

public class TestArtifactFinder extends TestCase {

	public void testArtifactFinder() throws Exception {
		class TestCase {
			final SimpleArtifact artifact;
			final String expectedPath;
			public TestCase(SimpleArtifact artifact, String expectedPath) {
				super();
				this.artifact = artifact;
				this.expectedPath = expectedPath;
			}
		}
		
		List<TestCase> testCases = Arrays.asList(
				new TestCase(
						new SimpleArtifact("com.myco.mygroup", "myartifact", "38.3.2-SNAPSHOT", "special", "uberbundle"),
						"/com/myco/mygroup/myartifact/38.3.2-SNAPSHOT/myartifact-38.3.2-SNAPSHOT-special.uberbundle"),
				new TestCase(
						new SimpleArtifact("g", "a", "1", null, null),
						"/g/a/1/a-1.jar"),
				new TestCase(
						new SimpleArtifact("g", "a", "1", null, "widgetzip"),
						"/g/a/1/a-1.widgetzip"),
				new TestCase(
						new SimpleArtifact("g", "a", "1", "withsecretsauce", null),
						"/g/a/1/a-1-withsecretsauce.jar")
				);
		
		
		// given
		final SimpleArtifactFinder finder = new SimpleArtifactFinder(new Properties(){{
			put("user.home", "/some/nonexistent/user/home");
		}});
		
		for(TestCase testCase : testCases){
			// when
			File file = finder.findLocal(testCase.artifact);
			
			// then
			assertEquals("/some/nonexistent/user/home/.m2/repository" + testCase.expectedPath, file.toString());
		}
		
	}
	
}
