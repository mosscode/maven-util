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

//http://www.sonatype.com/books/mvnref-book/reference/pom-relationships-sect-pom-syntax.html
//http://www.sonatype.com/books/mvnref-book/reference/pom-relationships-sect-pom-syntax.html#pom-relationships-sect-version-build-numbers
//http://maven.apache.org/pom.html#Maven_Coordinates
public class MavenVersion {
	  private final int majorVersion;
	  private final int minorVersion;
	  private final int incrementalVersion;
	  private final int buildNumber;
	  private final String qualifier;
	  
//	  public MavenVersion(String version) throws VersionParsingException {
//		  int firstHyphenIndex = version.indexOf('-');
//		  
//		  String majorMinorInc = version.substring(0, firstHyphenIndex);
//		  
//		  String[] majorMinorIncParts = majorMinorInc.split("-");
//		  
//		  if(majorMinorIncParts.length==0){
//			  throw new VersionParsingException("Invalid major+minor+incremental segment: " + majorMinorInc);
//		  }
//	  }
	  
	  public MavenVersion(int majorVersion, int minorVersion, int incrementalVersion, int buildNumber, String qualifier) {
		super();
		this.majorVersion = majorVersion;
		this.minorVersion = minorVersion;
		this.incrementalVersion = incrementalVersion;
		this.buildNumber = buildNumber;
		this.qualifier = qualifier;
	}
	  
}
