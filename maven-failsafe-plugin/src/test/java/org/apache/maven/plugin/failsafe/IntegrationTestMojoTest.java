package org.apache.maven.plugin.failsafe;/* * Licensed to the Apache Software Foundation (ASF) under one * or more contributor license agreements.  See the NOTICE file * distributed with this work for additional information * regarding copyright ownership.  The ASF licenses this file * to you under the Apache License, Version 2.0 (the * "License"); you may not use this file except in compliance * with the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, * software distributed under the License is distributed on an * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY * KIND, either express or implied.  See the License for the * specific language governing permissions and limitations * under the License. */import org.apache.maven.artifact.Artifact;import org.apache.maven.artifact.DefaultArtifact;import org.apache.maven.artifact.versioning.InvalidVersionSpecificationException;import org.apache.maven.project.MavenProject;import org.junit.Before;import org.junit.Test;import java.io.File;import java.io.IOException;import static org.apache.maven.artifact.versioning.VersionRange.createFromVersionSpec;import static org.mockito.Mockito.mock;import static org.mockito.Mockito.spy;import static org.mockito.Mockito.when;import static org.fest.assertions.Assertions.assertThat;/** * @since 2.19.2 */public class IntegrationTestMojoTest{    private IntegrationTestMojo mojo;    @Before    public void init() throws InvalidVersionSpecificationException, IOException    {        Artifact artifact = new DefaultArtifact( "g", "a", createFromVersionSpec( "1.0" ), "compile", "jar", "", null );        artifact.setFile( new File( "./target/tmp/a-1.0.jar" ) );        new File( "./target/tmp" ).mkdir();        artifact.getFile().createNewFile();        mojo = spy( IntegrationTestMojo.class );        MavenProject project = mock( MavenProject.class );        when( project.getArtifact() ).thenReturn( artifact );        when( mojo.getProject() ).thenReturn( project );    }    @Test    public void shouldBeJar()    {        mojo.setDefaultClassesDirectory( new File( "./target/classes" ) );        File binaries = mojo.getClassesDirectory();        assertThat( binaries.getName() ).isEqualTo( "a-1.0.jar" );    }    @Test    public void shouldBeAnotherJar()    {        mojo.setClassesDirectory( new File( "./target/another-1.0.jar" ) );        mojo.setDefaultClassesDirectory( new File( "./target/classes" ) );        File binaries = mojo.getClassesDirectory();        assertThat( binaries.getName() ).isEqualTo( "another-1.0.jar" );    }    @Test    public void shouldBeClasses()    {        mojo.setClassesDirectory( new File( "./target/classes" ) );        mojo.setDefaultClassesDirectory( new File( "./target/classes" ) );        File binaries = mojo.getClassesDirectory();        assertThat( binaries.getName() ).isEqualTo( "classes" );    }}