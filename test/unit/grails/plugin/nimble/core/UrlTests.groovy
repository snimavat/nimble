/*
 *  Nimble, an extensive application base for Grails
 *  Copyright (C) 2010 Bradley Beddoes
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package grails.plugin.nimble.core

import grails.test.GrailsUnitTestCase

/**
 * @author Bradley Beddoes
 */
class UrlTests extends GrailsUnitTestCase {

	private String name = 'name'
	private description = 'description'
	private location = 'http://unit.test.com'
	private altText = 'altText'

	private Url createValidUrl() {
		new Url(name:name, description:description, location:location, altText:altText)
	}

	void testUrlCreation() {
		def url = createValidUrl()

		assertEquals name, url.name
		assertEquals description, url.description
		assertEquals location, url.location
		assertEquals altText, url.altText
	}

	void testNameConstraint() {
		mockForConstraintsTests(Url)
		def url = createValidUrl()
		assertTrue url.validate()

		url.name = ''
		assertFalse url.validate()

		url.name = null
		assertTrue url.validate()
	}

	void testDescriptionConstraint() {
		mockForConstraintsTests(Url)
		def url = createValidUrl()
		assertTrue url.validate()

		url.description = ''
		assertFalse url.validate()

		url.description = null
		assertTrue url.validate()
	}

	void testLocationConstraint() {
		mockForConstraintsTests(Url)
		def url = createValidUrl()
		assertTrue url.validate()

		url.location = ''
		assertFalse url.validate()

		url.location = null
		assertFalse url.validate()

		url.location = 'asdfasdf'
		assertFalse url.validate()
	}

	void testAltTextConstraint() {
		mockForConstraintsTests(Url)
		def url = createValidUrl()
		assertTrue url.validate()

		url.altText = ''
		assertFalse url.validate()

		url.altText = null
		assertTrue url.validate()
	}
}
